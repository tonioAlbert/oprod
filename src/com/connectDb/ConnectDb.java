/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.connectDb;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author RAP
 */
public class ConnectDb {
    
    final private String host;
    final private Integer port;
    final private String dbname;
    final private String password;
    final private String user;
    
    public ConnectDb(String HOST, Integer PORT, String DBNAME, String PASSWORD, String USERNAME){
        this.host = HOST;
        this.port = PORT;
        this.dbname = DBNAME;
        this.password = PASSWORD;
        this.user = USERNAME;
    }


    
    public Connection getConnection(){
        
        //System.out.println("I méthode appeler ...");
        
        Connection connexion = null;
        
        try{
            
            Class.forName("org.postgresql.Driver");
            
            //String typeBase = "jdbc:postgresql://";
            
            String url = "jdbc:postgresql://"+this.host+":"+this.port+"/"+this.dbname;
            //String user = this.user;
            //String passwd = "2021.";
            //System.out.println("URL vaut : " + url);
            
            connexion = DriverManager.getConnection(url, this.user, this.password);
            
            //if(connexion != null){
                //System.out.println("Connexion dans la BDD OK..|||");
                
            //    return  connexion;
            //}else{
                //System.out.println("Impossible de connecter à la base de données");
            //    connexion.close();
            //    return  null;
            //}
            
            return connexion;

        }catch(Exception e){
            //System.out.println(e.getMessage());
            
            return null;
        }
        
        //return  connexion;
    }
    


}
