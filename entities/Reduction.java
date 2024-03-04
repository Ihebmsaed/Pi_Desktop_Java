package entities;

public class Reduction {

    private int idReduction;
    private String codeProduit;

    public Reduction(String codeProduit, float remise, float prixUnitaire, float nouveauPrix) {
        this.codeProduit = codeProduit;
        this.remise = remise;
        this.prixUnitaire = prixUnitaire;
        this.nouveauPrix = nouveauPrix;
    }

    public Reduction(int idReduction, String codeProduit, float remise, float prixUnitaire, float nouveauPrix) {
        this.idReduction = idReduction;
        this.codeProduit = codeProduit;
        this.remise = remise;
        this.prixUnitaire = prixUnitaire;
        this.nouveauPrix = nouveauPrix;
    }

    @Override
    public String toString() {
        return "Reduction{" +
                "idReduction=" + idReduction +
                ", codeProduit='" + codeProduit + '\'' +
                ", remise=" + remise +
                ", prixUnitaire=" + prixUnitaire +
                ", nouveauPrix=" + nouveauPrix +
                '}';
    }

    public int getIdReduction() {
        return idReduction;
    }

    public void setIdReduction(int idReduction) {
        this.idReduction = idReduction;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public float getRemise() {
        return remise;
    }

    public void setRemise(float remise) {
        this.remise = remise;
    }

    public float getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public float getNouveauPrix() {
        return nouveauPrix;
    }

    public void setNouveauPrix(float nouveauPrix) {
        this.nouveauPrix = nouveauPrix;
    }

    private float remise;
    private float prixUnitaire;
    private float nouveauPrix;

    public Reduction(){}




}
