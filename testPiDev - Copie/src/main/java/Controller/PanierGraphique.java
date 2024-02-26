package Controller;

import entities.Panier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PanierGraphique {

    @FXML
    private Button consulterPanierButton;

    @FXML
    void consulterPaniermeth(ActionEvent event) {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/PanierShow.fxml"));

        try {
            Parent root=loader.load();
            PanierShow pshow=loader.getController();
            //CommandeGraphique comm=loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }



}
