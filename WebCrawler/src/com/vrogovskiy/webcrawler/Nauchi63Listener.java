package com.vrogovskiy.webcrawler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class Nauchi63Listener implements WebCrawlerListener {

	public List<Teacher> postProcessing(Map<String, Elements> elements) {

		String nameStr = null, emailStr = null, specialityStr = null, cityStr = "Самара";

		if (elements.get(WebCrawlerListener.NAME_ID) != null) {
			if(elements.get(WebCrawlerListener.NAME_ID).first()!=null){
				nameStr = elements.get(WebCrawlerListener.NAME_ID).first()
						.ownText();
			}
		}

		if (elements.get(WebCrawlerListener.SPECIALITY_ID) != null) {
			specialityStr = elements.get(WebCrawlerListener.SPECIALITY_ID)
					.first().ownText();
		}

		if (elements.get(WebCrawlerListener.EMAIL_ID) != null) {
			Element emailDiv = elements.get(WebCrawlerListener.EMAIL_ID)
					.first();
			if (emailDiv != null) {
				if (emailDiv.childNodes().size() == 3) {
					Node emailScript = emailDiv.childNodes().get(2);
					try {
						emailStr = new String(
								Base64.getDecoder()
										.decode(emailScript
												.toString()
												.substring(
														63,
														emailScript
																.toString()
																.indexOf(
																		"\')+\'\">\'+base64_decode(\'"))
												.getBytes("UTF-8")));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}

		Teacher teacher = null;
		if (emailStr != null) {
			teacher = new Teacher();
			teacher.setName(nameStr);
			teacher.setCity(cityStr);
			teacher.setSpecialty(specialityStr);
			teacher.setEmail(emailStr);
		}

		List<Teacher> teacherList = new ArrayList<Teacher>();
		teacherList.add(teacher);
		return teacherList;

	}

}
