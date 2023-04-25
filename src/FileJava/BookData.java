package FileJava;

import java.util.ArrayList;
import java.util.List;

public class BookData {
    public static List<Book> generateBooks() {
        List<Book> books = new ArrayList<>();

        books.add(new Book("BK001", "To Kill a Mockingbird", "Harper Lee", "Publisher 1", 1960, 5, "Fiction", "Available", "Shelf 1"));
        books.add(new Book("BK002", "Pride and Prejudice", "Jane Austen", "Publisher 2", 1813, 3, "Romance", "Available", "Shelf 2"));
        books.add(new Book("BK003", "1984", "George Orwell", "Publisher 3", 1949, 2, "Dystopian", "Unavailable", "Shelf 3"));
        books.add(new Book("BK004", "The Catcher in the Rye", "J.D. Salinger", "Publisher 4", 1951, 4, "Fiction", "Available", "Shelf 4"));
        books.add(new Book("BK005", "The Great Gatsby", "F. Scott Fitzgerald", "Publisher 5", 1925, 6, "Fiction", "Available", "Shelf 5"));
        books.add(new Book("BK006", "Moby Dick", "Herman Melville", "Publisher 6", 1851, 1, "Adventure", "Unavailable", "Shelf 6"));
        books.add(new Book("BK007", "The Odyssey", "Homer", "Publisher 7", -800, 3, "Epic", "Available", "Shelf 7"));
        books.add(new Book("BK008", "Crime and Punishment", "Fyodor Dostoevsky", "Publisher 8", 1866, 2, "Fiction", "Available", "Shelf 8"));
        books.add(new Book("BK009", "Brave New World", "Aldous Huxley", "Publisher 9", 1932, 4, "Dystopian", "Unavailable", "Shelf 9"));
        books.add(new Book("BK010", "The Brothers Karamazov", "Fyodor Dostoevsky", "Publisher 10", 1880, 1, "Fiction", "Available", "Shelf 10"));
        books.add(new Book("BK011", "One Hundred Years of Solitude", "Gabriel García Márquez", "Publisher 11", 1967, 5, "Magical Realism", "Available", "Shelf 11"));
        books.add(new Book("BK012", "The Lord of the Rings", "J.R.R. Tolkien", "Publisher 12", 1954, 3, "Fantasy", "Unavailable", "Shelf 12"));
        books.add(new Book("BK013", "Catch-22", "Joseph Heller", "Publisher 13", 1961, 2, "Fiction", "Available", "Shelf 13"));
        books.add(new Book("BK014", "Jane Eyre", "Charlotte Bronte", "Publisher 14", 1847, 4, "Fiction", "Available", "Shelf 14"));
        books.add(new Book("BK015", "Animal Farm", "George Orwell", "Publisher 15", 1945, 6, "Satire", "Unavailable", "Shelf 15"));
        books.add(new Book("BK016", "Frankenstein", "Mary Shelley", "Publisher 16", 1818, 3, "Gothic", "Available", "Shelf 16"));
        books.add(new Book("BK017", "The Picture of Dorian Gray", "Oscar Wilde", "Publisher 17", 1890, 2, "Fiction", "Available", "Shelf 17"));
        books.add(new Book("BK018", "Wuthering Heights", "Emily Bronte", "Publisher 18", 1847, 4, "Gothic", "Available", "Shelf 18"));
        books.add(new Book("BK019", "The Iliad", "Homer", "Publisher 19", -750, 6, "Epic", "Unavailable", "Shelf 19"));
        books.add(new Book("BK020", "War and Peace", "Leo Tolstoy", "Publisher 20", 1869, 1, "Historical Fiction", "Available", "Shelf 20"));

        return books;
    }
}
