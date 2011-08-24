package com.pdmaf.ui.gwt.client.enums;

import java.util.EnumSet;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 14, 2009
 * Time: 9:05:47 PM
 */

public enum Language {
	ENGLISH(1, true, "English", "en"),
	GERMAN(2, true, "Deutsch", "de"),
	SPANISH(3, true, "Español", "es"),
	FRENCH(4, true, "Français", "fr"),
	ITALIAN(5, true, "Italiano", "it"),
	CHINESE(6, false, "??", "zh"),
	JAPANESE(7, false, "???", "ja"),
	RUSSIAN(8, true, "???????", "ru"),
	CZECH(9, true, "?eský", "cs");

	public static final EnumSet<Language> withContent =
		EnumSet.of(Language.ENGLISH, Language.GERMAN,
			Language.SPANISH, Language.FRENCH,
			Language.ITALIAN, Language.RUSSIAN);

	private int id;
	private boolean hasStemmer;
	private String displayName;
	private String abbreviation;

	private Language(int id, boolean hasStemmer, String displayName, String abbreviation) {
		this.id = id;
		this.hasStemmer = hasStemmer;
		this.displayName = displayName;
		this.abbreviation = abbreviation;
	}
	public int getId() {
		return id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public String getEnglishDisplayName() {
		String str = toString();
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public static Language language(int id) {
		EnumSet<Language> all = EnumSet.allOf(Language.class);
		for (Language l : all)
			if (l.id == id)
				return l;
		return null;
	}
	public boolean hasStemmer(){
		return hasStemmer;
	}
	public static Language forEnglishNameIgnoreCase(String languageName) {
		for (Language language : Language.values()) {
			if (language.toString().equalsIgnoreCase(languageName))
				return language;
		}
		return null;
	}
	public static Language forAbbreviation(String abbreviation) {
		for (Language language : Language.values())
			if (language.getAbbreviation().equalsIgnoreCase(abbreviation))
				return language;
		return null;
	}
}
