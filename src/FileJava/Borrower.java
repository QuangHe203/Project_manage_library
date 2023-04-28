package FileJava;

import java.time.LocalDate;

public class Borrower {
    private String id;  //ID người mượn
    private String fullName;    //Tên người mượn
    private String phoneNumber; //Số điện thoại người mượn
    private String email;   //Email người mượn
    private LocalDate dateOfBirth;  //Ngày tháng năm sinh
    private String type;    //Loại người mượn
    private LocalDate last; //Ngày mượn cuối cùng

    public Borrower() {

    }

    public Borrower(String id, String fullName, String phoneNumber,LocalDate date, String email, String type) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfBirth = date;
        this.type = type;
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

    public void setLast(LocalDate last) {
        this.last = last;
    }

    public LocalDate getLast() {
        return last;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
