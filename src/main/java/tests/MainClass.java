package tests;

import entities.User;
import services.UserService;


import java.sql.SQLException;

public class MainClass {
    public static void main(String[] args) throws SQLException {

        User u=new User(9,33,"ioob","mased","iheb.masead@gmail.com","iheb123","admin","taswira");

        UserService us=new UserService();
        //us.ajouter(u);
        //us.modifier(u);
        us.supprimer(u);
        //System.out.println(us.getAllData());


    }
}
