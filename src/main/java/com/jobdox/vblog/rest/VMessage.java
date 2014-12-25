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
public class VMessage {
    private String objectIdStr; //From database
    private Integer versionNumber; //From database
    private Integer messageId; //from database
    private String title;
    private String author;
    private Date created;

    public VMessage() {
    }

    public VMessage(String objectIdStr, Integer messageId, Integer versionNumber,  String title, String author, Date created) {
        this.objectIdStr = objectIdStr;
        this.messageId = messageId;
        this.versionNumber = versionNumber;
        this.title = title;
        this.author = author;
        this.created = created;
    }

    public String getObjectIdStr() {
        return objectIdStr;
    }

    public void setObjectIdStr(String objectIdStr) {
        this.objectIdStr = objectIdStr;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VMessage)) return false;

        VMessage vMessage = (VMessage) o;

        if (!author.equals(vMessage.author)) return false;
        if (!created.equals(vMessage.created)) return false;
        if (!messageId.equals(vMessage.messageId)) return false;
        if (!objectIdStr.equals(vMessage.objectIdStr)) return false;
        if (!title.equals(vMessage.title)) return false;
        if (!versionNumber.equals(vMessage.versionNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = objectIdStr.hashCode();
        result = 31 * result + versionNumber.hashCode();
        result = 31 * result + messageId.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + created.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "VMessage{" +
                "objectIdStr='" + objectIdStr + '\'' +
                ", versionNumber=" + versionNumber +
                ", messageId=" + messageId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", created=" + created +
                '}';
    }
}
