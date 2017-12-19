package org.ari.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ari.javabrains.messenger.database.StorageContainer;
import org.ari.javabrains.messenger.model.Message;

public class MessageService {
	
	public MessageService() {
		messages.put(1L, new Message(1L,"Hello World 1!", "Ari"));
		messages.put(2L, new Message(2L,"Hello World 2!", "Gopi"));
	}
	
	private Map<Long, Message> messages = StorageContainer.getMessages();
		
	public List<Message> getAllMessage() {
		return new ArrayList<Message>(messages.values());
	}
	
	public Message getMessage(long id) {
		return messages.get(id);
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if(message.getId() <=0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}
	
	// Returns list of messages created on particular year
	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messagesForYear = new ArrayList<Message>();
		Calendar cal = Calendar.getInstance();
		for(Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	// Returns list of messages with particular starting element and size
	public List<Message> getAllMessage(int start, int size) {
		ArrayList<Message> list = new ArrayList<Message>((messages).values());
		if(start+size > list.size()) return new ArrayList<Message>();
		return list.subList(start, start+size);
	}
}
