
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class NavbarController implements Initializable {

    @FXML
    private Text welcomeText;
    @FXML
    private ImageView profileIcon;
    @FXML
    private ImageView panierIcon;
    //private ImageView out;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String name = LoginController.UserConnected.getNom();
        welcomeText.setText("Welcome, "+name+"!");
        if(LoginController.UserConnected.getRole().equals("Admin")){
            profileIcon.setVisible(false);
            //panierIcon.setVisible(false);
            //out.setVisible(true);
        }
    }

    @FXML
    private void openProfile(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Edit.fxml"));
            Parent root = loader.load();
            EditController controller = loader.getController();
            controller.senduser(LoginController.UserConnected);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void openPanier(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(""));
            Parent root = loader.load();
            welcomeText.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void Logout(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            //EditController controller = loader.getController();
            //controller.senduser(LoginController.UserConnected);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    }



