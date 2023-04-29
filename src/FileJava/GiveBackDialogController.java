package FileJava;

import java.util.ArrayList;
import java.util.List;

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
    private ListView<String> listBook;

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

        // Hiển thị danh sách sách mượn trong ListView (giả định sách là chuỗi)
        listBook.getItems().setAll(getBookList(selectedCard));
    }

    // Phương thức giả định để tính phí trễ
    private double calculateLateFee(Card selectedCard) {
        // Thay thế bằng phương thức tính phí trễ của bạn
        return 0.0;
    }

    // Phương thức giả định để tính phí mất sách
    private double calculateMissingFee(Card selectedCard) {
        // Thay thế bằng phương thức tính phí mất sách của bạn
        return 0.0;
    }

    // Phương thức giả định để lấy danh sách sách mượn
    private List<String> getBookList(Card selectedCard) {
        // Thay thế bằng phương thức lấy danh sách sách mượn của bạn
        return new ArrayList<>();
    }
}
