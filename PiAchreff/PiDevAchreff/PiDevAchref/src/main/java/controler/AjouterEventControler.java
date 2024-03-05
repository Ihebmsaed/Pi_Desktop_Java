package controler;

import entities.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.EventServices;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AjouterEventControler {

    @FXML
    private TextField titleTf;

    @FXML
    private ChoiceBox<String> typeTf;

    @FXML
    private TextArea descTa;

    @FXML
    private DatePicker startDateDp;

    @FXML
    private DatePicker endDateDp;

    @FXML
    private TextField ticketCountTf;

    @FXML
    private TextField ticketPriceTf;

    @FXML
    private TextField location_id;

    @FXML
    private Button ajouterBtn;

    @FXML
    private ImageView imagePreview;

    private String imageFilePath;

    // Méthode appelée lors du clic sur le bouton "Ajouter"
    @FXML
    private void ajouterEvent() {
        if (validateInput()) {
            EventServices eventServices = new EventServices();
            Event event = new Event();

            String title = titleTf.getText();
            String type = typeTf.getValue();
            String description = descTa.getText();
            LocalDate startDate = startDateDp.getValue();
            LocalDate endDate = endDateDp.getValue();
            int ticketCount = Integer.parseInt(ticketCountTf.getText());
            float ticketPrice = Float.parseFloat(ticketPriceTf.getText());
            String location = location_id.getText();

            // Vérifier si la date de fin est antérieure à la date actuelle
            LocalDate today = LocalDate.now();
            if (endDate.isBefore(today)) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("ERROR");
                alert1.setContentText("Date incorrecte");
                alert1.showAndWait();
                return;
            }

            event.setTitle(title);
            event.setType(type);
            event.setDescription(description);
            event.setStartDate(Date.valueOf(startDate));
            event.setEndDate(Date.valueOf(endDate));
            event.setTicketCount(ticketCount);
            event.setTicketPrice(ticketPrice);
            event.setLocation(location);
            event.setAffiche(imageFilePath);

            eventServices.ajouterEvent(event);

            System.out.println("Evénement ajouté avec succès !");

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Dialog information");
            alert.setContentText("Evénement ajouté avec succès !");
            alert.show();


        }
    }

    private boolean validateInput() {
        if (titleTf.getText().isEmpty() || typeTf.getValue() == null || descTa.getText().isEmpty() ||
                startDateDp.getValue() == null || endDateDp.getValue() == null || ticketCountTf.getText().isEmpty() ||
                ticketPriceTf.getText().isEmpty() || location_id.getText().isEmpty() || imageFilePath == null) {
            // Afficher une boîte de dialogue d'alerte pour informer l'utilisateur des champs manquants
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    // Méthode pour charger une image
    @FXML
    public void onUploadButtonClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                FileInputStream inputStream = new FileInputStream(selectedFile);
                Image image = new Image(inputStream);
                imagePreview.setImage(image);
                imageFilePath = selectedFile.getAbsolutePath(); // Sauvegarder le chemin du fichier image
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode pour retourner à l'interface d'affichage de la liste des événements
    @FXML
    void retournerToListeEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeEvent.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
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

