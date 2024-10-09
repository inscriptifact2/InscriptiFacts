/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isf.dao;

import com.isf.beans.UserBean;
import com.isf.beans.UserRegistration;
import com.isf.utils.Utils;
//import com.isf.utils.InscriptConfigServerLoader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.mail.MessagingException;
import javax.mail.internet.*;
import oracle.sql.ARRAY;
import oracle.sql.Datum;

/**
 *
 * @author raok1
 */
public class UserDAO extends ISFBean {

    private static final String QUERY_END = "')";
    private static final String QUERY_LOGIN_START = "select rtrim(userRole),rtrim(userAgreementStatus) from userRegistrations where  rtrim(useridentity) ='";
    private static final String QUERY_LOGIN_END1 = "' and (rtrim(userpassword) ='";
    private static final String QUERY_LOGIN_END2 = "' or rtrim(userpassword) ='";

    public Object getUser(String userid, boolean isAdmin) {
        String query = " Select  nvl(salutation,' '),nvl(namesuffix,' '),"
                + "rtrim(useridentity),nvl(lastname,' '),nvl(firstname ,' '),"
                + "nvl(middlename,' '),email,institution,instCountry,usercity,"
                + "userstate,usercountry,nvl(zipcode,' '),mobile,rtrim(userrole),"
                + "nvl(createdby,'SELF'),useragreementstatus,nvl(addressline1,' ')"
                + ",nvl(addressline2,' '), to_char(useragreementdate , 'MON-DD-YYYY HH24:MI:SS') from userRegistrations"
                + " where rtrim(userIdentity) = '" + userid + "'";
        if (!isAdmin) {
            query = query + " and lower(rtrim(userrole))='user'";
        }
        UserRegistration userReg = null;
        try {
            setupConnection();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData resultsetmetadata = rs.getMetaData();
            int i = resultsetmetadata.getColumnCount();
            rs.next();
            userReg= populateUser(rs,i);
            userReg.setAction("EDIT");

        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            release();
            return userReg;
        }
    }

