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
    private String demarche = "";
    
    PreparedStatement st;
    ResultSet rs;
    private final Connection connectDatabase;
    private final Connection connectDatabasePLOF;
    
    HashMap <String, String> m = new HashMap <> () ;
    
    List <String[]> valeurDeRetour = new ArrayList<>() ;
    
    
    public Querry(String HOST, Integer PORT, String DBNAME, String USER, String PWD, String operation){
        
        this.BDD_HOST = HOST;
        this.BDD_PORT = PORT;
        this.BDD_DBNAME = DBNAME;
        this.BDD_USER = USER;
        this.BDD_PWD = PWD;
        this.demarche = operation;
        
        connectDatabase = new ConnectDb(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getConnection();
        connectDatabasePLOF = new ConnectDb(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getConnection();
    }
    
    
    
    
    
public List <String[] > getAnomaliesValChefEquipeNULL(String demarche, String[] enTete){
        
        List <String[]> saisies = new ArrayList<>() ;
        
            try {

                String sql = " SELECT demande.id_demande,\n" +
"                   demande.id_registre AS numero_demande,\n" +
"                       CASE\n" +
"                           WHEN demande.type_op::text = 'ogcf'::text THEN left(demande.id_registre::text, 6)\n" +
"                           ELSE right(LEFT(demande.id_registre::text, 8), 6)\n" +
"                       END AS c_commune,\n" +
"                   demande.id_parcelle AS code_parcelle,\n" +
"                       CASE\n" +
"                           WHEN demande.cf_annule IS TRUE THEN 'oui'::text\n" +
"                           WHEN demande.cf_annule IS FALSE THEN 'non'::text\n" +
"                           ELSE 'NULL c-à-d non FALSE et/ou TRUE'::text\n" +
"                       END AS annuler,\n" +
"                       CASE\n" +
"                           WHEN demande.opposition IS TRUE THEN 'oui'::text\n" +
"                           WHEN demande.opposition IS FALSE THEN 'non'::text\n" +
"                           ELSE 'NULL c-à-d non FALSE et/ou TRUE'::text\n" +
"                       END AS opposition,\n" +
"                       CASE\n" +
"                           WHEN demande.avis_crl IS TRUE THEN 'oui'::text\n" +
"                           WHEN demande.avis_crl IS FALSE THEN 'non'::text\n" +
"                           ELSE 'NULL c-à-d non FALSE et/ou TRUE'::text\n" +
"                       END AS avis_crl,\n" +
"                       CASE\n" +
"                           WHEN demande.val_chef_equipe IS TRUE THEN 'oui'::text\n" +
"                           WHEN demande.val_chef_equipe IS FALSE THEN 'non'::text\n" +
"                           ELSE 'NULL c-à-d non FALSE et/ou TRUE'::text\n" +
"                       END AS validation_cqi_1,\n" +
"                       CASE\n" +
"                           WHEN demande.id_certificat IS NULL THEN 'demande_pas_encore_édité'::character varying\n" +
"                           ELSE demande.id_certificat\n" +
"                       END AS deja_imprimer_en_cf,\n" +
"                   demande.type_op AS operation,\n" +
"				   region.nom as region, \n" +
"				   district.nom as district, \n" +
"				   commune.nom as commune, \n" +
"				   fokontany.nom as fokontany, \n" +
"				   hameau.nom as hameau\n" +
"                  FROM demande, region ,district, commune, fokontany , hameau\n" +
"                 WHERE \n" +
"				region.id_region = district.id_region\n" +
"                       AND commune.id_commune::text = fokontany.id_commune::text  \n" +
"                        AND district.id_district = commune.id_district \n" +
"                       AND fokontany.id_fokontany::text = hameau.id_fokontany::text  \n" +
"                       AND demande.id_hameau::text = hameau.id_hameau::text  \n" +
"               AND demande.type_op::text = ? \n" +
"			   AND demande.val_chef_equipe IS NULL\n" +
"                 ORDER BY operation DESC";
            
                st = connectDatabase.prepareStatement(sql); 
                st.setString(1, demarche.toLowerCase());
                rs = st.executeQuery();
                
                int n = 0;

                while(rs.next()){
                    
                    String[] saisie = { rs.getString("operation"), rs.getString("region"), rs.getString("commune") + "  ( " + rs.getString("c_commune") + " )",
                        rs.getString("numero_demande"), rs.getString("code_parcelle"), rs.getString("avis_crl"), rs.getString("opposition"),
                        rs.getString("validation_cqi_1"), rs.getString("annuler") };   
                    
                    saisies.add( saisie);
                    n++;
                }
                
                st.close();
                rs.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, " " + ex.getMessage(), "Excécution requette impossible !", JOptionPane.INFORMATION_MESSAGE);
            } 

        return saisies;
    }
    
    
    
    
    
    

    
    
   public List <String[] > getAnomaliesOPAvisCRLetOpposition(String demarche, String[] enTete){
        
        List <String[]> saisies = new ArrayList<String[]>() ;
        
        
        //System.out.println(" Code commune passé  = " + username);
            
            try {

                String sql = "SELECT DISTINCT \n" +
                "public.decoupage_admin.region,\n" +
                "public.decoupage_admin.commune,\n" +
                "public.\"AnomaliesAvisCrlouOppositionNULL\".id_demande,\n" +
                "public.\"AnomaliesAvisCrlouOppositionNULL\".numero_demande,\n" +
                "public.\"AnomaliesAvisCrlouOppositionNULL\".code_parcelle,\n" +
                "public.\"AnomaliesAvisCrlouOppositionNULL\".c_commune,\n" +
                "public.\"AnomaliesAvisCrlouOppositionNULL\".annuler,\n" +
                "public.\"AnomaliesAvisCrlouOppositionNULL\".opposition,\n" +
                "public.\"AnomaliesAvisCrlouOppositionNULL\".validation_cqi_1,\n" +
                "public.\"AnomaliesAvisCrlouOppositionNULL\".deja_imprimer_en_cf,\n" +
                "public.\"AnomaliesAvisCrlouOppositionNULL\".login_validateur,\n" +
                "public.\"AnomaliesAvisCrlouOppositionNULL\".login_op,\n" +
                "public.\"AnomaliesAvisCrlouOppositionNULL\".lot,\n" +
                        
                "public.\"AnomaliesAvisCrlouOppositionNULL\".operation ,\n" +
                " public.\"AnomaliesAvisCrlouOppositionNULL\".avis_crl \n" +
                " FROM public.\"AnomaliesAvisCrlouOppositionNULL\", public.decoupage_admin\n" +
                "WHERE public.\"AnomaliesAvisCrlouOppositionNULL\".c_commune = public.decoupage_admin.code_commune\n" +
                    " AND public.\"AnomaliesAvisCrlouOppositionNULL\".operation = ? " +
                "ORDER BY public.\"AnomaliesAvisCrlouOppositionNULL\".operation DESC";
            
                st = connectDatabase.prepareStatement(sql); 
                st.setString(1, demarche.toLowerCase());
                rs = st.executeQuery();
                
                int n = 0;

                while(rs.next()){
                    
                    String[] saisie = { rs.getString("operation"), rs.getString("region"), rs.getString("commune") + "  ( " + rs.getString("c_commune") + " )", 
                        rs.getString("numero_demande"), rs.getString("code_parcelle"), rs.getString("avis_crl"), rs.getString("opposition"),rs.getString("annuler"),
                        rs.getString("validation_cqi_1"),rs.getString("login_validateur"), rs.getString("login_op"), rs.getString("lot") };   
                    
                    saisies.add( saisie);
                    n++;
                }
                
                st.close();
                rs.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, " " + ex.getMessage(), "Excécution requette impossible !", JOptionPane.INFORMATION_MESSAGE);
            } 

        return saisies;
    }
    
    
    
    
