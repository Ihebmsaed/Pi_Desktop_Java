package interfaces;

import entities.Commande;
import javafx.collections.ObservableList;

import java.util.List;

public interface ICommandeServices <T>{

    void ajouterCommande(T t);
    List<T> recupererCommande();
    List<T> recupererCommandeOrderByDate(java.sql.Date dateRecherchee);
    List<T> recupererCommandeByUserID();

    void supprimeCommande();


}
