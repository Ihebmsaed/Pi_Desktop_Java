package entities;

import java.util.Date;

public class Commande {

    private int idPanier;
    private int idUser;
    private int idCommande;
    private String nomUser;
    private String prenomUser;
    private String numTel;
    private String codePostal;
    private String ville;
    private String rue;
    private Date dateCommande;
    private float prix;

    public Commande(){}

    public Commande(int idPanier, int idUser, String nomUser, String prenomUser, String numTel, String codePostal, String ville, String rue, Date dateCommande, Float prix) {
        //this.idCommande = idCommande;
        this.idPanier = idPanier;
        this.idUser = idUser;
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.numTel = numTel;
        this.codePostal = codePostal;
        this.ville = ville;
        this.rue = rue;
        this.dateCommande = dateCommande;
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "idPanier=" + idPanier +
                ", idUser=" + idUser +
                ", idCommande=" + idCommande +
                ", nomUser='" + nomUser + '\'' +
                ", prenomUser='" + prenomUser + '\'' +
                ", numTel='" + numTel + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                ", rue='" + rue + '\'' +
                ", dateCommande='" + dateCommande + '\'' +
                ", prix='" + prix + '\'' +
                '}';
    }

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getPrenomUser() {
        return prenomUser;
    }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }
}
