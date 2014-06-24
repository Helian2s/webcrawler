package com.vrogovskiy.webcrawler;

import java.io.InputStream;
import java.util.List;

public interface WebCrawler {

	void addAllowedURL(String URL);
	void run();
	List<Teacher> getTeacherList();
}
