/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.sc.beans;

import isf.common.utils.LogManager;
import isf.sc.utils.UserObject;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author raok1
 */
public class AdminBean extends ISFBean {

    private static final String LOGINQUERY = "Select USERROLE FROM ISFUSERS WHERE  USERROLE = 'admin' and toUpperCase(USERIDENTITY) ='{UID}' AND USERPASSWORD='{UPWD}'";
    private static final String SEARCHUSER = "Select USERIDENTIFIER,USERNAME,USERROLE,LOGINSTATUS FROM ISFUSERS WHERE  toUpperCase(USERIDENTITY) LIKE '%{UID}%'";
    private static final String ADDUSER = "INSERT INTO ISFUSERS( USERIDENTIFIER,USERPASSWORD,USERNAME,USERROLE,LOGINSTATUS) VALUES('{UID}','UPWD{}','{UNAME}','{UROLE}','F')";
    private static final String RESETUSER = "UPDATE ISFUSERS SET LOGINSTATUS ='F' WHERE toUpperCase(USERIDENTITY) ='{UID}' )";
    private static final String EDITUSER = "UPDATE ISFUSERS SET USERNAME ='{UNAME}', USERROLE='{UROLE}', USERPASSWORD='{UPWD}' WHERE toUpperCase(USERIDENTITY) ='{UID}' )";
    private static final String DELETEUSER = "DELETE FROM ISFUSERS WHERE  toUpperCase(USERIDENTITY) IN {UID}";
    Logger log = LogManager.getLogger(isf.sc.beans.AdminBean.class);
    public boolean validateAdminUser(String user, String pwd) {
        String query = LOGINQUERY.replaceAll("{UID}", user.toUpperCase()).replaceAll("{UPWD}", pwd);
        boolean valid = false;
        setupConnection();
        ResultSet rs = null;
        try {
            rs = st.executeQuery(query);
            if (rs.next()) {
                valid = true;
            } else {
                valid = false;
            }
        } catch (Exception exp) {
            valid = false;
        } finally {
            release();
            return valid;
        }
    }

    public ArrayList searchForUser(String user) {
        String query = SEARCHUSER.replaceAll("{UID}", user.toUpperCase());
        ArrayList users = new ArrayList();
        UserObject obj = new UserObject();
        setupConnection();
        ResultSet rs = null;
        try {
            rs = st.executeQuery(query);
            while (rs.next()) {

                obj.setUserIdentifier(rs.getString(1));
                obj.setUserName(rs.getString(2));
                obj.setUserRole(rs.getString(3));
                obj.setLoginStatus(rs.getString(4));
                users.add(obj);
            }
        } catch (Exception exp) {
            log.error(exp);
        } finally {
            release();
            return users;
        }
    }

    public int resetUser(String user) {
        String query = RESETUSER.replaceAll("{UID}", user.toUpperCase());
        int result = -1;
        setupConnection();

        try {
            result = st.executeUpdate(query);

        } catch (Exception exp) {
            result = -1;
        } finally {
            release();
            return result;
        }

    }

    public int addUser(String user, String pwd, String name, String role) {
        String query = ADDUSER.replaceAll("{UID}", user).replaceAll("{UPWD}", pwd).replaceAll("{UNAME}", name).replaceAll("{UROLE}", role);
        int result = -1;
        setupConnection();

        try {
            result = st.executeUpdate(query);

        } catch (Exception exp) {
            result = -1;
        } finally {
            release();
            return result;
        }
    }

    public int editUser(String user, String pwd, String name, String role) {
        String query = EDITUSER.replaceAll("{UID}", user).replaceAll("{UPWD}", pwd).replaceAll("{UNAME}", name).replaceAll("{UROLE}", role);
        int result = -1;
        setupConnection();

        try {
            result = st.executeUpdate(query);

        } catch (Exception exp) {
            result = -1;
        } finally {
            release();
            return result;
        }
    }

    public int deleteUsers(String users) {
        String query = DELETEUSER.replaceAll("{UID}", users);
        int result = -1;
        setupConnection();

        try {
            result = st.executeUpdate(query);

        } catch (Exception exp) {
            result = -1;
        } finally {
            release();
            return result;
        }
    }
    
    public ArrayList<String> getLoggedUserIDs(String mode){
        ArrayList<String> list = new ArrayList<String>();
        String query="";
       if(mode.equalsIgnoreCase("RESET_HALF_DAY")){
        query = "select rtrim(useridentity) from userRegistrations where loginstatus='T' and lastaccessedtime <= sysdate - 0.5 ";  
       }else if(mode.equalsIgnoreCase("RESET_ONE_DAY")){
          query = "select rtrim(useridentity) from userRegistrations where loginstatus='T' and lastaccessedtime <= sysdate - 1 ";   
       }else if(mode.equalsIgnoreCase("RESET_ALL")){
          query = "select rtrim(useridentity) from userRegistrations where loginstatus='T' ";  
       }
       setupConnection();
       
       try {
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
            {
                list.add(rs.getString(1));
            }
        } catch (Exception exp) {
            log.error(exp);
        } finally {
            release();
            return list;
        }
       
       
        
    }
}
