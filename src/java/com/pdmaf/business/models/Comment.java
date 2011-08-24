package com.pdmaf.business.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.svenson.JSON;
import org.joda.time.DateTime;

/**
 * This is to represent the Comment.
 * It extends Entity to enable the persistence to CouchDB
 * in Scala persistence service.  The type must be present
 * as it is used in query to the DB.
 * 
 * @author watt
 *
 */
public class Comment {
	private String text;
	private String author;
	private DateTime createdDate;
	private String type = "Comment";
	
	public Comment(String text, String author) {
		if (text == null || text.isEmpty()) {
			throw new IllegalArgumentException("Comment: entity can not be null");
		}
		this.text = text;
		this.author = author;
		this.createdDate = new DateTime();
	}

	public Comment(String text, String author, Date createdDate) {
		if (text == null || text.isEmpty() || author == null || author == "" || createdDate == null) {
			throw new IllegalArgumentException("Comment: text, author, createdDate can not be null");
		}
		this.text = text;
		this.author = author;
		this.createdDate = new DateTime(createdDate);
	}

    public final String getText() {
        return this.text;
    }

    public final String getAuthor() {
        return this.author;
    }

    public final Date getCreatedDate() {
        return this.createdDate.toDate();
    }
	/**
	 * This is needed by the persistence to initialize the object
	 * Persistence passes Map of JSON string to the object to initialize itself with.
	 * @param map
	 */
	protected Comment(Map map) {
		this.text = (String)map.get("text");
		this.author = (String)map.get("author");
		this.createdDate = new DateTime((String)map.get("createdDate"));
	}
	
	public String toJSON() {
		Map map = new HashMap<String, Object>();
	    map.put("text", text.toString());
        map.put("author", author);
	    map.put("createdDate", createdDate.toString());
	    JSON json = new JSON();
		
	    return json.forValue(map); 
	}

}
