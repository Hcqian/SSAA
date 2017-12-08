package WDLP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
 * Represents the node of the undirected graph.
 * @author pejakalabhargava
 *
 */
public class Node {
	public static int changed=0;

	public Map<Node, Double[]> neighbhours;//[权重，依赖度] 存放出度边
	public Map<Node, Double[]> beneighbhours;//[权重，依赖度] 存放入度边
	public int nodeId;//节点id
//	private double alpha=0.0001;//起初打算加入的调节值
	public int[] labels; //结合两种不同迭代方式，[目前的标签，之前的标签] 
	public Node(Integer source) {
		nodeId = source;
		//初始化
		initializeDataStructure();
	}

	private void initializeDataStructure() {
		neighbhours = new HashMap<Node, Double[]>();
		beneighbhours=new HashMap<Node, Double[]>();
		labels=new int[2];
		labels[0]=nodeId;
	}
	/**
	 * 标签传播过程
	 */
	public void listen() {
		Map<Integer, Double> labelMap = new HashMap<Integer, Double>();//存储该点每次收到的标签以及次数
		//遍历出度      得到出度的标签
		for (Node node : neighbhours.keySet()) {
			//得到邻接点的标签
			int label=node.labels[0];
			if (labelMap.get(label) == null) {
				labelMap.put(label, neighbhours.get(node)[1]);
			} else {
				Double currentLabelCount=labelMap.get(label);
				currentLabelCount+= neighbhours.get(node)[1];
				labelMap.put(label, currentLabelCount);
			}
		}
//		if(labelMap.isEmpty())
//		for (Node node : beneighbhours.keySet()) {
//			//得到邻接点的标签
//			int label=node.labels[0];
//			if (labelMap.get(label) == null) {
//				labelMap.put(label, beneighbhours.get(node)[1]);
//			} else {
//				Double currentLabelCount=labelMap.get(label);
//				currentLabelCount+= beneighbhours.get(node)[1];
//				labelMap.put(label, currentLabelCount);
//			}
//		}
		int popularLabel = getMostPopularLabel(labelMap);//得到标签出现次数最多(依赖度最大的)的标签
//		int popularLabel = getLabelRadom(labelMap);//根据概率得到标签
		//迭代方式 ，采用的是立即传播，可以改做结束整次迭代 再传播
		if(popularLabel!=-1){
			if(labels[0]!=popularLabel) changed++;
		labels[0]=popularLabel;
		
		}else {
//			labels[1]=labels[0];
		}
	}
	//依据占比概率随机传播 方式
	private int getLabelRadom(Map<Integer, Double> labelMap){
		Random random = new Random();
		double randomDoubleValue = random.nextDouble();
		double cumulativeSum = 0;
		double allvalue=0;
		for (Map.Entry<Integer, Double> entry : labelMap.entrySet()) {
			allvalue+=entry.getValue();
		}
		for (Map.Entry<Integer, Double> entry : labelMap.entrySet()) {
			Integer labelId = entry.getKey();
			Double labelCount = entry.getValue();
			cumulativeSum+=labelCount/allvalue;
			if (randomDoubleValue <= cumulativeSum) {
				return labelId;
			}
		}
		return -1;
	}

	private int getMostPopularLabel(Map<Integer, Double> labelMap) {
		Double maxLabelCount = 0.0;
		List<Integer> labelist=new ArrayList<>();
		for (Map.Entry<Integer, Double> entry : labelMap.entrySet()) {
			maxLabelCount=Math.max(maxLabelCount, entry.getValue());
		}
		for (Map.Entry<Integer, Double> entry : labelMap.entrySet()) {
			if(maxLabelCount.equals(entry.getValue()))
			labelist.add(entry.getKey());
		}
		if(labelist.size()==0) return -1;
		double index=Math.random()*(double)labelist.size();
		return labelist.get((int)index);
//		return labelist.get(labelist.size()-1);
	}

	//Getters and Setters
	/**
	 * Adds a neighbor to the node's adjacency list
	 * @param destNode
	 */
	public void addbeNeighbour(Node destNode,Double w,Double de) {
		Double[] d=beneighbhours.get(destNode);
		if(d!=null){
			d[0]+=w;
			d[1]+=de;
		}else {
			d=new Double[2];
			d[0]=w;
			d[1]=de;
		}
			beneighbhours.put(destNode,d);
	}
	
	
	public void addNeighbour(Node destNode,Double w,Double de) {
		Double[] d=neighbhours.get(destNode);
		if(d!=null){
			d[0]+=w;
			d[1]+=de;
		}else {
			d=new Double[2];
			d[0]=w;
			d[1]=de;
		}
			neighbhours.put(destNode,d);
		
	}

	/**
	 * Returns the neighbors of the node
	 * @return set of neighbors
	 */
	public Map<Node,Double[]> getNeighbhours() {
		return neighbhours;
	}
	/**
	 * Returns the NodeId
	 * @return node ID
	 */
	public int getNodeId() {
		return nodeId;
	}

	/**
	 * Returns the memory map of the node at given time t
	 * @return map
	 */
	public int[] getlLabels(){
		return labels;
	}
	/**
	 * Returns the total number of entries in the memory map's count
	 * @return int
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nodeId+":"+labels[0];
		
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		Integer i=new Integer(this.nodeId);
		return i.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Integer i=new Integer(this.nodeId);
		return i.equals(obj);
	}
	
	
}
