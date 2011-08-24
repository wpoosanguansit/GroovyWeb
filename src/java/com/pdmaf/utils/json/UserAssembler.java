package com.pdmaf.utils.json;

import com.pdmaf.ui.gwt.client.enums.LoginResponseType;
import com.pdmaf.business.models.User;
import net.sf.json.util.JSONStringer;
import com.pdmaf.ui.gwt.client.enums.SignupResponseType;
import net.sf.json.util.JSONBuilder;
import net.sf.json.JSONObject;
import com.pdmaf.business.models.Location;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 6, 2009
 * Time: 9:56:51 PM
 * This is a utility to create json representation for user object that will be
 * passed on to gwt components on the pages.
 */

public class UserAssembler {

    public static JSONBuilder addUserSpecification(User user, JSONBuilder jsonBuilder) {
            jsonBuilder.object();
            return addUserSpecificationProperties(user, jsonBuilder).endObject();
    }

    private static JSONBuilder addUserSpecificationProperties(User user, JSONBuilder jsonBuilder) {
            return jsonBuilder.key("username").value(user.username())
                .key("hasDefaultPhoto").value(user.hasDefaultPhoto()).key("serviceProviderProfileID")
                            .value(user.serviceProviderProfileID());
    }
        /**
         *
         * @param wasReset
         * @param rememberMeCookie null if not reset or if "remember me" not selected
         * @param user null if not reset
         * @return
         */

    public static String assemblePasswordResetResponse(boolean wasReset, String rememberMeCookie, User user) {
            JSONStringer jsonStringer = new JSONStringer();
            jsonStringer.object().key("wasReset").value(wasReset);
            if (wasReset) {
                if (rememberMeCookie != null)
                    jsonStringer.key("rememberMeCookie").value(rememberMeCookie);
                jsonStringer.key("user");
                assembleUserProfile(user, jsonStringer);
            }
            return jsonStringer.endObject().toString();
    }

    public static String assembleLocation(Location location) {
            return assembleLocation(location, new JSONStringer()).toString();

    }

    private static JSONBuilder assembleLocation(Location location, JSONBuilder jsonBuilder) {
            return jsonBuilder.object()
                .key("description").value(location.getLocation())
                .key("latitude").value(location.getLatitude())
                .key("longtitude").value(location.getLongitude())
            .endObject();

    }

    public static Location locationFromJSON(JSONObject json) {
            if (json == null)
                return null;
            return new Location(json.getString("description"), null,
                (float)json.getDouble("latitude"), (float)json.getDouble("longtitude"));

    }

    public static String assembleUserProfile(User user) {
            JSONStringer jsonStringer = new JSONStringer();
            assembleUserProfile(user, jsonStringer);
            return jsonStringer.toString();

    }

    public static String assembleEditProfileResponse(boolean changedEmailAddressTaken) {
            return new JSONStringer().object().key("changeEmailAddressTaken")
                .value(changedEmailAddressTaken)
                .endObject().toString();

    }

        /**
         *
         * @param responseType
         * @param user Can be null, depending on responseType.
         */

    public static String assembleLoginResponse(LoginResponseType responseType,
                User user, String rememberMe) {
            JSONStringer jsonStringer = new JSONStringer();
            jsonStringer.object()
                .key("loginResponse").value(responseType.toString());
            if (user != null) {
                jsonStringer.key("userProfile");
                assembleUserProfile(user, jsonStringer);
            }
            if (rememberMe != null)
                jsonStringer.key("rememberMe").value(rememberMe);
            return jsonStringer.endObject().toString();

    }

    public static String assembleSignupResponse(SignupResponseType responseType, User user, String rememberMeCookie) {
            JSONStringer jsonStringer = new JSONStringer();
            jsonStringer.object()
                .key("signupResponse").value(responseType.toString());
            if (user != null) {
                jsonStringer.key("user");
                assembleUserProfile(user, jsonStringer);
            }
            if (rememberMeCookie != null)
                jsonStringer.key("rememberMeCookie").value(rememberMeCookie);
            return jsonStringer.endObject().toString();

    }

    private static void assembleUserProfile(User user, JSONBuilder jsonBuilder) {
            jsonBuilder.object();
            addUserSpecificationProperties(user, jsonBuilder);
            jsonBuilder.key("id").value(user.username());
            jsonBuilder.key("isAdministrator").value(user.isAdministrator());
            jsonBuilder.endObject();

    }

}
