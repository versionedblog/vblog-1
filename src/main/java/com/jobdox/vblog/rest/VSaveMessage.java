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

import javax.ws.rs.FormParam;
import java.util.Date;
import java.util.UUID;

/**
 * Every time a new version of a message is saved to the git repository a corresponding entry is made to database
 */
public class VSaveMessage {
    private Integer userId;
    private String title;
    private String author;
    private String content;
    private String uuid;
    private Integer messageId;
    private Integer lastVersionId;

    public VSaveMessage() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getLastVersionId() {
        return lastVersionId;
    }

    public void setLastVersionId(Integer lastVersionId) {
        this.lastVersionId = lastVersionId;
    }

    @Override
    public String toString() {
        return "VSaveMessage{" +
                "userId=" + userId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", uuid='" + uuid + '\'' +
                ", messageId=" + messageId +
                ", lastVersionId=" + lastVersionId +
                '}';
    }
}