    private UserRegistration populateUser(ResultSet rs, int i) 
            throws SQLException , Exception{
        UserRegistration userReg = new UserRegistration();
        Object obj = null;
        String s = null;
        for (int j = 1; j <= i; j++) {
                obj = rs.getObject(j);
                
                if(obj instanceof String)
                    s = obj.toString().trim();
                else
                    s="";
                
            switch (j) {
                case 1:
                    userReg.setSalutation(s);
                    break;
                case 2:
                    userReg.setNameSuffix(s);
                    break;
                case 3:
                    userReg.setUserIdentity(s);
                    break;
                case 4:
                    userReg.setLastName(s);
                    break;
                case 5:
                    userReg.setFirstName(s);
                    break;
                case 6:
                    userReg.setMiddleName(s);
                    break;
                case 13:
                    userReg.setZip(s);
                    break;
                case 15:
                    userReg.setUserRole(s);
                    break;
                case 16:
                    userReg.setCreatedBy(s);
                    break;
                case 17:
                    userReg.setUserAgreementStatus(s);
                    break;
                case 18:
                    userReg.setAdd1(s);
                    break;
                case 19:
                    userReg.setAdd2(s);
                    break;
                case 20:
                    userReg.setUserAgreementDate(s);
                    break;
                case 7:
                    if(obj!=null){
                 
                    oracle.sql.ARRAY array = (oracle.sql.ARRAY)(((weblogic.jdbc.wrapper.Array)obj).unwrap(Class.forName("oracle.sql.ARRAY")));
                    Datum adatum[] = array.getOracleArray();
                    
                    if (adatum[0] != null) {
                        userReg.setPrimaryEmail(new String(adatum[0].getBytes())
                                .trim());
                    }
                    if (adatum[1] != null) {
                        userReg.setAltEmail1(new String(adatum[1].getBytes())
                                .trim());
                    }
                    if (adatum[2] != null) {
                        userReg.setAltEmail2(new String(adatum[2].getBytes())
                                .trim());
                    }
                    if (adatum[3] != null) {
                        userReg.setAltEmail3(new String(adatum[3].getBytes())
                                .trim());
                    }
                    if (adatum[4] != null) {
                        userReg.setAltEmail4(new String(adatum[4].getBytes())
                                .trim());
                    }
                    }

                    break;
                case 8:
                    if(obj!=null){
                    oracle.sql.ARRAY array = (oracle.sql.ARRAY)(((weblogic.jdbc.wrapper.Array)obj).unwrap(Class.forName("oracle.sql.ARRAY")));
                    Datum insDatum[] =array.getOracleArray();;
                    if (insDatum[0] != null) {
                        userReg.setPriInstitution(new String(insDatum[0]
                                .getBytes()).trim());
                    }
                    if (insDatum[1] != null) {
                        userReg.setAltInstitution(new String(insDatum[1]
                                .getBytes()).trim());
                    }
                    }
                    break;
                case 9:
                    if(obj!=null){
                    oracle.sql.ARRAY array = (oracle.sql.ARRAY)(((weblogic.jdbc.wrapper.Array)obj).unwrap(Class.forName("oracle.sql.ARRAY")));
                    Datum icDatum[] =array.getOracleArray();;
                    if (icDatum[0] != null) {
                        userReg.setPriInstCountry(new String(icDatum[0]
                                .getBytes()).trim());
                    }
                    if (icDatum[1] != null) {
                        userReg.setAltInstCountry(new String(icDatum[1]
                                .getBytes()).trim());
                    }
                    }
                    break;
                case 10:
                    if(obj!=null){
                    oracle.sql.ARRAY array = (oracle.sql.ARRAY)(((weblogic.jdbc.wrapper.Array)obj).unwrap(Class.forName("oracle.sql.ARRAY")));
                    Datum ucDatum[] =array.getOracleArray();;
                    if (ucDatum[0] != null) {
                        userReg.setUserCity(new String(ucDatum[0].getBytes())
                                .trim());
                    }
                    if (ucDatum[1] != null) {
                        userReg.setAltCity(new String(ucDatum[1].getBytes())
                                .trim());
                    }
                    }
                    break;
                case 11:
                    if(obj!=null){
                    oracle.sql.ARRAY array = (oracle.sql.ARRAY)(((weblogic.jdbc.wrapper.Array)obj).unwrap(Class.forName("oracle.sql.ARRAY")));
                    Datum usDatum[] =array.getOracleArray();;
                    if (usDatum[0] != null) {
                        userReg.setUserState(new String(usDatum[0].getBytes())
                                .trim());
                    }
                    if (usDatum[1] != null) {
                        userReg.setAltState(new String(usDatum[1].getBytes())
                                .trim());
                    }
                    }
                    break;
                case 12:
                    if(obj!=null){
                    oracle.sql.ARRAY array = (oracle.sql.ARRAY)(((weblogic.jdbc.wrapper.Array)obj).unwrap(Class.forName("oracle.sql.ARRAY")));
                    Datum uCyDatum[] =array.getOracleArray();;
                    if (uCyDatum[0] != null) {
                        userReg.setUserCountry(new String(uCyDatum[0]
                                .getBytes()).trim());
                    }
                    if (uCyDatum[1] != null) {
                        userReg.setAltCountry(new String(uCyDatum[1]
                                .getBytes()).trim());
                    }
                    }
                    break;
                case 14:
                    if(obj!=null){
                    oracle.sql.ARRAY array = (oracle.sql.ARRAY)(((weblogic.jdbc.wrapper.Array)obj).unwrap(Class.forName("oracle.sql.ARRAY")));
                    Datum mDatum[] =array.getOracleArray();;
                    if (mDatum[0] != null) {
                        userReg.setPrimaryMobile(new String(mDatum[0]
                                .getBytes()).trim());
                    }
                    if (mDatum[1] != null) {
                        userReg.setAltMobile(new String(mDatum[1]
                                .getBytes()).trim());
                    }
                    }
                    break;
                default:
                    break;
            }
        }
        return userReg;
    }

