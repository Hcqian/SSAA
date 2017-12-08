package analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MergeData {
	static Map<String, int[]>  map=new LinkedHashMap();
	
	public void getName(String path) throws IOException{
		BufferedReader read=new BufferedReader(new FileReader(path));
		String s=read.readLine();
		int i=0;
		while (s!=null&&!s.equals("")) {
			i++;
			map.put(s, new int[16]);//16为文件数量
			s=read.readLine();
		}
		System.out.println(i);
		read.close();
	}
	public void merge(String path,int index) throws IOException{
		BufferedReader read=new BufferedReader(new FileReader(path));
		String s=read.readLine();
		while (s!=null&&!s.equals("")) {
			String[] ss=s.split(":");
			map.get(ss[0])[index]=Integer.parseInt(ss[1]);
			s=read.readLine();
		}
		read.close();
	}
	public static void main(String[] arg){
		File[] f= new File("D:/data/output").listFiles();
		MergeData md=new MergeData();
		try {
			md.getName("D:/data/alledge.txt");
			int i=0;//文件计数
			for (File file : f) {
				md.merge("D:/data/output/"+file.getName(),i++);
			}
			BufferedWriter be=new BufferedWriter(new FileWriter("D:/data/mergeData.txt"));
			int ii=0;
			for (String file : map.keySet()) {
				//System.out.print(file);
				ii++;
				be.write(file);
				for (Integer num : map.get(file)) {
					//System.out.print(","+num);
					be.write(","+num);
				}
				//System.out.println();
				be.newLine();
			}
			System.out.println(ii);
			be.flush();
			be.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
}
