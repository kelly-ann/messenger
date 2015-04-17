package org.kelly_ann.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kelly_ann.messenger.database.DatabaseClass;
import org.kelly_ann.messenger.model.Message;

/*
 * The flow of events is that: the resource(MessageResource.java) makes a call to the service(MessageService.java) which 
 * uses the model object(Message.java) to populate the list of messages that are returned by the service class to the resource class.
 * to convert the message to XML JAXB needs to be given the @XmlRootElement tag of the model.
 */
// Note: if this were an actual service it would make a call to the db and get the list of Messages.
public class MessageService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		messages.put(1L, new Message(1, "Hello World", "Kelly-Ann"));
		messages.put(2L, new Message(2, "Hello Jersey", "Kelly-Ann"));
	}
	
	public List<Message> getAllMessages() {
		// pass the Map collection to the ArrayList collection to initialize the ArrayList with those elements.
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
		if (message.getId() <= 0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}
} 
