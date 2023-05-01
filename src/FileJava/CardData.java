package FileJava;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CardData {
        public static List<Card> generateCards() {
                List<Card> cards = new ArrayList<>();

                ObservableList<Book> books1 = FXCollections.observableArrayList();
                books1.add(new Book("BK001", "To Kill a Mockingbird", "Harper Lee", "Publisher 1", 1960, 5, "Fiction",
                                "Available", "Shelf 1", 100000));
                books1.add(new Book("BK002", "Pride and Prejudice", "Jane Austen", "Publisher 2", 1813, 3, "Romance",
                                "Available", "Shelf 2", 120000));
                books1.add(new Book("BK003", "1984", "George Orwell", "Publisher 3", 1949, 2, "Dystopian",
                                "Unavailable",
                                "Shelf 3", 140000));
                books1.add(new Book("BK004", "The Catcher in the Rye", "J.D. Salinger", "Publisher 4", 1951, 4,
                                "Fiction",
                                "Available", "Shelf 4", 150000));
                cards.add(new Card("B0001", "C0001", LocalDate.of(2023, 4, 20), LocalDate.of(2023, 5, 20), books1));

                ObservableList<Book> books2 = FXCollections.observableArrayList();
                books2.add(new Book("BK005", "The Great Gatsby", "F. Scott Fitzgerald", "Publisher 5", 1925, 6,
                                "Fiction",
                                "Available", "Shelf 5", 130000));
                books2.add(new Book("BK006", "Moby Dick", "Herman Melville", "Publisher 6", 1851, 1, "Adventure",
                                "Unavailable",
                                "Shelf 6", 180000));
                books2.add(new Book("BK007", "The Odyssey", "Homer", "Publisher 7", -800, 3, "Epic", "Available",
                                "Shelf 7",
                                200000));
                books2.add(new Book("BK008", "Crime and Punishment", "Fyodor Dostoevsky", "Publisher 8", 1866, 2,
                                "Fiction",
                                "Available", "Shelf 8", 160000));
                books2.add(new Book("BK009", "Brave New World", "Aldous Huxley", "Publisher 9", 1932, 4, "Dystopian",
                                "Unavailable", "Shelf 9", 170000));
                cards.add(new Card("B0002", "C0002", LocalDate.of(2023, 5, 20), LocalDate.of(2023, 6, 20), books2));

                return cards;

        }
}