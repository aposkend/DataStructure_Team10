package com.example.demo;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.example.webSearcher.*;
import java.util.ArrayList;


@Controller
public class appController {

    private KeywordList keys =  new KeywordList();
    private ArrayList<WebTree> resultList = new ArrayList<WebTree>();

    @GetMapping("/home")
    public String home(@PathVariable String input, Model model) {
        initialize();
        return "hello";
    }



    @GetMapping("/hello/{input}")
    public String hello(@PathVariable String input, Model model) {
        HashMap<String,String> name;
        name = googleSearch(input);
        if(buildTree(name)){
            selectionSort();
        }
        for(WebTree result : resultList){
            System.out.println(result.root.nodeScore);
        }

        model.addAttribute("resultList", resultList); // （變數名稱，變數值)
        return "hello";
    }

    public boolean buildTree(HashMap<String,String> websites){
    if(websites !=null){
        for(String web : websites.keySet()){
            WebPage p = new WebPage(websites.get(web),web);
            try {
                p.setScore(this.keys.arraylist());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            WebTree t = new WebTree(p);
            //Query
            System.out.println(websites.get(web));
            HashMap <String,String>websites2 = doQuery(websites.get(web));
            if(websites2 != null){
                for(String url2: websites2.keySet()){
                    WebPage p2 = new WebPage(websites2.get(url2), url2);
                    try {
                        p2.setScore(this.keys.arraylist());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    WebTree t2 = new WebTree(p2);
                    t.root.addChild(t2.root);
                }
            }
            //Score
            try{
              t.setPostOrderScore(t.root);
              resultList.add(t);
            }catch(IOException e) {
              e.printStackTrace();
            }
        }
    }   
        return true;
    }


    public void selectionSort(){
        for(int i = 0; i < resultList.size(); i ++){
            WebTree maxTree = resultList.get(i);
            for(int j = i+1; j< resultList.size(); j++){
                if(maxTree.root.nodeScore < resultList.get(j).root.nodeScore ){
                    maxTree = resultList.get(j);
                }
            }
            if(maxTree != resultList.get(i)){
                WebTree temp = resultList.get(i);
                int j = resultList.indexOf(maxTree);
                resultList.set(i,maxTree);
                resultList.set(j,temp);
            }
        }
    }
    
    public HashMap<String,String> doQuery(String url){
        GoogleQuery googleQuery = new GoogleQuery(null);
        try {
            return googleQuery.searchHTML(url);
        } catch (IOException e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
        }
        return null;
    }


    public HashMap<String, String> googleSearch(String input){
        GoogleQuery googleQuery = new GoogleQuery(input);
        try {
            return googleQuery.query();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    
    public void addTree(HashMap<String,String> website){
        for(String url : website.keySet()){
            WebPage root = new WebPage(url, website.get(url));		
			WebTree tree = new WebTree(root);
            score(tree);
        }
    }

    public void addNode(WebTree tree){
        /* 
        GoogleQuery google = new GoogleQuery(null);
        WebNode root = tree.root;
        int size = root.children.isEmpty() ? 0: root.children.size();
        while(!root.children.isEmpty()){
            root = root.children.get(3){

            }
        }
        do{
        try {
            HashMap<String, String> result = google.searchHTML(root.webPage.url);
            for(String url: result.keySet()){
               WebNode node = new WebNode(new WebPage(url, result.get(url)));
               root.children.add(node);
               node.parent = root;
               tree.count++;
            }
        } catch (IOException e) {
            System.out.println("no result");
        }
    }while(size != 0);
    */
    }


    public void score(WebTree tree){
      //  WordCounter wordCounter = new WordCounter(tree.root.webPage.url);
        try {
            tree.root.webPage.setScore(this.keys.arraylist());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        }
/* 
        try {
            
           // tree.setPostOrderScore(this.keys);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/

    private void initialize(){
        this.keys.add(new Keyword("動畫",300));
        this.keys.add(new Keyword("動畫",300));
        this.keys.add(new Keyword("蠟筆小新",50));
        this.keys.add(new Keyword("野原",50));
        this.keys.add(new Keyword("新之助", 50));
        this.keys.add(new Keyword("美冴",50));
        this.keys.add(new Keyword("廣志",50));
        this.keys.add(new Keyword("小白",40));
        this.keys.add(new Keyword("風間",40));
        this.keys.add(new Keyword("雙葉幼稚園", 40));
        this.keys.add(new Keyword("臼井儀人", 30));
        this.keys.add(new Keyword("實況主", -100));
    }
}

/*
 * 
 * public WebNode contetQuery(String url) throws IOException{

		if(content==null){
			content= fetchContent();
		}

		WebPage page = new WebPage();
        WebNode node = new WebNode(page);

		this.url = url;
		for(KeywordList:keys){
		}
		while(content.indexOf(keyword) != -1) {
			content = content.substring(content.indexOf(keyword) + keyword.length() -1);
			retVal++;
		}	

		}
		return retVal;


        public String startSearch(String input){
        WordCounter wordCounter = new WordCounter("http://soslab.nccu.edu.tw/Welcome.html");
        int count = 0;
        try {
            count = wordCounter.countKeyword(input);
        } catch (IOException e) {
        
            e.printStackTrace();
        }
        return "" + String.valueOf(count);
    }
 */