package entities;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import utils.MyConnection;

import java.sql.Connection;

public class Sms {

    Connection cnx;

    public Sms() {
        cnx = MyConnection.getInstance().getCnx();
    }

    // twilio.com/console
    public static final String ACCOUNT_SID = "AC51cd1275647b23a05dd4664700e8a284";
    public static final String AUTH_TOKEN = "37176df65d55ce2f4850332c07bdabd2";

    public static void main(String[] args) {

    }

    public static void SmsSend(String clientPhoneNumber, String s) {

        String accountSid = "AC51cd1275647b23a05dd4664700e8a284";
        String authToken = "37176df65d55ce2f4850332c07bdabd2";

        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new PhoneNumber("+216" + clientPhoneNumber),
                    new PhoneNumber("+15622423556"),
                    "Votre commande et payement sont passer avec succés!"
            ).create();

            System.out.println("SID du message : " + message.getSid());
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }
}