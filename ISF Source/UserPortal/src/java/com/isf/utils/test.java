package com.isf.utils;
/** * @author Kushal Paudyal * Last Modified on 05/11/2011 * Java Regular Expression to Validate US and Canada phone numbers. */
import java.util.regex.Matcher;
import java.util.regex.Pattern; 
public class test{    
    /**     
     * REGEX IS: ^[+]?[01]?[- .]?(\([2-9]\d{2}\)|[2-9]\d{2})[- .]?\d{3}[- .]?\d{4}$     
     * Escape Sequences are added in the following String for back slash (\)     */   
    static String phoneValidationUSCandaRegex =        "^[+]?[01]?[- .]?(\\([2-9]\\d{2}\\)|[2-9]\\d{2})[- .]?\\d{3}[- .]?\\d{4}$";     
    /**     
     * This method compares the parameter string against the US Phone number     
     * regex and returns true if the pattern is matched - meaning the phone     
     * number is valid.     
     *    
     * The regular expression used here can handle the following rules:     
     * The starting character may be '+' but is optional     
     * If the country code is used, it can be either 0 or 1 and is optional     
     * Various codes (country, area) might be separated with '-' or '.' but is optional     
     * Area code should not start with 0 but may be optionally enclosed in round brackets     
     *     
     */   
    public static boolean isAValidUSPhoneNumber(String str) {        
        return Pattern.matches(phoneValidationUSCandaRegex, str);     
    }     
    public static void main (String [] args ) {        
        String [] phoneNumbersToValidate={
            "2-309-778-4234", //invalid
            "1-309-778-4423",
            "309-778-4235", 
            "3097784234", 
            "309.778.4234", 
            "01-309-798-4234",//invalid
            "001-309-798-4234", //invalid
            "0-309-798-4234", 
            "+1-309-798-4235",
            "1-3097980577",
            "1-309.7980578", 
            "1-(309)-788-8978",
            "+919849203571",
            "+1617-671-1208",
            "+44 (7958) 454227"
        };         
        for(int index=0;index<phoneNumbersToValidate.length;index++) 
        {            
            String number=phoneNumbersToValidate[index];
          //  boolean isValid=isAValidUSPhoneNumber(phoneNumbersToValidate[index]);
              String regex = "^\\+?[0-9. ()-]{10,20}$";        
              Pattern pattern = Pattern.compile(regex);        
              Matcher matcher = pattern.matcher(number);         
              boolean isValid =matcher.matches();           
                          
            System.out.println(number+" : "+(isValid?"Valid":"Invalid")); 
        }    
    }
}