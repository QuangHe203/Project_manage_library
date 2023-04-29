package FileJava;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
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
    private TableColumn<Card, Card> actionColumn;
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
    private TextField idcardTextField;
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

        String idCard = idcardTextField.getText();
        String idBorrower = idBorrowerTextField.getText();
        LocalDate borrowDate = borrowDateTextField.getValue();
        LocalDate returnDate = returnDateTextField.getValue();

        // Call a method to search for cards in the database using search criteria
        ObservableList<Card> searchedCards = FXCollections
                .observableList(searchCards(idCard, idBorrower, borrowDate, returnDate));

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
            Alert alert = new Alert(AlertType.INFORMATION, "Chọn một sách để sửa", ButtonType.OK);
            alert.setTitle("Sai thao tác");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        showDialogAndConfirm(selectedCard);
    }
    
    private void showDialogAndConfirm(Card selectedCard) {
        // Tạo dialog để hiển thị thông tin của thẻ mượn sách được chọn
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Thông tin thẻ mượn sách");
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    
        // Tạo gridpane để chứa các trường thông tin
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
    
        // Thêm các trường thông tin về thẻ mượn sách vào gridpane
        gridPane.add(new Label("Mã thẻ mượn sách:"), 0, 0);
        gridPane.add(new Label(selectedCard.getCardId()), 1, 0);
    
        gridPane.add(new Label("Mã người mượn sách:"), 0, 1);
        gridPane.add(new Label(selectedCard.getBorrowerId()), 1, 1);
    
        gridPane.add(new Label("Ngày mượn sách:"), 0, 2);
        gridPane.add(new Label(selectedCard.getBorrowDate().toString()), 1, 2);
    
        gridPane.add(new Label("Danh sách sách đang mượn:"), 0, 3, 2, 1);
    
        ListView<Book> bookListView = new ListView<>(selectedCard.getBorrowedBooks());
        gridPane.add(bookListView, 0, 4, 2, 1);
    
        // Thêm gridpane vào dialog
        dialog.getDialogPane().setContent(gridPane);
    
        // Hiển thị dialog và chờ người dùng xác nhận
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Người dùng đã xác nhận, thực hiện xử lý trả sách tại đây
        }
    }
    
    private void createActionColumn() {
        actionColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()));
        actionColumn.setCellFactory(param -> {
            Button confirmButton = new Button("Xác nhận");
            TableCell<Card, Card> cell = new TableCell<>() {
                @Override
                protected void updateItem(Card item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(confirmButton);
                    }
                }
            };
            confirmButton.setOnAction(event -> {
                Card selectedCard = cell.getItem();
                showDialogAndConfirm(selectedCard);
            });
            return cell;
        });
    }
}    