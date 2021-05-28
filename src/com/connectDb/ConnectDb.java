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
    
    private String host = "";
    private Integer port = 0;
    private String dbname = "";
    private String password = "";
    private String user = "";
    
    public ConnectDb(String HOST, Integer PORT, String DBNAME, String USERNAME, String PASSWORD){
        
        this.host = HOST;
        this.port = PORT;
        this.dbname = DBNAME;
        this.password = PASSWORD;
        this.user = USERNAME;
    }


    
    public Connection getConnection(){
                
        Connection connexion = null;
        
        try{
            
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://"+this.host+":"+this.port+"/"+this.dbname;
            connexion = DriverManager.getConnection(url, this.user, this.password);
            return connexion;

        }catch(Exception e){
            //System.out.println(e.getMessage());
            return null;
        }
        
        //return  connexion;
    }

}
