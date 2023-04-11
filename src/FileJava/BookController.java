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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class BookController implements Initializable {

    @FXML private TableView<Book> tableView;
    @FXML private TableColumn<Book, String> idColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> publisherColumn;
    @FXML private TableColumn<Book, Integer> publicationYearColumn;
    @FXML private TableColumn<Book, String> genreColumn;
    @FXML private TableColumn<Book, String> locationColumn;
    @FXML private TableColumn<Book, String> statusColumn;

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
        //Tạo một bookList mới dành cho việc hiển thị những sách được tìm
        bookList = FXCollections.observableArrayList();
    }

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

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

    @FXML
    public void switchToAddBook(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/AddBookScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private TextField idTextField;
    
    @FXML
    private TextField titleTextField;
    
    @FXML
    private TextField authorTextField;
    
    @FXML
    private TextField publisherTextField;
    
    @FXML
    private ChoiceBox<String> genreComboBox;
    
    @FXML
    private TextField publishDateTextField;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();
    
    public ObservableList<Book> searchBooks(String id, String title, String author, String publisher, String genre, Integer publishDate) {
        ObservableList<Book> result = FXCollections.observableArrayList();
        for (Book book : App.books) {
            if (book.getId().contains(id)
                    && book.getTitle().contains(title)
                    && book.getAuthor().contains(author)
                    && book.getPublisher().contains(publisher)
                    && book.getGenre().equals(genre)
                    && (publishDate == null || book.getPublicationYear() == publishDate)) {
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
        Integer publishDate = !publishDateTextField.getText().isEmpty() ? Integer.parseInt(publishDateTextField.getText()) : null;
        
        // Call a method to search for books in database using search criteria
        ObservableList<Book> searchedBooks = FXCollections.observableList(searchBooks(id, title, author, publisher, genre, publishDate));
       

        // Update bookList with searchedBooks and display in bookTableView
        bookList.clear();
        bookList.addAll(searchedBooks);
        tableView.setItems(bookList);
    }
}
