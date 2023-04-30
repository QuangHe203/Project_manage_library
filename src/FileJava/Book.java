package FileJava;

public class Book {
    private String id; //Mã sách
    private String title;   //Tiêu đề
    private String author;  // Tác giả
    private String publisher;  //Nhà xuất bản
    private int publicationYear; //Năm xuất bản
    private int quantity;  //Số lượng
    private String genre;   // Thể loại
    private String status;  //Tình trạng
    private String location;    //Vị trí
    private double price; // Giá sách

    public Book() {

    }

    public Book(String id, String title, String author, String publisher, int publicationYear, int quantity, String genre, String status, String location, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.quantity = quantity;
        this.genre = genre;
        this.status = status;
        this.location = location;
        this.price = price;
    }

    public Book(String id, String title, String author, String publisher, int publicationYear, int quantity, String genre, String status, String location) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.quantity = quantity;
        this.genre = genre;
        this.status = status;
        this.location = location;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setStatus() {
        if(quantity == 0) {
            status = "Không có sẵn";
        } else {
            status = "Có sẵn";
        }
    }

    public String getStatus() {
        return status;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice(){ 
        return price;
    }
}
