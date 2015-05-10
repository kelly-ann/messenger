package org.kelly_ann.messenger.resources;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.kelly_ann.messenger.model.Message;
import org.kelly_ann.messenger.resources.beans.MessageFilterBean;
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
 * Step 5: (Optional) To return an individual value from the resource URL:  use the @Path("/{messageId}") annotation at the METHOD-level 
 * to tell Jersey that it is a variable element/subdirectory that when appended to the CLASS-level URL, Jersey should 
 * act (i.e. get, post, etc.) on the method. 
 * Ex: the method getMessage()'s URL looks like this: "http://localhost:8080/messenger/webapi/messages/[EnterMessageIdHere]".
 * 
 * Step 6: Then, use the @PathParam("messageId") annotation to tell Jersey to return the "messageId" element to the method so that 
 * it can be called/used within the method. 
 * Note: Jersey will do some autoboxing conversions and will for ex. convert a String to a long, as seen below.
 * 
 * Important Note:  
 * The @PathParam is pretty powerful!  It can be used to return multiple path params in a URI ex: "something/id1/name/id2".  It can also 
 * be used to return regex's ex: all URIs beginning with "something/".
 * 
 * Step 7:  To switch from returning XML to returning JSON via the webservice API: 1) change the @Produces annotation 
 * to "APPLICATION_JSON".  Then, open the Maven "pom.xml" file and click on the "pom.xml" tab.  Uncomment the lines that 
 * say "<!-- uncomment this to get JSON support", save the pom.xml file and restart/reload the Tomcat server.
 * 
 * Step 8:  To implement a POST method you need the @Consumes method annotation along with the @Produces annotation since the 
 * method will take in information from the user.
 * 
 * Tip!  Instead of doing multiple @Produces and @Consumes annotations on each method you can set them all at once at the 
 * class-level instead.
 * 
 * Step 9:  The @QueryParam annotation can be added in to determine which method to run from the MessageService class when there is or 
 * isn't a query parameter supplied.  Since we had 3 @QueryParam annotations in the getMessages() method we created a MessageFilterBean
 * class and used the @BeanParam instead to access the getter methods for the @QueryParam variables in the MessageFilterBean class.
 */
@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	// GET HTTP methods return an existing single resource or existing collection of resources.
	// API #1 - this GET method returns an existing collection of Messages
	// this is what gets called by the REST API client tool (i.e. Postman) by default
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
		if (filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	// API #2 - this GET method return an existing single
	@GET
	@Path("/{messageId}") // this denotes that messageId will be a VARIABLE URL element
	public Message getMessage(@PathParam("messageId") long id) { // Jersey will auto convert the String messageId to a long
		return messageService.getMessage(id);
		//return "Got path param " + messageId; this tests the method with a print statement
	}
	
	// API #3 POST HTTP methods add a new resource
	@POST
	public Message addMessage(Message message) {
		return messageService.addMessage(message);
	}
	
	// API #4 PUT HTTP methods update an existing resource
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	// API #5 DELETE HTTP methods remove an existing resource
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	
	
}
