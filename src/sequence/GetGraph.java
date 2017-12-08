package sequence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//对WALA生成的图筛选
public class GetGraph {
	
	public static void main(String[] arg){
		GetGraph g=new GetGraph("D:/temp.dt");
	}
	
	public GetGraph(String filepath) {
		int i=0;
		try {
			BufferedReader file=new BufferedReader(new FileReader(filepath));
			BufferedWriter wfile=new BufferedWriter(new FileWriter("D:/789.txt"));
			String m=file.readLine();
			while(m!=null){
				if(m.contains("test")){
				wfile.write(m);
				wfile.newLine();
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
