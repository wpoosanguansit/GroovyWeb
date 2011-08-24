package com.pdmaf.business.models;

import com.pdmaf.utils.enums.City;
import com.pdmaf.utils.enums.Country;
import com.pdmaf.utils.enums.State;

public class Address {
	private String street;
	private String apartmentnumber;
	private City city;
	private Country country;
	private State state;
	private String zipcode;
	
	public Address(String apartmentnumber, String street, City city, State state, Country country, String zipcode) {
		if (street == null || street.isEmpty() || apartmentnumber == null || apartmentnumber.isEmpty() ||
				zipcode == null || zipcode.isEmpty()) {
			throw new IllegalArgumentException("Address: the arguments can not be null - " +
					" street : " + street + " apartmentnumber : " + apartmentnumber + 
					" city : " + city + " country : " + country + " zipcode : " + zipcode);
		}
		
		this.street = street;
		this.apartmentnumber = checkNumberFormat(apartmentnumber, 8);
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = checkNumberFormat(zipcode, 5);

	}
	
	public final String apartmentnumber() {
		return this.apartmentnumber;
	}
	
	public final String street() {
		return this.street;
	}
	
	public final String city() {
		return this.city();
	}
	
	public final String state() {
		return this.state();
	}
	
	public final String country() {
		return this.country();
	}
	
	public final String zipcode() {
		return this.zipcode;
	}
	
	/**
	 * This is for the lower layer code to call using reflection.
	 * The format that the string comes in will be the same format 
	 * that will be generated from toString.
	 * 
	 * @param email
	 */
	private Address(String address) {
		String[] result = address.split(",");
		this.apartmentnumber = result[0];
		this.street = result[1];
		this.city = City.valueOf(result[2]);
		this.state = State.valueOf(result[3]);
		this.country = Country.valueOf(result[4]);
		this.zipcode = result[5];
	}
	
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(apartmentnumber);
		builder.append(",");
		builder.append(street);
		builder.append(",");
		builder.append(city);
		builder.append(",");
		builder.append(state);
		builder.append(",");
		builder.append(country);
		builder.append(",");
		builder.append(zipcode);
		
		return builder.toString();
	}
	
	private String checkNumberFormat(String text, int length) {
		//do the checking
		return text;
	}
}
