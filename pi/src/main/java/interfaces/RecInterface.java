/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Reclamation;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface RecInterface<T> {

    public void ajouter(T t)throws SQLException;
    public void modifier(T t)throws SQLException;
    public void ModifierEtat(int rec_id) ;
    public void supprimer(T t);
    public void supprimerParRecId(int rec_id) throws SQLException;
    public List<T> recupererParUtilisateur(int user_id) throws SQLException;
    public T recupererParId(int rec_id)throws SQLException;
    public List<T> recuperer() ;
    public List<T> recuperParStatus(String selectedStatus);
    public ObservableList<Reclamation>getall();
    public List<Reclamation> rechercherParMotCle(int user_id, String motCle) throws SQLException;
}
