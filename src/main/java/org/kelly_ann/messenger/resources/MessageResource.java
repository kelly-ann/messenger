package org.kelly_ann.messenger.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.kelly_ann.messenger.model.Message;
import org.kelly_ann.messenger.service.MessageService;

/*
 * How to create a resource:
 * 
 * Step 1:  whenever the app URL (i.e. "http://localhost:8080/webapi" is called Jersey uses the web.xml file to determine which 
 * package it should begin focusing on to answer the specific http method requested.  This is done using the <init-param> tag &
 * setting the <param-value> tag.  (Quick Tip! the package specified can have other packages within it.)
 * 
 * Step 2: after finding the package, Jersey then looks within it for the @Path annotation to determine which url string
 * should trigger the resource/class.  In the case below this class is for the URL "http://localhost:8080/webapi/messages".
 * 
 * Step 3:  the @GET annotation is used to tell Jersey to call the getMessages() method when a the http GET method is called via 
 * the web app's URL.  Why? -  b/c there could be more than one method in the class so Jersey need to know which one to return.
 * 
 * Step 4:  the @Produces annotation is used to tell Jersey what format to respond to the web service request in. (ex: application_xml, 
 * application_json, text_html, etc.)
 * 
 * IMPORTANT NOTE: the URL is mapped to the CLASS BUT the http method used on that URL (get, post, etc.) is mapped to a Java METHOD!
 */
@Path("/messages")
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	// this is what gets called by the REST API client tool (i.e. Postman)
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Message> getMessages() {
		return messageService.getAllMessages();
	}
	
}
