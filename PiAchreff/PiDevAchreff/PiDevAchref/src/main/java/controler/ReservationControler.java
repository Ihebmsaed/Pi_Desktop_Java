package controler;

import entities.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import services.EventServices;
import utils.MyConnection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ReservationControler  {

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView imagePreview;


    @FXML
    private Label priceLabel;

    @FXML
    private Label startDateLabel;

    @FXML
    private Label endDateLabel;

    @FXML
    private Label ticketCountLabel; // Nouveau champ pour afficher le nombre de tickets

    private Event event;


    // Méthode pour initialiser les détails de l'événement dans la fenêtre de réservation
    public void initData(Event event) {
        this.event = event;
        titleLabel.setText(event.getTitle());
        priceLabel.setText(String.valueOf(event.getTicketPrice()));
        startDateLabel.setText(event.getStartDate().toString());
        endDateLabel.setText(event.getEndDate().toString());
        ticketCountLabel.setText(String.valueOf(event.getTicketCount())); // Afficher le nombre de tickets
        File file = new File(event.getAffiche());
        Image image = new Image(file.toURI().toString());
        imagePreview.setImage(image);

    }

    // Méthode pour gérer l'action de réservation
    @FXML
    void reserver() {
        // Logique de réservation
       /* if (event != null) {
            // Metre à jour le nombre de tickets disponibles dans la base de données
            EventServices.updateTicketCount(event, 1); // Supposons que vous réservez un seul ticket
            // Afficher un message de succès ou effectuer d'autres actions nécessaires après la réservation
        } else {
            // Afficher un message d'erreur si les détails de l'événement ne sont pas disponibles
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de réservation");
            alert.setHeaderText(null);
            alert.setContentText("Impossible de réserver l'événement. Les détails de l'événement ne sont pas disponibles.");
            alert.showAndWait();*/

            try {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/Formilaire.fxml"));
                Parent root = loader.load();
                FormulaireControler formulaireControler = loader.getController();
                formulaireControler.setEvent(event);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



    public String afficherImage() {
        String affiche = "";
        Event event = new Event();
        AfficheListeEventControler afficheListeEventControler = new AfficheListeEventControler();
        String requete = "select affiche from event where title=? ";

        try {
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setString(1, afficheListeEventControler.EvenementSelected().getTitle());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                affiche = rs.getString(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return affiche;
    }

    public void afficherImageDansImageView() {

        // Récupérer le chemin retourné par la méthode afficherImage()
        String cheminImage = afficherImage();

        // Créer un objet File à partir du chemin de l'image
        File file = new File(cheminImage);

        // Vérifier si le fichier existe
        if (file.exists()) {
            // Créer un objet Image à partir du fichier
            Image image = new Image(file.toURI().toString());

            // Assigner l'image à l'ImageView
            imagePreview.setImage(image);
        } else {
            // Gérer le cas où le fichier n'existe pas
            System.err.println("L'image spécifiée n'existe pas : " + cheminImage);
        }
    }


    public void Home(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(""));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
