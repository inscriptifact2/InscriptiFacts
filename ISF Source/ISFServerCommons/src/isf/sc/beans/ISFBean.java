/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.sc.beans;

import isf.common.utils.LogManager;
import isf.sc.utils.ISFServiceLocator;
import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author raok1
 */
public class ISFBean {

    Connection con;
    Statement st;
    long timer;
    protected Logger logger = LogManager.getLogger(isf.sc.beans.ISFBean.class);
    static DataSource datasource;
    

    static {
        datasource = (DataSource) ISFServiceLocator.getInstance().getLocalDataSource("inscriptDs");
    }

    protected void setupConnection() {
        try {

            con = datasource.getConnection();
            st = (java.sql.Statement) con.createStatement();
        } catch (Exception exception) {
            logger.error("Error:", exception);
        }
    }

    protected void release(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            logger.error("connection error", e);
        }
    }

    protected void release() {
        try {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }

        } catch (Exception e) {
            logger.error("connection & statement error", e);
        }
    }
}
