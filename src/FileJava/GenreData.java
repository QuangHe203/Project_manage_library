package FileJava;

public class GenreData {
    private String genre;
    private int count;
    private int quantity;

    public GenreData(String genre, int count, int quantity) {
        this.genre = genre;
        this.count = count;
        this.quantity = quantity;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
