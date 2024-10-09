/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isf.utils;

import com.isf.dao.UserDAO;
import isf.common.ISFBase64;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author raok1
 */
public class Utils {
    Properties properties;
    String host, from, server;

    private Utils() {
        
         properties = new Properties();
         try{
            properties.load(getClass().getResourceAsStream("mailsettings.ini"));
         }catch(Exception exp){}
            host = properties.getProperty("mailHost");
            from = properties.getProperty("from");
            server = properties.getProperty("serverurl");
            properties.put("mail.smtp.host", host);
    }
    private static Utils utils = new Utils();

    public static Utils getInstance() {
        return utils;
    }

    public static ArrayList getValidIDs(String l, String f, String m, String e) {
        if (l == null) {
            l = "";
        }
        if (f == null) {
            f = "";
        }
        if (m == null || m.trim().length()==0) {
            m = "123";
        }
        if (e == null) {
            e = "@";
        }

        String l8, l7, l6, l5, l4, f4, f3, f2, f1, e8, m1, m2, m3;
        ArrayList list = new ArrayList();
        e8 = e.substring(0, e.indexOf("@")).replaceAll("[^\\w]", "");
        e8 = e8.length() > 8 ? e8.substring(0, 8) : e8;
        l8 = l.length() > 8 ? l.substring(0, 8) : l;
        l7 = l.length() > 7 ? l.substring(0, 7) : l;
        l6 = l.length() > 6 ? l.substring(0, 6) : l;
        l5 = l.length() > 5 ? l.substring(0, 5) : l;
        l4 = l.length() > 4 ? l.substring(0, 4) : l;
        f4 = f.length() > 4 ? f.substring(0, 4) : f;
        f3 = f.length() > 3 ? f.substring(0, 3) : f;
        f2 = f.length() > 2 ? f.substring(0, 2) : f;
        f1 = f.length() > 1 ? f.substring(0, 1) : f;
        m2 = m.substring(m.length() - 2);
        m1 = m.substring(m.length() - 1);
        m3 = m.substring(m.length() - 3);
//1
        if (!list.contains(e8)) {
            list.add(e8);
        }
        //2
        if (!list.contains(l8 + f1)) {
            list.add(l8 + f1);
        } else {
            list.add(l8 + "." + f1);
        }
        //3
        if (!list.contains(l7 + f1 + m2)) {
            list.add(l7 + f1 + m2);
        } else {
            list.add(l7 + m3);
        }
        //4
        if (!list.contains(l7 + f2)) {
            list.add(l7 + f2);
        } else {
            list.add(l7 + "-" + f2);
        }
        //5
        if (!list.contains(l6 + f2 + m2)) {
            list.add(l6 + f2 + m2);
        } else {
            list.add(l6 + "." + m3);
        }
        //6
        if (!list.contains(l4 + f2 + m2)) {
            list.add(l4 + f2 + m2);
        } else {
            list.add(l4 + "." + f2 + m2);
        }
        //7
        if (!list.contains(l5 + f3 + m1)) {
            list.add(l5 + f3 + m1);
        } else {
            list.add(l5 + "-" + f3 + m1);
        }
        //8
        if (!list.contains(l4 + f3 + m2)) {
            list.add(l4 + f3 + m2);
        } else {
            list.add(l4 + "_" + f3 + m2);
        }
        //9
        if (!list.contains(l5 + f4)) {
            list.add(l5 + f4);
        } else {
            list.add(l5 + "_" + f4);
        }
        //10
        if (!list.contains(l8 + m2)) {
            list.add(l8 + m2);
        } else {
            list.add(l7 + "-" + m2);
        }

        return list;

    }

    public static void main(String[] args) {


       Utils u = new Utils();
        u.sendEmail("test","test","kamesh.nittala@gmail.com");
    }
    
        public static String getNewPassword() {
        String pass = "";
        java.util.Random random = new java.util.Random();
        String seed = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijlmnopqrstuvwxyz";
        java.util.ArrayList ar = new java.util.ArrayList();

        for (int i = 0; i < seed.length(); i++) {
            ar.add(seed.charAt(i) + "");
        }
        java.util.Collections.shuffle(ar);

        for (int j = 0; j < 8; j++) {
            int roll = random.nextInt(ar.size());
            pass += (String) ar.get(roll);
            ar.remove(roll);

        }

        return pass;
    }
        
       public static String encrypt(String plaintext)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA"); //step 2
        } catch (NoSuchAlgorithmException e) {
           // throw new Exception(e.getMessage());
            e.printStackTrace();
        }
        try {
            md.update(plaintext.getBytes("UTF-8")); //step 3
        } catch (UnsupportedEncodingException e) {
           // throw new Exception(e.getMessage());
            e.printStackTrace();
        }

        byte raw[] = md.digest(); //step 4
        String hash = ISFBase64.encodeBytes(raw); //step 5
        return hash; //step 6
    } 
       


    public boolean sendEmail(String body, String subject, String toEmail) {
        boolean sent = false;
        try {
            

            Session session = Session.getDefaultInstance(properties, null);
            MimeMessage mimemessage = new MimeMessage(session);
            mimemessage.setFrom(new InternetAddress(from));
            mimemessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toEmail));
            mimemessage.setSubject(subject);
            MimeBodyPart mimebodypart = new MimeBodyPart();
            body = body.replaceAll("<server>", server);
            mimebodypart.setText(body);
            MimeMultipart mimemultipart = new MimeMultipart();
            mimemultipart.addBodyPart(mimebodypart);
            mimemessage.setContent(mimemultipart);
            Transport.send(mimemessage);
            sent=true;
        } catch (Exception exp) {
            exp.printStackTrace();
            System.out.println("HOST:"+host+"::FROM:"+from+"::SERVER:"+server);
            sent = false;
        }finally
        {
            return sent;
        }
    }
    
    public String getProperty(String prop){
        return properties.getProperty(prop);
    }
    
   
}
