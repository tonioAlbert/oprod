/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.action3saisie;

import com.connectDb.ConnectDb;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 *
 * @author RAP
 */
public class Demande {
    
    
    private String BDD_HOST = "";
    private Integer BDD_PORT;
    private String BDD_DBNAME = "";
    private String BDD_USER = "";
    private String BDD_PWD = "";
    
    PreparedStatement st;
    ResultSet rs;
    private Connection connectDatabase;
    private String login = "";
    private String com = "";
    private         String[][] data;
    
    HashMap <String, String> m = new HashMap <String, String> () ;
    
    public Demande(String HOST, Integer PORT, String DBNAME, String USER, String PWD){
        
        this.BDD_HOST = HOST;
        this.BDD_PORT = PORT;
        this.BDD_DBNAME = DBNAME;
        this.BDD_USER = USER;
        this.BDD_PWD = PWD;
        
        
        connectDatabase = new ConnectDb("127.0.0.1", 5432, "oprod", "2021.", "postgres").getConnection();
        //connectDatabase = new ConnectDb("192.168.88.10", 5432, "oprod", "root963.0", "gaetan").getConnection();
    }
    
    
    public HashMap <String, String> getSaisieParOperateur(String dateDebut, String DateFin, Boolean etatRequette){
        

        if (etatRequette == true) {
            
            //System.out.println("dans la méthode getSaisieParOperateur");
            
            try {

                String sql = "SELECT utilisateur.login, commune.nom AS commune, \n" +
                "             COUNT(*) AS nombre_de_saisie\n" +
                "             FROM commune, \n" +
                    "              fokontany, \n" +
                    "              hameau, \n" +
                    "              demande, utilisateur \n" +
                "              WHERE commune.id_commune::text = fokontany.id_commune::text  \n" +
                "              AND fokontany.id_fokontany::text = hameau.id_fokontany::text \n" +
                "              AND demande.id_hameau::text = hameau.id_hameau::text \n" +
                "              AND demande.demande_user = utilisateur.id_utilisateur \n" +
                "              AND demande.demande_date::TIMESTAMP::DATE = ? \n" +
                "              GROUP BY utilisateur.login, commune.nom";

                
                
                DateFormat dateFormatYMD = new SimpleDateFormat("yyyy/MM/dd");
                String vDateYMD = dateFormatYMD.format(dateDebut);
                
                java.sql.Date date = new java.sql.Date(0000-00-00);
System.out.println("(Req simple) sans param " + date.valueOf(vDateYMD));
                st = connectDatabase.prepareStatement(sql);    
                st.setDate(1, date.valueOf(vDateYMD));

                

                rs = st.executeQuery();
                
System.out.println("SQL avec param : " +sql);

                if (rs.next()){

                    //System.out.println("resultat rs " +rs.getString("commune"));
                    //this.login = rs.getString("login");
                    //p = "kkkk";
                    
                    //m.put("login", rs.getString("operateur"));
                    //m.put("commune", rs.getString("commune")); 
                    //m.put("nombre_de_saisie", rs.getString("nombre_de_saisie")); 

                }else{
                    System.out.println("Impossible de récupérer le saisie des opérateur ! (Req simple) ");
                }
                
                
                                
//System.out.println("Contenu dans 'm' " + m);

                st.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            
        }else{
        
            try {

                String sql2 = "SELECT utilisateur.login, commune.nom AS commune,demande.demande_date::DATE,\n" +
                "    COUNT(*) AS nombre_de_saisie\n" +
                "   FROM commune,\n" +
                "    fokontany,\n" +
                "    hameau,\n" +
                "    demande, utilisateur\n" +
                "  WHERE commune.id_commune::text = fokontany.id_commune::text \n" +
                "				 AND fokontany.id_fokontany::text = hameau.id_fokontany::text \n" +
                "				 AND demande.id_hameau::text = hameau.id_hameau::text\n" +
                "				 AND demande.demande_user = utilisateur.id_utilisateur\n" +
                "				   AND demande.demande_date::TIMESTAMP::DATE BETWEEN '2019/06/21' AND '2019/06/29'\n" +
                "  GROUP BY demande.demande_date::DATE,utilisateur.login, commune.nom";


                st = connectDatabase.prepareStatement(sql2);    
                st.setString(1, dateDebut);
                st.setString(2, DateFin);


                //System.out.println("SQL : " +st);
System.out.println("Req par intervalle) ");

                rs = st.executeQuery();

                if (rs.next()){

                    System.out.println("resultat rs " +rs.getString("commune"));
                    //this.login = rs.getString("login");
                    //p = "kkkk";
                    
                    m.put("login", rs.getString("operateur"));
                    m.put("commune", rs.getString("commune")); 
                    m.put("nombre_de_saisie", rs.getString("nbre_de_saisie")); 

                }else{
                    System.out.println("Impossible de récupérer le saisie des opérateur ! (Req par intervalle) ");
                }
                
                
                
System.out.println("Contenu dans 'm' " + m);

                st.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

       
        return m;
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
            System.out.println(ex.getMessage());;
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
     
    
    
    
    
    
public Boolean getRegistreParcellaireProvisoire(String reg, String dist, String com, String path){
    
    Boolean state = false;
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd 'a' HH'h'mm'Mn'ss'Sec'");
    Date date = new Date(System.currentTimeMillis());
    System.out.println(formatter.format(date));
    
    String realPath = path+"\\"+formatter.format(date)+"-"+reg+"-"+com+".xlsx";
    System.out.println("Anatiny méthide RP....");
            
        
        HashMap<String, String> rp = new HashMap<String, String>();

        try {
            String q = " SELECT commune.nom AS commune,\n" +
            "    demande.id_registre as numero_demande,\n" +
            "    demande.id_parcelle as numero_parcelle,\n" +
            "    persphys.nom,\n" +
            "    persphys.prenom,\n" +
            "	persphys.naissance_date as date_de_naissance,\n" +
            "	persphys.naissance_lieu as lieu_de_naissance,\n" +
            "	persphys.adresse,\n" +
            "    persphys.cni_num as numero_cin,\n" +
            "    persphys.cni_date as date_delivrance_cin,\n" +
            "    persphys.cni_lieu as lieu_delivrance_cin,\n" +
            "    persphys.acn_num as numero_acte_naissance,\n" +
            "    persphys.acn_date as date_delivrance_acte_naissance,\n" +
            "    persphys.acn_lieu as lieu_delivrance_acte_naissance,\n" +
            "	fokontany.nom as fokontany,\n" +
            "	hameau.nom as hameau,\n" +
            "	parcelle_cf.superficie as surface,\n" +
            "	parcelle_cf.coord_x,\n" +
            "	parcelle_cf.coord_y,\n" +
            "	demande.v_nord as nord,\n" +
            "	demande.v_sud as sud,\n" +
            "	demande.v_ouest as ouest,\n" +
            "	demande.v_est as est,\n" +
            "	demande.charges,\n" +
            "    proprietaire_pp.type_demandeur,\n" +
            "    persphys.cin_ok\n" +
            "   FROM \n" +
            "   region,\n" +
            "   district,\n" +
            "   demande,\n" +
            "    hameau,\n" +
            "    fokontany,\n" +
            "    commune,\n" +
            "    parcelle_cf,\n" +
            "    persphys,\n" +
            "    proprietaire_pp\n" +
            "  WHERE \n" +
            "  region.id_region = district.id_region\n" +
            "  AND district.id_district = commune.id_district\n" +
            "  AND demande.id_hameau::text = hameau.id_hameau::text \n" +
            "  AND fokontany.id_fokontany::text = hameau.id_fokontany::text \n" +
            "  AND commune.id_commune::text = fokontany.id_commune::text \n" +
            "  AND demande.id_parcelle::text = parcelle_cf.c_parcelle::text \n" +
            "  AND persphys.id_persphys::text = proprietaire_pp.id_persphys::text \n" +
            "  AND proprietaire_pp.id_demande::text = demande.id_demande::text \n" +
            "  AND demande.val_anomalie IS FALSE \n" +
            "  AND demande.cqi_complet IS TRUE \n" +
            "  AND demande.date_soumission_cqe IS NULL \n" +
            "  AND demande.val_cqe IS null \n" +
            "  AND parcelle_cf.anomalie IS FALSE \n" +
            "  AND parcelle_cf.limitrophe IS FALSE \n" +
            "  AND (demande.date_crl - demande.date_affichage) >= 15\n" +
            "  AND region.nom = ? \n" +
            "  AND district.nom = ? \n" +
            "  AND commune.nom = ? \n" +
            "  ORDER BY demande.num_registre";
            
            
            st = connectDatabase.prepareStatement(q);    
            st.setString(1, reg);
            st.setString(2, dist);
            st.setString(3, com);
        
            
            System.out.println("ma req : " + q);
            
            rs = st.executeQuery();
            
            
            
try{
    XSSFWorkbook excelData = new XSSFWorkbook();
    FileOutputStream out = new FileOutputStream(new File(path));
    XSSFSheet excelSheet = excelData.createSheet("rp_provisoire");

    
    excelData.write(out);
    
                
            out.close();
}catch(Exception createFileErreur){
    System.out.println(createFileErreur.getMessage());
}
 
            while(rs.next()){
                //System.out.println("Méthode get commune : " + rs.getString("code_commune") + " ::  " + rs.getString("commune"));

                rp.put(rs.getString("commune"), rs.getString("numero_demande"));
            }    
            
//excelData.write( new File(path+"\\"+reg+"-"+com+".xlsx"));
            st.close();
            rs.close();

            state = true;
            
            
        } catch (SQLException ex) {
            
            System.out.println(ex.getMessage());
            state = false;
        }
             
        return state;
    }
    
}
