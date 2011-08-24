package com.pdmaf.business.models;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 15, 2009
 * Time: 6:43:22 AM
 */
public class Location {

	private String location;
	private String countryCode;
	private float latitude;
	private float longitude;

	public Location() {}

	public Location(String location, String countryCode, float latitude, float longitude) {
		this.location = location;
		this.countryCode = countryCode;;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
}
