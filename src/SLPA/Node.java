package SLPA;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
/**
 * Represents the node of the undirected graph.
 * @author pejakalabhargava
 *
 */
public class Node {

	//Adjacenecy list holding all the neighbours of the given node 存放邻接点
	//private Set<Node> neighbhours;
	private Map<Node, Integer> neighbhours;
	//Id of the given node.
	private int nodeId;
	
	// Memory map used to hold the labelId and the count used for SLPA
	// algorithm.存储<标签，标签次数>
	private Map<Integer, Integer> memoryMap;
	
	// This represents the total number of counts(or communities) present in the
	// memory map of this node.//总标签数所属社区数量
	private int noOfCommunities;

	/**
	 * Constructor to create the node structure.
	 */
	public Node(Integer source) {
		nodeId = source;
		initializeDataStructure();
	}

	/**
	 * Helper method to initialize the required data strucutres. This also makes
	 * sure that the memory of each node is initialized with a unique label as
	 * part of SLPA algorithm.
	 */
	private void initializeDataStructure() {
		neighbhours = new HashMap();
		memoryMap = new LinkedHashMap <Integer, Integer>();
		noOfCommunities = 1;
		memoryMap.put(nodeId, 1);
	}
	
	
	/**
	 * This function implements the listen step of the SLPA algorithm. Each
	 * neighbor sends the selected label to the listener and the listener adds
	 * the most popular label received to its memory.
	 */
	public void listen() {
		//Map to hold the all the received labels from its neighbours in this iteration
		Map<Integer, Integer> labelMap = new HashMap<Integer, Integer>();//存储该点每次收到的标签以及次数
		//Iterate through all the neighbors and callk speak on them as part of SLPA algorithm.
		for (Node node : neighbhours.keySet()) {
			//Speak method returns label to the listener
			int label = node.speak(); //得到邻接点的标签
			//Add the label received to the temporary labelMap to hold the labelId and received count.
			if (labelMap.get(label) == null) {
				labelMap.put(label, 1);
			} else {
				int currentLabelCount = labelMap.get(label);
				currentLabelCount++;
				labelMap.put(label, currentLabelCount);
			}
		}
		//After all neighbours sends the label, findout the most popular label
		int popularLabel = getMostPopularLabel(labelMap);//得到标签出现次数最多的标签
		
		//add the popular label to the memory map of the node.
		//次数出现最多的标签 到标签集中，并且记录曾经出现的次数
		if(memoryMap.get(popularLabel) == null) {
			memoryMap.put(popularLabel, 1);
		} else {
			int currentCount = memoryMap.get(popularLabel);
			currentCount++;
			memoryMap.put(popularLabel, currentCount);
		}
		//Increment the noOfCommunities
		noOfCommunities++;
		labelMap.clear();
	}

	private int getMostPopularLabel(Map<Integer, Integer> labelMap) {
		int maxLabelCount = 0;
		int popularLabel= -1;
		for (Map.Entry<Integer, Integer> entry : labelMap.entrySet()) {
			Integer labelId = entry.getKey();
			Integer labelCount = entry.getValue();
			if (labelCount > maxLabelCount) {
				popularLabel = labelId;
				maxLabelCount = labelCount;
			}
		}
		return popularLabel;
	}

	/**
	 * Each neighbor of the selected node randomly selects a label with probability
	 * proportional to the occurrence frequency of this label in its memory and sends
	 * the selected label to the listener.
	 * @return label
	 */
	//随机选择记忆中概率最大的标签送出,按照概率分布随机取
	private int speak() {
		//generate a random double value
		Random random = new Random();
		double randomDoubleValue = random.nextDouble();
		double cumulativeSum = 0;
		// Randomly select a label with probability proportional to the
		// occurrence frequency of this label in its memory
		for (Map.Entry<Integer, Integer> entry : memoryMap.entrySet()) {
			Integer labelId = entry.getKey();
			Integer labelCount = entry.getValue();
			cumulativeSum = cumulativeSum + ((double)labelCount)/noOfCommunities;
			if(cumulativeSum >= randomDoubleValue) {
				return labelId;
			}
		}
		return nodeId;
	}
	
	//Getters and Setters
	/**
	 * Adds a neighbor to the node's adjacency list
	 * @param destNode
	 */
	public void addNeighbour(Node destNode,Integer value) {
		neighbhours.put(destNode,value);
	}

	/**
	 * Returns the neighbors of the node
	 * @return set of neighbors
	 */
//	public Set<Node> getNeighbhours() {
//		return neighbhours;
//	}
	public Map<Node,Integer> getNeighbhours() {
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
	public Map<Integer, Integer> getMemoryMap() {
		return memoryMap;
	}

	/**
	 * Returns the total number of entries in the memory map's count
	 * @return int
	 */
	public int getNoOfCommunities() {
		return noOfCommunities;
	}
}
