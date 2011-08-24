package com.pdmaf.business.models;

import com.pdmaf.utils.enums.TelephoneType;

public class Telephone {
	private TelephoneType type;
	private String number;
	
	public Telephone(TelephoneType type, String number) {
		if (number == null || number.isEmpty()) {
			throw new IllegalArgumentException("Telephone: number can not be null.");
		}
		
		this.type = type;
		this.number = checkNumberFormat(number);
	}
	
	/**
	 * This is for the lower layer code to call using reflection.
	 * The format that the string comes in will be the same format 
	 * that will be generated from toString.
	 * 
	 * @param email
	 */
	private Telephone(String telephone) {
		String[] result = telephone.split(",");
		this.type = TelephoneType.valueOf(result[0]);
		this.number = result[1];
	}
		
	public final String type() {
		return this.type.toString();
	}
	
	public final String number() {
		return this.number;
	}
	
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(type);
		builder.append(",");
		builder.append(number);
		
		return builder.toString();
	}

	private final String checkNumberFormat(String text) {
		//do the checking
		return text;
	}
}
