package analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SequenceToGraph {
//�ѵõ���sequence ת���� graph
	static String version="1.20";
	
	List<String> list;
	static List<String> num;//������������ת��
	Map<Integer, Set<WeightEdge>> adj;
	
	int index=1;   //���е�λ��
	int[][] matrix=null;
	public static void main(String[] arg){
//		File[] f= new File("D:/data/cflow/cflow"+version).listFiles();
		File[] f= new File("D:/data/tar/tar"+version).listFiles();
		SequenceToGraph stg=new SequenceToGraph();
		num=new ArrayList<>();//�ڵ�
		List<String> alledge=new ArrayList<>();
		try {
			for (File file : f) {
				//������Ҫһ������
				stg.SequenceToGraph("D:/data/tar/tar"+version+"/"+file.getName());//��ȡ����
//				stg.SequenceToGraph("D:/data/cflow/cflow"+version+"/"+file.getName());//��ȡ����
//				stg.getGraph(0);//ת������
//				stg.printGraph("D:/data/tar/output"+version+"/"+file.getName());//���ͼ
				
			}
//			BufferedWriter be=new BufferedWriter(new FileWriter("D:/data/output/alledge.txt"));
//			for (String string : alledge) {
//				be.write(string);
//				be.newLine();
//			}
//			be.flush();
//			be.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//���ͼ
	public void printGraph(String path) throws IOException{
		BufferedWriter be=new BufferedWriter(new FileWriter(path));
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if(matrix[i][j]!=0&&i!=j)
					{
					//String str=num.get(i)+","+num.get(j);
					//if(!alledge.contains(str)) alledge.add(str);
					be.write(num.get(i)+","+num.get(j)+","+matrix[i][j]);
					//be.write(i+"    "+j+"    "+stg.matrix[i][j]);
					be.newLine();}
			}
		}
		be.flush();
		be.close();
		
	}
	//�������У������ҵ����еĽڵ�
	public void SequenceToGraph(String path) throws IOException{
		list=new ArrayList<>();
		//num=new ArrayList<>();
		index=1;
		BufferedReader read=new BufferedReader(new FileReader(path));
		String node=read.readLine();
//		int nums=0;
		while (node!=null) {
//			nums++;
			list.add(node);
			if(node.startsWith("E")&&!num.contains(node.substring(1)))
				num.add(node.substring(1));
			node=read.readLine();
		}
//		System.out.println(path+" "+nums);
		matrix=new int[num.size()][num.size()];
	}
	//ת����ͼ
	public void getGraph(int i){
		while(index<list.size()&&list.get(index).startsWith("E")){
			int j=num.indexOf(list.get(index++).substring(1));
			matrix[i][j]++;
			getGraph(j);
		}
		index++;
	}
}
