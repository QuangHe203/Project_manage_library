package FileJava;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/MainScene.fxml"));
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

    public static void main(String[] args) {
        ObservableList<Book> books = FXCollections.observableArrayList(

        new Book("B01", "Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Bloomsbury Publishing", 1997, 50, "Fantasy", "Available", "A1"),
        new Book("B02", "The Hunger Games", "Suzanne Collins", "Scholastic Press", 2008, 40, "Science Fiction", "Available", "A2"),
        new Book("B03", "The Hobbit", "J.R.R. Tolkien", "Allen & Unwin", 1937, 30, "Fantasy", "Unavailable", "B1"),
        new Book("B04", "To Kill a Mockingbird", "Harper Lee", "J.B. Lippincott & Co.", 1960, 20, "Historical Fiction", "Available", "C2"),
        new Book("B06", "The Da Vinci Code", "Dan Brown", "Doubleday", 2003, 25, "Mystery", "Available", "B2"),
        new Book("B07", "The Girl with the Dragon Tattoo", "Stieg Larsson", "Norstedts Förlag", 2005, 15, "Thriller", "Available", "C3"),
        new Book("B08", "The Catcher in the Rye", "J.D. Salinger", "Little, Brown and Company", 1951, 20, "Coming-of-age Fiction", "Available", "A3"),
        new Book("B09", "The Lord of the Rings", "J.R.R. Tolkien", "Allen & Unwin", 1954, 30, "Fantasy", "Available", "D1"),
        new Book("B10", "The Great Gatsby", "F. Scott Fitzgerald", "Charles Scribner's Sons", 1925, 25, "Historical Fiction", "Available", "C4"),
        new Book("B11", "Pride and Prejudice", "Jane Austen", "T. Egerton, Whitehall", 1813, 25, "Romantic Fiction", "Available", "A4"),
        new Book("B12", "The Adventures of Tom Sawyer", "Mark Twain", "American Publishing Company", 1876, 15, "Children's Literature", "Available", "B3"),
        new Book("B13", "The Picture of Dorian Gray", "Oscar Wilde", "Lippincott's Monthly Magazine", 1890, 10, "Gothic Fiction", "Unavailable", "D2"),
        new Book("B14", "Little Women", "Louisa May Alcott", "Roberts Brothers", 1868, 20, "Coming-of-age Fiction", "Available", "C5"),
        new Book("B15", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "George Newnes Ltd", 1892, 30, "Mystery", "Available", "D3"),
        new Book("B16", "Wuthering Heights", "Emily Brontë", "Thomas Cautley Newby", 1847, 15, "Gothic Fiction", "Available", "B4"),
        new Book("B17", "The Brothers Karamazov", "Fyodor Dostoevsky", "The Russian Messenger", 1880, 25, "Philosophical Fiction", "Available", "A5"),
        new Book("B18", "Animal Farm", "George Orwell", "Secker and Warburg", 1945, 20, "Political Satire", "Available", "D4"),
        new Book("B19", "The Alchemist", "Paulo Coelho", "Editora Rocco", 1988, 20, "Philosophical Fiction", "Available", "B5"),
        new Book("B20", "The Chronicles of Narnia", "C.S. Lewis", "Geoffrey Bles", 1950, 40, "Fantasy", "Available", "C6"))
        launch(args);
    }
}

