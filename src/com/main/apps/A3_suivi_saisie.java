/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.apps;

import com.allInterfaces.action3saisies.UserFormDialog;
import com.classes.action3saisie.Querry;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;





/**
 *
 * @author RAP
 */
  


public class A3_suivi_saisie {
    
    //private static Connection connectDatabase;
    //private static PreparedStatement st;
    //private static ResultSet rs;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //this.
       // this.setLocation(parentLoc.x + JFrame.getWidth(), dialogLoc.y);
        //pane.setLocation(parentLoc.x , dialogLoc.y);
        lancement();
        
    }  // FIN CLASS MAIN
    
    
    
    private static void lancement(){

        //String demarche  = "";
        
	// VERIFIER SI FICHIER DE CONF EXISTE
        //String racine = new File("").getAbsolutePath();
        String path = new File("").getAbsolutePath()+"\\conf";
        String filePathAndName = path + "\\config.json";

        File folder = new File(path);
        File file = new File(filePathAndName);
        
        // SI LE DOSSIER DE CONF N'EXISTE PAS
        if(!folder.exists()){
            
            //System.out.println("Dossier de configuration introuvable !");
            JOptionPane jop = new JOptionPane();
            int option = jop.showConfirmDialog(null, "Dossier de configuration introuvable !\nVoulez-vous lancer le procédure de création du dossier et du fichier de configuration ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(option == JOptionPane.OK_OPTION){
                
                // creation du dossier de configuration
                //System.out.println("Creation dossier ...");
                folder.mkdir();
                
                    try{
                        
                        
                        //System.out.println("Creation fichier conf ...");
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
                            
                            // TRAITEMENT DES DONNEES SAISIE PAR L'UTILISATEUR

                        }else{
                            
                            //System.out.println("Voulez-vous vraimenet quitter le processus de création du dossier et fichier de configuration ?");
                            JOptionPane jopCreateFolderAndFile = new JOptionPane();
                            int option2 = jopCreateFolderAndFile.showConfirmDialog(null, "Annulation processus de création (dossier et fichier de conf) !\nVoulez-vous vraimenet quitter le processus de création du dossier et fichier de configuration ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            
                            if(option2 == JOptionPane.OK_OPTION){
                                System.exit(0);
                            }else{
                                
                                JOptionPane.showOptionDialog(null, 
                                new Object[] {"Nom d'hôte :", HoteName, "Nom de la base de données :", nameBdd, "Port :", portBdd, "Nom d'utilisateur :", userName, "Mot de passe :", passe},
                                "Connexion",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, null, null);
                            }
                        }
                        
                       
                        //System.out.println("Nom d'hôte :" + HoteName.getText()+ "Nom de la base de données :"+ nameBdd.getText()+ "Nom d'utilisateur :"+ userName.getText()+ "Mot de passe :"+ passe.getText());
                       
                        String hashHost = Base64.getEncoder().encodeToString(HoteName.getText().getBytes("UTF-8"));
                        String hashPort = Base64.getEncoder().encodeToString(portBdd.getText().getBytes("UTF-8"));
                        String hashDbname = Base64.getEncoder().encodeToString(nameBdd.getText().getBytes("UTF-8"));
                        String hashUser = Base64.getEncoder().encodeToString(userName.getText().getBytes("UTF-8"));
                        String hashPassword = Base64.getEncoder().encodeToString(passe.getText().getBytes("UTF-8"));
                        
                        // remplissage de l'objet JSON
                        JSONObject j = new JSONObject();
                        j.put("host",  hashHost);
                        j.put("port", hashPort);
                        j.put("dbname", hashDbname);
                        j.put("user", hashUser);
                        j.put("password", hashPassword);
                        
  
                        try{
                            
                            FileWriter fileJson = new FileWriter(filePathAndName);
                            //System.out.println("Remplissage du fichier de conf ...");
                            
                            fileJson.write(j.toString());
                            fileJson.close();
                            
                            
                            //System.out.println("dossier et fichier conf trouvé ! .......");

                        }catch(IOException e){
                            //e.printStackTrace();
                            
                            JOptionPane.showMessageDialog(null, "Ecriture impossible" + e.getMessage(), "Impossible d'écrire sur le fichier config.json", JOptionPane.ERROR_MESSAGE);

                        }

                    }catch(IOException f){
                        //f.printStackTrace();
                        
                        JOptionPane.showMessageDialog(null, "Ecriture impossible" + f.getMessage(), "Impossible de lancer le pocessus de création du fichier de configuration", JOptionPane.ERROR_MESSAGE);
                    } 
            }
   
        }else{
            // dossier existe déjà mais le fichier de conf pas encore
            
            
            if(!file.exists()){
                
                //System.out.println("Fichier de configuration introuvable !");
                
                JOptionPane jop2 = new JOptionPane();
                int option2 = jop2.showConfirmDialog(null, "Fichier de configuration introuvable !\nVoulez-vous maintenant procéder à la création du fichier de configuration ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                if(option2 == JOptionPane.OK_OPTION){

                    
                    try{
                        
                        
                        //System.out.println("Creation fichier conf ...");
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
                            
                            //System.out.println("Voulez-vous vraimenet quitter le processus de création du dossier et fichier de configuration ?");
                            JOptionPane jopCreateFolderAndFile = new JOptionPane();
                            int optionFileCreate = jopCreateFolderAndFile.showConfirmDialog(null, "Annulation processus de création (dossier et fichier de conf) !\nVoulez-vous vraimenet quitter le processus de création du dossier et fichier de configuration ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            
                            if(optionFileCreate == JOptionPane.OK_OPTION){
                                System.exit(0);
                            }else{
                                JOptionPane.showOptionDialog(null, 
                                new Object[] {"Nom d'hôte :", HoteName, "Nom de la base de données :", nameBdd, "Port :", portBdd, "Nom d'utilisateur :", userName, "Mot de passe :", passe},
                                "Connexion",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, null, null);
                            }
                        }
                        
                       
                        // ENCODAGE DES DONNEES
                        String hashHost = Base64.getEncoder().encodeToString(HoteName.getText().getBytes("UTF-8"));
                        String hashPort = Base64.getEncoder().encodeToString(portBdd.getText().getBytes("UTF-8"));
                        String hashDbname = Base64.getEncoder().encodeToString(nameBdd.getText().getBytes("UTF-8"));
                        String hashUser = Base64.getEncoder().encodeToString(userName.getText().getBytes("UTF-8"));
                        String hashPassword = Base64.getEncoder().encodeToString(passe.getText().getBytes("UTF-8"));
                        
                        // remplissage de l'objet JSON
                        JSONObject j = new JSONObject();
                        j.put("host",  hashHost);
                        j.put("port", hashPort);
                        j.put("dbname", hashDbname);
                        j.put("user", hashUser);
                        j.put("password", hashPassword);
                        
  
                        try(FileWriter fileJson = new FileWriter(filePathAndName)){
                            
                            //System.out.println("Remplissage du fichier de conf ...");
                            
                            fileJson.write(j.toString());
                            fileJson.close();
                            
                            
                            //System.out.println("dossier et fichier conf trouvé ! .......");

                        }catch(IOException e){
                            //e.printStackTrace();
                            
                            JOptionPane.showMessageDialog(null, "Ecriture impossible" + e.getMessage(), "Impossible d'écrire sur le fichier config.json", JOptionPane.ERROR_MESSAGE);
                            
                        }

                    }catch(IOException f){
                        f.printStackTrace();
                        
                        JOptionPane.showMessageDialog(null, "Ecriture impossible" + f.getMessage(), "Impossible de lancer le pocessus de création du fichier de configuration", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }   // FIN !file.exists()
        } // FIN ELSE !folder.exists()
        
        
        
        
        // LECTURE DU FICHIER CREER AVANT
        
        JSONParser parser = new JSONParser();

        try{
            
            FileReader f_read = new FileReader(filePathAndName);
            
            Object obj = parser.parse(f_read);
            JSONObject jsonObject = (JSONObject)obj;

            byte[] decodHost = Base64.getDecoder().decode(jsonObject.get("host").toString());
            byte[] decodPort = Base64.getDecoder().decode(jsonObject.get("port").toString());
            byte[] decodDbname = Base64.getDecoder().decode(jsonObject.get("dbname").toString());
            byte[] decodUser = Base64.getDecoder().decode(jsonObject.get("user").toString());
            byte[] decodPassword = Base64.getDecoder().decode(jsonObject.get("password").toString());
                       
            
            String json_host = new String(decodHost, "UTF-8");
            String json_port = new String(decodPort, "UTF-8");
            String json_dbname = new String(decodDbname, "UTF-8");
            String json_user = new String(decodUser, "UTF-8");
            String json_password = new String(decodPassword, "UTF-8");
            
            
            //System.out.println("Les valeur dans le fichier de conf sont : ");
            //System.out.println("json_host " + json_host);
            //System.out.println("json_port " + json_port);
            //System.out.println("json_dbname " + json_dbname);
           // System.out.println("json_user " + json_user);
            //System.out.println("json_password " + json_password);
            
            f_read.close();
            
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://"+json_host+":"+json_port+"/"+json_dbname;
            
            //System.out.println("URL : " +url);

            Connection connexion  = DriverManager.getConnection(url, json_user, json_password);
            
            
            // recuperation des démarches
            Querry d = new Querry(json_host, Integer.parseInt(json_port), json_dbname, json_user, json_password, "");
            List<String> demarches = new ArrayList<String>();
            
            Iterator it = d.getAllDemarche().entrySet().iterator();

            
	    while (it.hasNext()) {
	        Map.Entry<String, String> val = (Map.Entry)it.next();
	        //System.out.println( " = " + val.getValue().toString());
                demarches.add(val.getValue().toString());  
	    }
            
       
            //System.out.println("\nTout est Ok!\nLancement de l'application...  : ");
            
            
            SwingUtilities.invokeLater(() -> {
                UserFormDialog home = new UserFormDialog(json_host, Integer.parseInt(json_port), json_dbname, json_user, json_password, demarches);
                home.setVisible(true);
                home.setLocationRelativeTo(null);
            });
            
            connexion.close();
            

        }catch(FileNotFoundException exc_file){
            
            //System.out.println(exc_file.getMessage());
            //JOptionPane.showMessageDialog(null, "Impossible de se connecter à la basez de données !\n\nVérifier votre fichier de configuration", "Connexion dans la base de données impossible", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Fichier de configuration introuvable\n\n" + exc_file.getMessage(), "Impossible de trouver la fichier de configuration", JOptionPane.ERROR_MESSAGE);

        }catch(Exception ex){
            
            //System.out.println(ex.getMessage());
            
             if(ex.getMessage().equals("Last unit does not have enough valid bits")){
                 
                try{
                    
                    Files.deleteIfExists(Paths.get(filePathAndName));
                    
                    //System.out.println("Suppression fichier ok");
             
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Suppression impossible" + e.getMessage(), "Impossible de supprimer le fichier de configuration", JOptionPane.ERROR_MESSAGE);
                }

                lancement();
                //System.out.println("oui mitovy ");
            }
             
             if(ex.getMessage().equals("Input byte array has wrong 4-byte ending unit")){

                //System.out.println("oui mitovy ");
            }

            //JOptionPane.showMessageDialog(null, "Impossible de se connecter à la basez de données !\n\nVérifier votre fichier de configuration", "Connexion dans la base de données impossible", JOptionPane.INFORMATION_MESSAGE);

            JOptionPane jop_echec_connexion_bdd = new JOptionPane();
            int option_bdd = jop_echec_connexion_bdd.showConfirmDialog(null, "Une erreur s'est produise lors de la connexion dans la base de données ! ( Connexion impossible ! )\n\n"+ex.getMessage()+"\n\nVoulez-vous lancer le processus de création du fichier de conf ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if(option_bdd == JOptionPane.OK_OPTION){
                
                try{
                    //System.out.println("pahtssss ++++++ = =====  777 : " + Paths.get(filePathAndName));
                        //Files.delete(pathOfConfigFile);

                    ///Files.delete(Paths.get(filePathAndName));

                    Files.deleteIfExists(Paths.get(filePathAndName));
                    lancement();
                    //System.out.println("Suppression fichier ok");
             
                    
                }catch(Exception e){

                }
                
                
            }else{
                System.exit(0);
            }
            
        } 
    }
    
}
