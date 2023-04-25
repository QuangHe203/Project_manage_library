package FileJava;

import Database.BookDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class BookController extends BaseController implements Initializable {

    //Tạo tableview
    @FXML private TableView<Book> tableView;
    @FXML private TableColumn<Book, String> idColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> publisherColumn;
    @FXML private TableColumn<Book, Integer> publicationYearColumn;
    @FXML private TableColumn<Book, String> genreColumn;
    @FXML private TableColumn<Book, String> locationColumn;
    @FXML private TableColumn<Book, String> statusColumn;
    //Tạo một bookList mới dành cho việc hiển thị những sách được tìm
    private ObservableList<Book> bookList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        publisherColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        publicationYearColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPublicationYear()));
        genreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));
        locationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        //Khởi tạo giá trị cho tableView
        tableView.setItems(App.books);

        genreComboBox.getItems().addAll("Sách", "Sách tự giúp", "Tiểu thuyết", "Truyện cổ tích", "Truyện ngắn", "Truyện thiếu nhi", "Truyện tranh", "Văn học cổ điển");
        genreComboBox.setValue("Văn học cổ điển");
        bookList = FXCollections.observableArrayList();
    }
    
    //Chức năng tìm kiếm sách
    @FXML private TextField idTextField;
    @FXML private TextField titleTextField;
    @FXML private TextField authorTextField;
    @FXML private TextField publisherTextField;
    @FXML private TextField publicationYearTextField;
    @FXML private TextField quantityTextField;
    @FXML private ChoiceBox<String> genreComboBox;  
    @FXML private TextField statusTextField;
    @FXML private TextField locationTextField;
    
    public ObservableList<Book> searchBooks(String id, String title, String author, String publisher, String genre, Integer publishYear) {
        ObservableList<Book> result = FXCollections.observableArrayList();
        for (Book book : App.books) {
            if (book.getId().contains(id)
                    && book.getTitle().contains(title)
                    && book.getAuthor().contains(author)
                    && book.getPublisher().contains(publisher)
                    && book.getGenre().equals(genre)
                    && (publishYear == null || book.getPublicationYear() == publishYear)) {
                result.add(book);
            }
        }
        return result;
    }
    
    @FXML
    void searchBooks(ActionEvent event) {
        String id = idTextField.getText();
        String title = titleTextField.getText();
        String author = authorTextField.getText();
        String publisher = publisherTextField.getText();
        String genre = genreComboBox.getValue();
        Integer publishYear = !publicationYearTextField.getText().isEmpty() ? Integer.parseInt(publicationYearTextField.getText()) : null;
        
        // Call a method to search for books in database using search criteria
        ObservableList<Book> searchedBooks = FXCollections.observableList(searchBooks(id, title, author, publisher, genre, publishYear));
       
        // Hiển thị sách đã được tìm kiếm lên tableview
        bookList.clear();
        bookList.addAll(searchedBooks);
        tableView.setItems(bookList);
    }

    //CHức năng chỉnh sửa sách
    @FXML private Button editButton;

    @FXML
    void editBook(ActionEvent event) {
        Book selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            // Hiển thị thông báo cho người dùng biết họ cần chọn một sách để chỉnh sửa
            Alert alert = new Alert(AlertType.INFORMATION, "Chọn một sách để sửa", ButtonType.OK);
            alert.setTitle("Sai thao tác");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        // Hiển thị thông tin sách hiện tại trong các trường văn bản và hộp lựa chọn
        idTextField.setText(selectedBook.getId());
        idTextField.setEditable(false);
        titleTextField.setText(selectedBook.getTitle());
        authorTextField.setText(selectedBook.getAuthor());
        publisherTextField.setText(selectedBook.getPublisher());
        locationTextField.setText(selectedBook.getLocation());
        genreComboBox.setValue(selectedBook.getGenre());
        publicationYearTextField.setText(String.valueOf(selectedBook.getPublicationYear()));
        quantityTextField.setText(String.valueOf(selectedBook.getQuantity()));

        // Thay đổi nút Chỉnh sửa thành nút Lưu
        editButton.setText("Lưu");
        editButton.setOnAction(e -> saveEditedBook(selectedBook, e));
    }

    private void saveEditedBook(Book bookToEdit, ActionEvent event) {
        // Cập nhật thông tin sách từ các trường văn bản và hộp lựa chọn
        bookToEdit.setId(idTextField.getText());
        bookToEdit.setTitle(titleTextField.getText());
        bookToEdit.setAuthor(authorTextField.getText());
        bookToEdit.setPublisher(publisherTextField.getText());
        bookToEdit.setGenre(genreComboBox.getValue());
        bookToEdit.setPublicationYear(Integer.parseInt(publicationYearTextField.getText()));
        bookToEdit.setQuantity(Integer.parseInt(quantityTextField.getText()));
        bookToEdit.setLocation(locationTextField.getText());
        
        try {
            // cập nhật thông tin sách
            BookDAO.updateBook(bookToEdit);
            
            // nếu thành công, thực hiện các hành động cần thiết
            tableView.refresh();
            
            // hiển thị thông báo cho người dùng biết cập nhật thành công
            Alert alert = new Alert(AlertType.INFORMATION, "Đã cập nhật sách thành công!", ButtonType.OK);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.showAndWait();
            
            // Thay đổi nút Lưu thành nút Chỉnh sửa
            editButton.setText("Chỉnh sửa");
            editButton.setOnAction(this::editBook);
            
            //Refresh các textfield
            idTextField.setText("");
            idTextField.setEditable(true);
            titleTextField.setText("");
            authorTextField.setText("");
            publisherTextField.setText("");
            publicationYearTextField.setText("");
            quantityTextField.setText("");
            locationTextField.setText("");
            genreComboBox.setValue(null);
            
        } catch (Exception ex) {
            // nếu thất bại, hiển thị thông báo lỗi cho người dùng
            Alert alert = new Alert(AlertType.ERROR, "Lỗi khi cập nhật sách. Vui lòng thử lại!", ButtonType.OK);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
    
    @FXML private Button addButton;

    //Chức năng thêm sách
    @FXML
    void addBook(ActionEvent event) throws SQLException {
        try {
            String id = idTextField.getText();
            String title = titleTextField.getText();
            String author = authorTextField.getText();
            String publisher = publisherTextField.getText();
            int publicationYear = Integer.parseInt(publicationYearTextField.getText());
            int quantity = Integer.parseInt(quantityTextField.getText());
            String location = locationTextField.getText();
            String status = quantity == 0 ? "Không có sẵn" : "Có sẵn";
            String genre = genreComboBox.getValue();

            // Thêm sách vào database
            Book newBook = new Book(id, title, author, publisher, publicationYear, quantity, genre, status, location);
            BookDAO.addBook(newBook);

            // Thêm sách vào danh sách hiển thị trên giao diện ứng dụng
            App.books.add(newBook);

            //Refresh các textfield
            idTextField.setText("");
            titleTextField.setText("");
            authorTextField.setText("");
            publisherTextField.setText("");
            publicationYearTextField.setText("");
            quantityTextField.setText("");
            locationTextField.setText("");
            genreComboBox.setValue(null);

        } catch (NumberFormatException e) {
                //Thông báo khi 
                Alert alert = new Alert(AlertType.INFORMATION, "Vui lòng kiểm tra lại các trường thông tin", ButtonType.OK);
                alert.setTitle("Thông tin không hợp lệ");
                alert.setHeaderText(null);
                alert.showAndWait();
        }
    }

    // Phương thức xóa sách
    @FXML
    void deleteBook(ActionEvent event) {
        Book selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            Alert alert = new Alert(AlertType.INFORMATION, "Chọn một sách đê xóa", ButtonType.OK);
            alert.setTitle("Sai thao tác");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION, "Bạn có chắc chắn muốn xóa sách này không?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText(null);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            // Xóa sách khỏi cơ sở dữ liệu
            try {
                BookDAO.deleteBook(selectedBook);
            } catch (SQLException e) {
                Alert errorAlert = new Alert(AlertType.ERROR, "Đã xảy ra lỗi khi xóa sách khỏi cơ sở dữ liệu.", ButtonType.OK);
                errorAlert.setTitle("Lỗi xóa sách");
                errorAlert.setHeaderText(null);
                errorAlert.showAndWait();
                e.printStackTrace();
                return;
            }
            // Xóa sách khỏi danh sách hiển thị trên giao diện ứng dụng
            App.books.remove(selectedBook);
            //Tạo dialog thông báo xóa sách thành công
            Alert successAlert = new Alert(AlertType.INFORMATION, "Sách đã được xóa thành công.", ButtonType.OK);
            successAlert.setTitle("Xóa sách thành công");
            successAlert.setHeaderText(null);
            successAlert.showAndWait();
        }
    }
}