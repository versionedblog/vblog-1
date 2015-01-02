/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.jobdox.vblog.rest;

/**
 * Created by umesh on 12/24/14.
 */

import java.util.UUID;

/**
 * Every time a new version of a message is saved to the git repository a corresponding entry is made to database
 */
public class VNewMessageResponse {
    private Integer messageId;
    private Integer versionId;
    private boolean status;
    private String reason;

    public VNewMessageResponse() {
    }

    public VNewMessageResponse(Integer messageId, Integer versionId) {
        this.messageId = messageId;
        this.versionId = versionId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "VNewMessageResponse{" +
                "messageId=" + messageId +
                ", versionId=" + versionId +
                ", status=" + status +
                ", reason=" + reason +
                '}';
    }
}
