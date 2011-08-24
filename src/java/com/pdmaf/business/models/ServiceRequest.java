package com.pdmaf.business.models;

import com.pdmaf.utils.generators.IDGenerator;
import com.pdmaf.utils.enums.InstanceStatus;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.svenson.JSON;
import org.svenson.JSONParser;
import org.joda.time.DateTime;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 30, 2009
 * Time: 8:10:05 AM
 */
public class ServiceRequest implements Entity {

    public static enum FLAG {
        MISCATEGORIZED,
        PROHIBITED,
        SPAMOROVERPOST,
        GREAT
    }

    public final int getScore(FLAG flag) {
        int score = 0;
        switch (flag) {
             case MISCATEGORIZED : score = miscategorizedScore;
             case PROHIBITED     : score = prohibitedScore;
             case SPAMOROVERPOST : score = spamOrOverPostScore;
             case GREAT          : score = greatScore;
        }
        return score;
    }

    public ServiceRequest flagAsMisCategorized() {
        if (flag != null  && !flag.contains(FLAG.MISCATEGORIZED.toString())) {
            flag = flag + " " + FLAG.MISCATEGORIZED.toString();
        }

        miscategorizedScore++;
        
        return this;
    }

    public ServiceRequest flagAsProhibited() {
        if (flag != null  && !flag.contains(FLAG.PROHIBITED.toString())) {
            flag = flag + " " + FLAG.PROHIBITED.toString();
        }
        prohibitedScore++;

        return this;
    }

    public ServiceRequest flagAsSpamOrOverPost() {
        if (flag != null && !flag.contains(FLAG.SPAMOROVERPOST.toString())) {
            flag = flag + " " + FLAG.SPAMOROVERPOST.toString(); 
        }
        spamOrOverPostScore++;

        return this;
    }

    public ServiceRequest flagAsGreat() {
        if (flag != null && !flag.contains(FLAG.GREAT.toString())) {
            flag = flag + " " + FLAG.GREAT.toString(); 
        }
        greatScore++;

        return this;
    }

    private String _id;
    private String _rev;
    private String description;
    private DateTime postedDate;
    private String title;
    private String replyEmail;
    private String showEmailPreference;
    private String emailHashKey;
    private String specificLocation;
    private String city;
    private String state;
    private String country;
    private String poster;
    private String category;
    private String otherCompensation;
    private String pointsOffered;
    private String comments;
    private String flag;
    private int miscategorizedScore;
    private int prohibitedScore;
    private int spamOrOverPostScore;
    private int greatScore;
	private InstanceStatus status;
	private String type = "ServiceRequest";

    public ServiceRequest(String title, String description, String category, String replyEmail, String emailHashKey,
                          String showEmailPreference, String specificLocation, String city, String state,
                          String country, String poster) {
        if (title == null || title.isEmpty() || description == null || description.isEmpty() || category == null ||
            category.isEmpty() || replyEmail == null || replyEmail.isEmpty() || emailHashKey == null || emailHashKey.isEmpty() ||
                specificLocation == null || specificLocation.isEmpty())
            throw new IllegalArgumentException("ServiceRequest : title, description, category, reply email, email hash key " +
                    "and specific location can not be null or empty!");
        this._id = IDGenerator.generateIDFor("ServiceRequest");
        this.title = title;
        this.description = description;
        this.category = category;
        this.replyEmail = replyEmail;
        this.emailHashKey = emailHashKey;
        this.showEmailPreference = showEmailPreference;
        this.specificLocation = specificLocation;
        this.city = city;
        this.state = state;
        this.country = country;
        if (poster != null) {
            this.poster = poster;
        } else {
            this.poster = "annonymous";
        }
        this.status = InstanceStatus.TRANSCIENT;
    }

