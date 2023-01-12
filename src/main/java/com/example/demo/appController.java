package com.example.demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import com.example.webSearcher.*;
import com.fasterxml.jackson.core.format.InputAccessor;

import java.util.ArrayList;

@Controller
public class appController {

    private ArrayList<String> relativeWords = new ArrayList<String>();
    private KeywordList keys =  new KeywordList();
    private ArrayList<WebTree> resultList = new ArrayList<WebTree>();
    private ArrayList<Thread> ttList = new ArrayList<Thread>(); 
    private ArrayList<buildTree> btList = new ArrayList<buildTree>();


    
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("Text", new Text());
        initialize();
        System.out.println("你好");
        return "home";
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String start(Text text, Model model){
        return "hello";
    }

    @PostMapping("/hello")
    public String hello(Text text, Model model) throws UnsupportedEncodingException {
        initialize();
        resultList = new ArrayList<WebTree>();
        HashMap<String,String> name;
       // String input = new String(text.getText().getBytes("ISO-8859-1"),"UTF-8");
       // System.out.println(input);
        name = googleSearch(text.getText());
        if(buildTree(name)){
            System.out.println("Start sorting");
            selectionSort();
        }
        for(WebTree result : resultList){
            System.out.println(result.root.nodeScore);
        }

        model.addAttribute("resultList", resultList); // （變數名稱，變數值)
        model.addAttribute("relativeWords", relativeWords);
        return "hello";
    }

    @GetMapping("/hello/{input}")
    public String hello(@PathVariable("input") String input,Model model) throws UnsupportedEncodingException {
        initialize();
        resultList = new ArrayList<WebTree>();
        HashMap<String,String> name;
       // String input = new String(text.getText().getBytes("ISO-8859-1"),"UTF-8");
       // System.out.println(input);
        name = googleSearch(input);
        if(buildTree(name)){
            System.out.println("Start sorting");
            selectionSort();
        }
        for(WebTree result : resultList){
            System.out.println(result.root.nodeScore);
        }

        model.addAttribute("resultList", resultList); // （變數名稱，變數值)
        model.addAttribute("relativeWords", relativeWords);
        return "hello";
    }


    public boolean buildTree(HashMap<String,String> websites){
    if(websites !=null){
        for(String web : websites.keySet()){
            buildTree bTree = new buildTree(websites.get(web),web);
            btList.add(bTree);
            var tt = new Thread(bTree);
            ttList.add(tt);

            /* 
            WebPage p = new WebPage(websites.get(web),web);
            var pageThread = new Thread(p);
            pageThread.start();
            /* 
            try {
                p.setScore();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
            WebTree t = new WebTree(p);
            //Query
            System.out.println("first Layer: "+websites.get(web));

            HashMap <String,String>websites2 = doQuery(websites.get(web));
            if(websites2 != null){
                for(String url2: websites2.keySet()){
                    if(url2.equals(websites.get(web))) continue;
                    WebPage p2 = new WebPage(url2, websites2.get(url2));
                    var p2Thread = new Thread(p2);
                    p2Thread.start();

                    System.out.println("Second Layer: "+ url2);
                    /* 
                    try {
                        p2.setScore();
                        System.out.println("setScore of " + url2);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                    WebNode node2 = new WebNode(p2);
                    t.root.addChild(node2);
                }
            }

            //Score
            try{
              t.setPostOrderScore(this.keys);
              resultList.add(t);
            }catch(IOException e) {
              e.printStackTrace();
           }
            System.out.println("next round");
            */
        
        }
        for(int i = 0; i < ttList.size(); i++){
            ttList.get(i).start();
        }


        for (Thread element : ttList) {
            try {
                element.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        
        String titles = new String("");
        for(int i = 0; i < btList.size(); i++){
            resultList.add(btList.get(i).getTree());
            titles += btList.get(i).getTree().root.webPage.name;
        }
        find(titles);

    }   
    /* 
        for(Thread tt : ttList){
            tt.join();
            resultList.ad(t.getTree());
        }
        */
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
        Query Query = new Query(url);
        try {
            return Query.query();
        } catch (IOException e) {
            e.printStackTrace();
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


    private KeywordList initialize(){
        resultList = new ArrayList<WebTree>();
        ttList = new ArrayList<Thread>(); 
        btList = new ArrayList<buildTree>();
        relativeWords = new ArrayList<String>();
        return this.keys;
    }

    private void find(String s){
       // HashMap<Integer,Integer> result = new HAspMap<Integer,Integer>();//wa value and max index
		String[] lst = {"博多拉麵","豚骨拉麵","味噌拉麵","拉麵麵條","拉麵湯","拉麵店","拉麵吃法",
                        "醬燒拉麵","海鮮拉麵","特製拉麵","拉麵菜單","牛肉拉麵","長生塩人","老虎拉麵","一蘭拉麵",
                        "鷹流東京醬油拉麵","麵屋一燈","隱家拉麵","真劍拉麵","拉麵公子","鳥人拉麵","麵屋武藏","花月嵐拉麵",
                        "屯京拉麵","鬼金棒拉麵","台北拉麵","叉燒","激辣","麵條"
                    };
        int[] counts = new int[lst.length];
		for(int i = 0; i < lst.length; i++){
			int lcs = findLCS(lst[i], s);
            counts[i] = lcs/lst[i].length();
		}

        int max = counts[0];
        int index = 0;
        for(int i = 0; i < 5; i++){
            for(int j = 1; j < counts.length; j++){
                if(counts[j] >= max){
                    max = counts[j];
                    index = j;
                }
            }
            relativeWords.add(lst[index]);
            counts[index] = -1;
            max = counts[0];
        }
		//System.out.println(s + ": " + lst[maxIndex].toString());
	}

    private int findLCS(String x, String y){
		//1. Implement the LCS algorithm
			int[][] num = new int[x.length()+1][y.length()+1];
			for(int i = 0; i < x.length()+1; i++) num[i][0] = 0;
			for(int j = 0; j < y.length()+1; j++) num[0][j] = 0;
			
			for(int i = 1; i < x.length()+1; i ++) {
				for(int j = 1; j < y.length()+1; j ++) {
					if(x.charAt(i-1) == y.charAt(j-1)) {
						num[i][j] = num[i-1][j-1] + 1;
					}else {
						num[i][j] = Math.max(num[i][j-1], num[i-1][j]);
					}
				}
			}

		return num[x.length()][y.length()]; 
	}

}

/*
 * 博多拉麵
 * 豚骨拉麵
 * 味噌拉麵
 * 拉麵麵條
 * 拉麵湯
 * 拉麵店
 * 拉麵
 * 拉麵吃法
 * 味玉拉麵
 * 醬燒拉麵
 * 海鮮拉麵
 * 特製拉麵
 * 拉麵菜單
 * 牛肉拉麵
 * 長生塩人 
 * 老虎拉麵
 * 一蘭拉麵
 * 鷹流東京醬油拉麵 
 * 麵屋一燈
 * 隱家拉麵
 * 真劍拉麵
 * 拉麵公子 
 * 鳥人拉麵
 * 麵屋武藏 
 * 花月嵐拉麵
 * 屯京拉麵
 * 鬼金棒拉麵
 */