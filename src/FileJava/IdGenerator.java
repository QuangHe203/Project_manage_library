package FileJava;

public class IdGenerator {
    private static int numberBook = 20;

    public static String generateNextBookId() {
        if (numberBook >= 999) {
            throw new IllegalStateException("Cannot generate more book IDs.");
        }

        numberBook++; // Tăng giá trị số sau ký tự "B" lên 1
        String bookId = "Bo" + String.format("%03d", numberBook); // Tạo ID sách bằng cách ghép "Bo" với giá trị số mới
        return bookId;
    }

    private static int numberBorrower = 20;

    public static String generateNextBorrowerId() {
        if (numberBorrower >= 999) {
            throw new IllegalStateException("Cannot generate more book IDs.");
        }

        numberBorrower++; // Tăng giá trị số sau ký tự "B" lên 1
        String borrowerID = "Br" + String.format("%03d", numberBorrower); // Tạo ID sách bằng cách ghép "Br" với giá trị số mới
        return borrowerID;
    }

    private static int numberCard = 3;

    public static String generateNextCardId() {
        if (numberCard >= 999) {
            throw new IllegalStateException("Cannot generate more book IDs.");
        }

        numberCard++; // Tăng giá trị số sau ký tự "B" lên 1
        String cardId = "C" + String.format("%03d", numberCard); // Tạo ID sách bằng cách ghép "C" với giá trị số mới
        return cardId;
    }
}
