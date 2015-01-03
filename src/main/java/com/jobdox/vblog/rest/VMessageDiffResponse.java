/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.jobdox.vblog.rest;

/**
 * Created by umesh on 12/24/14.
 */



/**
 * Every time a new version of a message is saved to the git repository a corresponding entry is made to database
 */
public class VMessageDiffResponse {

    private Integer userId;
    private Integer messageId;
    private Integer versionNumber;
    private Integer maxVersionNumber;
    private String newMessage;
    private String oldMessage;
    private boolean success;
    private String reason;

    public VMessageDiffResponse() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Integer getMaxVersionNumber() {
        return maxVersionNumber;
    }

    public void setMaxVersionNumber(Integer maxVersionNumber) {
        this.maxVersionNumber = maxVersionNumber;
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    public String getOldMessage() {
        return oldMessage;
    }

    public void setOldMessage(String oldMessage) {
        this.oldMessage = oldMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "VMessageDiffResponse{" +
                "userId=" + userId +
                ", messageId=" + messageId +
                ", versionNumber=" + versionNumber +
                ", maxVersionNumber=" + maxVersionNumber +
                ", newMessage='" + newMessage + '\'' +
                ", oldMessage='" + oldMessage + '\'' +
                ", success='" + success + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }


}
