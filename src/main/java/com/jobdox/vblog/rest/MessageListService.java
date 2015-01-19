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
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by umesh on 12/24/14.
 */
@Path("/json/messagelist")
public class MessageListService {
    @Context
    UriInfo uriInfo;

    @Context
    Request request;
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

        MultivaluedMap<String, String> mvmpp = uriInfo.getPathParameters();
        MultivaluedMap<String, String> mvmqp = uriInfo.getQueryParameters();
        String hostName = uriInfo.getBaseUri().getHost();
        System.out.println("HostName:" + hostName);
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
    @Path("/all/user/{userId}/message/{messageId}/latest")
    @Produces(MediaType.APPLICATION_JSON)
    public VMessageEnvelope getLatestMessageInJSON(@PathParam("userId") Integer userId, @PathParam("messageId") Integer messageId) {
        Message message = MessageAPI.getLatestUserMessage(userId, messageId);
        Message messageLatest = MessageAPI.getLatestUserMessage(userId, message.getMessageId());
        System.out.println("Incoming id=" + messageId + ", Outgoing Id=" + messageLatest.getId());
        VMessageEnvelope messageEnvelope =  getMessageInJSON(userId, messageLatest.getMessageId(), messageLatest.getVersionId());
        messageEnvelope.setUuidOverride(UUID.randomUUID().toString());
        return messageEnvelope;
    }


    /**
     * Get the latest version of the specified message for a specified user
     *
     * @param userId
     * @return
     */
    @GET
    @Path("/all/user/{userId}/message/{messageId}/version/{versionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public VMessageEnvelope getMessageInJSON(@PathParam("userId") Integer userId, @PathParam("messageId") Integer messageId, @PathParam("versionId") Integer versionId) {

        VMessage message = new VMessage( );
        Message message1 = null;
        if(versionId == -1) {
            message1 = MessageAPI.getLatestUserMessage(userId, messageId);
        } else {
            message1 = MessageAPI.getMessage(userId, messageId, versionId);
        }

        Integer prevMessageId = MessageAPI.getPrevUserMessageId(userId, messageId);
        Integer nextMessageId = MessageAPI.getNextUserMessageId(userId, messageId);

        Integer prevVersionId = MessageAPI.getPrevUserVersionId(userId, messageId, message1.getVersionId());
        Integer nextVersionId = MessageAPI.getNextUserVersionId(userId, messageId, message1.getVersionId());

        Integer lastVersionId = MessageAPI.getLastVersionId(userId, messageId);


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
        messageEnvelope.setLastVersionId(lastVersionId);

        return messageEnvelope;

    }

    @GET
    @Path("/all/diff/user/{userId}/message/{messageId}/version/{versionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public VMessageDiffResponse getMessageDiffInJSON(@PathParam("userId") Integer userId, @PathParam("messageId") Integer messageId, @PathParam("versionId") Integer versionId) {
        return getMessageDiff(userId, messageId, versionId);
    }

    private VMessageDiffResponse getMessageDiff(Integer userId, Integer messageId, Integer versionId)
    {
        VMessageDiffResponse vmessageDiffResponse= new VMessageDiffResponse();
        vmessageDiffResponse.setSuccess(true);
        vmessageDiffResponse.setUserId(userId);
        vmessageDiffResponse.setMessageId(messageId);
        vmessageDiffResponse.setVersionNumber(versionId);
        Message latestMessage = MessageAPI.getLatestUserMessage(userId, messageId);
        if(latestMessage == null) {
            vmessageDiffResponse.setSuccess(false);
            vmessageDiffResponse.setReason(String.format("The messageId %d specified is not valid for userId %d", messageId, userId));
            return vmessageDiffResponse;
        }
        Integer maxVersionNumber = latestMessage.getVersionId();
        vmessageDiffResponse.setMaxVersionNumber(maxVersionNumber);
        if(versionId > maxVersionNumber) {
            vmessageDiffResponse.setSuccess(false);
            vmessageDiffResponse.setReason("The version number requested is greater than any available for message");
            return vmessageDiffResponse;
        }

        if(versionId == 0 || versionId < -1) {
            vmessageDiffResponse.setSuccess(false);
            vmessageDiffResponse.setReason(String.format("The version %d number requested is not valid for userId %d and messageId %d", versionId, userId, messageId) );
            return vmessageDiffResponse;
        }

        if(versionId == -1) {
            versionId = maxVersionNumber;
            vmessageDiffResponse.setVersionNumber(versionId);
        }
        Message newMessage = MessageAPI.getMessage(userId, messageId, versionId);
        FileData newMessageFileData = GitService.getFileContent(messageId.toString(), ObjectId.fromString(newMessage.getObjectId()));
        String newMessageContent = newMessageFileData.getContent();
        vmessageDiffResponse.setNewMessage(newMessageContent);
        String oldMessageContent = "";
        if(versionId == 1) {
            vmessageDiffResponse.setOldMessage(oldMessageContent);
        } else {
            Message oldMessage = MessageAPI.getMessage(userId, messageId, versionId-1);
            FileData oldMessageFileData = GitService.getFileContent(messageId.toString(), ObjectId.fromString(oldMessage.getObjectId()));
            oldMessageContent = oldMessageFileData.getContent();
            vmessageDiffResponse.setOldMessage(oldMessageContent);
        }

        return vmessageDiffResponse;
    }

    @POST
    @Path("/all/message/diff")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public VMessageDiffResponse getMessageDiffInJSONUsingPost(VMessageDiffRequest vmessageDiffRequest) {

        Integer userId = vmessageDiffRequest.getUserId();
        Integer messageId = vmessageDiffRequest.getMessageId();
        Integer versionId = vmessageDiffRequest.getVersionId();

        return getMessageDiff(userId, messageId, versionId);
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


}