package Controller;

import entities.Commande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.CommandeServises;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CommandeTerminé {

    @FXML
    private DatePicker datebare;

    @FXML
    private TextField tfdate;
    @FXML
    private TextField rec10;
    @FXML
    private ListView<String> listCommande;

    @FXML
    private ListView<String> listDate;

    @FXML
    void HomeButtonMeth(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PanierGraphique.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void recupCommandeParDate(ActionEvent event) {
        CommandeServises cs = new CommandeServises();
        LocalDate dateSelectionnee = datebare.getValue();

        if (dateSelectionnee != null) {
            java.sql.Date dateRecherchee = java.sql.Date.valueOf(dateSelectionnee);

            List<Commande> commandes = cs.recupererCommandeOrderByDate(dateRecherchee);

            afficherCommandes(commandes);
        } else {
            System.out.println("Veuillez sélectionner une date.");
        }
    }

    /*void recupCommandeParDate(ActionEvent event) {
        CommandeServises cs = new CommandeServises();
        LocalDate dateSelectionnee = datebare.getValue();

        if (dateSelectionnee != null) {
            java.sql.Date dateRecherchee = java.sql.Date.valueOf(dateSelectionnee);

            List<Commande> commandes = cs.recupererCommandeOrderByDate();

            afficherCommandes(commandes);
        } else {
            System.out.println("Veuillez sélectionner une date.");
        }
    }*/

    /*private void afficherCommandes(List<Commande> commandes) {
        StringBuilder sb = new StringBuilder();
        for (Commande commande : commandes) {
            sb.append("ID Commande: ").append(commande.getIdCommande()).append("\n");
        }
        tfdate.setText(sb.toString());
    }*/

    private void afficherCommandes(List<Commande> commandes) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Commande commande : commandes) {
            items.add("ID Commande: " + commande.getIdCommande());
        }
        listDate.setItems(items);
    }


    @FXML
    /*void recupererCommandeMeth(ActionEvent event) {
        CommandeServises cs=new CommandeServises();
        rec10.setText(cs.recupererCommandeByUserID(1).toString());
    }*/

   /* void recupererCommandeMeth(ActionEvent event) {
        CommandeServises cs = new CommandeServises();
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add(cs.recupererCommandeByUserID().toString());
        listCommande.setItems(items);
    }*/

    void recupererCommandeMeth(ActionEvent event) {
        CommandeServises cs = new CommandeServises();
        List<Commande> commandes = cs.recupererCommandeByUserID(); // Récupérer la liste des commandes

        ObservableList<String> items = FXCollections.observableArrayList();

        // Parcourir chaque commande et l'ajouter à la liste d'éléments sous forme de chaîne de caractères
        for (Commande commande : commandes) {
            String commandeString = "ID commande: " + commande.getIdCommande() +
                    ", ID utilisateur: " + commande.getIdUser() +
                    ", ID panier: " + commande.getIdPanier() +
                    ", Prix: " + commande.getPrix() +
                    ", Numéro de téléphone: " + commande.getNumTel() +
                    ", Date de commande: " + commande.getDateCommande() +
                    ", Code postal: " + commande.getCodePostal() +
                    ", Rue: " + commande.getRue() +
                    ", Ville: " + commande.getVille();
            items.add(commandeString);
        }

        listCommande.setItems(items); // Définir la liste d'éléments dans la ListView
    }



    @FXML
    void supprimerCommandeMeth(ActionEvent event) {
        CommandeServises cs=new CommandeServises();
        cs.supprimeCommande();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("votre commande a été supprimé avec succés");
        alert.show();
    }

}
