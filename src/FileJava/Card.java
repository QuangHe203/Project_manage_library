package FileJava;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Card {
    private String cardId;    // ID thẻ mượn sách
    private String borrowerId;  //ID người mượn sách
    private LocalDate borrowDate;   //Ngày mượn
    private LocalDate returnDate;  //Ngày hẹn trả
    private ObservableList<Book> borrowedBooks = FXCollections.observableArrayList(); // Danh sách sách mượn

    public Card() {

    }

    public Card(String cardId, String borrowerId, LocalDate borrowDate, LocalDate returnDate, List<Book> borrowedBooks) {
        this.cardId = cardId;
        this.borrowerId = borrowerId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.borrowedBooks = FXCollections.observableArrayList(borrowedBooks);
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public ObservableList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    
    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = FXCollections.observableArrayList(borrowedBooks);
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
