package com.jobdox.vblog;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.jobdox.vblog.GitUtils.FileData;
import org.eclipse.jgit.revplot.PlotCommit;
import org.eclipse.jgit.revplot.PlotCommitList;
import org.eclipse.jgit.revwalk.RevTree;

/**
 * Created by umesh on 12/20/14.
 */
public class GitService {

    public static void initFile(String fileName) {
    }

    public static List<ObjectId> getObjectIds(String fileName) {
        Repository repository = GitUtils.getRepository();
        PlotCommitList pcl = GitUtils.getPlotCommits(repository, fileName);
        int totalCount = pcl.size();
        Iterator<PlotCommit> plotCommitIterator = pcl.iterator();
        List<ObjectId> objectIds = new ArrayList<ObjectId>();
        while(plotCommitIterator.hasNext()) {
            PlotCommit obj = plotCommitIterator.next();
            ObjectId  objectId = obj.getId();
            objectIds.add(objectId);
        }
        return objectIds;
    }

    public static FileData getFileContent(Repository repository, String fileName, ObjectId fileVersionObjectId) {

        String content = null;
        RevTree tree = GitUtils.getTree(repository, fileVersionObjectId);
        if(tree != null) {
            FileData fileData = new FileData();
            content = GitUtils.getFileContent(repository, tree);
            fileData.setFileContent(content);
            fileData.setFileVersionObjectId(fileVersionObjectId);
            return fileData;
        }

        return null;
    }

    public static FileData getFileContentForPreviousVersion(Repository repository, String fileName, ObjectId currentFileVersionId) {
        return null;
    }

    public static FileData getFileContentForNextVersion(Repository repository, String fileName, ObjectId currentFileVersionId) {
        return null;
    }

    public static String getFileComment(Repository repository, String fileName, ObjectId fileVersionId) {
        return null;
    }

    public static String getFileCommentForPreviousVersion(Repository repository, String fileName, ObjectId currentFileVersionId) {
        return null;
    }

    public static String getFileCommentForNextVersion(Repository repository, String fileName, ObjectId currentFileVersionId) {
        return null;
    }

    public static boolean updateFile(String fileName, String fileContent, String comment) {
        return GitUtils.updateFile(fileName, fileContent, comment);
    }

}
