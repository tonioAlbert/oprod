/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allInterfaces.action3saisien;

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
    
}
