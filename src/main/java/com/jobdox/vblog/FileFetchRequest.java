/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.jobdox.vblog;

import org.eclipse.jgit.lib.ObjectId;

/**
 * Created by umesh on 12/20/14.
 */
public class FileFetchRequest {

    private String name="1";
    private String versionObjectId="";
    private int direction=0;

    public FileFetchRequest(String name, String versionObjectId, int direction) {
        this.name = name;
        this.versionObjectId = versionObjectId;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersionObjectId() {
        return versionObjectId;
    }

    public void setVersionObjectId(String versionObjectId) {
        this.versionObjectId = versionObjectId;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileFetchRequest)) return false;

        FileFetchRequest that = (FileFetchRequest) o;

        if (direction != that.direction) return false;
        if (!name.equals(that.name)) return false;
        if (!versionObjectId.equals(that.versionObjectId)) return false;

        return true;
    }


    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + versionObjectId.hashCode();
        result = 31 * result + direction;
        return result;
    }

    @Override
    public String toString() {
        return "FileFetchRequest{" +
                "name='" + name + '\'' +
                ", versionObjectId='" + versionObjectId + '\'' +
                ", direction=" + direction +
                '}';
    }

    public FileFetchRequest() {
        super();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }



}
