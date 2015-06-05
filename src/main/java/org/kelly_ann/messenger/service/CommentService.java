package org.kelly_ann.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.kelly_ann.messenger.database.DatabaseClass;
import org.kelly_ann.messenger.model.Comment;
import org.kelly_ann.messenger.model.ErrorMessage;
import org.kelly_ann.messenger.model.Message;



public class CommentService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	
	// this gets all comments associated with a specific message
	public List<Comment> getAllComments(long messageId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId) {
		// this is not the preferred way because you have  non-business logic in your service.  
		// Exceptions are more presentation than service.  They belong in their own classes/ package.
		ErrorMessage errorMessage = new ErrorMessage("Message not found", 404, "http://kelly-ann.org");
		ErrorMessage errorMessage2 = new ErrorMessage("Comment not found", 404, "http://kelly-ann.org");
		Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
		Response response2 = Response.status(Status.NOT_FOUND).entity(errorMessage2).build();

		Message message = messages.get(messageId);
		if(message == null) {
			throw new WebApplicationException(response);
		}
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		Comment comment = comments.get(commentId);
		if (comment == null) {
			throw new NotFoundException(response2);
		}
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if (comment.getId() <= 0) {
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
	
}
