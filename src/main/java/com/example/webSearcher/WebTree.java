package com.example.webSearcher;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;
import com.example.demo.*;

public class WebTree {
	public WebNode root;
	public int count = 0;
	
	public WebTree(WebPage rootPage){
		this.root = new WebNode(rootPage);
	}
	
	 
	public void setPostOrderScore(KeywordList keys) throws IOException{
		//setPostOrderScore(this.root, keys);

	}
	

	public void setPostOrderScore(WebNode startNode) throws IOException{
		ArrayList keywords = new ArrayList<Keyword>();
			keywords.add(new Keyword("動畫",300));
			keywords.add(new Keyword("動畫",300));
			keywords.add(new Keyword("蠟筆小新",50));
			keywords.add(new Keyword("野原",50));
			keywords.add(new Keyword("新之助", 50));
			keywords.add(new Keyword("美冴",50));
			keywords.add(new Keyword("廣志",50));
			keywords.add(new Keyword("小白",40));
			keywords.add(new Keyword("風間",40));
			keywords.add(new Keyword("雙葉幼稚園", 40));
			keywords.add(new Keyword("臼井儀人", 30));
			keywords.add(new Keyword("實況主", -100));
	
		//1. compute the score of children nodes postorder
		for(WebNode child : startNode.children){
			setPostOrderScore(child);
			
		}
		//**setNode score of startNode
			startNode.setNodeScore(keywords);
		}
	
	public void eularPrintTree(){
		eularPrintTree(root);
	}
	
	private void eularPrintTree(WebNode startNode){
		int nodeDepth = startNode.getDepth();
		
		if(nodeDepth > 1) System.out.print("\n" + repeat("\t", nodeDepth-1));
		//print "("
		System.out.print("(");
		//print "name","score"
		System.out.print(startNode.webPage.name+","+startNode.nodeScore);
		
		//2.print child preorder
		for(WebNode child : startNode.children){
			eularPrintTree(child);
		}
		
		//print ")"
		System.out.print(")");
		
		/*for example
		(Soslab,459.0
				(Publication,286.2)
				(Projects,42.0
						(Stranger,0.0)
				)
				(MEMBER,12.0)
				(Course,5.3999999999999995)
		)
		*/
		if(startNode.isTheLastChild()) System.out.print("\n" + repeat("\t", nodeDepth-2));
		
	}
//	eularPrintTree{
//		print=> "(startNode.webPage.name , startNode.nodeScore"
//		
//		//preorder
//		for(WebNode child : startNode.children){
//			eularPrintTree(child);
//		}
	
//		print=> ")"
//		
//	}
	
	
//	do {
//		print=> "(startNode.webPage.name , startNode.nodeScore"
//	}
//	while(WebNode child : startNode.children) {
//		print=> "(startNode.webPage.name , startNode.nodeScore"
//		
//	}
//	print=> ")"

	
	
	private String repeat(String str,int repeat){
		String retVal  = "";
		for(int i=0;i<repeat;i++){
			retVal+=str;
		}
		return retVal;
	}
}