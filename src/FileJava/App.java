package FileJava;

import Database.BookDAO;
import Database.BorrowerDAO;
import Database.CardDAO;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeCard(Card card) {
        // Xóa thẻ đã chọn từ danh sách (giả sử bạn có một danh sách chứa các thẻ)
        cards.remove(card);
    }

    public static void loadBooksFromDatabase() {
        try {
            books.addAll(BookDAO.getAllBooks());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadBorrowsFromDatabase() {
        try {
            borrowers.addAll(BorrowerDAO.getAllBorrowers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadCardsFromDatabase() {
        try {
            cards.addAll(CardDAO.getAllCards());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        loadBooksFromDatabase();
        loadBorrowsFromDatabase();
        loadCardsFromDatabase();
        launch(args);
    }
}