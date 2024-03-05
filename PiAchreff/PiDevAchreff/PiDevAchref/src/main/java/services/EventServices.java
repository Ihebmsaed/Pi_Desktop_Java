package services;

import entities.Event;
import interfaces.IEventServices;
import utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventServices implements IEventServices<Event> {
    @Override
    public void ajouterEvent(Event event)  {
        String requete = "INSERT INTO event (event_id,ticketCount, host_id, location_id,title, type, description, startDate, endDate,affiche,TicketPrice) values(?,?,?,?,?,?,?,?,?,?,?) ";

        try {
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            System.out.println("ahla");
            ps.setInt(1, event.getEvent_id());
            ps.setInt(2,event.getTicketCount());
            ps.setInt(3,event.getHost_id());
            ps.setString(4,event.getLocation()); // Corrected line
            ps.setString(5,event.getTitle());
            ps.setString(6,event.getType());
            ps.setString(7,event.getDescription());
            ps.setDate(8,event.getStartDate());
            ps.setDate(9,event.getEndDate());
            ps.setString(10,event.getAffiche());
            ps.setFloat(11,event.getTicketPrice());


            ps.executeUpdate();
            System.out.println("evenement ajoutée");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifierEvent(Event event, int event_id) {
        String requete = "UPDATE event SET title=?, type=?, startDate=?, endDate=? WHERE event_id=?";

        try {
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getType());
            ps.setDate(3, event.getStartDate());
            ps.setDate(4, event.getEndDate());
            ps.setFloat(5, event.getTicketPrice());
            ps.setInt(5, event_id);

            ps.executeUpdate();
            System.out.println("commande modifiée avec succès");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            ;
        }
    }

    @Override
    public boolean supprimerEvent(Event event, int event_id) {
        return false;
    }


    @Override
    public boolean supprimerEvent(Event event) {
        String requete = "DELETE FROM event WHERE title = ?";
        try {
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setString(1, event.getTitle());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Événement supprimé avec succès de la base de données.");
                return true; // Retourne true si la suppression réussit
            } else {
                System.out.println("Aucun événement n'a été supprimé de la base de données.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'événement dans la base de données : " + e.getMessage());
        }
        return false; // Retourne false en cas d'erreur
    }



    @Override
    public List<Event> recupererEvent() {
        List<Event> data = new ArrayList<>();
        String requete = "select * from event";

        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Event e = new Event();
                e.setEvent_id(rs.getInt(1));
                e.setTitle(rs.getString("title"));
                e.setType(rs.getString("type"));
                e.setStartDate(rs.getDate("startdate"));
                e.setEndDate(rs.getDate("enddate"));
                e.setTicketPrice(rs.getFloat("prix"));
                data.add(e);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;

    }


    @Override
    public List<Event> chercherEventParId(Event event,int event_id) {

        List<Event> data = new ArrayList<>();
        String requete = "Select * from event where event_id=?";

        try {
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1, event_id);
            ResultSet rs = ps.executeQuery();
            Event e = new Event();
            while (rs.next()) {
                //e.setEvent_id(rs.getInt(1));
                e.setTicketCount(rs.getInt("ticketcount"));
                e.setHost_id(rs.getInt("host_id"));
                e.setLocation(rs.getString("location"));
                e.setTitle(rs.getString("title"));
                e.setType(rs.getString("type"));
                e.setDescription(rs.getString("description"));
                e.setStartDate(rs.getDate("startdate"));
                e.setEndDate(rs.getDate("enddate"));
                e.setAffiche(rs.getString("affiche"));
                e.setTicketPrice(rs.getFloat("prix"));
                data.add(e);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            ;
        }

        return data;
    }

    public List<Event> rechercher(String search, String sortBy) {
        List<Event> data = new ArrayList<>();
        String requete = "SELECT * FROM event WHERE title LIKE ?";
        //if (sortBy != null) {
        switch (sortBy) {
            case "Title":
                requete += " ORDER BY title ASC";
                break;
            case "Type":
                requete += " ORDER BY type ASC";
                break;
            case "Start Date":
                requete += " ORDER BY startDate ASC";
                break;
            default:
                // No sorting
                break;
        }
        //}
        try {
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setString(1, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Event e = new Event();
                e.setEvent_id(rs.getInt("event_id"));
                e.setTitle(rs.getString("title"));
                e.setType(rs.getString("type"));
                e.setDescription(rs.getString("description"));
                e.setStartDate(rs.getDate("startDate"));
                e.setEndDate(rs.getDate("endDate"));
                e.setTicketCount(rs.getInt("ticketCount"));
                e.setHost_id(rs.getInt("host_id"));
                e.setLocation(rs.getString("location"));
                //e.setTicketPrice(rs.getFloat("ticketPrice"));
                e.setAffiche(rs.getString("affiche"));

                data.add(e);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return data;
    }

    public static void updateTicketCount(Event event, int ticketsReserved) {
        String query = "UPDATE event SET ticketCount = ticketCount - ? WHERE id = ?";
        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ticketsReserved);
            preparedStatement.setInt(2, event.getTicketCount()); // Supposons que l'événement ait un identifiant unique dans la base de données
            preparedStatement.executeUpdate();
            System.out.println("Nombre de tickets mis à jour avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du nombre de tickets : " + e.getMessage());
        }
    }
}




