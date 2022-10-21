package com.example.demo;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import com.example.webSearcher.WebSearcher;


@Controller
public class appController {
    @GetMapping("/hello/{input}")
    public String hello(@PathVariable String input, Model model) {
        String name = input;
        name = startSearch(name);
        model.addAttribute("hello", name); // （變數名稱，變數值)
        return "hello";
    }

    public String startSearch(String input){
        WebSearcher webSearcher = new WebSearcher("http://soslab.nccu.edu.tw/Welcome.html");
        int count = 0;
        try {
            count = webSearcher.countKeyword(input);
        } catch (IOException e) {
        
            e.printStackTrace();
        }
        return "" + String.valueOf(count);
    }
}