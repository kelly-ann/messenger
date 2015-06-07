package org.kelly_ann.messenger.resources;

import java.net.URI;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilderException;
import javax.ws.rs.core.UriInfo;

import org.kelly_ann.messenger.model.Message;
import org.kelly_ann.messenger.resources.beans.MessageFilterBean;
import org.kelly_ann.messenger.service.CommentService;
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
 * 
 * Step 10:  Create any sub resources by adding a method that returns the sub resource class's data type and uses the @Path to create 
 * the sub resource's URI on which the parent resource (i.e. this class) will invoke the sub resource's class (i.e. CommentResource.java).
 * 
 * Step 11:  To return the location URI in the response header have the POST method return the Response type and accept
 * @Context UriInfo as an argument.  Then use the getAbsolutePath() method to return a UriBuilder object that you add the new resource's
 * id to and return as a URI object via the build() method (i.e. the Build design pattern).
 * 
 * Step 12:  To return the status code in the response header call the created() method of the Response object then call the entity() 
 * method and pass it the new Message object.  Finally, use the build() method to return a Response object as the output of the POST 
 * method.
 * 
 * Step 13: If the GET method below is called it will call the MessageService.getMessage() method.  If a null message is 
 * returned we have created the DataNotFoundException class to handle this.  This Exception has 2 exceptionMapper classes: 
 * DataNotFoundExceptionMapper and GenericExceptionMapper which JAX-RS knows are the handlers via the @Provider annotation.  Both 
 * mapper classes implement the ExceptionMapper interface and accept the Generic type DataNotFoundException or the all 
 * encompassing Throwable type.  They then @Override the toResponse() method which takes in the Exception, sets the ErrorMessage.  
 * Finally the toResponse() method selects the Response object's  * Status.status enum, adds the errorMessage via 
 * the ResponseBuilder's entity() method and then calls the build() to return a Response object which is the toResponse() method's 
 * return type.
 * 
 * Note:  Alternatively, you can use the WebApplicationException class to return the Response object with an ErrorMessage.  However, 
 * this leads to code where the Service is doing exception handling which can get messy.  This is best handled in separate exception 
 * classes since Exceptions are relating to the View more.
 * 
 */
@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService messageService = new MessageService();
	CommentService commentService = new CommentService();

	// API #3 POST HTTP methods add a new resource
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		// the location URI of the newly created resource
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build(); 
		// set the status code to "201" and return it and the location URI in the header
		return Response.created(uri).entity(newMessage).build(); 
																	
	}

	// API #5 DELETE HTTP methods remove an existing resource
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}

	// API #6 - This creates a URI SUBRESOURCE that is not attached to any
	// specific resource and is handled by the CommentResource.java.
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}

	// API #2 - this GET method return an existing single
	@GET
	@Path("/{messageId}")
	// this denotes that messageId will be a VARIABLE URL element
	public Message getMessage(@PathParam("messageId") long id,
			@Context UriInfo uriInfo) {// Jersey autobox's the String msg to a
										// long
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		return message;
	}

	// GET HTTP methods return an existing single resource or existing
	// collection of resources.
	// API #1 - this GET method returns an existing collection of Messages
	// this is what gets called by the REST API client tool (i.e. Postman) by
	// default
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
		if (filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(
					filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder() 						// http://localhost:8080/messenger/webapi
			.path(MessageResource.class) 							// adds: /messages
			.path(MessageResource.class, "getCommentResource") 		// adds: /{messageId}/comments
			.path(CommentResource.class) 							// adds: /
			// this takes the template variable {messageId} and resolves it to an actual value for the URI
			.resolveTemplate("messageId", message.getId()) 
			.build();
		return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder() // http://localhost:8080/messenger/webapi
			.path(ProfileResource.class) // adds: /profiles
			.path(message.getAuthor()) // adds: /{authorName}
			.build();
		return uri.toString();
	}

	/**
	 * @param uriInfo
	 * @param message
	 * @return
	 * @throws IllegalArgumentException
	 * @throws UriBuilderException
	 */
	private String getUriForSelf(UriInfo uriInfo, Message message)
			throws IllegalArgumentException, UriBuilderException {
		String uri = uriInfo.getBaseUriBuilder() // http://localhost:8080/messenger/webapi
				.path(MessageResource.class) // adds: /messages
				.path(Long.toString(message.getId())) // adds: /{messageId}
				.build().toString();
		return uri;
	}

	// API #4 PUT HTTP methods update an existing resource
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id,
			Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}

}
