package com.example.webSearcher;

import java.io.IOException;
import java.util.ArrayList;
import com.example.*;

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
	
	public void setScore(KeywordList keywords) throws IOException{
		//score = 0;
//		3.calculate score
		KeywordList k = new KeywordList();
		k.add(new Keyword("拉麵",500));
        k.add(new Keyword("豚骨",300));
        k.add(new Keyword("叉燒",300));
        k.add(new Keyword("雞白湯",200));
        k.add(new Keyword("醬油", 200));
        k.add(new Keyword("台北",200));
        k.add(new Keyword("排隊",50));
        k.add(new Keyword("好吃",30));
        k.add(new Keyword("湯底",30));
        k.add(new Keyword("激辛", 30));
        k.add(new Keyword("日本", 30));
        k.add(new Keyword("日式", 30));
		k.add(new Keyword("美食", 30));
	//	k.add(new Keyword("湯", 5));
	//	k.add(new Keyword("麵", 5));
		for(Keyword key : k.lst){		
			score += key.weight*counter.countKeyword(key.name);	
		}
		System.out.println("finishing set score");
	}
}