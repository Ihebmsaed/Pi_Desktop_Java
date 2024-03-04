package interfaces;

import entities.Panier;


import java.util.List;

public interface IPanierServices<T> {

    void ajouterPanier(T t) ;

    List<Panier> recupererlastPanier(int idUser);

    void supprimerProduitParId(int idProduit);

   // List<String> afficherPanier(int idUser);
   // String recupererProduitParId(int idProduit);

    Boolean productExist(T t, int idUser);

    List<Panier> recuperer();
    void supprimerTousLesPanier();
}
