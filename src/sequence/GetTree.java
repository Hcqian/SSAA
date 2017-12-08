package sequence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import analysis.TreeNode;

public class GetTree {
	private List<String> list=new ArrayList<>();//存放序列
	private List<String> resquence=new ArrayList<>();//存放新序列
//	private List<String> changeNum=new ArrayList<>();//函数名与数字转换
	private int i=0;//标记list的位置
	public static void main(String[] arg) throws IOException{
		GetTree g=new GetTree();
		g.getTree("D:/sequence.txt");//把序列存入list
		//g.len= g.list.size();
		TreeNode t=new TreeNode(g.list.get(g.i++).substring(1));
		g.makeTree(t);
		g.searchTree(t);
		//函数名转换数字;
//		for (int i = 0; i < g.list.size(); i++) {
//			String s=g.list.get(i);
//			if(s.startsWith("X")) continue;
//			if(!g.changeNum.contains(s.substring(1)))
//				g.changeNum.add(s.substring(1));
//		}
		//把数字化名输出
//		BufferedWriter o=new BufferedWriter(new FileWriter("D://shuzi.txt"));
//		for (int i = 0; i < g.changeNum.size(); i++) {
//			o.write(g.changeNum.get(i)+":"+i);
//			o.newLine();
//		}
//		o.flush();
//		o.close();
		//显示数字时间序列
//		for (int i = 0; i < g.list.size(); i++) {
//			String s=g.list.get(i);
//			if(s.startsWith("X")) continue;
//			System.out.println(g.changeNum.indexOf(s.substring(1)));
//		}
		//去重复
//		List<String> s=new ArrayList<>();
//		for (int i = 1; i <g.resquence.size(); i++) {
//			if(g.resquence.get(i).equals(g.resquence.get(i-1))) continue;
//			System.out.println(g.resquence.get(i));
//			s.add(g.resquence.get(i));
//		}
//		System.out.println(s.size());
//		System.out.println(g.resquence.size());
//		g.matchTree(t,"D:/data/遍历序列.txt");
		g.ouputTree("D:/tree.txt", t);
	}
	//先序遍历树结构
	public void searchTree(TreeNode t){
//		if(t!=null) resquence.add(t.getName());
		int size=0;
		for (TreeNode node:t.getChildren()) {
			searchTree(node);
			t.allChld.add(node.getName());
			t.allChld.addAll(node.allChld);
		}
	}
	//两棵树的对比
	public void matchTree(TreeNode t,String path){
		try {
			BufferedReader f=new BufferedReader(new FileReader(path));
			String c=f.readLine();
			int allnum=0;
			int error=0;
			TreeNode temp=new TreeNode("");
			while(c!=null){
				TreeNode head=t;
				allnum++;
				String[] strs=c.split(" ");
				for (int i = 1; i < strs.length; i++) {
					temp.setName(strs[i]);
					List<TreeNode> l=head.getChildren();
					int index=l.indexOf(temp);
					if(index==-1){
						error++;
						break;
					}else {
						head=l.get(index);
					}
				}
				c=f.readLine();
			}
			System.out.println("number:"+allnum+"  error number:"+error);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void ouputTree(String path,TreeNode n){
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
