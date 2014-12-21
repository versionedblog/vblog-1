package org.example;

import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import java.io.*;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revplot.*;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

/**
 * Created by umesh on 12/20/14.
 */
public class TW {
    public static void main(String[] args) throws Exception
    {
        File gitDir = new File("/tmp/testRemotePath/test/.git");
        Repository repository = new FileRepository(gitDir);
        PlotCommitList pcl = getPlotCommits(repository, "test1.txt");
        int totalCount = pcl.size();
        Iterator<PlotCommit> plotCommitIterator = pcl.iterator();
        while(plotCommitIterator.hasNext()) {
            PlotCommit obj = plotCommitIterator.next();
            ObjectId  objectId = obj.getId();

            //System.out.println("objectId=" + ((AnyObjectId)objectId));
            String comment = getCommitLogComment(repository, objectId);
            System.out.println(comment);

            RevTree tree = getTree(repository, objectId);
            printFile(repository, tree);
        }

        System.exit(1);
        walkRev(repository);

        RevTree tree = getTree(repository);



        printFile(repository, tree);

        //printDirectory(repository, tree);

        // there is also FileMode.SYMLINK for symbolic links, but this is not handled here yet

        repository.close();
    }


    private static RevTree getTree(Repository repository, ObjectId  commitId) throws AmbiguousObjectException, IncorrectObjectTypeException,
            IOException, MissingObjectException {

        // a RevWalk allows to walk over commits based on some filtering
        RevWalk revWalk = new RevWalk(repository);
        RevCommit commit = revWalk.parseCommit(commitId);

        RevCommit[] commits = commit.getParents();

        System.out.println("Time of commit (seconds since epoch): " + commit.getCommitTime());

        // and using commit's tree find the path
        RevTree tree = commit.getTree();
        System.out.println("Having tree: " + tree);
        return tree;
    }

    private static void walkRev(Repository repository) throws IOException {

        Ref head = repository.getRef("refs/heads/master");

        // a RevWalk allows to walk over commits based on some filtering that is defined
        RevWalk walk = new RevWalk(repository);


        RevCommit commit = walk.parseCommit(head.getObjectId());

        String str = commit.getName();
        System.out.println("Start-Commit: " + commit);

        System.out.println("Walking all commits starting at HEAD");
        walk.markStart(commit);
        int count = 0;
        for (RevCommit rev : walk) {
            System.out.println("Commit: " + rev);
            count++;
        }
        System.out.println(count);

        walk.dispose();

        repository.close();



    }

