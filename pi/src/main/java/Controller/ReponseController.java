package Controller;

import entities.Reponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ReponseController implements Initializable {

    @FXML
    private Label RepOwn;
    @FXML
    private Label Rep;
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    //setData depuis AffichReclamOneController, pour passer le user ID lieé au reponse
    void setData(Reponse rep) {
       if (rep.getAdmin_id()==1) {
            RepOwn.setText("Reponse Admin :" );
            Rep.setText(rep.getRep_desc());
        } else {
            //RepOwn.setText("Utilisateur avec Id " + rep.getUser_id() + ":");
           // Rep.setText(rep.getRep_desc());
        }
    }
    
    //setAdminData depuis AfficherReclamOneAdminController TODEBUG
    void setAdminData(Reponse rep) {
        if (rep.getAdmin_id()==1) {
            RepOwn.setText("Reponse Admin :" );
            Rep.setText(rep.getRep_desc());
        } else {
            RepOwn.setText("Utilisateur avec ID: ");
            Rep.setText(rep.getRep_desc());
        }
    }

}
