package entities;

import tools.Myconnection;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.sql.Connection;

public class SMSsender {

    Connection cnx;

    public SMSsender() {
        cnx = Myconnection.getInstance().getCnx();
    }

    // twilio.com/console
    public static final String ACCOUNT_SID = "AC6e71bf8f7263378fa1cbf053926f044c";
    public static final String AUTH_TOKEN = "bc05a34cf765f4a4b208e08534907f23";

    public static void main(String[] args) {

    }

    public static void sendSMS(String clientPhoneNumber, String s) {

        String accountSid = "AC6e71bf8f7263378fa1cbf053926f044c";
        String authToken = "bc05a34cf765f4a4b208e08534907f23";

        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new PhoneNumber("+216" + clientPhoneNumber),
                    new PhoneNumber("+19048530285"),
                    "Votre compte est vérifié"
            ).create();

            System.out.println("SID du message : " + message.getSid());
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }
}