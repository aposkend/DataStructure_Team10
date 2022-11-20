package com.example.webSearcher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WordCounter {
	public String urlStr;
    private String content;

    public WordCounter(String urlStr) {
        this.urlStr = urlStr;
    }

    private String fetchContent() throws IOException{
		int count = 0;
		//5System.out.println("這是"+urlStr);
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");
		System.out.println("connection");
		try{
		InputStream in = conn.getInputStream();
		System.out.println("Stream");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
		String retVal = "";
	
		String line = null;
		
		while ((line = br.readLine()) != null && count < 20){
			count++;
		    retVal = retVal + line + "\n";
		}
		System.out.println("finish content fetch");
		return retVal;
	}catch(IOException e){
		
	}
	return "";
    }
    
    public int countKeyword(String keyword) throws IOException{
		if (content == null){
		    content = fetchContent();
			if(content.length() >10000){
				content = content.substring(0, 10000);
			}
		}

		//if(content == "0") return 0;
		
		//To do a case-insensitive search, we turn the whole content and keyword into upper-case:
	//	content = content.toUpperCase();
		//keyword = keyword.toUpperCase();
	
		int retVal = 0; 

		// 1. calculates appearances of keyword (Bonus: Implement Boyer-Moore Algorithm)
		// 用indexof的版本
		
		while(content.indexOf(keyword) != -1) {
			content = content.substring(content.indexOf(keyword) + keyword.length() -1);
			retVal++;
		}
		System.out.println("count keyword finish");
		return retVal;
    }
}