    private static PlotCommitList getPlotCommits(Repository repository, String path){
        System.out.println(path);
        PlotCommitList<PlotLane> plotCommitList = new PlotCommitList<PlotLane>();
        PlotWalk revWalk = new PlotWalk(repository);
        try {

            ObjectId rootId = repository.resolve("HEAD");
            if (rootId != null) {
                RevCommit root = revWalk.parseCommit(rootId);
                revWalk.markStart(root);
                //revWalk.setTreeFilter(PathFilter.create(path));
                revWalk.setTreeFilter(
                        AndTreeFilter.create(PathFilter.create(path), TreeFilter.ANY_DIFF));
                plotCommitList.source(revWalk);
                plotCommitList.fillTo(Integer.MAX_VALUE);
                return plotCommitList;
            }

        } catch (AmbiguousObjectException ex) {
            System.out.println(ex);
            //Logger.getLogger(GitRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println(ex);
            //Logger.getLogger(GitRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plotCommitList;
    }

    private static RevTree getTree(Repository repository) throws AmbiguousObjectException, IncorrectObjectTypeException,
            IOException, MissingObjectException {
        ObjectId lastCommitId = repository.resolve(Constants.HEAD);

        // a RevWalk allows to walk over commits based on some filtering
        RevWalk revWalk = new RevWalk(repository);
        RevCommit commit = revWalk.parseCommit(lastCommitId);

        RevCommit[] commits = commit.getParents();

        System.out.println("Time of commit (seconds since epoch): " + commit.getCommitTime());

        // and using commit's tree find the path
        RevTree tree = commit.getTree();
        System.out.println("Having tree: " + tree);
        return tree;
    }

    private static void printFile(Repository repository, RevTree tree) throws MissingObjectException,
            IncorrectObjectTypeException, CorruptObjectException, IOException {
        // now try to find a specific file
        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(false);
        treeWalk.setFilter(PathFilter.create("test1.txt"));
        if (!treeWalk.next()) {
            throw new IllegalStateException("Did not find expected file 'README.md'");
        }

        // FileMode specifies the type of file, FileMode.REGULAR_FILE for normal file, FileMode.EXECUTABLE_FILE for executable bit
// set
        FileMode fileMode = treeWalk.getFileMode(0);
        ObjectLoader loader = repository.open(treeWalk.getObjectId(0));
        /*
        System.out.println("README.md: " + getFileMode(fileMode) + ", type: " + fileMode.getObjectType() + ", mode: " + fileMode +
                " size: " + loader.getSize());
        */
        byte[] bytes = loader.getBytes();
        String str = new String(bytes);
        System.out.println(str);

        ObjectId objectId = treeWalk.getObjectId(0);
        System.out.println("res:" + objectId);
    }

    private static void printDirectory(Repository repository, RevTree tree) throws MissingObjectException,
            IncorrectObjectTypeException, CorruptObjectException, IOException {
        // look at directory, this has FileMode.TREE
        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(false);
        treeWalk.setFilter(PathFilter.create("src"));
        if (!treeWalk.next()) {
            throw new IllegalStateException("Did not find expected file 'README.md'");
        }

        // FileMode now indicates that this is a directory, i.e. FileMode.TREE.equals(fileMode) holds true
        FileMode fileMode = treeWalk.getFileMode(0);
        System.out.println("src: " + getFileMode(fileMode) + ", type: " + fileMode.getObjectType() + ", mode: " + fileMode);
    }

    private static String getFileMode(FileMode fileMode) {
        if (fileMode.equals(FileMode.EXECUTABLE_FILE)) {
            return "Executable File";
        } else if (fileMode.equals(FileMode.REGULAR_FILE)) {
            return "Normal File";
        } else if (fileMode.equals(FileMode.TREE)) {
            return "Directory";
        } else if (fileMode.equals(FileMode.SYMLINK)) {
            return "Symlink";
        } else {
            // there are a few others, see FileMode javadoc for details
            throw new IllegalArgumentException("Unknown type of file encountered: " + fileMode);
        }
    }


    private static void printAllVersions() throws IOException {
        File gitDir = new File("/tmp/testRemotePath/test/.git");
        Repository repository = new FileRepository(gitDir);
        ObjectId lastCommitId = repository.resolve(Constants.HEAD);
        System.out.println("lastCommitId=" + lastCommitId.toString());
        ObjectLoader loader = repository.open(lastCommitId);

        System.out.println("loader reads file below:");
                // and then one can the loader to read the file
        loader.copyTo(System.out);

        System.out.println("");
        System.out.println("file loaded");


        // a RevWalk allows to walk over commits based on some filtering that is defined
        RevWalk revWalk = new RevWalk(repository);
        RevCommit commit = revWalk.parseCommit(lastCommitId);




        // and using commit's tree find the path
        RevTree tree = commit.getTree();
        System.out.println("Having tree: " + tree);

        // now try to find a specific file
        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(false);
        treeWalk.setFilter(PathFilter.create("test1.txt"));
        int count = 0;

        ObjectId objectId = treeWalk.getObjectId(1);
        loader = repository.open(objectId);

        // and then one can the loader to read the file
        loader.copyTo(System.out);

        while (!treeWalk.next()) {
            objectId = treeWalk.getObjectId(0);
            loader = repository.open(objectId);

            // and then one can the loader to read the file
            loader.copyTo(System.out);

            System.out.println("====xxxxx====");
            count++;
        }
        System.out.println("Found Version Count=" + count);


        revWalk.dispose();

        repository.close();
    }


    public static String getFileContent2(Repository repository, ObjectId  objectId) throws IOException {
        ObjectLoader loader = repository.open(objectId);
        byte[] bytes = loader.getBytes();
        String str = new String(bytes);
        System.out.println(str);
        int nlCount = 0;
        for(int i=0;i<str.length();i++) {
            if(str.charAt(i) == '\n') {
                nlCount++;
                System.out.println(str.substring(i+1));
            }
            if(nlCount == 5) {
                return str.substring(i+1);
            }
        }

        return null;
    }

    public static String getCommitLogComment(Repository repository, ObjectId objectId) throws IOException {
        String lineSeparator = System.getProperty("line.separator");
        ObjectLoader loader = repository.open(objectId);
        byte[] bytes = loader.getBytes();
        String str = new String(bytes);
        //System.out.println(str);
        int lastNewLineIndex = -1;
        int currentNewLineIndex = -1;
        while((lastNewLineIndex+1) != currentNewLineIndex) {
            lastNewLineIndex = currentNewLineIndex;
            currentNewLineIndex = str.indexOf(lineSeparator, lastNewLineIndex+1);
        }
        return str.substring(currentNewLineIndex+1);

    }
}
