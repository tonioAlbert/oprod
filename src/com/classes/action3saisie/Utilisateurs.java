/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.action3saisie;


import com.connectDb.ConnectDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAP
 */
public class Utilisateurs {
    
    private String BDD_HOST = "";
    private Integer BDD_PORT;
    private String BDD_DBNAME = "";
    private String BDD_USER = "";
    private String BDD_PWD = "";
    
    private String login;
    private String password;
    private String profil;
    
    private Connection connectDatabase;
    
    
    PreparedStatement st;
    ResultSet rs;

    public Utilisateurs(String HOST, String DBNAME, Integer PORT, String USER, String PWD) {
        
        this.BDD_HOST = HOST;
        this.BDD_PORT = PORT;
        this.BDD_DBNAME = DBNAME;
        this.BDD_USER = USER;
        this.BDD_PWD = PWD;
        
        connectDatabase = new ConnectDb(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getConnection();
        //connectDatabase = new ConnectDb("192.168.88.10", 5432, "oprod", "C@seF&Ge0X2", "postgres").getConnection();
    }
    
    

    public String getLogin(String login) {
        
        //System.out.println("votre login est le : "+ login);

        try {
            
            String q = "SELECT login FROM utilisateur WHERE login = ?";
            st = connectDatabase.prepareStatement(q);    
            st.setString(1, login);
        
            rs = st.executeQuery();
            
            if (rs.next()){
                
                    //System.out.println("OKKKK" );
                    this.login = rs.getString("login");
                    //p = "kkkk";

            }else{
                    System.out.println("Impossible de récupérer le login de l'Utilisateur");
                    this.login = null;
            }

            st.close();
            rs.close();
            
            
        } catch (SQLException ex) {
            //System.out.println("Eurreur ato annn");
            Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.login;
    }
    
    
    

    public String getPassword() {

        try {
            String q = "SELECT pwd FROM utilisateur WHERE login = ?";
            st = connectDatabase.prepareStatement(q);    
            st.setString(1, this.login);
        
            rs = st.executeQuery();
            
            if (rs.next()){
                
                    //System.out.println("OKKKK" );
                    this.password = rs.getString("pwd");
                    //p = "kkkk";

            }else{
                    System.out.println("Impossible de récupérer le mot de passe de l'Utilisateur");
                    this.password = null;
            }

            st.close();
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.password;
    }
    
    

    public String[] getProfilDroits() {
        
        try {
            String q = "SELECT utilisateur.login, utilisateur.nom, utilisateur.prenom, utilisateur.pwd, utilisateur.id_profil, droits.id_droits, droits.param_data_base FROM utilisateur,\n" +
            "public.droits\n" +
            "WHERE utilisateur.id_droits = droits.id_droits AND login = ?";
            st = connectDatabase.prepareStatement(q);    
            st.setString(1, this.login);
        
            rs = st.executeQuery();
            
            if (rs.next()){
                
                    //System.out.println("OKKKK" );
                    String[] userProfilAnDroits = {rs.getString("nom"), rs.getString("prenom"), 
                        rs.getString("id_profil"), rs.getString("id_droits"), rs.getString("param_data_base")};
                    //p = "kkkk";
                    
                return userProfilAnDroits;
                
            }else{
                    System.out.println("Impossible de récupérer le profil de l'utilisateur");
                    this.profil = null;
                    
            }

            st.close();
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;

    }
    
    
    
    

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }
    
    
    
    
    
    
    
    
    
}
