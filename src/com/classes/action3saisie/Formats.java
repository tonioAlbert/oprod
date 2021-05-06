/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.action3saisie;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

/**
 *
 * @author RAP
 */
public class Formats {

    public Formats() {
    }
    
    
    
    public static String formatFirtsName(String v){
      
        String chaine1 = v;
        String chaine2 = "";

         // compilation de la regex
         Pattern patern = Pattern.compile(" ");    //(" "); // le séparateur est un espace
         // éclatement en sous-chaînes
         String[] sousChaines = patern.split(chaine1);
         for(int i = 0; i < sousChaines.length; i++)
         {
            {
               if(!sousChaines[i].equals(""))
               {
                   
                   // VERIFICATIN SI LE SOUS CHAINE DEUX EXISTE DANS LE TABLEAU DE L'ALPHABET
                  String ch2 = sousChaines[i].substring(0, 1);
                  ch2 = ch2.toUpperCase();
                  ch2+= sousChaines[i].substring(1).toLowerCase() + " "; // on ajoute 1 espace entre les 2 sous-chaines
                  // mais aussi apres la derniere
                  //System.out.println("oui identique : "+ ch2.toUpperCase()+"\n");

                  String comp_ch2 = ch2.toUpperCase().trim();
                  
                  
                  if (comp_ch2.equals("I") || comp_ch2.equals("II") || comp_ch2.equals("III") || comp_ch2.equals("IV") 
                          || comp_ch2.equals("V") || comp_ch2.equals("VI") || comp_ch2.equals("VII") || comp_ch2.equals("VIII") || comp_ch2.equals("IX")
                          || comp_ch2.equals("X") || comp_ch2.equals("XI") || comp_ch2.equals("XII") || comp_ch2.equals("XIII") || comp_ch2.equals("XIV") || comp_ch2.equals("XV")
                          || comp_ch2.equals("XVI") || comp_ch2.equals("XVII") || comp_ch2.equals("XVIII") || comp_ch2.equals("XIX") || comp_ch2.equals("XX") || comp_ch2.equals("XXI") 
                          || comp_ch2.equals("XXII") || comp_ch2.equals("XXIII") || comp_ch2.equals("XXIV") || comp_ch2.equals("XXV") || comp_ch2.equals("XXVI") 
                          || comp_ch2.equals("XXVII") || comp_ch2.equals("XXVIII") || comp_ch2.equals("XXIX") || comp_ch2.equals("XXX") || comp_ch2.equals("XXXI") 
                          || comp_ch2.equals("XXXII") || comp_ch2.equals("XXXIII") || comp_ch2.equals("XXXIV") || comp_ch2.equals("XXXV") || comp_ch2.equals("XXXVI") 
                          || comp_ch2.equals("XXXVII") || comp_ch2.equals("XXXVIII") || 	comp_ch2.equals("XXXIX") || comp_ch2.equals("XL") || comp_ch2.equals("XLI") 
                          || comp_ch2.equals("XLII") || comp_ch2.equals("XLIII") || comp_ch2.equals("XLIV") || comp_ch2.equals("XLV") || comp_ch2.equals("XLVI") || comp_ch2.equals("XLVII")
                          || comp_ch2.equals("XLVIII") || 	comp_ch2.equals("XLIX") || comp_ch2.equals("L") || comp_ch2.equals("LI") || comp_ch2.equals("LII") 
                          || comp_ch2.equals("LIII") || comp_ch2.equals("LIV") || comp_ch2.equals("LV") || comp_ch2.equals("LVI") || comp_ch2.equals("LVII") 
                          || comp_ch2.equals("LVIII") || comp_ch2.equals("LIX") || comp_ch2.equals("LX") || comp_ch2.equals("LXI") || comp_ch2.equals("LXII") 
                          || comp_ch2.equals("LXIII") || comp_ch2.equals("LXIV") || comp_ch2.equals("LXV") || comp_ch2.equals("LXVI") || comp_ch2.equals("LXVII") 
                          || comp_ch2.equals("LXVIII") || comp_ch2.equals("LXIX") || comp_ch2.equals("LXX") || comp_ch2.equals("LXXI") || comp_ch2.equals("LXXII") 
                          || comp_ch2.equals("LXXIII")|| comp_ch2.equals("RN.")|| comp_ch2.equals("RN. ")|| comp_ch2.equals(" RN.")|| comp_ch2.equals("Rn.")|| comp_ch2.equals("R.N")
                          || comp_ch2.equals("RN")|| comp_ch2.equals("RN1") || comp_ch2.equals("RN2") || comp_ch2.equals("RN3") || comp_ch2.equals("RN4") || comp_ch2.equals("RN5") 
                          || comp_ch2.equals("R.N.5")|| comp_ch2.equals("R/N/5")|| comp_ch2.equals("CR")|| comp_ch2.equals("C.R")|| comp_ch2.equals("PI")|| comp_ch2.equals("P.I")
                          || comp_ch2.equals("P.N.D")|| comp_ch2.equals("(F)")|| comp_ch2.equals("( F )")|| comp_ch2.equals("( F)")|| comp_ch2.equals("(F )")|| comp_ch2.equals("ET")
                          || comp_ch2.equals("SP")|| comp_ch2.equals("C/R")|| comp_ch2.equals("C.R.")|| comp_ch2.equals("C/R.")|| comp_ch2.equals("DPE")|| comp_ch2.equals("D.P.E")
                          || comp_ch2.equals("E.P.P")|| comp_ch2.equals("EPP")|| comp_ch2.equals("(EPP)")|| comp_ch2.equals("C.E.G")|| comp_ch2.equals("(C.E.G)")|| comp_ch2.equals("CEG")|| comp_ch2.equals("FKT")|| comp_ch2.equals("IVE")
                          || comp_ch2.equals("BIS")|| comp_ch2.equals("B.I.S")|| comp_ch2.equals("FLM")|| comp_ch2.equals("(FLM)")|| comp_ch2.equals("F.L.M")|| comp_ch2.equals("FJKM")|| comp_ch2.equals("F.J.K.M")){
                      
                      
                      // Mise en majuscule des chaines trouvé
                      chaine2 += ch2.toUpperCase();
                      
                      //System.out.println("oui identique : "+ ch2.toUpperCase()+"\n");
                  }else{
                      chaine2+= ch2;
                  }
               }  
            }
         }
         //System.out.println("chaine1 = \"" + chaine1 + "\"");
         //chaine2 = chaine2.trim(); // *** suppression de l'espace final
         //System.out.println("chaine2 = \"" + chaine2 + "\"\n");
         
         //Ou bien
         //System.out.println("chaine1 avant = \"" + chaine1 + "\"");
         chaine2 = chaine2.trim(); // *** suppression de l'espace final
         chaine1 = chaine2;
         //System.out.println("chaine1 apres = \"" + chaine1 + "\"");
         
         
        return chaine2;
    }
    
