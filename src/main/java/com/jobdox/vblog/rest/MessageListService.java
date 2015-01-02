/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.jobdox.vblog.rest;

import com.jobdox.vblog.db.MessageAPI;

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

    @GET
    @Path("/all/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public VMessageList getMesageListInJSON(@PathParam("userId") Integer userId) {


        /*
        VMessage message11 = new VMessage( "0101010", 1, 1, "title-1-1", "authorX", new Date());
        VMessage message12 = new VMessage( "0101010", 1, 1, "title-1-1", "authorX", new Date());
        VMessage message21 = new VMessage( "0101010", 1, 1, "title-1-1", "authorX", new Date());
        VMessage message22 = new VMessage( "0101010", 1, 1, "title-1-1", "authorX", new Date());
        List<VMessage> messageList = new ArrayList<VMessage>();
        messageList.add(message11);
        messageList.add(message12);
        messageList.add(message21);
        messageList.add(message22);
        */
        VMessageList vMessageList = new VMessageList();
        vMessageList.setUserId(userId.toString());
        vMessageList.setUserName("umesh");
        vMessageList.setMessages(MessageAPI.getLatestUserMessages(userId));


        return vMessageList;

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


    @GET
    @Path("/all/user/{userId}/message/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public VMessageEnvelope getMessageInJSON(@PathParam("userId") Integer userId, @PathParam("messageId") Integer messageId) {

        VMessage message = new VMessage( "0101010", 1, 1, "title-1-1", "authorX", new Date());
        message.setContent("This is the mesage content for messageId" + messageId);
        VMessageEnvelope messageEnvelope = new VMessageEnvelope();
        messageEnvelope.setVmessage(message);
        messageEnvelope.setUserId(userId);
        messageEnvelope.setMessageId(messageId);
        messageEnvelope.setNextMessageId(messageId+1);
        messageEnvelope.setPreviousMessageId(messageId-1);

        return messageEnvelope;

    }

    @GET
    @Path("/all/user/{userId}/message")
    @Produces(MediaType.APPLICATION_JSON)
    public VNewMessage getNewMessageInJSON(@PathParam("userId") Integer userId) {

        VNewMessage newMessage = new VNewMessage("");
        return newMessage;

    }


    @POST
    @Path("/all/message/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public VNewMessageResponse saveMessage(VSaveMessage saveMessage) {

        if(saveMessage.getMessageId() == null) {
            return MessageAPI.saveNewMessage(saveMessage.getUserId(),
                    saveMessage.getTitle(),
                    saveMessage.getAuthor(),
                    saveMessage.getContent(),
                    saveMessage.getUuid());
        } else {
            return MessageAPI.saveNewMessageVersion(saveMessage.getUserId(), saveMessage.getMessageId(), saveMessage.getLastVersionId(),
                    saveMessage.getTitle(),
                    saveMessage.getAuthor(),
                    saveMessage.getContent(),
                    saveMessage.getUuid());
        }

    }

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


}