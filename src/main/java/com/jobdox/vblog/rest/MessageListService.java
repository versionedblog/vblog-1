/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.jobdox.vblog.rest;

import com.jobdox.vblog.db.MessageAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    public VMessageList getTrackInJSON(@PathParam("userId") String userId) {

        VMessageList vMessageList = new VMessageList();
        VMessage message11 = new VMessage( "0101010", 1, 1, "title-1-1", "authorX", new Date());
        VMessage message12 = new VMessage( "0101010", 1, 1, "title-1-1", "authorX", new Date());
        VMessage message21 = new VMessage( "0101010", 1, 1, "title-1-1", "authorX", new Date());
        VMessage message22 = new VMessage( "0101010", 1, 1, "title-1-1", "authorX", new Date());
        List<VMessage> messageList = new ArrayList<VMessage>();
        messageList.add(message11);
        messageList.add(message12);
        messageList.add(message21);
        messageList.add(message22);

        vMessageList.setUserId(userId);
        vMessageList.setUserName("umesh");
        vMessageList.setMessages(MessageAPI.getLatestUserMessages(1));


        return vMessageList;

    }

    /*
    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTrackInJSON(Track track) {

        String result = "Track saved : " + track;
        return Response.status(201).entity(result).build();

    }
        */

}