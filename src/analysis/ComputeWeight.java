package analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ComputeWeight {
	Map<String, List<WeightEdge>> lastadj = new HashMap<>();// ����ͼ

	public static void main(String[] a) {
	    String  version="1.5";
	    String  fileName="D:/data/cflow/output";
		File[] f = new File(fileName+version).listFiles();
		ComputeWeight c = new ComputeWeight();
		try {
			for (File file : f) {
				c.computeII(c.readGraph(file.toString()));
				
			}
//			System.out.println(c.lastadj);
			c.printGraph(fileName+version+"/lastadj.txt");
			c.numberGraph(fileName+version+"/lastadj.txt", fileName+version+"/nlastadj.txt",fileName+version+"/numberName.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//��ȡlastadj
	public void getGraph(String path) throws IOException{
		lastadj=readGraph(path);
	}
	//���lastadj
	public void printGraph(String path) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(path));
		for (String l : lastadj.keySet()) {
			for (WeightEdge w : lastadj.get(l)) {
				bw.write(w.toString());
				bw.newLine();
			}
		}
		bw.flush();
		bw.close();
	}

	// ��ȡͼ�����ڽӱ�
	public Map<String, List<WeightEdge>> readGraph(String path) throws IOException {
		int len=path.lastIndexOf("\\");//����ǰȷ���ļ���ֻ��0-19�����ݣ�����������Ļ�ӽ�ȥ
		if(path.length()-len>7) return null;
		Map<String, List<WeightEdge>> adj = new HashMap<>();
		BufferedReader read = new BufferedReader(new FileReader(path));
		adj = new HashMap<>();
		String str = read.readLine();
		while (str != null) {
			String[] strs = str.split(",");
			WeightEdge w = new WeightEdge(strs[0], strs[1], Double.parseDouble(strs[2]));
			if (adj.containsKey(strs[0])) {
				List<WeightEdge> list = adj.get(strs[0]);
				list.add(w);
				/*
				 * if(list.contains(w)){ for (WeightEdge weightEdge : list) {
				 * if(weightEdge.equals(w)) weightEdge.weight+=w.weight; } }else
				 * { list.add(w); }
				 */
			} else {
				List<WeightEdge> set = new LinkedList<>();
				set.add(w);
				adj.put(strs[0], set);
			}
			str = read.readLine();
		}
		return adj;
	}
	//��һ�ּ���Ȩ�ط���
	public void computeII(Map<String, List<WeightEdge>> adj){
		if(adj==null) return;
		for (String s : adj.keySet()) {
			List<WeightEdge> l = adj.get(s);
			if (!l.isEmpty()) {
				double allvalue=0;
//				int point = 1;// ���� �������
				for (WeightEdge w : l) {
//					allvalue+=Math.log1p(w.dweight);
					allvalue+=w.dweight;
				}
				for (WeightEdge w : l) {
						putPointII(w.from, w.to, w.dweight/allvalue);
//					putPointII(w.from, w.to, Math.log1p(w.dweight)/allvalue);
				}
			}
		}
		
	}
	// ����ÿ����ֵ����lastadj
//	public void compute(Map<String, List<WeightEdge>> adj) {
//		for (String s : adj.keySet()) {
//			List<WeightEdge> l = adj.get(s);
//			Collections.sort(l);
//			// �������
//			if (!l.isEmpty()) {
//				int times = l.get(0).weight;// ֮ǰ��Ȩ��
//				int point = 1;// ���� �������
//				for (WeightEdge w : l) {
//					if (w.weight == times)
//						putPoint(w.from, w.to, point);
//					else {
//						times = w.weight;
//						putPoint(w.from, w.to, ++point);
//					}
//				}
//			}
//		}
//
//	}
	// ����������lastadjII
		public void putPointII(String from, String to, double point) {
			WeightEdge w = new WeightEdge(from, to, point);
			if (lastadj.containsKey(from)) {
				List<WeightEdge> list = lastadj.get(from);
				int index = list.indexOf(w);
				if (index != -1) {
					list.get(index).dweight += point;
				} else {
					list.add(w);
				}
			} else {
				List<WeightEdge> list = new LinkedList<>();
				list.add(w);
				lastadj.put(from, list);
			}
		}

	// ����������lastadj
//	public void putPoint(String from, String to, int point) {
//		WeightEdge w = new WeightEdge(from, to, point);
//		if (lastadj.containsKey(from)) {
//			List<WeightEdge> list = lastadj.get(from);
//			int index = list.indexOf(w);
//			if (index != -1) {
//				list.get(index).weight += point;
//			} else {
//				list.add(w);
//			}
//		} else {
//			List<WeightEdge> list = new LinkedList<>();
//			list.add(w);
//			lastadj.put(from, list);
//		}
//	}
	//��ͼ�ڵ�ת��������
	public void numberGraph(String inputpath,String outputpath,String out2) throws IOException{
		BufferedReader b=new BufferedReader(new FileReader(inputpath));
		List<String> vertex=new ArrayList<>();//�ڵ����Ӧ����
		List<String[]> edge=new ArrayList<>();
		String s=b.readLine();
		while(s!=null){
			String[] ss=s.split(",");
			if(!vertex.contains(ss[0])) vertex.add(ss[0]);
			if(!vertex.contains(ss[1])) vertex.add(ss[1]);
			edge.add(ss);
			s=b.readLine();
		}
		System.out.println(vertex.size() );
		b.close();
		BufferedWriter w=new BufferedWriter(new FileWriter(outputpath));
		for (String[] strings : edge) {
			w.write(vertex.indexOf(strings[0])+","+vertex.indexOf(strings[1])+","+strings[2]);
			w.newLine();
		}
		w.flush();
		w.close();
		BufferedWriter bw=new BufferedWriter(new FileWriter(out2));
		for (int i = 0; i < vertex.size(); i++) {
			bw.write(i+","+vertex.get(i));
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}

	public void show() {
		
		for (String l : lastadj.keySet()) {
			for (WeightEdge w : lastadj.get(l)) {
				System.out.println(w.toString());
			}
		}
	}
}
