package com.example.webSearcher;
import java.io.IOException;
import java.util.ArrayList;

public class WebNode {
	public WebNode parent;
	public ArrayList<WebNode> children;
	public WebPage webPage;	//child element
	public double nodeScore;//main element This node's score += all its children¡¦s nodeScore
	
	public WebNode(WebPage webPage){
		this.webPage = webPage;
		this.children = new ArrayList<WebNode>();
	}
	
	public void setNodeScore(KeywordList keywords) throws IOException{
		webPage.setScore();
		nodeScore = webPage.score;
		for(WebNode child : children){
			nodeScore += child.nodeScore;
		}			
	}

	public void addChild(WebNode child){
		//add the WebNode to its children list
		this.children.add(child);
		child.parent = this;
	}
}