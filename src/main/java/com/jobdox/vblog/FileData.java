package com.jobdox.vblog;

import org.eclipse.jgit.lib.ObjectId;

/**
 * Created by umesh on 12/20/14.
 */
public class FileData {
    private String name;
    private String content;
    private ObjectId versionObjectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ObjectId getVersionObjectId() {
        return versionObjectId;
    }

    public void setVersionObjectId(ObjectId versionObjectId) {
        this.versionObjectId = versionObjectId;
    }




}
