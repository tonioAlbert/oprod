/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.export.action3saisie;


import com.classes.action3saisie.Demande;
import com.connectDb.ConnectDb;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.classes.action3saisie.Formats;
import java.awt.Desktop;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author RAP
 */
public class Exports {
    
    private String BDD_HOST = "";
    private Integer BDD_PORT;
    private String BDD_DBNAME = "";
    private String BDD_USER = "";
    private String BDD_PWD = "";
    private String TYPE_OPERATION= "";
    private String op = "";
    PreparedStatement st;
    ResultSet rs;
    Connection connectDatabase;
    

    public Exports(String HOST, String DBNAME, Integer PORT, String USER, String PWD, String TYPE_OPERATION) {
        
        this.BDD_HOST = HOST;
        this.BDD_PORT = PORT;
        this.BDD_DBNAME = DBNAME;
        this.BDD_USER = USER;
        this.BDD_PWD = PWD;
        

        this.TYPE_OPERATION = TYPE_OPERATION;
        //connectDatabase = new ConnectDb("192.168.88.10", 5432, "oprod", "C@seF&Ge0X2", "postgres").getConnection();
        connectDatabase = new ConnectDb(this.BDD_HOST, this.BDD_DBNAME, this.BDD_PORT, this.BDD_PWD, this.BDD_USER).getConnection();
    }
    
    
    
public List<Demande> getRegistreParcellaireProvisoire(String reg, String dist, String com, String path){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_'a'_HH'h'mm'mn'ss'sec'");
        Date date = new Date(System.currentTimeMillis());
        //System.out.println(formatter.format(date));
        String realPath = path+"\\"+"RP_prov_"+formatter.format(date)+"_Region_"+reg+"_Commune_"+com+".xlsx";
        List<Demande> demandes = new ArrayList<>();
        
        int RowResultSet = 0;
        int CellResultSet = 28;
        int incr = 0;
        
        
        HashMap<String, String> rp = new HashMap<String, String>();
          
        
        try {
            
            String sql = " SELECT commune.nom AS commune,\n" +
            "    demande.id_registre as numero_demande,\n" +
            "    demande.id_parcelle as numero_parcelle,\n" +
            "    demande.date_demande as date_demande,\n" +
            "    demande.date_crl as date_rl,\n" +
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
            "  AND demande.val_cqe IS NULL \n" +
            "  AND parcelle_cf.anomalie IS FALSE \n" +
            "  AND parcelle_cf.limitrophe IS FALSE \n" +
            "  AND (demande.date_crl - demande.date_affichage) >= 15\n" +
            "  AND region.nom = ? \n" +
            "  AND district.nom = ? \n" +
            "  AND commune.nom = ? \n" +
            "  ORDER BY demande.num_registre";
            
            st = connectDatabase.prepareStatement(sql);    
            st.setString(1, reg);
            st.setString(2, dist);
            st.setString(3, com);
            
           
        
            rs = st.executeQuery();
            
 //System.out.println("Ma requette est de : " + sql);
            while (rs.next()) {
                RowResultSet++;                 
            }
            
            
            if (RowResultSet < 1) {
                JOptionPane.showMessageDialog(null, "Impossible d'exporter le registre parcellaire provisoir car aucun dossier est prêt CQE sur la commune "+com,"Export Registre Parcellaire Provisoire Impossible", JOptionPane.INFORMATION_MESSAGE);

            }else{
                
                try{
                    
                    // CREATION DU FICHIER
                    String nameOfSheet = "rp_provisoire";
                    XSSFWorkbook excelDataCreate = new XSSFWorkbook();
                    FileOutputStream out = new FileOutputStream(new File(realPath));
                    XSSFSheet excelSheet = excelDataCreate.createSheet(nameOfSheet);

                    excelDataCreate.write(out);
                    out.close();
                    
                    
                    // REMPLISSAGE DANS LE FICHIER
                    System.out.print("kakakka path : "+realPath);
                    File src = new File(realPath);
                    FileInputStream fis = new FileInputStream(src);
                    XSSFWorkbook  wb = new XSSFWorkbook(fis);
                    XSSFSheet sheet = wb.getSheet(nameOfSheet);
                    
                    
                    sheet.createRow(0).createCell(0).setCellValue("REGION");
                    sheet.getRow(0).createCell(1).setCellValue("DISTRICT");
                    sheet.getRow(0).createCell(2).setCellValue("COMMUNE");
                    
                    sheet.createRow(1).createCell(0).setCellValue(reg);
                    sheet.getRow(1).createCell(1).setCellValue(dist);
                    sheet.getRow(1).createCell(2).setCellValue(com);
                    
            while (rs.next()) {
                
                //incr++;
                
                for(int a=0; a < CellResultSet; a++){
                    sheet.createRow(incr+++4).createCell(CellResultSet).setCellValue(rs.getString("numero_demande"));
                }

                //System.out.println("Méthode get commune : " + rs.getString("commune") + " ::  " + rs.getString("numero_demande") 
                 //       + " type_demandeur = " +rs.getString("type_demandeur") + " cin ok ? = " + rs.getString("cin_ok")+ " charges = " + rs.getString("charges"));

                //rp.put(rs.getString("commune"), rs.getString("numero_demande"));
                 
            }
                    
                    FileOutputStream fout = new FileOutputStream(src);
                    
                    wb.write(fout);
                    wb.close();


                }catch(Exception createFileErreur){

                    System.out.println(createFileErreur.getMessage());
                }
   
            }
            

            
            rs.close();
            st.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
        
        //System.out.println(demandes);
        return demandes;
    }
    
    
    
    
    
public List<String> getRegistreAnomalie(String reg, String c_dist, String dist, String c_com,String com, String c_fkt, String fkt, String c_hameau, String hameau, String path){
        
        List retour = new ArrayList();
        
        if(this.TYPE_OPERATION.equals("OCM")){
            this.op = "OCFM";
        }else{
            this.op = "OGCF";
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_'a'_HH'h'mm'mn'ss'sec'");
        Date date = new Date(System.currentTimeMillis());
        
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateAujourdhui = dateFormat.format(new Date());
    
    
        String realPath = path+"\\"+this.op+"_RegAnomalieSaisie_"+formatter.format(date)+"_Reg_"+Formats.ConvertSlashToUnderscore(reg)+"_Com_"+Formats.ConvertSlashToUnderscore(com)+"_FKT_"+Formats.ConvertSlashToUnderscore(fkt)+"_Ham_"+Formats.ConvertSlashToUnderscore(hameau)+".xls";
        List<Demande> demandes = new ArrayList<>();
        
        int RowResultSet = 0;
        
        try {
            
            String sql = "  SELECT region.nom AS region,\n" +
            "    commune.code_commune,\n" +
            "    commune.nom AS commune,\n" +
            "    fokontany.code_fokontany,\n" +
            "    fokontany.nom AS fokontany,\n" +
            "    hameau.code_hameau,\n" +
            "    hameau.nom AS hameau,\n" +
            "    demande.code_equipe,\n" +
            "    demande.num_registre ,\n" +
            "    demande.id_registre as numero_demande,\n" +
            "    demande.id_parcelle,\n" +
            "    persphys.nom AS nom_demandeur,\n" +
            "    persphys.prenom AS prenom_demandeur,\n" +
            "    anomalie.description AS anomalie_description,\n" +
            "    utilisateur.login,\n" +
            "    demande.lot,\n" +
            "    demande.planche_plof\n" +
            "   FROM hameau,\n" +
            "    anomalie,\n" +
            "    demande,\n" +
            "    utilisateur,\n" +
            "    lst_type_anomalie,\n" +
            "    fokontany,\n" +
            "    commune,\n" +
            "    district,\n" +
            "    region,\n" +
            "    proprietaire_pp,\n" +
            "    persphys\n" +
            "  WHERE hameau.id_hameau::text = demande.id_hameau::text \n" +
            "  AND demande.id_demande::text = anomalie.id_demande::text \n" +
            "  AND demande.demande_user::text = utilisateur.id_utilisateur::text \n" +
            "  AND fokontany.id_fokontany::text = hameau.id_fokontany::text \n" +
            "  AND commune.id_commune::text = fokontany.id_commune::text \n" +
            "  AND district.id_district::text = commune.id_district::text \n" +
            "  AND region.id_region::text = district.id_region::text \n" +
            "  AND lst_type_anomalie.id_lst_type_anomalie::text = anomalie.id_lst_type_anomalie::text \n" +
            "  AND proprietaire_pp.id_demande::text = demande.id_demande::text \n" +
            "  AND proprietaire_pp.id_persphys::text = persphys.id_persphys::text \n" +
            "  AND proprietaire_pp.type_demandeur::text = 'DM'::text \n" +
            "  AND demande.val_anomalie IS TRUE \n" +
            "  AND anomalie.resolu IS FALSE \n" +
            "  AND demande.cf_annule IS NOT TRUE\n" +
            "  AND region.nom = ? \n" +
            "  AND district.nom = ? \n" +
            "  AND commune.nom = ? \n" +
            "  AND fokontany.nom = ? \n" +
            "  AND hameau.nom = ? \n" +
            "  AND demande.type_op = ? \n" +
            "  ORDER BY demande.num_registre";
            
            st = connectDatabase.prepareStatement(sql);    
            st.setString(1, reg);
            st.setString(2, dist);
            st.setString(3, com);
            st.setString(4, fkt);
            st.setString(5, hameau);
            st.setString(6, this.TYPE_OPERATION.toLowerCase());
            
            
            rs = st.executeQuery();
            
            System.out.println(st);
            
                try{
                    
                    // CREATION DU FICHIER
                    String nameOfSheet = "reg_anomalie";
                    XSSFWorkbook excelDataCreate = new XSSFWorkbook();
                    FileOutputStream out = new FileOutputStream(new File(realPath));
                    XSSFSheet excelSheet = excelDataCreate.createSheet(nameOfSheet);

                    
                    excelDataCreate.write(out);
                    out.close();
                    
                    // REMPLISSAGE DANS LE FICHIER
                    File src = new File(realPath);
                    FileInputStream fis = new FileInputStream(src);
                    XSSFWorkbook  wb = new XSSFWorkbook(fis);
                    XSSFSheet sheet = wb.getSheet(nameOfSheet);
                    
                    
                    //sheet.createRow(0).createCell(0).setCellValue("Commune :");
                    //sheet.getRow(0).createCell(1).setCellValue(com);

            Row headerRow0 = sheet.createRow(0);


            XSSFCellStyle cellStyleBold = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            cellStyleBold.setAlignment(HorizontalAlignment.CENTER);
            cellStyleBold.setFont(headerFont);
        
        
            cellStyleBold.setBorderBottom(BorderStyle.THIN);  
            cellStyleBold.setBottomBorderColor(IndexedColors.BLACK.getIndex()); 
            
            cellStyleBold.setBorderRight(BorderStyle.THIN);  
            cellStyleBold.setRightBorderColor(IndexedColors.BLACK.getIndex());  
            
            cellStyleBold.setBorderTop(BorderStyle.THIN);  
            cellStyleBold.setTopBorderColor(IndexedColors.BLACK.getIndex()); 

        
            cellStyleBold.setBorderLeft(BorderStyle.THIN);  
            cellStyleBold.setLeftBorderColor(IndexedColors.BLACK.getIndex()); 
            
            Cell headerCell0 = headerRow0.createCell(0);
            headerCell0.setCellValue("Commune :");
            headerCell0.setCellStyle(cellStyleBold);


            headerCell0 = headerRow0.createCell(1);
            headerCell0.setCellValue(com);

 
            headerCell0 = headerRow0.createCell(2);
            headerCell0.setCellValue("Code Com : " + c_com);

            //headerCell0 = headerRow0.createCell(3);
            //headerCell0.setCellValue(c_com);

            headerCell0 = headerRow0.createCell(4);
            headerCell0.setCellValue("Fokontany :");
            headerCell0.setCellStyle(cellStyleBold);
        

            headerCell0 = headerRow0.createCell(5);
            headerCell0.setCellValue(fkt);   

            headerCell0 = headerRow0.createCell(6);
            headerCell0.setCellValue("Code FKT :");
            headerCell0.setCellStyle(cellStyleBold);

            headerCell0 = headerRow0.createCell(7);
            headerCell0.setCellValue(c_fkt);

            headerCell0 = headerRow0.createCell(8);
            headerCell0.setCellValue("N° Equipe :");
            headerCell0.setCellStyle(cellStyleBold);

            //headerCell0 = headerRow0.createCell(9);
            //headerCell0.setCellValue("xxxxx n° equipe");


            Row headerRow1 = sheet.createRow(1);

            Cell headerCell1 = headerRow1.createCell(0);
            headerCell1.setCellValue("Hameau :");
            headerCell1.setCellStyle(cellStyleBold);


            headerCell1 = headerRow1.createCell(1);
            headerCell1.setCellValue(hameau);

            headerCell1 = headerRow1.createCell(2);
            headerCell1.setCellValue("Code Ham : " + (String) c_hameau);

            //headerCell1 = headerRow1.createCell(3);
            //headerCell1.setCellValue(c_hameau);

            headerCell1 = headerRow1.createCell(4);
            headerCell1.setCellValue("Atelier :");
            headerCell1.setCellStyle(cellStyleBold);


            headerCell1 = headerRow1.createCell(5);
            headerCell1.setCellValue("Nom de l'atelier");   

            headerCell1 = headerRow1.createCell(6);
            headerCell1.setCellValue("Date d’envoi :");
            headerCell1.setCellStyle(cellStyleBold);

        
        
            headerCell1 = headerRow1.createCell(7);
            headerCell1.setCellValue(dateAujourdhui);

            headerCell1 = headerRow1.createCell(8);
            headerCell1.setCellValue("Date de retour :");
            headerCell1.setCellStyle(cellStyleBold);


            Row headerRow4 = sheet.createRow(4);

            Cell headerCell4 = headerRow4.createCell(0);
            headerCell4.setCellValue("Atelier");
            headerCell4.setCellStyle(cellStyleBold);

            // fusionnage des cellules pour atelier
            String[] cellStrings = ("A5:C5").split(":");
            CellReference start = new CellReference(cellStrings[0]);
            CellReference end = new CellReference(cellStrings[1]);
            CellRangeAddress address = new CellRangeAddress(start.getRow(),
            end.getRow(), start.getCol(), end.getCol());
            sheet.addMergedRegion(address);

            headerCell4 = headerRow4.createCell(3);
            headerCell4.setCellValue("Correction (Antenne / Terrain)");
            headerCell4.setCellStyle(cellStyleBold);

            // fusionnage des cellules pour antenne / terrain
            String[] cellStrings2 = ("D5:H5").split(":");
            CellReference start2 = new CellReference(cellStrings2[0]);
            CellReference end2 = new CellReference(cellStrings2[1]);
            CellRangeAddress address2 = new CellRangeAddress(start2.getRow(),
            end2.getRow(), start2.getCol(), end2.getCol());
            sheet.addMergedRegion(address2);

            headerCell4 = headerRow4.createCell(8);
            headerCell4.setCellValue("Contrôles");
            headerCell4.setCellStyle(cellStyleBold);
            //headerCell4.setCellStyle(cellStyleCenter);

            // fusionnage des cellules pour contrôle ( signature AGF, AFO, ect.
            String[] cellStrings3 = ("I5:L5").split(":");
            CellReference start3 = new CellReference(cellStrings3[0]);
            CellReference end3 = new CellReference(cellStrings3[1]);
            CellRangeAddress address3 = new CellRangeAddress(start3.getRow(),
            end3.getRow(), start3.getCol(), end3.getCol());
            sheet.addMergedRegion(address3);
        
       
            Row headerRow5 = sheet.createRow(5);

            Cell headerCell5 = headerRow5.createCell(0);
            headerCell5.setCellValue("Lot");
            headerCell5.setCellStyle(cellStyleBold);

            headerCell5 = headerRow5.createCell(1);
            headerCell5.setCellValue("N° Demande");
            headerCell5.setCellStyle(cellStyleBold);

            headerCell5 = headerRow5.createCell(2);
            headerCell5.setCellValue("Description anomalie");
            headerCell5.setCellStyle(cellStyleBold);

            headerCell5 = headerRow5.createCell(3);
            headerCell5.setCellValue("Corrigé");
            headerCell5.setCellStyle(cellStyleBold);
 
            headerCell5 = headerRow5.createCell(4);
            headerCell5.setCellValue("Non corrigé");
            headerCell5.setCellStyle(cellStyleBold);

            headerCell5 = headerRow5.createCell(5);
            headerCell5.setCellValue("Date correction");
            headerCell5.setCellStyle(cellStyleBold);

            headerCell5 = headerRow5.createCell(6);
            headerCell5.setCellValue("Observation sur la correction");
            headerCell5.setCellStyle(cellStyleBold);

            headerCell5 = headerRow5.createCell(7);
            headerCell5.setCellValue("Signature ADA");
            headerCell5.setCellStyle(cellStyleBold);

            headerCell5 = headerRow5.createCell(8);
            headerCell5.setCellValue("Date");
            headerCell5.setCellStyle(cellStyleBold);

            headerCell5 = headerRow5.createCell(9);
            headerCell5.setCellValue("Signature AGF");
            headerCell5.setCellStyle(cellStyleBold);

            headerCell5 = headerRow5.createCell(10);
            headerCell5.setCellValue("Date");
            headerCell5.setCellStyle(cellStyleBold);

            headerCell5 = headerRow5.createCell(11);
            headerCell5.setCellValue("Signature AFO");
            headerCell5.setCellStyle(cellStyleBold);

        int n = 6;
        
            while (rs.next()) {
                
                RowResultSet++;
                    
                    Row headerRow7 = sheet.createRow(n);

                    Cell headerCell7 = headerRow7.createCell(0);
                    headerCell7.setCellValue(rs.getString("lot"));


                    Cell headerCell8 = headerRow7.createCell(1);
                    headerCell8.setCellValue(rs.getString("numero_demande"));

                    Cell headerCell9 = headerRow7.createCell(2);
                    headerCell9.setCellValue(rs.getString("anomalie_description"));
                    
                    System.out.println("Get commune : " + rs.getString("commune") + " ::  " + rs.getString("numero_demande") 
                                           + " Login = " +rs.getString("login") + " lot : " + rs.getString("lot")+ " anomalie_description = " + rs.getString("anomalie_description"));

                    n++;
            }
  
                    FileOutputStream fout = new FileOutputStream(src);
                    
                    wb.write(fout);
                    wb.close();
                    out.close();
                    fout.close();


                }catch(Exception createFileErreur){

                    System.out.println(createFileErreur.getMessage());
                }
                     
            rs.close();
            st.close();
            
            if(RowResultSet == 0){
                //System.out.println("val fiale de RowResultSet = " + RowResultSet);
                retour.add("error");
                retour.add(realPath);
                Files.deleteIfExists(Paths.get(realPath));
                
                JOptionPane.showMessageDialog(null, "Export registre d'anomalie annuler\nAucune anomalie a été trouvé sur la : \n\ncommune: "+com+"\n"+"Fokontany : "+fkt+"\n"+"Hameau : "+hameau+"\n"+"Type d'opération : "+this.op, "Export du registre d'anonamlie impossible", JOptionPane.INFORMATION_MESSAGE);
 
            }else{
                retour.add("success");
                JOptionPane.showMessageDialog(null, "Export registre d'anomalie effectué avec succès !", "Export registre d'anomalie effectué avec succès", JOptionPane.INFORMATION_MESSAGE);
                // ouverture de l'emplacement selectionner par l'utiisateur
                Desktop.getDesktop().open(new File(path));
            }
  
        } catch (Exception ex) {
            //ex.printStackTrace();
            //throw new RuntimeException();
            retour.add("error");
            retour.add("Error executing query: " +ex.getMessage());
        }
        
        //System.out.println(demandes);
        return retour;
    }
    
    
public List<String> getListesCfEditer(String reg, String c_dist, String dist, String c_com,String com, String path){
        
        List retour = new ArrayList();
        
        if(this.TYPE_OPERATION.equals("OCM")){
            this.op = "OCFM";
        }else{
            this.op = "OGCF";
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_'a'_HH'h'mm'mn'ss'sec'");
        Date date = new Date(System.currentTimeMillis());
    
    
        String realPath = path+"\\"+formatter.format(date)+"_"+this.op+"_CF_EDITER_du_xxxxxxx"+"_Reg_"+Formats.ConvertSlashToUnderscore(reg)+"_Com_"+com+".xls";

        int RowResultSet = 0;
        
        try {
            
            String sql = "  SELECT \n" +
"                region.nom AS region,\n" +
"                district.nom AS district,\n" +
"                commune.code_commune AS c_com,\n" +
"                commune.nom AS commune,\n" +
"                fokontany.code_fokontany AS c_fkt,\n" +
"                fokontany.nom AS fokontany,\n" +
"                hameau.code_hameau AS c_hameau,\n" +
"                hameau.nom AS hameau,\n" +
"                demande.id_registre,\n" +
"                demande.id_parcelle,\n" +
"                demande.val_cf_edit_date AS date_edition_pdf,\n" +
"                demande.num_registre AS numero_demande,\n" +
"                demande.num_certificat AS numero_certificat,\n" +
"                demande.id_certificat,\n" +
"				CASE \n" +
"					WHEN proprietaire_pp.type_demandeur = 'DM' THEN 'Demandeur'\n" +
"					ELSE  'Co-demandeur'\n" +
"				END AS type_demandeur,\n" +
"                persphys.nom AS nom_demandeur,\n" +
"                CASE \n" +
"					WHEN persphys.prenom IS NULL THEN ''\n" +
"					ELSE persphys.prenom\n" +
"				END AS prenom_demandeur,\n" +
"                persphys.sexe,\n" +
"				CASE \n" +
"					WHEN persphys.d_naiss_approx IS TRUE THEN CONCAT('Vers ', EXTRACT(YEAR FROM persphys.naissance_date))\n" +
"					ELSE TO_CHAR(persphys.naissance_date, 'DD/MM/YYYY')\n" +
"				END AS naissance_date,\n" +
"                persphys.naissance_lieu,\n" +
"				CASE \n" +
"					WHEN persphys.cni_num IS NULL THEN ''\n" +
"					ELSE persphys.cni_num\n" +
"				END,\n" +
"				CASE \n" +
"					WHEN persphys.cni_lieu IS NULL THEN ''\n" +
"					ELSE persphys.cni_lieu\n" +
"				END,\n" +
"				CASE \n" +
"					WHEN persphys.acn_num IS NULL THEN ''\n" +
"					ELSE persphys.acn_num\n" +
"				END,\n" +
"                persphys.acn_date,\n" +
"				CASE \n" +
"					WHEN persphys.acn_lieu IS NULL THEN ''\n" +
"					ELSE persphys.acn_lieu\n" +
"				END AS acn_lieu\n" +
"               FROM demande,\n" +
"                commune,\n" +
"                district,\n" +
"                region,\n" +
"                fokontany,\n" +
"                hameau,\n" +
"                proprietaire_pp,\n" +
"                persphys\n" +
"              WHERE demande.id_hameau = hameau.id_hameau \n" +
"                   AND hameau.id_fokontany= fokontany.id_fokontany \n" +
"                   AND district.id_district = commune.id_district \n" +
"                   AND commune.id_commune = fokontany.id_commune \n" +
"                   AND region.id_region = district.id_region \n" +
"                   AND proprietaire_pp.id_demande= demande.id_demande \n" +
"                   AND persphys.id_persphys = proprietaire_pp.id_persphys \n" +
"                  AND demande.val_cf_edit IS TRUE\n" +
"           AND region.nom = ?  \n" +
"		  AND district.nom = ?        \n" +
"			  AND commune.nom = ? \n" +
"			AND demande.type_op = ?  \n" +
"  ORDER BY demande.num_certificat ASC"; 
            
            st = connectDatabase.prepareStatement(sql);    
            st.setString(1, reg);
            st.setString(2, dist);
            st.setString(3, com);;
            st.setString(4, this.TYPE_OPERATION.toLowerCase());
            
            rs = st.executeQuery();
            
            System.out.println(st);
            
                try{
                    
                    // CREATION DU FICHIER
                    String nameOfSheet = "cf_editer";
                    
                    
                    // REMPLISSAGE DANS LE FICHIER

            HSSFWorkbook   wb = new HSSFWorkbook ();
            HSSFSheet  sheet = wb.createSheet(nameOfSheet);
                    
                    
            //sheet.createRow(0).createCell(0).setCellValue("Commune :");
           //sheet.getRow(0).createCell(1).setCellValue(com);

            Row headerRow0 = sheet.createRow(0);
 
        
            HSSFCellStyle cellStyleBold = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            cellStyleBold.setAlignment(HorizontalAlignment.CENTER);
            cellStyleBold.setFont(headerFont);
            
            // create table with data
            HSSFCellStyle cadre = wb.createCellStyle();
            cadre.setBorderBottom(BorderStyle.THIN);  
        
            cellStyleBold.setBorderBottom(BorderStyle.THIN);  
            cellStyleBold.setBottomBorderColor(IndexedColors.BLACK.getIndex()); 
            
            cellStyleBold.setBorderRight(BorderStyle.THIN);  
            cellStyleBold.setRightBorderColor(IndexedColors.BLACK.getIndex());  
            
            cellStyleBold.setBorderTop(BorderStyle.THIN);  
            cellStyleBold.setTopBorderColor(IndexedColors.BLACK.getIndex()); 

        
            cellStyleBold.setBorderLeft(BorderStyle.THIN);  
            cellStyleBold.setLeftBorderColor(IndexedColors.BLACK.getIndex()); 
            
            Cell headerCell0 = headerRow0.createCell(0);
            headerCell0.setCellValue("Région :");
            headerCell0.setCellStyle(cellStyleBold);


            headerCell0 = headerRow0.createCell(1);
            headerCell0.setCellValue(reg);


            headerCell0 = headerRow0.createCell(2);
            headerCell0.setCellValue("Type Opération : ");
            headerCell0.setCellStyle(cadre);

            headerCell0 = headerRow0.createCell(3);
            headerCell0.setCellValue(this.op);
            headerCell0.setCellStyle(cadre);

            headerCell0 = headerRow0.createCell(4);
            headerCell0.setCellValue("Date d'édition CF :");
            headerCell0.setCellStyle(cellStyleBold);


            headerCell0 = headerRow0.createCell(5);
            headerCell0.setCellValue("xxxxxx_Date_édition_CF");   
            headerCell0.setCellStyle(cadre);
//==============================================================================
            Row headerRow1 = sheet.createRow(1);

            Cell headerCell1 = headerRow1.createCell(0);
            headerCell1.setCellValue("District :");
            headerCell1.setCellStyle(cellStyleBold);


            headerCell1 = headerRow1.createCell(1);
            headerCell1.setCellValue(dist);
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(2);
            headerCell1.setCellValue("Commune : ");

            headerCell1 = headerRow1.createCell(3);
            headerCell1.setCellValue(com);
            headerCell1.setCellStyle(cadre);
// ============================================================================
            Row headerRow2 = sheet.createRow(2);

            Cell headerCell2 = headerRow2.createCell(0);
            headerCell2.setCellValue("Code Dist :");
            headerCell2.setCellStyle(cellStyleBold);


            headerCell2 = headerRow2.createCell(1);
            headerCell2.setCellValue(c_dist);
            headerCell2.setCellStyle(cadre);

            headerCell2 = headerRow2.createCell(2);
            headerCell2.setCellValue("Code Com :");

            headerCell2 = headerRow2.createCell(3);
            headerCell2.setCellValue(c_com);
            headerCell2.setCellStyle(cadre);
// ============================================================================ 

            String[] TextEnTeteTableau = {"date_edition_pdf", "numero_certificat"
                    , "id_certificat", "type_demandeur", "nom_demandeur"
                    , "prenom_demandeur", "sexe", "naissance_date", "naissance_lieu", "cni_num", "cni_lieu"
                    , "acn_num", "acn_date", "acn_lieu"};

            TreeMap<Integer, String> EnTeteTableauAExporter = new TreeMap<Integer, String>();

            for (int i = 0; i < TextEnTeteTableau.length; i++) {
              EnTeteTableauAExporter.put(i, TextEnTeteTableau[i]);
            }

            //System.out.println("EnTeteTableauAExporter vaut : " + EnTeteTableauAExporter);

            Row headerRow3 = sheet.createRow(4);

            for (Map.Entry<Integer, String> textTab : EnTeteTableauAExporter.entrySet()) {
                Cell headerCell0Ligne3 = headerRow3.createCell(textTab.getKey());
                headerCell0Ligne3.setCellValue(textTab.getValue());
                headerCell0Ligne3.setCellStyle(cellStyleBold);
            }


            
                    int n = 5;
                    
                    
                    
                    while (rs.next()) {

                        RowResultSet++;
                        
                        Row headerRow4 = sheet.createRow(n);
                        
                            Cell headerCell7 = headerRow4.createCell(0);
                            headerCell7.setCellValue(rs.getString("date_edition_pdf"));
                            headerCell7.setCellStyle(cadre);

                            Cell headerCell8 = headerRow4.createCell(1);
                            headerCell8.setCellValue(rs.getString("numero_certificat"));
                            headerCell8.setCellStyle(cadre);

                            Cell headerCell9 = headerRow4.createCell(2);
                            headerCell9.setCellValue(rs.getString("id_certificat"));
                            headerCell9.setCellStyle(cadre);
                            
                            Cell headerCell_10 = headerRow4.createCell(3);
                            headerCell_10.setCellValue(rs.getString("type_demandeur"));
                            headerCell_10.setCellStyle(cadre);
                            
                            Cell headerCell_11 = headerRow4.createCell(4);
                            headerCell_11.setCellValue(rs.getString("nom_demandeur"));
                            headerCell_11.setCellStyle(cadre);
                            
                            Cell headerCell_12 = headerRow4.createCell(5);
                            headerCell_12.setCellValue(rs.getString("prenom_demandeur"));
                            headerCell_12.setCellStyle(cadre);
                            
                            Cell headerCell_13 = headerRow4.createCell(6);
                            headerCell_13.setCellValue(rs.getString("sexe"));
                            headerCell_13.setCellStyle(cadre);
                            
                            Cell headerCell_14 = headerRow4.createCell(7);
                            headerCell_14.setCellValue(rs.getString("naissance_date"));
                            headerCell_14.setCellStyle(cadre);
                            
                            Cell headerCell_15 = headerRow4.createCell(8);
                            headerCell_15.setCellValue(rs.getString("naissance_lieu"));
                            headerCell_15.setCellStyle(cadre);
                            
                            Cell headerCell_16 = headerRow4.createCell(9);
                            headerCell_16.setCellValue(rs.getString("cni_num"));
                            headerCell_16.setCellStyle(cadre);
                            
                            Cell headerCell_17 = headerRow4.createCell(10);
                            headerCell_17.setCellValue(rs.getString("cni_lieu"));
                            headerCell_17.setCellStyle(cadre);
                            
                            Cell headerCell_18 = headerRow4.createCell(11);
                            headerCell_18.setCellValue(rs.getString("acn_num"));
                            headerCell_18.setCellStyle(cadre);
                            
                            Cell headerCell_19 = headerRow4.createCell(12);
                            headerCell_19.setCellValue(rs.getString("acn_date"));
                            headerCell_19.setCellStyle(cadre);
                            
                            Cell headerCell_20 = headerRow4.createCell(13);
                            headerCell_20.setCellValue(rs.getString("acn_lieu"));
                            headerCell_20.setCellStyle(cadre);
                            

                            //System.out.println("Get commune : " + rs.getString("commune") + " ::  " + rs.getString("numero_demande") 
                             //                      + " Login = " +rs.getString("login") + " lot : " + rs.getString("lot")+ " anomalie_description = " + rs.getString("anomalie_description"));

                        n++;
                    }
  
                    FileOutputStream fout = new FileOutputStream(realPath);

                    wb.write(fout);
                    wb.close();
                    fout.close();


                }catch(Exception createFileErreur){

                    System.out.println(createFileErreur.getMessage());
                }
                     
            rs.close();
            st.close();
            
            if(RowResultSet == 0){
                //System.out.println("val fiale de RowResultSet = " + RowResultSet);
                retour.add("error");
                retour.add(realPath);
                // SUPPRESSION DU FICHIER EXPORTE CAR IL Y AVAIT UNE ERREUR LORS DE L'EXPORT
                Files.deleteIfExists(Paths.get(realPath));
                //System.out.println("votre commune : "+ com);

            }else{
                retour.add("success");
                //JOptionPane.showMessageDialog(null, "Listes CF éditer exporté avec succès !", "Listes CF éditer exporté avec succès ", JOptionPane.INFORMATION_MESSAGE);
                // ouverture de l'emplacement selectionner par l'utiisateur
                //Desktop.getDesktop().open(new File(path));
            }
  
        } catch (Exception ex) {
            //ex.printStackTrace();
            //throw new RuntimeException();
            retour.add("error");
            retour.add("Error executing query: " +ex.getMessage());
        }
        
        //System.out.println(demandes);
        return retour;
    }
  
    
    
}
