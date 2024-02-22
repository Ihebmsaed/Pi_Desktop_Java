package interfaces;
import entities.User;

import java.sql.SQLException;
import java.util.List;
public interface IService <User> {
    void ajouter(User t) throws SQLException;

    void modifier(User t) throws SQLException;

    void supprimer(User t) throws SQLException;

    List<entities.User> recuperer() throws SQLException;

    List <User> getAllData()throws SQLException;
}
