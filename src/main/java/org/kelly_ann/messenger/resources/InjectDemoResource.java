package org.kelly_ann.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {
	
	/**
	 * Notes:
	 * @QueryParam is a parameter that is added to the URI with a semicolon "?".
	 * @MatrixParam is a parameter that is added to the URI with a semicolon ";" instead of a "?".
	 * @HeaderParam is a parameter that is usually used to send a user session authentication token's value back to the code that can 
	 * then be accessed and used.
	 * @CookieParam is a parameter that is to retrieve the cookie's value.
	 * @FormParam is a parameter that uses the form name as the key and returns the value of the form element to the application.
	 */
	
	@GET
	@Path("annotations")
	public String getParamsUsingAnnotations(@MatrixParam("param") String matrixParam,
											@HeaderParam("authSessionId") String header,
											@CookieParam("name") String cookie) {
		return "Matrix param: " + matrixParam 
				+ " Header param: " + header
				+ " Cookie: " + cookie;
	}
	
}
