/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.action3saisie;

import com.connectDb.ConnectDb;
import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



/**
 *
 * @author RAP
 */
public class Querry {
    
    
    private String BDD_HOST = "";
    private Integer BDD_PORT ;
    private String BDD_DBNAME = "";
    private String BDD_USER = "";
    private String BDD_PWD = "";
    
    PreparedStatement st;
    ResultSet rs;
    private final Connection connectDatabase;
    
    HashMap <String, String> m = new HashMap <> () ;
    
    List <String[]> valeurDeRetour = new ArrayList<String[]>() ;
    
    
    public Querry(String HOST, Integer PORT, String DBNAME, String USER, String PWD){
        
        this.BDD_HOST = HOST;
        this.BDD_PORT = PORT;
        this.BDD_DBNAME = DBNAME;
        this.BDD_USER = USER;
        this.BDD_PWD = PWD;
        
        connectDatabase = new ConnectDb(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getConnection();
    }
    
    
    
    
public List <String []> getNombresDossiersPretCQE(String reg, String operation){
 
try {

                String sql = " SELECT region.nom AS region, district.nom as district,\n" +
                "    commune.nom AS commune,\n" +
                "    count(*) AS nombre_de_dossier_pret_cqe\n" +
                "   FROM demande,\n" +
                "    hameau,\n" +
                "    fokontany,\n" +
                "    commune, district, region,\n" +
                "    parcelle_cf\n" +
                "  WHERE demande.id_hameau::text = hameau.id_hameau::text \n" +
                "  AND fokontany.id_fokontany::text = hameau.id_fokontany::text \n" +
                "  AND commune.id_commune::text = fokontany.id_commune::text \n" +
                " AND region.id_region::text = district.id_region::text\n" +
                " AND district.id_district = commune.id_district\n" +
                "  AND demande.id_parcelle::text = parcelle_cf.c_parcelle::text \n" +
                "  AND demande.val_anomalie IS FALSE \n" +
                "  AND demande.cqi_complet IS TRUE\n" +
                "  AND demande.date_soumission_cqe IS NULL \n" +
                "  AND demande.val_cqe IS NULL \n" +
                "  AND parcelle_cf.anomalie IS FALSE \n" +
                "    AND region.nom = ? \n" +
                "  AND demande.type_op = ? \n" +     
                "  AND parcelle_cf.limitrophe IS FALSE \n" +
                "  and demande.avis_crl is TRUE\n" +
                "  AND (demande.date_crl - demande.date_affichage) >= 15\n" +
                "  GROUP BY region.nom ,district.nom , commune.nom";
                
                

            

                st = connectDatabase.prepareStatement(sql);    
                st.setString(1, reg);
                st.setString(2, operation.toLowerCase());
                rs = st.executeQuery();
                
                
                System.out.println("SQL pret CQE = " + rs );
            
                int n = 0;
                
                // remise à vide des valeurs de retours valeurDeRetour.clear();
                valeurDeRetour.clear();

                while(rs.next()){
                    
                    String[] traitements_saisies = { rs.getString("region"), rs.getString("district"), rs.getString("commune") , rs.getString("nombre_de_dossier_pret_cqe")};          
                    
                    valeurDeRetour.add(traitements_saisies);
                    
                    n++;
                    
                }
                

                st.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
            } 
    

    return valeurDeRetour;
    

}
    
public List <String[] > AnomaliesSaisieParCommune(String reg, String operation){
                   
        List <String[]> anomalies = new ArrayList<String[]>() ;

        
            try {

                String sql = "  SELECT DISTINCT region.nom AS region,\n" +
                "    district.nom AS district,\n" +
                "    commune.nom AS commune,\n" +
                "    COUNT(*) AS nbre_anomalie\n" +
                "   FROM commune,\n" +
                "    fokontany,\n" +
                "    hameau,\n" +
                "    demande,\n" +
                "    region,\n" +
                "    district\n" +
                "  WHERE region.id_region::text = district.id_region::text\n" +
                "  AND commune.id_district::text = district.id_district::text \n" +
                "  AND commune.id_commune::text = fokontany.id_commune::text \n" +
                "  AND fokontany.id_fokontany::text = hameau.id_fokontany::text \n" +
                "  AND demande.id_hameau::text = hameau.id_hameau::text \n" +
                "  AND demande.val_anomalie = TRUE\n" +
                "  AND demande.cf_annule IS NOT TRUE\n" +
                "  AND region.nom = ? \n" +
                "  AND demande.type_op = ? \n" +
                "  GROUP BY region.nom, district.nom, commune.nom\n" +
                "  ORDER BY region.nom, district.nom, commune.nom";
            

                st = connectDatabase.prepareStatement(sql);    
                st.setString(1, reg);
                st.setString(2, operation.toLowerCase());
                rs = st.executeQuery();
            
            //System.out.println("SAISIE :: " + sql);
            
                int n = 0;

                while(rs.next()){
                    
                    String[] traitements_saisies = { rs.getString("region"), rs.getString("district"), rs.getString("commune") , rs.getString("nbre_anomalie")};          
                    
                    anomalies.add(traitements_saisies);
                    
                    n++;
                    
                }
                

                st.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
            } 

        return anomalies;
    }
  
    
    
public List <String[] > RapportCfEditableParCommune(String reg, String operation){
                   
        List <String[]> saisies = new ArrayList<String[]>() ;

        
            try {

                String sql = "  SELECT region.nom AS region,\n" +
                "    district.nom AS district,\n" +
                "    commune.nom AS commune,\n" +
                "    count(*) AS cf\n" +
                "   FROM region,\n" +
                "    district,\n" +
                "    commune,\n" +
                "    fokontany,\n" +
                "    hameau,\n" +
                "    demande\n" +
                "     JOIN parcelle_cf ON demande.id_parcelle::text = parcelle_cf.c_parcelle::text\n" +
                "  WHERE demande.val_anomalie = false AND parcelle_cf.limitrophe = false \n" +
                "  AND parcelle_cf.anomalie = false AND demande.val_chef_equipe = true \n" +
                "  AND commune.id_commune::text = fokontany.id_commune::text \n" +
                "  AND fokontany.id_fokontany::text = hameau.id_fokontany::text \n" +
                "  AND commune.id_district::text = district.id_district::text \n" +
                "  AND demande.id_hameau::text = hameau.id_hameau::text \n" +
                "  AND region.id_region::text = district.id_region::text\n" +
                "  AND demande.num_certificat IS NULL \n" +
                "  AND (demande.date_crl - demande.date_demande) >= 15\n" +
                "  AND region.nom = ? \n" +
                "  AND demande.type_op = ? \n" +
                "  GROUP BY region.nom, district.nom, commune.nom\n" +
                "  ORDER BY region.nom, district.nom, commune.nom";
            

                st = connectDatabase.prepareStatement(sql);    
                st.setString(1, reg);
                st.setString(2, operation.toLowerCase());
                rs = st.executeQuery();
            
            //System.out.println("SAISIE :: " + sql);
            
                int n = 0;

                while(rs.next()){
                    
                    String[] traitements_saisies = { rs.getString("region"), rs.getString("district"), rs.getString("commune") , rs.getString("cf")};          
                    
                    saisies.add(traitements_saisies);
                    
                    n++;
                    
                }
                

                st.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
            } 

        return saisies;
    }
    
    
public List <String[] > getRapportSAISIE(String reg, String operation){
                   
        List <String[]> saisies = new ArrayList<String[]>() ;

        
            try {

                String sql = "  SELECT  DISTINCT region.nom AS region,\n" +
                "    district.nom AS district,\n" +
                "    commune.nom AS commune,\n" +
                "    count(*) AS nbre_saisie\n" +
                "   FROM commune,\n" +
                "    fokontany,\n" +
                "    hameau,\n" +
                "    demande,\n" +
                "    region,\n" +
                "    district\n" +
                "  WHERE commune.id_commune::text = fokontany.id_commune::text "+
                "AND commune.id_district::text = district.id_district::text "+
                "AND region.id_region::text = district.id_region::text\n" +
                "AND fokontany.id_fokontany::text = hameau.id_fokontany::text "+
                "AND demande.id_hameau::text = hameau.id_hameau::text "+
                "AND region.nom = ? \n" +
                "AND demande.type_op = ? \n" +
                "  GROUP BY region.nom, district.nom, commune.nom\n" +
                "  ORDER BY region.nom, district.nom, commune.nom";
            

                st = connectDatabase.prepareStatement(sql);    
                st.setString(1, reg);
                st.setString(2, operation.toLowerCase());
                rs = st.executeQuery();
            
            System.out.println("SAISIE :: " + sql);
            
                int n = 0;

                while(rs.next()){
                    
                    String[] traitements_saisies = { rs.getString("region"), rs.getString("district"), rs.getString("commune") , rs.getString("nbre_saisie")};          
                    
                    saisies.add(traitements_saisies);
                    
                    n++;
                    
                }
                

                st.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
            } 

        return saisies;
    }
    

    
    public List <String[] > getRapportSIG(String reg, String operation){
        
        String newFilterQuerry = "";
        
        if(operation.equals("OGCF")){
            newFilterQuerry = "AND t1.type_op IS NOT TRUE";
        }else{
            newFilterQuerry = "AND t1.type_op IS TRUE";
        }
        
        
        List <String[]> sig = new ArrayList<String[]>() ;

        
            try {

                String sql = " SELECT region.nom AS region,\n" +
                "    district.nom AS district,\n" +
                "    commune.nom AS commune,\n" +
                "    sum(\n" +
                "        CASE\n" +
                "            WHEN t1.limitrophe = false THEN 1\n" +
                "            ELSE 0\n" +
                "        END) AS non_acheval,\n" +
                "    sum(\n" +
                "        CASE\n" +
                "            WHEN (t1.observation::text ~~ '02%'::text OR t1.observation::text ~~ '03%'::text OR t1.observation::text ~~ '04%'::text OR t1.observation::text ~~ '05%'::text OR t1.observation::text ~~ '06%'::text OR t1.observation::text ~~ '07%'::text OR t1.observation::text ~~ '08%'::text OR t1.observation::text ~~ '09%'::text OR t1.observation::text ~~ '11%'::text OR t1.observation::text ~~ '21%'::text OR t1.observation::text ~~ '22%'::text OR t1.observation::text ~~ 'Mb%'::text) AND NOT (t1.observation::text ~~ '%12%'::text OR t1.observation::text ~~ '%13%'::text OR t1.observation::text ~~ '%14%'::text OR t1.observation::text ~~ '%15%'::text OR t1.observation::text ~~ '%16%'::text OR t1.observation::text ~~ '%17%'::text OR t1.observation::text ~~ '%18%'::text OR t1.observation::text ~~ '%20%'::text) AND t1.limitrophe = false THEN 1\n" +
                "            ELSE 0\n" +
                "        END) AS rl_anomalie\n" +
                "   FROM region,\n" +
                "    district,\n" +
                "    commune,\n" +
                "    parcelle_cf t1\n" +
                "  WHERE region.id_region::text = district.id_region::text \n" +
                "  AND district.id_district::text = commune.id_district::text \n" +
                "  AND t1.c_district::text = district.code_district::text \n" +
                "  AND t1.c_commune::text = commune.code_commune::text \n" + newFilterQuerry +
                "  AND region.nom = ?\n" +
                "  GROUP BY region.nom, district.nom, commune.nom\n" +
                "  ORDER BY region.nom, district.nom, commune.nom";
            

                st = connectDatabase.prepareStatement(sql);    
                st.setString(1, reg);
                rs = st.executeQuery();
            
            System.out.println("RSIG :: " + sql);
            
                int n = 0;

                while(rs.next()){
                    
                    String[] traitements_sig = { rs.getString("region"), rs.getString("district"), rs.getString("commune") , rs.getString("non_acheval") , rs.getString("rl_anomalie") };          
                    
                    sig.add(traitements_sig);
                    
                    n++;
                    
                }
                

                st.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
            } 

        return sig;
    }
    
    
  
    public List <String[] > getSimpleSaisieParOperateur(JDateChooser dateDebut, String demarche){
        
        List <String[]> saisies = new ArrayList<String[]>() ;
            
            //System.out.println("dans la méthode getSaisieParOperateur");
            
            try {

                String sql = "SELECT utilisateur.id_utilisateur as id, utilisateur.login, commune.nom AS commune,   \n" +
"                             COUNT(*) AS nb_saisie\n" +
"                             FROM commune,  \n" +
"                                  fokontany, \n" +
"                                  hameau,   \n" +
"                                  demande, utilisateur  \n" +
"                              WHERE commune.id_commune::text = fokontany.id_commune::text  \n" +
"                              AND fokontany.id_fokontany::text = hameau.id_fokontany::text  \n" +
"                              AND demande.id_hameau::text = hameau.id_hameau::text  \n" +
"                              AND demande.demande_user = utilisateur.id_utilisateur  \n" +
"                              AND demande.demande_date::TIMESTAMP::DATE = ? \n" +
"							  AND demande.type_op = ? \n" +
"                              GROUP BY utilisateur.id_utilisateur, utilisateur.login, commune.nom";
            
            
            //System.out.println("date_sql: " +dateDebut.getDate());
            
                st = connectDatabase.prepareStatement(sql);    
                st.setDate(1, Formats.convertUtilToSql(dateDebut.getDate()));
                st.setString(2, demarche.toLowerCase());

                rs = st.executeQuery();
                
                System.out.println("SQL SAISIE SIMPLE OPERATEUR: " +st);
                
                int n = 0;

                while(rs.next()){
                    
                    String[] saisie = { rs.getString("login"), rs.getString("commune"), rs.getString("nb_saisie") };          
                    
                    saisies.add( saisie);
                    
                    n++;
                    
                }
                

                st.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
            } 

        return saisies;
    }
    
    
    
    public List <String[] > getSaisieParOperateurBetweenTwoDate(JDateChooser dateDebut, JDateChooser dateFin, String demarche){
        
        List <String[]> saisies = new ArrayList<String[]>() ;
            
            //System.out.println("dans la méthode getSaisieParOperateur");
            
            try {

                String sql = "SELECT utilisateur.id_utilisateur as id, utilisateur.login, commune.nom AS commune,   \n" +
"                         COUNT(*) AS nb_saisie\n" +
"                         FROM commune,  \n" +
"                              fokontany, \n" +
"                              hameau,   \n" +
"                              demande, utilisateur  \n" +
"                          WHERE commune.id_commune::text = fokontany.id_commune::text  \n" +
"                          AND fokontany.id_fokontany::text = hameau.id_fokontany::text  \n" +
"                          AND demande.id_hameau::text = hameau.id_hameau::text  \n" +
"                          AND demande.demande_user = utilisateur.id_utilisateur  \n" +
"                          AND demande.demande_date::TIMESTAMP::DATE BETWEEN ? AND ?  \n" +
"						  AND demande.type_op = ?  \n" +
"                          GROUP BY utilisateur.id_utilisateur, utilisateur.login, commune.nom";
            
            
            //System.out.println("date_sql: " +dateDebut.getDate());
            
                st = connectDatabase.prepareStatement(sql);    
                st.setDate(1, Formats.convertUtilToSql(dateDebut.getDate()));
                st.setDate(2, Formats.convertUtilToSql(dateFin.getDate()));
                st.setString(3, demarche.toLowerCase());

                rs = st.executeQuery();
                
                System.out.println("SQL SAISIE BETWEEN TWO DATE OPERATEUR: " +st);
                
                int n = 0;

                while(rs.next()){
                    String[] saisie = { rs.getString("login"), rs.getString("commune"), rs.getString("nb_saisie") };          
                    saisies.add( saisie);
                    n++;
                }
                

                st.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
            } 

        return saisies;
    }
    
    

    public List <String[] > getSaisieWithLogin(String demarche, String username){
        
        List <String[]> saisies = new ArrayList<String[]>() ;
            
            try {

                String sql = "SELECT utilisateur.id_utilisateur as id, utilisateur.login, commune.nom AS commune,   \n" +
"                         COUNT(*) AS nb_saisie\n" +
"                         FROM commune,  \n" +
"                              fokontany, \n" +
"                              hameau,   \n" +
"                              demande, utilisateur  \n" +
"                          WHERE commune.id_commune::text = fokontany.id_commune::text  \n" +
"                          AND fokontany.id_fokontany::text = hameau.id_fokontany::text  \n" +
"                          AND demande.id_hameau::text = hameau.id_hameau::text  \n" +
"                          AND demande.demande_user = utilisateur.id_utilisateur  \n" +
"                          AND utilisateur.login = ?  \n" +
"						  AND demande.type_op = ?  \n" +
"                          GROUP BY utilisateur.id_utilisateur, utilisateur.login, commune.nom";
            
                st = connectDatabase.prepareStatement(sql); 
                st.setString(1, username);
                st.setString(2, demarche.toLowerCase());

                rs = st.executeQuery();
                
                int n = 0;

                while(rs.next()){
                    String[] saisie = { rs.getString("login"), rs.getString("commune"), rs.getString("nb_saisie") };          
                    saisies.add( saisie);
                    n++;
                }
                

                st.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
            } 

        return saisies;
    }
    
    
      
    public HashMap<String, String> getRegions(String r){
        
        HashMap<String, String> regions = new HashMap<String, String>();
        
        try {
            String q = "SELECT district.code_district, district.nom as district\n" +
            "FROM region, district \n" +
            "WHERE district.id_region = region.id_region\n" +
            "AND region.nom = ? ORDER BY district ASC";
            
            
            st = connectDatabase.prepareStatement(q);    
            st.setString(1, r);
        
            rs = st.executeQuery();
 
            while(rs.next()){
                regions.put(rs.getString("code_district"), rs.getString("district")); 
            }    

            st.close();
            rs.close();
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
             
        return regions;
    }

  
    public HashMap<String, String> getCommunes(String c){
        
        HashMap<String, String> communes = new HashMap<String, String>();

        try {
            String q = "SELECT commune.code_commune, commune.nom as commune\n" +
            "FROM district, commune \n" +
            "WHERE commune.id_district = district.id_district\n" +
            "AND district.nom = ? ORDER BY commune ASC";
            
            
            st = connectDatabase.prepareStatement(q);    
            st.setString(1, c);
        
            rs = st.executeQuery();
 
            while(rs.next()){
                //System.out.println("Méthode get commune : " + rs.getString("code_commune") + " ::  " + rs.getString("commune"));

                communes.put(rs.getString("code_commune"), rs.getString("commune")); 
            }    

            st.close();
            rs.close();
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
             
        return communes;
    }
    

    public HashMap<String, String> getFokontany(String c){
        
        HashMap<String, String> communes = new HashMap<String, String>();

        try {
            String q = "SELECT fokontany.code_fokontany, fokontany.nom as fokontany\n" +
"            FROM commune, fokontany\n" +
"            WHERE fokontany.id_commune = commune.id_commune\n" +
"             AND commune.nom = ? ORDER BY fokontany ASC";
            
            
            st = connectDatabase.prepareStatement(q);    
            st.setString(1, c);
        
            rs = st.executeQuery();
 
            while(rs.next()){
                communes.put(rs.getString("code_fokontany"), rs.getString("fokontany")); 
            }    

            st.close();
            rs.close();
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
             
        return communes;
    }
    
    public HashMap<String, String> getHameau(String c){
        
        HashMap<String, String> communes = new HashMap<String, String>();

        try {
            String q = "SELECT hameau.code_hameau, hameau.nom as hameau\n" +
"            FROM fokontany, hameau\n" +
"            WHERE hameau.id_fokontany = fokontany.id_fokontany\n" +
"             AND fokontany.nom = ? ORDER BY hameau ASC";
            
            
            st = connectDatabase.prepareStatement(q);    
            st.setString(1, c);
        
            rs = st.executeQuery();
 
            while(rs.next()){
                communes.put(rs.getString("code_hameau"), rs.getString("hameau")); 
            }    

            st.close();
            rs.close();
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
             
        return communes;
    } 
    
    
    public HashMap<String, String> getAllDemarche(){
        
        HashMap<String, String> demarches = new HashMap<String, String>();

        try {
            String q = "SELECT id_type_op, abrev_type FROM public.type_op\n" +
            "order by id_type_op ASC";
            
            st = connectDatabase.prepareStatement(q);          
            rs = st.executeQuery();
 
            while(rs.next()){
                demarches.put(rs.getString("id_type_op"), rs.getString("abrev_type")); 
            }    

            st.close();
            rs.close();
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
             
        return demarches;
    } 
     
      
    public HashMap<String, String> getAllUsers(){
        
        HashMap<String, String> utilisateurs = new HashMap<String, String>();
        
        try {
            String q = "SELECT id_utilisateur, login FROM utilisateur\n" +
            "ORDER BY login ASC";

            st = connectDatabase.prepareStatement(q);    
            rs = st.executeQuery();
            
            System.out.println("RETOUT GETS ALL USER : " + rs);
 
            while(rs.next()){
                utilisateurs.put(rs.getString("id_utilisateur"), rs.getString("login")); 
            }    

            st.close();
            rs.close();
 
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
             
        return utilisateurs;
    }

    
    public String getNomAtelier(){
        
        String nomAtelier = "";
        String newNameAtelier = "";
        
        try {
            
            String q = "SELECT * FROM param";

            st = connectDatabase.prepareStatement(q);    
            rs = st.executeQuery();
 
            while(rs.next()){      
                //System.out.println("Nom de atelier est : " + rs.getString("valeur"));
                nomAtelier = rs.getString("valeur");
            }    

            st.close();
            rs.close();
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        //System.out.println("OUF nomAtelier : "+ nomAtelier);
        switch (nomAtelier){
            case "T":
                newNameAtelier = "ATS";
                break;
            case "A":
                newNameAtelier = "VAK";
                break;
            case "G":
                newNameAtelier = "AMA";
                break;
            default:

                System.out.println("Impossible de récupéré le nom de l'atelier !");
                JOptionPane.showMessageDialog(null, "Impossible de récupéré le nom de l'atelier !","Erreur récupération nom de l'atelier", JOptionPane.INFORMATION_MESSAGE);
                break;   
                
        }

                    
             
        return newNameAtelier;
    }
    

    public List <String[] > getDistinctDateEditionCF(String reg, String dist, String com, String demarche){
        
        List <String[]> dateEditionCF = new ArrayList<String[]>() ;
            
            try {

                String sql = "SELECT DISTINCT TO_CHAR(demande.val_cf_edit_date, 'DD / MM / YYYY') AS date_edition \n" +
                "FROM \n" +
                "public.demande, region, district, commune, fokontany, hameau\n" +
                "WHERE region.id_region::text = district.id_region::text\n" +
                "AND district.id_district::text = commune.id_district::text\n" +
                "AND commune.id_commune::text = fokontany.id_commune::text\n" +
                "AND fokontany.id_fokontany::text = hameau.id_fokontany::text\n" +
                "AND hameau.id_hameau::text = demande.id_hameau::text\n" +
                "AND demande.val_cf_edit_date IS NOT NULL\n" +
                "AND region. nom = ?\n" +
                "AND district.nom = ?\n" +
                "AND commune.nom = ?\n" +
                "AND demande.type_op = ?\n" +
                "ORDER BY date_edition ASC";
            
                st = connectDatabase.prepareStatement(sql);
                st.setString(1, reg);
                st.setString(2, dist);
                st.setString(3, com);
                st.setString(4, demarche);
                rs = st.executeQuery();
                
                while(rs.next()){
                    String[] saisie = { rs.getString("date_edition")};          
                    dateEditionCF.add( saisie);
                }
                

                st.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);

                JOptionPane.showMessageDialog(null, ex.getMessage(),"Erreur de récupération des dates édition CF (getDistinctDateEditionCF) ", JOptionPane.INFORMATION_MESSAGE);
            } 

        return dateEditionCF;
    }
    
    

}  // FIN DE LA CLASSE