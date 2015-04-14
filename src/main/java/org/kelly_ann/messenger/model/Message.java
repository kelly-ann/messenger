package org.kelly_ann.messenger.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


/*
 * JAXB needs to know which model class element is the root XML element to do the conversion to XML as
 * requested in the MessageResource.java file
 * 
 */
@XmlRootElement
public class Message {
	
	private long id;
	private String message;
	private Date created;
	private String author;
	
	
	/*
	 * need a no-arg constructor because when using XML/JSON or any other framework you need 
	 * a way for them to create a new instance of your class to return a response.
	 */
	public Message() {
		
	}
	
	public Message(long id, String message, String author) {
		this.id = id;
		this.message = message;
		this.author = author;
		this.created = new Date();
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}
