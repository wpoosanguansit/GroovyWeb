package com.pdmaf.business.models;

import com.pdmaf.utils.enums.EmailType;

public class Email {
	private EmailType type;
	private String email;
	
	public Email(EmailType type, String email) {
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Email : email argument can not be null.");
		}
		
		this.type = type;
		this.email = checkEmailFormat(email);
	}
	
	public final String email() {
		return this.email.toString();
	}
	
	/**
	 * This is for the lower layer code to call using reflection.
	 * The format that the string comes in will be the same format 
	 * that will be generated from toString.
	 * 
	 * @param email
	 */
	private Email(String email) {
		String[] result = email.split(",");
		this.type = EmailType.valueOf(result[0]);
		this.email = result[1];
	}
	
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(type);
		builder.append(",");
		builder.append(email);
		
		return builder.toString();
	}
	
	private final String checkEmailFormat(String email) {
		//check the format
		return email;
	}
}
