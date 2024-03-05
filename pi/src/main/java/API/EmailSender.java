package API;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(String recipient, String subject, String content) {
        // Configurez les propriétés SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Remplacez par le serveur SMTP de votre fournisseur de messagerie
        properties.put("mail.smtp.port", "587"); // Port SMTP
        properties.put("mail.smtp.auth", "true"); // Authentification requise
        properties.put("mail.smtp.starttls.enable", "true"); // Utilisez TLS

        // Configurez l'authentification SMTP
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("khmiliamine208@gmail.com", "votre_mot_de_passe"); // Remplacez par votre adresse e-mail et mot de passe
            }
        };

        // Créez une session SMTP avec authentification
        Session session = Session.getInstance(properties, authenticator);

        try {
            // Créez un message MIME
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("votre_adresse_email")); // Adresse e-mail de l'expéditeur
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient)); // Adresse e-mail du destinataire
            message.setSubject(subject); // Sujet de l'e-mail
            message.setText(content); // Contenu de l'e-mail

            // Envoyez le message
            Transport.send(message);
            System.out.println("E-mail envoyé avec succès !");
        } catch (MessagingException e) {
            System.out.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
        }
    }
}
