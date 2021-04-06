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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAP
 */
public class Persphys {
    
    private static String str_idPersphys = "";
    private static String str_prenom = "";
    private static PreparedStatement st;
    private static ResultSet rs;
    private static Connection connectDatabase;
    private static String state = "false";
    
    
    // CONSTRUCTEUR
    public Persphys(String idPersohys, String prenom) {
        
        this.str_idPersphys = idPersohys;
        this.str_prenom = prenom;
        
        connectDatabase = new ConnectDb("127.0.0.1", 5432, "oprod", "2021.", "postgres").getConnection();
        
    }

    
    
    public static String setPrenom(String id, String prenom){
                

                String q = "UPDATE persphys SET prenom='"+prenom+"' WHERE id_persphys='"+id+"' ;";
                
                //String q = "UPDATE persphys SET prenom = 'gAETAN AntoNIo AlBerT' WHERE id_persphys = 'T626';";

            try {
                
                //String q = "UPDATE persphys SET prenom=? WHERE id_persphys=? ;";

                System.out.println(q + "\n");
                st = connectDatabase.prepareStatement(q);
                int result = st.executeUpdate(q);
                //st.setString(1, prenom);
                //st.setString(2, id);
                //st.executeUpdate(q);


                st.close();
                //rs.close();


            } catch (SQLException e) {
                System.err.println("Error executing query: " +e.getMessage());
            }
        
        return state;
    }
    
    
}

