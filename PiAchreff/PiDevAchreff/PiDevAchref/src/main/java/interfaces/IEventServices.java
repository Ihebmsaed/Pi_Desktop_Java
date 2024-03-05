package interfaces;

import entities.Event;

import java.util.List;

public interface IEventServices<T> {

    void ajouterEvent(T t);
    void modifierEvent(T t,int event_id);
    boolean supprimerEvent(T t, int event_id);

    boolean supprimerEvent(Event event);

    List<T> recupererEvent();
    List<T> chercherEventParId(T t,int event_id);

    List<Event> rechercher(String search,String sortBy);


}
