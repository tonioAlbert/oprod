/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.action3saisie;


import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author RAP
 */
public class BarreDeProgression {
    
    JProgressBar pBar;
    JLabel label;

    public BarreDeProgression(JProgressBar pBarParam, JLabel label) {
        this.pBar = pBarParam;
        this.label = label;
    }
    
    
    public void run(){
        int min = 0;
        int max = 100;
        
        
        pBar.setMaximum(min);
        pBar.setMinimum(max);
            
        this.label.setVisible(true);
        this.pBar.setVisible(true);
        
        for(int i = min ; i < max; i ++){
            
            this.pBar.setValue(i);
            this.label.setText(Integer.toString(i));

            
            try{
                
                Thread.sleep(500);
                
            }catch(InterruptedException inExc){
                inExc.getMessage();
            }
            
        }
        
        
    }
    
}
