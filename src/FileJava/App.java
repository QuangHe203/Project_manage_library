package FileJava;

import Database.BookDAO;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    public static ObservableList<Borrower> borrowers = FXCollections.observableArrayList();
    public static ObservableList<Card> cards = FXCollections.observableArrayList();
    public static ObservableList<Book> books = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/HomeScene.fxml"));
            Scene scene1 = new Scene(root);

            Image icon = new Image("/Images/library.png");
            stage.getIcons().add(icon);
            stage.setTitle("Manage library app");
            stage.setScene(scene1);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadBooksFromDatabase() {
        try {
            books.addAll(BookDAO.getAllBooks());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        borrowers.addAll(BorrowerData.generateBorrowers());
        books.addAll(BookData.generateBooks());
<<<<<<< HEAD
        cards.addAll(CardData.generateCards());
=======
>>>>>>> dafd9721a6119c32ef860cb22d45480cbff9c586
        loadBooksFromDatabase();
        launch(args);
    }
}

