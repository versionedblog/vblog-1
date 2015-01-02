/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.jobdox.vblog.db;
import com.jobdox.vblog.FileData;
import com.jobdox.vblog.GitService;
import com.jobdox.vblog.dataobj.Message;
import com.jobdox.vblog.rest.VNewMessageResponse;
import com.jobdox.vblog.util.HibernateUtil;
import org.eclipse.jgit.lib.ObjectId;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by umesh on 12/24/14.
 */
public class MessageAPI {

    public static void main( String[] args )
    {
//"test the content of file for message:" + messageId + ", Random Int=" + (int)(Math.random() * 10000)
        saveNewMessage(1, "title for message", "test the content of file for message:"  + ", Random Int=" + (int)(Math.random() * 10000), "Max Mule", "uuid");
        //List results = getLatestUserMessages(1);
        //addMessage();
        System.exit(1);
    }

    public static VNewMessageResponse saveNewMessage(Integer userId, String title, String author, String messageContent, String uuid) {

        VNewMessageResponse vNewMessageResponse= new VNewMessageResponse();
        vNewMessageResponse.setStatus(false);
        Integer messageId = MessageAPI.getNextMessageId(userId);
        addMessage(userId,  messageId, 1, title, author, "-1", uuid);
        GitService.updateFile(messageId.toString(), messageContent, "Comment for message id: " + messageId);
        List<ObjectId> objectIds =  GitService.getObjectIds(messageId.toString());
        //Repository repository, String fileName, ObjectId versionObjectId

        if(objectIds.size() > 0) {
            String objectIdStr = objectIds.get(0).name();
            System.out.println("objectIdStr=" + objectIdStr);
            //FileData fileData1 = GitService.getFileContent(messageId.toString(), objectIds.get(0));
            //FileData fileData2 = GitService.getFileContent(messageId.toString(), ObjectId.fromString(objectIdStr));
            Message message = getLatestUserMessage(userId,  messageId);
            message.setObjectId(objectIdStr);
            updateMessage(message);
            vNewMessageResponse.setMessageId(message.getMessageId());
            vNewMessageResponse.setVersionId(message.getVersionId());
            vNewMessageResponse.setStatus(true);
            int ii =0;
        }

        return vNewMessageResponse;
    }

    public static void addMessage(Integer userId, Integer messageId, Integer versionId, String title, String author, String objectId, String uuid) {
        Session session = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            /*
            Integer id, Integer userId, Integer messageId, Integer versionId, String title,
                   String author, Date created, String objectId
             */
            Message message = new Message(-1, userId, messageId, versionId, title, author, new Date(), objectId, uuid);
            session.save(message);
            session.getTransaction().commit();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    public static void updateMessage(Message message) {
        Session session = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(message);
            session.getTransaction().commit();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    /**
     * Get the last version of all the messages for a specified user.
     * @return
     */
    public static Integer getNextMessageId(Integer userId) {
        Session session = null;
        List<Message> results = new ArrayList<Message>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();
            String sql = "select max(message_id) as message_id from message M  where user_id = %d";
            sql = String.format(sql, userId);

            SQLQuery query = session.createSQLQuery(sql);

            Integer messageId = (Integer)query.uniqueResult();

            session.getTransaction().commit();
            return messageId+1;

        } catch(Throwable t) {
            t.printStackTrace();
        } finally {
            session.close();
        }

        return null;

    }


    /**
     * Get the last version of all the messages for a specified user.
     * @return
     */
    public static Message getLatestUserMessage(Integer userId, Integer messageId) {
        Session session = null;
        Message result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();
            String sql = null;
            sql =
                    "select m1.id as id, m1.user_id as user_id , m1.message_id as message_id , m1.version_id as version_id, m1.title as title , m1.author as author , m1.created as created, m1.object_id  from " +
                            "message m1 " +
                            "where " +
                            "m1.user_id = %d " +
                            "AND " +
                            "m1.message_id = %d order by m1.id desc";

            sql = String.format(sql, userId, messageId);

            System.out.println(sql);
            SQLQuery query = session.createSQLQuery(sql).addEntity(Message.class);

            List<Message> messages = (List<Message>)query.list();
            if(messages.size() > 0) {
                result = messages.get(0);
            }
            session.getTransaction().commit();
            return result;

        } catch(Throwable t) {
            t.printStackTrace();
        } finally {
            session.close();
        }

        return null;

    }

