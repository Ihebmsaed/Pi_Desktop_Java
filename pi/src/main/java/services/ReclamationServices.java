package services;

import entities.Reclamation;
import interfaces.RecInterface;
import tools.Myconnection;

import java.sql.ResultSet;
import java.sql.Connection;
//import java.sql.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class ReclamationServices implements RecInterface<Reclamation> {

    Connection cnx;
    PreparedStatement pst, ps, st;

    public ReclamationServices() {
        cnx = Myconnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(Reclamation r) throws SQLException {

        String query = "INSERT INTO pi (user_id,titre_rec,type,description,status,`date_creation`) VALUES(?,?,?,?,?,?)";

        ps = cnx.prepareStatement(query);
        //PreparedStatement ps = Myconnection.getInstance().getCnx().prepareStatement(query);
        //ps = cnx.prepareStatement(query);
       // ps.setInt(1, r.getRec_id());
        ps.setInt(1, r.getUser_id());
        ps.setString(2, r.getTitre_rec());
        ps.setString(3, r.getType());
        ps.setString(4, r.getDescription());
        ps.setString(5, r.getStatus());
        ps.setDate(6, new java.sql.Date(r.getDate_creation().getTime()));

        ps.executeUpdate();
        System.out.println("reclamation ajoutée");


    }

    public void modifier(Reclamation r) throws SQLException {
        String query = "UPDATE pi SET user_id=?, titre_rec=?, type=?, description=?, date_creation=?, status=?  WHERE rec_id=?";
        pst = cnx.prepareStatement(query);
        pst.setInt(1, r.getUser_id());
        pst.setString(2, r.getTitre_rec());
        pst.setString(3, r.getType());
        pst.setString(4, r.getDescription());
        pst.setDate(5, new java.sql.Date(r.getDate_creation().getTime()));
        pst.setString(6, r.getStatus());
        pst.setInt(7, r.getRec_id());
        pst.executeUpdate();
        System.out.println("modifié");
    }

    @Override
    public void ModifierEtat(int rec_id) {

    }

    @Override
    public void supprimer(Reclamation r) {

        String query = "DELETE FROM pi WHERE rec_id=?";
        //PreparedStatement ps = Myconnection.getInstance().getCnx().prepareStatement(query);
        PreparedStatement ps = null;
        try {
            ps = Myconnection.getInstance().getCnx().prepareStatement(query);
            ps.setInt(1, r.getRec_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void supprimerParRecId(int rec_id) throws SQLException {
        String query = "DELETE FROM pi WHERE rec_id=?";
        pst = cnx.prepareStatement(query);
        pst.setInt(1, rec_id);
        pst.executeUpdate();
    }

    @Override
    //Recuperer tous les reclamation , methode ADMIN
    public List<Reclamation> recuperer() {
        List<Reclamation> reclamations = new ArrayList<>();
        String query = "SELECT * FROM pi";
        try {
            PreparedStatement pst = Myconnection.getInstance().getCnx().prepareStatement(query);

            // pst = cnx.prepareStatement(query);
            ResultSet rs = null;

            rs = pst.executeQuery();
            while (rs.next()) {
                int rec_id = rs.getInt(1); // 1 represents the first column
                int user_id = rs.getInt("user_id");
                String titre_rec = rs.getString("titre_rec");
                String type = rs.getString("type");
                String description = rs.getString("description");
                String status = rs.getString("status");
                java.util.Date date_creation = rs.getDate("date-creation");
                // java.util.Date date_fin = rs.getDate("date_fin");
                Reclamation reclamation = new Reclamation(rec_id, user_id, titre_rec, type, description, status, date_creation);
                reclamations.add(reclamation);

            }
        } catch (SQLException e) {
            //
        }

        return reclamations;
    }

    @Override
    public List<Reclamation> recuperParStatus(String selectedStatus) {
        return null;
    }

    @Override
    public List<Reclamation> recupererParUtilisateur(int userId) throws SQLException {
        List<Reclamation> reclamations = new ArrayList<>();
        String query = "SELECT * FROM pi WHERE user_id=?";
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int rec_id = rs.getInt(1); // 1 represents the first column
            String titre_rec = rs.getString("titre_rec");
            String type = rs.getString("type");
            String description = rs.getString("description");
            String status = rs.getString("status");
            Reclamation reclamation = new Reclamation(rec_id, userId, titre_rec, type, description, status);
            reclamations.add(reclamation);
        }
        return reclamations;
    }

    @Override
    public Reclamation recupererParId(int rec_id) throws SQLException {
        String query = "SELECT * FROM pi WHERE rec_id=?";
        pst = cnx.prepareStatement(query);
        pst.setInt(1, rec_id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            int userId = rs.getInt("user_id");
            String titre_rec = rs.getString("titre_rec");
            String type = rs.getString("type");
            String description = rs.getString("description");
            String status = rs.getString("status");
            return new Reclamation(rec_id, userId, titre_rec, type, description, status);
        } else {
            return null;
        }
    }

    public List<Reclamation> rechercherParMotCle(int user_id, String motCle) throws SQLException {
        List<Reclamation> reclamations = new ArrayList<>();
        String query = "SELECT * FROM pi WHERE user_id = ? AND (titre_rec LIKE ? OR description LIKE ? OR type LIKE ?)";
        pst = cnx.prepareStatement(query);
        pst.setInt(1, user_id);
        pst.setString(2, "%" + motCle + "%");
        pst.setString(3, "%" + motCle + "%");
        pst.setString(4, "%" + motCle + "%");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int rec_id = rs.getInt("rec_id");
            String titre_rec = rs.getString("titre_rec");
            String type = rs.getString("type");
            String description = rs.getString("description");
            String status = rs.getString("status");
            Reclamation reclamation = new Reclamation(rec_id, user_id, titre_rec, type, description, status);
            reclamations.add(reclamation);
        }
        return reclamations;
    }

    public ObservableList<Reclamation> getall()  {
        ObservableList<Reclamation> reclamation = FXCollections.observableArrayList();
        try {
            String req = "select * from pi";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Reclamation p = new Reclamation();
                p.setRec_id(rs.getInt(1));
                p.setTitre_rec(rs.getString("titre_rec"));
                p.setType(rs.getString("type"));
                p.setDescription(rs.getString("description"));
                p.setStatus(rs.getString("status"));
                p.setDate_creation(rs.getDate("date_creation"));
                reclamation.add(p);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reclamation;
        ///  @Override
        //  public List<Reclamation> recuperer()  {
        //    return null;
        //}

    }
}