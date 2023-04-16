package FileJava;

import java.io.IOException;
import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.stage.Stage;

public class CardController {
    @FXML private Stage stage;
    @FXML private Scene scene;

    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/HomeScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToBook(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/BookScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToBorrower(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/BorrowerScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToGiveBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/GiveBackScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML private TextField cardIdTextField;
    @FXML private TextField borrowerIdTextField;
    @FXML private DatePicker borrowDateDatePicker;
    @FXML private DatePicker returnDateDatePicker;

    @FXML private TableView<Book> booksTableView;
    @FXML private TableColumn<Book, Integer> indexColumn;
    @FXML private TableColumn<Book, String> inputBookIdColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> publisherColumn;
    @FXML private TableColumn<Book, Integer> publicationYearColumn;
    @FXML private TableColumn<Book, String> genreColumn;

    private ObservableList<Book> borrowedBooks;

    public void initialize() {
        borrowedBooks = FXCollections.observableArrayList();

        indexColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(booksTableView.getItems().indexOf(cellData.getValue()) + 1).asObject());
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        publisherColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        publicationYearColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPublicationYear()).asObject());
        genreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));

        inputBookIdColumn.setCellFactory(param -> {
            TextField bookIdField = new TextField();
        
            TableCell<Book, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
        
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(bookIdField);
                    }
                }
            };
        
            // Xử lý sự kiện khi TextField được chỉnh sửa
            bookIdField.setOnAction(event -> {
                String newBookId = bookIdField.getText();
            
                // Tìm sách dựa trên newBookId
                Book bookToBorrow = findBookById(newBookId);
            
                // Nếu tìm thấy sách, cập nhật thông tin sách trong TableView
                if (bookToBorrow != null) {
                    updateBookInformation(cell.getIndex(), bookToBorrow);

                    // Kiểm tra xem dòng trống cuối cùng có phải là dòng cuối cùng trong TableView không
                    if (cell.getIndex() == borrowedBooks.size() - 1) {
                        borrowedBooks.add(new Book()); // Thêm một dòng trống mới vào cuối
                    }
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy sách", ButtonType.OK);
                    alert.setTitle("Không tìm thấy sách");
                    alert.setHeaderText(null);
                    alert.showAndWait();
            return;
                }
            });
            
        
            return cell;
        });
        
        
        borrowedBooks.add(new Book());
        booksTableView.setItems(borrowedBooks);
    }

    public Book findBookById(String id) {
        for (Book book : App.books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }
    
    private void updateBookInformation(int index, Book book) {
        borrowedBooks.set(index, book);
    }

    @FXML
    public void addBookToBorrowList(Book book) {
        borrowedBooks.add(book);
    }

    @FXML
    public void removeBookFromBorrowList(Book book) {
        borrowedBooks.remove(book);
    }

    @FXML
    public void createBorrowCard() {
        // Lấy thông tin từ các trường nhập liệu
        String cardId = cardIdTextField.getText();
        String borrowerId = borrowerIdTextField.getText();
        LocalDate borrowDate = borrowDateDatePicker.getValue();
        LocalDate returnDate = returnDateDatePicker.getValue();

        // Tạo đối tượng BorrowCard với thông tin đã lấy
        Card newCard = new Card(cardId, borrowerId, borrowDate, returnDate, borrowedBooks);
        
        App.cards.add(newCard); 
    }
}