    public ServiceRequest(String title, String description, String category, String replyEmail, String emailHashKey,
                          String showEmailPreference, String specificLocation, String city, String state,
                          String country, String poster, String pointsOffered, String otherCompensation) {
        if (title == null || title.isEmpty() || description == null || description.isEmpty() || category == null ||
            category.isEmpty() || replyEmail == null || replyEmail.isEmpty() || emailHashKey == null || emailHashKey.isEmpty() ||
                specificLocation == null || specificLocation.isEmpty())
            throw new IllegalArgumentException("ServiceRequest : title, description, category, reply email, email hash key " +
                    "and specific location can not be null or empty!");
        this._id = IDGenerator.generateIDFor("ServiceRequest");
        this.title = title;
        this.description = description;
        this.category = category;
        this.replyEmail = replyEmail;
        this.emailHashKey = emailHashKey;
        this.showEmailPreference = showEmailPreference;
        this.specificLocation = specificLocation;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pointsOffered = pointsOffered;
        this.otherCompensation = otherCompensation;
        if (poster != null) {
            this.poster = poster;
        } else {
            this.poster = "annonymous";
        }
        this.postedDate = new DateTime();
        this.status = InstanceStatus.TRANSCIENT;
    }

	public ServiceRequest addComment(Comment comment) {
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

	public ServiceRequest addComments(List<Comment> comments) {
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

    public boolean isPostedByRegisteredUser() {
        return poster != null && !poster.isEmpty() && poster != "annonymous";
    }


    public List<FLAG> getFlags() {
        if (this.flag == null || this.flag.isEmpty())
            return null;

        List<FLAG> result = new ArrayList<FLAG>();
        String[] flags = flag.split(" ");
        for (int i = 0; i < flags.length; i++) {
             result.add(FLAG.valueOf(flags[i]));
        }

        return result;
    }

    public final String id() {
        return this._id;
    }

    public final String getEmailHashKey() {
        return this.emailHashKey;
    }

    public final String getReplyEmail() {
        return this.replyEmail;
    }

    /**
     * to check the state of the service request if it has already been
     * activated - Yes if it has, no otherwise.
     * @return boolean
     */
    public final boolean isActive() {
        return status == InstanceStatus.ACTIVE;
    }

    public final boolean hasExpired() {
        return status == InstanceStatus.EXPIRED;
    }
    
    public final boolean hasBeenActivated() {
        return !(status == InstanceStatus.TRANSCIENT || status == InstanceStatus.SAVED);
    }

    public void expire() {
        this.status = InstanceStatus.EXPIRED;
    }
    /**
     * We only allow activation on service request that has already been saved.
     * We do not allow reactivation as that might cause a conflict with the directory
     * that might not have clean up the reply mail account yet
     */
    public void activate()  {
        if (status != InstanceStatus.SAVED) {
            throw new IllegalStateException("ServiceRequest: activate - the service request " +
                    "has not been saved or has already been activated, hence it can not be activated. " +
                    "or reactivated.");
        }

        status = InstanceStatus.ACTIVE;
    }

    public final String getDescription() {
        return description;
    }

    public final Date getPostedDate() {
        return postedDate.toDate();
    }

    public final String getTitle() {
        return title;
    }

    public final String getShowEmailPreference() {
        return showEmailPreference;
    }

    public final String getSpecificLocation() {
        return specificLocation;
    }

    public final String getCity() {
        return city;
    }

    public final String getCountry() {
        return country;
    }

    public final String getState() {
        return state;
    }

    public final String getPoster() {
        return poster;
    }

    public final String getCategory() {
        return category;
    }

    public final String getCompensation() {
        if (this.otherCompensation != null) {
            return otherCompensation;
        } else if (this.pointsOffered != null) {
            return pointsOffered;
        } else {
            return "No Compensation";
        }
    }

    public final InstanceStatus getStatus() {
        return status;
    }

    public String toJSON() {
        Map<String, Object> map = new HashMap<String, Object>();
	    map.put("_id", _id.toString());
	    map.put("_rev", _rev.toString());
        map.put("title", title);
        map.put("description", description);
        map.put("category", category);
        map.put("replyEmail", replyEmail);
        map.put("emailHashKey", emailHashKey);
        map.put("showEmailPreference", showEmailPreference);
        map.put("specificLocation", specificLocation);
        map.put("city", city);
        map.put("state", state);
        map.put("country", country);
        map.put("pointsOffered", pointsOffered);
        map.put("otherCompensation", otherCompensation);
        map.put("poster", poster);
	    if (comments != null)
	    	map.put("comments", comments.toString());
	    map.put("postedDate", postedDate.toString());
	    map.put("status", status.toString());
	    JSON json = new JSON();
		return json.forValue(map);
    }

    public boolean isPersisted() {
        return _rev != null || !_rev.isEmpty();
    }

    public void update(ServiceRequest serviceRequest) {
        Map map = JSONParser.defaultJSONParser().parse(Map.class, serviceRequest.toJSON());
        if (map.get("title") != null)
            this.title = (String)map.get("title");
        if (map.get("description") != null)
            this.description = (String)map.get("description");
        if (map.get("category") != null)
            this.category = (String)map.get("category");
        if (map.get("replyEmail") != null)
            this.replyEmail = (String)map.get("replyEmail");
        if (map.get("emailHashKey") != null)
            this.emailHashKey = (String)map.get("emailHashKey");
        if (map.get("showEmailPreference") != null)
            this.showEmailPreference = (String)map.get("showEmailPreference");
        if (map.get("specificLocation") != null)
            this.specificLocation = (String)map.get("specificLocation");
        if (map.get("city") != null)
            this.city = (String)map.get("city");
        if (map.get("state") != null)
            this.state = (String)map.get("state");
        if (map.get("country") != null)
            this.country = (String)map.get("country");
        if (map.get("poster") != null)
            this.country = (String)map.get("poster");
        if (map.get("pointsOffered") != null && map.get("otherCompensation") == null)
            this.pointsOffered = (String)map.get("pointsOffered");
        if (map.get("otherCompensation") != null && map.get("pointsOffered") == null)
            this.otherCompensation = (String)map.get("otherCompensation");
        if (map.get("comments") != null)
            this.comments = ((String)map.get("comments"));
        if (map.get("status") != null)
		    this.status = InstanceStatus.valueOf((String)map.get("status"));
        if (map.get("postedDate") != null)
		    this.postedDate = new DateTime((String)map.get("postedDate"));
        if (map.get("miscategorizedScore") != null)
            this.miscategorizedScore = Integer.parseInt((String)map.get("miscategorizedScore"));
        if (map.get("prohibitedScore") != null)
            this.prohibitedScore = Integer.parseInt((String)map.get("prohibitedScore"));
        if (map.get("spamOrOverPostScore") != null)
            this.spamOrOverPostScore = Integer.parseInt((String)map.get("spamOrOverPostScore"));
        if (map.get("greatScore") != null)
            this.greatScore = Integer.parseInt((String)map.get("greatScore"));
    }
    /**
	 * This is the constructor to be used by the reflection api
	 * in the persistence service.  Since constructor can be
	 * queried based on the parameters and to make it generic,
	 * Map is used to pass the information for object initialization
	 * from the persistence to the object that is known at run time.
	 */

	protected ServiceRequest(Map map) {
		this._id = (String)map.get("_id");
		this._rev = (String)map.get("_rev");
        this.title = (String)map.get("title");
        this.description = (String)map.get("description");
        this.category = (String)map.get("category");
        this.replyEmail = (String)map.get("replyEmail");
        this.emailHashKey = (String)map.get("emailHashKey");
        this.showEmailPreference = (String)map.get("showEmailPreference");
        this.specificLocation = (String)map.get("specificLocation");
        this.city = (String)map.get("city");
        this.state = (String)map.get("state");
        this.country = (String)map.get("country");
        this.poster = (String)map.get("poster");
        this.pointsOffered = (String)map.get("pointsOffered");
        this.otherCompensation = (String)map.get("otherCompensation");
        this.comments = ((String)map.get("comments"));
		this.status = InstanceStatus.valueOf((String)map.get("status"));
		this.postedDate = new DateTime((String)map.get("postedDate"));
        this.miscategorizedScore = Integer.parseInt((String)map.get("miscategorizedScore"));
        this.prohibitedScore = Integer.parseInt((String)map.get("prohibitedScore"));
        this.spamOrOverPostScore = Integer.parseInt((String)map.get("spamOrOverPostScore"));
        this.greatScore = Integer.parseInt((String)map.get("greatScore"));
	}
}
