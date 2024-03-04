package services;

import entities.Panier;
import interfaces.IPanierServices;
import utils.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PanierServices implements IPanierServices<Panier> {


    @Override
    public void ajouterPanier(Panier panier) {
        String requete="INSERT INTO panier(iduser,idProduit,Quantite) VALUES (?,?,?)";

        try {
            PreparedStatement ps= MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1,recupererDernierIdUtilisateur());
            ps.setInt(2,recupererDernierIdProduit());
            ps.setInt(3,panier.getQuantite());
            ps.executeUpdate();
            System.out.println("Panier ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }

    @Override
    public List<Panier> recupererlastPanier(int idUser) {
        String requete="select * from panier where iduser= ? order by idpanier desc limit 1";
        List<Panier> data=new ArrayList<>();
        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1,idUser);
            ResultSet rs= ps.executeQuery();
            while (rs.next()){
                Panier p=new Panier();
                p.setIdPanier(rs.getInt("idpanier"));
                p.setIdUser(rs.getInt("iduser"));
                p.setIdProduit(rs.getInt("idproduit"));
                p.setQuantite(rs.getInt("quantite"));
                data.add(p);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return data;

    }

    @Override
    public void supprimerProduitParId(int idProduit) {
        String requete="delete from panier where idproduit= ? ";

        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1,idProduit);
            ps.executeUpdate();
            System.out.println("la suppression par idProduit est traité avec succées");
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }

    }

   /* @Override
    public List<String> afficherPanier(int idUser) {
        List<Produit> data=new ArrayList<>();
        String requete="select from panier where iduser=?";

        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1,idUser);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                //Produit p=new Produit();
                /*p.setId(rs.getInt(1));
                p.setCodeProduit(rs.getString(2));
                p.setDesgination(rs.getString(3));
                p.setQteStock(rs.getInt(4));
                p.setQteMin(rs.getInt(5));
                p.setPrixUnite(rs.getInt(6));
                p.setIdUnite(rs.getString(7));
                p.setImage(rs.getString(8));
                p.setIdCat(rs.getString(9));
                data.add(p);*/

           // }

/*        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        //return data;
    }

    @Override
    public String recupererProduitParId(int idProduit) {
        List<Produit> data=new ArrayList<>();
        Produit p=new Produit();

        String requete="select from panier where idproduit=?";

        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1,idProduit);
            ResultSet rs= ps.executeQuery();
            while (rs.next()){

                /*p.setId(rs.getInt(1));
                p.setCodeProduit(rs.getString(2));
                p.setDesgination(rs.getString(3));
                p.setQteStock(rs.getInt(4));
                p.setQteMin(rs.getInt(5));
                p.setPrixUnite(rs.getInt(6));
                p.setIdUnite(rs.getString(7));
                p.setImage(rs.getString(8));
                p.setIdCat(rs.getString(9));
                data.add(p);*/
          //  }

      /*  } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return p;
    }*/

    @Override
    public Boolean productExist(Panier panier, int idUser) {
        String requete="select count(*) from panier where iduser=? and idproduit=?";

        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1,idUser);
            ps.setInt(2,panier.getIdProduit());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                int count=rs.getInt(1);
                return count>0;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }

        return false;
    }

    @Override
    public List<Panier> recuperer() {
        List<Panier> data=new ArrayList<>();
        String requete="select * from panier";

        try {
            Statement st=MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs=st.executeQuery(requete);
            while (rs.next()){
                Panier p=new Panier();
                p.setIdPanier(rs.getInt(1));
                p.setIdUser(rs.getInt(2));
                p.setIdProduit(rs.getInt(3));
                p.setQuantite(rs.getInt(4));
                data.add(p);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }

        return data;
    }

    //@Override
   /* public void supprimerTousLesPanier() {
        String requete="DELETE FROM panier";
        try {
            Statement st=MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(requete);
            System.out.println("tous les paniers sont supprimés");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/

    @Override
    public void supprimerTousLesPanier() {
        String requete1 = "DELETE FROM commande WHERE idpanier IN (SELECT idpanier FROM panier)";
        String requete2 = "DELETE FROM panier";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(requete1);
            st.executeUpdate(requete2);
            System.out.println("Tous les paniers et les commandes associées sont supprimés");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




    public List<String> afficherProduit(){
        List<String> lesProduits=new ArrayList<>();
        String requete = "SELECT p.des " +    // Add a space after "p.des"
                "FROM panier pa " +
                "INNER JOIN prod p ON pa.idproduit = p.id_produit " +
                "WHERE pa.iduser = ?";

        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1,recupererDernierIdUtilisateur());
            ResultSet rs=ps.executeQuery();
            String st=new String();
               while (rs.next()){
                   String produit = rs.getString("des"); // Assuming "des" is the column name for product names
                   lesProduits.add(produit); // Add each product name to the list
               }

            for (String produit : lesProduits) {
                System.out.println(produit);
            }

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesProduits;
    }

    public void decrementerQteStock() {
        String requete = "UPDATE prod p " +
                "INNER JOIN panier pa ON p.id_produit = pa.idproduit " +
                "SET p.qtestock = p.qtestock - 1";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(requete);
            System.out.println("La quantité de stock a été mise à jour avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la quantité de stock : " + e.getMessage());
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



    public List<String> afficherPrix(){
        List<String> lesPrix=new ArrayList<>();
        String requete="SELECT p.PrixUnitaire " +
                "FROM panier pa " +
                "INNER JOIN prod p ON pa.idproduit = p.id_produit " +
                "WHERE pa.iduser = ?";

        try {
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1,recupererDernierIdUtilisateur());
            ResultSet rs=ps.executeQuery();
            String st=new String();
            while (rs.next()){
                String prix=rs.getString("PrixUnitaire");
                lesPrix.add(prix);

            }
            for (String prix: lesPrix){
                System.out.println(prix);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesPrix;
    }



    // Méthode pour récupérer le dernier ID panier de la table panier
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

    public int recupererDernierIdCommande() {
        int dernierIdCommande = 0;
        try {
            String requete = "SELECT MAX(idcommande) FROM commande";
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dernierIdCommande = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dernierIdCommande;
    }





}
