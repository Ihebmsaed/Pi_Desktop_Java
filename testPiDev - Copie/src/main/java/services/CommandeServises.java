package services;

import Controller.PanierShow;
import entities.Commande;
import interfaces.ICommandeServices;
import javafx.collections.ObservableList;
import utils.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.Map;

public class CommandeServises implements ICommandeServices<Commande> {


    @Override
    public void ajouterCommande(Commande commande) {

        String requete="INSERT INTO commande(idUser,idPanier,prix,tel,dateCommande,codepostal,rue,ville) VALUES(?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps= MyConnection.getInstance().getCnx().prepareStatement(requete);
            //ps.setInt(1,commande.getIdCommande());
            ps.setInt(1,recupererDernierIdUtilisateur());
            ps.setInt(2,recupererDernierIdPanier());
            //ps.setString(3, commande.getNomUser());
            //ps.setString(4, commande.getPrenomUser());
            //PanierShow panierShow=new PanierShow();
            //ps.setFloat(3,panierShow.m);
            ps.setFloat(3,afficherSommePrix());
            //ps.setFloat(3, panierShow.NvTotalPrix());
            ps.setString(4, commande.getNumTel());
            java.sql.Date sqlDate = new java.sql.Date(commande.getDateCommande().getTime());
            ps.setDate(5, sqlDate);
            //ps.setDate(7, commande.getDateCommande());
            ps.setString(6, commande.getCodePostal());
            ps.setString(7, commande.getRue());
            ps.setString(8, commande.getVille());
            ps.executeUpdate();
            System.out.println("Commande ajoutée avec succés");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Commande> recupererCommande() {
        List<Commande> data=new ArrayList<>();
        String requete="Select * from commande";
        try {
            Statement st=MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs=st.executeQuery(requete);
            while (rs.next()){
                Commande c=new Commande();
                c.setIdCommande(rs.getInt(1));
                c.setIdUser(rs.getInt(2));
                c.setIdPanier(rs.getInt(3));
                //c.setNomUser(rs.getString("nom"));
                //c.setPrenomUser(rs.getString("prenom"));
                c.setPrix(rs.getFloat("prix"));
                c.setNumTel(rs.getString("tel"));
                c.setDateCommande(rs.getDate("datecommande"));
                c.setCodePostal(rs.getString("codepostal"));
                c.setRue(rs.getString("rue"));
                c.setVille(rs.getString("ville"));
                data.add(c);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return data;
    }

    @Override
    public List<Commande> recupererCommandeOrderByDate(java.sql.Date dateRecherchee) {
        List<Commande> data = new ArrayList<>();
        String requete = "SELECT * FROM commande WHERE datecommande = ?";
        try {
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setDate(1, dateRecherchee);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Commande c = new Commande();
                c.setIdCommande(rs.getInt("idcommande"));
                c.setIdUser(rs.getInt("iduser"));
                c.setIdPanier(rs.getInt("idpanier"));
                //c.setNomUser(rs.getString("nom"));
                //c.setPrenomUser(rs.getString("prenom"));
                c.setPrix(rs.getFloat("prix"));
                c.setNumTel(rs.getString("tel"));
                c.setDateCommande(rs.getDate("datecommande"));
                c.setCodePostal(rs.getString("codepostal"));
                c.setRue(rs.getString("rue"));
                c.setVille(rs.getString("ville"));
                data.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }



  /*  @Override
    public List<Commande> recupererCommandeOrderByDate() {
        List<Commande> data=new ArrayList<>();
        String requete="select * from commande order by datecommande";
        try {
            Statement st=MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs=st.executeQuery(requete);
            while (rs.next()){
                Commande c =new Commande();
                c.setIdCommande(rs.getInt("idcommande"));
                c.setIdUser(rs.getInt("iduser"));
                c.setIdPanier(rs.getInt("idpanier"));
                //c.setNomUser(rs.getString("nom"));
                //c.setPrenomUser(rs.getString("prenom"));
                c.setPrix(rs.getFloat("prix"));
                c.setNumTel(rs.getString("tel"));
                c.setDateCommande(rs.getDate("datecommande"));
                c.setCodePostal(rs.getString("codepostal"));
                c.setRue(rs.getString("rue"));
                c.setVille(rs.getString("ville"));
                data.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return data;
    }*/



    @Override
    public List<Commande> recupererCommandeByUserID() {
        List<Commande> data=new ArrayList<>();
        String requete="select * from commande where iduser=?";
        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1,recupererDernierIdUtilisateur());
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Commande c =new Commande();
                c.setIdCommande(rs.getInt("idcommande"));
                c.setIdUser(rs.getInt("iduser"));
                c.setIdPanier(rs.getInt("idpanier"));
                //c.setNomUser(rs.getString("nom"));
                //c.setPrenomUser(rs.getString("prenom"));
                c.setPrix(rs.getFloat("prix"));
                c.setNumTel(rs.getString("tel"));
                c.setDateCommande(rs.getDate("datecommande"));
                c.setCodePostal(rs.getString("codepostal"));
                c.setRue(rs.getString("rue"));
                c.setVille(rs.getString("ville"));
                data.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return data;
    }

    @Override
    public void supprimeCommande() {
        String requete="delete from commande where iduser=? ";
        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1,recupererDernierIdUtilisateur());
            ps.executeUpdate();
            System.out.println("commande sélectionné par id user est supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    public void supprimerTousLesCommandes(){
        String requete="delete from commande";
        try {
            Statement st= MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(requete);
            System.out.println("tous les commandes sont supprimés");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public float afficherSommePrix(){
        float totalPrix = 0;
        String requete="SELECT p.PrixUnitaire " +
                "FROM panier pa " +
                "INNER JOIN prod p ON pa.idproduit = p.id_produit " +
                "WHERE pa.iduser = ?";

        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1,recupererDernierIdUtilisateur());
            ResultSet rs= ps.executeQuery();
            while (rs.next()){
                float prixUnitaire = rs.getFloat("PrixUnitaire");
                totalPrix += prixUnitaire; // Accumulate the price
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return totalPrix; // Return the total price
    }




    // Méthode pour récupérer le dernier ID utilisateur de la table user
    public int recupererDernierIdUtilisateur() {
        int dernierIdUtilisateur = 0;
        try {
            String requete = "SELECT MAX(id) FROM user";
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dernierIdUtilisateur = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dernierIdUtilisateur;
    }

    public int recupererDernierIdProduit() {
        int DernierIdProduit = 0;
        try {
            String requete = "SELECT MAX(id_produit) FROM prod";
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DernierIdProduit = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return DernierIdProduit;
    }
    public int recupererDernierIdPanier() {
        int dernierIdPanier = 0;
        try {
            String requete = "SELECT MAX(idPanier) FROM panier";
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dernierIdPanier = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dernierIdPanier;
    }


}
