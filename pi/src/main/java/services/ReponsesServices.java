package services;
import entities.Reponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.RepInterface;

import java.util.ArrayList;
import java.util.List;
import tools.Myconnection;
public class ReponsesServices implements RepInterface<Reponse> {
    Connection cnx;
    PreparedStatement pst;

    public ReponsesServices() {
        cnx = Myconnection.getInstance().getCnx();
    }

    @Override
    //ajouter nouvelle reponse
    public void ajouter(Reponse rep) throws SQLException {
        String query = "INSERT INTO reponse (rec_id,  admin_id, rep_desc, `date_rep`) VALUES (?, ?, ?, ?)";
        pst = cnx.prepareStatement(query);
        pst.setInt(1, rep.getRec_id());
        pst.setInt(2, rep.getAdmin_id());
        pst.setString(3, rep.getRep_desc());
        pst.setDate(4, new java.sql.Date(rep.getDate_rep().getTime()));
        pst.executeUpdate();
        System.out.println("reponse ajoutée");
    }


    @Override
    //recuperer reponse par ID de reclamation
    public List<Reponse> recupererParRecId(int rec_id) throws SQLException {
        List<Reponse> reponsesList = new ArrayList<>();
        String query = "SELECT * FROM reponse WHERE rec_id = ?";
        pst = cnx.prepareStatement(query);
        pst.setInt(1, rec_id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Reponse rep = new Reponse();
            rep.setRep_id(rs.getInt("rep_id"));
            rep.setRec_id(rs.getInt("rec_id"));

            rep.setAdmin_id(rs.getInt("admin_id"));
            rep.setRep_desc(rs.getString("rep_desc"));
            rep.setDate_rep(rs.getDate("date_rep"));
            reponsesList.add(rep);
        }
        return reponsesList;
    }

    @Override
    //suprimer reponse lieé a une reclamation donne (rec_id)
    public void supprimerParRecId(int rec_id) throws SQLException {
        String query = "DELETE FROM reponse WHERE rec_id=?";
        pst = cnx.prepareStatement(query);
        pst.setInt(1, rec_id);
        pst.executeUpdate();
        System.out.println("Reponse Supprimée !!");
    }

    public void supprimer(Reponse reponse) {
    }




}
