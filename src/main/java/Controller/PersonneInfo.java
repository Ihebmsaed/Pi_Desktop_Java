package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PersonneInfo {

    @FXML
    private TextField nomShowtxfil;

    public void setNomShowtxfil(String nomShowtxfil) {
        this.nomShowtxfil.setText(nomShowtxfil);
    }

    public void setPrenomShowtextfl(String prenomShowtextfl) {
        this.prenomShowtextfl.setText(prenomShowtextfl);
    }

    @FXML
    private TextField prenomShowtextfl;



}
