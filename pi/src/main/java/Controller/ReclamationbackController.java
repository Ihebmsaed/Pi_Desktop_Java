
package Controller;

import entities.Reclamation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ReclamationServices;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ReclamationbackController implements Initializable {

    @FXML
    private TableView<Reclamation> tftableview;
    @FXML
    private TableColumn<?, ?> tfobjet;
    @FXML
    private TableColumn<?, ?> tfdescription;
    @FXML
    private TableColumn<?, ?> tftype;
    @FXML
    private Button Supprimer;
    @FXML
    private TableColumn<?, ?> tfdate;
    @FXML
    private TableColumn<?, ?> tfetat;
    @FXML
    private Button tftraite;

    @FXML
    private TextField supprimerTextField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ReclamationServices service=new ReclamationServices();
        ObservableList<Reclamation> list = service.getall();
         tfobjet.setCellValueFactory(new PropertyValueFactory<>("Rec_id"));
          tfdescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
          tftype.setCellValueFactory(new PropertyValueFactory<>("Type"));
          tfdate.setCellValueFactory(new PropertyValueFactory<>("status"));
          tfetat.setCellValueFactory(new PropertyValueFactory<>("Date_creation"));
          tftableview.setItems(list);




    }



    @FXML
    private void repondre(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichReclamOne.fxml"));
        Parent root = loader.load();

        // Obtenez la scène actuelle à partir de l'événement
        Scene scenee = ((Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow()).getScene();

        // Définir la nouvelle scène
        scenee.setRoot(root);
    }

    public void supprimer(ActionEvent actionEvent) throws SQLException {

        String idS=supprimerTextField.getText();
        int id=Integer.parseInt(idS);
        ReclamationServices service=new ReclamationServices();
        service.supprimerParRecId(id);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Reclamation supprimée");
        alert.show();


    }
}
