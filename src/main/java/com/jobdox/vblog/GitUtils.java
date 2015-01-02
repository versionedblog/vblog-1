package com.jobdox.vblog;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revplot.PlotCommitList;
import org.eclipse.jgit.revplot.PlotLane;
import org.eclipse.jgit.revplot.PlotWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by umesh on 12/20/14.
 */
public class GitUtils {

    private static Properties getVblogProperties() {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("Vblog.properties");
        try {
            prop.load(stream);
        } catch (IOException e) {
            return prop;
        }

        return prop;

    }

    private static Properties vblogProperties = getVblogProperties();
    private static Repository repository = initRepository();


    private static Repository initRepository() {
        File gitDir = new File(vblogProperties.getProperty("RepositoryPath") + "/.git");
        Repository repository = null;
        try {
            repository = new FileRepository(gitDir);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return repository;
    }


    public static PlotCommitList getPlotCommits(Repository repository, String path){
        System.out.println(path);
        PlotCommitList<PlotLane> plotCommitList = new PlotCommitList<>();
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

    public static RevTree getTree(Repository repository, ObjectId  commitId)  {

        // a RevWalk allows to walk over commits based on some filtering
        RevWalk revWalk = new RevWalk(repository);
        RevCommit commit = null;
        try {
            commit = revWalk.parseCommit(commitId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //RevCommit[] commits = commit.getParents();

        //System.out.println("Time of commit (seconds since epoch): " + commit.getCommitTime());

        // and using commit's tree find the path
        RevTree tree = commit.getTree();
        //System.out.println("Having tree: " + tree);
        return tree;
    }

    public static Repository getRepository() {
        return repository;
    }


    public static String getFileContent(Repository repository, RevTree tree, String fileName) {
        // now try to find a specific file
        TreeWalk treeWalk = new TreeWalk(repository);
        try {
            treeWalk.addTree(tree);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        treeWalk.setRecursive(false);
        treeWalk.setFilter(PathFilter.create(fileName));
        try {
            if (!treeWalk.next()) {
                throw new IllegalStateException("Did not find expected file");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // FileMode specifies the type of file, FileMode.REGULAR_FILE for normal file,
        // FileMode.EXECUTABLE_FILE for executable bit set
        FileMode fileMode = treeWalk.getFileMode(0);
        ObjectLoader loader = null;
        try {
            loader = repository.open(treeWalk.getObjectId(0));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        byte[] bytes = loader.getBytes();
        String str = new String(bytes);

        return str;
    }

    public static boolean updateFile(String fileName, String fileContent, String comment) {

        File file = new File(vblogProperties.getProperty("RepositoryPath") + "/" + fileName);
        String userName = vblogProperties.getProperty("RepositoryUserName");
        String password = vblogProperties.getProperty("RepositoryPassword");

        try
        {
            FileUtils.writeStringToFile(file, fileContent);
            Repository repository = getRepository();
            Git git = new Git(repository);
            git.add().addFilepattern(fileName).call();
            git.commit().setMessage(comment).call();
            // credentials
            CredentialsProvider cp = new UsernamePasswordCredentialsProvider(userName, password);
            PushCommand pc = git.push();
            pc.setCredentialsProvider(cp).setForce(true).setPushAll().call();
        } catch (GitAPIException e)
        {
            e.printStackTrace();
            return false;
        }   catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


}
