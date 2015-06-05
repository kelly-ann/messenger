package org.kelly_ann.messenger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


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
	private Map<Long, Comment> comments = new HashMap<>();
	private List<Link> links = new ArrayList<>();
		
	
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

	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
	}
	
	public String getAuthor() {
		return author;
	}
	
	@XmlTransient // this makes it so that when you access a Message you don't get all the Comment's for that message returned to you also.
	public Map<Long, Comment> getComments() {
		return comments;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public long getId() {
		return id;
	}
	
	public List<Link> getLinks() {
		return links;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setComments(Map<Long, Comment> comments) {
		this.comments = comments;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
