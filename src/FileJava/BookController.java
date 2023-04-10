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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;


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

    tableView.setItems(App.books);
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

    public TableView<Book> getBookTable() {
        return bookTable;
    }

    public void setBookTable(TableView<Book> bookTable) {
        this.bookTable = bookTable;
    }

    public TableColumn<Book, Integer> getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(TableColumn<Book, Integer> idColumn) {
        this.idColumn = idColumn;
    }

    public TableColumn<Book, String> getTitleColumn() {
        return titleColumn;
    }

    public void setTitleColumn(TableColumn<Book, String> titleColumn) {
        this.titleColumn = titleColumn;
    }

    public TableColumn<Book, String> getAuthorColumn() {
        return authorColumn;
    }

    public void setAuthorColumn(TableColumn<Book, String> authorColumn) {
        this.authorColumn = authorColumn;
    }

    public TableColumn<Book, String> getGenreColumn() {
        return genreColumn;
    }

    public void setGenreColumn(TableColumn<Book, String> genreColumn) {
        this.genreColumn = genreColumn;
    }

    public TableColumn<Book, Integer> getQuantityColumn() {
        return quantityColumn;
    }

    public void setQuantityColumn(TableColumn<Book, Integer> quantityColumn) {
        this.quantityColumn = quantityColumn;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
