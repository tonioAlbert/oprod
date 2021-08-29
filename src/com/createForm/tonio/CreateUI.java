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

/**
 *
 * @author RAP
 */
public class CreateUI {
    
    
    
    
    
    public static void CreateDialogForm(JFrame jf, String title, String messages, int typeMessage){
        
        
        //JOptionPane.showMessageDialog(null, "Le champ mot de passe est requise !","Champ mot de passe requise", JOptionPane.INFORMATION_MESSAGE);
        
        
        //JTextField topicTitle = new JTextField();
        //JTextField topicDesc = new JTextField();
        //Object[] message = {"Title: ", topicTitle, "Description: ", topicDesc};

        Object[] message = {messages};

        JOptionPane pane = new JOptionPane(message, typeMessage);
        JDialog getTopicDialog =  pane.createDialog(jf, title);
        Point dialogLoc = getTopicDialog.getLocation();
        Point parentLoc = jf.getLocation();
        pane.setLocation(parentLoc.x + jf.getWidth(), dialogLoc.y);
        pane.setLocation(parentLoc.x , dialogLoc.y);

        getTopicDialog.setVisible(true);

        
           // JDialog dialogD = new JOptionPane(messages).createDialog(jf, title);
           // Point dialogLoc = dialogD.getLocation();
            //Point parentLoc = jf.getLocation();
           // dialogD.setLocation(parentLoc.x + jf.getWidth(), dialogLoc.y);
           //dialogD.setLocation(parentLoc.x , dialogLoc.y);
           // dialogD.setVisible(true);

        
    }
    
    
    
    
    public static void setFontAndPolicyMenuBar(JMenu j){
        Font fonte = new Font("Arial Narrow",Font.BOLD,14);
        j.setFont(fonte);
    }
    
    
    
}
