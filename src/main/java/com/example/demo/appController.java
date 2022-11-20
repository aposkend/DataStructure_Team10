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
        initialize();
        resultList = new ArrayList<WebTree>();
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
                p.setScore(this.keys);
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
                    if(url2.equals(websites.get(web)))continue;
                    WebPage p2 = new WebPage(url2, websites2.get(url2));
                    System.out.println("Second Layer: "+ url2);
                    try {
                        p2.setScore(this.keys);
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
        return this.keys;
    }
}
