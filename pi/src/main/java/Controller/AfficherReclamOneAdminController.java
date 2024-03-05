/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;


import entities.Reclamation;
import entities.Reponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import services.ReclamationServices;
import services.ReponsesServices;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class AfficherReclamOneAdminController implements Initializable {

    @FXML
    private Button AddRepAdmin;
    @FXML
    private ImageView GoBckBtn;
    @FXML
    private TextArea RepTextAdmin;

    @FXML
    private Button SetStatusClosed;
    @FXML
    private Label StatusRec;
    @FXML
    private Label TitreRec;
    @FXML
    private Label DescRec;
    @FXML
    private VBox DescRep;
    private int recId;
    private Reclamation r;
    
    ReclamationServices rs = new ReclamationServices();
    
    private ReponsesServices Reps = new ReponsesServices();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void AjoutRep(ActionEvent event) throws SQLException, IOException {
        // Get the response description from the text area
    String repDesc = RepTextAdmin.getText();

    // Check that the response description is not empty
    if (repDesc.trim().isEmpty()) {
        // Show a popup error message and return
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez entrer une réponse.");
        alert.showAndWait();
        return;
    }
    // Create a new Reponses object with the rec_id, user_id, and response description
    Reponse rep = new Reponse();
    rep.setRec_id(recId);
    //rep.setUser_id(r.getUser_id());
    rep.setAdmin_id(1);
    rep.setRep_desc(repDesc);
    rep.setDate_rep(new Date());

    // Call the ajouter method of ReponsesService and pass the new Reponses object to it
    Reps.ajouter(rep);

    // Show a popup message to notify the user that the reply has been added
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Succès");
    alert.setHeaderText(null);
    alert.setContentText("Votre réponse Admin a été ajoutée.");
    alert.showAndWait();
    // Clear the reply text area
    RepTextAdmin.clear();
    // RefreshPage
    SetReclamation(rep.getRec_id(),  r);
    }
    

    //methode pour changer l'etat du reclamation
    @FXML
    private void ChangeStateToClosed(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Boîte de dialogue de confirmation");
        alert.setHeaderText("Définir le statut a \"Fermé\" ?");
        alert.setContentText("Cliquez sur OK pour confirmer ou sur Annuler pour revenir");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (DescRep.getChildren().isEmpty()) {
                Alert noResponsesAlert = new Alert(AlertType.WARNING);
                noResponsesAlert.setTitle("Attention");
                noResponsesAlert.setHeaderText("Impossible de fermer la réclamation");
                noResponsesAlert.setContentText("La réclamation doit avoir au moins une réponse pour pouvoir être fermée");
                noResponsesAlert.showAndWait();
            } else {
                rs.ModifierEtat(recId);
                r.setStatus("Fermé");
                StatusRec.setText("Status: " + r.getStatus());
                SetStatusClosed.setDisable(true);
                AddRepAdmin.setDisable(true);
                RepTextAdmin.setDisable(true);
            }
    }
}
    
    @FXML
    private void GoBck(MouseEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLReclamationback.fxml"));
            Parent root = loader.load();

            // Set the root of the current scene to the new FXML file
            GoBckBtn.getScene().setRoot(root);
    }

    
    //Methode Set reclamation avec appel reponses
    void SetReclamation(int rec_id, Reclamation reclamation) throws SQLException, IOException {
            this.recId = rec_id;
            this.r = reclamation;

            // Get the reclamation with the specified ID from the database
            Reclamation r = rs.recupererParId(recId);
            List<Reponse> reponses = Reps.recupererParRecId(recId);

            // Set the labels to display the reclamation data
            DescRep.getChildren().clear();
            
            
            int numRep = 1;
            for (Reponse rep : reponses) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Reponse.fxml"));
                AnchorPane reponseNode = loader.load();
                ReponseController reponseController = loader.getController();
                reponseController.setAdminData(rep);


                DescRep.getChildren().add(reponseNode);
                numRep++;
            }


            // Check the status of the reclamation and hide the buttons if the status is "Closed"
            if (r.getStatus().equals("Fermé")) {
            SetStatusClosed.setDisable(true);
            AddRepAdmin.setDisable(true);
            RepTextAdmin.setDisable(true);
            }
}
}
