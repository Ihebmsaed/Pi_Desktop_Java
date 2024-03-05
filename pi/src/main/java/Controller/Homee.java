package Controller;

import entities.Reponse;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Homee extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
            //FXMLLoader loadere = new FXMLLoader(getClass().getResource("/AffichReclamOne.fxml"));

        try {
            Parent root = loader.load();

            //Parent roote = loadere.load();
            Scene scene = new Scene(root);
           // Scene scene1 = new Scene(roote);
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage secondStage = new Stage();
            //secondStage.setScene(scene1);
            //secondStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setData(Reponse rep) {
    }
}
