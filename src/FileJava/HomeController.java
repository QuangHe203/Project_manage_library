package FileJava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Label totalBooksLabel;

    @FXML
    private PieChart bookPieChart;

    int totalBooks = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    Map<String, GenreData> genreMap = new HashMap<>();

    for(Book book : App.books) {
        String genre = book.getGenre();
        if(!genreMap.containsKey(genre)) {
            GenreData genreData = new GenreData(genre, 1, book.getQuantity());
            genreMap.put(genre, genreData);
        } else {
            GenreData genreData = genreMap.get(genre);
            genreData.setCount(genreData.getCount() + 1);
            genreData.setQuantity(genreData.getQuantity() + book.getQuantity());
        }
        totalBooks += book.getQuantity();
    }
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    for (java.util.Map.Entry<String, GenreData> entry : genreMap.entrySet()) {
        String genre = entry.getKey();
        GenreData genreData = entry.getValue();
        pieChartData.add(new PieChart.Data(genre + " (" + genreData.getQuantity() + " sách)", genreData.getCount()));
    }

    bookPieChart.setData(pieChartData);
    totalBooksLabel.setText("Thống kê số lượng sách : " + totalBooks);
    }

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    public void switchToBook(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/BookScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToBorrower(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/Borrower.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @ FXML
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
}

