package FileJava;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

public class HomeController implements Initializable {
    @FXML
    private PieChart bookPieChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<String, Integer> genreMap = new HashMap<>();
    
        for(Book book : App.books) {
            String genre = book.getGenre();
            if(!genreMap.containsKey(genre)) {
              genreMap.put(genre, 1);
            } else {
             int count = genreMap.get(genre);
             genreMap.put(genre, count + 1);
            }
        }
    
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    
        for(Map.Entry<String, Integer> entry : genreMap.entrySet()) {
           pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    
        bookPieChart.setData(pieChartData);
}

}