public List <String[] > getAnomaliesSaisiesOPCategoriesTerrain(String demarche, String[] enTete){
        
        List <String[]> saisies = new ArrayList<String[]>() ;
        
        
        //System.out.println(" Code commune passé  = " + username);
            
            try {

                String sql = "SELECT region.nom as region, commune.nom AS commune, CONCAT(district.code_district , '-', commune.code_commune) AS c_commune,\n" +
"\n" +
"demande.id_registre as numero_demande, demande.id_parcelle as code_parcelle,\n" +
"\n" +
"CASE\n" +
"	WHEN  demande.cf_annule IS TRUE THEN 'oui'\n" +
"    WHEN demande.cf_annule IS FALSE THEN 'non'\n" +
"    ELSE 'non_renseigner'\n" +
"END AS annuler\n" +
",\n" +
" \n" +
"CASE\n" +
"	WHEN  demande.opposition IS TRUE THEN 'oui'\n" +
"    WHEN demande.opposition IS FALSE THEN 'non'\n" +
"    ELSE 'non_renseigner'\n" +
"END AS opposition\n" +
" ,\n" +
" \n" +
"CASE\n" +
"	WHEN  demande.val_chef_equipe IS TRUE THEN 'oui'\n" +
"    WHEN demande.val_chef_equipe IS FALSE THEN 'non'\n" +
"    ELSE 'pas_encore_validé'\n" +
"END AS validation_cqi_1\n" +
" \n" +
"                      FROM region, commune,  \n" +
"                           fokontany, \n" +
"                           hameau,   \n" +
"                           demande, utilisateur , district  \n" +
"                       WHERE\n" +
"                       region.id_region = district.id_region\n" +
"                       AND commune.id_commune::text = fokontany.id_commune::text  \n" +
"                        AND district.id_district = commune.id_district \n" +
"                       AND fokontany.id_fokontany::text = hameau.id_fokontany::text  \n" +
"                       AND demande.id_hameau::text = hameau.id_hameau::text  \n" +
"                       AND demande.demande_user = utilisateur.id_utilisateur \n" +
"                       \n" +
"                        AND demande.cat_autre is false \n" +
"                        AND demande.cat_bois is false \n" +
"                        AND demande.cat_champ is false \n" +
"                        AND demande.cat_cour is false \n" +
"                        AND demande.cat_etable is false \n" +
"                        AND demande.cat_etang is false\n" +
"                        AND demande.cat_riz is false\n" +
"                         \n" +
"                         AND demande.type_op = ?  \n" +
"                       order by region.nom , commune.nom , demande.num_registre ASC";
            
                st = connectDatabase.prepareStatement(sql); 
                st.setString(1, demarche.toLowerCase());

                rs = st.executeQuery();
                
                //System.out.println("REQ = " + st);
                
                int n = 0;

                while(rs.next()){
                    
                    String[] saisie = { rs.getString("region"), rs.getString("commune") + "  ( " + rs.getString("c_commune") + " )", rs.getString("numero_demande"), rs.getString("code_parcelle"), rs.getString("annuler"), rs.getString("opposition"), rs.getString("validation_cqi_1") };   
                    
                    
       System.out.println(rs.getString("region")+ rs.getString("commune") + "  ( " + rs.getString("c_commune") + " )"+ rs.getString("numero_demande"));
  
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
    
    
    
    


public String Insert(String table, String[] colonnes, String[] datas){
    
    String formatColonne = "";
    String formatData = "";
    int tailleTableauColonneTable = colonnes.length;
    String sql = "";
    
  
    sql += "INSERT INTO public."+table+"(";
    
    for (int i = 0; i < tailleTableauColonneTable; i++) {
        
        if (i == tailleTableauColonneTable) {
            sql += colonnes[i];
        }else{
            sql += colonnes[i]+",";
        }

    }
    
    //sql.length();
    
    sql += sql.substring(1, sql.length()-1);
    
    sql += ") VALUES (";
    
    
    for (int i = 0; i < datas.length; i++) {
        
        if (i == datas.length) {
            sql += datas[i];
        }else{
            sql += datas[i]+","; 
        }

    }
    
    sql += sql.substring(1, sql.length()-1);
    
    sql += ");";


    System.out.println("dans la méthode getSaisieParOperateur\n\n" + sql);
        
        String lastValue = "";

 

        return lastValue;
    }
    
    
public String getInfoParam(){
        
        String nomAtelier = "";
        
        try {
            
            String q = "SELECT * FROM param";

            st = connectDatabase.prepareStatement(q);    
            rs = st.executeQuery();
            

            while(rs.next()){      
                //System.out.println("Nom de atelier est : " + rs.getString("valeur"));
                nomAtelier += rs.getString("valeur");
            }    

            st.close();
            rs.close();
            
            
        } catch (SQLException ex) {
                System.out.println("Impossible de récupéré le nom de l'atelier !");
                JOptionPane.showMessageDialog(null, "Impossible de récupéré le nom de l'atelier !","Erreur récupération nom de l'atelier", JOptionPane.INFORMATION_MESSAGE);
        }
    
        return nomAtelier;
    }
    
  
public List <String[] > getSaisieCroise(String reg, String demarche, String critere, JDateChooser dateDebut, JDateChooser DateFin, String lotOrUser){
        
        String filtreOperation ="";
        
                
        if (critere.equals("Par Date")) {
            filtreOperation = " NOT FALSE";
        }
        else if (critere.equals("Par intervale de date")) {
            filtreOperation = " AND demande.demande_date::TIMESTAMP::DATE BETWEEN '"+Formats.convertUtilToSql(dateDebut.getDate())+"' AND '"+Formats.convertUtilToSql(DateFin.getDate())+ "'; ";
        }
        else if (critere.equals("Par Utilisateurs")) {
            filtreOperation = " AND demande.demande_user = "+ "\"; ";
        }
        else {
            filtreOperation = " AND demande.lot = \"" + lotOrUser + "\"; ";
        }
        
        List <String[]> saisies = new ArrayList<String[]>() ;
            
            //System.out.println("dans la méthode getSaisieParOperateur");
            
            try {

                String sql = "select * from demande, persphys, proprietaire_pp, utilisateur \n" +
                " WHERE demande.id_demande = proprietaire_pp.id_demande\n" +
                " AND proprietaire_pp.id_persphys = persphys.id_persphys\n" +
                " AND utilisateur.id_utilisateur = demande.demande_user "+
                "\n AND demande.type_op = ? " + filtreOperation;


                st = connectDatabase.prepareStatement(sql);
                st.setString(1, demarche);
                rs = st.executeQuery();
                
                //System.out.println("REQUETTE EXCECUTER : " + st);
               
            
                                
                int n = 1;

                while(rs.next()){
                    String[] vectos = { rs.getString("id_demande"), rs.getString("id_fiplof"), rs.getString("id_hameau"), rs.getString("num_parcelle") };          
                    saisies.add(vectos);
                    n++; 
                }

                st.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
            } 

        return saisies;
    }    
    
    
public Long getValSequenceTable(String nomSequence){
        
        long lastValue = 0;

            try {

                String sql = "SELECT last_value, increment_by FROM "+ nomSequence;
            
                st = connectDatabasePLOF.prepareStatement(sql);
                rs = st.executeQuery();
                                
                int n = 1;

                while(rs.next()){
                    lastValue = rs.getLong("last_value");  
                    n++; 
                }

                st.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
            } 

        return lastValue;
    }
    
    

    public List <String[] > getVectorisationParCommune(String reg, String demarche){
        
        String filtreOperation ="";
        
                
        if (demarche.equals("ocm")) {
            filtreOperation = " NOT FALSE";
        }else{
            filtreOperation = " NOT TRUE";
        }
        
        List <String[]> saisies = new ArrayList<String[]>() ;
            
            //System.out.println("dans la méthode getSaisieParOperateur");
            
            try {

                String sql = "SELECT region.nom AS region,\n" +
                "    district.nom AS district,\n" +
                "    commune.nom AS commune,\n" +
                "	count(*)\n" +
                " AS vecto\n" +
                "   FROM region,\n" +
                "    district,\n" +
                "    commune,\n" +
                "    parcelle_cf t1\n" +
                "  WHERE region.id_region::text = district.id_region::text\n" +
                "  AND district.id_district::text = commune.id_district::text \n" +
                "  AND t1.c_district::text = district.code_district::text \n" +
                "  AND t1.c_commune::text = commune.code_commune::text \n" +
                "  AND t1.type_op IS "+filtreOperation+"  \n" +
                "    AND region.nom = ? \n" +
                "  GROUP BY region.nom, district.nom, commune.nom\n" +
                "  ORDER BY region.nom, district.nom, commune.nom";
            
                st = connectDatabase.prepareStatement(sql);
                st.setString(1, reg);
                rs = st.executeQuery();
                                
                int n = 1;

                while(rs.next()){
                    String[] vectos = { rs.getString("region"), rs.getString("district"), rs.getString("commune"), rs.getString("vecto") };          
                    saisies.add(vectos);
                    n++; 
                }

                st.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
            } 

        return saisies;
    }
    

    public List <String []> getRpProvisoire(String reg, String c_dist, String dist, String c_com,String com, String path, String operation, String typePersonne){

    String sql = "SELECT demande.id_registre, TO_CHAR(demande.date_demande, 'DD/MM/YYYY') AS date_demande ,\n" +
    "              	CONCAT(persphys.nom,' ',persphys.prenom) AS nom_et_prenom,\n" +
    "                 \n" +
    "              CASE \n" +
    "              	WHEN persphys.d_naiss_approx IS TRUE THEN CONCAT('Vers ', EXTRACT(YEAR FROM persphys.naissance_date))\n" +
    "              	ELSE TO_CHAR(persphys.naissance_date, 'DD/MM/YYYY')\n" +
    "              END AS naissance_date,\n" +
    "              \n" +
    "                 persphys.naissance_lieu,persphys.adresse,\n" +
    "                 \n" +
    "              CASE\n" +
    "              	WHEN     persphys.cni_num IS NULL THEN ''\n" +
    "              	ELSE     persphys.cni_num\n" +
    "              END, \n" +
    "                  TO_CHAR(persphys.cni_date, 'DD/MM/YYYY') AS cni_date , \n" +
    "                  \n" +
    "              CASE\n" +
    "              	WHEN     persphys.cni_lieu IS NULL THEN ''\n" +
    "              	ELSE     persphys.cni_lieu\n" +
    "              END\n" +
    "              ,\n" +
    "              CASE\n" +
    "              	WHEN     persphys.acn_num IS NULL THEN ''\n" +
    "              	ELSE     persphys.acn_num\n" +
    "              END\n" +
    "              , \n" +
    "              TO_CHAR(persphys.acn_date, 'DD/MM/YYYY') AS acn_date \n" +
    "              ,  \n" +
    "              CASE\n" +
    "              	WHEN     persphys.acn_lieu IS NULL THEN ''\n" +
    "              	ELSE     persphys.acn_lieu\n" +
    "              END\n" +
    "              ,\n" +
    "                   region.nom AS region,\n" +
    "                   district.nom AS district,\n" +
    "                   commune.nom AS commune,\n" +
    "                 fokontany.nom AS fokontany,\n" +
    "                 hameau.nom AS hameau,\n" +
    "              	parcelle_cf.superficie,	\n" +
    "                 parcelle_cf.coord_x, \n" +
    "                 parcelle_cf.coord_y,\n" +
    "                 demande.v_nord, \n" +
    "              	demande.v_sud, \n" +
    "                 demande.v_ouest, \n" +
    "                 demande.v_est,\n" +
    "                 TO_CHAR(demande.date_crl, 'DD/MM/YYYY') AS date_crl ,\n" +
    "              CASE\n" +
    "              	WHEN     demande.charges IS NULL THEN ''\n" +
    "              	ELSE     demande.charges\n" +
    "              END\n" +
    "              \n" +
    "              	\n" +
    "                 FROM  public.proprietaire_pp, public.persphys,public.district,\n" +
    "                  public.region,public.commune,public.fokontany,\n" +
    "                   public.hameau,public.demande  \n" +
    "                   INNER JOIN public.parcelle_cf ON public.demande.id_parcelle=public.parcelle_cf.c_parcelle \n" +
    "              	WHERE \n" +
    "                 region.id_region = district.id_region\n" +
    "                 AND district.id_district= commune.id_district \n" +
    "                 AND commune.id_commune=fokontany.id_commune \n" +
    "                 AND fokontany.id_fokontany=hameau.id_fokontany \n" +
    "                 AND demande.id_hameau=hameau.id_hameau \n" +
    "				AND demande.id_parcelle=parcelle_cf.c_parcelle \n" +
    "                 AND demande.id_demande=proprietaire_pp.id_demande \n" +
    "                 AND proprietaire_pp.id_persphys=persphys.id_persphys\n" +
    "              	AND demande.val_anomalie IS FALSE\n" +
    "                 AND parcelle_cf.limitrophe IS FALSE\n" +
    "                 AND parcelle_cf.anomalie IS FALSE\n" +
    "                 AND demande.opposition IS FALSE\n" +
    "                 AND demande.val_chef_equipe IS TRUE\n" +
    "                 AND avis_crl IS TRUE \n" +
    "                 AND demande.cqi_complet IS TRUE \n" +
    "                 AND val_cqe IS NULL \n" +
    "                 AND region.nom = ? \n" +
    "                 AND district.nom = ? \n" +
    "                 AND commune.nom = ? \n" +
    "                 AND demande.type_op = ? \n" +
    "              	AND demande.date_crl-demande.date_demande >= 15  \n" +
    "                 ORDER BY demande.num_registre  ASC";

        if(typePersonne.toLowerCase().equals("morale")){

            sql = "SELECT demande.id_registre, TO_CHAR(demande.date_demande, 'DD/MM/YYYY') AS date_demande , \n" +
            "public.lst_type_pm.valeur AS type_personne_morale, \n" +
            "public.persmor.denomination, \n" +
            "public.persmor.numero_pm AS numero, \n" +
            "TO_CHAR(public.persmor.date_acte, 'DD/MM/YYYY') AS date_creation, \n" +
            "\n" +
            "public.persmor.adresse\n" +
            "           	\n" +
            "              FROM  public.proprietaire_pm, public.persmor,public.district,\n" +
            "               public.region,public.commune,public.fokontany, public.lst_type_pm, \n" +
            "                public.hameau,public.demande  \n" +
            "                INNER JOIN public.parcelle_cf ON public.demande.id_parcelle = public.parcelle_cf.c_parcelle \n" +
            "           	WHERE \n" +
            "              region.id_region = district.id_region\n" +
            "              AND district.id_district= commune.id_district \n" +
            "              AND commune.id_commune=fokontany.id_commune \n" +
            "              AND fokontany.id_fokontany=hameau.id_fokontany \n" +
            "              AND demande.id_hameau=hameau.id_hameau \n" +
            "				AND demande.id_parcelle = parcelle_cf.c_parcelle \n" +
            "              AND demande.id_demande = proprietaire_pm.id_demande \n" +
            "              AND proprietaire_pm.id_persmor = persmor.id_persmor\n" +
            "			  AND persmor.id_type_persmor = lst_type_pm.id_lst_type_pm\n" +
            "           	AND demande.val_anomalie IS FALSE\n" +
            "              AND parcelle_cf.limitrophe IS FALSE\n" +
            "              AND parcelle_cf.anomalie IS FALSE\n" +
            "              AND demande.opposition IS FALSE\n" +
            "              AND demande.val_chef_equipe IS TRUE\n" +
            "              AND avis_crl IS TRUE \n" +
            "              AND demande.cqi_complet IS TRUE \n" +
            "              AND val_cqe IS NULL \n" +
            "              AND region.nom = ? \n" +
            "              AND district.nom = ? \n" +
            "              AND commune.nom = ? \n" +
            "              AND demande.type_op = 'ogcf'\n" +
            "           	AND demande.date_crl-demande.date_demande >= 15  \n" +
            "              ORDER BY demande.num_registre  ASC";
        }





    try {


                st = connectDatabase.prepareStatement(sql);    
                st.setString(1, reg);
                st.setString(2, dist);
                st.setString(3, com);
                st.setString(4, Formats.ConvertOcfmToOcm(operation).toLowerCase());
                rs = st.executeQuery();


                    System.out.println("SQL pret CQE = " + rs );

                    int n = 0;

                    // remise à vide des valeurs de retours valeurDeRetour.clear();
                    valeurDeRetour.clear();


                    if(typePersonne.toLowerCase().equals("morale")){

                        while(rs.next()){

                            String[] traitements_saisies = { rs.getString("id_registre"), rs.getString("date_demande")
                                    , rs.getString("type_personne_morale") , rs.getString("denomination"), rs.getString("numero"), rs.getString("date_creation"), rs.getString("adresse")};          

                            valeurDeRetour.add(traitements_saisies);

                            n++;

                        }

                    }else{
                        while(rs.next()){

                            String[] traitements_saisies = { rs.getString("id_registre"), rs.getString("date_demande")
                                    , rs.getString("type_personne_morale") , rs.getString("denomination"), rs.getString("numero"), rs.getString("date_creation"), rs.getString("adresse")};          

                            valeurDeRetour.add(traitements_saisies);

                            n++;

                        }
                    }




                    st.close();
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
                } 


        return valeurDeRetour;
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

                    String sql = "select region.nom as region, district.nom AS district, commune.nom as commune,count(*) as cf\n" +
                    "from demande, parcelle_cf, district, commune, fokontany, hameau, region\n" +
                    "where demande.id_parcelle = parcelle_cf.c_parcelle\n" +
                    "and demande.id_hameau=hameau.id_hameau\n" +
                    "and district.id_district=commune.id_district\n" +
                    "and hameau.id_fokontany=fokontany.id_fokontany\n" +
                    "and fokontany.id_commune=commune.id_commune\n" +
                    "and demande.val_anomalie=false\n" +
                    "and parcelle_cf.anomalie=false\n" +
                    "and demande.opposition is false\n" +
                    "and district.id_region=region.id_region\n" +
                    "and val_chef_equipe is true\n" +
                    "and demande.opposition is false\n" +
                    "AND (demande.date_crl - demande.date_affichage) >= 15\n" +
                    "AND avis_crl is true\n" +
                    "AND demande.num_certificat IS NULL\n" +
                            
                            
                    "  AND region.nom = ? \n" +
                    "  AND demande.type_op = ? \n" +
                    "  GROUP BY region.nom, district.nom, commune.nom\n" +
                    "  ORDER BY region.nom, district.nom, commune.nom";


                    st = connectDatabase.prepareStatement(sql);    
                    st.setString(1, reg);
                    st.setString(2, operation.toLowerCase());
                    rs = st.executeQuery();

                System.out.println("SAISIE :: " + st);

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

                String sql = "SELECT utilisateur.id_utilisateur as id, utilisateur.login, commune.nom AS commune, CONCAT(district.code_district , '-', commune.code_commune) AS c_commune,\n" +
"                        COUNT(*) AS nb_saisie" +
"                             FROM commune,  \n" +
"                                  fokontany, \n" +
"                                  hameau,   \n" +
"                                  demande, utilisateur , district  \n" +
"                              WHERE commune.id_commune::text = fokontany.id_commune::text  \n" +
"                               AND district.id_district = commune.id_district  \n" +
"                              AND fokontany.id_fokontany::text = hameau.id_fokontany::text  \n" +
"                              AND demande.id_hameau::text = hameau.id_hameau::text  \n" +
"                              AND demande.demande_user = utilisateur.id_utilisateur  \n" +
"                              AND demande.demande_date::TIMESTAMP::DATE = ? \n" +
"							  AND demande.type_op = ? \n" +
"                             GROUP BY utilisateur.id_utilisateur, utilisateur.login, commune.nom, c_commune";
            
            
            //System.out.println("date_sql: " +dateDebut.getDate());
            
                st = connectDatabase.prepareStatement(sql);    
                st.setDate(1, Formats.convertUtilToSql(dateDebut.getDate()));
                st.setString(2, demarche.toLowerCase());

                rs = st.executeQuery();
                
                System.out.println("SQL SAISIE SIMPLE OPERATEUR: " +st);
                
                int n = 0;

                while(rs.next()){
                    
                    String[] saisie = { rs.getString("login"), rs.getString("commune") + "  ( " + rs.getString("c_commune") + " )", rs.getString("nb_saisie") };          
                    
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

                String sql = "SELECT utilisateur.id_utilisateur as id, utilisateur.login, commune.nom AS commune, CONCAT(district.code_district , '-', commune.code_commune) AS c_commune,\n" +
"                        COUNT(*) AS nb_saisie" +
"                         FROM commune,  \n" +
"                              fokontany, \n" +
"                              hameau,   \n" +
"                              demande, utilisateur , district  \n" +
"                          WHERE commune.id_commune::text = fokontany.id_commune::text  \n" +
"                          AND district.id_district = commune.id_district  \n" +
"                          AND fokontany.id_fokontany::text = hameau.id_fokontany::text  \n" +
"                          AND demande.id_hameau::text = hameau.id_hameau::text  \n" +
"                          AND demande.demande_user = utilisateur.id_utilisateur  \n" +
"                          AND demande.demande_date::TIMESTAMP::DATE BETWEEN ? AND ?  \n" +
"						  AND demande.type_op = ?  \n" +
"                          GROUP BY utilisateur.id_utilisateur, utilisateur.login, commune.nom, c_commune";
            
            
            //System.out.println("date_sql: " +dateDebut.getDate());
            
                st = connectDatabase.prepareStatement(sql);    
                st.setDate(1, Formats.convertUtilToSql(dateDebut.getDate()));
                st.setDate(2, Formats.convertUtilToSql(dateFin.getDate()));
                st.setString(3, demarche.toLowerCase());

                rs = st.executeQuery();
                
                //System.out.println("SQL SAISIE BETWEEN TWO DATE OPERATEUR: " +st);
                
                int n = 0;

                while(rs.next()){
                    String[] saisie = { rs.getString("login"), rs.getString("commune") + "  ( " + rs.getString("c_commune") + " )", rs.getString("nb_saisie") };          
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

                String sql = "SELECT utilisateur.id_utilisateur as id, utilisateur.login, commune.nom AS commune, CONCAT(district.code_district , '-', commune.code_commune) AS c_commune,\n" +
"                        COUNT(*) AS nb_saisie\n" +
"                         FROM commune,  \n" +
"                              fokontany, \n" +
"                              hameau,   \n" +
"                              demande, utilisateur , district  \n" +
"                          WHERE commune.id_commune::text = fokontany.id_commune::text  \n" +
"                           AND district.id_district = commune.id_district \n" +
"                          AND fokontany.id_fokontany::text = hameau.id_fokontany::text  \n" +
"                          AND demande.id_hameau::text = hameau.id_hameau::text  \n" +
"                          AND demande.demande_user = utilisateur.id_utilisateur  \n" +
"                          AND utilisateur.login = ?  \n" +
"						  AND demande.type_op = ?  \n" +
"                          GROUP BY utilisateur.id_utilisateur, utilisateur.login, commune.nom, c_commune";
            
                st = connectDatabase.prepareStatement(sql); 
                st.setString(1, username);
                st.setString(2, demarche.toLowerCase());

                rs = st.executeQuery();
                
                int n = 0;

                while(rs.next()){
                    String[] saisie = { rs.getString("login"), rs.getString("commune") + "  ( " + rs.getString("c_commune") + " )", rs.getString("nb_saisie") };          
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
    
    
    
    
public List <String[] > getSaisieWithCommune(String demarche, String username){
        
        List <String[]> saisies = new ArrayList<String[]>() ;
        
        
        //System.out.println(" Code commune passé  = " + username);
            
            try {

                String sql = "SELECT region.nom as region, commune.nom AS commune, CONCAT(district.code_district , '-', commune.code_commune) AS c_commune,\n" +
"                       COUNT(*) AS nb_saisie\n" +
"                        FROM region, commune,  \n" +
"                             fokontany, \n" +
"                             hameau,   \n" +
"                             demande, utilisateur , district  \n" +
"                         WHERE\n" +
"                         region.id_region = district.id_region\n" +
"                         AND commune.id_commune::text = fokontany.id_commune::text  \n" +
"                          AND district.id_district = commune.id_district \n" +
"                         AND fokontany.id_fokontany::text = hameau.id_fokontany::text  \n" +
"                         AND demande.id_hameau::text = hameau.id_hameau::text  \n" +
"                         AND demande.demande_user = utilisateur.id_utilisateur  \n" +
"                           AND commune.nom = ?  \n" +
"                           AND demande.type_op = ?  \n" +
"                       \n" +
"                         GROUP BY region.nom ,district.nom, commune.nom, c_commune\n" +
"                         order by region.nom ASC\n" +
"                         ";
            
                st = connectDatabase.prepareStatement(sql); 
                st.setString(1, username.trim());
                st.setString(2, demarche.toLowerCase());

                rs = st.executeQuery();
                
                //System.out.println("REQ = " + rs);
                
                int n = 0;

                while(rs.next()){
                    String[] saisie = { rs.getString("region"), rs.getString("commune") + "  ( " + rs.getString("c_commune") + " )", rs.getString("nb_saisie") };   
                    
                    
                    System.out.println(rs.getString("region")+ rs.getString("commune") + "  ( " + rs.getString("c_commune") + " )"+ rs.getString("nb_saisie"));
  
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
            

            String q = "";
            
            
            
            if (c.equals("")) {
                
                q = " SELECT DISTINCT region.nom AS region,\n" +
"                  district.code_district AS code_district,\n" +
"                  district.nom AS district,\n" +
"                  commune.code_commune AS code_commune,\n" +
"                  commune.nom AS commune\n" +
"                 FROM district,\n" +
"                  region,\n" +
"                  hameau,\n" +
"                  commune,\n" +
"                  fokontany, demande\n" +
"                WHERE hameau.id_fokontany::text = fokontany.id_fokontany::text \n" +
"                AND commune.id_commune::text = fokontany.id_commune::text \n" +
"                AND district.id_district::text = commune.id_district::text \n" +
"                AND region.id_region::text = district.id_region::text\n" +
"                AND demande.id_hameau = hameau.id_hameau\n" +
"                AND demande.type_op = ? \n" +
"                ORDER BY commune.nom ASC";
                
                st = connectDatabase.prepareStatement(q);    
                st.setString(1,Formats.ConvertOcfmToOcm(this.demarche).toLowerCase());

                rs = st.executeQuery();
            }else{

                q = " SELECT DISTINCT region.nom AS region,\n" +
                "   district.code_district AS code_district,\n" +
                "   district.nom AS district,\n" +
                "   commune.code_commune AS code_commune,\n" +
                "   commune.nom AS commune,\n" +
                "   fokontany.code_fokontany AS code_fkt,\n" +
                "   fokontany.nom AS fkt,\n" +
                "   hameau.code_hameau AS code_hameau,\n" +
                "   hameau.nom AS hameau\n" +
                "  FROM district,\n" +
                "   region,\n" +
                "   hameau,\n" +
                "   commune,\n" +
                "   fokontany, demande\n" +
                " WHERE hameau.id_fokontany::text = fokontany.id_fokontany::text \n" +
                " AND commune.id_commune::text = fokontany.id_commune::text \n" +
                " AND district.id_district::text = commune.id_district::text \n" +
                " AND region.id_region::text = district.id_region::text\n" +
                "AND demande.id_hameau = hameau.id_hameau\n" +
                "AND district.nom = ? \n" +
                "AND demande.type_op = ?\n" +
                "ORDER BY commune.nom ASC";
                
                st = connectDatabase.prepareStatement(q);    
                st.setString(1, c);
                st.setString(2,Formats.ConvertOcfmToOcm(this.demarche).toLowerCase());
                
                rs = st.executeQuery();
            }
            
            //System.out.println("Méthode get commune : " + st);

 
            while(rs.next()){
                //System.out.println("Méthode get commune : " + rs.getString("code_commune") + " ::  " + rs.getString("commune"));

                communes.put(rs.getString("code_district")+"-"+rs.getString("code_commune"), rs.getString("commune")); 
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
    
    public HashMap<String, String> getHameau(String reg, String dist, String com, String fkt){
        
        HashMap<String, String> communes = new HashMap<String, String>();

        try {
            String q = "        \n" +
" SELECT DISTINCT region.nom AS region,\n" +
"    district.code_district AS code_district,\n" +
"    district.nom AS district,\n" +
"    commune.code_commune AS code_commune,\n" +
"    commune.nom AS commune,\n" +
"    fokontany.code_fokontany AS code_fkt,\n" +
"    fokontany.nom AS fkt,\n" +
"    hameau.code_hameau AS code_hameau,\n" +
"    hameau.nom AS hameau\n" +
"   FROM district,\n" +
"    region,\n" +
"    hameau,\n" +
"    commune,\n" +
"    fokontany\n" +
"  WHERE hameau.id_fokontany::text = fokontany.id_fokontany::text \n" +
"  AND commune.id_commune::text = fokontany.id_commune::text \n" +
"  AND district.id_district::text = commune.id_district::text \n" +
"  AND region.id_region::text = district.id_region::text\n" +
"  AND region.nom = ? \n" +
"        AND district.nom = ? \n" +
"        AND commune.nom = ? \n" +
"        AND fokontany.nom = ? \n" +
"  ORDER BY hameau ASC";
            
            
            st = connectDatabase.prepareStatement(q);    
            st.setString(1, reg);
            st.setString(2, dist);
            st.setString(3, com);
            st.setString(4, fkt);
        
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
            //System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Impossible de récupérer les démarches\n\n" + ex.getMessage(), " Récupération impossible !", JOptionPane.INFORMATION_MESSAGE);
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