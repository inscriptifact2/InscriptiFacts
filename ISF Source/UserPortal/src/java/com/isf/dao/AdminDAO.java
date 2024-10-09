/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isf.dao;

import com.isf.beans.UserStats;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 *
 * @author raok1
 */
public class AdminDAO extends ISFBean {

    private static final String RESET_ONE_DAY = "update userRegistrations set loginstatus='F' where loginstatus='T' and lastaccessedtime <= sysdate -1 ";
    private static final String RESET_HALF_DAY = "update userRegistrations set loginstatus='F' where loginstatus='T' and (lastaccessedtime <= sysdate -.5 and lastaccessedtime >= sysdate -1) ";
    private static final String RESET_ALL = "update userRegistrations set loginstatus='F' where loginstatus='T' ";
    private static final String RESET_LIKE_USER = "update userRegistrations set loginstatus='F' where loginstatus='T' and userIdentity like '";
    private static final String QUERY_ONE_DAY = "select count(*) from userRegistrations where loginstatus='T' and lastaccessedtime <= sysdate -1 ";
    private static final String QUERY_HALF_DAY = "select count(*) from userRegistrations where loginstatus='T' and (lastaccessedtime <= sysdate -.5 and lastaccessedtime > sysdate -1) ";
    private static final String QUERY_ALL = "select count(*) from userRegistrations where loginstatus='T' ";
    
    private static final String QUERY_INST = "select count(*),b.column_value from userregistrations a, table(institution) b where b.column_value is not null group by b.column_value ";
    private static final String QUERY_COUNTRY = "select count(*),b.column_value from userregistrations a, table(usercountry) b where b.column_value is not null group by b.column_value ";
    private static final String QUERY_USERS = "select count(distinct(rtrim(firstname)||'-'||rtrim(lastname)||'-'||rtrim(inst.column_value)||'-'||rtrim(city.column_value)||'-'||rtrim(country.column_Value))) from userregistrations,"+
    " table(institution) inst, table(usercity) city, table(usercountry) country where inst.column_value is not null and city.column_value is not null and country.column_value is not null";


    private int getAllCount() {
        int i = 0;
        try {
            setupConnection();
            ResultSet rs = st.executeQuery(QUERY_ALL);
            rs.next();
            i = (rs.getInt(1));

        } catch (Exception exp) {
        } finally {
            release();
        }
        return i;
    }

    private int get24HrCount() {
        int i = 0;
        try {
            setupConnection();
            ResultSet rs = st.executeQuery(QUERY_ONE_DAY);
            rs.next();
            i = (rs.getInt(1));

        } catch (Exception exp) {
        } finally {
            release();
        }
        return i;
    }

    private int get12HrCount() {
        int i = 0;
        try {
            setupConnection();
            ResultSet rs = st.executeQuery(QUERY_HALF_DAY);
            rs.next();
            i = (rs.getInt(1));

        } catch (Exception exp) {
        } finally {
            release();
        }
        return i;
    }

    public int[] getUserStats() {
        int[] stats = new int[3];

        stats[0] = get24HrCount();
        stats[1] = get12HrCount();
        stats[2] = getAllCount();
        return stats;
    }

    public String resetOneDayOld() {
        setupConnection();
        String msg = "";
        try {
            int i = st.executeUpdate(RESET_ONE_DAY);
           
            msg = "SUCCESS";
        } catch (Exception exp) {
            msg = exp.getMessage();
        } finally {
            release();
            return msg;
        }
    }

    public String resetHalfDayOld() {
        setupConnection();
        String msg = "";
        try {
            int i = st.executeUpdate(RESET_HALF_DAY);
            System.out.println("Reset:Half:" + i);
            msg = "SUCCESS";
        } catch (Exception exp) {
            msg = exp.getMessage();
        } finally {
            release();
            return msg;
        }
    }

    public String resetAll() {
        setupConnection();
        String msg = "";
        try {
            int i = st.executeUpdate(RESET_ALL);
            System.out.println("Reset:All:" + i);
            msg = "SUCCESS";
        } catch (Exception exp) {
            msg = exp.getMessage();
        } finally {
            release();
            return msg;
        }
    }

    public String resetUser(String userid) {
        setupConnection();
        String msg = "";
        try {
            int i = st.executeUpdate(RESET_LIKE_USER + userid + "%'");
            System.out.println("Reset:User:" + i);
        } catch (Exception exp) {
            msg = exp.getMessage();
        } finally {
            release();
            return msg;
        }
    }
    
    
    public int getUniqueUsers(){
        int i = 0;
            try {
                setupConnection();
                ResultSet rs = st.executeQuery(QUERY_USERS);
                rs.next();
                i = (rs.getInt(1));

            } catch (Exception exp) {
            } finally {
                release();
            }
           return i;
    }
    
    public HashMap getInstUsers(){
        HashMap map = new HashMap();
        try {
                setupConnection();
                ResultSet rs = st.executeQuery(QUERY_INST);
                while(rs.next())
                    map.put(rs.getString(2), new Integer(rs.getInt(1)));

            } catch (Exception exp) {
            } finally {
                release();
            }
        return map;
    }
     public HashMap getCountryUsers(){
        HashMap map = new HashMap();
        try {
                setupConnection();
                ResultSet rs = st.executeQuery(QUERY_COUNTRY);
                while(rs.next())
                    map.put(rs.getString(2), new Integer(rs.getInt(1)));

            } catch (Exception exp) {
            } finally {
                release();
            }
        return map;
    }
}
