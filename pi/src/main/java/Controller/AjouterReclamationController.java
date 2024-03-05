package Controller;

import API.EmailSender;
import entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import services.ReclamationServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AjouterReclamationController implements Initializable {
    @FXML
    private DatePicker date;

    @FXML
    private Label date_creation;
    @FXML
    private AnchorPane Titre_rec;

    @FXML
    private TextArea description;

    @FXML
    private TextField titre_rec;

    @FXML
    private ChoiceBox<String> type;
    @FXML
    private Button Modifier;
    private int user_id;
    @FXML
    private void Modifier(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLReclamationback.fxml"));
        Parent root = loader.load();

        // Obtenez la scène actuelle à partir de l'événement
        Scene scene = ((Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow()).getScene();

        // Définir la nouvelle scène
        scene.setRoot(root);
    }
    ReclamationServices rs = new ReclamationServices();
    public void initData(Reclamation r) {
        this.type.getSelectionModel().select(r.getType());
        this.description.setText(r.getDescription());
        this.titre_rec.setText(r.getTitre_rec());
        System.out.println(r.getDescription());
        this.Modifier.setOnMouseClicked((m) -> {
            ReclamationServices serv = new ReclamationServices();
            if (!r.getDescription().equals("")) {
                r.setDescription(this.description.getText());
                r.setTitre_rec(this.titre_rec.getText());
                r.setType((String)this.type.getValue());
                try {
                    serv.modifier(r);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/AjouterReclamation.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage)((Node)m.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException var7) {
                    Logger.getLogger(ReclamationbackController.class.getName()).log(Level.SEVERE, (String)null, var7);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("");
                alert.setContentText("Veuiller Selectionner La reclamation a modifier ");
                alert.showAndWait();
            }

        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> choices = FXCollections.observableArrayList(
                "Evenement",
                "Ticket",
                "Produit"
        );
        type.setItems(choices);

        type.setOnAction(event -> {
            String selectedChoice = type.getValue();
            System.out.println("Selected: " + selectedChoice);
        });
    }
    private void reset() {
        titre_rec.setText("");
        description.setText("");
    }



    @FXML
    private void AjouterReclamation(ActionEvent event) {
       // String titre_rec = titre_rec.getText();
       // String description = description.getText();
        //ReclamationServices reclamationServices = new ReclamationServices();
        if (titre_rec.getText().isEmpty() || description.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ(s) vide(s)");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs avant de continuer.");
            alert.showAndWait();
            return;
        }
        bad_words(titre_rec.getText());
        bad_words(description.getText());



        Reclamation r = new Reclamation();
        r.setUser_id(user_id);

        r.setTitre_rec(titre_rec.getText());
        r.setType(type.getValue());
        r.setDescription(bad_words(description.getText()));//r.setDescription_Restaurant(rc.bad_words(var3));
                r.setDate_creation(java.sql.Date.valueOf(date.getValue()));
        r.setStatus("en cours de traitement");


            // Affiche un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Réclamation ajoutée");
            alert.setHeaderText(null);
            alert.setContentText("La réclamation a été ajoutée avec succès.");
            alert.showAndWait();

            // Efface les champs de saisie
            titre_rec.clear();
            description.clear();
        try {
            rs.ajouter(r);
            alert = new Alert(Alert.AlertType.INFORMATION);
            String recipientEmail = "destinataire@example.com"; // Adresse e-mail du destinataire
            String emailSubject = "Nouvelle réclamation ajoutée";
            String emailContent = "Une nouvelle réclamation a été ajoutée. Veuillez la traiter dès que possible.";

// Appelez la méthode pour envoyer l'e-mail
            EmailSender.sendEmail(recipientEmail, emailSubject, emailContent);
            alert.setTitle("Ajout de reclamation");
            alert.setHeaderText(null);

            alert.setContentText("Reclamation Ajoutée avec succès\n" +
                    " Status: en cours de traitement");
            //alert.setContentText("Status: en cours de traitement ");
            alert.show();
        } catch (SQLException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }



    public String bad_words(String description) {

        List<String> badListW = Arrays.asList("zebi", "sorem","nayek","asba lik","zabour","9ahba","khahba");
        String badNew = "";
        List<String> newList = new ArrayList<>();
        for (String str : badListW) {
            if (description.contains(str)) {
                badNew += "" + str;
                if (str.length() >= 1) {
                    StringBuilder result = new StringBuilder();
                    result.append(str.charAt(0));
                    for (int i = 0; i < str.length() - 2; ++i) {
                        result.append("*");
                    }
                    result.append(str.charAt(str.length() - 1));
                    str = result.toString();
                    if (!str.isEmpty()) {
                        System.out.println("ATTENTION !! Vous avez écrit un gros mot  : " + result + " .C'est un avertissement ! Priére d'avoir un peu de respect ! Votre description sera envoyée comme suit :");
                        System.out.println(description.replace(badNew, "") + " ");
                    }
                }
            }
        }
        return (description.replace(badNew, "") + " ");
    }



    //public void Modifier(ActionEvent actionEvent) {
    //}
}