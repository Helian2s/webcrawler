package com.vrogovskiy.webcrawler;

import java.util.List;
import java.util.Map;

import org.jsoup.select.Elements;

public interface WebCrawlerListener {
	
	public static String NAME_ID = "name";
	public static String SPECIALITY_ID = "speciality";
	public static String EMAIL_ID = "email";
	public static String CITY_ID = "city";
	
	List<Teacher> postProcessing(Map<String, Elements> elements);

}
