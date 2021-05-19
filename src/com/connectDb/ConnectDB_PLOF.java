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
public class ConnectDB_PLOF {
    
    private String host = "";
    private Integer port = 0;
    private String dbname = "";
    private String password = "";
    private String user = "";
    
    public ConnectDB_PLOF(String HOST, Integer PORT, String DBNAME, String USERNAME, String PASSWORD){
        
        this.host = "localhost";
        this.port = 5432;
        this.dbname = "plof";
        this.password = "2021.";
        this.user = "postgres";
    }


    
    public Connection getConnection(){
                
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
