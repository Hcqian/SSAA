package WDLP;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import Modularity.Qfunction;
import NEWA.edge;
public class WDLP {

	Graph graph;
	int iterations;//迭代次数
	/**
	 * 构造函数
	 * @param inputFileName
	 * @param inputIterations
	 */
	public WDLP(String inputFileName, int inputIterations) {
		iterations = inputIterations;
		graph = new Graph(inputFileName);
	}
	
	public static void main(String[] args) {
		String inputFileName ="D:/data/cflow/output1.3/Dependece1.txt"; //读取图的path
		int inputIterations = 10;//迭代次数
		WDLP algorithm = new WDLP(inputFileName,inputIterations);
		algorithm.propogateMemorylabel();
	}
	private void propogateMemorylabel() {
		
		int[] nodeId = new int[graph.getNumberVertices()];
		for (int i = 0; i < nodeId.length; i++) {
			nodeId[i] = i;
		}
		
		//Loop iteration T number of times
//		for (edge e : graph.sortgraph) {
//			graph.graphADT.get(e.from).labels[0]=graph.graphADT.get(e.to).nodeId;
//		}
		Qfunction q=new Qfunction();
		System.out.println(q.Qfunction(graph));
		for (int i = 1; i <=iterations; i++) {
			ShuffleArray(nodeId);
			Node.changed=0;
			for (int j = 0; j < nodeId.length; j++) {
				Node node = graph.getNode(nodeId[j]);
				node.listen();
				
			}
//			for (int j = 0; j < nodeId.length; j++) {
//				Node node = graph.getNode(j);
//				System.out.print(node+"|");
//			}
//			System.out.println();
			//两种迭代方式   使用的是立刻覆盖的方式
//			for (int j = 0; j < nodeId.length; j++) {
//				Node node = graph.getNode(j);
//				int[] labels=node.getlLabels();
//				if(labels[0]==labels[1]);
//				labels[0]=labels[1];
//				
//			}
//			System.out.println(Node.changed);
			System.out.println(q.Qfunction(graph));
			
		}
		this.show(nodeId.length); 
		System.out.println(graph.edges+" "+graph.vertices);
		
	}
	public void show(int len){
		Map<Integer, Set<Integer>> Communities=new HashMap<>();
		for (int i = 0; i < len; i++) {
			Node node = graph.getNode(i);
			int labels=node.getlLabels()[0];
			Set<Integer> s=Communities.get(labels);
			if(s!=null){
				s.add(node.getNodeId());
			}else {
				s=new HashSet<>();
				s.add(node.getNodeId());
				Communities.put(labels,s);
			}
		}
//		System.out.println(Communities);
		for (Integer integer : Communities.keySet()) {
			System.out.print(integer+":");
			for (Integer m : Communities.get(integer)) {
				System.out.print(m+" ");
			}
			System.out.println();
		}
		System.out.println(Communities.size());
	}
	private void ShuffleArray(int[] array)
	{
	    int index;
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        if (index != i)
	        {
	            array[index] ^= array[i];
	            array[i] ^= array[index];
	            array[index] ^= array[i];
	        }
	    }
	}
	
}
