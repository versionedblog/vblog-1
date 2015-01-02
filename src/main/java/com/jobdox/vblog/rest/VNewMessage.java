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
import java.util.UUID;

/**
 * Every time a new version of a message is saved to the git repository a corresponding entry is made to database
 */
public class VNewMessage {
    private String author;
    private String uuid= UUID.randomUUID().toString();

    public VNewMessage() {
    }

    public VNewMessage(String author) {
        this.author = author;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "VNewMessage{" +
                "uuid='" + uuid + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
