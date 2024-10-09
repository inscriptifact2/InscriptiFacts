/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.sc.beans;

import isf.common.utils.LogManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import org.apache.log4j.Logger;

/**
 *
 * @author raok1
 */
public class ValidateUserBean extends ISFBean {

    private Logger logger = LogManager.getLogger(isf.sc.beans.ValidateUserBean.class);

    public ValidateUserBean() {
        // TODO implement ejbCreate if necessary, acquire resources
        // This method has access to the JNDI context so resource aquisition
        // spanning all methods can be performed here such as home interfaces
        // and data sources.
    }

    public String getValidSession(String user) throws Exception {
        String session = "";

        try {
            setupConnection();
            ResultSet rs = st.executeQuery("Select rtrim(useridentity)||'---'||max(loginseqno) from logindetails where rtrim(useridentity)='" + user + "'  group by useridentity");
            while (rs.next()) {
                session = rs.getString(1);
            }
            rs.close();

        } catch (SQLException sql) {
            logger.error("Exception", sql);

            throw sql;
        } finally {
            release();

        }
        return session;

    }

    public void killSession(String session)  {
        String user = "";
        if (session.indexOf("---") > -1) {
            user = session.substring(0, session.indexOf("---"));
        } else {
            user = session;
        }

        try {
            setupConnection();
            st.execute("update userregistrations set LOGINSTATUS='F', lastaccessedtime=sysdate where rtrim(USERIDENTITY)='" + user + "' and loginstatus='T'");
            st.execute("update logindetails set LOGOUTTIME=sysdate where rtrim(USERIDENTITY)='" + user + "' and logouttime is null");
        //  ISFSessionManager.getInstance().kill(session);
        } catch (Exception ie) {
            logger.error("Exception", ie);

        } finally {
            release();
        }

    }

    public Hashtable getSessions() throws Exception {
        Hashtable hs = new Hashtable();


        try {
            setupConnection();
            st.execute("update userregistrations set LOGINSTATUS='F', lastaccessedtime=sysdate where  loginstatus='T' and rtrim(userrole)='user'");
            st.execute("update logindetails set LOGOUTTIME=sysdate where logouttime is null");
            ResultSet rs = st.executeQuery("select sessiontime from isfusrlimit");
            String s = "30";
            while (rs.next()) {
                s = rs.getInt(1) + "";
            }
            hs.put("SessionTime", s);
        } catch (Exception ie) {
            logger.error("Exception", ie);
        } finally {
            release();
        }
        return hs;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
}
