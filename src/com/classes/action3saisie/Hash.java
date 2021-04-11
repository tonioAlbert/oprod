/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.action3saisie;

import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
/**
 *
 * @author RAP
 */
public class Hash {
    
    
    
    public static String getHash(byte[] inputBytes, String algorithm){
        String hasValue = "";
        
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(inputBytes);
            
            byte[] digestBytes = messageDigest.digest();
            
            hasValue = DatatypeConverter.printHexBinary(digestBytes).toLowerCase();
            
        }catch(Exception e){
            
        }
        return hasValue;
    }
    
    
    public static String SetHash(byte[] inputBytes, String algorithm){
        String hasValue = "";
        
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(inputBytes);
            
            byte[] digestBytes = messageDigest.digest();
            
            hasValue = DatatypeConverter.printHexBinary(digestBytes).toLowerCase();
            
        }catch(Exception e){
            System.out.println("Impossible de lancer de cryptage.");
        }
        return hasValue;
    }
    
}
