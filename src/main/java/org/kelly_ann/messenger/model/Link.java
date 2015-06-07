package org.kelly_ann.messenger.model;


// Note: we use a custom Link class vs. the javax.rs.core.Link class b/c the JAX-RS one can sometimes be buggy depending
// on the converter being used.
public class Link {
	private String link;
	private String rel;
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getRel() {
		return rel;
	}
	
	public void setRel(String rel) {
		this.rel = rel;
	}
	
}
