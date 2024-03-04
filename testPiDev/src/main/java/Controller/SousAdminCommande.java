package Controller;

import entities.Commande;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import utils.MyConnection;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SousAdminCommande {

    @FXML
    private Label codeposft;

    @FXML
    private Label commande_date;

    @FXML
    private Label commande_id;

    @FXML
    private Button imprimer;

    @FXML
    private Label nomft;

    @FXML
    private Label numft;

    @FXML
    private Label panier_id;

    @FXML
    private Label prenomft;

    @FXML
    private Label prixtotft;

    @FXML
    private Label rueft;

    @FXML
    private Label user_id;

    @FXML
    private Label villeft;

    private Commande commande;
    // Méthode pour afficher les données de la commande
    public void afficherDonneesCommande(Commande commande) {


        String requete="select nom,prenom  from user where id=?";
        try {
            PreparedStatement ps= MyConnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1,commande.getIdUser());
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                commande.setNomUser(rs.getString("nom"));
                commande.setPrenomUser(rs.getString("prenom"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
this.commande=commande;
        nomft.setText("nom : "+commande.getNomUser());
        prenomft.setText("prenom : "+commande.getPrenomUser());
        System.out.println("commm"+commande);
        // Assurez-vous d'ajouter d'autres champs nécessaires

        commande_id.setText("ID Commande : " + commande.getIdCommande());
        user_id.setText("User ID : " + commande.getIdUser());
        panier_id.setText("Panier ID : " + commande.getIdPanier());
        numft.setText("Téléphone : " + commande.getNumTel());
        villeft.setText("Ville : " + commande.getVille());
        rueft.setText("Rue : " + commande.getRue());
        codeposft.setText("code postale "+commande.getCodePostal());
        prixtotft.setText("prix en dt "+commande.getPrix());
        commande_date.setText("commande_date :\n"+commande.getDateCommande());
    }

    public void Pdf(Commande commande){

        // Créer un document PDF
        try (PDDocument document = new PDDocument()) {
            this.commande=commande;
            PDPage page = new PDPage();
            document.addPage(page);
            float x = 100;
            float y = 600;

            // Créer un flux de contenu pour écrire dans le document PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                // Ajouter du texte au document PDF
                PDFont font = PDType1Font.HELVETICA_BOLD;
                contentStream.setFont(font, 12);
                contentStream.beginText();
                System.out.println();
                // Positionner le premier élément de texte
                contentStream.newLineAtOffset(x, y);
                contentStream.showText("Date commande : " + commande.getDateCommande());
                // Passer à la ligne suivante
                y -= 20; // Ajuster cette valeur en fonction de l'espacement souhaité
                contentStream.newLineAtOffset(0, 20);
                contentStream.showText("Rue : " + commande.getRue());
                // Passer à la ligne suivante
                y -= 20; // Ajuster cette valeur en fonction de l'espacement souhaité
                contentStream.newLineAtOffset(0, 20);
                contentStream.showText("Code Postale : " + commande.getCodePostal());
                // Passer à la ligne suivante
                y -= 20; // Ajuster cette valeur en fonction de l'espacement souhaité
                contentStream.newLineAtOffset(0, 20);
                contentStream.showText("Téléphone : " + commande.getNumTel());
                // Passer à la ligne suivante
                y -= 20; // Ajuster cette valeur en fonction de l'espacement souhaité
                contentStream.newLineAtOffset(0, 20);
                contentStream.showText("Ville : " + commande.getVille());
                // Passer à la ligne suivante
                y -= 20; // Ajuster cette valeur en fonction de l'espacement souhaité
                contentStream.newLineAtOffset(0, 20);
                contentStream.showText("prix : " + commande.getPrix());
                // Passer à la ligne suivante
                y -= 20; // Ajuster cette valeur en fonction de l'espacement souhaité
                contentStream.newLineAtOffset(0, 20);
                contentStream.showText("Prenom Utilisateur : " + commande.getPrenomUser());
                // Passer à la ligne suivante
                y -= 20; // Ajuster cette valeur en fonction de l'espacement souhaité
                contentStream.newLineAtOffset(0, 20);
                contentStream.showText("Nom Utilisateur : " + commande.getNomUser());
                // Passer à la ligne suivante
                y -= 20; // Ajuster cette valeur en fonction de l'espacement souhaité
                contentStream.newLineAtOffset(0, 20);
                contentStream.showText("IDCommande :" + commande.getIdCommande());
                // Passer à la ligne suivante
                y -= 20; // Ajuster cette valeur en fonction de l'espacement souhaité
                contentStream.newLineAtOffset(0, 20);

                // Terminer l'ajout de texte
                contentStream.endText();
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de l'écriture dans le flux de contenu.", e);
            }

            // Enregistrer le document PDF sur le disque
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("Pdf Colis.pdf");
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                try {
                    document.save(file);
                } catch (IOException e) {
                    throw new RuntimeException("Erreur lors de la sauvegarde du document PDF.", e);
                } finally {
                    try {
                        document.close(); // Fermer le document après l'avoir sauvegardé
                    } catch (IOException e) {
                        throw new RuntimeException("Erreur lors de la fermeture du document PDF.", e);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la création du document PDF.", e);
        }

    }


    @FXML
    public void imprimerPdf(ActionEvent event) {
        //Commande commande1=new Commande();

        afficherDonneesCommande(this.commande);
        Pdf(this.commande);
    }
}
