package entities;
import java.sql.Date;

public class Reservation {
    private int IdEv, IdRes, IdUser;
    private int nombreDePlace;

    private String nom;

    @Override
    public String toString() {
        return "Reservation{" +
                "IdEv=" + IdEv +
                ", IdRes=" + IdRes +
                ", IdUser=" + IdUser +
                ", nombreDePlace=" + nombreDePlace +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    private String prenom;

    public Reservation() {
        this(0, 0, 0, 0);
    }

    public Reservation( int idRes,int idEv, int idUser, int nombreDePlace) {
        IdEv = idEv;
        IdRes = idRes;
        IdUser = idUser;
        this.nombreDePlace = nombreDePlace;
    }

    public int getIdEv() {
        return IdEv;
    }

    public void setIdEv(int idEv) {
        IdEv = idEv;
    }

    public int getIdRes() {
        return IdRes;
    }

    public void setIdRes(int idRes) {
        IdRes = idRes;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public int getNombreDePlace() {
        return nombreDePlace;
    }

    public void setNombreDePlace(int nombreDePlace) {
        this.nombreDePlace = nombreDePlace;
    }

}