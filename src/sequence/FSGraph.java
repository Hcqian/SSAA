package sequence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
//对图深度优先遍历
public class FSGraph {
	static Map<String, Set<String>> adj;
	public static void main(String[] arg){
		adj=new HashMap<>();//图的邻接表
		FSGraph f=new FSGraph();
		f.putGraph("D:/graph.dot");
	}
	public void putGraph(String path){
		try {
			BufferedReader bf=new BufferedReader(new FileReader(path));
			bf.readLine();
			bf.readLine();
			String str=bf.readLine();
			while(str.length()>0){
				str=str.trim();
				if(str.contains("->")){
					String[] ss=str.split(" ");
					adj.get(ss[0]).add(ss[2]);
				}else {
					adj.put(str.split(" ")[0], new HashSet<>());
				}
				str=bf.readLine();
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
