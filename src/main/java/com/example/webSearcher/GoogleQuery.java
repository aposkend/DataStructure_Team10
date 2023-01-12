package com.example.webSearcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery {

	public String searchKeyword;
	public String url;
	public String content;
	public ArrayList<String> relatedSearches = new ArrayList<String>();

	public GoogleQuery(String searchKeyword){
		this.searchKeyword = searchKeyword;
		this.url = "http://www.google.com/search?q="+searchKeyword+"拉麵"+"台北"+"&oe=utf8&num=5";
	}

	

	private String fetchContent() throws IOException{
		//HttpURLConnection urlConnection = null;
		String retVal = "";
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");
		int status = conn.getResponseCode();
		if(status != HttpURLConnection.HTTP_OK){
			System.out.println("page 404 and cannot fetch content");
			return "0";
		}else{
			InputStream in = conn.getInputStream();
			InputStreamReader inReader = new InputStreamReader(in,"utf-8");
			BufferedReader bufReader = new BufferedReader(inReader);
			String line = null;
			while((line=bufReader.readLine())!=null){
				retVal += line;
			}
			return retVal;
		}
	}

	public HashMap<String, String> query() throws IOException{
		if(content==null){
			content= fetchContent();
		}
		HashMap<String, String> retVal = new HashMap<String, String>();
		Document doc = Jsoup.parse(content);
		Elements lis = doc.select("div");
		lis = lis.select(".kCrYT");
		for(Element li : lis){
			try {
				String citeUrl = li.select("a").get(0).attr("href");
				String title = li.select("a").get(0).select(".vvjwJb").text();
				if(title == null) continue;
				if(title.equals("")) {
					continue;
				}
				if(citeUrl.contains("&")) {
						citeUrl = citeUrl.substring(7, citeUrl.indexOf("&"));
				}
				else if(citeUrl.contains("%")) {
						citeUrl = citeUrl.substring(7, citeUrl.indexOf("%"));
				}		
				System.out.println(title + ","+citeUrl);
				if(citeUrl.startsWith("https://tw.tech.yahoo.com/news")) continue;
				citeUrl = URLDecoder.decode(citeUrl, "UTF-8");
				retVal.put(title, citeUrl);
			} catch (IndexOutOfBoundsException e) {
				System.out.println("index out of bounds when crawling");
				//e.printStackTrace();
			}
		}
		return retVal;
	}

}
