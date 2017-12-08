package analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GetTree {
	private List<String> list=new ArrayList<>();//存放序列
	private int i=0;//标记list的位置
	private Map<String,Set<String>> map=new HashMap<>();//
	static String version="1.3";
	public static void main(String[] arg) throws IOException{
		GetTree g=new GetTree();
		g.TrieTree("D:/data/tar/tar"+version, "D:/tree.txt");
		
	}
	public Map<String,Set<String>> TrieTree(String inputPath,String outPutPath){
		
		File[] f=new File(inputPath).listFiles();
		TreeNode t=null;
		for (File file : f) {
			i=0;
			getTree(file.toString());
			if(t==null)
				t=new TreeNode(list.get(i++).substring(1));
			t.call++;
			makeTree(t);
		}
		searchTree(t);
		if(outPutPath!=null)
		outputTree(outPutPath, t);
	    return map;
	}
	//先序遍历树结构
	public void searchTree(TreeNode t){
		int size=0;
		for (TreeNode node:t.getChildren()) {
			searchTree(node);
			t.allChld.add(node.getName());
			t.allChld.addAll(node.allChld);
			
		}
		Set<String> s=map.get(t.getName());
		if(s==null){
			s=new HashSet<>();
			map.put(t.getName(), s);
		}
			s.addAll(t.allChld);
	}
	public void outputTree(String path,TreeNode n){
		try {
			BufferedWriter o=new BufferedWriter(new FileWriter(path));
			drawTree(n, new StringBuffer(), o);
			o.flush();
			o.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void drawTree(TreeNode n,StringBuffer str,BufferedWriter o) throws IOException{
		
		str.append("---"+n.allChld.size()+"---"+n.getName());
//		str.append(n.getName()+" ");
		if(n.getChildren().size()==0){
			o.write(str.toString());
			o.newLine();
		}
		int len=str.length();
		for (TreeNode s : n.getChildren()) {
			drawTree(s, str, o);
			str.delete(len,str.length());
		}
	}
	//通过序列构造前缀树
	public void makeTree(TreeNode n){
		while(i<list.size()&&list.get(i).startsWith("E")){
		makeTree(n.addChild(list.get(i++).substring(1)));
		}
		i++;
	}
	//把序列存入list
	 public void getTree(String path){
		 list=new ArrayList<>();
		try {
			BufferedReader f=new BufferedReader(new FileReader(path));
			String c=f.readLine();
			while(c!=null){
				list.add(c);
				c=f.readLine();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

}
