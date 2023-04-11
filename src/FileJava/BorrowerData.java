package FileJava;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowerData {
    public static List<Borrower> generateBorrowers() {
        List<Borrower> borrowers = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            String id = "BR" + String.format("%03d", i);
            String fullName = "Người Mượn " + i;
            String phoneNumber = "0123456789";
            LocalDate dateOfBirth = LocalDate.of(1990, 1, 1).plusYears(i);
            String email = "borrower" + i + "@example.com";
            String type = (i % 3 == 0) ? "Sinh viên" : (i % 3 == 1) ? "Giáo viên" : "Nhân viên";

            borrowers.add(new Borrower(id, fullName, phoneNumber, dateOfBirth, email, type));
        }

        return borrowers;
    }
}
