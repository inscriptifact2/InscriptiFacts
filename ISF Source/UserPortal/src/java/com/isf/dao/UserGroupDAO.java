/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isf.dao;

import com.isf.beans.UserGroup;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author raok1
 */
public class UserGroupDAO extends ISFBean {
    private static UserGroupDAO ugdao = new UserGroupDAO();
    private UserGroupDAO(){
        
    }
    
    public static UserGroupDAO getInstance()
    {
        return ugdao;
    }
    
    public void saveUserGroup(UserGroup ug) throws SQLException
    {
        setupConnection();
        //save the usergroup
        release();
    }
    
   
    public ArrayList fetchUserGroupsForUser(String userid)
    {
        ArrayList groups = new ArrayList();
        setupConnection();
        release();
        return groups;
    }
     public ArrayList fetchAllUserGroups()
    {
        ArrayList groups = new ArrayList();
        setupConnection();
        release();
        return groups;
    }
    
    public String[] getRestrictedTextsForUser(String userid)
    {
        setupConnection();
        release();
        return new String[]{""};
    }
    
}