    public ArrayList<UserRegistration> getAllUsers() {
                String query = " Select  nvl(salutation,' '),nvl(namesuffix,' '),"
                + "rtrim(useridentity),nvl(lastname,' '),nvl(firstname ,' '),"
                + "nvl(middlename,' '),email,institution,instCountry,usercity,"
                + "userstate,usercountry,nvl(zipcode,' '),mobile,rtrim(userrole),"
                + "nvl(createdby,'SELF'),useragreementstatus,nvl(addressline1,' ')"
                + ",nvl(addressline2,' '), to_char(useragreementdate , 'MON-DD-YYYY HH24:MI:SS') from userRegistrations"
                + " where lower(rtrim(userrole))='user' "
                + "or lower(rtrim(userrole))='admin'";


        UserRegistration userReg = null;
        ArrayList<UserRegistration> users = new ArrayList<UserRegistration>();
        try {
            setupConnection();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData resultsetmetadata = rs.getMetaData();
            int i = resultsetmetadata.getColumnCount();
            while (rs.next()) {
                userReg = populateUser(rs,i);
                userReg.setAction("EDIT");
                users.add(userReg);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            release();
            return users;
        }
    }

    public String editUser(UserBean user) {
        String query = " update userRegistrations set userName='" + user.getUserName() + "', userPassword='" + user.getUserPassword() + "',userRole='"
                + user.getUserRole() + "' where rtrim(userIdentity) ='" + user.getUserIdentity() + "'";


        String msg = "";

        try {
            setupConnection();
            int i = st.executeUpdate(query);
            if (i >= 1) {
                msg = "SUCCESS";
            }
        } catch (Exception exp) {

            exp.printStackTrace();
            msg = exp.getMessage();

        } finally {
            release();
            return msg;
        }
    }

    public String updatePassword(String user, String opwd, String newPWD) {
        String query = "update userRegistrations set userPassword='" + Utils.encrypt(newPWD) + "' where rtrim(userIdentity)='" + user + "' ";
        if (opwd != null) {
            query += " and (rtrim(userPassword)='" + opwd + "' or rtrim(userPassword)='"+Utils.encrypt(opwd)+"')";
        }
        String msg = "";
        System.out.println("query:" + query);
        setupConnection();
        int i = 0;
        try {
            i = st.executeUpdate(query);
            msg = "Success";
        } catch (Exception exp) {
            msg = exp.getMessage();
            exp.printStackTrace();
        } finally {
            release();
            return msg;
        }
    }

    public UserRegistration loginUser(String uid, String pwd) {
        String query = "";
        UserRegistration reg = null;

        if (uid != null && pwd != null) {
            query = QUERY_LOGIN_START + uid + QUERY_LOGIN_END1 + pwd 
                    + QUERY_LOGIN_END2 + Utils.encrypt(pwd) +QUERY_END;
        }
        setupConnection();
        try {

            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                reg = new UserRegistration();
                reg.setUserRole(rs.getString(1).trim());
                reg.setUserAgreementStatus(rs.getString(2).trim());
                reg.setUserIdentity(uid);


            }

        } catch (Exception exp) {
            exp.printStackTrace();

        } finally {

            release();

            return reg;
        }
    }

