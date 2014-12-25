package com.jobdox.vblog.dataobj;

/**
 * Created by umesh on 12/24/14.
 */

import java.util.Date;

/**
 * Every time a new version of a message is saved to the git repository a corresponding entry is made to database
 */
public class Message {
    private Integer id;
    private Integer userId;
    private Integer messageId;
    private Integer versionId;
    private String title;
    private String author;
    private Date created;

    public Message() {
    }

    public Message(Integer id, Integer userId, Integer messageId, Integer versionId, String title, String author, Date created) {
        this.id = id;
        this.userId = userId;
        this.messageId = messageId;
        this.versionId = versionId;
        this.title = title;
        this.author = author;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
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
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", userId=" + userId +
                ", messageId=" + messageId +
                ", versionId=" + versionId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", created=" + created +
                '}';
    }
}
