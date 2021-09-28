/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.createForm.tonio;

import java.awt.Font;
import java.awt.Point;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author RAP
 */
public class CreateUI {
    
    
    
    
    public static boolean CreateYesOrNonDialogForm(JFrame jf, String title, String messages, int typeMessage){
        
        Object[] message = {messages};

        JOptionPane pane = new JOptionPane(message, typeMessage);
        JDialog getTopicDialog =  pane.createDialog(jf, title);
        Point dialogLoc = getTopicDialog.getLocation();
        Point parentLoc = jf.getLocation();
        pane.setLocation(parentLoc.x + jf.getWidth(), dialogLoc.y);
        pane.setLocation(parentLoc.x , dialogLoc.y);

        getTopicDialog.setVisible(true); 
        
        return true;
    }
    
    
    
    public static void CreateDialogForm(JFrame jf, String title, String messages, int typeMessage){
        
        Object[] message = {messages};

        JOptionPane pane = new JOptionPane(message, typeMessage);
        JDialog getTopicDialog =  pane.createDialog(jf, title);
        Point dialogLoc = getTopicDialog.getLocation();
        Point parentLoc = jf.getLocation();
        pane.setLocation(parentLoc.x + jf.getWidth(), dialogLoc.y);
        pane.setLocation(parentLoc.x , dialogLoc.y);

        getTopicDialog.setVisible(true);
 
    }
    
    
    
    
    public static void setFontAndPolicyMenuBar(JMenu j){
        Font fonte = new Font("Arial Narrow",Font.BOLD,14);
        j.setFont(fonte);
    }
     //Row headerRow5 = sheet.createRow(5);
    
    public static void HeaderCell(XSSFWorkbook wb,  Row headerRow, Integer createNumberCell, String cellValue, Boolean bold){
        
        
                // create table with data
                XSSFCellStyle cadre = wb.createCellStyle();
                cadre.setBorderBottom(BorderStyle.THIN);
                cadre.setBorderTop(BorderStyle.THIN);
                cadre.setBorderLeft(BorderStyle.THIN);
                cadre.setBorderRight(BorderStyle.THIN);
                
                
            XSSFCellStyle cellStyleBold = wb.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = wb.createFont();
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
            
            if(bold == true){
                Cell headerCell5 = headerRow.createCell(createNumberCell);
                headerCell5.setCellValue(cellValue);
                headerCell5.setCellStyle(cadre);
                headerCell5.setCellStyle(cellStyleBold);
            }else{
                Cell headerCell5 = headerRow.createCell(createNumberCell);
                headerCell5.setCellValue(cellValue);
                headerCell5.setCellStyle(cadre);
               // headerCell5.setCellStyle(cellStyleBold);
            }
        

    }
    
    
    
}
