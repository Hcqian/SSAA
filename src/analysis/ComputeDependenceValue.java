package analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

public class ComputeDependenceValue {
	Map<Integer, List<WeightEdge>> lastadj = new HashMap<>();
	static String version="1.3";
	public static void main(String[] args) {
		
		ComputeDependenceValue cdw=new ComputeDependenceValue();
		try {
			cdw.computeDII();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void computeDII() throws NumberFormatException, IOException{
		GetTree gt=new GetTree();
		Map<String, Set<String>> map= gt.TrieTree("D:/data/cflow/cflow"+version, null);
		readGraph("D:/data/cflow/output"+version+"/nlastadj.txt");
		List<String> numberName=getNumberName("D:/data/cflow/output"+version+"/numberName.txt");
		
		for (Integer from : lastadj.keySet()) {
			for (WeightEdge w : lastadj.get(from)) {
				Set<String> unSet=new HashSet<>();
				Set<String> interSet=new HashSet<>();
				Set<String> fromSet=map.get(numberName.get(from));
				Set<String> toSet=map.get(numberName.get(Integer.parseInt(w.to)));
				unSet.addAll(fromSet);
				
				unSet.addAll(toSet);
				interSet.add(numberName.get(Integer.parseInt(w.to)));
				for (String string : toSet) {
					if(fromSet.contains(string)) interSet.add(string);
				}
				double nd=(double)interSet.size()/(double)unSet.size()*w.dweight;
				w.setDependence(nd);
			}
		}
			printGraph("D:/data/cflow/output"+version+"/Dependece1.txt");
		
	}
	public List<String> getNumberName(String path){
		BufferedReader br;
		List<String> l=null;
		try {
			br = new BufferedReader(new FileReader(path));
			l=new ArrayList<>();
			String line;
			while ((line = br.readLine()) != null) {
				l.add(line.split(",")[1]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return l;
	}
	public void computeD(){
		try {
			readGraph("D:/data/cflow/output"+version+"/nlastadj.txt");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Integer from : lastadj.keySet()) {
			for (WeightEdge w : lastadj.get(from)) {
				double de=findCommenValue(from, Integer.parseInt(w.to),w.dweight);
				w.setDependence(de);
			}
		}
		try {
			printGraph("D:/data/cflow/output"+version+"/Dependece.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//打印
	public void printGraph(String path) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(path));
		for (Integer l : lastadj.keySet()) {
			for (WeightEdge w : lastadj.get(l)) {
				bw.write(w.toString());
				bw.newLine();
			}
		}
		bw.flush();
		bw.close();
	}
	//读取图
	public void readGraph(String path) throws NumberFormatException, IOException{
		BufferedReader br=new BufferedReader(new FileReader(path));
		String line;
		while ((line = br.readLine()) != null) {
			String[] config = line.split(",");
			int source = Integer.parseInt(config[0]);
			int dest = Integer.parseInt(config[1]);
			double value = Double.parseDouble(config[2]);
			WeightEdge e=new WeightEdge(source+"", dest+"", value);
			if(lastadj.containsKey(source)){
				lastadj.get(source).add(e);
				
			}else {
				List<WeightEdge> l=new ArrayList<>();
				l.add(e);
				lastadj.put(source, l);
			}
		}
	}
	//计算依赖度
	public double findCommenValue(int from, int to,double toVlaue) {
		List<WeightEdge> fromList = lastadj.get(from);
		List<WeightEdge> toList = lastadj.get(to);
//		List<String> commenList = new ArrayList<>();
		double commenValue = 0;
		double fromValue=0;
		for (WeightEdge f : fromList) {
			fromValue+=f.dweight;
			if(toList!=null)
			for (WeightEdge t : toList) {
				if (f.to.equals(t.to)) {
//					commenList.add(f.to);
					commenValue += f.dweight;
				}
			}
		}
		return (commenValue+toVlaue)/fromValue;
	}

}
