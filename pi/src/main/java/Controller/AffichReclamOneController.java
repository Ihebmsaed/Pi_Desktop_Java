package Controller;

import entities.Reclamation;
import entities.Reponse;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ReclamationServices;
import services.ReponsesServices;

public class AffichReclamOneController implements Initializable {




    private ReclamationServices rs = new ReclamationServices();
    private ReponsesServices Reps = new ReponsesServices();

    private int rec_id;
    @FXML
    private TextArea RepText;
    @FXML
    private ImageView GoBckBtn;
    private int user_id;
    @FXML
    private ComboBox<Reclamation> reclamationComboBox;

    @FXML
    private Button RemRec;
    @FXML
    private Button AddRep1;
    @FXML
    private VBox DescRep;

    private ReclamationServices reclamationService;

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            reclamationService = new ReclamationServices();

            // Récupère la liste des réclamations depuis le service
            ObservableList<Reclamation> reclamations = reclamationService.getall();

            // Ajoute les réclamations au ComboBox
            reclamationComboBox.setItems(reclamations);

            // Configure l'affichage du nom de la réclamation dans le ComboBox
            reclamationComboBox.setCellFactory(param -> new ListCell<Reclamation>() {
                @Override
                protected void updateItem(Reclamation reclamation, boolean empty) {
                    super.updateItem(reclamation, empty);
                    if (empty || reclamation == null) {
                        setText(null);
                    } else {
                        setText(reclamation.getRec_id() + ": " + reclamation.getDescription()); // Ou un autre attribut pertinent
                    }
                }

            });


        }

    //setUserId mab3outha men aand ReclamationController
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }



    @FXML
    public void AfficherReponses(int rec_id) throws SQLException, IOException {
        this.rec_id = rec_id;

        // Get the reclamation with the specified ID from the database
        Reclamation r = rs.recupererParId(rec_id);
        List<Reponse> reponses = Reps.recupererParRecId(rec_id);

        // Set the labels to display the reclamation data
        DescRep.getChildren().clear();
        int numRep = 1;
        for (Reponse rep : reponses) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Reponse.fxml"));
            AnchorPane reponseNode = loader.load();
            Homee reponseController = loader.getController();
            reponseController.setData(rep);
            DescRep.getChildren().add(reponseNode);
            numRep++;
        }


        // Check the status of the reclamation and hide the buttons if the status is "Closed"
        if (r.getStatus().equals("Fermé")) {
            AddRep1.setDisable(true);
            RepText.setDisable(true);
        }
    }






    @FXML
    private void AjoutRep(ActionEvent event) throws SQLException, IOException {
        // Get the response description from the text area
        String repDesc = RepText.getText();

        // Check that the response description is not empty
        if (repDesc.trim().isEmpty()) {
            // Show a popup error message and return
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une réponse.");
            alert.showAndWait();
            return;
        }

        // Create a new Reponses object with the rec_id, user_id, and response description
        Reponse rep = new Reponse();
        rep.setRec_id(rec_id);
        //rep.setuser_id(user_id);
        rep.setRep_desc(repDesc);
        rep.setDate_rep(new Date());

        // Call the ajouter method of ReponsesService and pass the new Reponses object to it
        Reps.ajouter(rep);

        // Show a popup message to notify the user that the reply has been added
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Votre réponse a été ajoutée.");
        alert.showAndWait();

        // Récupère la réclamation sélectionnée dans le ComboBox
        Reclamation selectedReclamation = reclamationComboBox.getValue();

        // Vérifie si une réclamation est sélectionnée
        if (selectedReclamation != null) {
            // Met à jour le statut de la réclamation sélectionnée pour la marquer comme traitée
            selectedReclamation.setStatus("Traité");

            // Appelle la méthode de mise à jour du service de réclamation
            reclamationService.modifier(selectedReclamation);
        }

        // Clear the reply text area
        RepText.clear();
        // Refresh the response page with the newly added response
        AfficherReponses(rec_id);



    }


     /*
     @FXML
     private void GoBck(MouseEvent event) throws IOException {
         // Load the new FXML file
         FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLReclamationback.fxml"));
         Parent root = loader.load();

         // Set the root of the current scene to the new FXML file
         FXMLReclamationbackController controller = loader.getController();
         controller.setNewUserId(user_id);

         GoBckBtn.getScene().setRoot(root);
     }
     */
    @FXML
    private void RemoveRec(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       // alert.setTitle("Confirmation");
        //alert.setHeaderText(null);
       // alert.setContentText("Voulez-vous vraiment supprimer cette réclamation ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {


                Reps.supprimerParRecId(rec_id);
                rs.supprimerParRecId(rec_id);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reclamationstat.fxml"));
                Parent root = loader.load();
                Scene sceneee = ((Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow()).getScene();
                sceneee.setRoot(root);
                ReclamationbackController controller = loader.getController();
               // controller.setNewUser_id(user_id);
                RemRec.getScene().setRoot(root);


            } catch (SQLException ex) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Erreur");
                alertError.setHeaderText(null);
                alertError.setContentText("Une erreur s'est produite lors de la suppression de la récupération.");
                alertError.showAndWait();
            } catch (IOException ex) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Erreur");
                alertError.setHeaderText(null);
                alertError.setContentText("Une erreur s'est produite lors du chargement de la liste des réclamations.");
                alertError.showAndWait();
            }
        }
    }


    public void GoBck(MouseEvent mouseEvent) {
    }
}
