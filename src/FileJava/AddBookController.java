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
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AddBookController implements Initializable {
    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    public void switchToBook(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/BookScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void refresh(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/AddBookScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML private ChoiceBox<String> genreComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        genreComboBox.getItems().addAll("Fantasy", "Historical Fiction", "Romantic Fiction", "Gothic Fiction", "Thriller", "Mystery");
        genreComboBox.setValue("Fantasy");
    }

    @FXML private TextField idTextField;
    @FXML private TextField titleTextField;
    @FXML private TextField authorTextField;
    @FXML private TextField publisherTextField;
    @FXML private TextField publicationYearTextField;
    @FXML private TextField quantityTextField;
    @FXML private TextField locationTextField;
   
    @FXML
    void addBook(ActionEvent event) {
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

            Book newBook = new Book(id, title, author, publisher, publicationYear, quantity, genre, status, location);
            App.books.add(newBook);

            // clear text fields after adding book
            idTextField.setText("");
            titleTextField.setText("");
            authorTextField.setText("");
            publisherTextField.setText("");
            publicationYearTextField.setText("");
            quantityTextField.setText("");
            locationTextField.setText("");

            genreComboBox.setValue(null);

        } catch (NumberFormatException e) {
            // Handle exception if user enters invalid input for publication year or quantity
            System.out.println("Invalid input format");
        }
    }
}
