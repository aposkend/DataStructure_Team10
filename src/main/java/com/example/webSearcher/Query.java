package com.example.webSearcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
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
/* 
		HashMap<String, String> retVal = new HashMap<String, String>();
		Document doc = Jsoup.parse(content);
//		System.out.println(doc.text());
		Elements lis = doc.select("a");
//		 System.out.println(lis);
		//lis = lis.select("herf");
//		 System.out.println(lis.size());
        for (Element link : lis) {
            try{
             String foundUrl = link.attr("abs:href").toLowerCase();
            if(foundUrl != "") {
                retVal.put(foundUrl, "test");
            }
		}catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
		return retVal;
*/
}



/*
 * Document doc = Jsoup.connect(url).get();
    Elements links = doc.select("a");

    for (Element link : links) {
        String foundUrl = link.attr("abs:href").toLowerCase();

        if( isSuitable(foundUrl) && ( !hs.contains(foundUrl) ) ) {
            hs.add(foundUrl);
            crawl(foundUrl);
        }
    }
 * 
 * 
 * 
 * private void crawler() throws IOException {
    while (!q.isEmpty()){
        String link  = q.remove();
        System.out.println("------"+link);
        Document doc = Jsoup.connect(link).ignoreContentType(true).timeout(0).get();
        if(doc.text().contains("publicly intoxicated behavior or persistence")){
            System.out.println("************ On this page ******************");
            System.out.println(doc.text());
            return;
        }
        Elements links = doc.select("a[href]");
        for (Element link1 : links){ 
            String absUrl = link1.attr("abs:href");
            if (absUrl == null || absUrl.length() == 0) {
                continue;
            }
//          System.out.println(absUrl);
            q.add(absUrl);
        }
    }
}
 */