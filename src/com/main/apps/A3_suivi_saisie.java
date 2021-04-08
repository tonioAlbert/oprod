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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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
        //String racine = new File("").getAbsolutePath();
        String path = new File("").getAbsolutePath()+"\\conf";
        String filePathAndName = path + "\\config.json";

        File folder = new File(path);
        File file = new File(filePathAndName);
        
        if(!folder.exists()){
            
            System.out.println("Dossier de configuration introuvable !");
            JOptionPane jop = new JOptionPane();
            int option = jop.showConfirmDialog(null, "Dossier de configuration introuvable !\nVoulez-vous lancer le procédure de création du dossier et du fichier de configuration ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(option == JOptionPane.OK_OPTION){
                
                // creation du dossier de configuration
                System.out.println("Creation dossier ...");
                folder.mkdir();
                
                    try{
                        
                        
                        System.out.println("Creation fichier conf ...");
                        file.createNewFile();
                        
                        JTextField HoteName = new JTextField();
                        JTextField nameBdd = new JTextField();
                        JTextField portBdd = new JTextField();
                        JTextField userName = new JTextField();
                        JPasswordField passe = new JPasswordField();
                        
                                                
                        int optionConfigs = JOptionPane.showOptionDialog(null, 
                          new Object[] {"Nom d'hôte :", HoteName, "Nom de la base de données :", nameBdd, "Port :", portBdd, "Nom d'utilisateur :", userName, "Mot de passe :", passe},
                          "Connexion",
                          JOptionPane.OK_CANCEL_OPTION,
                          JOptionPane.QUESTION_MESSAGE, null, null, null);
                        
                        if(optionConfigs == 0 ){

                        }else{
                            
                            System.out.println("Vous les vous vraimenet quitter le processus de création du dossier et fichier de configuration ?");
                            JOptionPane jopCreateFolderAndFile = new JOptionPane();
                            int option2 = jopCreateFolderAndFile.showConfirmDialog(null, "Annulation processus de création (dossier et fichier de conf) !\nVous les vous vraimenet quitter le processus de création du dossier et fichier de configuration ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            
                            if(option2 == JOptionPane.OK_OPTION){
                                System.exit(0);
                            }else{
                                int optionConfigs2 = JOptionPane.showOptionDialog(null, 
                                new Object[] {"Nom d'hôte :", HoteName, "Nom de la base de données :", nameBdd, "Port :", portBdd, "Nom d'utilisateur :", userName, "Mot de passe :", passe},
                                "Connexion",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, null, null);
                            }
                        }
                        
                       
                            System.out.println("Nom d'hôte :" + HoteName.getText()+ "Nom de la base de données :"+ nameBdd.getText()+ "Nom d'utilisateur :"+ userName.getText()+ "Mot de passe :"+ passe.getText());
                       
                        
                        
                        // remplissage de l'objet JSON
                        JSONObject j = new JSONObject();
                        j.put("host",  HoteName.getText());
                        j.put("port", portBdd.getText());
                        j.put("dbname", nameBdd.getText());
                        j.put("user", userName.getText());
                        j.put("password", passe.getText());
                        
  
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
            // dossier existe déjà mais le fichier de conf pas encore
            
            
            if(!file.exists()){
                
                System.out.println("Fichier de configuration introuvable !");
                
                JOptionPane jop2 = new JOptionPane();
                int option2 = jop2.showConfirmDialog(null, "Fichier de configuration introuvable !\nVoulez-vous maintenant procéder à la création du fichier de configuration ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(option2 == JOptionPane.OK_OPTION){

                    
                    try{
                        
                        
                        System.out.println("Creation fichier conf ...");
                        file.createNewFile();
                        
                        JTextField HoteName = new JTextField();
                        JTextField nameBdd = new JTextField();
                        JTextField portBdd = new JTextField();
                        JTextField userName = new JTextField();
                        JPasswordField passe = new JPasswordField();
                        
                                                
                        int optionConfigs = JOptionPane.showOptionDialog(null, 
                          new Object[] {"Nom d'hôte :", HoteName, "Nom de la base de données :", nameBdd, "Port :", portBdd, "Nom d'utilisateur :", userName, "Mot de passe :", passe},
                          "Connexion",
                          JOptionPane.OK_CANCEL_OPTION,
                          JOptionPane.QUESTION_MESSAGE, null, null, null);
                        
                        if(optionConfigs == 0 ){

                        }else{
                            
                            System.out.println("Vous les vous vraimenet quitter le processus de création du dossier et fichier de configuration ?");
                            JOptionPane jopCreateFolderAndFile = new JOptionPane();
                            int optionFileCreate = jopCreateFolderAndFile.showConfirmDialog(null, "Annulation processus de création (dossier et fichier de conf) !\nVous les vous vraimenet quitter le processus de création du dossier et fichier de configuration ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            
                            if(optionFileCreate == JOptionPane.OK_OPTION){
                                System.exit(0);
                            }else{
                                int optionConfigs2 = JOptionPane.showOptionDialog(null, 
                                new Object[] {"Nom d'hôte :", HoteName, "Nom de la base de données :", nameBdd, "Port :", portBdd, "Nom d'utilisateur :", userName, "Mot de passe :", passe},
                                "Connexion",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, null, null);
                            }
                        }
                        
                       
                            System.out.println("Nom d'hôte :" + HoteName.getText()+ "Nom de la base de données :"+ nameBdd.getText()+ "Nom d'utilisateur :"+ userName.getText()+ "Mot de passe :"+ passe.getText());
                       
                        
                        
                        // remplissage de l'objet JSON
                        JSONObject j = new JSONObject();
                        j.put("host",  HoteName.getText());
                        j.put("port", portBdd.getText());
                        j.put("dbname", nameBdd.getText());
                        j.put("user", userName.getText());
                        j.put("password", passe.getText());
                        
  
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
            }   // FIN !file.exists()
        } // FIN ELSE !folder.exists()

        try{
            
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://"+"127.0.0.1"+":"+"5432"+"/"+"oprod";

            Connection connexion  = DriverManager.getConnection(url, "postgres", "2021.");
            
            //System.out.println("Lancement de l'application...  : ");
            UserFormDialog home = new UserFormDialog();
            home.setVisible(true);
            home.setLocationRelativeTo(null);

        }catch(Exception e){
            
            System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null, "Impossible de se connecter à la basez de données !\n\nVérifier votre fichier de configuration", "Connexion dans la base de données impossible", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Vérifier votre fichier de configuration\n\n" + e.getMessage(), "Connexion dans la base de données impossible", JOptionPane.INFORMATION_MESSAGE);
            
        }
        
        
    }  // FIN CLASS MAIN
    
}
