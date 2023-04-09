package FileJava;

import java.time.LocalDate;

public class Borrower {
    private String id;  //ID người mượn
    private String fullName;    //Tên gnuowfi mượn
    private String phoneNumber; //Số điện thoại người mượn
    private String email;   //Email người mượn
    private LocalDate dateOfBirth;  //Ngày tháng năm sinh
    private String gender;  //Giới tính
    private String type;    //Loại người mượn

    public Borrower() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
