package com.example.webSearcher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;

public class WordCounter implements Runnable{
	public String urlStr;
    private String content;
	private int score;
	private String keyword;

	public WordCounter(String urlStr) {
        this.urlStr = urlStr;
    }

    public WordCounter(String urlStr, String keyword) {
        this.urlStr = urlStr;
		this.keyword = keyword;
    }

	public void run(){
		try {
			this.countKeyword(this.keyword);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public String fetchContent() throws IOException{
		int count = 0;
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");
		conn.setReadTimeout(10000);
		conn.setConnectTimeout(10000);
		System.out.println("connection");
		try{
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		System.out.println("reading..." + this.urlStr);
		String retVal = "";
	
		String line = null;
		
		while ((line = br.readLine()) != null && count <= 10000){
			count++;
		    retVal = retVal + line + "\n";
		}
		System.out.println("finish content fetch");
		//System.out.println(retVal);
		return Jsoup.parse(retVal).text();

	}catch(IOException e){
		
	}
	return "";
    }
	

	public int countKeyword(String keyword) throws IOException{
		if (content == null){
		    content = fetchContent();
			if(content.length() >30000){
				content = content.substring(0, 30000);
			}
		}
	
		int retVal = 0; 
		
		while(content.indexOf(keyword) != -1) {
			content = content.substring(content.indexOf(keyword) + keyword.length() -1);
			retVal++;
		}
		System.out.println("count keyword finish");
		this.score = retVal;
		return retVal;
    }

	public int getScore(){
		return this.score;
	}
}