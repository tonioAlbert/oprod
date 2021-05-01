/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.action3saisie;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

/**
 *
 * @author RAP
 */
public class MiseEnFormes {
       
    HSSFWorkbook wb;
    
    public MiseEnFormes() {
        
        //this.wb = wb_pram_construct;
    }
    
    
    public HSSFCellStyle MiseEnGras(HSSFWorkbook wb_pram_construct){
        
        HSSFCellStyle cellStyleBold = wb.createCellStyle();
        Font headerFont = wb.createFont();
        headerFont.setBold(true);
        cellStyleBold.setFont(headerFont);
        
        
        return cellStyleBold;
    }
    
    
    
    
    //HSSFWorkbook   wb = new HSSFWorkbook ();
    
}
