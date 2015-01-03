/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.jobdox.vblog.rest;

import com.jobdox.vblog.FileData;
import com.jobdox.vblog.GitService;
import com.jobdox.vblog.dataobj.Message;
import com.jobdox.vblog.db.MessageAPI;
import org.eclipse.jgit.lib.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by umesh on 12/24/14.
 */
@Path("/json/messagelist")
public class MessageListService {

    /**
     * Get the complete list of messages for a specified user
     *
     * @param userId
     * @return
     */
    @GET
    @Path("/all/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public VMessageList getMesageListInJSON(@PathParam("userId") Integer userId) {

        VMessageList vMessageList = new VMessageList();
        vMessageList.setUserId(userId.toString());
        vMessageList.setUserName("umesh");
        vMessageList.setMessages(MessageAPI.getLatestUserMessages(userId));


        return vMessageList;

    }



    /**
     * Get the latest version of the specified message for a specified user
     *
     * @param userId
     * @return
     */
    @GET
    @Path("/all/user/{userId}/message/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public VMessageEnvelope getMessageInJSON(@PathParam("userId") Integer userId, @PathParam("messageId") Integer id) {

        VMessage message = new VMessage( );
        //message.setContent("This is the mesage content for messageId" + messageId);

        Message message1 = MessageAPI.getMessageById(id);
        Integer versionId = message1.getVersionId();
        Integer messageId = message1.getMessageId();
        Integer prevMessageId = MessageAPI.getPrevUserMessageId(userId, messageId);
        Integer nextMessageId = MessageAPI.getNextUserMessageId(userId, messageId);

        Integer prevVersionId = MessageAPI.getPrevUserVersionId(userId, messageId, versionId);
        Integer nextVersionId = MessageAPI.getNextUserVersionId(userId, messageId, versionId);


        FileData fileData = GitService.getFileContent(messageId.toString(), ObjectId.fromString(message1.getObjectId()));
        message.setContent(fileData.getContent());
        message.setMessageId(messageId);
        message.setAuthor(message1.getAuthor());
        message.setCreated(message1.getCreated());
        message.setObjectIdStr(message1.getObjectId());
        message.setTitle(message1.getTitle());
        message.setVersionNumber(message1.getVersionId());

        VMessageEnvelope messageEnvelope = new VMessageEnvelope();
        messageEnvelope.setVmessage(message);
        messageEnvelope.setUserId(userId);
        messageEnvelope.setMessageId(message1.getMessageId());
        messageEnvelope.setNextMessageId(nextMessageId);
        messageEnvelope.setPreviousMessageId(prevMessageId);

        messageEnvelope.setNextVersionId(nextVersionId);
        messageEnvelope.setPreviousVersionId(prevVersionId);

        return messageEnvelope;

    }

    /**
     * Initialize a message with a UUID
     * @param userId
     * @return
     */
    @GET
    @Path("/all/user/{userId}/message")
    @Produces(MediaType.APPLICATION_JSON)
    public VNewMessage getNewMessageInJSON(@PathParam("userId") Integer userId) {

        VNewMessage newMessage = new VNewMessage("");
        return newMessage;

    }


    /**
     * Create the initial version message or create a new version of a message with one or more prior versions.
     * @param saveMessage
     * @return
     */
    @POST
    @Path("/all/message/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public VNewMessageResponse saveMessage(VSaveMessage saveMessage) {

        //Create the first version of this message.
        if(saveMessage.getMessageId() == null) {
            return MessageAPI.saveNewMessage(saveMessage.getUserId(),
                    saveMessage.getTitle(),
                    saveMessage.getAuthor(),
                    saveMessage.getContent(),
                    saveMessage.getUuid());
        } else {
            //Create a version of the message other than the first version of the message.
            return MessageAPI.saveNewMessageVersion(saveMessage.getUserId(), saveMessage.getMessageId(), saveMessage.getLastVersionId(),
                    saveMessage.getTitle(),
                    saveMessage.getAuthor(),
                    saveMessage.getContent(),
                    saveMessage.getUuid());
        }

    }

    /** Hide these for now

    @POST
    @Path("/all/message/test")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public VNewMessageResponse saveMessage1(
                                String password) {

        VNewMessageResponse newMessageResponse = new VNewMessageResponse();
        newMessageResponse.setMessageId(10);
        newMessageResponse.setVersionId(1);
        newMessageResponse.setStatus(true);
        newMessageResponse.setReason("eeee");
        return newMessageResponse;

    }


    @GET
    @Path("/all/user/{userId}/message/{messageId}/hide")
    @Produces(MediaType.APPLICATION_JSON)
    public VMessageEnvelope getMessageInJSONHide(@PathParam("userId") Integer userId, @PathParam("messageId") Integer messageId) {


        VMessage message = new VMessage( "0101010", 1, 1, "title-1-1", "authorX", new Date());
        message.setContent("This is the mesage content");


        VMessageEnvelope messageEnvelope = new VMessageEnvelope();
        messageEnvelope.setVmessage(message);
        messageEnvelope.setUserId(userId);
        messageEnvelope.setMessageId(messageId);
        messageEnvelope.setNextMessageId(messageId+1);
        messageEnvelope.setPreviousMessageId(messageId-1);

        return messageEnvelope;

    }

    **/

}