/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.export.action3saisie;


import com.allInterfaces.action3saisies.Home;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import com.classes.action3saisie.Querry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

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
    

    public Exports(String HOST, Integer PORT, String DBNAME, String USER, String PWD, String operation) {
        
        this.BDD_HOST = HOST;
        this.BDD_PORT = PORT;
        this.BDD_DBNAME = DBNAME;
        this.BDD_USER = USER;
        this.BDD_PWD = PWD;
        

        this.TYPE_OPERATION = Formats.ConvertOcmToOcfm(operation);
        
        System.out.println("operation vaut = " + this.TYPE_OPERATION);
        //connectDatabase = new ConnectDb("192.168.88.10", 5432, "oprod", "C@seF&Ge0X2", "postgres").getConnection();
        connectDatabase = new ConnectDb(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getConnection();
    }
    
    
    
public List<String> getListesVectorisationSansSaisie(String reg, String c_dist, String dist, String c_com,String com, String path){

        List retour = new ArrayList();
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_'a'_HH'h'mm'mn'ss'sec'");
        Date date = new Date(System.currentTimeMillis());
        String realPath = path+"\\"+this.TYPE_OPERATION+"_"+formatter.format(date)+"_VECTO_SANS_SAISIE_"+"_Reg_"+Formats.ConvertSlashToUnderscore(reg)+"_Com_"+com+".xls";
        String sql = "";
        String contrainte =" IS TRUE";
        
        System.out.println("TYPE OPERATION DANS vecto sans saisie = " + this.TYPE_OPERATION);
        
        if (this.TYPE_OPERATION.equals("OGCF")) {
            //contrainte = " IS NOT TRUE ";
            
            
            sql = " SELECT region.nom as region, district.nom AS district, commune.nom AS commune, c_parcelle, id_lot AS numero_planche_plof, limitrophe, superficie, coord_x, coord_y, anomalie\n" +
            "   FROM parcelle_cf, region,\n" +
            "    district,\n" +
            "    commune\n" +
            "   WHERE parcelle_cf.type_op IS TRUE\n" +
            "   AND NOT (parcelle_cf.c_parcelle::text IN ( SELECT demande.id_parcelle\n" +
            "           FROM demande))\n" +
            "AND region.id_region::text = district.id_region::text \n" +
            "  AND district.id_district::text = commune.id_district::text \n" +
            "  AND parcelle_cf.c_district::text = district.code_district::text \n" +
            "  AND parcelle_cf.type_op IS NOT TRUE " +
            "           AND region.nom = ?  \n" +
            "		  AND district.nom = ?        \n" +
            "			  AND commune.nom = ? \n" +
            "  AND parcelle_cf.c_commune::text = commune.code_commune::text "; 
            
        }else{
            sql ="SELECT region.nom as region, district.nom AS district, commune.nom AS commune, c_parcelle, CONCAT('M-', c_district, '-', c_commune, '-F-', c_fokontany, c_hameau, '-',num_parcelle ) AS c_parcelle, id_lot AS numero_planche_plof, limitrophe, superficie, coord_x, coord_y, anomalie\n" +
            "              FROM parcelle_cf, region,\n" +
            "               district,\n" +
            "               commune\n" +
            "              WHERE parcelle_cf.type_op IS TRUE\n" +
            "              AND NOT (parcelle_cf.c_parcelle::text IN ( SELECT demande.id_parcelle\n" +
            "                      FROM demande))\n" +
            "           AND region.id_region::text = district.id_region::text \n" +
            "             AND district.id_district::text = commune.id_district::text \n" +
            "             AND parcelle_cf.c_district::text = district.code_district::text \n" +
            "             AND parcelle_cf.type_op IS TRUE \n" +
            "           AND region.nom = ?  \n" +
            "		  AND district.nom = ?        \n" +
            "			  AND commune.nom = ? \n" +
            "             AND parcelle_cf.c_commune::text = commune.code_commune::text";
        }

        int RowResultSet = 0;
        
        try {
            
            
            
            st = connectDatabase.prepareStatement(sql);    
            st.setString(1, reg);
            st.setString(2, dist);
            st.setString(3, com);
            rs = st.executeQuery();
            
            //System.out.println("RETOUR SQL = " + st);
            

            
            try{
                    
                // CREATION DU FICHIER
                String nameOfSheet = "VectoSansSaisie";
                    
                    
                // REMPLISSAGE DANS LE FICHIER

                XSSFWorkbook   wb = new XSSFWorkbook ();
                XSSFSheet  sheet = wb.createSheet(nameOfSheet);

                // MISE EN PAGE DU FICHIER
                sheet.getHeader().setCenter("Listes des véctorisations sans saisie");
                sheet.getFooter().setCenter("CASEF / GEOX2");
                sheet.getPrintSetup().setLandscape(true);
                PrintSetup printsetup = sheet.getPrintSetup();
                sheet.getPrintSetup().setPaperSize(printsetup.A4_PAPERSIZE);

                String[] cellAFixer = ("$1:$5").split(":");
                CellReference startCellFixed = new CellReference(cellAFixer[0]);
                CellReference endCellFixed = new CellReference(cellAFixer[1]);
                CellRangeAddress addressCellAFixer = new CellRangeAddress(startCellFixed.getRow(),
                endCellFixed.getRow(), startCellFixed.getCol(), endCellFixed.getCol());

                sheet.setRepeatingRows(addressCellAFixer);
                
                // FIN MISE EN PAGE
                
                
                Row headerRow0 = sheet.createRow(0);

                XSSFCellStyle cellStyleBold = wb.createCellStyle();
                Font headerFont = wb.createFont();
                headerFont.setBold(true);
                cellStyleBold.setAlignment(HorizontalAlignment.CENTER);
                cellStyleBold.setFont(headerFont);
            
                // create table with data
                XSSFCellStyle cadre = wb.createCellStyle();
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
                headerCell0.setCellStyle(cadre);

                headerCell0 = headerRow0.createCell(1);
                headerCell0.setCellValue(reg);
                headerCell0.setCellStyle(cadre);

                headerCell0 = headerRow0.createCell(2);
                headerCell0.setCellValue("Type Opération : ");
                headerCell0.setCellStyle(cadre);

                headerCell0 = headerRow0.createCell(3);
                headerCell0.setCellValue(this.TYPE_OPERATION);
                headerCell0.setCellStyle(cadre);
            

//==============================================================================
                Row headerRow1 = sheet.createRow(1);

                Cell headerCell1 = headerRow1.createCell(0);
                headerCell1.setCellValue("District :");
                headerCell1.setCellStyle(cellStyleBold);
                headerCell1.setCellStyle(cadre);

                headerCell1 = headerRow1.createCell(1);
                headerCell1.setCellValue(dist);
                headerCell1.setCellStyle(cadre);

                headerCell1 = headerRow1.createCell(2);
                headerCell1.setCellValue("Commune : ");
                headerCell1.setCellStyle(cadre);
                
                headerCell1 = headerRow1.createCell(3);
                headerCell1.setCellValue(com);
                headerCell1.setCellStyle(cadre);
// ============================================================================
                Row headerRow2 = sheet.createRow(2);

                Cell headerCell2 = headerRow2.createCell(0);
                headerCell2.setCellValue("Code Dist :");
                headerCell2.setCellStyle(cellStyleBold);
                headerCell2.setCellStyle(cadre);

                headerCell2 = headerRow2.createCell(1);
                headerCell2.setCellValue(c_dist);
                headerCell2.setCellStyle(cadre);

                headerCell2 = headerRow2.createCell(2);
                headerCell2.setCellValue("Code Com :");
                headerCell2.setCellStyle(cadre);
                
                headerCell2 = headerRow2.createCell(3);
                headerCell2.setCellValue(c_com);
                headerCell2.setCellStyle(cadre);
// ============================================================================ 

                String[] TextEnTeteTableau = {};
                
                String Str1 = "id_parcelle, numéro_planche_plof, limitrophe, superficie, coord_x, coord_y, présence anomalie vecto ?";
                String Str2 = "id_parcelle, numéro_planche_plof, limitrophe, superficie, coord_x, coord_y, présence anomalie vecto ?";
            
            
                if (this.TYPE_OPERATION.equals("OGCF")) {
                    TextEnTeteTableau = Str1.split(",");
                }else{
                    TextEnTeteTableau = Str2.split(",");
                }

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
                    
                    String newLimitrophe = "";
                    
                    while (rs.next()) {

                        RowResultSet++;
                        
                        Row headerRow4 = sheet.createRow(n);
                        
                        if (this.TYPE_OPERATION.equals("OCFM")) {
                            
                            
                        
                            Cell headerCell8 = headerRow4.createCell(0);
                            headerCell8.setCellValue(rs.getString("c_parcelle"));
                            headerCell8.setCellStyle(cadre);
                            
                            Cell headerCell_10 = headerRow4.createCell(1);
                            headerCell_10.setCellValue(rs.getString("numero_planche_plof"));
                            headerCell_10.setCellStyle(cadre);
                            
                            
                            if (rs.getString("limitrophe").equals("f")) {
                                newLimitrophe = "NON";
                            }else{
                                newLimitrophe = "OUI";
                            }
                            
                            Cell headerCell_11 = headerRow4.createCell(2);
                            headerCell_11.setCellValue(newLimitrophe);
                            headerCell_11.setCellStyle(cadre);
                            
                            Cell headerCell_12 = headerRow4.createCell(3);
                            headerCell_12.setCellValue(rs.getString("superficie"));
                            headerCell_12.setCellStyle(cadre);
                            
                            
                            Cell headerCell_13 = headerRow4.createCell(4);
                            headerCell_13.setCellValue(rs.getString("coord_x"));
                            headerCell_13.setCellStyle(cadre);
                            
                            Cell headerCell_14 = headerRow4.createCell(5);
                            headerCell_14.setCellValue(rs.getString("coord_y"));
                            headerCell_14.setCellStyle(cadre);
                            
                        }else{
                            
                            //Row headerRow4 = sheet.createRow(n);
                        
                            Cell headerCell8 = headerRow4.createCell(0);
                            headerCell8.setCellValue(rs.getString("c_parcelle"));
                            headerCell8.setCellStyle(cadre);
                            
                            Cell headerCell_10 = headerRow4.createCell(1);
                            headerCell_10.setCellValue(rs.getString("numero_planche_plof"));
                            headerCell_10.setCellStyle(cadre);
                            
                            if (rs.getString("limitrophe").equals("f")) {
                                newLimitrophe = "NON";
                            }else{
                                newLimitrophe = "OUI";
                            }
                            
                            Cell headerCell_11 = headerRow4.createCell(2);
                            headerCell_11.setCellValue(newLimitrophe);
                            headerCell_11.setCellStyle(cadre);
                            
                            Cell headerCell_12 = headerRow4.createCell(3);
                            headerCell_12.setCellValue(rs.getString("superficie"));
                            headerCell_12.setCellStyle(cadre);
                            
                            
                            Cell headerCell_13 = headerRow4.createCell(4);
                            headerCell_13.setCellValue(rs.getString("coord_x"));
                            headerCell_13.setCellStyle(cadre);
                            
                            Cell headerCell_14 = headerRow4.createCell(5);
                            headerCell_14.setCellValue(rs.getString("coord_y"));
                            headerCell_14.setCellStyle(cadre);
                        }
                        
                        
                            if (rs.getString("anomalie").equals("f")) {
                                newLimitrophe = "NON";
                            }else{
                                newLimitrophe = "OUI";
                            }
                            
                            Cell headerCell_15 = headerRow4.createCell(6);
                            headerCell_15.setCellValue(newLimitrophe);
                            headerCell_15.setCellStyle(cadre);
                        

                        n++;
                    }
  
                    FileOutputStream fout = new FileOutputStream(realPath);

                    wb.write(fout);
                    wb.close();
                    fout.close();


                }catch(Exception createFileErreur){

                    //System.out.println(createFileErreur.getMessage());
                    
                    JOptionPane.showMessageDialog(null, "Classes export véctorisaiton sans saisie erreur",createFileErreur.getMessage(), JOptionPane.INFORMATION_MESSAGE);
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

public List<String> getListesSaisieSansVectorisation(String reg, String c_dist, String dist, String c_com,String com, String path){
        
    System.out.println("OUF ATO ALONGANY");
    
    
        List retour = new ArrayList();
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_'a'_HH'h'mm'mn'ss'sec'");
        Date date = new Date(System.currentTimeMillis());
        String realPath = path+"\\"+this.TYPE_OPERATION+"_"+formatter.format(date)+"_SAISIE_SANS_VECTO_"+"_Reg_"+Formats.ConvertSlashToUnderscore(reg)+"_Com_"+com+".xls";

        int RowResultSet = 0;
        
        try {
            
            String sql = " SELECT region.nom AS region,\n" +
"    commune.code_commune AS c_commune,\n" +
"    commune.nom AS commune,\n" +
"    fokontany.code_fokontany AS c_fkt,\n" +
"    fokontany.nom AS fkt,\n" +
"    hameau.code_hameau AS c_hameau,\n" +
"    hameau.nom AS hameau,\n" +
"        CASE\n" +
"            WHEN persphys.prenom IS NULL THEN persphys.nom::text\n" +
"            ELSE concat(persphys.nom, ' ', persphys.prenom)\n" +
"        END AS nom_et_prenom,\n" +
"    demande.id_registre,\n" +
"    demande.id_parcelle,\n" +
"    demande.v_nord,\n" +
"    demande.v_sud,\n" +
"    demande.v_est,\n" +
"    demande.v_ouest,\n" +
"        CASE\n" +
"            WHEN persphys.adresse IS NULL THEN ''::character varying\n" +
"            ELSE persphys.adresse\n" +
"        END AS adresse\n" +
"   FROM demande,\n" +
"    persphys,\n" +
"    proprietaire_pp,\n" +
"    hameau,\n" +
"    fokontany,\n" +
"    commune,\n" +
"    district,\n" +
"    region\n" +
"  WHERE demande.cf_annule IS NOT TRUE "
                    + "AND demande.id_hameau::text = hameau.id_hameau::text "
                    + "AND hameau.id_fokontany::text = fokontany.id_fokontany::text "
                    + "AND fokontany.id_commune::text = commune.id_commune::text "
                    + "AND district.id_district::text = commune.id_district::text "
                    + "AND region.id_region::text = district.id_region::text "
                    + "AND demande.id_demande::text = proprietaire_pp.id_demande::text "
                    + "AND proprietaire_pp.id_persphys::text = persphys.id_persphys::text"
                    + " AND proprietaire_pp.type_demandeur::text = 'DM'::text "
            +"           AND region.nom = ?  \n" +
            "		  AND district.nom = ?        \n" +
            "			  AND commune.nom = ? \n" +
            "			AND demande.type_op = ?  \n" 
                    + "AND NOT (demande.id_parcelle::text IN ( SELECT parcelle_cf.c_parcelle\n" +
"           FROM parcelle_cf))\n" +
"  ORDER BY demande.num_registre ASC"; 
            
            st = connectDatabase.prepareStatement(sql);    
            st.setString(1, reg);
            st.setString(2, dist);
            st.setString(3, com);;
            st.setString(4, Formats.ConvertOcfmToOcm(this.TYPE_OPERATION.toLowerCase()));
            rs = st.executeQuery();
            
            //System.out.println("saisie sans vecto : "+ st);
            

            
            try{
                    
                // CREATION DU FICHIER
                String nameOfSheet = "saisieSansVecto";
                    
                    
                // REMPLISSAGE DANS LE FICHIER

                XSSFWorkbook   wb = new XSSFWorkbook ();
                XSSFSheet  sheet = wb.createSheet(nameOfSheet);

                // MISE EN PAGE DU FICHIER
                sheet.getHeader().setCenter("Listes des saisies sans véctorisation");
                sheet.getFooter().setCenter("CASEF / GEOX2");
                sheet.getPrintSetup().setLandscape(true);
                PrintSetup printsetup = sheet.getPrintSetup();
                sheet.getPrintSetup().setPaperSize(printsetup.A4_PAPERSIZE);

                String[] cellAFixer = ("$1:$5").split(":");
                CellReference startCellFixed = new CellReference(cellAFixer[0]);
                CellReference endCellFixed = new CellReference(cellAFixer[1]);
                CellRangeAddress addressCellAFixer = new CellRangeAddress(startCellFixed.getRow(),
                endCellFixed.getRow(), startCellFixed.getCol(), endCellFixed.getCol());

                sheet.setRepeatingRows(addressCellAFixer);
                
                // FIN MISE EN PAGE
                
                
                Row headerRow0 = sheet.createRow(0);

                XSSFCellStyle cellStyleBold = wb.createCellStyle();
                Font headerFont = wb.createFont();
                headerFont.setBold(true);
                cellStyleBold.setAlignment(HorizontalAlignment.CENTER);
                cellStyleBold.setFont(headerFont);
            
                // create table with data
                XSSFCellStyle cadre = wb.createCellStyle();
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
                headerCell0.setCellStyle(cadre);

                headerCell0 = headerRow0.createCell(1);
                headerCell0.setCellValue(reg);
                headerCell0.setCellStyle(cadre);

                headerCell0 = headerRow0.createCell(2);
                headerCell0.setCellValue("Type Opération : ");
                headerCell0.setCellStyle(cadre);

                headerCell0 = headerRow0.createCell(3);
                headerCell0.setCellValue(this.TYPE_OPERATION);
                headerCell0.setCellStyle(cadre);
            

//==============================================================================
                Row headerRow1 = sheet.createRow(1);

                Cell headerCell1 = headerRow1.createCell(0);
                headerCell1.setCellValue("District :");
                headerCell1.setCellStyle(cellStyleBold);
                headerCell1.setCellStyle(cadre);

                headerCell1 = headerRow1.createCell(1);
                headerCell1.setCellValue(dist);
                headerCell1.setCellStyle(cadre);

                headerCell1 = headerRow1.createCell(2);
                headerCell1.setCellValue("Commune : ");
                headerCell1.setCellStyle(cadre);
                
                headerCell1 = headerRow1.createCell(3);
                headerCell1.setCellValue(com);
                headerCell1.setCellStyle(cadre);
// ============================================================================
                Row headerRow2 = sheet.createRow(2);

                Cell headerCell2 = headerRow2.createCell(0);
                headerCell2.setCellValue("Code Dist :");
                headerCell2.setCellStyle(cellStyleBold);
                headerCell2.setCellStyle(cadre);

                headerCell2 = headerRow2.createCell(1);
                headerCell2.setCellValue(c_dist);
                headerCell2.setCellStyle(cadre);

                headerCell2 = headerRow2.createCell(2);
                headerCell2.setCellValue("Code Com :");
                headerCell2.setCellStyle(cadre);
                
                headerCell2 = headerRow2.createCell(3);
                headerCell2.setCellValue(c_com);
                headerCell2.setCellStyle(cadre);
// ============================================================================ 

                String[] TextEnTeteTableau = {};
                
                String Str1 = "id_registre, nom_et_prénom(s), adresse, v_nord, v_sud, v_est, v_ouest";
                
                String Str2 = "id_registre , id_parcelle, nom_et_prénom(s), adresse, voisin nord, voisin sud, voisin est, voisin ouest";
            
            
                if (this.TYPE_OPERATION.equals("OCFM")) {
                    TextEnTeteTableau = Str1.split(",");
                }else{
                    TextEnTeteTableau = Str2.split(",");
                }

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
                        
                        if (this.TYPE_OPERATION.equals("OCFM")) {
                            
                            Row headerRow4 = sheet.createRow(n);
                        
                            Cell headerCell8 = headerRow4.createCell(0);
                            headerCell8.setCellValue(rs.getString("id_registre"));
                            headerCell8.setCellStyle(cadre);
                            
                            Cell headerCell_10 = headerRow4.createCell(1);
                            headerCell_10.setCellValue(rs.getString("nom_et_prenom"));
                            headerCell_10.setCellStyle(cadre);
                            
                            Cell headerCell_11 = headerRow4.createCell(2);
                            headerCell_11.setCellValue(rs.getString("adresse"));
                            headerCell_11.setCellStyle(cadre);
                            
                            Cell headerCell_12 = headerRow4.createCell(3);
                            headerCell_12.setCellValue(rs.getString("v_nord"));
                            headerCell_12.setCellStyle(cadre);
                            
                            Cell headerCell_13 = headerRow4.createCell(4);
                            headerCell_13.setCellValue(rs.getString("v_sud"));
                            headerCell_13.setCellStyle(cadre);
                            
                            Cell headerCell_14 = headerRow4.createCell(5);
                            headerCell_14.setCellValue(rs.getString("v_est"));
                            headerCell_14.setCellStyle(cadre);
                            
                            Cell headerCell_15 = headerRow4.createCell(6);
                            headerCell_15.setCellValue(rs.getString("v_ouest"));
                            headerCell_15.setCellStyle(cadre);
                            
                        }else{
                            
                            Row headerRow4 = sheet.createRow(n);
                        
                            Cell headerCell8 = headerRow4.createCell(0);
                            headerCell8.setCellValue(rs.getString("id_registre"));
                            headerCell8.setCellStyle(cadre);

                            Cell headerCell9 = headerRow4.createCell(1);
                            headerCell9.setCellValue(rs.getString("id_parcelle"));
                            headerCell9.setCellStyle(cadre);
                            
                            Cell headerCell_10 = headerRow4.createCell(2);
                            headerCell_10.setCellValue(rs.getString("nom_et_prenom"));
                            headerCell_10.setCellStyle(cadre);
                            
                            Cell headerCell_11 = headerRow4.createCell(3);
                            headerCell_11.setCellValue(rs.getString("adresse"));
                            headerCell_11.setCellStyle(cadre);
                            
                            Cell headerCell_12 = headerRow4.createCell(4);
                            headerCell_12.setCellValue(rs.getString("v_nord"));
                            headerCell_12.setCellStyle(cadre);
                            
                            Cell headerCell_13 = headerRow4.createCell(5);
                            headerCell_13.setCellValue(rs.getString("v_sud"));
                            headerCell_13.setCellStyle(cadre);
                            
                            Cell headerCell_14 = headerRow4.createCell(6);
                            headerCell_14.setCellValue(rs.getString("v_est"));
                            headerCell_14.setCellStyle(cadre);
                            
                            Cell headerCell_15 = headerRow4.createCell(7);
                            headerCell_15.setCellValue(rs.getString("v_ouest"));
                            headerCell_15.setCellStyle(cadre);
                        }
                        


                        n++;
                    }
  
                    FileOutputStream fout = new FileOutputStream(realPath);

                    wb.write(fout);
                    wb.close();
                    fout.close();


                }catch(Exception createFileErreur){

                    //System.out.println(createFileErreur.getMessage());
                    
                    JOptionPane.showMessageDialog(null, "Classes export saisie sans vectorisaiton erreur",createFileErreur.getMessage(), JOptionPane.INFORMATION_MESSAGE);
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
  
    
    
public void ExportTableToExcel(JTable table, String nameOfSheet, String[] EnTeteTable, String messageSuccess){
    
    new SwingWorker(){
            
            @Override
            protected Object doInBackground() throws Exception{
                
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Enregistrer le fichier sous ...");
                
                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Fichier Excel ( xls ou xlsx Seulement )", "xls", "xlsx");
                fc.setFileFilter(fileFilter);
                int responseChooser = fc.showSaveDialog(null);

                if (responseChooser == JFileChooser.APPROVE_OPTION) {
                    
                    XSSFWorkbook wb = new XSSFWorkbook ();
                    XSSFSheet  sheet = wb.createSheet(nameOfSheet);
                    
                    DefaultTableModel tableau = (DefaultTableModel) table.getModel();
                    
                    //System.out.println("NOMBRE DE LIGNE : " +tableau.getRowCount());

                    TreeMap<Integer, String> EnTeteTableauAExporter = new TreeMap<Integer, String>();

                    for (int i = 0; i < EnTeteTable.length; i++) {
                      EnTeteTableauAExporter.put(i, EnTeteTable[i]);
                    }

                    Row headerRow0 = sheet.createRow(0);

                    for (Map.Entry<Integer, String> textTab : EnTeteTableauAExporter.entrySet()) {
                        Cell headerCell0Ligne3 = headerRow0.createCell(textTab.getKey());
                        headerCell0Ligne3.setCellValue(textTab.getValue());
                    }
                    
                    for (int i = 0; i < tableau.getRowCount() ; i++) {
                        
                        XSSFRow row = sheet.createRow(i+1);
                        
                        for (int j = 0; j < tableau.getColumnCount(); j++) {
                            
                            XSSFCell cell = row.createCell(j);
                            

                            if (tableau.getColumnCount() == 3) {
                                if (j == 2) {
                                    cell.setCellValue(Integer.parseInt(tableau.getValueAt(i, j).toString()));
                                }else{
                                    cell.setCellValue(tableau.getValueAt(i, j).toString());
                                }
                            }else{
                                if (j == 3) {
                                    cell.setCellValue(Integer.parseInt(tableau.getValueAt(i, j).toString()));
                                }else{
                                    cell.setCellValue(tableau.getValueAt(i, j).toString());
                                }
                            }
                            
                        }
                    }
                    
                    
                    try{
                        FileOutputStream fileSortie = new FileOutputStream(fc.getSelectedFile()+".xlsx");
                        //BufferedOutputStream excelBuffer = new BufferedOutputStream(fileSortie);
                        wb.write(fileSortie);
                        wb.close();
                        fileSortie.close();
                        
                        
                        return "export-ok";
                        
                    }catch(Exception createFileErreur){
                        //System.out.println(createFileErreur.getMessage());
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, createFileErreur);
                        JOptionPane.showMessageDialog(null, createFileErreur.getMessage(),"Erreur exportation", JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }
                      
                }
                
                return null;
            }
            
            
            
            
                @Override
                protected void done(){
                    
                    try {
                        
                        try{
                            if (get().toString().equals("export-ok")) {
                                
                                //JOptionPane.showMessageDialog(null, "Export stat anomalie saisie OK!","Export Excel OK", JOptionPane.INFORMATION_MESSAGE);
                                JOptionPane.showMessageDialog(null, messageSuccess,"Export Excel OK", JOptionPane.INFORMATION_MESSAGE);
                            } 
                        }catch(NullPointerException exNull){
                            //JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'exportation\n\n"+exNull.getMessage(),"Erreur exportation Excel", JOptionPane.INFORMATION_MESSAGE);
                        }
 
                        //System.out.println(get());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            
            
        }.execute();
       
}
    
// --------------------------------------------------------------------------------

public List<String> GetAnomaliesVectoCSVLola(String reg, String path){

        List retour = new ArrayList();
        
        List cutObsAnomalie = new ArrayList();
        
        HashMap<String, String> AnomaliesBloquantesAvecType = new HashMap<>();
        
        HashMap<String, String> AnomaliesNonBloquantesAvecType = new HashMap<>();
        
        HashMap<String, String> AutresTypesAvecType = new HashMap<>();
       
        
        AnomaliesBloquantesAvecType.put("03", "Délimitation non fermée");
        AnomaliesBloquantesAvecType.put("05", "Acheval sans la 2è portion");
        AnomaliesBloquantesAvecType.put("06", "Parcelle acheval mais des codes hameaux ou fokontany ou numéro de parcelles différents");
        AnomaliesBloquantesAvecType.put("07", "1 parcelle a plusieurs (> = 2 ) codes");
        AnomaliesBloquantesAvecType.put("08", "Numéro illisible(ou information illisible)");
        AnomaliesBloquantesAvecType.put("09", "Parcelle sans numéro (ou introuvable sur la planche)");
        AnomaliesBloquantesAvecType.put("10", "Sans code hameau et/ou Fokontany");
        AnomaliesBloquantesAvecType.put("11", "Sans délimitation hameau ou incompréhensible");
        AnomaliesBloquantesAvecType.put("12", "Demande annulée");
        AnomaliesBloquantesAvecType.put("13", "Annulée après CQ du BS TOPO");
        AnomaliesBloquantesAvecType.put("14", "Chevauchement avec titre existant");
        AnomaliesBloquantesAvecType.put("15", "Chevauchement avec cadastre existant");
        AnomaliesBloquantesAvecType.put("16", "Chevauchement avec certificat existant");
        AnomaliesBloquantesAvecType.put("17", "Chevauchement avec des parcelles existantes");
        AnomaliesBloquantesAvecType.put("18", "Annulée à cause du reserve route");
        AnomaliesBloquantesAvecType.put("20", "Supérficie supérieur à 10 ha");
        AnomaliesBloquantesAvecType.put("21", "Code hameau et/ou Fokontany et/ou numéro de parcelle de la planche PLOF et le fiche de levé GPS différent");
        AnomaliesBloquantesAvecType.put("22", "Parcelle déformée à cause des points GPS abérant non supprimable");
        // AJOUT ANOMALIE NON BLOQUANTE
        AnomaliesNonBloquantesAvecType.put("01", "Parcelle non RL");
        AnomaliesNonBloquantesAvecType.put("02", "2 ou plusieurs parcelles ayant le même code");
        AnomaliesNonBloquantesAvecType.put("04", "Délimitation non précise");
        AnomaliesNonBloquantesAvecType.put("25", "Parcelle située en déhors de la Commune");
        AnomaliesNonBloquantesAvecType.put("26", "Parcelle située en déhors du Fokontany");
        AnomaliesNonBloquantesAvecType.put("27", "Parcelle située en déhors du Hameau");
        
        //AJOUT AUTRE INFO
        AutresTypesAvecType.put("19", "Ajustée suivant reserve route");
        AutresTypesAvecType.put("23", "Parcelle tracée à partir des coordonnées GPS");
        AutresTypesAvecType.put("24", "Charge à mentionner dans la formulaire de demande");
        
        
        // PREPARATION BOUCLE SUR HASHMAP DES ANOMALIES VECTOS
        //Iterator it = AnomaliesAvecType.entrySet().iterator();


        
        //HashMap<String, HashMap> ListesAnomaliesVectosAvecIdentifiant = new HashMap<>();
  

        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        String dateAujourdhui = dateFormat.format(new Date());
    
    
        String realPath = path;
        String nomAtelier = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getNomAtelier();
        String nomAntenne = "";
        String sql ="";
        List <String> enTeteTableau = new ArrayList();
        String newOp = " IS NOT TRUE";
        
        switch (nomAtelier){
            case "ATS":
                nomAntenne= "ANTENNE : ATSINANANA";
                break;
            case "VAK":
                nomAntenne= "ANTENNE : VAKINANKARATRA";
                break;
            default:
                if (reg.toUpperCase().equals("ANALAMANGA")) {
                    nomAntenne= "ANTENNE : ANALAMANGA";
                }else{
                    nomAntenne= "ANTENNE : ITASY";
                }
                
                break;
        }
        
        if (Formats.ConvertOcfmToOcm(TYPE_OPERATION).equals("ocm")) {
        newOp = " IS TRUE";
    }
        
        int RowResultSet = 0;
        
        try {
            

            
if (reg.equals("all")) {
                
                sql = "SELECT region.nom as region, district.nom AS district, commune.nom AS commune, CONCAT('M-', c_district, '-', c_commune, '-F-', c_fokontany, c_hameau, '-',num_parcelle ) AS numero_demande\n" +
", c_parcelle, parcelle_cf.observation\n" +
"                     FROM parcelle_cf, region,\n" +
"                      district,\n" +
"                      commune\n" +
"                     WHERE  region.id_region::text = district.id_region::text \n" +
"                    AND district.id_district::text = commune.id_district::text \n" +
"                    AND parcelle_cf.c_district::text = district.code_district::text \n" +
"                    AND parcelle_cf.type_op " + newOp +
"        \n            AND parcelle_cf.c_commune::text = commune.code_commune::text\n" +
"					AND parcelle_cf.anomalie IS TRUE\n" +
"					GROUP BY region, district, commune, numero_demande, c_parcelle, observation\n" +
"					ORDER BY region, district, commune, c_parcelle";
                
                st = connectDatabase.prepareStatement(sql);    
                rs = st.executeQuery();
                
                enTeteTableau.add("Région");
                enTeteTableau.add("District");
                enTeteTableau.add("Commune");
                enTeteTableau.add("Numéro_demande");
                enTeteTableau.add("c_parcelle");
                enTeteTableau.add("Type_anomalie");
                enTeteTableau.add("Description");
                
                
            }else{
                
                sql = " SELECT region.nom as region, district.nom AS district, commune.nom AS commune, CONCAT('M-', c_district, '-', c_commune, '-F-', c_fokontany, c_hameau, '-',num_parcelle ) AS numero_demande\n" +
", c_parcelle, parcelle_cf.observation\n" +
"                     FROM parcelle_cf, region,\n" +
"                      district,\n" +
"                      commune\n" +
"                     WHERE  region.id_region::text = district.id_region::text \n" +
"                    AND district.id_district::text = commune.id_district::text \n" +
"                    AND parcelle_cf.c_district::text = district.code_district::text \n" +
"                    AND parcelle_cf.type_op  " + newOp +
"       \n             AND parcelle_cf.c_commune::text = commune.code_commune::text\n" +
"					AND parcelle_cf.anomalie IS TRUE\n" +
"                  AND region.nom = ?  \n" +
"					GROUP BY region, district, commune, numero_demande, c_parcelle, observation\n" +
"					ORDER BY region, district, commune, c_parcelle";

                st = connectDatabase.prepareStatement(sql);    
                st.setString(1, reg);
                rs = st.executeQuery();
                
                enTeteTableau.add("Région");
                enTeteTableau.add("District");
                enTeteTableau.add("Commune");
                enTeteTableau.add("Numéro_demande");
                enTeteTableau.add("c_parcelle");
                enTeteTableau.add("Type_anomalie");
                enTeteTableau.add("Description");
                
            }

//System.out.println("RST RS VAUT = " +  st);
            

                try{
                    
                    // CREATION DU FICHIER
                    String nameOfSheet = "AnomaliesVecto";
                    
                    
                    // REMPLISSAGE DANS LE FICHIER
                    File src = new File(realPath);
                    FileInputStream fis = new FileInputStream(src);
                    XSSFWorkbook  wb = new XSSFWorkbook(fis);
                    
                    wb.createSheet(nameOfSheet);
                    XSSFSheet sheet = wb.getSheet(nameOfSheet);
                    

                    // MISE EN PAGE ET MISE EN FORME DU FICHIER
                    sheet.getHeader().setRight("LISTE DES ANOMALIES VECTOS");
                    sheet.getHeader().setLeft("CASEF / GEOX2");
                    sheet.getFooter().setLeft(nomAntenne);
                    sheet.getFooter().setRight("Opération "+ Formats.ConvertOcmToOcfm(this.TYPE_OPERATION).toUpperCase());

                    sheet.getPrintSetup().setLandscape(true);
                    PrintSetup printsetup = sheet.getPrintSetup();
                    sheet.getPrintSetup().setPaperSize(printsetup.A4_PAPERSIZE);

                    String[] cellAFixer = ("$1:$1").split(":");
                    CellReference startCellFixed = new CellReference(cellAFixer[0]);
                    CellReference endCellFixed = new CellReference(cellAFixer[1]);
                    CellRangeAddress addressCellAFixer = new CellRangeAddress(startCellFixed.getRow(),
                    endCellFixed.getRow(), startCellFixed.getCol(), endCellFixed.getCol());

                    sheet.setRepeatingRows(addressCellAFixer);

                
                
                    // FIN MISE EN PAGE ET MISE EN FORME DU FICHIER

                    // create table with data
                    XSSFCellStyle cadre = wb.createCellStyle();
                    cadre.setBorderBottom(BorderStyle.THIN);
                    cadre.setBorderTop(BorderStyle.THIN);
                    cadre.setBorderLeft(BorderStyle.THIN);
                    cadre.setBorderRight(BorderStyle.THIN);
                
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

                    cellStyleBold.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                    cellStyleBold.setFillPattern(FillPatternType.SOLID_FOREGROUND); 

                    Row headerRow0 = sheet.createRow(0);


                    for (int i = 0; i < enTeteTableau.size(); i++) {

                        Cell headerCell0 = headerRow0.createCell(i);
                        headerCell0.setCellValue(enTeteTableau.get(i));
                        headerCell0.setCellStyle(cadre);
                        headerCell0.setCellStyle(cellStyleBold);         
                    }

                    int n = 1;

                    while (rs.next()) {

                        RowResultSet++;

                            
                            
                            if (nomAtelier.equals("ATS")) {
                            
                                
                                if (rs.getString("observation").length() == 2) {
                                    
                                    Row headerRow7 = sheet.createRow(n);

                                    Cell headerCell7 = headerRow7.createCell(0);
                                    headerCell7.setCellValue(rs.getString("region"));

                                    Cell headerCell8 = headerRow7.createCell(1);
                                    headerCell8.setCellValue(rs.getString("district"));

                                    Cell headerCell9 = headerRow7.createCell(2);
                                    headerCell9.setCellValue(rs.getString("commune"));

                                    Cell headerCel20 = headerRow7.createCell(3);
                                    headerCel20.setCellValue(rs.getString("numero_demande"));

                                    Cell headerCel21 = headerRow7.createCell(4);
                                    headerCel21.setCellValue(rs.getString("c_parcelle"));

                                    Cell headerCel22 = headerRow7.createCell(5);
                                    
                                    Cell headerCel23 = headerRow7.createCell(6);
                                    
                                    
                                    if (rs.getString("observation").equals("03") || rs.getString("observation").equals("05") || rs.getString("observation").equals("06")
                                            || rs.getString("observation").equals("07") || rs.getString("observation").equals("08") || rs.getString("observation").equals("09") || rs.getString("observation").equals("10")
                                            || rs.getString("observation").equals("11") || rs.getString("observation").equals("12") || rs.getString("observation").equals("13") || rs.getString("observation").equals("14")
                                            || rs.getString("observation").equals("15") || rs.getString("observation").equals("16") || rs.getString("observation").equals("17") || rs.getString("observation").equals("18")
                                            || rs.getString("observation").equals("20") || rs.getString("observation").equals("21") || rs.getString("observation").equals("22")  ) {
                                        
                                        headerCel22.setCellValue("Anomalie bloquante");
                                        headerCel23.setCellValue(AnomaliesBloquantesAvecType.get(rs.getString("observation")));
                                        
                                        
                                    }else if (rs.getString("observation").equals("01") || rs.getString("observation").equals("02") || rs.getString("observation").equals("04")
                                            || rs.getString("observation").equals("25") || rs.getString("observation").equals("26") || rs.getString("observation").equals("27") ){
                                        
                                        headerCel22.setCellValue("Anomalie Non Bloquante");
                                        headerCel23.setCellValue(AnomaliesNonBloquantesAvecType.get(rs.getString("observation")));

                                    }else{
                                        headerCel22.setCellValue("Autre Information");
                                        headerCel23.setCellValue(AutresTypesAvecType.get(rs.getString("observation")));
                                    }

                                    
                                    //headerCel23.setCellValue(ListesAnomaliesVectos.get(rs.getString("observation")));
                                }else{
                                    
                                    
                                    Row headerRow7 = sheet.createRow(0);
                                    
                                    Cell headerCell7 = headerRow7.createCell(0);
                                    headerCell7.setCellValue(rs.getString("region"));

                                    Cell headerCell8 = headerRow7.createCell(1);
                                    headerCell8.setCellValue(rs.getString("district"));

                                    Cell headerCell9 = headerRow7.createCell(2);
                                    headerCell9.setCellValue(rs.getString("commune"));

                                    Cell headerCel20 = headerRow7.createCell(3);
                                    headerCel20.setCellValue(rs.getString("numero_demande"));

                                    Cell headerCel21 = headerRow7.createCell(4);
                                    headerCel21.setCellValue(rs.getString("c_parcelle"));
                                    
                                    
                                    cutObsAnomalie.add(rs.getString("observation").split("\\"));
                                    

                                    
                                    for (int i = 0; i < cutObsAnomalie.size(); i++) {
                                        
                                    //Row headerRow7 = sheet.createRow(0);
                                        
                                        
                                    
                                    Cell headerCel22 = headerRow7.createCell(5+i);
                                    Cell headerCel23 = headerRow7.createCell(6+i);
                                        
                                        if (rs.getString("observation").equals("03") || rs.getString("observation").equals("05") || rs.getString("observation").equals("06")
                                                || rs.getString("observation").equals("07") || rs.getString("observation").equals("08") || rs.getString("observation").equals("09") || rs.getString("observation").equals("10")
                                                || rs.getString("observation").equals("11") || rs.getString("observation").equals("12") || rs.getString("observation").equals("13") || rs.getString("observation").equals("14")
                                                || rs.getString("observation").equals("15") || rs.getString("observation").equals("16") || rs.getString("observation").equals("17") || rs.getString("observation").equals("18")
                                                || rs.getString("observation").equals("20") || rs.getString("observation").equals("21") || rs.getString("observation").equals("22")  ) {

                                            headerCel22.setCellValue("Anomalie bloquante");
                                            headerCel23.setCellValue(AnomaliesBloquantesAvecType.get(rs.getString("observation")));


                                        }else if (rs.getString("observation").equals("01") || rs.getString("observation").equals("02") || rs.getString("observation").equals("04")
                                                || rs.getString("observation").equals("25") || rs.getString("observation").equals("26") || rs.getString("observation").equals("27") ){

                                            headerCel22.setCellValue("Anomalie Non Bloquante");
                                            headerCel23.setCellValue(AnomaliesNonBloquantesAvecType.get(rs.getString("observation")));

                                        }else{
                                            headerCel22.setCellValue("Autre Information");
                                            headerCel23.setCellValue(AutresTypesAvecType.get(rs.getString("observation")));
                                        }
                                        
                                    }


                                    //headerCel22.setCellValue("IL FAUT TRAITER");
                                    
                                    
                                    //headerCel23.setCellValue(rs.getString("observation"));
                                }
                                
                            }else if(nomAtelier.equals("VAK")){

                            }else{
                                
                                // ATELIER AMA

                            }
                            



                            n++;
                    }

            
                String[] CelluleAMettreDeBordure = {"A", "B", "C", "D", "E", "F", "G"};


                for(int i = 0; i < CelluleAMettreDeBordure.length; i++){

                    for(int a = 2; a<n+1; a++){

                        String rangCellule = CelluleAMettreDeBordure[i]+a;

                        RegionUtil.setBorderBottom(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderTop(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderRight(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderLeft(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet); 
                    }

                }

  
                    FileOutputStream fout = new FileOutputStream(src);
                    
                    wb.write(fout);
                    wb.close();
                    //out.close();
                    fout.close();


                }catch(Exception createFileErreur){

                    //System.out.println("ERREUR DANS  get registre anomalie = " +createFileErreur.getMessage());
                    
                    JOptionPane.showMessageDialog(null, "Classes export registre anomalies erreur",createFileErreur.getMessage(), JOptionPane.INFORMATION_MESSAGE);
                }
                     
            rs.close();
            st.close();
            
            if(RowResultSet == 0){
                //System.out.println("val fiale de RowResultSet = " + RowResultSet);
                retour.add("empty-anomalie-vecto");
                retour.add(realPath);
                //Files.deleteIfExists(Paths.get(realPath));
                
                //JOptionPane.showMessageDialog(null, "Aucune anomalie bloquante a été trouvé sur la : \n\ncommune: "+com+"\n"+"Fokontany : "+fkt+"\n"+"Hameau : "+hameau+"\n"+"Type d'opération : "+this.op, "Export du registre d'anonamlie impossible", JOptionPane.INFORMATION_MESSAGE);
 
            }else{
                retour.add("success-anomalie-vecto");
                retour.add(realPath);
                //JOptionPane.showMessageDialog(null, "Export registre d'anomalie effectué avec succès !", "Export registre d'anomalie effectué avec succès", JOptionPane.INFORMATION_MESSAGE);
                // ouverture de l'emplacement selectionner par l'utiisateur
                //Desktop.getDesktop().open(new File(path));
            }
  
        } catch (Exception ex) {
            //ex.printStackTrace();
            //throw new RuntimeException();
            retour.add("error-anomalie-vecto");
            retour.add("Error executing query: " +ex.getMessage());
            JOptionPane.showMessageDialog(null, "Impossible de lancer la requette de récupération des anomalies\n\nRetour : "+ex.getMessage(), "Erreur SQL trouvé", JOptionPane.INFORMATION_MESSAGE);
        }
        
        //System.out.println(demandes);
        return retour;
        
    }




public List<String> GetAnomaliesSaisieCSVLola(String reg, String path){

        List retour = new ArrayList();
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_'a'_HH'h'mm'mn'ss'sec'");
        Date date = new Date(System.currentTimeMillis());
        
        
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        String dateAujourdhui = dateFormat.format(new Date());
    
    
        String realPath = "";
        String nomAtelier = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getNomAtelier();
        String nomAntenne = "";
        String sql = "";
        List <String> enTeteTableau = new ArrayList();
        
        switch (nomAtelier){
            case "ATS":
                nomAntenne= "ANTENNE : ATSINANANA";
                break;
            case "VAK":
                nomAntenne= "ANTENNE : VAKINANKARATRA";
                break;
            default:
                if (reg.toUpperCase().equals("ANALAMANGA")) {
                    nomAntenne= "ANTENNE : ANALAMANGA";
                }else{
                    nomAntenne= "ANTENNE : ITASY";
                }
                
                break;
        }
        
        int RowResultSet = 0;
        
        try {
            
            if (reg.equals("all")) {
                
                sql = " SELECT region.nom AS region, district.nom AS District, commune.nom AS commune, demande.id_registre AS numero_demande,\n" +
                "lst_type_anomalie.valeur AS type_anomalie, anomalie.description AS description\n" +
                "FROM demande, district, commune, fokontany, hameau, region, anomalie, lst_type_anomalie\n" +
                "WHERE   demande.id_hameau=hameau.id_hameau\n" +
                "AND region.id_region::text = district.id_region::text \n" +
                "AND district.id_district = commune.id_district\n" +
                "AND hameau.id_fokontany = fokontany.id_fokontany\n" +
                "AND fokontany.id_commune = commune.id_commune\n" +
                "AND district.id_region = region.id_region\n" +
                "AND lst_type_anomalie.id_lst_type_anomalie = anomalie.id_lst_type_anomalie\n" +
                "AND  demande.id_demande = anomalie.id_demande\n" +
                "AND demande.val_anomalie IS TRUE\n" +
                "AND demande.type_op = ? \n" +
                "AND demande.id_certificat IS NULL \n" +
                "AND lst_type_anomalie.valeur NOT LIKE '%Formulaire corri%'\n" +
                "GROUP BY region, district, commune,fokontany.code_fokontany, hameau.code_hameau, demande.num_registre, numero_demande, type_anomalie,description\n" +
                "ORDER BY district, commune,fokontany.code_fokontany, hameau.code_hameau, demande.num_registre, numero_demande, type_anomalie,description";
                
                st = connectDatabase.prepareStatement(sql);    
                st.setString(1, Formats.ConvertOcfmToOcm(this.TYPE_OPERATION).toLowerCase());
                rs = st.executeQuery();
                
                enTeteTableau.add("Région");
                enTeteTableau.add("District");
                enTeteTableau.add("Commune");
                enTeteTableau.add("Numéro_demande");
                enTeteTableau.add("Type_anomalie");
                enTeteTableau.add("Description");
                
                realPath = path+"\\"+this.TYPE_OPERATION.toUpperCase()+"_RegAnomalieCSV_Rapport_Hebdo_"+formatter.format(date)+"_ALL_REGION_"+".xlsx";
                
            }else{
                
                sql = " SELECT region.nom AS region, district.nom AS District, commune.nom AS commune, demande.id_registre AS numero_demande,\n" +
                "lst_type_anomalie.valeur AS type_anomalie, anomalie.description AS description\n" +
                "FROM demande, district, commune, fokontany, hameau, region, anomalie, lst_type_anomalie\n" +
                "WHERE   demande.id_hameau=hameau.id_hameau\n" +
                "AND region.id_region::text = district.id_region::text \n" +
                "AND district.id_district = commune.id_district\n" +
                "AND hameau.id_fokontany = fokontany.id_fokontany\n" +
                "AND fokontany.id_commune = commune.id_commune\n" +
                "AND district.id_region = region.id_region\n" +
                "AND lst_type_anomalie.id_lst_type_anomalie = anomalie.id_lst_type_anomalie\n" +
                "AND  demande.id_demande = anomalie.id_demande\n" +
                "AND demande.val_anomalie IS TRUE\n" +
                "  AND region.nom = ? \n" +
                "AND demande.type_op = ? \n" +
                "AND demande.id_certificat IS NULL \n" +
                "AND lst_type_anomalie.valeur NOT LIKE '%Formulaire corri%'\n" +
                "GROUP BY region, district, commune,fokontany.code_fokontany, hameau.code_hameau, demande.num_registre, numero_demande, type_anomalie,description\n" +
                "ORDER BY district, commune,fokontany.code_fokontany, hameau.code_hameau, demande.num_registre, numero_demande, type_anomalie,description";

                st = connectDatabase.prepareStatement(sql);    
                st.setString(1, reg);
                st.setString(2, Formats.ConvertOcfmToOcm(this.TYPE_OPERATION).toLowerCase());
                rs = st.executeQuery();
                
                enTeteTableau.add("Région");
                enTeteTableau.add("District");
                enTeteTableau.add("Commune");
                enTeteTableau.add("Numéro_demande");
                enTeteTableau.add("Type_anomalie");
                enTeteTableau.add("Description");
                
                realPath = path+"\\"+this.TYPE_OPERATION.toUpperCase()+"_RegAnomaliesCSV_Rapport_Hebdo_"+formatter.format(date)+"_Reg_"+reg+".xlsx";
            }
            

                try{
                    
                    // CREATION DU FICHIER
                    String nameOfSheet = "AnomaliesSaisie";
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
                    

                // MISE EN PAGE ET MISE EN FORME DU FICHIER
                sheet.getHeader().setRight("LISTE DES ANOMALIES SAISIE");
                sheet.getHeader().setLeft("CASEF / GEOX2");
                sheet.getFooter().setLeft(nomAntenne);
                sheet.getFooter().setRight("Opération "+Formats.ConvertOcmToOcfm(this.TYPE_OPERATION).toUpperCase());
                
                sheet.getPrintSetup().setLandscape(true);
                PrintSetup printsetup = sheet.getPrintSetup();
                sheet.getPrintSetup().setPaperSize(printsetup.A4_PAPERSIZE);

                String[] cellAFixer = ("$1:$1").split(":");
                CellReference startCellFixed = new CellReference(cellAFixer[0]);
                CellReference endCellFixed = new CellReference(cellAFixer[1]);
                CellRangeAddress addressCellAFixer = new CellRangeAddress(startCellFixed.getRow(),
                endCellFixed.getRow(), startCellFixed.getCol(), endCellFixed.getCol());

                sheet.setRepeatingRows(addressCellAFixer);
                
                
                
                // FIN MISE EN PAGE ET MISE EN FORME DU FICHIER
                
                // create table with data
                XSSFCellStyle cadre = wb.createCellStyle();
                cadre.setBorderBottom(BorderStyle.THIN);
                cadre.setBorderTop(BorderStyle.THIN);
                cadre.setBorderLeft(BorderStyle.THIN);
                cadre.setBorderRight(BorderStyle.THIN);
                
            //RegionUtil.setBorderBottom(BorderStyle.DOUBLE,
            //CellRangeAddress.valueOf("A1:B7"), sheet);

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
            
            cellStyleBold.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyleBold.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
            
            

            Row headerRow0 = sheet.createRow(0);
            
            for (int i = 0; i < enTeteTableau.size(); i++) {
                
                Cell headerCell0 = headerRow0.createCell(i);
                headerCell0.setCellValue(enTeteTableau.get(i));
                headerCell0.setCellStyle(cadre);
                headerCell0.setCellStyle(cellStyleBold);         
            }



        int n = 1;
        
            while (rs.next()) {
                
                RowResultSet++;
                    
                    Row headerRow7 = sheet.createRow(n);

                    Cell headerCell7 = headerRow7.createCell(0);
                    headerCell7.setCellValue(rs.getString("region"));

                    Cell headerCell8 = headerRow7.createCell(1);
                    headerCell8.setCellValue(rs.getString("district"));
                    
                    Cell headerCell9 = headerRow7.createCell(2);
                    headerCell9.setCellValue(rs.getString("commune"));
                    
                    Cell headerCel20 = headerRow7.createCell(3);
                    headerCel20.setCellValue(rs.getString("numero_demande"));
                    
                    Cell headerCel21 = headerRow7.createCell(4);
                    headerCel21.setCellValue(rs.getString("type_anomalie"));
                    
                    Cell headerCel22 = headerRow7.createCell(5);
                    headerCel22.setCellValue(rs.getString("description"));
                    
                    n++;
            }
            
            
                String[] CelluleAMettreDeBordure = {"A", "B", "C", "D", "E", "F"};


                for(int i = 0; i < CelluleAMettreDeBordure.length; i++){

                    for(int a = 2; a<n+1; a++){

                        String rangCellule = CelluleAMettreDeBordure[i]+a;

                        RegionUtil.setBorderBottom(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderTop(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderRight(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderLeft(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet); 
                    }

                }

  
                    FileOutputStream fout = new FileOutputStream(src);
                    
                    wb.write(fout);
                    wb.close();
                    out.close();
                    fout.close();
                    


                }catch(Exception createFileErreur){

                    //System.out.println("ERREUR DANS  get registre anomalie = " +createFileErreur.getMessage());
                    
                    JOptionPane.showMessageDialog(null, "Classes export registre anomalies csv erreur",createFileErreur.getMessage(), JOptionPane.INFORMATION_MESSAGE);
                }
                     
            rs.close();
            st.close();
            
            if(RowResultSet == 0){
                //System.out.println("val fiale de RowResultSet = " + RowResultSet);
                retour.add("empty-anomalie-bloquante");
                retour.add(realPath);
                //Files.deleteIfExists(Paths.get(realPath));
                
                //JOptionPane.showMessageDialog(null, "Aucune anomalie bloquante a été trouvé sur la : \n\ncommune: "+com+"\n"+"Fokontany : "+fkt+"\n"+"Hameau : "+hameau+"\n"+"Type d'opération : "+this.op, "Export du registre d'anonamlie impossible", JOptionPane.INFORMATION_MESSAGE);
 
            }else{
                retour.add("success-anomalie-bloquante");
                retour.add(realPath);
                //JOptionPane.showMessageDialog(null, "Export registre d'anomalie effectué avec succès !", "Export registre d'anomalie effectué avec succès", JOptionPane.INFORMATION_MESSAGE);
                // ouverture de l'emplacement selectionner par l'utiisateur
                //Desktop.getDesktop().open(new File(path));
            }
  
        } catch (Exception ex) {
            //ex.printStackTrace();
            //throw new RuntimeException();
            retour.add("error-anomalie-bloquante");
            retour.add("Error executing query: " +ex.getMessage());
            JOptionPane.showMessageDialog(null, "Impossible de lancer la requette de récupération des anomalies\n\nRetour : "+ex.getMessage(), "Erreur SQL trouvé", JOptionPane.INFORMATION_MESSAGE);
        }
        
        //System.out.println(demandes);
        return retour;
        
    }



public List<String> GetAnomaliesBloquante(String reg, String c_dist, String dist, String c_com, String com, String c_fkt, String fkt, String c_hameau, String hameau, String path){

        List retour = new ArrayList();
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_'a'_HH'h'mm'mn'ss'sec'");
        Date date = new Date(System.currentTimeMillis());
        
        
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        String dateAujourdhui = dateFormat.format(new Date());
    
    
        String realPath = path+"\\"+this.TYPE_OPERATION.toUpperCase()+"_RegAnomalieSaisie_"+formatter.format(date)+"_Reg_"+Formats.ConvertSlashToUnderscore(reg)+"_Com_"+Formats.ConvertSlashToUnderscore(com)+"_FKT_"+Formats.ConvertSlashToUnderscore(fkt)+"_Ham_"+Formats.ConvertSlashToUnderscore(hameau)+".xlsx";
        String nomAtelier = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getNomAtelier();
        String nomAntenne = "";
        
        switch (nomAtelier){
            case "ATS":
                nomAntenne= "ANTENNE : ATSINANANA";
                break;
            case "VAK":
                nomAntenne= "ANTENNE : VAKINANKARATRA";
                break;
            default:
                if (reg.toUpperCase().equals("ANALAMANGA")) {
                    nomAntenne= "ANTENNE : ANALAMANGA";
                }else{
                    nomAntenne= "ANTENNE : ITASY";
                }
                
                break;
        }
        
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
            st.setString(6, Formats.ConvertOcfmToOcm(this.TYPE_OPERATION).toLowerCase());
            rs = st.executeQuery();

                try{
                    
                    // CREATION DU FICHIER
                    String nameOfSheet = "anomalies_bloquantes";
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
                    

                // MISE EN PAGE ET MISE EN FORME DU FICHIER
                sheet.getHeader().setRight("Fiche 9 – LISTE DES ANOMALIES SUR LES FORMULAIRES");
                sheet.getHeader().setLeft("CASEF / GEOX2");
                sheet.getFooter().setLeft(nomAntenne);
                sheet.getFooter().setCenter("LISTE(S) DE(S) ANOMALIE(S) BLOQUANTE(S)");
                sheet.getFooter().setRight("Opération "+Formats.ConvertOcmToOcfm(this.TYPE_OPERATION).toUpperCase());
                
                sheet.getPrintSetup().setLandscape(true);
                PrintSetup printsetup = sheet.getPrintSetup();
                sheet.getPrintSetup().setPaperSize(printsetup.A3_PAPERSIZE);

                String[] cellAFixer = ("$1:$6").split(":");
                CellReference startCellFixed = new CellReference(cellAFixer[0]);
                CellReference endCellFixed = new CellReference(cellAFixer[1]);
                CellRangeAddress addressCellAFixer = new CellRangeAddress(startCellFixed.getRow(),
                endCellFixed.getRow(), startCellFixed.getCol(), endCellFixed.getCol());

                sheet.setRepeatingRows(addressCellAFixer);
                
                
                
                // FIN MISE EN PAGE ET MISE EN FORME DU FICHIER
                
                // create table with data
                XSSFCellStyle cadre = wb.createCellStyle();
                cadre.setBorderBottom(BorderStyle.THIN);
                cadre.setBorderTop(BorderStyle.THIN);
                cadre.setBorderLeft(BorderStyle.THIN);
                cadre.setBorderRight(BorderStyle.THIN);
                
            //RegionUtil.setBorderBottom(BorderStyle.DOUBLE,
            //CellRangeAddress.valueOf("A1:B7"), sheet);

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
            
            cellStyleBold.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyleBold.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
            
            Cell headerCell0 = headerRow0.createCell(0);
            headerCell0.setCellValue("Commune :");
            headerCell0.setCellStyle(cadre);
            headerCell0.setCellStyle(cellStyleBold);

            
            headerCell0 = headerRow0.createCell(1);
            headerCell0.setCellValue(com);
            headerCell0.setCellStyle(cadre);

 
            headerCell0 = headerRow0.createCell(2);
            headerCell0.setCellValue("Code Com : " + c_com);
            headerCell0.setCellStyle(cadre);

            //headerCell0 = headerRow0.createCell(3);
            //headerCell0.setCellValue(c_com);

            headerCell0 = headerRow0.createCell(4);
            headerCell0.setCellValue("Fokontany :");
            headerCell0.setCellStyle(cadre);
            headerCell0.setCellStyle(cellStyleBold);
            
        
            headerCell0 = headerRow0.createCell(5);
            headerCell0.setCellValue(fkt);
            headerCell0.setCellStyle(cadre);

            headerCell0 = headerRow0.createCell(6);
            headerCell0.setCellValue("Code FKT :");
            headerCell0.setCellStyle(cadre);
            headerCell0.setCellStyle(cellStyleBold);
            

            headerCell0 = headerRow0.createCell(7);
            headerCell0.setCellValue(c_fkt);
            headerCell0.setCellStyle(cadre);
            
            

            headerCell0 = headerRow0.createCell(8);
            headerCell0.setCellValue("N° Equipe :");
            headerCell0.setCellStyle(cadre);
            headerCell0.setCellStyle(cellStyleBold);
            

            //headerCell0 = headerRow0.createCell(9);
            //headerCell0.setCellValue("xxxxx n° equipe");


            Row headerRow1 = sheet.createRow(1);

            Cell headerCell1 = headerRow1.createCell(0);
            headerCell1.setCellValue("Hameau :");
            headerCell1.setCellStyle(cadre);
            headerCell1.setCellStyle(cellStyleBold);
            


            headerCell1 = headerRow1.createCell(1);
            headerCell1.setCellValue(hameau);
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(2);
            headerCell1.setCellValue("Code Ham : " + (String) c_hameau);
            headerCell1.setCellStyle(cadre);

            //headerCell1 = headerRow1.createCell(3);
            //headerCell1.setCellValue(c_hameau);

            headerCell1 = headerRow1.createCell(4);
            headerCell1.setCellValue("Atelier :");
            headerCell1.setCellStyle(cadre);
            headerCell1.setCellStyle(cellStyleBold);
            

            
            headerCell1 = headerRow1.createCell(5);
            headerCell1.setCellValue(nomAtelier);
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(6);
            headerCell1.setCellValue("Date d’envoi :");
            headerCell1.setCellStyle(cadre);
            headerCell1.setCellStyle(cellStyleBold);
            

        
        
            headerCell1 = headerRow1.createCell(7);
            headerCell1.setCellValue(dateAujourdhui);
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(8);
            headerCell1.setCellValue("Date de retour :");
            headerCell1.setCellStyle(cadre);
            headerCell1.setCellStyle(cellStyleBold);
            


            Row headerRow4 = sheet.createRow(4);

            Cell headerCell4 = headerRow4.createCell(0);
            headerCell4.setCellValue("Atelier");
            headerCell4.setCellStyle(cadre);
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
            headerCell4.setCellStyle(cadre);
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
            headerCell4.setCellStyle(cadre);
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
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(1);
            headerCell5.setCellValue("N° Demande");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(2);
            headerCell5.setCellValue("Description anomalie(s) - Bloquante");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(3);
            headerCell5.setCellValue("Corrigé");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            
 
            headerCell5 = headerRow5.createCell(4);
            headerCell5.setCellValue("Non corrigé");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(5);
            headerCell5.setCellValue("Date correction");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(6);
            headerCell5.setCellValue("Observation sur la correction");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(7);
            headerCell5.setCellValue("Signature ADA");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(8);
            headerCell5.setCellValue("Date");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            
            

            headerCell5 = headerRow5.createCell(9);
            headerCell5.setCellValue("Signature AGF");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(10);
            headerCell5.setCellValue("Date");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(11);
            headerCell5.setCellValue("Signature AFO");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

        int n = 6;
        
            while (rs.next()) {
                
                RowResultSet++;
                    
                    Row headerRow7 = sheet.createRow(n);

                    Cell headerCell7 = headerRow7.createCell(0);
                    headerCell7.setCellValue(rs.getString("lot") + " (Equipe : " +rs.getString("code_equipe") + ")");
                    headerCell7.setCellStyle(cadre);


                    Cell headerCell8 = headerRow7.createCell(1);
                    headerCell8.setCellValue(rs.getString("numero_demande"));
                    headerCell8.setCellStyle(cadre);

                    Cell headerCell9 = headerRow7.createCell(2);
                    headerCell9.setCellValue(rs.getString("anomalie_description"));
                    headerCell9.setCellStyle(cadre);
                    
                    
                    n++;
            }
            
            
                        RegionUtil.setBorderBottom(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J1:J1"), sheet);

                        RegionUtil.setBorderTop(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J1:J1"), sheet);

                        RegionUtil.setBorderRight(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J1:J1"), sheet);

                        RegionUtil.setBorderLeft(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J1:J1"), sheet);
                        
                        
            
                        RegionUtil.setBorderBottom(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J2:J2"), sheet);

                        RegionUtil.setBorderTop(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J2:J2"), sheet);

                        RegionUtil.setBorderRight(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J2:J2"), sheet);

                        RegionUtil.setBorderLeft(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J2:J2"), sheet);
            
            
                String[] CelluleAMettreDeBordure = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};


                for(int i = 0; i < CelluleAMettreDeBordure.length; i++){

                    for(int a = 5; a<n+1; a++){

                        String rangCellule = CelluleAMettreDeBordure[i]+a;

                        RegionUtil.setBorderBottom(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderTop(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderRight(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderLeft(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet); 
                    }

                }

  
                    FileOutputStream fout = new FileOutputStream(src);
                    
                    wb.write(fout);
                    wb.close();
                    out.close();
                    fout.close();


                }catch(Exception createFileErreur){

                    //System.out.println("ERREUR DANS  get registre anomalie = " +createFileErreur.getMessage());
                    
                    JOptionPane.showMessageDialog(null, "Classes export registre anomalies erreur",createFileErreur.getMessage(), JOptionPane.INFORMATION_MESSAGE);
                }
                     
            rs.close();
            st.close();
            
            if(RowResultSet == 0){
                //System.out.println("val fiale de RowResultSet = " + RowResultSet);
                retour.add("empty-anomalie-bloquante");
                retour.add(realPath);
                //Files.deleteIfExists(Paths.get(realPath));
                
                //JOptionPane.showMessageDialog(null, "Aucune anomalie bloquante a été trouvé sur la : \n\ncommune: "+com+"\n"+"Fokontany : "+fkt+"\n"+"Hameau : "+hameau+"\n"+"Type d'opération : "+this.op, "Export du registre d'anonamlie impossible", JOptionPane.INFORMATION_MESSAGE);
 
            }else{
                retour.add("success-anomalie-bloquante");
                retour.add(realPath);
                //JOptionPane.showMessageDialog(null, "Export registre d'anomalie effectué avec succès !", "Export registre d'anomalie effectué avec succès", JOptionPane.INFORMATION_MESSAGE);
                // ouverture de l'emplacement selectionner par l'utiisateur
                //Desktop.getDesktop().open(new File(path));
            }
  
        } catch (Exception ex) {
            //ex.printStackTrace();
            //throw new RuntimeException();
            retour.add("error-anomalie-bloquante");
            retour.add("Error executing query: " +ex.getMessage());
            JOptionPane.showMessageDialog(null, "Impossible de lancer la requette de récupération des anomalies\n\nRetour : "+ex.getMessage(), "Erreur SQL trouvé", JOptionPane.INFORMATION_MESSAGE);
        }
        
        //System.out.println(demandes);
        return retour;
        
    }



public List<String> GetAnomaliesNonBloquante(String reg, String c_dist, String dist, String c_com, String com, String c_fkt, String fkt, String c_hameau, String hameau, String path){

        List retour = new ArrayList();

        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        String dateAujourdhui = dateFormat.format(new Date());
    
    
        String realPath = path;
        String nomAtelier = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getNomAtelier();
        String nomAntenne = "";
        
        switch (nomAtelier){
            case "ATS":
                nomAntenne= "ANTENNE : ATSINANANA";
                break;
            case "VAK":
                nomAntenne= "ANTENNE : VAKINANKARATRA";
                break;
            default:
                if (reg.toUpperCase().equals("ANALAMANGA")) {
                    nomAntenne= "ANTENNE : ANALAMANGA";
                }else{
                    nomAntenne= "ANTENNE : ITASY";
                }
                
                break;
        }
        
        int RowResultSet = 0;
        
        try {
            
            String sql = " SELECT region.nom AS region,\n" +
"             commune.code_commune,\n" +
"             commune.nom AS commune,\n" +
"             fokontany.code_fokontany,\n" +
"             fokontany.nom AS fokontany,\n" +
"             hameau.code_hameau,\n" +
"             hameau.nom AS hameau,\n" +
"             demande.code_equipe,\n" +
"             demande.num_registre ,\n" +
"             demande.id_registre as numero_demande,\n" +
"             demande.id_parcelle,\n" +
"             persphys.nom AS nom_demandeur,\n" +
"             persphys.prenom AS prenom_demandeur,\n" +
"             anomalie.description AS anomalie_description,\n" +
"             utilisateur.login,\n" +
"             demande.lot,\n" +
"             demande.planche_plof\n" +
"            FROM hameau,\n" +
"             anomalie,\n" +
"             demande,\n" +
"             utilisateur,\n" +
"             lst_type_anomalie,\n" +
"             fokontany,\n" +
"             commune,\n" +
"             district,\n" +
"             region,\n" +
"             proprietaire_pp,\n" +
"             persphys\n" +
"           WHERE hameau.id_hameau::text = demande.id_hameau::text \n" +
"           AND demande.id_demande::text = anomalie.id_demande::text \n" +
"           AND demande.demande_user::text = utilisateur.id_utilisateur::text \n" +
"           AND fokontany.id_fokontany::text = hameau.id_fokontany::text \n" +
"           AND commune.id_commune::text = fokontany.id_commune::text \n" +
"           AND district.id_district::text = commune.id_district::text \n" +
"           AND region.id_region::text = district.id_region::text \n" +
"           AND lst_type_anomalie.id_lst_type_anomalie::text = anomalie.id_lst_type_anomalie::text \n" +
"           AND proprietaire_pp.id_demande::text = demande.id_demande::text \n" +
"           AND proprietaire_pp.id_persphys::text = persphys.id_persphys::text \n" +
"           AND proprietaire_pp.type_demandeur::text = 'DM'::text \n" +
"		   AND anomalie.id_lst_type_anomalie = lst_type_anomalie.id_lst_type_anomalie\n" +
"           AND demande.val_anomalie IS FALSE \n" +
"           AND anomalie.resolu IS TRUE \n" +
"           AND demande.cf_annule IS NOT TRUE\n" +
"		   AND lst_type_anomalie.valeur NOT LIKE '%ormulaire corrig%'\n" +
"           AND region.nom = ? \n" +
"           AND district.nom = ? \n" +
"           AND commune.nom = ? \n" +
"           AND fokontany.nom = ? \n" +
"           AND hameau.nom = ? \n" +
"           AND demande.type_op = ? \n" +
"           ORDER BY demande.num_registre";
            
            st = connectDatabase.prepareStatement(sql);    
            st.setString(1, reg);
            st.setString(2, dist);
            st.setString(3, com);
            st.setString(4, fkt);
            st.setString(5, hameau);
            st.setString(6, Formats.ConvertOcfmToOcm(TYPE_OPERATION).toLowerCase());
            rs = st.executeQuery();

                try{
                    
                    // CREATION DU FICHIER
                    String nameOfSheet = "anomalies_non_bloquantes";
                    

                    
                    // REMPLISSAGE DANS LE FICHIER
                    File src = new File(realPath);
                    FileInputStream fis = new FileInputStream(src);
                    XSSFWorkbook  wb = new XSSFWorkbook(fis);
                    
                    wb.createSheet(nameOfSheet);
                    XSSFSheet sheet = wb.getSheet(nameOfSheet);
                    

                // MISE EN PAGE ET MISE EN FORME DU FICHIER
                sheet.getHeader().setRight("Fiche 9 – LISTE DES ANOMALIES SUR LES FORMULAIRES");
                sheet.getHeader().setLeft("CASEF / GEOX2");
                sheet.getFooter().setLeft(nomAntenne);
                sheet.getFooter().setCenter("LISTE(S) DE(S) ANOMALIE(S) NON BLOQUANTE(S)");
                sheet.getFooter().setRight("Opération "+ Formats.ConvertOcmToOcfm(this.TYPE_OPERATION).toUpperCase());
                
                sheet.getPrintSetup().setLandscape(true);
                PrintSetup printsetup = sheet.getPrintSetup();
                sheet.getPrintSetup().setPaperSize(printsetup.A3_PAPERSIZE);

                String[] cellAFixer = ("$1:$6").split(":");
                CellReference startCellFixed = new CellReference(cellAFixer[0]);
                CellReference endCellFixed = new CellReference(cellAFixer[1]);
                CellRangeAddress addressCellAFixer = new CellRangeAddress(startCellFixed.getRow(),
                endCellFixed.getRow(), startCellFixed.getCol(), endCellFixed.getCol());

                sheet.setRepeatingRows(addressCellAFixer);
                
                
                
                // FIN MISE EN PAGE ET MISE EN FORME DU FICHIER
                
                // create table with data
                XSSFCellStyle cadre = wb.createCellStyle();
                cadre.setBorderBottom(BorderStyle.THIN);
                cadre.setBorderTop(BorderStyle.THIN);
                cadre.setBorderLeft(BorderStyle.THIN);
                cadre.setBorderRight(BorderStyle.THIN);
                
            //RegionUtil.setBorderBottom(BorderStyle.DOUBLE,
            //CellRangeAddress.valueOf("A1:B7"), sheet);

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
            
            cellStyleBold.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyleBold.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
            
            Cell headerCell0 = headerRow0.createCell(0);
            headerCell0.setCellValue("Commune :");
            headerCell0.setCellStyle(cadre);
            headerCell0.setCellStyle(cellStyleBold);

            
            headerCell0 = headerRow0.createCell(1);
            headerCell0.setCellValue(com);
            headerCell0.setCellStyle(cadre);

 
            headerCell0 = headerRow0.createCell(2);
            headerCell0.setCellValue("Code Com : " + c_com);
            headerCell0.setCellStyle(cadre);

            //headerCell0 = headerRow0.createCell(3);
            //headerCell0.setCellValue(c_com);

            headerCell0 = headerRow0.createCell(4);
            headerCell0.setCellValue("Fokontany :");
            headerCell0.setCellStyle(cadre);
            headerCell0.setCellStyle(cellStyleBold);
            
        
            headerCell0 = headerRow0.createCell(5);
            headerCell0.setCellValue(fkt);
            headerCell0.setCellStyle(cadre);

            headerCell0 = headerRow0.createCell(6);
            headerCell0.setCellValue("Code FKT :");
            headerCell0.setCellStyle(cadre);
            headerCell0.setCellStyle(cellStyleBold);
            

            headerCell0 = headerRow0.createCell(7);
            headerCell0.setCellValue(c_fkt);
            headerCell0.setCellStyle(cadre);
            
            

            headerCell0 = headerRow0.createCell(8);
            headerCell0.setCellValue("N° Equipe :");
            headerCell0.setCellStyle(cadre);
            headerCell0.setCellStyle(cellStyleBold);
            

            //headerCell0 = headerRow0.createCell(9);
            //headerCell0.setCellValue("");


            Row headerRow1 = sheet.createRow(1);

            Cell headerCell1 = headerRow1.createCell(0);
            headerCell1.setCellValue("Hameau :");
            headerCell1.setCellStyle(cadre);
            headerCell1.setCellStyle(cellStyleBold);
            


            headerCell1 = headerRow1.createCell(1);
            headerCell1.setCellValue(hameau);
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(2);
            headerCell1.setCellValue("Code Ham : " + (String) c_hameau);
            headerCell1.setCellStyle(cadre);

            //headerCell1 = headerRow1.createCell(3);
            //headerCell1.setCellValue(c_hameau);

            headerCell1 = headerRow1.createCell(4);
            headerCell1.setCellValue("Atelier :");
            headerCell1.setCellStyle(cadre);
            headerCell1.setCellStyle(cellStyleBold);
            

            
            headerCell1 = headerRow1.createCell(5);
            headerCell1.setCellValue(nomAtelier);
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(6);
            headerCell1.setCellValue("Date d’envoi :");
            headerCell1.setCellStyle(cadre);
            headerCell1.setCellStyle(cellStyleBold);
            

        
        
            headerCell1 = headerRow1.createCell(7);
            headerCell1.setCellValue(dateAujourdhui);
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(8);
            headerCell1.setCellValue("Date de retour :");
            headerCell1.setCellStyle(cadre);
            headerCell1.setCellStyle(cellStyleBold);
            


            Row headerRow4 = sheet.createRow(4);

            Cell headerCell4 = headerRow4.createCell(0);
            headerCell4.setCellValue("Atelier");
            headerCell4.setCellStyle(cadre);
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
            headerCell4.setCellStyle(cadre);
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
            headerCell4.setCellStyle(cadre);
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
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(1);
            headerCell5.setCellValue("N° Demande");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(2);
            headerCell5.setCellValue("Description anomalie(s) - Non Bloquante");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(3);
            headerCell5.setCellValue("Corrigé");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            
 
            headerCell5 = headerRow5.createCell(4);
            headerCell5.setCellValue("Non corrigé");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(5);
            headerCell5.setCellValue("Date correction");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(6);
            headerCell5.setCellValue("Observation sur la correction");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(7);
            headerCell5.setCellValue("Signature ADA");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(8);
            headerCell5.setCellValue("Date");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            
            

            headerCell5 = headerRow5.createCell(9);
            headerCell5.setCellValue("Signature AGF");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(10);
            headerCell5.setCellValue("Date");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(11);
            headerCell5.setCellValue("Signature AFO");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

        int n = 6;
        
            while (rs.next()) {
                
                RowResultSet++;
                    
                    Row headerRow7 = sheet.createRow(n);

                    Cell headerCell7 = headerRow7.createCell(0);
                    headerCell7.setCellValue(rs.getString("lot") + " (Equipe : " +rs.getString("code_equipe") + ")");
                    headerCell7.setCellStyle(cadre);


                    Cell headerCell8 = headerRow7.createCell(1);
                    headerCell8.setCellValue(rs.getString("numero_demande"));
                    headerCell8.setCellStyle(cadre);

                    Cell headerCell9 = headerRow7.createCell(2);
                    headerCell9.setCellValue(rs.getString("anomalie_description"));
                    headerCell9.setCellStyle(cadre);
                    
                    
                    n++;
            }
            
            
            
                        RegionUtil.setBorderBottom(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J1:J1"), sheet);

                        RegionUtil.setBorderTop(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J1:J1"), sheet);

                        RegionUtil.setBorderRight(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J1:J1"), sheet);

                        RegionUtil.setBorderLeft(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J1:J1"), sheet);
                        
                        
            
                        RegionUtil.setBorderBottom(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J2:J2"), sheet);

                        RegionUtil.setBorderTop(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J2:J2"), sheet);

                        RegionUtil.setBorderRight(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J2:J2"), sheet);

                        RegionUtil.setBorderLeft(BorderStyle.THIN,
                        CellRangeAddress.valueOf("J2:J2"), sheet);
                        
            
                String[] CelluleAMettreDeBordure = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "J"};


                for(int i = 0; i < CelluleAMettreDeBordure.length; i++){

                    for(int a = 5; a<n+1; a++){

                        String rangCellule = CelluleAMettreDeBordure[i]+a;

                        RegionUtil.setBorderBottom(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderTop(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderRight(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderLeft(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet); 
                    }

                }

  
                    FileOutputStream fout = new FileOutputStream(src);
                    
                    wb.write(fout);
                    wb.close();
                    //out.close();
                    fout.close();


                }catch(Exception createFileErreur){

                    //System.out.println("ERREUR DANS  get registre anomalie = " +createFileErreur.getMessage());
                    
                    JOptionPane.showMessageDialog(null, "Classes export registre anomalies erreur",createFileErreur.getMessage(), JOptionPane.INFORMATION_MESSAGE);
                }
                     
            rs.close();
            st.close();
            
            if(RowResultSet == 0){
                //System.out.println("val fiale de RowResultSet = " + RowResultSet);
                retour.add("empty-anomalie-non-bloquante");
                retour.add(realPath);
                //Files.deleteIfExists(Paths.get(realPath));
                
                //JOptionPane.showMessageDialog(null, "Aucune anomalie bloquante a été trouvé sur la : \n\ncommune: "+com+"\n"+"Fokontany : "+fkt+"\n"+"Hameau : "+hameau+"\n"+"Type d'opération : "+this.op, "Export du registre d'anonamlie impossible", JOptionPane.INFORMATION_MESSAGE);
 
            }else{
                retour.add("success-anomalie-non-bloquante");
                retour.add(realPath);
                //JOptionPane.showMessageDialog(null, "Export registre d'anomalie effectué avec succès !", "Export registre d'anomalie effectué avec succès", JOptionPane.INFORMATION_MESSAGE);
                // ouverture de l'emplacement selectionner par l'utiisateur
                //Desktop.getDesktop().open(new File(path));
            }
  
        } catch (Exception ex) {
            //ex.printStackTrace();
            //throw new RuntimeException();
            retour.add("error-anomalie-non-bloquante");
            retour.add("Error executing query: " +ex.getMessage());
            JOptionPane.showMessageDialog(null, "Impossible de lancer la requette de récupération des anomalies\n\nRetour : "+ex.getMessage(), "Erreur SQL trouvé", JOptionPane.INFORMATION_MESSAGE);
        }
        
        //System.out.println(demandes);
        return retour;
        
    }

  
    
public List<String> getRegistreAnomalieBloquanteSeulement(String reg, String c_dist, String dist, String c_com, String com, String c_fkt, String fkt, String c_hameau, String hameau, String path){

        
        List retour = new ArrayList();
        
        if(this.TYPE_OPERATION.equals("OCM")){
            this.op = "OCFM";
        }else{
            this.op = "OGCF";
        }
        
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_'a'_HH'h'mm'mn'ss'sec'");
        Date date = new Date(System.currentTimeMillis());
        
        
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        String dateAujourdhui = dateFormat.format(new Date());
    
    
        String realPath = path+"\\"+this.op+"_RegAnomalieSaisie_"+formatter.format(date)+"_Reg_"+Formats.ConvertSlashToUnderscore(reg)+"_Com_"+Formats.ConvertSlashToUnderscore(com)+"_FKT_"+Formats.ConvertSlashToUnderscore(fkt)+"_Ham_"+Formats.ConvertSlashToUnderscore(hameau)+".xlsx";
        String nomAtelier = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getNomAtelier();
        String nomAntenne = "";
        
        switch (nomAtelier){
            case "ATS":
                nomAntenne= "ANTENNE : ATSINANANA";
                break;
            case "VAK":
                nomAntenne= "ANTENNE : VAKINANKARATRA";
                break;
            default:
                if (reg.toUpperCase().equals("ANALAMANGA")) {
                    nomAntenne= "ANTENNE : ANALAMANGA";
                }else{
                    nomAntenne= "ANTENNE : ITASY";
                }
                
                break;
        }
        
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
                    

                // MISE EN PAGE ET MISE EN FORME DU FICHIER
                sheet.getHeader().setRight("Fiche 9 – LISTE DES ANOMALIES SUR LES FORMULAIRES");
                sheet.getHeader().setLeft("CASEF / GEOX2");
                sheet.getFooter().setLeft(nomAntenne);
                sheet.getFooter().setRight("Opération "+this.TYPE_OPERATION.toUpperCase());
                
                sheet.getPrintSetup().setLandscape(true);
                PrintSetup printsetup = sheet.getPrintSetup();
                sheet.getPrintSetup().setPaperSize(printsetup.A3_PAPERSIZE);

                String[] cellAFixer = ("$1:$6").split(":");
                CellReference startCellFixed = new CellReference(cellAFixer[0]);
                CellReference endCellFixed = new CellReference(cellAFixer[1]);
                CellRangeAddress addressCellAFixer = new CellRangeAddress(startCellFixed.getRow(),
                endCellFixed.getRow(), startCellFixed.getCol(), endCellFixed.getCol());

                sheet.setRepeatingRows(addressCellAFixer);
                
                
                
                // FIN MISE EN PAGE ET MISE EN FORME DU FICHIER
                
                // create table with data
                XSSFCellStyle cadre = wb.createCellStyle();
                cadre.setBorderBottom(BorderStyle.THIN);
                cadre.setBorderTop(BorderStyle.THIN);
                cadre.setBorderLeft(BorderStyle.THIN);
                cadre.setBorderRight(BorderStyle.THIN);
                
            //RegionUtil.setBorderBottom(BorderStyle.DOUBLE,
            //CellRangeAddress.valueOf("A1:B7"), sheet);

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
            
            cellStyleBold.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyleBold.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
            
            Cell headerCell0 = headerRow0.createCell(0);
            headerCell0.setCellValue("Commune :");
            headerCell0.setCellStyle(cadre);
            headerCell0.setCellStyle(cellStyleBold);

            
            headerCell0 = headerRow0.createCell(1);
            headerCell0.setCellValue(com);
            headerCell0.setCellStyle(cadre);

 
            headerCell0 = headerRow0.createCell(2);
            headerCell0.setCellValue("Code Com : " + c_com);
            headerCell0.setCellStyle(cadre);

            //headerCell0 = headerRow0.createCell(3);
            //headerCell0.setCellValue(c_com);

            headerCell0 = headerRow0.createCell(4);
            headerCell0.setCellValue("Fokontany :");
            headerCell0.setCellStyle(cadre);
            headerCell0.setCellStyle(cellStyleBold);
            
        
            headerCell0 = headerRow0.createCell(5);
            headerCell0.setCellValue(fkt);
            headerCell0.setCellStyle(cadre);

            headerCell0 = headerRow0.createCell(6);
            headerCell0.setCellValue("Code FKT :");
            headerCell0.setCellStyle(cadre);
            headerCell0.setCellStyle(cellStyleBold);
            

            headerCell0 = headerRow0.createCell(7);
            headerCell0.setCellValue(c_fkt);
            headerCell0.setCellStyle(cadre);
            
            

            headerCell0 = headerRow0.createCell(8);
            headerCell0.setCellValue("N° Equipe :");
            headerCell0.setCellStyle(cadre);
            headerCell0.setCellStyle(cellStyleBold);
            

            //headerCell0 = headerRow0.createCell(9);
            //headerCell0.setCellValue("xxxxx n° equipe");


            Row headerRow1 = sheet.createRow(1);

            Cell headerCell1 = headerRow1.createCell(0);
            headerCell1.setCellValue("Hameau :");
            headerCell1.setCellStyle(cadre);
            headerCell1.setCellStyle(cellStyleBold);
            


            headerCell1 = headerRow1.createCell(1);
            headerCell1.setCellValue(hameau);
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(2);
            headerCell1.setCellValue("Code Ham : " + (String) c_hameau);
            headerCell1.setCellStyle(cadre);

            //headerCell1 = headerRow1.createCell(3);
            //headerCell1.setCellValue(c_hameau);

            headerCell1 = headerRow1.createCell(4);
            headerCell1.setCellValue("Atelier :");
            headerCell1.setCellStyle(cadre);
            headerCell1.setCellStyle(cellStyleBold);
            

            
            headerCell1 = headerRow1.createCell(5);
            headerCell1.setCellValue(nomAtelier);
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(6);
            headerCell1.setCellValue("Date d’envoi :");
            headerCell1.setCellStyle(cadre);
            headerCell1.setCellStyle(cellStyleBold);
            

        
        
            headerCell1 = headerRow1.createCell(7);
            headerCell1.setCellValue(dateAujourdhui);
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(8);
            headerCell1.setCellValue("Date de retour :");
            headerCell1.setCellStyle(cadre);
            headerCell1.setCellStyle(cellStyleBold);
            


            Row headerRow4 = sheet.createRow(4);

            Cell headerCell4 = headerRow4.createCell(0);
            headerCell4.setCellValue("Atelier");
            headerCell4.setCellStyle(cadre);
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
            headerCell4.setCellStyle(cadre);
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
            headerCell4.setCellStyle(cadre);
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
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(1);
            headerCell5.setCellValue("N° Demande");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(2);
            headerCell5.setCellValue("Description anomalie");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(3);
            headerCell5.setCellValue("Corrigé");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            
 
            headerCell5 = headerRow5.createCell(4);
            headerCell5.setCellValue("Non corrigé");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(5);
            headerCell5.setCellValue("Date correction");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(6);
            headerCell5.setCellValue("Observation sur la correction");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(7);
            headerCell5.setCellValue("Signature ADA");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(8);
            headerCell5.setCellValue("Date");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            
            

            headerCell5 = headerRow5.createCell(9);
            headerCell5.setCellValue("Signature AGF");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(10);
            headerCell5.setCellValue("Date");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

            headerCell5 = headerRow5.createCell(11);
            headerCell5.setCellValue("Signature AFO");
            headerCell5.setCellStyle(cadre);
            headerCell5.setCellStyle(cellStyleBold);
            

        int n = 6;
        
            while (rs.next()) {
                
                RowResultSet++;
                    
                    Row headerRow7 = sheet.createRow(n);

                    Cell headerCell7 = headerRow7.createCell(0);
                    headerCell7.setCellValue(rs.getString("lot"));
                    headerCell7.setCellStyle(cadre);


                    Cell headerCell8 = headerRow7.createCell(1);
                    headerCell8.setCellValue(rs.getString("numero_demande"));
                    headerCell8.setCellStyle(cadre);

                    Cell headerCell9 = headerRow7.createCell(2);
                    headerCell9.setCellValue(rs.getString("anomalie_description"));
                    headerCell9.setCellStyle(cadre);
                    
                    
                    n++;
            }
            
            
                String[] CelluleAMettreDeBordure = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};


                for(int i = 0; i < CelluleAMettreDeBordure.length; i++){

                    for(int a = 5; a<n+1; a++){

                        String rangCellule = CelluleAMettreDeBordure[i]+a;

                        RegionUtil.setBorderBottom(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderTop(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderRight(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet);

                        RegionUtil.setBorderLeft(BorderStyle.THIN,
                        CellRangeAddress.valueOf(rangCellule), sheet); 
                    }

                }

  
                    FileOutputStream fout = new FileOutputStream(src);
                    
                    wb.write(fout);
                    wb.close();
                    out.close();
                    fout.close();


                }catch(Exception createFileErreur){

                    //System.out.println("ERREUR DANS  get registre anomalie = " +createFileErreur.getMessage());
                    
                    JOptionPane.showMessageDialog(null, "Classes export registre anomalie erreur",createFileErreur.getMessage(), JOptionPane.INFORMATION_MESSAGE);
                }
                     
            rs.close();
            st.close();
            
            if(RowResultSet == 0){
                //System.out.println("val fiale de RowResultSet = " + RowResultSet);
                retour.add("error-empty");
                retour.add(realPath);
                Files.deleteIfExists(Paths.get(realPath));
                
                JOptionPane.showMessageDialog(null, "Aucune anomalie a été trouvé sur la : \n\ncommune: "+com+"\n"+"Fokontany : "+fkt+"\n"+"Hameau : "+hameau+"\n"+"Type d'opération : "+this.op, "Export du registre d'anonamlie impossible", JOptionPane.INFORMATION_MESSAGE);
 
            }else{
                retour.add("success");
                //JOptionPane.showMessageDialog(null, "Export registre d'anomalie effectué avec succès !", "Export registre d'anomalie effectué avec succès", JOptionPane.INFORMATION_MESSAGE);
                // ouverture de l'emplacement selectionner par l'utiisateur
                //Desktop.getDesktop().open(new File(path));
            }
  
        } catch (Exception ex) {
            //ex.printStackTrace();
            //throw new RuntimeException();
            retour.add("error");
            retour.add("Error executing query: " +ex.getMessage());
            JOptionPane.showMessageDialog(null, "Impossible de lancer la requette de récupération des anomalies\n\nRetour : "+ex.getMessage(), "Erreur SQL trouvé", JOptionPane.INFORMATION_MESSAGE);
        }
        
        //System.out.println(demandes);
        return retour;
    }
    
    
public List<String> getListesCfEditerWithoutFilterDate(String reg, String c_dist, String dist, String c_com,String com, String path){
        
        List retour = new ArrayList();
        
        if(this.TYPE_OPERATION.equals("OCM")){
            this.op = "OCFM";
        }else{
            this.op = "OGCF";
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_'a'_HH'h'mm'mn'ss'sec'");
        Date date = new Date(System.currentTimeMillis());
        String realPath = path+"\\"+this.op+"_"+formatter.format(date)+"_CF_EDITER_"+"_Reg_"+Formats.ConvertSlashToUnderscore(reg)+"_Com_"+com+".xls";

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
"                TO_CHAR(demande.val_cf_edit_date, 'DD/MM/YYYY')  AS date_edition_pdf,\n" +
"                demande.num_registre AS numero_demande,\n" +
"                demande.num_certificat AS numero_certificat,\n" +
"                demande.id_certificat,\n" +
"				CASE \n" +
"					WHEN proprietaire_pp.type_demandeur = 'DM' THEN 'Demandeur'\n" +
"					ELSE  'Co-demandeur'\n" +
"				END AS type_demandeur,\n" +
"                              CASE \n" +
"					WHEN persphys.prenom IS NULL THEN persphys.nom\n" +
"					ELSE CONCAT(persphys.nom, ' ',persphys.prenom)\n" +
"				END AS nom_et_prenom,\n" +
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
            
            try{
                    
                // CREATION DU FICHIER
                String nameOfSheet = "cf_editer";
                    
                    
                // REMPLISSAGE DANS LE FICHIER

                XSSFWorkbook   wb = new XSSFWorkbook ();
                XSSFSheet  sheet = wb.createSheet(nameOfSheet);

                // MISE EN PAGE DU FICHIER
                sheet.getHeader().setCenter("Listes des CF éditer");
                sheet.getFooter().setCenter("CASEF / GEOX2");
                sheet.getPrintSetup().setLandscape(true);
                PrintSetup printsetup = sheet.getPrintSetup();
                sheet.getPrintSetup().setPaperSize(printsetup.A3_PAPERSIZE);

                String[] cellAFixer = ("$1:$5").split(":");
                CellReference startCellFixed = new CellReference(cellAFixer[0]);
                CellReference endCellFixed = new CellReference(cellAFixer[1]);
                CellRangeAddress addressCellAFixer = new CellRangeAddress(startCellFixed.getRow(),
                endCellFixed.getRow(), startCellFixed.getCol(), endCellFixed.getCol());

                sheet.setRepeatingRows(addressCellAFixer);
                
                // FIN MISE EN PAGE
                
                
                Row headerRow0 = sheet.createRow(0);

                XSSFCellStyle cellStyleBold = wb.createCellStyle();
                Font headerFont = wb.createFont();
                headerFont.setBold(true);
                cellStyleBold.setAlignment(HorizontalAlignment.CENTER);
                cellStyleBold.setFont(headerFont);
            
                // create table with data
                XSSFCellStyle cadre = wb.createCellStyle();
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
                headerCell0.setCellStyle(cadre);

                headerCell0 = headerRow0.createCell(1);
                headerCell0.setCellValue(reg);
                headerCell0.setCellStyle(cadre);

                headerCell0 = headerRow0.createCell(2);
                headerCell0.setCellValue("Type Opération : ");
                headerCell0.setCellStyle(cadre);

                headerCell0 = headerRow0.createCell(3);
                headerCell0.setCellValue(this.op);
                headerCell0.setCellStyle(cadre);
            

//==============================================================================
                Row headerRow1 = sheet.createRow(1);

                Cell headerCell1 = headerRow1.createCell(0);
                headerCell1.setCellValue("District :");
                headerCell1.setCellStyle(cellStyleBold);
                headerCell1.setCellStyle(cadre);

                headerCell1 = headerRow1.createCell(1);
                headerCell1.setCellValue(dist);
                headerCell1.setCellStyle(cadre);

                headerCell1 = headerRow1.createCell(2);
                headerCell1.setCellValue("Commune : ");
                headerCell1.setCellStyle(cadre);
                
                headerCell1 = headerRow1.createCell(3);
                headerCell1.setCellValue(com);
                headerCell1.setCellStyle(cadre);
// ============================================================================
                Row headerRow2 = sheet.createRow(2);

                Cell headerCell2 = headerRow2.createCell(0);
                headerCell2.setCellValue("Code Dist :");
                headerCell2.setCellStyle(cellStyleBold);
                headerCell2.setCellStyle(cadre);

                headerCell2 = headerRow2.createCell(1);
                headerCell2.setCellValue(c_dist);
                headerCell2.setCellStyle(cadre);

                headerCell2 = headerRow2.createCell(2);
                headerCell2.setCellValue("Code Com :");
                headerCell2.setCellStyle(cadre);
                
                headerCell2 = headerRow2.createCell(3);
                headerCell2.setCellValue(c_com);
                headerCell2.setCellStyle(cadre);
// ============================================================================ 

            String[] TextEnTeteTableau = {"date_edition_pdf", "numéro_certificat"
                    , "id_certificat", "type_demandeur", "nom_et_prénom(s)", "sexe", "naissance_date", "naissance_lieu", "cni_num", "cni_lieu"
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
                            headerCell8.setCellValue(Integer.parseInt(rs.getString("numero_certificat")));
                            headerCell8.setCellStyle(cadre);

                            Cell headerCell9 = headerRow4.createCell(2);
                            headerCell9.setCellValue(rs.getString("id_certificat"));
                            headerCell9.setCellStyle(cadre);
                            
                            Cell headerCell_10 = headerRow4.createCell(3);
                            headerCell_10.setCellValue(rs.getString("type_demandeur"));
                            headerCell_10.setCellStyle(cadre);
                            
                            Cell headerCell_11 = headerRow4.createCell(4);
                            headerCell_11.setCellValue(rs.getString("nom_et_prenom"));
                            headerCell_11.setCellStyle(cadre);
                            
                            Cell headerCell_12 = headerRow4.createCell(5);
                            headerCell_12.setCellValue(rs.getString("sexe"));
                            headerCell_12.setCellStyle(cadre);
                            
                            Cell headerCell_13 = headerRow4.createCell(6);
                            headerCell_13.setCellValue(rs.getString("naissance_date"));
                            headerCell_13.setCellStyle(cadre);
                            
                            Cell headerCell_14 = headerRow4.createCell(7);
                            headerCell_14.setCellValue(rs.getString("naissance_lieu"));
                            headerCell_14.setCellStyle(cadre);
                            
                            
                            //DataFormat format = wb.createDataFormat();
                            //cellStyleBold.setDataFormat(format.getFormat("0.0"));
                            
                            Cell headerCell_15 = headerRow4.createCell(8);
                            headerCell_15.setCellValue(rs.getString("cni_num"));
                            headerCell_15.setCellStyle(cadre);
                            
                            Cell headerCell_16 = headerRow4.createCell(9);
                            headerCell_16.setCellValue(rs.getString("cni_lieu"));
                            headerCell_16.setCellStyle(cadre);
                            
                            Cell headerCell_17 = headerRow4.createCell(10);
                            headerCell_17.setCellValue(rs.getString("acn_num"));
                            headerCell_17.setCellStyle(cadre);
                            
                            Cell headerCell_18 = headerRow4.createCell(11);
                            headerCell_18.setCellValue(rs.getString("acn_date"));
                            headerCell_18.setCellStyle(cadre);
                            
                            Cell headerCell_19 = headerRow4.createCell(12);
                            headerCell_19.setCellValue(rs.getString("acn_lieu"));
                            headerCell_19.setCellStyle(cadre);
                            

                            //System.out.println("Get commune : " + rs.getString("commune") + " ::  " + rs.getString("numero_demande") 
                             //                      + " Login = " +rs.getString("login") + " lot : " + rs.getString("lot")+ " anomalie_description = " + rs.getString("anomalie_description"));

                        n++;
                    }
  
                    FileOutputStream fout = new FileOutputStream(realPath);

                    wb.write(fout);
                    wb.close();
                    fout.close();


                }catch(Exception createFileErreur){

                    //System.out.println(createFileErreur.getMessage());
                    
                    JOptionPane.showMessageDialog(null, "Classes export cf editer erreur",createFileErreur.getMessage(), JOptionPane.INFORMATION_MESSAGE);
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
  

public List<String> getListesCfEditerWithFilterDate(String reg, String c_dist, String dist, String c_com,String com, String path, String dateEdition){
        
        List retour = new ArrayList();
        
        if(this.TYPE_OPERATION.equals("OCM")){
            this.op = "OCFM";
        }else{
            this.op = "OGCF";
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_'a'_HH'h'mm'mn'ss'sec'");
        Date date = new Date(System.currentTimeMillis());
    
        String realPath = path+"\\"+this.op+"_"+formatter.format(date)+"_CF_EDITER_du_"+dateEdition.replace("/", "-")+"_Reg_"+Formats.ConvertSlashToUnderscore(reg)+"_Com_"+com+".xls";

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
"                              CASE \n" +
"					WHEN persphys.prenom IS NULL THEN persphys.nom\n" +
"					ELSE CONCAT(persphys.nom, ' ',persphys.prenom)\n" +
"				END AS nom_et_prenom,\n" +
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
"			AND demande.val_cf_edit_date::DATE = '"+dateEdition+"'  \n" +
"  ORDER BY demande.num_certificat ASC"; 
            
            st = connectDatabase.prepareStatement(sql);    
            st.setString(1, reg);
            st.setString(2, dist);
            st.setString(3, com);;
            st.setString(4, this.TYPE_OPERATION.toLowerCase());
            rs = st.executeQuery();
            
                try{
                    
                    // CREATION DU FICHIER
                    String nameOfSheet = "cf_editer_filtre_date";
                    
                    
                    // REMPLISSAGE DANS LE FICHIER

            XSSFWorkbook   wb = new XSSFWorkbook ();
            XSSFSheet  sheet = wb.createSheet(nameOfSheet);
            
            sheet.getHeader().setCenter("Listes CF éditer du "+dateEdition+"\nRégion : "+reg+", Commune: "+com);
            sheet.getFooter().setCenter("CASEF / GEOX2");
            sheet.getPrintSetup().setLandscape(true);
            PrintSetup printsetup = sheet.getPrintSetup();
            sheet.getPrintSetup().setPaperSize(printsetup.A3_PAPERSIZE);
            
            String[] cellAFixer = ("$1:$5").split(":");
            CellReference startCellFixed = new CellReference(cellAFixer[0]);
            CellReference endCellFixed = new CellReference(cellAFixer[1]);
            CellRangeAddress addressCellAFixer = new CellRangeAddress(startCellFixed.getRow(),
            endCellFixed.getRow(), startCellFixed.getCol(), endCellFixed.getCol());
            
            sheet.setRepeatingRows(addressCellAFixer);

//System.out.println("sheet.getFitToPage() = "+ sheet.getFitToPage());


            Row headerRow0 = sheet.createRow(0);
            //sheet.autoSizeColumn(0);
 
            XSSFCellStyle cellStyleBold = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            cellStyleBold.setAlignment(HorizontalAlignment.CENTER);
            cellStyleBold.setFont(headerFont);
            
            // create table with data
            XSSFCellStyle cadre = wb.createCellStyle();
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
            headerCell0.setCellStyle(cadre);

            headerCell0 = headerRow0.createCell(1);
            headerCell0.setCellValue(reg);
            headerCell0.setCellStyle(cadre);

            headerCell0 = headerRow0.createCell(2);
            headerCell0.setCellValue("Type Opération : ");
            headerCell0.setCellStyle(cadre);

            headerCell0 = headerRow0.createCell(3);
            headerCell0.setCellValue(this.op);
            headerCell0.setCellStyle(cadre);

            headerCell0 = headerRow0.createCell(4);
            headerCell0.setCellValue("Date d'édition CF :");
            headerCell0.setCellStyle(cellStyleBold);
            headerCell0.setCellStyle(cadre);

            headerCell0 = headerRow0.createCell(5);
            headerCell0.setCellValue(dateEdition);   
            headerCell0.setCellStyle(cadre);
//==============================================================================
            Row headerRow1 = sheet.createRow(1);

            Cell headerCell1 = headerRow1.createCell(0);
            headerCell1.setCellValue("District :");
            headerCell1.setCellStyle(cellStyleBold);
            headerCell1.setCellStyle(cadre);


            headerCell1 = headerRow1.createCell(1);
            headerCell1.setCellValue(dist);
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(2);
            headerCell1.setCellValue("Commune : ");
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(3);
            headerCell1.setCellValue(com);
            headerCell1.setCellStyle(cadre);
// ============================================================================
            Row headerRow2 = sheet.createRow(2);

            Cell headerCell2 = headerRow2.createCell(0);
            headerCell2.setCellValue("Code Dist :");
            headerCell2.setCellStyle(cellStyleBold);
            headerCell2.setCellStyle(cadre);

            headerCell2 = headerRow2.createCell(1);
            headerCell2.setCellValue(c_dist);
            headerCell2.setCellStyle(cadre);

            headerCell2 = headerRow2.createCell(2);
            headerCell2.setCellValue("Code Com :");
            headerCell2.setCellStyle(cadre);

            headerCell2 = headerRow2.createCell(3);
            headerCell2.setCellValue(c_com);
            headerCell2.setCellStyle(cadre);
// ============================================================================ 

            String[] TextEnTeteTableau = {"numéro_certificat"
                    , "id_certificat", "type_demandeur", "nom_et_prénom(s)"
                    , "sexe", "naissance_date", "naissance_lieu", "cni_num", "cni_lieu"
                    , "acn_num", "acn_date", "acn_lieu"};

            TreeMap<Integer, String> EnTeteTableauAExporter = new TreeMap<Integer, String>();

            for (int i = 0; i < TextEnTeteTableau.length; i++) {
              EnTeteTableauAExporter.put(i, TextEnTeteTableau[i]);
            }

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
                            headerCell7.setCellValue(Integer.parseInt(rs.getString("numero_certificat")));
                            headerCell7.setCellStyle(cadre);


                            Cell headerCell8 = headerRow4.createCell(1);
                            headerCell8.setCellValue(rs.getString("id_certificat"));
                            headerCell8.setCellStyle(cadre);
                            
                            Cell headerCell9 = headerRow4.createCell(2);
                            headerCell9.setCellValue(rs.getString("type_demandeur"));
                            headerCell9.setCellStyle(cadre);
                            
                            Cell headerCell_10 = headerRow4.createCell(3);
                            headerCell_10.setCellValue(rs.getString("nom_et_prenom"));
                            headerCell_10.setCellStyle(cadre);
                            //cellStyleBold.setWrapText(true);
                            
                            Cell headerCell_11 = headerRow4.createCell(4);
                            headerCell_11.setCellValue(rs.getString("sexe"));
                            headerCell_11.setCellStyle(cadre);
                            
                            Cell headerCell_12 = headerRow4.createCell(5);
                            headerCell_12.setCellValue(rs.getString("naissance_date"));
                            headerCell_12.setCellStyle(cadre);
                            
                            Cell headerCell_13 = headerRow4.createCell(6);
                            headerCell_13.setCellValue(rs.getString("naissance_lieu"));
                            headerCell_13.setCellStyle(cadre);
                            
                            Cell headerCell_14 = headerRow4.createCell(7);
                            headerCell_14.setCellValue(rs.getString("cni_num"));
                            headerCell_14.setCellStyle(cadre);
                            
                            Cell headerCell_15 = headerRow4.createCell(8);
                            headerCell_15.setCellValue(rs.getString("cni_lieu"));
                            headerCell_15.setCellStyle(cadre);
                            
                            Cell headerCell_16 = headerRow4.createCell(9);
                            headerCell_16.setCellValue(rs.getString("acn_num"));
                            headerCell_16.setCellStyle(cadre);
                            
                            Cell headerCell_17 = headerRow4.createCell(10);
                            headerCell_17.setCellValue(rs.getString("acn_date"));
                            headerCell_17.setCellStyle(cadre);
                            
                            Cell headerCell_18 = headerRow4.createCell(11);
                            headerCell_18.setCellValue(rs.getString("acn_lieu"));
                            headerCell_18.setCellStyle(cadre);
                            

                            //System.out.println("Get commune : " + rs.getString("commune") + " ::  " + rs.getString("numero_demande") 
                             //                      + " Login = " +rs.getString("login") + " lot : " + rs.getString("lot")+ " anomalie_description = " + rs.getString("anomalie_description"));

                        n++;
                    }
  
                    FileOutputStream fout = new FileOutputStream(realPath);

                    wb.write(fout);
                    wb.close();
                    fout.close();


                }catch(Exception createFileErreur){

                    //System.out.println(createFileErreur.getMessage());
                    
                    JOptionPane.showMessageDialog(null, "Classes export cf editer erreur",createFileErreur.getMessage(), JOptionPane.INFORMATION_MESSAGE);
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


    
public List<Querry> getRegistreParcellaireProvisoirePersonnePhysique(String reg, String c_dist, String dist, String c_com,String com, String path){
            
        int n = 5;
                    
        List retour = new ArrayList();
        
        if(this.TYPE_OPERATION.equals("OCM")){
            this.op = "OCFM";
        }else{
            this.op = "OGCF";
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_'a'_HH'h'mm'mn'ss'sec'");
        Date date = new Date(System.currentTimeMillis());
    
    
        String realPath = path+"\\"+this.op+"_RP_prov ( LISTING )_"+formatter.format(date)+"_"+"_Reg_"+Formats.ConvertSlashToUnderscore(reg)+"_Com_"+Formats.ConvertSlashToUnderscore(com)+".xlsx";
        
        String nomAtelier = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getNomAtelier();
        String nomAntenne = "";
        
        switch (nomAtelier){
            case "ATS":
                nomAntenne= "ANTENNE : ATSINANANA";
                break;
            case "VAK":
                nomAntenne= "ANTENNE : VAKINANKARATRA";
                break;
            default:
                if (reg.equals("ANALAMANGA")) {
                    nomAntenne= "ANTENNE : ANALAMANGA";
                }else{
                    nomAntenne= "ANTENNE : ITASY";
                }
                
                break;
        }
        
        
        int RowResultSet = 0;
        
        try {
            
            String sql = " SELECT demande.id_registre, TO_CHAR(demande.date_demande, 'DD/MM/YYYY') AS date_demande ,\n" +
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
            
            
            
            st = connectDatabase.prepareStatement(sql);    
            st.setString(1, reg);
            st.setString(2, dist);
            st.setString(3, com);
            st.setString(4, Formats.ConvertOcfmToOcm(this.TYPE_OPERATION).toLowerCase());
            rs = st.executeQuery();
            
                try{
                    
                    // CREATION DU FICHIER
                    String nameOfSheet = "RP_prov_pers_physique";
                    
                    
                    // REMPLISSAGE DANS LE FICHIER

                XSSFWorkbook   wb = new XSSFWorkbook ();
                XSSFSheet  sheet = wb.createSheet(nameOfSheet);
            

                // MISE EN PAGE ET MISE EN FORME DU FICHIER
                //sheet.getHeader().setRight("Du: &[ Date ] à &[ Heure ]");
                sheet.getHeader().setCenter("LISTING DES DOSSERS A CONTROLER");
                sheet.getHeader().setLeft("CASEF / GEOX2");
                //sheet.getFooter().setCenter("Page : &[ Page ] à &[ Pages ]");
                sheet.getFooter().setLeft(nomAntenne);
                sheet.getFooter().setRight("Opération "+Formats.ConvertOcmToOcfm(this.TYPE_OPERATION).toUpperCase());
                
                sheet.getPrintSetup().setLandscape(true);
                PrintSetup printsetup = sheet.getPrintSetup();
                sheet.getPrintSetup().setPaperSize(printsetup.A3_PAPERSIZE);

                String[] cellAFixer = ("$1:$5").split(":");
                CellReference startCellFixed = new CellReference(cellAFixer[0]);
                CellReference endCellFixed = new CellReference(cellAFixer[1]);
                CellRangeAddress addressCellAFixer = new CellRangeAddress(startCellFixed.getRow(),
                endCellFixed.getRow(), startCellFixed.getCol(), endCellFixed.getCol());

                sheet.setRepeatingRows(addressCellAFixer);
                
                
                
                // FIN MISE EN PAGE ET MISE EN FORME DU FICHIER      
            
            Row headerRow0 = sheet.createRow(0);
 
        
            XSSFCellStyle cellStyleBold = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            cellStyleBold.setAlignment(HorizontalAlignment.CENTER);
            cellStyleBold.setFont(headerFont);
            //cellStyleBold.setWrapText(true);
            
            // create table with data
            XSSFCellStyle cadre = wb.createCellStyle();
            
            
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
            headerCell0.setCellStyle(cadre);
            
            //new MiseEnFormes().MiseEnGras(wb);


            headerCell0 = headerRow0.createCell(1);
            headerCell0.setCellValue(reg);
            headerCell0.setCellStyle(cadre);


            headerCell0 = headerRow0.createCell(2);
            headerCell0.setCellValue("Type Opération : ");
            headerCell0.setCellStyle(cadre);

            headerCell0 = headerRow0.createCell(3);
            headerCell0.setCellValue(Formats.ConvertOcmToOcfm(this.op));
            headerCell0.setCellStyle(cadre);

//==============================================================================
            Row headerRow1 = sheet.createRow(1);

            Cell headerCell1 = headerRow1.createCell(0);
            headerCell1.setCellValue("District :");
            headerCell1.setCellStyle(cellStyleBold);
            headerCell1.setCellStyle(cadre);


            headerCell1 = headerRow1.createCell(1);
            headerCell1.setCellValue(dist);
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(2);
            headerCell1.setCellValue("Commune : ");
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(3);
            headerCell1.setCellValue(com);
            headerCell1.setCellStyle(cadre);
// ============================================================================
            Row headerRow2 = sheet.createRow(2);

            Cell headerCell2 = headerRow2.createCell(0);
            headerCell2.setCellValue("Code Dist :");
            headerCell2.setCellStyle(cellStyleBold);
            headerCell2.setCellStyle(cadre);


            headerCell2 = headerRow2.createCell(1);
            headerCell2.setCellValue(c_dist);
            headerCell2.setCellStyle(cadre);

            headerCell2 = headerRow2.createCell(2);
            headerCell2.setCellValue("Code Com :");
            headerCell2.setCellStyle(cadre);

            headerCell2 = headerRow2.createCell(3);
            headerCell2.setCellValue(c_com);
            headerCell2.setCellStyle(cadre);
// ============================================================================ 

            String[] TextEnTeteTableau = {"id_registre", "date_demande"
                    , "nom_et_prenom","naissance_lieu", "naissance_date", "adresse"
                    , "cni_num", "cni_date", "cni_lieu", "acn_num", "acn_date", "acn_lieu", "fokontany"
                    , "hameau", "superficie (m2)", "coord_x", "coord_y", "v_nord"
                    , "v_sud", "v_ouest", "v_est", "date_crl", "charges"};

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

            
                    while (rs.next()) {

                        RowResultSet++;
                        
                        Row headerRow4 = sheet.createRow(n);
                        
                            Cell headerCell7 = headerRow4.createCell(0);
                            headerCell7.setCellValue(rs.getString("id_registre"));
                            headerCell7.setCellStyle(cadre);

                            
                            Cell headerCell8 = headerRow4.createCell(1);
                            headerCell8.setCellValue(rs.getString("date_demande"));
                            headerCell8.setCellStyle(cadre);

                            
                            Cell headerCell9 = headerRow4.createCell(2);
                            headerCell9.setCellValue(rs.getString("nom_et_prenom"));
                            headerCell9.setCellStyle(cadre);
                            
                            Cell headerCell10 = headerRow4.createCell(3);
                            headerCell10.setCellValue(rs.getString("naissance_lieu"));
                            headerCell10.setCellStyle(cadre);
                            

                            
                            Cell headerCell_11 = headerRow4.createCell(4);
                            headerCell_11.setCellValue(rs.getString("naissance_date"));
                            headerCell_11.setCellStyle(cadre);
                            
                            
                            Cell headerCell_12 = headerRow4.createCell(5);
                            headerCell_12.setCellValue(rs.getString("adresse"));
                            headerCell_12.setCellStyle(cadre);
                            
                            
                            Cell headerCell_13 = headerRow4.createCell(6);
                            headerCell_13.setCellValue(rs.getString("cni_num"));
                            headerCell_13.setCellStyle(cadre);
                            
                            
                            Cell headerCell_14 = headerRow4.createCell(7);
                            headerCell_14.setCellValue(rs.getString("cni_date"));
                            headerCell_14.setCellStyle(cadre);
                            
                            Cell headerCell_15 = headerRow4.createCell(8);
                            headerCell_15.setCellValue(rs.getString("cni_lieu"));
                            headerCell_15.setCellStyle(cadre);
                            
                            Cell headerCell_16 = headerRow4.createCell(9);
                            headerCell_16.setCellValue(rs.getString("acn_num"));
                            headerCell_16.setCellStyle(cadre);
                            
                            Cell headerCell_17 = headerRow4.createCell(10);
                            headerCell_17.setCellValue(rs.getString("acn_date"));
                            headerCell_17.setCellStyle(cadre);
                            
                            Cell headerCell_18 = headerRow4.createCell(11);
                            headerCell_18.setCellValue(rs.getString("acn_lieu"));
                            headerCell_18.setCellStyle(cadre);
                            

                            Cell headerCell_19 = headerRow4.createCell(12);
                            headerCell_19.setCellValue(rs.getString("fokontany"));
                            headerCell_19.setCellStyle(cadre);
                            
                            Cell headerCell_20 = headerRow4.createCell(13);
                            headerCell_20.setCellValue(rs.getString("hameau"));
                            headerCell_20.setCellStyle(cadre);
                            
                            Cell headerCell_21 = headerRow4.createCell(14);
                            headerCell_21.setCellValue(rs.getString("superficie"));
                            headerCell_21.setCellStyle(cadre);
                            
                            Cell headerCell_22 = headerRow4.createCell(15);
                            headerCell_22.setCellValue(rs.getString("coord_x"));
                            headerCell_22.setCellStyle(cadre);
                            
                            Cell headerCell_23 = headerRow4.createCell(16);
                            headerCell_23.setCellValue(rs.getString("coord_y"));
                            headerCell_23.setCellStyle(cadre);

                            Cell headerCell_24 = headerRow4.createCell(17);
                            headerCell_24.setCellValue(rs.getString("v_nord"));
                            headerCell_24.setCellStyle(cadre);
                            
                            
                            Cell headerCell_25 = headerRow4.createCell(18);
                            headerCell_25.setCellValue(rs.getString("v_sud"));
                            headerCell_25.setCellStyle(cadre);

                            
                            Cell headerCell_26 = headerRow4.createCell(19);
                            headerCell_26.setCellValue(rs.getString("v_ouest"));
                            headerCell_26.setCellStyle(cadre);

                            Cell headerCell_27 = headerRow4.createCell(20);
                            headerCell_27.setCellValue(rs.getString("v_ouest"));
                            headerCell_27.setCellStyle(cadre);
                            
                            
                            Cell headerCell_28 = headerRow4.createCell(21);
                            headerCell_28.setCellValue(rs.getString("date_crl"));
                            headerCell_28.setCellStyle(cadre);

                            
                            Cell headerCell_29 = headerRow4.createCell(22);
                            headerCell_29.setCellValue(rs.getString("charges"));
                            headerCell_29.setCellStyle(cadre);

                            
                        n++;
                    }
  
                    FileOutputStream fout = new FileOutputStream(realPath);

                    wb.write(fout);
                    wb.close();
                    fout.close();


                }catch(Exception createFileErreur){
                    
                    JOptionPane.showMessageDialog(null, createFileErreur.getMessage(), "Erreur SQL trouvé !", JOptionPane.INFORMATION_MESSAGE);

                    //System.out.println("ERREUR DANS  get registre parcellaire" +createFileErreur.getMessage());
                }
                     
            rs.close();
            st.close();
            
            if(RowResultSet == 0){
                //System.out.println("val fiale de RowResultSet = " + RowResultSet);
                retour.add("empty-personne-physique");
                retour.add(realPath);
                retour.add(0);
                // SUPPRESSION DU FICHIER EXPORTE CAR IL Y AVAIT UNE ERREUR LORS DE L'EXPORT
                Files.deleteIfExists(Paths.get(realPath));
                //System.out.println("votre commune : "+ com);

            }else{
                retour.add("success-personne-physique");
                retour.add(realPath);
                retour.add(n);
                //JOptionPane.showMessageDialog(null, "Listes CF éditer exporté avec succès !", "Listes CF éditer exporté avec succès ", JOptionPane.INFORMATION_MESSAGE);
                // ouverture de l'emplacement selectionner par l'utiisateur
                //Desktop.getDesktop().open(new File(path));
            }
  
        } catch (Exception ex) {
            //ex.printStackTrace();
            //throw new RuntimeException();
            retour.add("error-personne-physique");
            retour.add(realPath);
            retour.add(0);
            //retour.add("Error executing query: " +ex.getMessage());
        }
        
        //System.out.println(demandes);
        return retour;
    }



public List<Querry> getRegistreParcellaireProvisoirePersonneMorale(String reg, String c_dist, String dist, String c_com,String com, String path){
            
        int n = 5;
                    
        List retour = new ArrayList();
        
        String realPath = path;
        String nomAtelier = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getNomAtelier();
        String nomAntenne = "";
        
        switch (nomAtelier){
            case "ATS":
                nomAntenne= "ANTENNE : ATSINANANA";
                break;
            case "VAK":
                nomAntenne= "ANTENNE : VAKINANKARATRA";
                break;
            default:
                if (reg.equals("ANALAMANGA")) {
                    nomAntenne= "ANTENNE : ANALAMANGA";
                }else{
                    nomAntenne= "ANTENNE : ITASY";
                }
                
                break;
        }
        
        
        int RowResultSet = 0;
        
        try {
            
            String sql = "SELECT demande.id_registre, TO_CHAR(demande.date_demande, 'DD/MM/YYYY') AS date_demande , \n" +
        "public.lst_type_pm.valeur AS type_personne_morale, \n" +
        "public.persmor.denomination, \n" +
        "public.persmor.numero_pm AS numero, \n" +
        "TO_CHAR(public.persmor.date_acte, 'DD/MM/YYYY') AS date_creation, \n" +
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
"              END     ,    \n" +
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
        "              AND demande.type_op = ? \n" +
        "           	AND demande.date_crl-demande.date_demande >= 15  \n" +
        "              ORDER BY demande.num_registre  ASC";
            
            
            
            st = connectDatabase.prepareStatement(sql);    
            st.setString(1, reg);
            st.setString(2, dist);
            st.setString(3, com);
            st.setString(4, Formats.ConvertOcfmToOcm(this.TYPE_OPERATION).toLowerCase());
            rs = st.executeQuery();
            
                try{

                   // CREATION DU FICHIER
                    String nameOfSheet = "RP_prov_pers_morale";
                    
                    // REMPLISSAGE DANS LE FICHIER
                    File src = new File(realPath);
                    FileInputStream fis = new FileInputStream(src);
                    XSSFWorkbook  wb = new XSSFWorkbook(fis);
                    
                    wb.createSheet(nameOfSheet);
                    XSSFSheet sheet = wb.getSheet(nameOfSheet);
            

                // MISE EN PAGE ET MISE EN FORME DU FICHIER
                //sheet.getHeader().setRight("Du: &[ Date ] à &[ Heure ]");
                sheet.getHeader().setCenter("LISTING DES DOSSERS A CONTROLER");
                sheet.getHeader().setLeft("CASEF / GEOX2");
                //sheet.getFooter().setCenter("Page : &[ Page ] à &[ Pages ]");
                sheet.getFooter().setLeft(nomAntenne);
                sheet.getFooter().setRight("Opération "+Formats.ConvertOcmToOcfm(this.TYPE_OPERATION).toUpperCase());
                
                sheet.getPrintSetup().setLandscape(true);
                PrintSetup printsetup = sheet.getPrintSetup();
                sheet.getPrintSetup().setPaperSize(printsetup.A3_PAPERSIZE);

                String[] cellAFixer = ("$1:$5").split(":");
                CellReference startCellFixed = new CellReference(cellAFixer[0]);
                CellReference endCellFixed = new CellReference(cellAFixer[1]);
                CellRangeAddress addressCellAFixer = new CellRangeAddress(startCellFixed.getRow(),
                endCellFixed.getRow(), startCellFixed.getCol(), endCellFixed.getCol());

                sheet.setRepeatingRows(addressCellAFixer);
                
                
                
                // FIN MISE EN PAGE ET MISE EN FORME DU FICHIER      
            
            Row headerRow0 = sheet.createRow(0);
 
        
            XSSFCellStyle cellStyleBold = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            cellStyleBold.setAlignment(HorizontalAlignment.CENTER);
            cellStyleBold.setFont(headerFont);
            //cellStyleBold.setWrapText(true);
            
            // create table with data
            XSSFCellStyle cadre = wb.createCellStyle();
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
            headerCell0.setCellStyle(cadre);
            
            //new MiseEnFormes().MiseEnGras(wb);


            headerCell0 = headerRow0.createCell(1);
            headerCell0.setCellValue(reg);
            headerCell0.setCellStyle(cadre);


            headerCell0 = headerRow0.createCell(2);
            headerCell0.setCellValue("Type Opération : ");
            headerCell0.setCellStyle(cadre);

            headerCell0 = headerRow0.createCell(3);
            headerCell0.setCellValue(Formats.ConvertOcmToOcfm(this.TYPE_OPERATION).toUpperCase());
            headerCell0.setCellStyle(cadre);

//==============================================================================
            Row headerRow1 = sheet.createRow(1);

            Cell headerCell1 = headerRow1.createCell(0);
            headerCell1.setCellValue("District :");
            headerCell1.setCellStyle(cellStyleBold);
            headerCell1.setCellStyle(cadre);


            headerCell1 = headerRow1.createCell(1);
            headerCell1.setCellValue(dist);
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(2);
            headerCell1.setCellValue("Commune : ");
            headerCell1.setCellStyle(cadre);

            headerCell1 = headerRow1.createCell(3);
            headerCell1.setCellValue(com);
            headerCell1.setCellStyle(cadre);
// ============================================================================
            Row headerRow2 = sheet.createRow(2);

            Cell headerCell2 = headerRow2.createCell(0);
            headerCell2.setCellValue("Code Dist :");
            headerCell2.setCellStyle(cellStyleBold);
            headerCell2.setCellStyle(cadre);


            headerCell2 = headerRow2.createCell(1);
            headerCell2.setCellValue(c_dist);
            headerCell2.setCellStyle(cadre);

            headerCell2 = headerRow2.createCell(2);
            headerCell2.setCellValue("Code Com :");
            headerCell2.setCellStyle(cadre);

            headerCell2 = headerRow2.createCell(3);
            headerCell2.setCellValue(c_com);
            headerCell2.setCellStyle(cadre);
// ============================================================================ 

            String[] TextEnTeteTableau = {"id_registre", "date_demande"
                    , "type_personne_morale","denomination", "numero", "date_creation"
                    , "adresse", "fokontany"
                    , "hameau", "superficie (m2)", "coord_x", "coord_y", "v_nord"
                    , "v_sud", "v_ouest", "v_est", "date_crl", "charges"};

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



                    
                    
                    while (rs.next()) {

                        RowResultSet++;
                        
                        Row headerRow4 = sheet.createRow(n);
                        
                            Cell headerCell7 = headerRow4.createCell(0);
                            headerCell7.setCellValue(rs.getString("id_registre"));
                            headerCell7.setCellStyle(cadre);

                            
                            Cell headerCell8 = headerRow4.createCell(1);
                            headerCell8.setCellValue(rs.getString("date_demande"));
                            headerCell8.setCellStyle(cadre);

                            
                            Cell headerCell9 = headerRow4.createCell(2);
                            headerCell9.setCellValue(rs.getString("type_personne_morale"));
                            headerCell9.setCellStyle(cadre);
                            
                            Cell headerCell10 = headerRow4.createCell(3);
                            headerCell10.setCellValue(rs.getString("denomination"));
                            headerCell10.setCellStyle(cadre);
                            

                            
                            Cell headerCell_11 = headerRow4.createCell(4);
                            headerCell_11.setCellValue(rs.getString("numero"));
                            headerCell_11.setCellStyle(cadre);
                            
                            
                            Cell headerCell_12 = headerRow4.createCell(5);
                            headerCell_12.setCellValue(rs.getString("date_creation"));
                            headerCell_12.setCellStyle(cadre);
                            
                            Cell headerCell_13 = headerRow4.createCell(6);
                            headerCell_13.setCellValue(rs.getString("adresse"));
                            headerCell_13.setCellStyle(cadre);

                            

                            Cell headerCell_14 = headerRow4.createCell(7);
                            headerCell_14.setCellValue(rs.getString("fokontany"));
                            headerCell_14.setCellStyle(cadre);
                            
                            Cell headerCell_15 = headerRow4.createCell(8);
                            headerCell_15.setCellValue(rs.getString("hameau"));
                            headerCell_15.setCellStyle(cadre);
                            
                            Cell headerCell_16 = headerRow4.createCell(9);
                            headerCell_16.setCellValue(rs.getString("superficie"));
                            headerCell_16.setCellStyle(cadre);
                            
                            Cell headerCell_17 = headerRow4.createCell(10);
                            headerCell_17.setCellValue(rs.getString("coord_x"));
                            headerCell_17.setCellStyle(cadre);
                            
                            Cell headerCell_18 = headerRow4.createCell(11);
                            headerCell_18.setCellValue(rs.getString("coord_y"));
                            headerCell_18.setCellStyle(cadre);

                            Cell headerCell_19 = headerRow4.createCell(12);
                            headerCell_19.setCellValue(rs.getString("v_nord"));
                            headerCell_19.setCellStyle(cadre);
                            
                            
                            Cell headerCell_20 = headerRow4.createCell(13);
                            headerCell_20.setCellValue(rs.getString("v_sud"));
                            headerCell_20.setCellStyle(cadre);

                            
                            Cell headerCell_21 = headerRow4.createCell(14);
                            headerCell_21.setCellValue(rs.getString("v_ouest"));
                            headerCell_21.setCellStyle(cadre);

                            Cell headerCell_22 = headerRow4.createCell(15);
                            headerCell_22.setCellValue(rs.getString("v_ouest"));
                            headerCell_22.setCellStyle(cadre);
                            
                            
                            Cell headerCell_23 = headerRow4.createCell(16);
                            headerCell_23.setCellValue(rs.getString("date_crl"));
                            headerCell_23.setCellStyle(cadre);

                            
                            Cell headerCell_24 = headerRow4.createCell(17);
                            headerCell_24.setCellValue(rs.getString("charges"));
                            headerCell_24.setCellStyle(cadre);

                            
                            
                        n++;
                    }
  
                    FileOutputStream fout = new FileOutputStream(realPath);

                    wb.write(fout);
                    wb.close();
                    fout.close();


                }catch(Exception createFileErreur){
                    
                    //JOptionPane.showMessageDialog(null, createFileErreur.getMessage(), "Erreur SQL trouvé !", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("ERREUR DANS  get registre parcellaire personne morale" +createFileErreur.getMessage());
                }
                     
            rs.close();
            st.close();
            
            if(RowResultSet == 0){
                //System.out.println("val fiale de RowResultSet = " + RowResultSet);
                retour.add("empty-personne-morale");
                retour.add(realPath);
                retour.add(0);
                // SUPPRESSION DU FICHIER EXPORTE CAR IL Y AVAIT UNE ERREUR LORS DE L'EXPORT
                //Files.deleteIfExists(Paths.get(realPath));
                //System.out.println("votre commune : "+ com);

            }else{
                retour.add("success-personne-morale");
                retour.add(realPath);
                retour.add(n);
                //JOptionPane.showMessageDialog(null, "Listes CF éditer exporté avec succès !", "Listes CF éditer exporté avec succès ", JOptionPane.INFORMATION_MESSAGE);
                // ouverture de l'emplacement selectionner par l'utiisateur
                //Desktop.getDesktop().open(new File(path));
            }
  
        } catch (Exception ex) {
            //ex.printStackTrace();
            //throw new RuntimeException();
            retour.add("error-personne-morale");
            retour.add("Error executing query: " +ex.getMessage());
            retour.add(0);
        }
        
        //System.out.println(demandes);
        return retour;
    }
 
    
}
