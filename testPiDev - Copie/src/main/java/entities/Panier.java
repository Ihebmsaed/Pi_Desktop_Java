package entities;

public class Panier {
    private int idPanier;
    private int idProduit;
    private int idUser;
    private int Quantite;

    public Panier(){}

    public Panier( int idProduit, int Quantite) {
        //this.idPanier = idPanier;
        this.idProduit = idProduit;
        this.idUser = idUser;
        this.Quantite = Quantite;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "idPanier=" + idPanier +
                ", idProduit=" + idProduit +
                ", idUser=" + idUser +
                ", Quantite=" + Quantite +
                '}';
    }

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int Quantite) {
        this.Quantite = Quantite;
    }


}
