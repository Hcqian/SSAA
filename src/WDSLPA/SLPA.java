package WDSLPA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import NEWA.edge;

import java.util.Random;
import java.util.Set;

/**
 * This project is implementation of Speaker-listener Label Propagation
 * Algorithm which is used to detecting overlapping community. Algorithm:SLPA(T,
 * r ) T : the user defined maximum iteration r: post-processing threshold 1)
 * First, the memory of each node is initialized with a unique label. 2) Then,
 * the following steps are repeated until the maximum iteration T is reached: a.
 * One node is selected as a listener. b. Each neighbor of the selected node
 * randomly selects a label with probability proportional to the occurrence
 * frequency of this label in its memory and sends the selected label to the
 * listener. c. The listener adds the most popular label received to its memory.
 * 3) Finally, the post-processing based on the labels in the memories and the
 * threshold r is applied to output the communities.
 * 
 * Run as: mvn clean install mvn exec:java -Dexec.args=
 * "amazon.graph.original amazon.graph.comm 20 0.5" from command line. or mvn
 * clean install cd target java -jar slpa-0.0.1.jar amazon.graph.original
 * amazon.graph.comm 20 0.5
 * 
 * @author pejakalabhargava
 *
 */
public class SLPA {

	// This holds the input graph
	Graph graph;

	// r: post-processing threshold
	double threshHold;// 某个阈值
	Map<Integer,Set<Integer>> OverNode;

	// T : the user defined maximum iteration
	int iterations;// 迭代次数

	// Output file to store the communities
	String outputFile;// 输出的位置

	/*
	 * Constructor to create the graph from the given input.
	 */
	public SLPA(String inputFileName, String outPutFileName, int inputIterations, double inputThreshHold) {
		threshHold = inputThreshHold;
		iterations = inputIterations;
		outputFile = outPutFileName;
		graph = new Graph(inputFileName);
	}

	public static void main(String[] args) {
		// Check if there are 4 arguments input
		// if (args.length != 4) {
		// System.out.println("Invalid number of arguments.Please re run");
		// System.exit(0);
		// }
		System.out
				.println("\nStarting the Speaker-listener Label Propagation Algorithm to find overlapping communities");
		String inputFileName = "D:/data/tar/output1.20/Dependece.txt"; // args[0];
		System.out.println("Input file name is:" + inputFileName);
		String outPutFileName = "D:/data/tar/output1.20/OverlappingC.txt"; // args[1];
		int inputIterations = 500;// Integer.parseInt(args[2]);
		System.out.println("Number of iterations are:" + inputIterations);
		double inputThreshHold = 0.1;// Double.parseDouble(args[3]);
		System.out.println("Threshold is:" + inputThreshHold);
		SLPA algorithm = new SLPA(inputFileName, outPutFileName, inputIterations, inputThreshHold);
		// Step 2 of the SLAP algorithm where memory labels are updated on each
		// iteration
		System.out.println("Propogating the memory labels....");
		algorithm.propogateMemorylabel();
		// Step 3:post-processing based on the labels in the memories and the
		// threshold r is applied to output the communities
		System.out.println("Post-Processing to apply threshold " + inputThreshHold + " to output communities");
		algorithm.postProcessing();
		// Save the output file
		System.out.println("Saving the communities to output file:" + outPutFileName);
		algorithm.outputOverlappingCommunitiesToFile();
		System.out.println("Ending the Speaker-listener Label Propagation Algorithm\n\n");
	}

	/**
	 * The following steps are repeated until the maximum iteration T is
	 * reached: a. One node is selected as a listener. b. Each neighbor of the
	 * selected node randomly selects a label with probability proportional to
	 * the occurrence frequency of this label in its memory and sends the
	 * selected label to the listener. c. The listener adds the most popular
	 * label received to its memory.
	 */
	private void propogateMemorylabel() {

		// Create an array to hold all the nodeIds from 0 to (noOfVertices-1)
		int[] nodeId = new int[graph.getNumberVertices()];
		// Initilaize the array with values from 0 to (noOfVertices-1)
		for (int i = 0; i < nodeId.length; i++) {
			nodeId[i] = i;
		}

		// Loop iteration T number of times
		for (int i = 1; i <= iterations; i++) {
			System.out.println("starting Iteration " + i + " of SLPA.");
			// Rearrange the integer array to hold numbers in random order from
			// 0 to (noOfVertices-1) in it.
//			ShuffleArray(nodeId);
//			for (int j = 0; j < nodeId.length; j++) {
//				// One node is selected as a listener
//				Node listener = graph.getNode(j);
//				// Call listen so that all neighbours can speak to this node by
//				// sending selected label.
//				listener.listen();
//			}
			for (edge e : graph.sortgraph) {
				graph.graphADT.get(e.from).listen();
				graph.graphADT.get(e.to).listen();
			}
		}
	}

