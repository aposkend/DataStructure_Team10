package com.example.demo;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import com.example.webSearcher.*;
import java.util.ArrayList;

@Controller
public class appController {

    private KeywordList keys =  new KeywordList();
    private ArrayList<WebTree> resultList = new ArrayList<WebTree>();
    private ArrayList<Thread> ttList = new ArrayList<Thread>(); 
    private ArrayList<buildTree> btList = new ArrayList<buildTree>();

    
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("Text", new Text());
        initialize();
        return "home";
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String start(Text text, Model model){
        return "hello";
    }

    @PostMapping("/hello")
    public String hello(Text text, Model model) {
        initialize();
        resultList = new ArrayList<WebTree>();
        HashMap<String,String> name;
        name = googleSearch(text.getText());
        if(buildTree(name)){
            System.out.println("Start sorting");
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

        

        for(int i = 0; i < btList.size(); i++){
            resultList.add(btList.get(i).getTree());
        }


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
        return this.keys;
    }
}
