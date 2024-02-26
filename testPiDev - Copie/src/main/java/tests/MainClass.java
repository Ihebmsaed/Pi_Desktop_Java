package tests;

import entities.Commande;
import entities.Panier;
import services.CommandeServises;
import services.PanierServices;

import java.util.Date;

public class MainClass {
    public static void main(String[] args) {

        Date dateActuelle=new Date();

        Commande c1=new Commande(1,1,"rayen","benoun","333333333","2021","oued ellil","25 rue el jem",dateActuelle,20.5f);
        Commande c2=new Commande(2,2,"sajed","benoun","333333333","2021","oued ellil","25 rue el jem",dateActuelle,20.5f);
        CommandeServises cs=new CommandeServises();
        //cs.ajouterCommande(c1);
        //cs.supprimerTousLesCommandes();
        //cs.ajouterCommande(c2);
        //System.out.println(cs.recupererCommande());
        //System.out.println(cs.recupererCommandeByUserID(2));
        //cs.supprimeCommande(2);
        //System.out.println(cs.recupererCommandeOrderByDate());

        Panier p1=new Panier(50,75);
        Panier p2=new Panier(10,100);
        Panier p3=new Panier(10,50);
        PanierServices ps=new PanierServices();
        //ps.ajouterPanier(p1);
        //ps.ajouterPanier(p2);
        //ps.ajouterPanier(p3);
        //ps.supprimerTousLesPanier();
        //System.out.println(ps.recupererlastPanier(18));
        //System.out.println(ps.recuperer());
        //ps.supprimerProduitParId(50);
        //System.out.println(ps.productExist(p2,20));
        //ps.afficherProduit();
        //ps.afficherPrix();


    }
}
