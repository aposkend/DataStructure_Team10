package com.example.webSearcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

public class buildTree implements Runnable{
    private String websites;
    private String web; 
    private KeywordList keys = new KeywordList();
    private WebTree resultList;

    public buildTree(String websites, String web){
            this.websites = websites;
            this.web = web;
    }
    
    public void run(){
            WebPage p = new WebPage(websites,web);
            var pageThread = new Thread(p);
            pageThread.start();
            WebTree t = new WebTree(p);
            System.out.println("first Layer: "+websites);
            HashMap <String,String>websites2 = doQuery(websites);
            if(websites2 != null){
                for(String url2: websites2.keySet()){
                    if(url2.equals(websites)) continue;
                    WebPage p2 = new WebPage(url2, websites2.get(url2));
                    var p2Thread = new Thread(p2);
                    p2Thread.start();
                    System.out.println("Second Layer: "+ url2);
                    WebNode node2 = new WebNode(p2);
                    t.root.addChild(node2);
                }
            } try {
                t.setPostOrderScore(this.keys);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            resultList = t;
            System.out.println("next round");
    }

    public HashMap<String,String> doQuery(String url){
        Query Query = new Query(url);
        try {
            return Query.query();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WebTree getTree(){
        return resultList;
    }

    
}
