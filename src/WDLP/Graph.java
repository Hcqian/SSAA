package WDLP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import NEWA.edge;
import test.inter;

/**
 * Data structure the hold the undirected graph on which clustering algorithm is run to
 * find the overlapping communities using SLPA algorithm.
 * @author pejakalabhargava
 *
 */
public class Graph {
	
	int vertices=0;
	public Map<Integer, Node> graphADT;
	public List<edge> sortgraph;
	public int edges=0;
	Map<Integer,Set<Integer>> overlappingCommunities;
	/**
	 * 初始化
	 * @param filepath
	 */
	public Graph(String filepath) {
		graphADT = new LinkedHashMap<Integer, Node>();
		sortgraph=new ArrayList<>();
		overlappingCommunities =  new HashMap<Integer, Set<Integer>>();
		readGraph(filepath);
		sortgraph.sort((edge e1,edge e2)->e1.w.compareTo(e2.w));
	}

	/**
	 * 读取图
	 * @param filepath
	 */
	private void readGraph(String filepath) {
		System.out.println("读取图...");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			String line;
			while ((line = br.readLine()) != null) {
				edges++;
				String[] config = line.split(",");
				int source = Integer.parseInt(config[0]);
				int dest = Integer.parseInt(config[1]);
				double value = Double.parseDouble(config[2]);
				double de= Double.parseDouble(config[3]);
				addEdge(source, dest,value,de);
			}
//			vertices=graphADT.size();
		} catch (IOException e) {
			System.out.println("读取出错....");
			System.exit(0);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @param source
	 * @param dest
	 * source-——>dest
	 * @param w    权重
	 * @param de   依赖度
	 */
	//source->dest   source把标签传到dest  //dest的bug传到source
	private void addEdge(Integer source, Integer dest,Double w,Double de) {
		sortgraph.add(new edge(source, dest, de));
		//生成source节点 
		Node sourceNode = graphADT.get(source);;
		if (sourceNode == null) {	
			sourceNode = new Node(source);
			graphADT.put(source, sourceNode);
		}
		//生成dest节点 
		Node destNode = graphADT.get(dest);
		if (destNode == null) {
			destNode = new Node(dest);
			graphADT.put(dest, destNode);
		}
		//Add an entry into the adjacenecy list.
		sourceNode.addNeighbour(destNode,w,de);
//		destNode.addbeNeighbour(sourceNode, w,de);
//		sourceNode.addAlphaNeighbour(destNode,value);
		destNode.addNeighbour(sourceNode,w,de);
		
	}
	public Node getNode(int nodeId) {
		return graphADT.get(nodeId);
	}

	public Map<Integer, Set<Integer>> getOverlappingCommunities() {
		return overlappingCommunities;
	}
	public int getNumberVertices() {
		if(vertices==0) this.vertices=graphADT.size();
		return vertices;
	}
}
