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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author RAP
 */
public class Region {
    
    private String BDD_HOST = "";
    private Integer BDD_PORT;
    private String BDD_DBNAME = "";
    private String BDD_USER = "";
    private String BDD_PWD = "";
    
    PreparedStatement st;
    ResultSet rs;
    private Connection connectDatabase;
    
    
    public Region(String HOST, Integer PORT, String DBNAME, String USER, String PWD){
        
        this.BDD_HOST = HOST;
        this.BDD_PORT = PORT;
        this.BDD_DBNAME = DBNAME;
        this.BDD_USER = USER;
        this.BDD_PWD = PWD;
        
        
        connectDatabase = new ConnectDb(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getConnection();
        //connectDatabase = new ConnectDb("192.168.88.10", 5432, "oprod", "root963.0", "gaetan").getConnection();
    }
    
    
    
    public List <String[]> getAllRegionsForPLOF(){
        
        List <String[]> regions = new ArrayList();
        
        try {
            
            String q = "select replace(id_region,left(id_region,1),'')::integer as idregion,\n" +
            "code_region as coderegion,\n" +
            "nom as nomregion from region";
            
            st = connectDatabase.prepareStatement(q);    
            rs = st.executeQuery();
 
            while(rs.next()){
                String[] datas = { rs.getString("idregion"), rs.getString("coderegion"), rs.getString("nomregion")};          
                regions.add(datas);
            }    

            st.close();
            rs.close();
   
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
             
        return regions;
    }
    
      
    public HashMap<String, String> getAllRegions(){
        
        HashMap<String, String> regions = new HashMap<String, String>();
        
        try {
            
            String q = "SELECT id_region, nom as nom_region FROM region ORDER BY id_region ASC";
            st = connectDatabase.prepareStatement(q);    
            rs = st.executeQuery();
 
            while(rs.next()){
                regions.put(rs.getString("id_region"), rs.getString("nom_region")); 
            }    

            st.close();
            rs.close();
   
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
             
        return regions;
    }
    
}
