package FileJava;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.stage.Stage;

public class BorrowerController implements Initializable{
    @FXML private Stage stage;
    @FXML private Scene scene;

    @FXML
    private TextField cardIdField;

    @FXML
    private TextField borrowerIdField;

    @FXML
    private DatePicker borrowDateField;

    @FXML
    private DatePicker returnDueDateField;
    
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
    
    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/HomeScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToBook(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/BookScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToCard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/CardScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToGiveBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/GiveBackScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Tạo tableview
    @FXML private TableView<Borrower> tableView;
    @FXML private TableColumn<Borrower, String> idColumn;
    @FXML private TableColumn<Borrower, String> fullNameColumn;
    @FXML private TableColumn<Borrower, String> phoneNumberColumn;
    @FXML private TableColumn<Borrower, LocalDate> dateOfBirthColumn;
    @FXML private TableColumn<Borrower, String> emailColumn;
    @FXML private TableColumn<Borrower, String> typeColumn;
    @FXML private TableColumn<Borrower, LocalDate> lastColumn;
    @FXML private ChoiceBox<String> typeComboBox;
    //Tạo một bookList mới dành cho việc hiển thị những sách được tìm
    private ObservableList<Borrower> borrowerList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        fullNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        dateOfBirthColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateOfBirth()));
        emailColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmail()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        lastColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLast()));

        //Khởi tạo giá trị cho tableView
        tableView.setItems(App.borrowers);

        typeComboBox.getItems().addAll("Sinh viên", "Giáo viên", "Nhân viên");
        typeComboBox.setValue("Sinh viên");
        borrowerList = FXCollections.observableArrayList();
    }

    //Chức năng tìm kiếm người mượn
    @FXML private TextField idTextField;
    @FXML private TextField fullNameTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private DatePicker dateOfBirthTextField;
    @FXML private TextField emailTextField;

    public ObservableList<Borrower> searchBorrowers(String id, String fullName, String phoneNumber, LocalDate dateOfBirth, String email, String type) {
        ObservableList<Borrower> result = FXCollections.observableArrayList();
        for (Borrower borrower : App.borrowers) {
            if (borrower.getId().contains(id)
                && borrower.getFullName().contains(fullName)
                && borrower.getPhoneNumber().contains(phoneNumber)
                && borrower.getDateOfBirth().equals(dateOfBirth)
                && borrower.getEmail().equals(email)
                && borrower.getType().equals(type)) {
                result.add(borrower);
            }
        }
        return result;
    }

    @FXML
    void searchBorrowers(ActionEvent event) {

        String id = idTextField.getText();
        String fullName = fullNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        LocalDate dateOfBirth = dateOfBirthTextField.getValue();
        String email = emailTextField.getText();
        String type = typeComboBox.getValue();

        // Call a method to search for borrowers in the database using search criteria
        ObservableList<Borrower> searchedBorrowers = FXCollections.observableList(searchBorrowers(id, fullName, phoneNumber, dateOfBirth, email, type));

        // Hiển thị người mượn đã được tìm kiếm lên tableview
        borrowerList.clear();
        borrowerList.addAll(searchedBorrowers);
        tableView.setItems(borrowerList);
    }

    // Chức năng chỉnh sửa người mượn sách
    @FXML private Button editBorrowerButton;

    @FXML
    void editBorrower(ActionEvent event) {
        Borrower selectedBorrower = tableView.getSelectionModel().getSelectedItem();
        if (selectedBorrower == null) {
            // Hiển thị thông báo cho người dùng biết họ cần chọn một người mượn sách để chỉnh sửa
            Alert alert = new Alert(AlertType.INFORMATION, "Chọn một người mượn sách để sửa", ButtonType.OK);
            alert.setTitle("Sai thao tác");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        // Hiển thị thông tin người mượn sách hiện tại trong các trường văn bản và hộp lựa chọn
        idTextField.setText(selectedBorrower.getId());
        fullNameTextField.setText(selectedBorrower.getFullName());
        phoneNumberTextField.setText(selectedBorrower.getPhoneNumber());
        dateOfBirthTextField.setValue(selectedBorrower.getDateOfBirth());
        emailTextField.setText(selectedBorrower.getEmail());
        typeComboBox.setValue(selectedBorrower.getType());

        // Thay đổi nút Chỉnh sửa thành nút Lưu
        editBorrowerButton.setText("Lưu");
        editBorrowerButton.setOnAction(e -> saveEditedBorrower(selectedBorrower, e));
    }

    private void saveEditedBorrower(Borrower borrowerToEdit, ActionEvent event) {
        // Cập nhật thông tin người mượn sách từ các trường văn bản và hộp lựa chọn
        borrowerToEdit.setId(idTextField.getText());
        borrowerToEdit.setFullName(fullNameTextField.getText());
        borrowerToEdit.setPhoneNumber(phoneNumberTextField.getText());
        borrowerToEdit.setDateOfBirth(dateOfBirthTextField.getValue());
        borrowerToEdit.setEmail(emailTextField.getText());
        borrowerToEdit.setType(typeComboBox.getValue());

        // Đồng bộ hóa với cơ sở dữ liệu (ví dụ: gọi phương thức cập nhật cơ sở dữ liệu)

        // Cập nhật giao diện người dùng
        tableView.refresh();

        // Thay đổi nút Lưu thành nút Chỉnh sửa
        editBorrowerButton.setText("Chỉnh sửa");
        editBorrowerButton.setOnAction(this::editBorrower);

        //Refresh các textfield
        idTextField.setText("");
        fullNameTextField.setText("");
        phoneNumberTextField.setText("");
        dateOfBirthTextField.setValue(null);
        emailTextField.setText("");
        typeComboBox.setValue(null);
}

    // Chức năng thêm người mượn sách
    @FXML private Button addBorrowerButton;

    @FXML
    void addBorrower(ActionEvent event) {
        try {
            String id = idTextField.getText();
            String fullName = fullNameTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();
            LocalDate dateOfBirth = dateOfBirthTextField.getValue();
            String email = emailTextField.getText();
            String type = typeComboBox.getValue();

            Borrower newBorrower = new Borrower(id, fullName, phoneNumber, dateOfBirth, email, type);
            App.borrowers.add(newBorrower);

            //Refresh các textfield
            idTextField.setText("");
            fullNameTextField.setText("");
            phoneNumberTextField.setText("");
            dateOfBirthTextField.setValue(null);
            emailTextField.setText("");
            typeComboBox.setValue(null);

        } catch (Exception e) {
            //Thông báo khi nhập liệu sai
            Alert alert = new Alert(AlertType.INFORMATION, "Kiểm tra lại các trường thông tin", ButtonType.OK);
            alert.setTitle("Cú pháp sai");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    @FXML private Button deleteBorrowerButton;

    @FXML
    void deleteBorrower(ActionEvent event) {
        Borrower selectedBorrower = tableView.getSelectionModel().getSelectedItem();
        if (selectedBorrower == null) {
            Alert alert = new Alert(AlertType.INFORMATION, "Chọn một người mượn để xóa", ButtonType.OK);
            alert.setTitle("Sai thao tác");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION, "Bạn có chắc chắn muốn xóa người mượn này không?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText(null);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            // Xóa người mượn khỏi cơ sở dữ liệu 
            App.borrowers.remove(selectedBorrower);

            // Tạo thông báo xóa người mượn thành công
            Alert successAlert = new Alert(AlertType.INFORMATION, "Người mượn đã được xóa thành công.", ButtonType.OK);
            successAlert.setTitle("Xóa người mượn thành công");
            successAlert.setHeaderText(null);
            successAlert.showAndWait();
        }
    }


}
