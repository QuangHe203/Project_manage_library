package FileJava;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import Database.CardDAO;
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
    String cardId = IdGenerator.generateNextCardId();
        
    private Set<String> bookIds = new HashSet<>();

    public void initialize() {
        borrowedBooks = FXCollections.observableArrayList();

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

        borrowerIdTextField.setOnKeyPressed(event ->{
            if (event.getCode() == KeyCode.ENTER) {
                checkBorrowerId();
            }
        });
        cardIdTextField.setEditable(false);
        cardIdTextField.setText(cardId);
        booksTableView.setItems(borrowedBooks);
    }

    public void checkBorrowerId() {
        String borowerId = borrowerIdTextField.getText();
        if (!borowerId.isEmpty()) {
            Borrower borrower = findBorrowerById(borowerId);
            if (borrower == null) {
                 // Hiển thị thông báo nếu không tìm người mượn tương ứng
                 Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy người mượn có ID tương ứng", ButtonType.OK);
                 alert.setTitle("Không tìm thấy");
                 alert.setHeaderText(null);
                 alert.showAndWait();
            }
        }
    }

    public Borrower findBorrowerById(String id) {
        for (Borrower borrower : App.borrowers) {
            if (borrower.getId().equals(id)) {
                return borrower;
            }
        }
        return null;
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
            if (bookIds.contains(bookId)) {
                // Hiển thị thông báo nếu sách đã có trong danh sách mượn
                Alert alert = new Alert(AlertType.INFORMATION, "Sách đã có trong danh sách mượn", ButtonType.OK);
                alert.setTitle("Sách đã có");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }
            Book book = findBookById(bookId);
            if (book != null) {
                borrowedBooks.add(book);
                bookIds.add(bookId); // Thêm ID sách vào tập hợp để kiểm tra sách đã có trong danh sách mượn hay chưa
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

        try {
            CardDAO.addCard(newCard);
        } catch (SQLException ex) {
            Alert alert = new Alert(AlertType.ERROR, "Lỗi khi lưu sách mới, vui lòng thử lại", ButtonType.OK);
            alert.setTitle("Lỗi cơ sở dữ liệu");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        App.cards.add(newCard);

        //Gắn ngày mượn gần nhất của người mượn sách với ngày tạo thẻ sách
        for (Borrower borrower : App.borrowers) {
            if (borrower.getId().equals(borrowerId)) {
                borrower.setLast(borrowDate);
            }
        }

        
        // Loại bỏ sách đã mượn khỏi danh sách sách có sẵn
        for (Book borrowedBook : borrowedBooks) {
            App.books.remove(borrowedBook);
        }

        // Làm mới TableView và các trường nhập liệu
        booksTableView.refresh();
        cardIdTextField.setText("");
        borrowerIdTextField.setText("");
        borrowerIdTextField.setEditable(true);
        borrowDateDatePicker.setValue(null);
        returnDateDatePicker.setValue(null);

        // Làm mới danh sách sách đã mượn và HashSet chứa ID sách
        borrowedBooks.clear();
        bookIds.clear();
        IdGenerator.updateNumberCard();
        updateCardId();

        //Thông báo thành công
        Alert alert2 = new Alert(AlertType.INFORMATION, "Tạo thẻ mượn sách thành công", ButtonType.OK);
        alert2.setTitle("Tạo thẻ mượn sách thành công");
        alert2.setHeaderText(null);
        alert2.showAndWait();
        return;
    }
    // Thêm phương thức updateCardId() để sinh mã thẻ mượn sách mới
    private void updateCardId() {
        cardId = IdGenerator.generateNextCardId(); // Tạo mã thẻ mượn sách mới
        cardIdTextField.setText(cardId); // Đặt giá trị mới cho cardIdTextField
    }
}