    public String forgotPassword(String uid, String email)
            throws SQLException, MessagingException, AddressException, Exception {
        String status = "";
        String newPass = Utils.getNewPassword();
        String name = "";
        try {
            boolean valid = validateUser(uid, email, name);
            if (valid) {
                setupConnection();

                int i = st.executeUpdate("update userregistrations set USERPASSWORD='" + Utils.encrypt(newPass) + "' where rtrim(USERIDENTITY)='" + uid + "'");
                if (i == 1) {
                    String subject = "Your request for a new password has been processed.";
                    String body = "Dear InscriptiFact User, \n    We have received a request to reset the password for your account.\n "
                            + "A new password has been generated as below: \n\nYour Username:  " + uid
                            + "\n" + "Your Password:    " + newPass + "\n\n"
                            + "Please ensure you change your password at the next login.\n\n"
                            + "InscriptiFact Team";
                    if (Utils.getInstance().sendEmail(body, subject, email)) {
                        status = "Your password has been sent to your email.";
                    } else {
                        con.rollback();
                        status = "Some error occured. Please try later.";
                    }

                } else {
                    //     con.rollback();
                    status = "Some error occured. Please try later.";
                }
            }else{
            status = "Please check your useridentity and email address.";
            }
        } catch (SQLException sqlexception) {
            con.rollback();
            status = "Error:" + sqlexception.getMessage();
            sqlexception.printStackTrace();
            throw sqlexception;
        } finally {
          //  con.commit();
            release();
           return status;

        }
        
    }

