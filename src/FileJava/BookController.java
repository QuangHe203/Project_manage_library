package FileJava;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class BookController implements Initializable {
    //Các chức năng chuyển
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
    public void switchToBorrower(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/BorrowerScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToCard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/CardScene.fxml"));
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

        genreComboBox.getItems().addAll("Fantasy", "Historical Fiction", "Romantic Fiction", "Gothic Fiction", "Thriller", "Mystery");
        genreComboBox.setValue("Fantasy");
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
            Alert alert = new Alert(AlertType.INFORMATION, "Sai thao tác", ButtonType.OK);
            alert.setTitle("Chọn một sách để sửa");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        // Hiển thị thông tin sách hiện tại trong các trường văn bản và hộp lựa chọn
        idTextField.setText(selectedBook.getId());
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

        // Đồng bộ hóa với cơ sở dữ liệu (ví dụ: gọi phương thức cập nhật cơ sở dữ liệu)

        // Cập nhật giao diện người dùng
        tableView.refresh();

        // Thay đổi nút Lưu thành nút Chỉnh sửa
        editButton.setText("Chỉnh sửa");
        editButton.setOnAction(this::editBook);

        //Refresh các textfield
        idTextField.setText("");
        titleTextField.setText("");
        authorTextField.setText("");
        publisherTextField.setText("");
        publicationYearTextField.setText("");
        quantityTextField.setText("");
        locationTextField.setText("");
        genreComboBox.setValue(null);
        }

    //Chức năng thêm sách
    @FXML
    void addBook(ActionEvent event) {
        try {                String id = idTextField.getText();
            String title = titleTextField.getText();
            String author = authorTextField.getText();
            String publisher = publisherTextField.getText();
            int publicationYear = Integer.parseInt(publicationYearTextField.getText());
            int quantity = Integer.parseInt(quantityTextField.getText());
            String location = locationTextField.getText();
            String status = quantity == 0 ? "Không có sẵn" : "Có sẵn";
            String genre = genreComboBox.getValue();
    
            Book newBook = new Book(id, title, author, publisher, publicationYear, quantity, genre, status, location);
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
                Alert alert = new Alert(AlertType.INFORMATION, "Cú pháp sai", ButtonType.OK);
                alert.setTitle("Kiểm tra lại các trường thông tin");
                alert.setHeaderText(null);
                alert.showAndWait();
        }
    }

    @FXML
    void deleteBook(ActionEvent event) {
        Book selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            Alert alert = new Alert(AlertType.INFORMATION, "Sai thao tác", ButtonType.OK);
            alert.setTitle("Chọn một sách đê xóa");
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
            App.books.remove(selectedBook);
            //Tạo dialog thông báo xóa sách thành công
            Alert sucessAlert = new Alert(AlertType.INFORMATION, "Sách đã được xóa thành công.", ButtonType.OK);
            sucessAlert.setTitle("Xóa sách thành công");
            sucessAlert.setHeaderText(null);
            sucessAlert.showAndWait();
        }
    }
}