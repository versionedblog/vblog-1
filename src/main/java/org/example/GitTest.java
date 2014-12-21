package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class GitTest
{

    private static void writeFile() throws Exception
    {
        
        FileUtils.writeStringToFile(new File("/tmp/testRemotePath/test/test1.txt"), "The new line " + new java.util.Date()
                + "\n", true);
        
        File gitDir = new File("/tmp/testRemotePath/test/.git");
        Repository localRepo = new FileRepository(gitDir);
        Git git = new Git(localRepo);
        git.add().addFilepattern("test1.txt").call();
        git.commit().setMessage(new Date().toString() + "Another random comment").call();
        
        // credentials
        CredentialsProvider cp = new UsernamePasswordCredentialsProvider("umeshvk", "harry123");
        PushCommand pc = git.push();
        pc.setCredentialsProvider(cp)
                .setForce(true)
                .setPushAll().call();
        
    }

    private static void getRemote() throws Exception
    {
        String localPath = "/tmp/testRemotePath";
        String remotePath = "git@github.com:umeshvk/test.git";
        Git.cloneRepository().setURI(remotePath).setDirectory(new File(localPath)).call();
    }

    private static void createRepository()
    {
        try
        {
            File gitDir = new File("/tmp/to/newrepo/.git");
            Repository repo = new FileRepository(gitDir);
            repo.create();
            /*
        */
        } catch (IllegalStateException ise)
        {
            // The repository already exists!
        } catch (IOException ioe)
        {
            // Failed to create the repository!
        }
    }

    public static void main(String[] args) throws Exception
    {
        // createRepository();
        //getRemote();
        writeFile();
    }
    /*
  */
}
