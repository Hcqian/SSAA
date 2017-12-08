package sequence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReSequence {
//–Ú¡–»•÷ÿ
	public ReSequence(String path) throws IOException{
		List<String> list=new ArrayList<>();
		BufferedReader read=new BufferedReader(new FileReader(path));
		String node=read.readLine();
		while (node!=null) {
			if(node.startsWith("E")){
				list.add(node.substring(1));
			}
			node=read.readLine();
		}
		System.out.println(list.size());
		List<String>  freashlist=new ArrayList<>();
		freashlist.add(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			if(list.get(i).equals(list.get(i-1))) continue;
			freashlist.add(list.get(i));
		}
		System.out.println(freashlist.size());
	}
}
