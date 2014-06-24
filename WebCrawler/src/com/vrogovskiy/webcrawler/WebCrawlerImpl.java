package com.vrogovskiy.webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class WebCrawlerImpl implements WebCrawler {

	private List<String> allowedURLList;
	private Map<String, Boolean> URLList;
	private List<Teacher> teacherList;
	private WebCrawlerListener webCrawlerListener;
	private String nameQuery;
	private String specialityQuery;
	private String emailQuery;
	private String cityQuery;

	WebCrawlerImpl(WebCrawlerListener webCrawList, String nameQ, String specQ,
			String emailQ, String cityQ, String entryURL) {
		allowedURLList = new ArrayList<String>();
		URLList = new HashMap<String, Boolean>();
		teacherList = new ArrayList<Teacher>();
		webCrawlerListener = webCrawList;
		nameQuery = nameQ;
		specialityQuery = specQ;
		emailQuery = emailQ;
		cityQuery = cityQ;
		URLList.put(entryURL, false);
	}

	public void addAllowedURL(String URL) {
		allowedURLList.add(URL);
	}

	private synchronized void addURL(String url) {
		boolean isAllowed = false;
		for (String allowedURL : allowedURLList) {
			if (url.contains(allowedURL)) {
				isAllowed = true;
				break;
			}
		}
		if (isAllowed) {
			if (!URLList.containsKey(url)) {
				URLList.put(url, false);
			}
		}
	}

	public void run() {
		while (URLList.containsValue(false) && true) {
			String url = getRawURL();
			try {
				processURL(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private synchronized String getRawURL() {
		Iterator<Map.Entry<String, Boolean>> iterator = URLList.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Boolean> entry = iterator.next();
			if (entry.getValue().equals(false)) {
				String rawURL = entry.getKey();
				URLList.put(rawURL, true);
				return rawURL;
			}
		}
		return null;
	}

	private void processURL(String url) throws IOException {

		System.out.println(URLList.size());

		Map<String, Elements> teacherElements = new HashMap<String, Elements>();

		Document documentObject = Jsoup.connect(url).get();

		if (nameQuery != null) {
			Elements nameElement = documentObject.select(nameQuery);
			teacherElements.put(WebCrawlerListener.NAME_ID, nameElement);
		}

		if (specialityQuery != null) {
			Elements specialtyElement = documentObject.select(specialityQuery);
			teacherElements.put(WebCrawlerListener.SPECIALITY_ID,
					specialtyElement);
		}

		if (emailQuery != null) {
			Elements emailElement = documentObject.select(emailQuery);
			teacherElements.put(WebCrawlerListener.EMAIL_ID, emailElement);
		}

		if (cityQuery != null) {
			Elements cityElement = documentObject.select(cityQuery);
			teacherElements.put(WebCrawlerListener.CITY_ID, cityElement);
		}

		List<Teacher> teacherlst = webCrawlerListener.postProcessing(teacherElements);
		if (teacherlst != null) {
			for(Teacher tcr : teacherlst){
				if (!teacherList.contains(tcr)) {
					teacherList.add(tcr);
				}
			}
		}

		Elements linksElements = documentObject.select("a[href]");
		for (Element link : linksElements) {
			addURL(link.attr("abs:href"));
		}
	}

	public List<Teacher> getTeacherList() {
		return teacherList;
	}

}
