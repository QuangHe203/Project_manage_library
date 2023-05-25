package FileJava;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import Database.CardDAO;
import javafx.fxml.Initializable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

public class GiveBackController extends BaseController implements Initializable {
    // Tạo tableview
    @FXML
    private TableView<Card> tableView;
    @FXML
    private TableColumn<Card, String> idCardColumn;
    @FXML
    private TableColumn<Card, String> idBorrowerColumn;
    @FXML
    private TableColumn<Card, LocalDate> borrowDateColumn;
    @FXML
    private TableColumn<Card, LocalDate> returnDateColumn;
    @FXML
    private TableColumn<Card, Void> actionColumn;
    private ObservableList<Card> cardList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCardColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardId()));
        idBorrowerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBorrowerId()));
        borrowDateColumn
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBorrowDate()));
        returnDateColumn
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getReturnDate()));

        // Khởi tạo giá trị cho tableView
        tableView.setItems(App.cards);

        cardList = FXCollections.observableArrayList();
        createActionColumn();
    }

    @FXML
    private TextField idCardTextField;
    @FXML
    private TextField idBorrowerTextField;
    @FXML
    private DatePicker borrowDateTextField;
    @FXML
    private DatePicker returnDateTextField;

    public ObservableList<Card> searchCards(String idCard, String idBorrower, LocalDate borrowDate,
            LocalDate returnDate) {
        ObservableList<Card> result = FXCollections.observableArrayList();
        for (Card card : App.cards) {
            if (card.getCardId().contains(idCard)
                    && card.getBorrowerId().contains(idBorrower)
                    && (borrowDate == null || card.getBorrowDate().equals(borrowDate))
                    && (returnDate == null || card.getBorrowDate().equals(returnDate))) {
                result.add(card);
            }
        }
        return result;
    }

    @FXML
    void searchCards(ActionEvent event) {

        String idCard = idCardTextField.getText();
        String idBorrower = idBorrowerTextField.getText();
        LocalDate borrowDate = borrowDateTextField.getValue();
        LocalDate returnDate = returnDateTextField.getValue();

        // Call a method to search for cards in the database using search criteria
        ObservableList<Card> searchedCards = searchCards(idCard, idBorrower, borrowDate, returnDate);

        // Hiển thị thẻ đã được tìm kiếm lên tableview
        cardList.clear();
        cardList.addAll(searchedCards);
        tableView.setItems(cardList);
    }

    @FXML
    private Button confirmButton;

    @FXML
    void confirmCard(ActionEvent event) {
        Card selectedCard = tableView.getSelectionModel().getSelectedItem();
        if (selectedCard == null) {
            // Hiển thị thông báo cho người dùng biết họ cần chọn một sách để chỉnh sửa
            Alert alert = new Alert(AlertType.INFORMATION, "Chọn thẻ mượn sách", ButtonType.OK);
            alert.setTitle("Sai thao tác");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        showDialogAndConfirm(selectedCard);
    }

    private void showDialogAndConfirm(Card selectedCard) {
        try {
            // Load the FXML file and create the Dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FileFXML/GiveBackDialog.fxml"));
            DialogPane dialogPane = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);

            // Get controller of the Dialog and set the selectedCard data
            GiveBackDialogController controller = loader.getController();
            controller.setSelectedCardData(selectedCard);

            // Show the Dialog and handle the result
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.FINISH) {
                App.cards.remove(selectedCard);
                try {
                    CardDAO.deleteCard(selectedCard);
                } catch (SQLException e) {
                    Alert errorAlert = new Alert(AlertType.ERROR, "Đã xảy ra lỗi khi xóa sách khỏi cơ sở dữ liệu.", ButtonType.OK);
                    errorAlert.setTitle("Lỗi xóa sách");
                    errorAlert.setHeaderText(null);
                    errorAlert.showAndWait();
                    e.printStackTrace();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button confirmButton = new Button("Xác nhận");
    
            {
                confirmButton.setOnAction(event -> {
                    Card selectedCard = getTableView().getItems().get(getIndex());
                    showDialogAndConfirm(selectedCard);
                });
            }
    
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(confirmButton);
                }
            }
        });
    }
}