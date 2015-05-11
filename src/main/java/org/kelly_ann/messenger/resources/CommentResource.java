package org.kelly_ann.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.kelly_ann.messenger.model.Comment;
import org.kelly_ann.messenger.service.CommentService;

/*
 *  How to create a sub resource:
 *  Step 1: In the parent resource create a method that returns the sub resource as its return type and returns the sub resource.
 *  
 *  Step 2: In the parent resource, use the @Path annotation to specify which URI string should call the sub resource's methods.
 *  Note: Once Jersey sees that the method in the parent resource is returning an instance of the sub resource it is smart enough to know 
 *  that it should refer to the sub resources class for methods that will tell how to act on the sub resource string (i.e. HTTP methods).
 *  
 *  Step 3:  In the sub resouce, although optional, for clarity use the @Path("/") annotation to show that the URI has no extra additions.
 *  
 *  Step 3: In the sub resource, specify methods for the corresponding HTTP methods (i.e. GET, POST, PUT, DELETE, etc.).
 */

@Path("/")  //this is optional because this class is a SUBresource vs. being mandatory on a resource.
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {
	
	private CommentService commentService = new CommentService();
	
	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId) {
		return commentService.getAllComments(messageId);
	}
	
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
		return commentService.getComment(messageId, commentId);
	}
	
	@POST
	public Comment addComment(@PathParam("messageId") long messageId, Comment comment) {
		return commentService.addComment(messageId, comment);
	}
	
	// NOTE: that the messageId is only available because in the MessageResource we made a call to the CommentResource using {messageId}
	// in the @Path annotation.
	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("messageId") long messageId, @PathParam("commentId") long id, Comment comment) {
		comment.setId(id);
		return commentService.updateComment(messageId, comment);
	}
	
	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
		commentService.removeComment(messageId, commentId);
	}
	
}
