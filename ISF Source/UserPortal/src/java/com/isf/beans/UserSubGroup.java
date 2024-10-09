/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isf.beans;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author raok1
 */
public class UserSubGroup {
    private String userSubGroup;
    private String userGroup;
    private ArrayList restrictedTexts;
    private String createdBy;
    private Date createdOn;
    private String status;
    private String recordAction;

    /**
     * @return the userSubGroup
     */
    public String getUserSubGroup() {
        return userSubGroup;
    }

    /**
     * @param userSubGroup the userSubGroup to set
     */
    public void setUserSubGroup(String userSubGroup) {
        this.userSubGroup = userSubGroup;
    }

    /**
     * @return the restrictedTexts
     */
    public ArrayList getRestrictedTexts() {
        return restrictedTexts;
    }

    /**
     * @param restrictedTexts the restrictedTexts to set
     */
    public void setRestrictedTexts(ArrayList restrictedTexts) {
        this.restrictedTexts = restrictedTexts;
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