    public boolean validateUser(String userid, String email, String name) {
        String query = " Select rtrim(lastName), rtrim(firstname),rtrim(b.column_value)"
                + " as email from userregistrations a, table(email) b where "
                + "rtrim(userIdentity) = '" + userid + "'";
        boolean found = false;
        String lname, fname, em = null;

        try {
            setupConnection();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                lname = rs.getString(1);
                fname = rs.getString(2);
                em = rs.getString(3);
                if (em != null) {
                     if (email.trim().equalsIgnoreCase(em)) {
                        found = true;
                        name = lname + ", " + fname;
                        break;
                    }
                }

            }
        } catch (Exception exp) {
            exp.printStackTrace();

        } finally {
            release();
            return found;
        }
    }

    public ArrayList getUniqueIdentities() {
        ArrayList list = new ArrayList();
        try {
            setupConnection();
            ResultSet rs = st.executeQuery("Select rtrim(userIdentity) from userRegistrations");
            while (rs.next()) {
                list.add(rs.getString(1).trim());
            }
        } catch (Exception exp) {
            exp.printStackTrace();

        } finally {
            release();
            return list;
        }
    }

    public String updateUser(UserRegistration user) throws Exception {
        StringBuffer query = new StringBuffer("update userregistrations set salutation =");

        query.append("'" + user.getSalutation() + "',");
        query.append("namesuffix='" + user.getNameSuffix() + "',");
        query.append("lastname='" + user.getLastName() + "',");
        query.append("firstname='" + user.getFirstName() + "',");
        query.append("middlename='" + user.getMiddleName() + "',");
        query.append("email=useremail_va('" + user.getPrimaryEmail() + "','" + user.getAltEmail1()
                + "','" + user.getAltEmail2() + "','" + user.getAltEmail3() + "','" + user.getAltEmail4() + "'),");
        query.append("institution=userinstitution_va('" + user.getPriInstitution() + "','" + user.getAltInstitution() + "'),");
        query.append("instCountry=userinstcountry_va('" + user.getPriInstCountry() + "','" + user.getAltInstCountry() + "'),");
        query.append("usercity=usercity_va('" + user.getUserCity() + "','" + user.getAltCity() + "'),");
        query.append("userstate=userstate_va('" + user.getUserState() + "','" + user.getAltState() + "'),");
        query.append("usercountry=usercountry_va('" + user.getUserCountry() + "','" + user.getAltCountry() + "'),");
        query.append("zipcode='" + user.getZip() + "',");
        query.append("mobile=usermobile_va('" + user.getPrimaryMobile() + "','" + user.getAltMobile() + "'),");
        query.append("userrole='" + user.getUserRole() + "',");
        query.append("addressline1='" + user.getAdd1() + "',");
        query.append("addressline2='" + user.getAdd2() + "' ");
        query.append(" where rtrim(useridentity)='" + user.getUserIdentity() + "'");



        int i = 0;
        String msg = "";
        try {
            setupConnection();
            i = st.executeUpdate(query.toString());
            if (i > 0) {
                msg = "You have successfully modified the user details.";
            }


        } catch (Exception exp) {
            msg = exp.getMessage();
            exp.printStackTrace();

        } finally {
            release();
        }


        return msg;
    }

    public String registerUser(UserRegistration user) throws Exception {
        StringBuffer query = new StringBuffer("insert into userregistrations ( salutation,namesuffix,useridentity,userpassword,"
                + "lastname,firstname,middlename,email,institution,instCountry,usercity,userstate,"
                + "usercountry,zipcode,mobile,userrole,createdby,createdon,useragreementstatus,"
                + "useragreementdate,useractivated,lastaccessedtime,addressline1,addressline2) values(");
        query.append("'" + user.getSalutation() + "',");
        query.append("'" + user.getNameSuffix() + "',");
        query.append("'" + user.getUserIdentity() + "',");
        query.append("'" + Utils.encrypt(user.getUserPassword()) + "',");
        query.append("'" + user.getLastName() + "',");
        query.append("'" + user.getFirstName() + "',");
        query.append("'" + user.getMiddleName() + "',");
        query.append("useremail_va('" + user.getPrimaryEmail() + "','" + user.getAltEmail1()
                + "','" + user.getAltEmail2() + "','" + user.getAltEmail3() + "','" + user.getAltEmail4() + "'),");
        query.append("userinstitution_va('" + user.getPriInstitution() + "','" + user.getAltInstitution() + "'),");
        query.append("userinstcountry_va('" + user.getPriInstCountry() + "','" + user.getAltInstCountry() + "'),");
        query.append("usercity_va('" + user.getUserCity() + "','" + user.getAltCity() + "'),");
        query.append("userstate_va('" + user.getUserState() + "','" + user.getAltState() + "'),");
        query.append("usercountry_va('" + user.getUserCountry() + "','" + user.getAltCountry() + "'),");
        query.append("'" + user.getZip() + "',");
        query.append("usermobile_va('" + user.getPrimaryMobile() + "','" + user.getAltMobile() + "'),");
        query.append("'" + user.getUserRole() + "',");
        query.append("'" + user.getCreatedBy() + "',");
        query.append("sysdate,");
        query.append("'" + user.getUserAgreementStatus() + "',");
        query.append("sysdate,'A','");
        query.append(user.getLastAccessedTime() + "',");
        query.append("'" + user.getAdd1() + "',");
        query.append("'" + user.getAdd2() + "')");


        int i = 0;
        String msg = "";
        try {
            setupConnection();
            i = st.executeUpdate(query.toString());
            /*
             * TODO: modifications needed
             */
            String header = "";
            StringBuilder instructions = new StringBuilder();
            if (user.getCreatedBy().equalsIgnoreCase("SELF")) {
                header = "You have completed the InscriptiFact user registration successfully.\n\n";

            } else {
                //add user..
                header = "You have been registered as an InscriptFact user.\n\n";
                if (user.getUserAgreementStatus().equalsIgnoreCase("A")) {
                    msg = "User created successfully. Email has been sent to the user at " + user.getPrimaryEmail() + ".";
                } else {
                    msg = "User created successfully. Email has been sent to the user at " + user.getPrimaryEmail()
                            + ".\nUser needs to be adviced to log onto the admin portal and accept the User Agreement "
                            + " before using the ISF Application.";
                    instructions.append("Before starting InscriptiFact, you need to sign the User Agreement.\n").append(" Instructions to sign the User Agreement:\n")
                            .append(" Go to: <server>:7060/UserPortal and log in using the above credentials. \n").append(" On successful logon, you would be presented with the User Agreement.\n")
                            .append(" Please accept the same to sign the User Agreement.\n\n");

                }
            }



            StringBuilder body = new StringBuilder();
            body.append(header).append("Your username and password for InscriptiFact are as follows:\n\nUsername:    ").append(user.getUserIdentity())
                    .append("\nPassword:    ").append(user.getUserPassword()).append("\n\n").append(instructions.toString())
                    .append("To start InscriptiFact, go to:  \n").append("<server>/Inscriptifact/ and follow the instructions.\n\n")
                    .append("Instructions and sample searches can be found at: \n").append("http://www.inscriptifact.com/instructions/index.html\n\n")
                    .append("The sample searches are designed to walk you through the various features of InscriptiFact.\n\n")
                    .append("A standalone viewer to view PTMs is available at \n").append("<server>/ISFStandaloneViewer/. \n")
                    .append("You can download PTM (RTI) images from InscriptiFact, and \n").append("work with them on your own computer using the standalone viewer.\n\n")
                    .append("NOTE: WHEN LEAVING INSCRIPTIFACT IT IS IMPORTANT THAT YOU GO BACK TO\n").append("THE LOGIN PAGE AND CLICK ON THE EXIT BUTTON. OTHERWISE WHEN YOU LOG IN \n")
                    .append("AGAIN YOU WILL RECEIVE A MESSAGE THAT SOMEONE WITH THE SAME USERNAME IS \n").append("ALREADY LOGGED IN.\n\n")
                    .append("If you have a Facebook account, you might be interested in going to the\n").append("InscriptiFact Page, and clicking on the Like button. If you do so,\n")
                    .append("you will receive notifications through Facebook of times \n").append("when InscriptiFact is down for maintenance, or of new image uploads.\n\n")
                    .append("Don't hesitate to contact isfsupp@usc.edu if we can be of help with InscriptiFact.  \n\n").append("InscriptiFact Team");

            if (Utils.getInstance().sendEmail(body.toString(), "InscriptiFact User", user.getPrimaryEmail()) && user.getCreatedBy().equalsIgnoreCase("SELF")) {
                msg = "You have completed the InscriptiFact user registration successfully. \n A confirmation email will be sent to you at " + user.getPrimaryEmail();
            }
        } catch (Exception exp) {
            msg = exp.getMessage();

            exp.printStackTrace();
            i = -1;
        } finally {
            release();


        }
        if (i == -1) {
            throw new Exception(msg);
        }

        return msg;
    }

    public String updateAgreement(String userid, String status) {
        StringBuffer query = new StringBuffer("update userregistrations set ");
        query.append("useragreementstatus='" + status + "',");
        query.append("useragreementdate= sysdate");
        query.append(" where rtrim(useridentity)='" + userid + "'");
        int i = 0;
        String msg = "";
        try {
            setupConnection();
            i = st.executeUpdate(query.toString());
            if (i > 0) {
                msg = "SUCCESS";
            }


        } catch (Exception exp) {
            msg = exp.getMessage();
            exp.printStackTrace();

        } finally {
            release();
        }


        return msg;
    }

    public void migrateUser() {
        UserRegistration reg = new UserRegistration();
        try {
            setupConnection();
            ResultSet rs = st.executeQuery("Select useridentity,userpassword,username,userrole,loginstatus,lastaccessedtime from isfusers");
            while (rs.next()) {
                reg.setUserIdentity(rs.getString(1));
                reg.setUserPassword(rs.getString(2));
                reg.setLastName(rs.getString(3));
                reg.setFirstName(" ");
                reg.setUserRole(rs.getString(4));
                reg.setLoginStatus(rs.getString(5));
                reg.setUserAgreementStatus("A");

                reg.setCreatedBy("SELF");
                registerUser(reg);

            }
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            release();
        }

    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        dao.migrateUser();
    }

    public int validateUID(String userid) {
        String query = " Select count(*) from userregistrations where lower(rtrim(userIdentity)) = '" + userid.toLowerCase() + "'";
        int found = 0;


        try {
            setupConnection();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    found = 1;
                } else {
                    found = 0;
                }

            }
        } catch (Exception exp) {
            exp.printStackTrace();

        } finally {
            release();
            return found;
        }
    }
}