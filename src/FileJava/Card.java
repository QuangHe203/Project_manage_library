package FileJava;

import java.time.LocalDate;
import java.util.List;

public class Card {
    private String borrowCardId;    // ID thẻ mượn sách
    private String borrowerId;  //ID người mượn sách
    private List<Book> borrowedBooks;   //Danh sách sách mượn
    private LocalDate borrowDate;   //Ngày mượn
    private LocalDate dueDate;  //Ngày hẹn trả

    public Card() {

    }

    public Card(String borrowCardId, String borrowerId, List<Book> borrowedBooks, LocalDate borrowDate, LocalDate dueDate) {
        this.borrowCardId = borrowCardId;
        this.borrowerId = borrowerId;
        this.borrowedBooks = borrowedBooks;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public String getBorrowCardId() {
        return borrowCardId;
    }

    public void setBorrowCardId(String borrowCardId) {
        this.borrowCardId = borrowCardId;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
