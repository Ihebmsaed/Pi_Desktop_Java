package interfaces;

import entities.Panier;
import entities.Produit;

import java.util.List;

public interface IPanierServices<T> {

    void ajouterPanier(T t) ;

    List<Panier> recupererlastPanier(int idUser);

    void supprimerProduitParId(int idProduit);

    List<Produit> afficherPanier(int idUser);
    Produit recupererProduitParId(int idProduit);

    Boolean productExist(T t, int idUser);

    List<Panier> recuperer();
    void supprimerTousLesPanier();
}
