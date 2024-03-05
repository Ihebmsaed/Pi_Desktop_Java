package interfaces;

import entities.Event;

import java.util.List;

public interface IReservationService <T> {
    void ajouterReservation(T t);
    void modifierReservation(T t,int IdEv);
    void countReservation(T t,int IdEv );
    void consulterReservation(T t,int IdEv);
}
