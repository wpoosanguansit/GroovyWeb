package com.pdmaf.ui.gwt.client.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 27, 2009
 * Time: 7:42:09 AM
 */
public class ServiceRequest extends JavaScriptObject {
    protected ServiceRequest() {};

    public static enum FLAG {
        MISCATEGORIZED,
        PROHIBITED,
        SPAMOROVERPOST,
        GREAT
    }

    public final int getScore(FLAG flag) {
        int score = 0;
        switch (flag) {
             case MISCATEGORIZED : score = getMiscategorizedScore();
             case PROHIBITED     : score = getProhibitedScore();
             case SPAMOROVERPOST : score = getSpamOrOverPostScore();
             case GREAT          : score = getGreatScore();
        }
        return score;
    }

    public final native void flagAsMisCategorized() /*-{  this.miscategorizedScore++; }-*/;
    public final native void flagAsProhibited() /*-{ this.prohibitedScore++; }-*/;
    public final native void flagAsSpamOrOverPost() /*-{ this.spamOrOverPostScore++; }-*/;
    public final native void flagAsGreat() /*-{ this.greatScore++; }-*/;
    
    public final native String getID() /*-{ return this.id; }-*/;
    public final native String getRev() /*-{ return this.rev; }-*/;
    public final native String getDescription() /*-{ return this.description; }-*/;
    public final native void setDescription(String description) /*-{ this.description = description; }-*/;
    //format "EEE, MMM d, ''yy", i.e Wed, July 10, '96
    public final Date getPostedDate() {
        DateTimeFormat dateFormatter = DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        return dateFormatter.parse(this.getStringPostedDate());
    };
    public final void setPostedDate(Date postedDate) {
        DateTimeFormat dateFormatter = DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        this.setStringPostedDate(dateFormatter.format(postedDate));
    }
    public final native String getTitle() /*-{ return this.title; }-*/;
    public final native void setTitle(String title) /*-{ this.title = title; }-*/;
    public final native String getReplyEmail() /*-{ return this.replyEmail; }-*/;
    public final native void setReplyEmail(String replyEmail) /*-{ this.replyEmail = replyEmail; }-*/;
    public final native String showEmailPreference() /*-{ this.showEmailPreference; }-*/;
    public final native void setShowEmailPreference(String showEmailPreference) /*-{ this.showEmailPreference = showEmailPreference; }-*/;
    public final native String getEmailHashKey() /*-{ this.emailHashKey; }-*/;
    public final native void setEmailHashKey(String emailHashKey) /*-{ this.emailHashKey = emailHashKey; }-*/;
    public final native String getSpecificLocation() /*-{ this.specificLocation; }-*/;
    public final native void setSpecificLocation(String specificLocation) /*-{ this.specificLocation = specificLocation; }-*/;
    public final native String getCity() /*-{ this.city; }-*/;
    public final native void setCity(String city) /*-{ this.city = city; }-*/;
    public final native String getState() /*-{ this.state; }-*/;
    public final native void setState(String state) /*-{ this.state = state; }-*/;
    public final native String getCountry() /*-{ this.country; }-*/;
    public final native void setCountry(String country) /*-{ this.country = country; }-*/;
    public final native String getPoster() /*-{ return this.poster; }-*/;
    public final native void setPoster(String poster) /*-{ this.poster = poster; }-*/;
    public final native String getCategory() /*-{ return this.category; }-*/;
    public final native void setCategory(String category) /*-{ this.category = category; }-*/;
    public final native String getOtherCompensation()  /*-{ return this.otherCompensation; }-*/;
    public final native void setOtherCompensation(String otherCompensation) /*-{ this.otherCompensation = otherCompensation;}-*/;
    public final native int getPointsOffered() /*-{ return this.pointsOffered; }-*/;
    public final native void setPointsOffered(int pointsOffered) /*-{ this.pointsOffered = pointsOffered; }-*/;
    public final native List<Comment> getComments() /*-{ return this.comments;}-*/;
    public final native void setCommnets(List<Comment> comments) /*-{ this.comments = comments; }-*/;
    public final native List<Attachment> getAttachments() /*-{ return this.attachments;}-*/;
    public final native void setAttachments(List<Attachment> attachments) /*-{ this.attachments = attachments; }-*/;    
    public final native List<FLAG> getServiceRequestFlag() /*-{ return this.serviceRequestFlag;}-*/;
    public final native void setServiceRequestFlag(List<FLAG> serviceRequestFlag) /*-{ this.serviceRequestFlag = serviceRequestFlag; }-*/;
    public final native String getStringPostedDate() /*-{ return this.postedDate; }-*/;
    private final native void setStringPostedDate(String postedDate) /*-{ this.postedDate = postedDate; }-*/;
    private final native int getMiscategorizedScore() /*-{ this.miscategorizedScore; }-*/;
    private final native int getProhibitedScore() /*-{ this.prohibitedScore; }-*/;
    private final native int getSpamOrOverPostScore() /*-{ this.spamOrOverPostScore; }-*/;
    private final native int getGreatScore() /*-{ this.greatScore; }-*/;
}
