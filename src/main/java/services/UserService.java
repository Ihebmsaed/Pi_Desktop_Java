package services;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import interfaces.IService;

import tools.Myconnection;

public class UserService implements IService<User> {

    Connection cnx;

    public UserService() {
        cnx = Myconnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(User t) throws SQLException {

        String req = "INSERT INTO user (nom,prenom,email,mdp,tel,image,role) VALUES(?,?,?,?,?,?,?)";
        //String req = "INSERT INTO yyy (nom,prenom) VALUES(?,?)";
        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setString(1, t.getNom());
        stmt.setString(2, t.getPrenom());
        stmt.setString(3, t.getEmail());
        stmt.setString(4, t.getMdp());
        stmt.setInt(5, t.getTel());
        stmt.setString(6, t.getImage());
        stmt.setString(7, t.getRole());
        int result = stmt.executeUpdate();


        System.out.println(result + " enregistrement ajouté.");


    }

    public boolean existemail(String email) throws SQLException {
        boolean exist = false;
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            exist = true;

        }
        return exist;
    }


    @Override
    public void modifier(User t) throws SQLException {
        String req = "Update user set nom=?, prenom=?, email=?, mdp=?, tel=?, image=?, role=? where id=?";
        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setString(1, t.getNom());
        stmt.setString(2, t.getPrenom());
        stmt.setString(3, t.getEmail());
        stmt.setString(4, t.getMdp());
        stmt.setInt(5, t.getTel());
        stmt.setString(6, t.getImage());
        stmt.setString(7, t.getRole());
        stmt.setInt(8, t.getId());

        stmt.executeUpdate();

        System.out.println(" modification etablie!");
    }


    @Override
    public void supprimer(User t) throws SQLException {
        String req = "Delete from user where id=?";
        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setInt(1, t.getId());
        stmt.executeUpdate();
        System.out.println(" suppression etablie!");


    }



    public List<User> rechercherParNom(String nom) throws SQLException {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM user WHERE (role='Artiste' OR role='simple utilisateur') AND (nom LIKE '%" + nom + "%' OR email LIKE '%" + nom + "%' OR prenom LIKE '%" + nom + "%')";
        Statement stm = cnx.createStatement();
        ResultSet rs = stm.executeQuery(req);

        while(rs.next()){
            User p = new User();
            p.setId(rs.getInt("id"));
            p.setTel(rs.getInt("tel"));
            p.setEmail(rs.getString("email"));
            p.setNom(rs.getString("nom"));
            p.setPrenom(rs.getString("prenom"));
            p.setRole(rs.getString("role"));
            // p.setImage(rs.getString("image"));


            p.setImage(rs.getString("image"));


            p.setMdp(rs.getString("mdp"));

            users.add(p);


        }




        return users;
    }
    public void ModifMDP(String email, String mdp) throws SQLException{

        String req="Update user set mdp=? where email=?";
        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setString(1, mdp);
        stmt.setString(2, email);
        stmt.executeUpdate();
    }
    @Override
    public List<User> recuperer() throws SQLException  {
        List<User> users = new ArrayList<>();
        String req="select * from user";
        Statement st = cnx.createStatement();
        ResultSet rs =  st.executeQuery(req);
        while(rs.next()){
            User p = new User();
            p.setId(rs.getInt("id"));
            p.setTel(rs.getInt("tel"));
            p.setEmail(rs.getString("email"));
            p.setNom(rs.getString("nom"));
            p.setPrenom(rs.getString("prenom"));
            p.setRole(rs.getString("role"));
            // p.setImage(rs.getString("image"));


            p.setImage(rs.getString("image"));


            p.setMdp(rs.getString("mdp"));

            users.add(p);


        }




        return users;
    }

    public String getNom(int id) throws SQLException {
        String req = "SELECT nom FROM user WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        String nom = "";
        if (rs.next()) {
            nom = rs.getString("nom");
        }
        return nom;
    }




    @Override
        public List<User> getAllData () throws SQLException {
            List<User> data = new ArrayList<>();
            String requete = "Select * From user";
            Statement st = Myconnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                User p = new User();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setEmail(rs.getString("email"));
                p.setMdp(rs.getString("mdp"));
                p.setTel(rs.getInt(6));
                p.setImage(rs.getString("image"));
                p.setRole(rs.getString("role"));

                data.add(p);


            }
            return data;
        }


    }





