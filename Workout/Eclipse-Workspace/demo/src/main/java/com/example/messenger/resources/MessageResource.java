package com.example.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.example.messenger.model.Message;
import com.example.messenger.service.MessageService;

@RestController
@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService messageService = new MessageService(); 
	
	@GET
	public List<Message> getMessages(
			@QueryParam("year") int year, 
			@QueryParam("start") int start, @QueryParam("size") int size) {
		final Logger slf4jLogger = LoggerFactory.getLogger(MessageResource.class);
		slf4jLogger.trace("Hello World!");
		slf4jLogger.info("Msg #1");
		slf4jLogger.warn("Msg #2");
		slf4jLogger.error("Msg #3");
		slf4jLogger.debug("Msg #4");

		if(year > 0) {
			return messageService.getAllMessagesForYear(year);
		} else if(start >= 0 && size >= 0) {
			return messageService.getAllMessage(start, size);
		} else {
			return messageService.getAllMessage();
		}
	}
	
	@POST
	public Message addMessage(Message message) {
		return messageService.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
		message.setId(messageId);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public Message deleteMessage(@PathParam("messageId") long messageId) {
		return messageService.removeMessage(messageId);
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId) {
		return messageService.getMessage(messageId);
	}
	
}
