package Controller;

import entities.Reduction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ReductionServices;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;



public class ReductionGraphique {

    @FXML
    private Label booleanproduit;

    @FXML
    private TextField codeproduit;

    @FXML
    private ListView<String> listRemise;

    @FXML
    private Label prixnovo;

    @FXML
    private Label prixuni;

    @FXML
    private TextField remise;



    @FXML
    private void initialize() {
        // Ajouter un écouteur de changement de texte au TextField codeproduit
        codeproduit.textProperty().addListener((observable, oldValue, newValue) -> {
            // Vérifier si le nouveau texte n'est pas vide
            if (!newValue.isEmpty()) {
                // Appeler la méthode affichageLesPrix
                affichageLesPrix();
            } else {
                // Effacer les labels si le champ est vide
                prixuni.setText("");
                prixnovo.setText("");
            }
        });
    }




    @FXML
    void SupprimerUneRemise(ActionEvent event) {
        Reduction reduction=new Reduction();
        ReductionServices rs=new ReductionServices();
        reduction.setCodeProduit(codeproduit.getText());
        rs.mettreAncinnePrixUnitaire(codeproduit.getText());
        rs.supprimerReduction(reduction);
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Dialog information");
        alert.setContentText("le produit est supprimer avec succées de la liste des produits avec remise");
        alert.show();
        initialize();
    }

    @FXML
    void ajouterUneRemise(ActionEvent event) {
        Reduction reduction=new Reduction();
        ReductionServices rs=new ReductionServices();
        reduction.setRemise(Float.parseFloat(remise.getText()));
        reduction.setCodeProduit(codeproduit.getText());
        String codeProduit = codeproduit.getText();
        if (rs.verifCodeProduit(codeProduit)==true){
            rs.ajouterReduction(reduction);
            rs.mettreUnNouveauPrixDansProd();
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Dialog information");
            alert.setContentText("le code produit est insérer avec succés dans le base de donneés des produits avec remise");
            alert.show();
            initialize();

        }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Dialog information");
            alert.setContentText("le code produit que vous insérer n'est pas disponible dans le base de donneés des produits");
            alert.show();
            initialize();
        }


    }


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


    @FXML
    void rechercherProduit(ActionEvent event) {
        ReductionServices rs = new ReductionServices();
        // Récupérer le code produit à partir de votre interface utilisateur
        String codeProduit = codeproduit.getText(); // Supposez que codeProduitTextField est votre champ de saisie pour le code produit

        // Appeler la méthode checkCodeExists pour vérifier si le code produit existe
        String message = rs.checkCodeExists(codeProduit);

        // Afficher le message dans le Label
        booleanproduit.setText(message);
        initialize();
    }

    @FXML
    void toutLesProduitsAvecRemise(ActionEvent event) {
        ReductionServices rs=new ReductionServices();
        // Récupérer toutes les réductions avec un code produit non nul
        List<Reduction> reductionsAvecRemise = rs.getAll().stream()
                .filter(reduction -> reduction.getCodeProduit() != null)
                .collect(Collectors.toList());

        // Extraire les codes produit des réductions
        ObservableList<String> codesProduit = FXCollections.observableArrayList();
        for (Reduction reduction : reductionsAvecRemise) {
            codesProduit.add(reduction.getCodeProduit());
        }

        // Afficher les codes produit dans la ListView
        listRemise.setItems(codesProduit);
        initialize();
    }

    public void affichageLesPrix(){
        Reduction reduction=new Reduction();
        ReductionServices rs=new ReductionServices();
        reduction.setCodeProduit(codeproduit.getText());
        String codeProduit = codeproduit.getText();
        if (rs.verifCodeProduit(codeProduit)==true){
            float prixUni = rs.recuprerPrixUniatire(codeProduit);
            float prixNovo = rs.recuprerPrixNovo(codeProduit);

            prixuni.setText(String.valueOf(prixUni));
            prixnovo.setText(String.valueOf(prixNovo));
        }
    }



}
