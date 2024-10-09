package isf.sc.response;

import isf.common.jro.JavaRequestObject;
import isf.common.utils.LogManager;
import isf.common.xml.Clause;
import isf.common.xml.Query;
import isf.common.xml.QueryCondition;
import isf.sc.beans.LoginBean;
import isf.sc.beans.ValidateUserBean;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

public class AuthResponseObject
        implements Serializable {

    private JavaRequestObject jro;
    private String str[];
    private Logger logger = LogManager.getLogger(isf.sc.response.AuthResponseObject.class);
    LoginBean login = null;
    // ValidateUserLocal validateUser = null;

    public AuthResponseObject()
            throws Exception {
        str = new String[3];
        try {
            login = new LoginBean();

        } catch (Exception exception) {
            throw exception;
        }
    }

    public void setRequestObject(JavaRequestObject javarequestobject) {
        jro = javarequestobject;
    }

    public Object getResponse() {
        extractValues();
        String s = "";
        String session = "";
        try {
            final String type = jro.getQuery().getType();

            logger.debug("Type in AuthResponseObject:"+type);
            if (type.equals("Authentication") || type.equalsIgnoreCase("UserAcceptance")) {

                String msg = "";
                try {
                    if (type.equalsIgnoreCase("UserAcceptance")) {
                        msg = login.updateAcceptance(str[0], str[1]);
                        logger.debug("UA:"+msg);
                    } else {
                        msg = login.isValidUser(str[0], str[1]);
                        logger.debug("Login:"+msg);
                    }
                   // System.out.println("MSG after Authentication:"+msg+"::");
                    if (msg.equalsIgnoreCase("Success")) {
                        // logger.debug("Authenticated");
                         ValidateUserBean validateUser = new ValidateUserBean();
                        session = validateUser.getValidSession(str[0]);
                        // ISFSessionManager.getInstance().setNewSession(session);
                        //  logger.debug("session valid?:"+session);
                        Vector vector = login.getMainSearchData();
                        session = ("".equals(session)) ? "NEW" : session;
                        s = (String) vector.elementAt(0);
                        logger.debug("Last:"+s);
                    } else {
                        s = "<ERRMSG MSG='"+msg+"'/>";
                        logger.debug("Error Message:" + s);
                    }
                    // logger.debug("Second session valid?:"+session);
                }
                catch(Exception exp) {
                    s = "<ERRMSG MSG='" + msg + "'/>";
                    logger.debug("Error Message:" + s);
                }
                // logger.debug("Finally:"+s);

            }
            /*
             * else if(jro.getQuery().getType().equals("Authentication") ) { s =
             * "<ERRMSG MSG='UserId or Password is not correct.'/>"; }
             */
            if (type.equals("ChangePassword")) {
                if (login.changePassword(str[0], str[1], str[2])) {
                    s = "<SUCCESSMSG MSG='Password Changed'/>";
                } else {
                    s = "<ERRMSG MSG='UserId or Password is not correct.'/>";
                }
            }
            if (type.equals("ForgotPassword")) {
                if (login.forgotPassword(str[0], str[1])) {
                    s = "<SUCCESSMSG MSG='Forgot Password'/>";
                } else {
                    s = "<ERRMSG MSG='UserId is not correct.'/>";
                }
            }
            if (type.equals("Login")) {
                String msg = login.isValidUser(str[0], str[1]);

                if (!msg.equalsIgnoreCase("Success")) {
                    s = "<ERRMSG MSG='" + msg + "'/>";
                } else {
                    // InitialContext initialcontext = new InitialContext();
                    // ValidateUserHome slh = (ValidateUserHome) ISFServiceLocator.getInstance().getLocalHome("isf.ejb.beans.ValidateUserBean/local");
                    ValidateUserBean validateUser = new ValidateUserBean();
                    session = validateUser.getValidSession(str[0]);
                    session = ("".equals(session)) ? "NEW" : session;
                    // ISFSessionManager.getInstance().setNewSession(session);

                }

            }
            if (type.equals("Logout")) {
                login.logout(str[0]);
                s = "<SUCCESSMSG MSG='LoggedOut'/>";
                // ISFSessionManager.getInstance().kill(session);

            }
        } catch (SQLException sqlexception) {
            logger.error("here in sqlexception of auth", sqlexception);
            s = "<ERRMSG MSG='" + sqlexception.toString() + "'/>";
        } catch (Exception exception) {
            logger.error("here in exception of auth", exception);
            s = "<ERRMSG MSG='" + exception.getMessage() + "'/>";
        } finally {
            logger.debug("Session:" + session);
            s = "<RESPONSE TYPE='initialisationdata' SESSIONID='" + session + "'>" + s + "</RESPONSE>";

        }
        logger.debug("Finally from the method:"+s);
        return s;
    }

    private void extractValues() {
        Query query = jro.getQuery();
        Vector vector = query.getQueryConditions();
        int j = 0;
        for (int k = vector.size(); j < k; j++) {
            Clause clause = (Clause) vector.elementAt(j);
            Vector vector1 = clause.getQueryConditions();
            int l = 0;
            for (int i1 = vector1.size(); l < i1; l++) {
                QueryCondition querycondition = (QueryCondition) vector1.elementAt(l);
                str[j] = querycondition.getValue();
            }

        }

    }
}
