package FileJava;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;

public class CardController extends BaseController {

    @FXML private TextField cardIdTextField;
    @FXML private TextField borrowerIdTextField;
    @FXML private TextField bookIdTextField;
    @FXML private DatePicker borrowDateDatePicker;
    @FXML private DatePicker returnDateDatePicker;

    @FXML private TableView<Book> booksTableView;
    @FXML private TableColumn<Book, Integer> indexColumn;
    @FXML private TableColumn<Book, String> bookIdColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> publisherColumn;
    @FXML private TableColumn<Book, Integer> publicationYearColumn;
    @FXML private TableColumn<Book, String> genreColumn;
    @FXML private TableColumn<Book, Void> deleteColumn;

    private ObservableList<Book> borrowedBooks;
    private Set<String> bookIds = new HashSet<>();

    public void initialize() {
        borrowedBooks = FXCollections.observableArrayList();

        //inputBookIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        indexColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(booksTableView.getItems().indexOf(cellData.getValue()) + 1).asObject());
        bookIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        publisherColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        publicationYearColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPublicationYear()).asObject());
        genreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));


        deleteColumn.setCellFactory(param -> new TableCell<Book, Void>() {
            private final Button deleteButton = new Button("Xóa");
        
            {
                deleteButton.setOnAction(event -> {
                    Book bookToDelete = getTableView().getItems().get(getIndex());
                    removeBookFromBorrowList(bookToDelete);
                    bookIds.remove(bookToDelete.getId()); // Xóa ID sách khỏi tập hợp khi sách bị xóa khỏi danh sách mượn
                });
            }
        
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
        
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
        
        bookIdTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addBookToBorrowList();
            }
        });
        
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

    @FXML
    public void addBookToBorrowList() {
        String bookId = bookIdTextField.getText();
        if (!bookId.isEmpty()) {
            Book book = findBookById(bookId);
            if (book != null) {
                borrowedBooks.add(book);
                bookIdTextField.clear();
            } else {
                // Hiển thị thông báo nếu không tìm thấy sách
                Alert alert = new Alert(AlertType.INFORMATION, "Không tìm tháy sách có ID tương ứng", ButtonType.OK);
                alert.setTitle("Không tìm thấy sách");
                alert.setHeaderText(null);
                alert.showAndWait();
            return;
            }
        }
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

        // Kiểm tra tính hợp lệ của dữ liệu nhập
        if (cardId.isEmpty() || borrowerId.isEmpty() || borrowDate == null || returnDate == null) {
            Alert alert1 = new Alert(AlertType.INFORMATION, "Vui lòng nhập đầy đủ thông tin", ButtonType.OK);
            alert1.setTitle("Thông tin không hợp lệ");
            alert1.setHeaderText(null);
            alert1.showAndWait();
            return;
        }

        // Tạo đối tượng BorrowCard với thông tin đã lấy
        Card newCard = new Card(cardId, borrowerId, borrowDate, returnDate, borrowedBooks);

        App.cards.add(newCard);

        
        // Loại bỏ sách đã mượn khỏi danh sách sách có sẵn
        for (Book borrowedBook : borrowedBooks) {
            App.books.remove(borrowedBook);
        }

        // Làm mới TableView và các trường nhập liệu
        booksTableView.refresh();
        cardIdTextField.setText("");
        borrowerIdTextField.setText("");
        borrowDateDatePicker.setValue(null);
        returnDateDatePicker.setValue(null);

        // Làm mới danh sách sách đã mượn và HashSet chứa ID sách
        borrowedBooks.clear();
        bookIds.clear();
        borrowedBooks.add(new Book());

        //Thông báo thành công
        Alert alert2 = new Alert(AlertType.INFORMATION, "Tạo thẻ mượn sách thành công", ButtonType.OK);
        alert2.setTitle("Tạo thẻ mượn sách thành công");
        alert2.setHeaderText(null);
        alert2.showAndWait();
        return;
    }
}