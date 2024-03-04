package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.MyConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PanierGraphique {

    @FXML
    private Button consulterPanierButton;


    @FXML
    void ajouterDesProduitsStatiquement(ActionEvent event) throws SQLException {
        // First INSERT statement
        String requet1 = "INSERT INTO `panier` (`idpanier`, `iduser`, `idproduit`, `quantite`) VALUES ('1', '3', '7', '0');";
        PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requet1);
        ps.executeUpdate();

        // Second INSERT statement
        String requet2 = "INSERT INTO panier(iduser, idproduit,quantite) VALUES (3, 3,0)";
        PreparedStatement ps2 = MyConnection.getInstance().getCnx().prepareStatement(requet2);
        ps2.executeUpdate();

        // Third INSERT statement
        String requet3 = "INSERT INTO panier(iduser, idproduit,quantite) VALUES (3, 4,0)";
        PreparedStatement ps3 = MyConnection.getInstance().getCnx().prepareStatement(requet3);
        ps3.executeUpdate();

        // Fourth INSERT statement
        String requet4 = "INSERT INTO panier(iduser, idproduit,quantite) VALUES (3, 7,0)";
        PreparedStatement ps4 = MyConnection.getInstance().getCnx().prepareStatement(requet4);
        ps4.executeUpdate();

        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Dialog information");
        alert.setContentText("les produits sont insérés au panier");
        alert.show();


    }


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

    @FXML
    void consulterCommandeAdmin(ActionEvent event) {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AdminCommande.fxml"));

        try {
            Parent root=loader.load();
            AdminCommande acc=loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }

}
