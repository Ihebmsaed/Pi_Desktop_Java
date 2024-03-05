package services;

import entities.Reservation;
import interfaces.IReservationService;
import utils.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IReservationService<Reservation> {

    @Override
    public void ajouterReservation(Reservation reservation) {
            String requete = "INSERT INTO Reservation (IdEv, IdRes, IdUser,nombreDePlace) VALUES (?, ?, ?,?)";
            try {
                PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
                ps.setInt(1, reservation.getIdRes());
                ps.setInt(2, reservation.getIdEv());
                ps.setInt(3, reservation.getIdUser());
                ps.setInt(4,reservation.getNombreDePlace());
                ps.executeUpdate();
                System.out.println("reservation ajoutée");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


    }

    @Override
    public void modifierReservation(Reservation reservation, int IdEv) {
        String requete = "UPDATE reservation SET IdEv=?, IdRes=?, IdUser=?  , nombreDePlace=?";
        try {
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1, reservation.getIdRes());
            ps.setInt(2, reservation.getIdEv());
            ps.setInt(3, reservation.getIdUser());
            ps.setInt(4, reservation.getNombreDePlace());


            ps.executeUpdate();
            System.out.println("reservation modifier");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void countReservation(Reservation reservation, int IdEv) {
        String query = "SELECT COUNT(*) FROM Reservation WHERE IdRes = ?";
        try {
            PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query);
            statement.setInt(1, IdEv);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = ((ResultSet) resultSet).getInt(1);
                System.out.println("Nombre de réservations pour l'événement avec IdEv " + IdEv + " : " + count);
            } else {
                System.out.println("Aucune réservation trouvée pour l'événement avec IdEv " + IdEv);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du comptage des réservations pour l'événement avec IdEv " + IdEv, e);
        }
    }


    @Override
    public void consulterReservation(Reservation reservation, int IdEv) {
        String query = "SELECT * FROM Reservation WHERE IdEv = ?";
        try {
            PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query);
            statement.setInt(1, IdEv);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int IdRes = resultSet.getInt("IdRes");
                int IdUser = resultSet.getInt("IdUser");
                int nombreDePlace = resultSet.getInt("nombreDePlace");
                System.out.println("Réservation IdRes: " + IdRes + ", IdUser: " + IdUser + ", Nombre de places: " + nombreDePlace);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la consultation des réservations pour l'événement avec IdEv " + IdEv, e);
        }
    }



}
