package org.kelly_ann.messenger.service;

import java.util.ArrayList;
import java.util.List;

import org.kelly_ann.messenger.model.Message;

/*
 * The flow of events is that: the resource(MessageResource.java) makes a call to the service(MessageService.java) which 
 * uses the model object(Message.java) to populate the list of messages that are returned by the service class to the resource class.
 * to convert the message to XML JAXB needs to be given the @XmlRootElement tag of the model.
 */
// Note: if this were an actual service it would make a call to the db and get the list of Messages.
public class MessageService {
	
	public List<Message> getAllMessages() {
		Message m1 = new Message(1L, "Hello World!", "Kelly-Ann");
		Message m2 = new Message(2L, "Hello Jersey!", "Kelly-Ann");
		List<Message> list = new ArrayList<>();
		list.add(m1);
		list.add(m2);
		return list;
	}
	
}
