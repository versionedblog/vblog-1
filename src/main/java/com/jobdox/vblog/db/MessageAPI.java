/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.jobdox.vblog.db;
import com.jobdox.vblog.dataobj.Message;
import com.jobdox.vblog.util.HibernateUtil;
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
        List results = getLatestUserMessages(1);
        //addMessage();
        System.exit(1);
    }

    public static void addMessage() {
        Session session = null;
        try {
            System.out.println("Maven + Hibernate + MySQL");
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Message message = new Message(-1, 1, 4, 9, "title", "author", new Date());
            session.save(message);
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
    public static List getLatestUserMessages(Integer userId) {
        Session session = null;
        List<Message> results = new ArrayList<Message>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();
            String sql = "select id, user_id, message_id, version_id, title, author, created from message";
            sql =
                    "select m1.id as id, m1.user_id as user_id , m1.message_id as message_id , m1.version_id as version_id, m1.title as title , m1.author as author , m1.created as created  from ( " +
                    "select M.user_id as user_id, M.message_id as message_id, max(M.version_id) as max_version_id from message M group by M.user_id, M.message_id " +
                    ") m2 " +
                    "INNER JOIN " +
                    "message m1 " +
                    "ON " +
                    "m1.user_id = m2.user_id " +
                    "AND " +
                    "m1.message_id = m2.message_id " +
                    "AND " +
                    "m1.version_id = m2.max_version_id";

            SQLQuery query = session.createSQLQuery(sql).addEntity(Message.class);

            results = (List<Message>)query.list();
            /*
            for (Object result : results) {
                Message m = (Message)result;
                Integer id = m.getId();
                System.out.println(id);
            }
            */
            return results;

        } catch(Throwable t) {
            t.printStackTrace();
        } finally {
            session.close();
        }

        return null;

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