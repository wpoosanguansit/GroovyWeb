package com.pdmaf.ui.gwt.client.utils;

import com.google.gwt.user.client.Cookies;
import com.pdmaf.ui.gwt.client.enums.CookieConstants;
import com.pdmaf.ui.gwt.client.json.UserProfile;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 13, 2009
 * Time: 2:09:42 AM
 */
public class CookieHelper {

	public static void setRememberMeCookie(String cookieValue) {
		Cookies.setCookie(CookieConstants.REM_COOKIE_NAME,
			cookieValue, expiresDate(),
			PageJSInterface.COOKIEDOMAIN(), "/", false);
	}

	public static void removeRememberMeCookie() {
		Cookies.removeCookie(CookieConstants.REM_COOKIE_NAME);
	}

	private static Date expiresDate() {
		return new Date(System.currentTimeMillis() +
			CookieConstants.REM_COOKIE_DURATION);
	}
}