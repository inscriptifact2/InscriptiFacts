/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isf.dao;

import javax.naming.*;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 *
 * @author raok1
 */
public class ISFBean {

    Connection con;
    Statement st;
    long timer;
    static DataSource datasource;


    static {
        try {
            Context initContext = new InitialContext();
            //Context envContext = (Context) initContext.lookup("java:/comp/env");
            datasource = (DataSource) initContext.lookup("inscriptDs");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected void setupConnection() {
        try {
            con = datasource.getConnection();
            st = (java.sql.Statement) con.createStatement();
        } catch (Exception exception) {
            System.out.println("Error:" + exception);
        }
    }

    protected void release(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            System.out.println("connection error:" + e);
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
            System.out.println("connection & statement error:" + e);
        }
    }
}
