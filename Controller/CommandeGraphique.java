package Controller;

import entities.Commande;
import entities.Sms;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CommandeServises;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import java.sql.SQLException;
import java.util.Date;

public class CommandeGraphique {


    @FXML
    private TextField codePostaltf;

    //@FXML
    //private TextField nomtf;

    @FXML
    private TextField numTeltf;

    //@FXML
    //private TextField prenomtf;

    @FXML
    private TextField ruetf;

    @FXML
    private TextField villetf;




    @FXML
    void EnregistrerCommandeMeth(ActionEvent event) throws SQLException {
       // String nom=nomtf.getText();
       // String prenom=prenomtf.getText();
        String tel=numTeltf.getText();
        String rue=ruetf.getText();
        String ville=villetf.getText();
        String codepostal=codePostaltf.getText();

       /* if(!nom.matches("[a-zA-Z]+")){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Format nom non valide");
            //alert.showAndWait();
            alert.show();
        }

        else if (!prenom.matches("[a-zA-Z]+")){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Format prenom est nom valide");
            alert.show();
        }*/

        if (tel.matches("")&&rue.matches("")&&ville.matches("")&&codepostal.matches("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("case non valide");
            alert.setHeaderText(null);
            alert.setContentText("remplisser les cases");
            alert.show();
            alert.showAndWait();
        }

         else if (!tel.matches("\\d{8}")){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Format tel non valide");
            alert.show();
            alert.showAndWait();
        }

        else if (!rue.matches("[a-zA-Z0-9]+")){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Format rue non valide");
            alert.show();
            alert.showAndWait();
        }

        else if (!ville.matches("[a-zA-Z]+")){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Format ville non valide");
            alert.show();
            alert.showAndWait();
        }

        else if (!codepostal.matches("[0-9]+")){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Format code postale non valide");
            alert.show();
            alert.showAndWait();
        }

        else if (numTeltf.getText()==""){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("case non valide");
            alert.setHeaderText(null);
            alert.setContentText("case num de tlf non valide");
            alert.show();
            alert.showAndWait();
         }
         else if (ruetf.getText()==""){
             Alert alert=new Alert(Alert.AlertType.ERROR);
             alert.setTitle("case non valide");
             alert.setHeaderText(null);
             alert.setContentText("case rue non valide");
             alert.show();
            alert.showAndWait();
         }
         else if (villetf.getText()==""){
             Alert alert=new Alert(Alert.AlertType.ERROR);
             alert.setTitle("case non valide");
             alert.setHeaderText(null);
             alert.setContentText("case ville non valide");
             alert.show();
            alert.showAndWait();
         }
         else if (codePostaltf.getText()==""){
             Alert alert=new Alert(Alert.AlertType.ERROR);
             alert.setTitle("case non valide");
             alert.setHeaderText(null);
             alert.setContentText("case code postale non valide");
             alert.show();
            alert.showAndWait();
         }

        else {
            Commande p=new Commande();
            CommandeServises cs=new CommandeServises();
            //p.setNomUser(nomtf.getText());
           // p.setPrenomUser(prenomtf.getText());
            p.setNumTel(numTeltf.getText());
            p.setRue(ruetf.getText());
            p.setVille(villetf.getText());
            p.setCodePostal(codePostaltf.getText());
            //p.setIdUser(1);
            //p.setPrix(12.5f);
            //p.setIdPanier(8);
            //p.setDateCommande(p.getDateCommande().getTime());
            Date dateActuelle=new Date();
            p.setDateCommande(dateActuelle);
            PanierShow panierShow=new PanierShow();
            cs.ajouterCommande(p);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dialog information");
            alert.setHeaderText(null);
            alert.setContentText("votre commande est enregistré");
            alert.show();

        }

    }


    @FXML
    void retournerVersPanier(ActionEvent event) {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/PanierShow.fxml"));
        try {
            Parent root=loader.load();
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void CommanderMeth(ActionEvent event) {

            String tel=numTeltf.getText();
            String rue=ruetf.getText();
            String ville=villetf.getText();
            String codepostal=codePostaltf.getText();


            if (tel.matches("")&&rue.matches("")&&ville.matches("")&&codepostal.matches("")){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("case non valide");
                alert.setHeaderText(null);
                alert.setContentText("remplisser les cases");
                alert.show();
                alert.showAndWait();
            }

            else if (!tel.matches("\\d{8}")){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Format tel non valide");
                alert.show();
                alert.showAndWait();
            }

            else if (!rue.matches("[a-zA-Z0-9]+")){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Format rue non valide");
                alert.show();
                alert.showAndWait();
            }

            else if (!ville.matches("[a-zA-Z]+")){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Format ville non valide");
                alert.show();
                alert.showAndWait();
            }

            else if (!codepostal.matches("[0-9]+")){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Format code postale non valide");
                alert.show();
                alert.showAndWait();
            }

            else if (numTeltf.getText()==""){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("case non valide");
                alert.setHeaderText(null);
                alert.setContentText("case num de tlf non valide");
                alert.show();
                alert.showAndWait();
            }
            else if (ruetf.getText()==""){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("case non valide");
                alert.setHeaderText(null);
                alert.setContentText("case rue non valide");
                alert.show();
                alert.showAndWait();
            }
            else if (villetf.getText()==""){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("case non valide");
                alert.setHeaderText(null);
                alert.setContentText("case ville non valide");
                alert.show();
                alert.showAndWait();
            }
            else if (codePostaltf.getText()==""){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("case non valide");
                alert.setHeaderText(null);
                alert.setContentText("case code postale non valide");
                alert.show();
                alert.showAndWait();
            }


            Commande p=new Commande();
            PanierShow panierShow=new PanierShow();
            CommandeServises cs=new CommandeServises();
            //p.setNomUser(nomtf.getText());
            //p.setPrenomUser(prenomtf.getText());
            p.setNumTel(numTeltf.getText());
            p.setRue(ruetf.getText());
            p.setVille(villetf.getText());
            p.setCodePostal(codePostaltf.getText());
            //p.setIdUser(1);
            //p.setPrix(12.5f);
            //p.setIdPanier(5);
            Date dateActuelle=new Date();
            //p.setDateCommande(p.getDateCommande());
            p.setDateCommande(dateActuelle);
            cs.ajouterCommande(p);

        //mettre une metier ou un api pour tu rendre un pdf
            //qui contient un recu pdf

        FXMLLoader loader =new FXMLLoader(getClass().getResource("/Payement.fxml"));
        try {
            Parent root=loader.load();
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dialog information");
            alert.setHeaderText(null);
            alert.setContentText("votre commande est passer avec succées");
            alert.show();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }






}
