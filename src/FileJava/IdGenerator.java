package FileJava;

public class IdGenerator {
    private static int bookCounter = 20;

    public static String generateNextBookId() {
        if (bookCounter >= 999) {
            throw new IllegalStateException("Cannot generate more book IDs.");
        }
        String bookId = "Bo" + String.format("%03d", bookCounter);
        return bookId;
    }
    public static void updateNumberBook() {
        bookCounter++;
    }

    private static int borrowerCounter = 20;

    public static String generateNextBorrowerId() {
        if (borrowerCounter >= 999) {
            throw new IllegalStateException("Cannot generate more borrower IDs.");
        }

        String borrowerID = "Br" + String.format("%03d", borrowerCounter);
        return borrowerID;
    }

    public static void updateNumberBorrower() {
        borrowerCounter++;
    }

    private static int cardCounter = 3;

    public static String generateNextCardId() {
        if (cardCounter >= 999) {
            throw new IllegalStateException("Cannot generate more card IDs.");
        }

        String cardId = "C" + String.format("%03d", cardCounter);
        return cardId;
    }

    public static void updateNumberCard() {
        cardCounter++;
    }
}
