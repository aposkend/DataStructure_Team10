package com.example.webSearcher;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;
import com.example.webSearcher.*;

public class WebTree {
	private KeywordList keys;
	public WebNode root;
	public int count = 0;
	
	public WebTree(WebPage rootPage){
		this.root = new WebNode(rootPage);
		System.out.println("tree is constructed");
	}
	
	 
	public void setPostOrderScore(KeywordList keys) throws IOException{
		//setPostOrderScore(this.root, keys);
		this.keys = keys;
		setPostOrderScore(root);
	}
	

	public void setPostOrderScore(WebNode startNode) throws IOException{
	
		//1. compute the score of children nodes postorder
		for(WebNode child : startNode.children){
			setPostOrderScore(child);
			
		}
		//**setNode score of startNode
			startNode.setNodeScore(keys);
		}
}