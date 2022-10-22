package com.example.webSearcher;

import java.io.IOException;
import java.util.ArrayList;

public class WebPage {
	public String url;
	public String name;
	public WordCounter counter = new WordCounter(url);
	public double score = 0;
	
	public WebPage(String url,String name){
		this.url = url;
		this.name = name;
		this.counter = new WordCounter(url);	
	}
	
	public void setScore(ArrayList<Keyword> keywords) throws IOException{
		score = 0;
//		3.calculate score
		for(Keyword k : keywords){		
			score += k.weight*counter.countKeyword(k.name);	
		}
	}
	
}