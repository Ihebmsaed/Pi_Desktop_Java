package tests;

import entities.Reclamation;
import entities.Reponse;
import services.ReclamationServices;
import services.ReponsesServices;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Date;



public class Test {
    public static void main(String[] args) throws SQLException {


        ReclamationServices rs = new ReclamationServices();
        ReponsesServices ps = new ReponsesServices();
         Date r = new Date(2024,1,14);
        Reclamation R = new Reclamation(1,2,"hahahah","EVENEMENT","hahahhahaha","1",r);
        Reclamation S = new Reclamation(7,3,"produit perimé","hahahahha","hahahhahaha","1");
        Reclamation K = new Reclamation(8,3,"theatre","admin","ticket ","1");
        Reponse F = new Reponse(1,2,8,"dsff");
        Reponse L = new Reponse(2,3,5,"dssdc");
        //rs.ajouter(R);
        rs.ajouter(R);
        //ps.ajouter(F);
        //ps.ajouter(L);
       // rs.ajouter(K);
        //rs.modifier(S);
        //rs.supprimer(R);
       // rs.supprimerParRecId(2);
       // ps.supprimerParRecId(2);
       // System.out.println(rs.recupererParId(1));
        //System.out.println(rs.recuperer());
        //System.out.println(rs.recupererParUtilisateur(3));
        //System.out.println(ps.recupererParRecId(2));
    }
}
