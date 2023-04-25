package FileJava;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class GiveBackController extends BaseController implements Initializable {
    //Tạo tableview
    @FXML private TableView<Card> tableView;
    @FXML private TableColumn<Card, String> idCardColumn;
    @FXML private TableColumn<Card, String> idBorrowerColumn;
    @FXML private TableColumn<Card, LocalDate> borrowDateColumn;
    @FXML private TableColumn<Card, LocalDate> returnDateColumn;

    private ObservableList<Card> cardList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCardColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardId()));
        idBorrowerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBorrowerId()));
        borrowDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBorrowDate()));
        returnDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getReturnDate()));
        

        //Khởi tạo giá trị cho tableView
        tableView.setItems(App.cards);

        cardList = FXCollections.observableArrayList();
    }

    @FXML private TextField idcardTextField;
    @FXML private TextField idBorrowerTextField;
    @FXML private DatePicker borrowDateTextField;
    @FXML private DatePicker returnDateTextField;

    public ObservableList<Card> searchCards(String idCard, String idBorrower, LocalDate borrowDate, LocalDate returnDate) {
        ObservableList<Card> result = FXCollections.observableArrayList();
        for (Card card : App.cards) {
            if (card.getCardId().contains(idCard)
                && card.getBorrowerId().contains(idBorrower)
                && (borrowDate == null || card.getBorrowDate().equals(borrowDate))
                && (returnDate == null || card.getBorrowDate().equals(returnDate))
                ) {
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
        ObservableList<Card> searchedCards = FXCollections.observableList(searchCards(idCard, idBorrower, borrowDate, returnDate));

        // Hiển thị thẻ đã được tìm kiếm lên tableview
        cardList.clear();
        cardList.addAll(searchedCards);
        tableView.setItems(cardList);
    }
}
