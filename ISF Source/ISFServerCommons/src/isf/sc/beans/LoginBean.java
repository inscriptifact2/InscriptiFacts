/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.sc.beans;

import isf.common.ISFBase64;
import isf.sc.utils.InscriptConfigServerLoader;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author raok1
 */
public class LoginBean extends ISFBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
    public String isValidUser(String s, String s1) throws SQLException {

        String flag = "Success";
        try {
            setupConnection();
            CallableStatement callablestatement = con.prepareCall("{ call ISFLoginNew(?,?,?) }");
            callablestatement.setString(1, s);
            callablestatement.setString(2, s1);
            callablestatement.setString(3, encrypt(s1));
            callablestatement.execute();
            callablestatement.close();
        } catch (SQLException sqle) {
            flag = sqle.getErrorCode() + "-" + sqle.getMessage();
            logger.error("exception:", sqle);
        } finally {
            release(con);

        }
        return flag;
    }
    
    public String updateAcceptance(String s, String s1) throws SQLException, Exception {
        String flag = "";
        try{
            setupConnection();
            st = con.createStatement();
        StringBuffer query = new StringBuffer("update userregistrations set ");
        query.append("useragreementstatus='A',");
        query.append("useragreementdate= sysdate");
        query.append(" where rtrim(useridentity)='" + s + "'");
             int i = st.executeUpdate(query.toString());
             flag = "SUCCESS";
        }catch(SQLException sqlexception){
          con.rollback();
            flag = "FAILURE";
            logger.error("Exception", sqlexception);
            throw sqlexception;  
        }
        finally{
            release();
            return flag;
        }
    }

    public boolean changePassword(String s, String s1, String s2)
            throws SQLException, Exception {
        boolean flag = false;

        try {
            setupConnection();
            st = con.createStatement();
            ResultSet resultset = st.executeQuery("select USERPASSWORD from userregistrations where (rtrim(USERPASSWORD)='" + s1 + "' or rtrim(USERPASSWORD)='"+encrypt(s1)+"') and rtrim(USERIDENTITY)='" + s + "'");
            if (resultset.next()) {
                resultset.close();
                String s3 = "update userregistrations set USERPASSWORD='" + s2 + "' where rtrim(USERIDENTITY)='" + s + "'";
                int i = st.executeUpdate(s3);
                if (i == 1) {
                    flag = true;
                } else {
                    flag = false;
                }
            } else {
                flag = false;
                resultset.close();
            }
        } catch (SQLException sqlexception) {
            con.rollback();
            flag = false;
            logger.error("Exception", sqlexception);
            throw sqlexception;
        } finally {
            release();

        }
        return flag;
        /*
         * do { } while(true);
         */
    }

    public boolean forgotPassword(String s, String s1)
            throws SQLException, MessagingException, AddressException, Exception {
        boolean flag = false;
        String s2 = getNewPassword();
        try {

            setupConnection();
            int i = st.executeUpdate("update userregistrations set USERPASSWORD='" + encrypt(s2) + "' where rtrim(USERIDENTITY)='" + s + "'");
            if (i == 1) {
                Properties properties = new Properties();
                try {
                    properties = InscriptConfigServerLoader.getInstance().getMailProperties();//load(getClass().getResourceAsStream("forgotpassword.ini"));
                    String s3 = properties.getProperty("mailHost");
                    String s4 = properties.getProperty("from");
                    String s5 = properties.getProperty("subject");
                    String s6 = "Dear InscriptiFact User, \n    We have received a request to reset the password for your account.\n "
                            + "A new password has been generated as below: \n\nYour Username:  " + s
                            + "\n" + "Your Password:    " + s2 + "\n\n"
                            + "Please ensure you change your password at the next login.\n\n"
                            + "InscriptiFact Team";
                    //String s6 = "Your Login ID : " + s + "\n" + "Your Password :" + s2 + "\n" + "Please ensure you change your password the next time you login.\n" + "Regards\n" + "Administrator, Inscriptifact Team";
                    properties.put("mail.smtp.host", s3);
                    Session session = Session.getDefaultInstance(properties, null);
                    MimeMessage mimemessage = new MimeMessage(session);
                    mimemessage.setFrom(new InternetAddress(s4));
                    mimemessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(s1));
                    mimemessage.setSubject(s5);
                    MimeBodyPart mimebodypart = new MimeBodyPart();
                    mimebodypart.setText(s6);
                    MimeMultipart mimemultipart = new MimeMultipart();
                    mimemultipart.addBodyPart(mimebodypart);
                    mimemessage.setContent(mimemultipart);
                    Transport.send(mimemessage);
                    logger.debug("Mail has been sent");
                    flag = true;
                } catch (Exception exception) {
                    con.rollback();
                    flag = false;
                    //exception);
                    logger.error("failed to send", exception);
                }
            } else {
                con.rollback();
                flag = false;
            }
        } catch (SQLException sqlexception) {
            con.rollback();
            flag = false;
            logger.error("Exception", sqlexception);
            throw sqlexception;
        } finally {
            con.commit();
            release();

        }
        return flag;
    }

    public void logout(String s) throws Exception {
        try {

            setupConnection();
            st.execute("update userregistrations set LOGINSTATUS='F',lastaccessedtime=sysdate where rtrim(USERIDENTITY)='" + s + "' and loginstatus='T'");
            st.execute("update logindetails set LOGOUTTIME=sysdate where rtrim(USERIDENTITY)='" + s + "' and logouttime is null");
        } catch (Exception exception) {
            logger.error(exception);
            throw exception;
        } finally {
            con.commit();
            release();
        }
    }

    public Vector getTextPublicationData() {
        Vector vector = new Vector();
        return vector;
    }

    public Vector getMainSearchData() {
        logger.debug("getting main Search Data:");
        Vector vector = new Vector();
        StringBuffer sb = new StringBuffer();
        String s = "";
        String s1 = "";
        try {
            setupConnection();
            // logger.debug("Cor:" + System.currentTimeMillis());
            sb.append(getParentData("select CORPUSNAME,  CORPUSCATEGORY ,CORPUSUBCATEGORY from corpusobject_ov order "
                    + "by  CORPUSNAME, CORPUSCATEGORYSEQNO, CORPUSSUBCATEGORYSEQNO"));
            // logger.debug("CorEnd: " + System.currentTimeMillis());
            sb.append(getParentData("select distinct medium from  textobject where medium is not null order by medium"));
            // logger.debug("Mediumend : " + System.currentTimeMillis());
            sb.append(getParentData("select distinct isffindsite from ISFFINDSITE_OV order by isffindsite"));
            // logger.debug("fSiteEnd : " + System.currentTimeMillis());
            sb.append(getParentData("select TIMELINENAME,x from (select TIMELINENAME,trim(NAMEDTIMEPERIOD) ||'('||tri"
                    + "m(TIMEPERIODBEGINDATEYEAR)|| ' ' || trim(TIMEPERIODBEGINDATEBCE_CE)||'-' || trim"
                    + "(TIMEPERIODENDDATEYEAR)|| ' ' || trim(TIMEPERIODENDDATEBCE_CE) ||')' x from time"
                    + "periodobject where TIMEPERIODBEGINDATEBCE_CE='BCE' order by TIMELINENAME,TIMEPER"
                    + "IODBEGINDATEBCE_CE,to_number(TIMEPERIODBEGINDATEYEAR ) desc) union all select TI"
                    + "MELINENAME,x from (select TIMELINENAME,trim(NAMEDTIMEPERIOD)||'('||trim(TIMEPERI"
                    + "ODBEGINDATEYEAR)|| ' ' || trim(TIMEPERIODBEGINDATEBCE_CE)||'-' || trim(TIMEPERIO"
                    + "DENDDATEYEAR)|| ' ' || trim(TIMEPERIODENDDATEBCE_CE) ||')' x from timeperiodobje"
                    + "ct where TIMEPERIODBEGINDATEBCE_CE='CE' order by TIMELINENAME,TIMEPERIODBEGINDAT"
                    + "EBCE_CE,to_number(TIMEPERIODBEGINDATEYEAR ))"));
            // logger.debug("TPend : " + System.currentTimeMillis());
            sb.append(getParentData("select distinct language from LANGUAGE_OV order by language"));
            //logger.debug("LanEnd : " + System.currentTimeMillis());
            sb.append(getParentData("select script,SCRIPTSUBLEVEL from  SCRIPT_OV group by script,SCRIPTSUBLEVEL order by script,scriptsublevel"));
            // logger.debug("ScrEnd : " + System.currentTimeMillis());
            sb.append(getParentData("select distinct  LOCATIONOFORIGINAL from LOCNOFORIGIN_OV order by locationoforiginal"));
            // logger.debug("LocEnd : " + System.currentTimeMillis());
            //  s = s + getParentData("select  distinct MAINTEXTORPUBLCNNOPREFIX  from  textobject where MAINTEXTORPUBLCNNOPREFIX is not null");
            sb.append(getParentData("select distinct MAINTEXTORPUBLCNNOPREFIX from ("
                    + "select  distinct rtrim(MAINTEXTORPUBLCNNOPREFIX) as MAINTEXTORPUBLCNNOPREFIX  from  textobject where "
                    + "MAINTEXTORPUBLCNNOPREFIX is not null union all select  distinct rtrim(ALTTEXTORPUBLCNNOPREFIX) as"
                    + " MAINTEXTORPUBLCNNOPREFIX  from  MAINORALTTEXTORPUBLCN_OV where ALTTEXTORPUBLCNNOPREFIX is not null) order by MAINTEXTORPUBLCNNOPREFIX"));
            //logger.debug("MTEnd : " + System.currentTimeMillis());
            //  s = s + getParentData("select  distinct ALTTEXTORPUBLCNNOPREFIX  from  MAINORALTTEXTORPUBLCN_OV where ALTTEXTORPUBLCNNOPREFIX is not null");
            //  logger.debug("ATEnd : "+System.currentTimeMillis());
        } catch (Exception exception) {
            String s2 = "<ERRMSG MSG='" + exception.toString() + "'/>";
            sb.append(s2);
        } finally {
            // logger.debug("SB:"+sb.toString());
            vector.addElement(sb.toString());
            release();

        }
        logger.debug("Vector:"+vector.size());
        return vector;
        /*
         * do { } while(true);
         */
    }

    private String getParentData(String s)
            throws Exception {
        StringBuffer s1 = new StringBuffer();
        try {


            ResultSet resultset = st.executeQuery(s);
            ResultSetMetaData resultsetmetadata = resultset.getMetaData();
            int i = resultsetmetadata.getColumnCount();
            while (resultset.next()) {
                s1.append("<RECORD>");
                for (int j = 1; j < i + 1; j++) {
                    s1.append("<RECORDVALUES KEY='" + resultsetmetadata.getColumnName(j) + "' VALUE='" + ISFBase64.getVal(resultset.getString(j)) + "' TYPE='CV'> </RECORDVALUES>");
                }

                s1.append("</RECORD>");
            }
            resultset.close();
            //  logger.debug("Text:"+s1.toString());
        } catch (Exception exception) {
            throw exception;
        }
        return s1.toString();
    }

    private String getNewPassword() {
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
    
     private  String encrypt(String plaintext)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA"); //step 2
        } catch (NoSuchAlgorithmException e) {
           // throw new Exception(e.getMessage());
            logger.error(e);
        }
        try {
            md.update(plaintext.getBytes("UTF-8")); //step 3
        } catch (UnsupportedEncodingException e) {
           // throw new Exception(e.getMessage());
           logger.error( e);
        }

        byte raw[] = md.digest(); //step 4
        String hash = ISFBase64.encodeBytes(raw); //step 5
        return hash; //step 6
    }       
}
