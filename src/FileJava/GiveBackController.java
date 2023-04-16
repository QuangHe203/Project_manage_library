package FileJava;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

public class GiveBackController extends BaseController {

    @FXML private TextField cardIdField;
    @FXML private TextField borrowerIdField;
    @FXML private DatePicker borrowDateField;
    @FXML private DatePicker returnDueDateField;

    // Nhập thông tin thẻ mượn sách, ID người mượn, ngày mượn, ngày hẹn trả
    @FXML
    public void processReturn(ActionEvent event) throws IOException {
        String cardId = cardIdField.getText();
        String borrowerId = borrowerIdField.getText();
        LocalDate borrowDate = borrowDateField.getValue();
        LocalDate returnDueDate = returnDueDateField.getValue();

        if (validateInput(cardId, borrowerId, borrowDate, returnDueDate)) {
            // Xử lý việc trả sách ở đây

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Trả sách thành công!");
            alert.showAndWait();

            // Chuyển về trang chủ sau khi trả sách thành công
            switchToHome(event);
        }
    }

    // kiểm tra và thông báo nếu nhập sai hoặc thiếu thông tin
    private boolean validateInput(String cardId, String borrowerId, LocalDate borrowDate, LocalDate returnDueDate) {
        String errorMessage = "";

        if (cardId == null || cardId.trim().isEmpty()) {
            errorMessage += "Vui lòng nhập ID thẻ mượn sách\n";
        }

        if (borrowerId == null || borrowerId.trim().isEmpty()) {
            errorMessage += "Vui lòng nhập ID người mượn sách\n";
        }

        if (borrowDate == null) {
            errorMessage += "Vui lòng chọn ngày mượn\n";
        }

        if (returnDueDate == null) {
            errorMessage += "Vui lòng chọn ngày hẹn trả\n";
        }

        if (returnDueDate != null && returnDueDate.isBefore(borrowDate)) {
            errorMessage += "Ngày hẹn trả không hợp lệ\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}



