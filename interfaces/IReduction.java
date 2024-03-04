package interfaces;

import entities.Reduction;

import java.util.List;

public interface IReduction<T> {

    void ajouterReduction(T t);
    void modifierReduction(T t);
    void supprimerReduction(T t);

    List<Reduction> getAll();
}
