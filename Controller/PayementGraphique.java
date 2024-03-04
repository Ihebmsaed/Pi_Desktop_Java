package Controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import entities.Sms;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import utils.MyConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PayementGraphique {

    @FXML
    private Button PurchaseBtn;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField cvvField;

    @FXML
    private TextField exprMonthField;

    @FXML
    private TextField exprYearField;

    @FXML
    void PayerCommande(ActionEvent event) {

        String carNum = cardNumberField.getText();
        String cvv = cvvField.getText();
        String mouth = exprMonthField.getText();
        String year = exprYearField.getText();

        if (carNum.matches("")&&cvv.matches("")&&mouth.matches("")&&year.matches("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("case non valide");
            alert.setHeaderText(null);
            alert.setContentText("remplisser les cases");
            alert.show();
            alert.showAndWait();
        } else if(!carNum.matches("\\d{4} \\d{4} \\d{4} \\d{4}")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Format numéro de carte non valide");
            alert.show();
            alert.showAndWait();
        }else if(!cvv.matches("\\d{3}")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Format cvv non valide");
            alert.show();
            alert.showAndWait();
        }else if(!mouth.matches("\\d{2}")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Format month non valide");
            alert.show();
            alert.showAndWait();
        }else if(!year.matches("\\d{2}")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Format year non valide");
            alert.show();
            alert.showAndWait();
        }



        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommandeTerminé.fxml"));

        try {

            processPayment();


            //Sms.SmsSend(getNumTlf(), "Bienvenue ! Votre inscription est réussie.");

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dialog information");
            alert.setHeaderText(null);
            alert.setContentText("votre payement est passer avec succées");
            alert.show();





        } catch (IOException e) {
            System.out.println(e.getMessage());
            ;
        }

    }


    private void processPayment() {
        try {
            float prix=0;

            String requete="select prix from commande where idcommande = (SELECT MAX(idcommande) FROM commande)";
            try {
                PreparedStatement ps= MyConnection.getInstance().getCnx().prepareStatement(requete);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    prix = rs.getInt(1);
                }



            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


// Set your secret key here
            Stripe.apiKey = "sk_test_51OpSALDEN9VkIs736GAAZyNKvqMHMwIfXT8FRwfVo2RJcNNgUWQ2xS4J2HdapuS3QoXgTyUuwX6TtrVWLlcTokMC003VZHybX6";



// Create a PaymentIntent with other payment details
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) prix) // Amount in cents (e.g., $10.00)
                    .setCurrency("usd")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

// If the payment was successful, display a success message
            System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
        } catch (StripeException e) {
// If there was an error processing the payment, display the error message
            System.out.println("Payment failed. Error: " + e.getMessage());
        }
    }

    public String getNumTlf(){
        String tel="";
        String requete="select tel from commande where idcommande = (SELECT MAX(idcommande) FROM commande) ";

        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tel = rs.getString(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tel;
    }

}
