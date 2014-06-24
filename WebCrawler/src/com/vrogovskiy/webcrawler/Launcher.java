package com.vrogovskiy.webcrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class Launcher {

	public static void main(String[] args) {
		WebCrawler nauchi63webCraw = new WebCrawlerImpl(new Nauchi63Listener(), "h5.fio",
				"p.predmet > a", "div[style].info", null,
				"http://nauchi63.ru/foreign_languages/english_language/");
		nauchi63webCraw.addAllowedURL("http://nauchi63.ru/foreign_languages/english_language/");
		nauchi63webCraw.addAllowedURL("http://nauchi63.ru/repetitors");
		nauchi63webCraw.run();

		File nauchi63outputFile = new File("nauchi63Output.txt");
		try {
			FileOutputStream fos = new FileOutputStream(nauchi63outputFile);
			for (Teacher teacher : nauchi63webCraw.getTeacherList()) {
				if(teacher!=null){
					fos.write(teacher.toString().getBytes("UTF-8"));
					fos.write(System.getProperty("line.separator").getBytes());
				}
			}
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/////////////////////////
		
		WebCrawler obrazovanie66webCraw = new WebCrawlerImpl(new Obrazovanie66Listener(), "table.clear > tbody > tr > td > table > tbody > tr > td[valign] > table[style] > tbody > tr",
				"table[cellpadding]", "table[cellpadding]", null,
				"http://www.obrazovanie66.ru/repetitors.php");
		obrazovanie66webCraw.addAllowedURL("http://www.obrazovanie66.ru/repetitors.php");
		obrazovanie66webCraw.run();

		File obrazovanie66outputFile = new File("obrazovanie66Output.txt");
		try {
			FileOutputStream fos = new FileOutputStream(obrazovanie66outputFile);
			for (Teacher teacher : obrazovanie66webCraw.getTeacherList()) {
				fos.write(teacher.toString().getBytes("UTF-8"));
				fos.write(System.getProperty("line.separator").getBytes());
			}
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
