package com.vrogovskiy.webcrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Obrazovanie66Listener implements WebCrawlerListener {
	public List<Teacher> postProcessing(Map<String, Elements> elements) {
		List<Teacher> teacherList = new ArrayList<Teacher>();
		
		Elements tableElements = elements.get(WebCrawlerListener.NAME_ID);
		for(int i = 0; i<tableElements.size();i=i+4){
			teacherList.add(getTeacher(tableElements.get(i), tableElements.get(i+1)));
		}
		
		return teacherList;
	}
	
	private Teacher getTeacher(Element name, Element details){
		Teacher teacher = new Teacher();
		teacher.setName(name.getAllElements().get(1).ownText());
		teacher.setCity("Екатеринбург");
		teacher.setEmail(details.getElementsContainingOwnText("@").get(0).ownText());
		teacher.setSpecialty(details.getElementsContainingOwnText("Предметы").get(0).parent().parent().getAllElements().get(3).ownText());
		return teacher;
	}
}