    public static CellRangeAddress GetAdresseCellule(String[] debut, String[] fin){
       
        String[] cellStrings = (debut+":"+fin).split(":");
        CellReference start = new CellReference(cellStrings[0]);
        CellReference end = new CellReference(cellStrings[1]);

        //CellRangeAddress address = new CellRangeAddress(start.getRow(), end.getRow(), start.getCol(), end.getCol());
        //return address;
        return new CellRangeAddress(start.getRow(), end.getRow(), start.getCol(), end.getCol());
    }
    
    
    public static String ConvertSlashToUnderscore(String textToConvert){
        
        String txt1 = textToConvert.replace("/","");
        return txt1.replace(" ","_");
        
        //return replaceString;
    }
    
    
    public static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        //java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        //return sDate;
        return new java.sql.Date(uDate.getTime());
    }
    
    
    public static java.sql.Date convertStringToSql(String StringDate) {
        String[] tabDate = StringDate.split("/");
        
        DateFormat df = new SimpleDateFormat("dd-MM-YYYY");
       
        
        Date daty = new Date();
        //daty.setMonth(Integer.parseInt(tabDate[1]));
        //daty.setYear(Integer.parseInt(tabDate[2]));
        //daty.setDate(Integer.parseInt(tabDate[0]));
        
        
        daty.setMonth(11);
        daty.setYear(2020);
        daty.setDate(11);

        Calendar c = null;
        
        c.set(2020, 11, 24);
        
        //c.get
        
//System.out.println("daty = "+ daty);


        //daty.setDate(Integer.parseInt(StringDate.replace("/", "")));
        
        //System.out.println("daty = "+ StringDate.replace("/", ""));
        
        java.util.Date d;
        d = new java.util.Date(df.format(daty));
        
        java.sql.Date sDate = new java.sql.Date(d.getTime());
        
        return sDate;
    }
    
    
    public static void resetTable(DefaultTableModel table){
        // VIDAGE DU table passé en paramètre 
        for(int i = table.getRowCount(); i > 0; --i){
            table.removeRow(i-1);  
        }
    }
    
    
    
    public static String ConvertOcmToOcfm(String operation){
        
        String newOperation = operation;
        
        //System.out.println("simple opération vaut = "+ operation);
        
        //System.out.println("opération dans formats = "+ operation);
        
        if(operation.toLowerCase().equals("ocm")){
            newOperation = "OCFM";
        }else if(operation.toLowerCase().equals("ogcf")){
            newOperation = "OGCF";
        }
        
        //System.out.println("opération dans formats retour vaut = "+ newOperation);
        
        return newOperation.toUpperCase();
    }
    
    
    
    public static String ConvertOcfmToOcm(String operation){
        
        String newOperation = operation;

        if(operation.toLowerCase().equals("ocfm")){
            newOperation = "ocm";
        }else if(operation.toLowerCase().equals("ogcf")){
            newOperation = "ogcf";
        }
        
        return newOperation.toLowerCase();
    }
    
}
