package controler;

import com.google.zxing.WriterException;
import entities.Event;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import utils.MyConnection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormulaireControler {

    @FXML
    private TextField nbtxt;

    @FXML
    private TextField nomtxt;

    @FXML
    private TextField prenomtxt;

    private Event event;
    public void setEvent(Event event) {
        this.event = event;
    }

    public void confirmerReservation(ActionEvent actionEvent) throws WriterException, IOException {

        metier(event);

        int width = 200;
        int height = 200;

        String nom = nomtxt.getText();
        String prenom = prenomtxt.getText();
        String nbticket = nbtxt.getText();

        String requete = "INSERT INTO RESERVATION (idev,iduser,nombreDePlace,nom,prenom) VALUES(1,1,?,?,?)";
        try {
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setString(1, nbticket);  // Set the first parameter
            ps.setString(2, nom);       // Set the second parameter
            ps.setString(3, prenom);    // Set the third parameter
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String qrCodeText = "nom " + nom + ", prenom " + prenom + ", nbticket " + nbticket;

        BufferedImage qrCodeBufferedImage = QrCode.generateQRCodeImage(qrCodeText, width, height);
        WritableImage qrCodeImage = SwingFXUtils.toFXImage(qrCodeBufferedImage, null);

        System.out.println("hello qr: " + qrCodeImage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/QrAffiche.fxml"));
        Parent root = loader.load();
        QrAffiche controller = loader.getController();

        controller.setData(qrCodeImage);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }


    public void metier(Event event){
        this.event = event;
        event.getTicketCount();
        event.getTitle();
        String nbticket = nbtxt.getText();
        int nombreTickets = Integer.parseInt(nbticket);
        int mettreAjourTicketCount=event.getTicketCount()-nombreTickets;


        String requete="update event set ticketCount=? where title=?";

        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1,mettreAjourTicketCount);
            ps.setString(2,event.getTitle());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
