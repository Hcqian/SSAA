package sequence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DotToTxt {
	public static void main(String[] arg){
		DotToTxt g=new DotToTxt("D:/graph.dot");
	}
	
	public DotToTxt(String filepath) {
		List<String> node=new ArrayList<>();
		int i=0;
		try {
			BufferedReader file=new BufferedReader(new FileReader(filepath));
			BufferedWriter wfile=new BufferedWriter(new FileWriter("D:/789.txt"));
			String m=file.readLine();
			m=file.readLine();
			m=file.readLine();
			node.add("hcq");
			while(m!=null){
				if(m.contains("->")){
					m=m.trim();
					String[] ss=m.split(" ");
				wfile.write(node.indexOf(ss[0])+":"+node.indexOf(ss[2]));
				wfile.newLine();
				}else {
					m=m.trim();
					node.add(m.split(" ")[0]);
				}
				m=file.readLine();
			}
			wfile.flush();
			wfile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
