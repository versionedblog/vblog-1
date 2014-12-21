package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

public class HelloServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        printAllVersions();
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello Servlet</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());

    }
    
    private void printAllVersions() throws IOException { 
        File gitDir = new File("/tmp/testRemotePath/test/.git");
        Repository repository = new FileRepository(gitDir);
        ObjectId lastCommitId = repository.resolve(Constants.HEAD);
        System.out.println("lastCommitId=" + lastCommitId.toString());
        ObjectLoader loader = repository.open(lastCommitId);
        // and then one can the loader to read the file
        loader.copyTo(System.out);
        
        // a RevWalk allows to walk over commits based on some filtering that is defined
        RevWalk revWalk = new RevWalk(repository);
        RevCommit commit = revWalk.parseCommit(lastCommitId);
        // and using commit's tree find the path
        RevTree tree = commit.getTree();
        System.out.println("Having tree: " + tree);

        // now try to find a specific file
        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException
    {
        String blogData = request.getParameter("blogData");
        FileUtils.writeStringToFile(new File("/tmp/testRemotePath/test/test1.txt"), blogData + " : "
                + new java.util.Date() + "\n", true);
        try
        {
            File gitDir = new File("/tmp/testRemotePath/test/.git");
            Repository localRepo = new FileRepository(gitDir);
            Git git = new Git(localRepo);
            git.add().addFilepattern("test1.txt").call();
            git.commit().setMessage(new Date().toString() + ": " + blogData).call();
            // credentials
            CredentialsProvider cp = new UsernamePasswordCredentialsProvider("umeshvk", "harry123");
            PushCommand pc = git.push();
            pc.setCredentialsProvider(cp).setForce(true).setPushAll().call();
        } catch (GitAPIException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
