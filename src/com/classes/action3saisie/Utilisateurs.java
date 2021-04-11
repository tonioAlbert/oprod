/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.action3saisie;

import com.connectDb.ConnectDb;
import com.connectDb.ConnectDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAP
 */
public class Utilisateurs {
    
    private String login;
    private String password;
    private String profil;
    
    private Connection connectDatabase;
    
    
    PreparedStatement st;
    ResultSet rs;

    public Utilisateurs(String login) {
        
        this.login = login;
        connectDatabase = new ConnectDb("127.0.0.1", 5432, "oprod", "2021.", "postgres").getConnection();
        //connectDatabase = new ConnectDb("192.168.88.10", 5432, "oprod", "C@seF&Ge0X2", "postgres").getConnection();
    }
    
    

    public String getLogin() {

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
    
    

    public String getProfil() {

        
        try {
            String q = "SELECT id_profil FROM utilisateur WHERE login = ?";
            st = connectDatabase.prepareStatement(q);    
            st.setString(1, this.login);
        
            rs = st.executeQuery();
            
            if (rs.next()){
                
                    //System.out.println("OKKKK" );
                    this.profil = rs.getString("id_profil");
                    //p = "kkkk";
                    this.profil = null;

            }else{
                    System.out.println("Impossible de récupérer l'Utilisateur");
            }

            st.close();
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.profil;

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
