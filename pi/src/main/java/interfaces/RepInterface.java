package interfaces;

import entities.Reponse;

import java.sql.SQLException;
import java.util.List;
public interface RepInterface<T> {
    public void ajouter(T t) throws SQLException;
    public void supprimer(Reponse reponse);

    public List<T>recupererParRecId(int rec_id) throws SQLException;
    public void supprimerParRecId(int rec_id) throws SQLException;

}
