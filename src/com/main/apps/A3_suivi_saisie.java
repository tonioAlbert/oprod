/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.apps;

import com.connectDb.ConnectDb;
import com.allInterfaces.action3saisien.UserFormDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;





/**
 *
 * @author RAP
 */
  


public class A3_suivi_saisie {
    
    private static Connection connectDatabase;
    
    private static PreparedStatement st;
    private static ResultSet rs;
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
        String host = "";
        String port = "";
        String dbname  = "";
        String user  = "";
        String password  = "";
        
	// VERIFIER SI FICHIER DE CONF EXISTE
        String racine = new File("").getAbsolutePath();
        String path = new File("").getAbsolutePath()+"\\conf";
        String filePathAndName = path + "\\config.json";

        File folder = new File(path);
        File file = new File(filePathAndName);
        
        if(!folder.exists()){
            
            System.out.println("Dossier de configuration introuvable !");
            JOptionPane jop = new JOptionPane();
            int option = jop.showConfirmDialog(null, "Dossier de configuration introuvable !\nVoulez-vous lancer le procédure de création du dossier de configuration ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(option == JOptionPane.OK_OPTION){
                
                System.out.println("Creation dossier ...");
                folder.mkdir();
            }
   
        }else if(!file.exists()){
            
                System.out.println("Fichier de configuration introuvable !");
                
                JOptionPane jop2 = new JOptionPane();
                int option2 = jop2.showConfirmDialog(null, "Fichier de configuration introuvable !\nVoulez-vous maintenant procéder à la création du fichier de configuration ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(option2 == JOptionPane.OK_OPTION){

                    
                    try{
                        
                        
                        System.out.println("Creation fichier conf ...");
                        file.createNewFile();
                        
                        
                        // remplissage du fichier
                        JSONObject j = new JSONObject();
                        j.put("host", "127.0.0.1");
                        j.put("port", "5432");
                        j.put("dbname", "oprod");
                        j.put("user", "postgres");
                        j.put("password", "2021.");
                        
  
                        try(FileWriter fileJson = new FileWriter(filePathAndName)){
                            
                            System.out.println("Remplissage du fichier de conf ...");
                            
                            fileJson.write(j.toString());
                            fileJson.flush();
                            
                            
                            System.out.println("dossier et fichier conf trouvé ! .......");

                        }catch(IOException e){
                            e.printStackTrace();
                        }

                    }catch(IOException f){
                        f.printStackTrace();
                    }
                }
            }else{
            
            //connectDatabase = new ConnectDb("127.0.0.15", 5432, "oprod", "2021.", "postgres").getConnection();
            
            
        // IL FAUT LIRE LE FICHIER DE CONFIGURATION
        
        JSONParser parser = new JSONParser();
        
        try{
            Object obj = parser.parse(new FileReader(filePathAndName));
            
            JSONObject jsonObject = (JSONObject) obj;

            
            host = (String) jsonObject.get("host");
            port = (String) jsonObject.get("port");
            dbname = (String) jsonObject.get("dbname");
            user = (String) jsonObject.get("user");
            password = (String) jsonObject.get("password");
            

            //System.out.println("Port dans le fichier : " + dbname);
            
            
        }
        catch(FileNotFoundException e) {e.printStackTrace();}
        catch(IOException e) {e.printStackTrace();}
        catch(ParseException e) {e.printStackTrace();}
        catch(Exception e) {e.printStackTrace();}
        
        
            
        //String host = "127.0.0.1";
        //String port = "5432";
        //String dbname = "oprod";
        //String user = "postgres";
        //String password = "2021.";
        


        //Connection connexion = null;
        
        try{
            
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://"+host+":"+port+"/"+dbname;

            Connection connexion  = DriverManager.getConnection(url, user, password);
            
            
            //System.out.println("Lancement de l'application...  : ");
            UserFormDialog home = new UserFormDialog();
            home.setVisible(true);
            home.setLocationRelativeTo(null);

        }catch(Exception e){
            
            System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null, "Impossible de se connecter à la basez de données !\n\nVérifier votre fichier de configuration", "Connexion dans la base de données impossible", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Vérifier votre fichier de configuration\n\n" + e.getMessage(), "Connexion dans la base de données impossible", JOptionPane.INFORMATION_MESSAGE);
            
        }
             
            
        }
        
    }
    
}
