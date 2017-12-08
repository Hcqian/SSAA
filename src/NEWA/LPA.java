/*package NEWA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
*//**
 * This project is implementation of Speaker-listener Label Propagation Algorithm
 * which is used to detecting overlapping community.
 * Algorithm:SLPA(T, r )
 *	T : the user defined maximum iteration
 *	r: post-processing threshold
 *	1)	First, the memory of each node is initialized with a unique label.
 *	2)	Then, the following steps are repeated until the maximum iteration T is reached:
 *		a. 	One node is selected as a listener.
 *		b. 	Each neighbor of the selected node randomly selects a label with probability
 *			proportional to the occurrence frequency of this label in its memory and sends
 *			the selected label to the listener.
 *		c. The listener adds the most popular label received to its memory.
 *	3)	Finally, the post-processing based on the labels in the memories and the threshold
 *		r is applied to output the communities.
 * 
 * Run as: 	mvn clean install
 * 		 	mvn exec:java -Dexec.args="amazon.graph.original amazon.graph.comm 20 0.5" from command line.
 * 		    or
 * 		   	mvn clean install
 * 			cd target
 * 			java -jar slpa-0.0.1.jar amazon.graph.original amazon.graph.comm 20 0.5
 * 
 * @author pejakalabhargava
 * 
 * 
 *
 *//*
public class LPA {

	//This holds the input graph 
	Graph graph;
	
	//r: post-processing threshold
	double threshHold;//某个阈值
	
	//T : the user defined maximum iteration
	int iterations;//迭代次数
	
	//Output file to store the communities
	String outputFile;//输出的位置
	
	WeightedQuickUnionUF uf;

	
	 * Constructor to create the graph from the given input.
	 
	public LPA(String inputFileName, String outPutFileName,
			int inputIterations, double inputThreshHold) {
		threshHold = inputThreshHold;
		iterations = inputIterations;
		outputFile = outPutFileName;
		graph = new Graph(inputFileName);
		uf=new WeightedQuickUnionUF(graph.getNumberVertices());
	}
	
	public static void main(String[] args) {
		String inputFileName ="D:/data/output/nlastadj.txt"; //args[0];
		String outPutFileName ="D:/data/output/l.txt"; //args[1];
		int inputIterations = 4;//Integer.parseInt(args[2]);
		double inputThreshHold = 0.5;//Double.parseDouble(args[3]);
		LPA algorithm = new LPA(inputFileName, outPutFileName,
				inputIterations, inputThreshHold);
		//Step 2 of the SLAP algorithm where memory labels are updated on each iteration
		System.out.println("Propogating the memory labels....");
		algorithm.propogateMemorylabel();
		// Step 3:post-processing based on the labels in the memories and the
		// threshold r is applied to output the communities
		System.out.println("Post-Processing to apply threshold " + inputThreshHold + " to output communities");
//		algorithm.postProcessing();
		//Save the output file 
		System.out.println("Saving the communities to output file:" + outPutFileName);
//		algorithm.outputOverlappingCommunitiesToFile();
		System.out.println("Ending the Speaker-listener Label Propagation Algorithm\n\n");
	}
	private void propogateMemorylabel() {
		
		int[] nodeId = new int[graph.getNumberVertices()];
		for (int i = 0; i < nodeId.length; i++) {
			nodeId[i] = i;
		}
		
		//Loop iteration T number of times
		for (int i = 1; i <=iterations; i++) {
			ShuffleArray(nodeId);
			for (int j = 0; j < nodeId.length; j++) {
				Node listener = graph.getNode(nodeId[j]);
				listener.Union(uf);
			}
			double dif=0;
//			System.out.println(dif/(double)nodeId.length);
			
		}
		this.show(nodeId.length); 
		
	}
	public void show(int len){
		Map<Integer, Set<Integer>> Communities=new HashMap<>();
		for (int i = 0; i < len; i++) {
			int root=uf.find(i);
			if(Communities.containsKey(root)){
				Communities.get(root).add(i);
			}else{
				Set<Integer> s=new HashSet<>();
				s.add(i);
				Communities.put(root, s);
			}
		}
		for (Integer integer : Communities.keySet()) {
			System.out.print(integer+":");
			for (Integer m : Communities.get(integer)) {
				System.out.print(m+" ");
			}
			System.out.println();
		}
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
	
	private void postProcessing() {
		
		Map<Integer, Set<Integer>> community = graph.getOverlappingCommunities();
		for (int i = 0; i < graph.getNumberVertices(); i++) {
			Node node = graph.getNode(i);
			Map<Integer, Integer> memoryMap = node.getMemoryMap();
			int noOfCommunities = node.getNoOfCommunities();
			for (Map.Entry<Integer, Integer> entry : memoryMap.entrySet()) {//labelId所属社区id
				Integer labelId = entry.getKey();
				Integer count = entry.getValue();
				double probalityDensity = (double)count/noOfCommunities;
				//If ration is greater than threhsold input, then add this node to the community
				//identified by the label.
				//使用阈值去掉出现次数较少的社区
				if(probalityDensity >= threshHold) {
					//Check if the label exits in the community map and if it doesnt
					//exist create one for this label and add the node to the set.
					if(community.get(labelId) == null) { 
						Set<Integer> communitySet = new HashSet<Integer>();
						community.put(labelId,communitySet);
						communitySet.add(node.getNodeId());
					} else {
						Set<Integer> communitySet = community.get(labelId);
						communitySet.add(node.getNodeId());
					}
				}
			}
		}
	}
	
	private void outputOverlappingCommunitiesToFile() {
		Writer fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			File file = new File(outputFile);
			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			Map<Integer, Set<Integer>> communities = graph.getOverlappingCommunities();
			int no =0;
			for (Entry<Integer, Set<Integer>> entry : communities.entrySet()) {
				no++;
				Set<Integer> nodeIdSet = entry.getValue();
				for (Integer node : nodeIdSet) {
					bufferedWriter.write(node.toString());
					bufferedWriter.write(" ");
				}
				bufferedWriter.newLine();
			}
			System.out.println("There are totally " + no + " communities detected.");
		} catch (IOException e) {
			System.err.println("Error writing the file : ");
			e.printStackTrace();
		} finally {
			if (bufferedWriter != null && fileWriter != null) {
				try {
					bufferedWriter.close();
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
*/