    /**
     * Get the last version of all the messages for a specified user.
     * @return
     */
    public static List getLatestUserMessages(Integer userId) {
        Session session = null;
        List<Message> results = new ArrayList<Message>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();
            String sql = "select id, user_id, message_id, version_id, title, author, created from message";
            sql =
                    "select m1.id as id, m1.user_id as user_id , m1.message_id as message_id , m1.version_id as version_id, m1.title as title , m1.author as author , m1.created as created  from ( " +
                    "select M.user_id as user_id, M.message_id as message_id, max(M.version_id) as max_version_id from message M  where user_id = %d group by M.user_id, M.message_id " +
                    ") m2 " +
                    "INNER JOIN " +
                    "message m1 " +
                    "ON " +
                    "m1.user_id = m2.user_id " +
                    "AND " +
                    "m1.message_id = m2.message_id " +
                    "AND " +
                    "m1.version_id = m2.max_version_id";

            sql = String.format(sql, userId);

            SQLQuery query = session.createSQLQuery(sql).addEntity(Message.class);

            results = (List<Message>)query.list();
            /*
            for (Object result : results) {
                Message m = (Message)result;
                Integer id = m.getId();
                System.out.println(id);
            }
            */
            session.getTransaction().commit();
            return results;

        } catch(Throwable t) {
            t.printStackTrace();
        } finally {
            session.close();
        }

        return null;

    }

    public static VNewMessageResponse saveNewMessageVersion(Integer userId, Integer messageId, Integer lastVersionId, String title, String author, String content, String uuid) {
        VNewMessageResponse vNewMessageResponse= new VNewMessageResponse();
        vNewMessageResponse.setStatus(false);
        GitService.updateFile(messageId.toString(), content, "Comment for message id: " + messageId);
        List<ObjectId> objectIds =  GitService.getObjectIds(messageId.toString());
        //Repository repository, String fileName, ObjectId versionObjectId

        if(objectIds.size() > 0) {
            String objectIdStr = objectIds.get(0).name();
            System.out.println("objectIdStr=" + objectIdStr);
            //FileData fileData1 = GitService.getFileContent(messageId.toString(), objectIds.get(0));
            //FileData fileData2 = GitService.getFileContent(messageId.toString(), ObjectId.fromString(objectIdStr));
            Message message = getLatestUserMessage(userId,  messageId);
            message.setObjectId(objectIdStr);


            addMessage(userId, messageId, lastVersionId+1, title, author, objectIdStr, uuid);
            vNewMessageResponse.setMessageId(messageId);
            vNewMessageResponse.setVersionId(lastVersionId+1);
            vNewMessageResponse.setStatus(true);
        }

        return vNewMessageResponse;
    }


                /*
            String hql = "select M.id, M.userId, M.messageId, max(M.versionId) FROM Message M WHERE M.userId = %d group by M.messageId";
            String nl = System.getProperty("line.separator");
            hql =
                "FROM Message m1 " + nl +
                "INNER JOIN M.userId, M.messageId, max(M.versionId) as maxversionId FROM Message M WHERE M.userId = %d group by M.messageId m2 " + nl +
                "ON m1.userId = m2.userId AND m1.messageId = m2.messageId AND m1.versionId = m2.maxversionId";

            //hql = "FROM Message m1";

            //hql = String.format(hql, userId);
            Query query = session.createQuery(hql);
            */
}