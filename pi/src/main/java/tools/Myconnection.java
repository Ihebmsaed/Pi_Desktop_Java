package tools;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Myconnection {



    private final String url="jdbc:mysql://localhost:3306/reclamationn";

    private final String login="root";

    private final String mdp="";
    public static Myconnection myconnection;
     private Connection cnx;
    public static Myconnection instance;
    private Myconnection(){
        try {
            cnx= DriverManager.getConnection(url,login,mdp);
            System.out.println("Connection établie!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static Myconnection getInstance() {
        if(instance==null){
            instance=new Myconnection();
        }
        return instance;
    }
    public Connection getCnx() {
        return cnx;
    }
}
