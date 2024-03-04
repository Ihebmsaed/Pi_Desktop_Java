package services;

import entities.Reduction;
import interfaces.IReduction;
import javafx.beans.binding.When;
import utils.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReductionServices implements IReduction<Reduction> {
    @Override
    public void ajouterReduction(Reduction reduction) {
        String requete="INSERT INTO reduction (codeproduit,remise,prixunitaire,nouveauprix) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps= MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setString(1,reduction.getCodeProduit());
            ps.setFloat(2,reduction.getRemise());
            ps.setFloat(3,getPrixUnitaire(reduction.getCodeProduit()));
            ps.setFloat(4,calculNouveauPrix(reduction.getCodeProduit(), reduction.getRemise()));
            ps.executeUpdate();
            System.out.println("Reduction ajoutée");


        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }

    @Override
    public void modifierReduction(Reduction reduction) {
        String requete="UPDATE reduction set remise=? where codeproduit=?";

        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setFloat(1,reduction.getRemise());
            ps.setString(2,reduction.getCodeProduit());
            ps.executeUpdate();
            System.out.println("reduction modifieé");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimerReduction(Reduction reduction) {
        String requete="DELETE FROM reduction where codeproduit=?";
        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setString(1,reduction.getCodeProduit());
            ps.executeUpdate();
            System.out.println("reduction supprimé");

        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }

    @Override
    public List<Reduction> getAll() {
        List<Reduction> reductions=new ArrayList<>();
        String requete="select * from reduction";
        try {
            Statement ps=MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs= ps.executeQuery(requete);
            while (rs.next()){
                Reduction reduction=new Reduction();
                reduction.setCodeProduit(rs.getString("codeproduit"));
                //reduction.setRemise(rs.getFloat("remise"));
                reductions.add(reduction);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }

        return reductions;
    }

    public String checkCodeExists(String codeProduit) {
        String request = "SELECT * FROM reduction WHERE codeproduit = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(request);
            pst.setString(1, codeProduit);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return "votre produit est disponible\n dans la liste des produits avec remise";
            } else {
                return "votre produit n'est pas disponible \n dans la liste des produits avec remise";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "Une erreur s'est produite lors de la recherche du produit";
        }
    }


    public void mettreAncinnePrixUnitaire(String codeProduit){
        Reduction reduction=new Reduction();
        String requete = "UPDATE prod p " +
                "JOIN reduction r ON p.codeproduit = r.codeproduit " +
                "SET p.prixunitaire = r.prixunitaire where r.codeproduit = ?";

        try (PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete)) {
            ps.setString(1,codeProduit);
            ps.executeUpdate();
            System.out.println("Prix unitaires mis à jour dans la table prod par l'ancienne valeur.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public float getPrixUnitaire(String codeProduit) {
        float prixUnitaire = 0;
        String requete = "SELECT prixunitaire FROM prod WHERE codeproduit=?";

        try (PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete)) {
            ps.setString(1, codeProduit);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    prixUnitaire = rs.getFloat(1);
                    System.out.println("Prix unitaire : " + prixUnitaire);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prixUnitaire;
    }

    public float calculNouveauPrix(String codeProduit, float remise) {
        float prixUnitaire = getPrixUnitaire(codeProduit);
        float nouveauPrix = prixUnitaire * (1 - remise / 100);
        System.out.println("Nouveau prix : " + nouveauPrix);
        return nouveauPrix;
    }

    public void mettreUnNouveauPrixDansProd(){
        String requete = "UPDATE prod p " +
                "JOIN reduction r ON p.codeproduit = r.codeproduit " +
                "SET p.prixunitaire = r.nouveauprix";

        try (PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete)) {
            ps.executeUpdate();
            System.out.println("Prix unitaires mis à jour dans la table prod.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean verifCodeProduit(String codeProduit){
        String requete="select * from prod where codeproduit=?";
        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setString(1,codeProduit);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                return true;
            }else
                return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
            return false;
        }
    }

    public float recuprerPrixUniatire(String codeProduit){
        float prixUni=0;
        String requete="select prixunitaire from reduction where codeproduit=? and idreduction = (SELECT MIN(idreduction) FROM reduction WHERE codeproduit = ?)";
        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setString(1,codeProduit);
            ps.setString(2,codeProduit);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    prixUni = rs.getFloat(1);
                    System.out.println("Prix unitaire : " + prixUni);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prixUni;
    }

    public float recuprerPrixNovo(String codeProduit){
        float prixNovo=0;
        String requete="select nouveauprix from reduction where codeproduit=? and idreduction = (SELECT max(idreduction) FROM reduction WHERE codeproduit = ?) ";
        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setString(1,codeProduit);
            ps.setString(2,codeProduit);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    prixNovo = rs.getFloat(1);
                    System.out.println("Prix unitaire : " + prixNovo);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prixNovo;
    }




}