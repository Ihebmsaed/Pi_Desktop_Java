package tools;

import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Myconnection {
    private final  String url="jdbc:mysql://localhost:3306/Daily_Fiesta";
    private final  String login="root";
    private final  String mdp="";
    public static Myconnection instance;
    private Connection cnx;



    public Connection getCnx() {
        return cnx;
    }


    public static Myconnection getInstance() {
        if(instance==null){
            instance = new Myconnection();
        }
        return instance;
    }

    private Myconnection(){
        try {
            cnx = DriverManager.getConnection(url,login,mdp);
            System.out.println("Connexion etablie!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}