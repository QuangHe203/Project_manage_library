package FileJava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FileFXML/MainScene.fxml"));
            Scene scene1 = new Scene(root);

            Image icon = new Image("/Images/library.png");
            stage.getIcons().add(icon);
            stage.setTitle("Manage library app");
            stage.setScene(scene1);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

