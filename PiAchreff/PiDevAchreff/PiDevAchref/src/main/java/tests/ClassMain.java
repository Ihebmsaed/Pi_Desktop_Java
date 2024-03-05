package tests;

import entities.Event;
import entities.Reservation;
import interfaces.IReservationService;
import services.EventServices;
import services.ReservationService;

import java.sql.Date;


public class ClassMain {

    public static void main(String[] args) {

        // Création de deux instances d'événement avec les données fournies
        Date date = new Date(System.currentTimeMillis()); // Exemple de date pour le début et la fin de l'événement
        Date date2 = new Date(System.currentTimeMillis() + 86400000); // Exemple de date pour le début et la fin de l'événement

        Event event = new Event(30, 11, "maersa", "achref", "7adhra", "malajaw", date, date2, "chbi baba", 30);
        Event event2 = new Event(40, 15, "lac0", "rayen", "mizwed", "malajaw", date, date2, "chbi baba", 5.5F);
        Event event3=new Event(40,15,"mourouj","tarek","7adhra","malajaw",  date,  date2,"chbi baba",50);
        Reservation reservation=new Reservation(4,9,10,5);
        Reservation reservation1=new Reservation(5,7,1,10);

        EventServices eventServices=new EventServices();
        ReservationService reservationService=new ReservationService();
        eventServices.ajouterEvent(event3);
        //eventServices.modifierEvent(event3,2);
        //System.out.println(eventServices.chercherEventParId(event2,1));
        //System.out.println(eventServices.recupererEvent());
        //eventServices.supprimerEvent(event,1);
        //System.out.println(eventServices.rechercher("tarek","7adhra"));
        //reservationService.ajouterReservation(reservation1);
        //reservationService.modifierReservation(reservation1,8);
        //reservationService.consulterReservation(reservation1, 5);


    }

}
