package NEWA;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.lang.model.element.QualifiedNameable;

import Modularity.Qfunction;
import WDLP.Graph;
import WDLP.Node;
public class WDLP {

	Graph graph;
	int iterations;//迭代次数
	WeightedQuickUnionUF uf;
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
		Qfunction q=new Qfunction();
//		for (edge e : graph.sortgraph) {
//			graph.graphADT.get(e.from).labels[0]=graph.graphADT.get(e.to).nodeId;
//		}
		for (int i = 0; i <=iterations; i++) {
			Node.changed=0;
			for (edge e : graph.sortgraph) {
				graph.graphADT.get(e.from).listen();
				graph.graphADT.get(e.to).listen();
				
				
//				uf.union(e.from, e.to);
//				System.out.println(e.w);
//				this.show(nodeId.length); 
			}
//			if(Node.changed<=10) break;
			System.out.println(q.Qfunction(graph)+","+Node.changed);
			
		}
		this.show(nodeId.length); 
//		for (int i : uf.parent) {
//			System.out.print(i+" ");
//		}
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
