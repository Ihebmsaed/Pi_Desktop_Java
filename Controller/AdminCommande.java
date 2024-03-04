package Controller;

import entities.Commande;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.stage.Stage;
import services.CommandeServises;

public class AdminCommande implements Initializable {

    @FXML
    private ChoiceBox<String> trichoix;
    @FXML
    private FlowPane flowp;
    @FXML
    private AnchorPane affichfinale;
    @FXML
    private ImageView backback;

    private Commande commandeSelectionnee;


    CommandeServises cs = new CommandeServises();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        afficherCommandes(cs.recupererCommande()); // Affichage initial des commandes

        // Ajout des options dans le ChoiceBox
        trichoix.getItems().addAll("Trié par", "Date", "UserID");
        trichoix.setValue("Trié par");

        // Action à effectuer lorsqu'une option est sélectionnée dans le ChoiceBox
        trichoix.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Date".equals(newValue)) {
                afficherCommandes(cs.recupererCommandeOrderByDate1());
            } else if ("UserID".equals(newValue)) {
                afficherCommandes(cs.recupererCommandeByUserID1());
            } else {
                afficherCommandes(cs.recupererCommande());
            }
        });
    }

    @FXML
    void ReductionMeth(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reduction.fxml"));

        try {
            Parent root = loader.load();
            ReductionGraphique rg=loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            System.out.println(e.getMessage());
            ;
        }

    }


    // Méthode pour afficher les commandes dans le FlowPane
    private void afficherCommandes(List<Commande> commandes) {
        flowp.getChildren().clear(); // Nettoyer le FlowPane avant d'ajouter de nouvelles commandes
        for (Commande commande : commandes) {
            // Créer un élément d'interface utilisateur pour afficher la commande
            Pane commandePane = createCommandePane(commande);
            // Ajouter la classe "line" à chaque élément d'interface utilisateur
            commandePane.getStyleClass().add("line");
            // Ajouter l'élément au FlowPane
            flowp.getChildren().add(commandePane);
        }
    }

    // Méthode pour créer un élément d'interface utilisateur pour afficher une commande
  /*  private Pane createCommandePane(Commande commande) {
        // Ici vous pouvez créer un élément d'interface utilisateur personnalisé pour afficher une commande,
        // tel qu'un Pane avec des étiquettes pour afficher les détails de la commande.
        // Par exemple :
        // Pane commandePane = new Pane();
        // Label label = new Label("ID : " + commande.getId() + ", Date : " + commande.getDate() + ", UserID : " + commande.getUserID());
        // commandePane.getChildren().add(label);
        // return commandePane;
        Pane commandePane = new Pane();
        Label label = new Label("ID : " + commande.getIdCommande() + ", Date : " + commande.getDateCommande() + ", UserID : " + commande.getIdUser() + ", code postale : " +
                commande.getCodePostal() + ", Ville : " + commande.getVille() + ", rue : " + commande.getRue() + ", tlf" + commande.getNumTel());

        commandePane.getChildren().add(label);
        return commandePane;

        //return null; // À remplacer avec la logique réelle pour créer l'élément d'interface utilisateur de la commande
    }*/

    @FXML
    void retournerVersHome(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PanierGraphique.fxml"));

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            System.out.println(e.getMessage());
            ;
        }
    }

   /* @FXML
    void imprimerFactureCommande(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SousAdminCommande.fxml"));

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            ;
        }
    }*/

    @FXML
    void imprimerFactureCommande(ActionEvent event) {
        // Vérifiez d'abord si une commande est sélectionnée
        if (commandeSelectionnee != null) {
            // Charger la scène SousAdminCommande
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SousAdminCommande.fxml"));

            try {
                Parent root = loader.load();
                SousAdminCommande controller = loader.getController();

                // Appeler la méthode afficherDonneesCommande avec la commande sélectionnée
                controller.afficherDonneesCommande(commandeSelectionnee);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Aucune commande sélectionnée.");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Dialog information");
            alert.setContentText("Aucune commande sélectionnée.");
            alert.show();
        }
    }


    private Pane createCommandePane(Commande commande) {
        Pane commandePane = new Pane();
        Label label = new Label("ID : " + commande.getIdCommande() + ", Date : " + commande.getDateCommande() + ", UserID : " + commande.getIdUser() + ", code postale : " +
                commande.getCodePostal() + ", Ville : " + commande.getVille() + ", rue : " + commande.getRue() + ", tlf" + commande.getNumTel());

        commandePane.getChildren().add(label);

        // Ajouter un gestionnaire d'événements pour détecter le clic sur cette commande
        commandePane.setOnMouseClicked(event -> {
            // Récupérer la commande sélectionnée
            commandeSelectionnee = commande;

            // Mettre à jour visuellement la commande sélectionnée (par exemple, en changeant le style)
            commandePane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        });

        return commandePane;
    }




}
