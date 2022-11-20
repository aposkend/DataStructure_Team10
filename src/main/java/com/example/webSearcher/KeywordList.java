package com.example.webSearcher;

import java.util.ArrayList;

public class KeywordList{
    public int size = 0;
	public ArrayList<Keyword> lst;
	
	public KeywordList(){
		this.lst = new ArrayList<Keyword>();
    }
	
	public void add(Keyword keyword){
		lst.add(keyword);
        this.size++;
//		System.out.println("Done");
    }

	public Keyword get(int index){
		return lst.get(index);
	}
}