	/**
	 * Fisher鈥揧ates
	 * shuffle(http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle)
	 * function to shuffle the elements of a given integer array.
	 */
	private void ShuffleArray(int[] array) {
		int index;
		Random random = new Random();
		for (int i = array.length - 1; i > 0; i--) {
			index = random.nextInt(i + 1);
			if (index != i) {
				array[index] ^= array[i];
				array[i] ^= array[index];
				array[index] ^= array[i];
			}
		}
	}

	/**
	 * This function implements the post-processing based on the labels in the
	 * memories and the threshold r is applied to output the communities
	 */
	private void postProcessing() {

		// Get the map to hold the community label and set of nodes in that
		// community
		Map<Integer, Set<Integer>> community = graph.getOverlappingCommunities();// <社区id，包含节点id>
		// find the cluster each node of the graph belongs to.
		for (int i = 0; i < graph.getNumberVertices(); i++) {
			Node node = graph.getNode(i);
			// Get the memory map of the node with label as key
			// and count as value
			Map<Integer, Integer> memoryMap = node.getMemoryMap();
			// get the number of communities this node belongs to
			int noOfCommunities = node.getNoOfCommunities();
			// Iterate through the memory map
			for (Map.Entry<Integer, Integer> entry : memoryMap.entrySet()) {// labelId所属社区id
				Integer labelId = entry.getKey();
				Integer count = entry.getValue();
				// Calculate the ratio of the label count against total number
				// of communities
				double probalityDensity = (double) count / noOfCommunities;
				// If ration is greater than threhsold input, then add this node
				// to the community
				// identified by the label.
				// 使用阈值去掉出现次数较少的社区
				if (probalityDensity >= threshHold) {
					// Check if the label exits in the community map and if it
					// doesnt
					// exist create one for this label and add the node to the
					// set.
					if (community.get(labelId) == null) {
						Set<Integer> communitySet = new HashSet<Integer>();
						community.put(labelId, communitySet);
						communitySet.add(node.getNodeId());
					} else {
						Set<Integer> communitySet = community.get(labelId);
						communitySet.add(node.getNodeId());
					}
				}
			}
		}
	}

	// 对社区进行去重
	private List<Integer> clear() {
		Map<Integer, Set<Integer>> communities = graph.getOverlappingCommunities();
		OverNode=new HashMap<>();
		List<Integer> list = new LinkedList();
		for (Integer i : communities.keySet()) {
			list.add(i);
		}
		for (int i = 0; i < list.size(); i++) {
			int iflag=i;
			Set<Integer> set = communities.get(list.get(i));
			//社团去重
			for (int j = 0; j<list.size(); j++) {
				if(j==i) continue;
				Set<Integer> set2 = communities.get(list.get(j));
				if (set.size() <= set2.size()) {
					boolean flag = false;
					for (Integer integer : set) {
						if (!set2.contains(integer)) {
							flag = true;
							break;
						}
					}
					if (!flag){
						list.remove(i);
						i=i-1;
						break;
						
					}
				}
				
			}
			if(iflag!=i) continue;
			//找到每个节点所在的社团
			for (int j = 0; j < graph.vertices; j++) {
				if(set.contains(j)){
					Set<Integer> Nodeset=OverNode.get(j);
					if(Nodeset==null){
						Nodeset=new HashSet<>();
						OverNode.put(j, Nodeset);
					}
					Nodeset.add(list.get(i));
				}
			}
		}
		return list;
	}

	/**
	 * This method is used to ouput the communities to an output file.
	 */
	private void outputOverlappingCommunitiesToFile() {
		Writer fileWriter = null;
		BufferedWriter bufferedWriter = null;
		List<Integer> lit = clear();
		try {
			File file = new File(outputFile);
			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);

			// This consists of map of label and set of nodes in the community
			// identified by the label.
			Map<Integer, Set<Integer>> communities = graph.getOverlappingCommunities();
			int no = 0;
			// Iterate through the each community to output the community to the
			// output file line by line.
			int nums=0;
			for (Entry<Integer, Set<Integer>> entry : communities.entrySet()) {
				if (!lit.contains(entry.getKey()))
				continue;
				no++;
				bufferedWriter.write(entry.getKey() + ":");
				Set<Integer> nodeIdSet = entry.getValue();
				for (Integer node : nodeIdSet) {
					bufferedWriter.write(node.toString());
					bufferedWriter.write(" ");
					nums++;
				}
				bufferedWriter.newLine();
			}
			for (Entry<Integer, Set<Integer>> entry : OverNode.entrySet()) {
				if(entry.getValue().size()==1) continue;
				System.out.print(entry.getKey()+":");
				for (Integer integer : entry.getValue()) {
					System.out.print(integer+" ");
				}
				System.out.println();
			}
			System.out.println("There are totally " + no + " communities detected."+nums+","+graph.vertices);
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
