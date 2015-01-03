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

import java.util.Date;

/**
 * Every time a new version of a message is saved to the git repository a corresponding entry is made to database
 */
public class VMessageEnvelope {
    private VMessage vmessage; //From database
    private Integer messageId; //from database
    private Integer userId;
    private Integer previousMessageId;
    private Integer nextMessageId;
    private Integer previousVersionId;
    private Integer nextVersionId;

    public VMessageEnvelope() {
    }

    public VMessageEnvelope(VMessage vmessage, Integer messageId, Integer userId, Integer previousMessageId, Integer nextMessageId) {
        this.vmessage = vmessage;
        this.messageId = messageId;
        this.userId = userId;
        this.previousMessageId = previousMessageId;
        this.nextMessageId = nextMessageId;
    }

    public VMessage getVmessage() {
        return vmessage;
    }

    public void setVmessage(VMessage vmessage) {
        this.vmessage = vmessage;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPreviousMessageId() {
        return previousMessageId;
    }

    public void setPreviousMessageId(Integer previousMessageId) {
        this.previousMessageId = previousMessageId;
    }

    public Integer getNextMessageId() {
        return nextMessageId;
    }

    public void setNextMessageId(Integer nextMessageId) {
        this.nextMessageId = nextMessageId;
    }

    public Integer getPreviousVersionId() {
        return previousVersionId;
    }

    public void setPreviousVersionId(Integer previousVersionId) {
        this.previousVersionId = previousVersionId;
    }

    public Integer getNextVersionId() {
        return nextVersionId;
    }

    public void setNextVersionId(Integer nextVersionId) {
        this.nextVersionId = nextVersionId;
    }

    @Override
    public String toString() {
        return "VMessageEnvelope{" +
                "vmessage=" + vmessage +
                ", messageId=" + messageId +
                ", userId=" + userId +
                ", previousMessageId=" + previousMessageId +
                ", nextMessageId=" + nextMessageId +
                ", previousVersionId=" + previousVersionId +
                ", nextVersionId=" + nextVersionId +
                '}';
    }
}
