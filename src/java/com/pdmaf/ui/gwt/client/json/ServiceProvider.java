package com.pdmaf.ui.gwt.client.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 21, 2009
 * Time: 9:33:48 AM
 */
public class ServiceProvider extends JavaScriptObject {
    protected ServiceProvider() {};


    public final native String getID() /*-{ return this.id; }-*/;
    public final native String getRev() /*-{ return this.rev; }-*/;
    public final native String getUserid() /*-{ return this.userid; }-*/;
    public final native void setUserid(String userid) /*-{ this.userid = userid; }-*/;
    public final native String getDisplayName() /*-{ return this.displayName; }-*/;
    public final native void setDisplayName(String displayName) /*-{ this.displayName = displayName; }-*/;
    public final native String getSkillDescription() /*-{ return this.skillDescription; }-*/;
    public final native void setSkillDescription(String skillDescriptio) /*-{ this.skillDescriptio = skillDescriptio; }-*/;
    public final native String getSkillCategory() /*-{ return this.skillCategory; }-*/;
    public final native void setSkillCategory(String skillDescription) /*-{ this.skillCategory = skillCategory; }-*/;
    public final native String getReference() /*-{ return this.reference; }-*/;
    public final native void setReference(String reference) /*-{ this.reference = reference; }-*/;
    public final native String getNote() /*-{ return this.note; }-*/;
    public final native void setNote(String note) /*-{ this.note = note; }-*/;
    public final native String getComments() /*-{ return this.comments; }-*/;
    public final native void setComments(String comments) /*-{ this.comments = comments; }-*/;
        //format "EEE, MMM d, ''yy", i.e Wed, July 10, '96
    public final Date getPostedDate() {
        DateTimeFormat dateFormatter = DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        return dateFormatter.parse(this.getStringPostedDate());
    }
    public final void setPostedDate(Date postedDate) {
        DateTimeFormat dateFormatter = DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        this.setStringPostedDate(dateFormatter.format(postedDate));
    }
    public final native String getStringPostedDate() /*-{ return this.postedDate; }-*/;
    private final native void setStringPostedDate(String postedDate) /*-{  this.postedDate = postedDate; }-*/;            

}
