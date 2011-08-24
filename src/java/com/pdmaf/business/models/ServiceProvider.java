package com.pdmaf.business.models;

import com.pdmaf.utils.generators.IDGenerator;
import com.pdmaf.utils.enums.InstanceStatus;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.svenson.JSON;
import org.svenson.JSONParser;
import org.joda.time.DateTime;

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 19, 2009
 * Time: 1:26:20 PM
 */
public class ServiceProvider implements Entity {

    private String _id;
    private String _rev;
    private String userid;
    private String displayName;
    private String skillDescription;
    private String skillCategory;
    private String reference;
    private String note;
    private String comments;
    private DateTime postedDate;
    private InstanceStatus status;

    public ServiceProvider(String userid, String displayName, String skillDescription, String skillCategory) {
        if (userid == null || userid.isEmpty() || displayName == null || displayName.isEmpty() || skillDescription == null
                || displayName.isEmpty() || skillCategory == null || skillCategory.isEmpty()) {
            throw new IllegalArgumentException("ServiceProvider: userid, displayName, skillDescription, skillCategory " +
                    "can not be null or empty.");
        }

        this._id = IDGenerator.generateIDFor("ServiceProvider");
        this.userid = userid;
        this.displayName = displayName;
        this.skillDescription = skillDescription;
        this.skillCategory = skillCategory;
        this.status = InstanceStatus.TRANSCIENT;
    }

    public ServiceProvider(String userid, String displayName, String skillDescription, String skillCategory,
                           String reference, String note) {
        if (userid == null || userid.isEmpty() || displayName == null || displayName.isEmpty() || skillDescription == null
                || displayName.isEmpty() || skillCategory == null || skillCategory.isEmpty() || reference == null ||
                reference.isEmpty() || note == null || note.isEmpty()) {
            throw new IllegalArgumentException("ServiceProvider: userid, displayName, skillDescription, skillCategory, " +
                    "reference or note can not be null or empty.");
        }

        this._id = IDGenerator.generateIDFor("ServiceProvider");
        this.userid = userid;
        this.displayName = displayName;
        this.skillDescription = skillDescription;
        this.skillCategory = skillCategory;
        this.reference = reference;
        this.note = note;
        this.status = InstanceStatus.TRANSCIENT;
    }
    public final String getUserid() {
        return userid;
    }

    public final String getDisplayName() {
        return displayName;
    }

    public final String getSkillDescription() {
        return skillDescription;
    }

    public final String getSkillCategory() {
        return skillCategory;
    }

    public final String getReference() {
        return reference;
    }

    public final String getNote() {
        return note;
    }

    public final InstanceStatus getStatus() {
        return status;
    }

    @Override
    public final String id() {
        return this._id; 
    }

    @Override
    public final String toJSON() {
        Map<String, Object> map = new HashMap<String, Object>();
	    map.put("_id", _id.toString());
	    map.put("_rev", _rev.toString());
	    if (comments != null)
	    	map.put("comments", comments.toString());
	    map.put("postedDate", postedDate.toString());
	    map.put("status", status.toString());
        map.put("userid", userid.toString());
        map.put("displayName", displayName.toString());
        map.put("skillDescription", skillDescription.toString());
        map.put("skillCategory", skillCategory.toString());
        map.put("reference", reference.toString());
        map.put("note", note.toString());
        map.put("comments", comments.toString());
	    JSON json = new JSON();
		return json.forValue(map);
    }


	public ServiceProvider addComment(Comment comment) {
		if (this.comments != null && !this.comments.isEmpty()) {
			//we check if the comment was already added if yes does nothing
		    Pattern pattern = Pattern.compile(comment.getText());
		    Matcher matcher = pattern.matcher(this.comments);
		    if (!matcher.find()) {
		    	this.comments = this.comments.replaceFirst(Pattern.quote("["), "");
		    	this.comments = this.comments.replace(Pattern.quote("]"), "");
		    	if (this.comments.isEmpty()) {
		    		this.comments = "[ " + comment.toJSON() + " ]";
		    	} else {
		    		this.comments = "[ " + this.comments + " , " + comment.toJSON() + " ]";
		    	}
		    }
		} else {
			this.comments = "[ " + comment.toJSON() + " ]";
		}

	    return this;
	}

	public ServiceProvider addComments(List<Comment> comments) {
		String tmp = "";
		if (this.comments != null && !this.comments.isEmpty()) {
			this.comments.replace(Pattern.quote("["),  "");
			this.comments.replace(Pattern.quote("]"),  "");
			tmp = this.comments + " ,";
			for(Comment comment : comments) {
				Pattern pattern = Pattern.compile(comment.getText());
				Matcher matcher = pattern.matcher(this.comments);
				if (!matcher.find()) {
					tmp +=  tmp + comment.toJSON() + ",";
				} else {
					continue;
				}
			}
			tmp = "[" + tmp.substring(0, tmp.lastIndexOf(",")) + "]";
			this.comments = tmp.replaceAll("null", "");
		} else {
			for(Comment comment : comments) {
				Pattern pattern = Pattern.compile(comment.getText());
				Matcher matcher = pattern.matcher(this.comments);
				if (!matcher.find()) {
					tmp +=  tmp + comment.toJSON() + ",";
				} else {
					continue;
				}
			}
			tmp = "[" + tmp.substring(0, tmp.lastIndexOf(",")) + "]";
			this.comments = tmp.replaceAll("null", "");
		}

		return this;
	}

	public List<Comment> getComments() {
		List<Comment> commentList = new ArrayList<Comment>();
		if (this.comments != null && !comments.isEmpty()) {
			JSON json = new JSON();
			List<Map<String, Object>> list = JSONParser.defaultJSONParser().parse(List.class, this.comments);
			for (Map<String, Object> map : list) {
				commentList.add(new Comment(map));
			}
		}

		return commentList;
	}

    protected ServiceProvider(Map map) {
      	this._id = (String)map.get("_id");
		this._rev = (String)map.get("_rev");
        this.userid = (String)map.get("userid");
        this.displayName = (String)map.get("displayName");
        this.skillDescription = (String)map.get("skillDescription");
        this.skillCategory = (String)map.get("skillCategory");
        this.reference = (String)map.get("reference");
        this.note = (String)map.get("note");
        this.postedDate = new DateTime((String)map.get("postedDate"));
        this.status = InstanceStatus.valueOf((String)map.get("status"));
    }
}
