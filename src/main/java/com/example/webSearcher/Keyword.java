package com.example.webSearcher;

public class Keyword {
	public String name;
	public double weight = 0;
	public int count = 0;
	
	public Keyword(String name,double weight){
		this.name = name;
		this.weight = weight;
	}
	
	public String toString(){
		return "["+name+","+weight+"]";
	}
}