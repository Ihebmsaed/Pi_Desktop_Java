package Controller;

import entities.Commande;
import javafx.application.Platform;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CommandeServises;
import services.PanierServices;
import utils.MyConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class PanierShow implements Initializable {

    PanierServices ps=new PanierServices();

    CommandeServises cs=new CommandeServises();


    @FXML
    private ListView<String> listview;

    @FXML
    private ListView<String> listprix;

    @FXML
    private TextField showProduit;
    @FXML
    private TextField totalPrix;


    @FXML
    private TableView<String> tableview;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherTousLesProduits(null); // Appeler la méthode afficherTousLesProduits au chargement de l'interface
        // Supprimer le bouton associé à l'action afficherTousLesProduits
        Node consulterPanierButton;
        //consulterPanierButton.setVisible(false);
        //consulterPanierButton.setManaged(false);
    }

    /*@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Produit> produits=new ArrayList<>();
        produits.add(ps.recupererProduitParId(1));
        FPProduit.setHgap(10.0);
        FPProduit.setPrefWrapLength(Double.MAX_VALUE);

        for (Produit produit: produits){
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/SousProduit"));
            try {
                AnchorPane pane=loader.load();
                SousProduitController spc=loader.getController();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }*/


    @FXML
    void CommanderCettePanierMeth(ActionEvent event) {
        PanierServices ps=new PanierServices();
        ps.decrementerQteStock();
        ObservableList<String> observablePrix = listprix.getItems();
        float prixTotal = calculerTotalPrix(observablePrix);
        if (ps.afficherSommePrix()==0||prixTotal==0){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dialog information");
            alert.setHeaderText(null);
            alert.setContentText("votre Panier est vide");
            alert.show();
            alert.showAndWait();
        }



        FXMLLoader loader=new FXMLLoader(getClass().getResource("/CommandeGraphique.fxml"));
        try {
            Parent root =loader.load();
            CommandeGraphique cg=loader.getController();
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    @FXML
    void VersPageDacceuil(ActionEvent event) {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/PanierGraphique.fxml"));
        try {
            Parent root =loader.load();
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void ViderCettePanierMeth(ActionEvent event) {
        PanierServices panierServices=new PanierServices();
        panierServices.supprimerTousLesPanier();

        // Afficher à nouveau tous les produits après avoir vidé le panier
        afficherTousLesProduits(null);

        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Dialog information");
        alert.setHeaderText(null);
        alert.setContentText("tous les produits de votre panier sont supprimés");
        alert.show();

    }

    @FXML
    void afficherTousLesProduits(ActionEvent event) {
        PanierServices ps = new PanierServices();

        List<String> produits = ps.afficherProduit();
        ObservableList<String> observableProduits = FXCollections.observableArrayList(produits);
        listview.setItems(observableProduits);

        List<String> prix = ps.afficherPrix();

        ObservableList<String> observablePrix = FXCollections.observableArrayList(prix);
        listprix.setItems(observablePrix);

        totalPrix.setText(String.valueOf(ps.afficherSommePrix()));

        }




    @FXML
    void supprimerProduit(ActionEvent event) {

        ObservableList<String> observableProduits = listview.getItems();
        ObservableList<String> observablePrix = listprix.getItems();


        // Récupérer l'index de l'élément sélectionné dans la ListView
        int selectedIndex = listview.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) { // Vérifier si un élément est sélectionné
            // Supprimer l'élément sélectionné de la liste des produits
            observableProduits.remove(selectedIndex);
            // Supprimer également le prix correspondant de la liste des prix
            observablePrix.remove(selectedIndex);

            // Mettre à jour la ListView et le prix total
            listview.setItems(observableProduits);
            listprix.setItems(observablePrix);
            totalPrix.setText(String.valueOf(calculerTotalPrix(observablePrix)));
            // Mettre à jour le prix total dans la base de données
            //mettreAJourPrixTotalDansLaBaseDeDonnees(calculerTotalPrix(observablePrix));
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> {
                Platform.runLater(() -> {
                    mettreAJourPrixTotalDansLaBaseDeDonnees(calculerTotalPrix(observablePrix));
                    executorService.shutdown();
                });
            }, 30, TimeUnit.SECONDS);


        } else {
            // Afficher un message d'erreur si aucun élément n'est sélectionné
            System.out.println("Veuillez sélectionner un produit à supprimer.");
        }
    }

    // Méthode pour mettre à jour le prix total dans la base de données
    public  void mettreAJourPrixTotalDansLaBaseDeDonnees(float totalPrix){
        String requete = "UPDATE commande SET prix = ? WHERE idcommande = ?"; // Modifier avec votre requête SQL appropriée

        try {
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            PanierServices panierServices=new PanierServices();
            ps.setFloat(1, totalPrix);
            ps.setInt(2, panierServices.recupererDernierIdCommande()); // Remplacez avec votre identifiant approprié
            ps.executeUpdate();
            System.out.println("Prix total mis à jour avec succès dans la base de données.");

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du prix total dans la base de données : " + e.getMessage());
        }
    }

   public float calculerTotalPrix(ObservableList<String> observablePrix) {

        float totalPrix = 0;
        for (String prix : observablePrix) {
            totalPrix += Float.parseFloat(prix);
        }
        return totalPrix;
    }


    }





