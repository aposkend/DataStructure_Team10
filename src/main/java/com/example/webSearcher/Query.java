package com.example.webSearcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Query {

	public String url;
	public String content;
	public int count = 0;

	public Query(String url){
		this.url = url;
	}

	private String fetchContent() throws IOException{
		//HttpURLConnection urlConnection = null;
		String retVal = "";
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");
		conn.setReadTimeout(5000);
		conn.setConnectTimeout(5000);
	try{
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
	}catch(SocketTimeoutException e){
			System.out.println("Connection is timeout");
		}	
		return retVal;
		
	}

	public HashMap<String, String> query() throws IOException{
		if(content==null){
			content= fetchContent();
		}
		 
		if(content.length() > 18000){
			content = content.substring(0, 18000);
		}
		
        HashMap<String, String> retVal = new HashMap<String, String>();
        Document doc = Jsoup.parse(content);
		Elements lis = doc.select("a");
		for(Element li : lis){
			if(count == 4){
				return retVal;
			}
			try {
				String citeUrl = li.attr("href");
				//System.out.println("citeurl = "+ citeUrl);
				if(citeUrl.startsWith("https://")) {
					if(citeUrl.contains("&")) {
						citeUrl = citeUrl.substring(0, citeUrl.indexOf("&"));
					}
					else if(citeUrl.contains("%")) {
						citeUrl = citeUrl.substring(0, citeUrl.indexOf("%"));
					}
					retVal.put(citeUrl,""+count);
					count++;
				}				
			} 
			
			catch (IndexOutOfBoundsException e) 
			{
				e.printStackTrace();
			}
		}
		
		return retVal;
	}
}