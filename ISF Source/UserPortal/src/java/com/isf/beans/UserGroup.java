/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isf.beans;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author raok1
 */
public class UserGroup {
    private String userGroup;
    private ArrayList<UserSubGroup> userSubGroups;
    private String createdBy;
    private Date createdOn;
    private String groupAdmin;
    private String status;
    private String recordAction;

    /**
     * @return the userGroup
     */
    public String getUserGroup() {
        return userGroup;
    }

    /**
     * @param userGroup the userGroup to set
     */
    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * @return the userSubGroups
     */
    public ArrayList<UserSubGroup> getUserSubGroups() {
        return userSubGroups;
    }

    /**
     * @param userSubGroups the userSubGroups to set
     */
    public void setUserSubGroups(ArrayList<UserSubGroup> userSubGroups) {
        this.userSubGroups = userSubGroups;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the createdOn
     */
    public Date getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn the createdOn to set
     */
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * @return the groupAdmin
     */
    public String getGroupAdmin() {
        return groupAdmin;
    }

    /**
     * @param groupAdmin the groupAdmin to set
     */
    public void setGroupAdmin(String groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the recordAction
     */
    public String getRecordAction() {
        return recordAction;
    }

    /**
     * @param recordAction the recordAction to set
     */
    public void setRecordAction(String recordAction) {
        this.recordAction = recordAction;
    }
}
