package FileJava;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class BorrowerController extends BaseController implements Initializable {

    // Tạo tableview
    @FXML
    private TableView<Borrower> tableView;
    @FXML
    private TableColumn<Borrower, String> idColumn;
    @FXML
    private TableColumn<Borrower, String> fullNameColumn;
    @FXML
    private TableColumn<Borrower, String> phoneNumberColumn;
    @FXML
    private TableColumn<Borrower, LocalDate> dateOfBirthColumn;
    @FXML
    private TableColumn<Borrower, String> emailColumn;
    @FXML
    private TableColumn<Borrower, String> typeColumn;
    @FXML
    private TableColumn<Borrower, LocalDate> lastColumn;

    // Tạo một bookList mới dành cho việc hiển thị những sách được tìm
    private ObservableList<Borrower> borrowerList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        fullNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        phoneNumberColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        dateOfBirthColumn
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateOfBirth()));
        emailColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmail()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        lastColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLast()));

        // Khởi tạo giá trị cho tableView
        tableView.setItems(App.borrowers);

        typeComboBox.getItems().addAll("Sinh viên", "Giáo viên", "Nhân viên");
        typeComboBox.setValue("Sinh viên");
        borrowerList = FXCollections.observableArrayList();
    }

    // Chức năng tìm kiếm người mượn
    @FXML
    private TextField idTextField;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private DatePicker dateOfBirthTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private ChoiceBox<String> typeComboBox;

    public ObservableList<Borrower> searchBorrowers(String id, String fullName, String phoneNumber,
            LocalDate dateOfBirth, String email, String type) {
        ObservableList<Borrower> result = FXCollections.observableArrayList();
        for (Borrower borrower : App.borrowers) {
            if (borrower.getId().contains(id)
                    && borrower.getFullName().contains(fullName)
                    && borrower.getPhoneNumber().contains(phoneNumber)
                    && (dateOfBirth == null || borrower.getDateOfBirth().equals(dateOfBirth))
                    && borrower.getEmail().contains(email)
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
        ObservableList<Borrower> searchedBorrowers = FXCollections
                .observableList(searchBorrowers(id, fullName, phoneNumber, dateOfBirth, email, type));

        // Hiển thị người mượn đã được tìm kiếm lên tableview
        borrowerList.clear();
        borrowerList.addAll(searchedBorrowers);
        tableView.setItems(borrowerList);
    }

    // Chức năng chỉnh sửa người mượn sách
    @FXML
    private Button editBorrowerButton;

    @FXML
    void editBorrower(ActionEvent event) {
        Borrower selectedBorrower = tableView.getSelectionModel().getSelectedItem();
        if (selectedBorrower == null) {
            // Hiển thị thông báo cho người dùng biết họ cần chọn một người mượn sách để
            // chỉnh sửa
            Alert alert = new Alert(AlertType.INFORMATION, "Chọn một người mượn sách để sửa", ButtonType.OK);
            alert.setTitle("Sai thao tác");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        // Hiển thị thông tin người mượn sách hiện tại trong các trường văn bản và hộp
        // lựa chọn
        idTextField.setText(selectedBorrower.getId());
        idTextField.setEditable(false);
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

        try {
            // BorrowerDAO.updateBorrower(borrowerToEdit);
            App.borrowers.add(borrowerToEdit);
            // Cập nhật giao diện người dùng
            tableView.refresh();

            // hiển thị thông báo cho người dùng biết cập nhật thành công
            Alert alert = new Alert(AlertType.INFORMATION, "Đã cập nhật sách thành công!", ButtonType.OK);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.showAndWait();

            // Thay đổi nút Lưu thành nút Chỉnh sửa
            editBorrowerButton.setText("Lưu");
            editBorrowerButton.setOnAction(this::editBorrower);

            // Refresh các textfield
            idTextField.setText("");
            idTextField.setEditable(true);
            fullNameTextField.setText("");
            phoneNumberTextField.setText("");
            dateOfBirthTextField.setValue(null);
            emailTextField.setText("");
            typeComboBox.setValue(null);
        } catch (Exception ex) {
            // nếu thất bại, hiển thị thông báo lỗi cho người dùng
            Alert alert = new Alert(AlertType.ERROR, "Lỗi khi cập nhật sách. Vui lòng thử lại!", ButtonType.OK);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    // Chức năng thêm người mượn sách
    @FXML
    private Button addBorrowerButton;

    @FXML
    void addBorrower(ActionEvent event) {
        String id = IdGenerator.generateNextBorrowerId();
        idTextField.setText(id);
        idTextField.setEditable(false);

        addBorrowerButton.setText("Lưu");
        addBorrowerButton.setOnAction(e -> saveNewBorrower(e));
    }

    @FXML
    void saveNewBorrower(ActionEvent event) {
        String id = idTextField.getText();
        String fullName = fullNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        LocalDate dateOfBirth = dateOfBirthTextField.getValue();
        String email = emailTextField.getText();
        String type = typeComboBox.getValue();

        // Kiểm tra tính hợp lệ của dữ liệu nhập
        if (fullName.isEmpty() || phoneNumber.isEmpty() || dateOfBirth == null || email.isEmpty() || type == null) {
            Alert alert1 = new Alert(AlertType.INFORMATION, "Vui lòng nhập đầy đủ thông tin", ButtonType.OK);
            alert1.setTitle("Thông tin không hợp lệ");
            alert1.setHeaderText(null);
            alert1.showAndWait();
            return;
        }

        Borrower newBorrower = new Borrower(id, fullName, phoneNumber, dateOfBirth, email, type);
        App.borrowers.add(newBorrower);
        //Thêm sách vào cơ sở dũ liệu
        /*
        try {
            Borrower.addBorrower(newBorrower);
        } catch (SQLException ex) {
            Alert alert = new Alert(AlertType.ERROR, "Lỗi khi lưu sách mới, vui lòng thử lại", ButtonType.OK);
            alert.setTitle("Lỗi cơ sở dữ liệu");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        */

        // Refresh các trường thông tin
        idTextField.setText("");
        idTextField.setEditable(true);
        fullNameTextField.setText("");
        phoneNumberTextField.setText("");
        dateOfBirthTextField.setValue(null);
        emailTextField.setText("");
        typeComboBox.setValue(null);
        //Đặt lại hành động của nút
        addBorrowerButton.setText("Thêm");
        addBorrowerButton.setOnAction(this::addBorrower);

        // Thông báo thành công
        Alert alert2 = new Alert(AlertType.INFORMATION, "Tạo thẻ mượn sách thành công", ButtonType.OK);
        alert2.setTitle("Tạo thẻ mượn sách thành công");
        alert2.setHeaderText(null);
        alert2.showAndWait();
        return;
    }

    @FXML
    private Button deleteBorrowerButton;

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

        Alert alert = new Alert(AlertType.CONFIRMATION, "Bạn có chắc chắn muốn xóa người mượn này không?",ButtonType.YES, ButtonType.NO);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText(null);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            //Xóa sách khỏi cơ sở dữ liệu
            /*
             try {
                BorrowerDAO.deleteBorrower(selectedBorrower);
             } catch (SQLException e) {
                Alert errorAlert = new Alert(AlertType.ERROR, "Đã xảy ra lỗi khi xóa sách khỏi cơ sở dữ liệu.", ButtonType.OK);
                errorAlert.setTitle("Lỗi xóa sách");
                errorAlert.setHeaderText(null);
                errorAlert.showAndWait();
                e.printStackTrace();
                return;  
             }
             */
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
