package FileJava;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class GiveBackDialogController {

    @FXML
    private Label idBorrowerLabel;

    @FXML
    private Label idCardLabel;

    @FXML
    private Label borrowDateLabel;

    @FXML
    private Label returnDateLabel;

    @FXML
    private Label feeLate;

    @FXML
    private Label feeMiss;

    @FXML
    private ListView<Book> listBook;

    public void setSelectedCardData(Card selectedCard) {
        idBorrowerLabel.setText(selectedCard.getBorrowerId());
        idCardLabel.setText(selectedCard.getCardId());
        borrowDateLabel.setText(selectedCard.getBorrowDate().toString());
        returnDateLabel.setText(selectedCard.getReturnDate().toString());

        // Tính phí trễ và phí mất sách
        double lateFee = calculateLateFee(selectedCard);
        double missingFee = calculateMissingFee(selectedCard);

        feeLate.setText(String.format("%.2f", lateFee));
        feeMiss.setText(String.format("%.2f", missingFee));

        /// Lấy danh sách sách mượn và hiển thị trong ListView
        List<Book> borrowedBooks = getBookList(selectedCard);
        listBook.getItems().clear();
        listBook.getItems().addAll(borrowedBooks);

        // Cập nhật cách hiển thị cho các mục trong ListView
        listBook.setCellFactory(CheckBoxListCell.forListView(new Callback<Book, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Book book) {
                BooleanProperty returned = new SimpleBooleanProperty(true);
                returned.addListener((obs, wasReturned, isNowReturned) -> {
                    if (!isNowReturned) {
                        // Cập nhật phí sách mất
                        double currentFee = Double.parseDouble(feeMiss.getText());
                        feeMiss.setText(String.valueOf(currentFee + book.getPrice()));
                    } else {
                        // Trừ phí sách mất
                        double currentFee = Double.parseDouble(feeMiss.getText());
                        feeMiss.setText(String.valueOf(currentFee - book.getPrice()));
                    }
                });
                return returned;
            }
        }, new StringConverter<Book>() {
            @Override
            public String toString(Book book) {
                return book.getTitle();
            }

            @Override
            public Book fromString(String string) {
                return null; // Không cần thiết
            }
        }));
    }

    // Phương thức giả định để tính phí trễ
    private double calculateLateFee(Card selectedCard) {
        double lateFeePerDay = 3000.0;
        LocalDate currentDate = LocalDate.now();
        LocalDate returnDate = selectedCard.getReturnDate();

        if (currentDate.isAfter(returnDate)) {
            long daysLate = ChronoUnit.DAYS.between(returnDate, currentDate);
            return daysLate * lateFeePerDay;
        } else {
            return 0.0;
        }
    }

    // Phương thức giả định để tính phí mất sách
    private double calculateMissingFee(Card selectedCard) {
        // Thay thế bằng phương thức tính phí mất sách của bạn
        return 0.0;
    }

    // Phương thức giả định để lấy danh sách sách mượn
    private List<Book> getBookList(Card selectedCard) {
        return selectedCard.getBorrowedBooks();
    }
}
