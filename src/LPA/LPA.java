package LPA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import Modularity.Qfunction;

import java.util.Random;
import java.util.Set;
/**
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
 */
public class LPA {

	//This holds the input graph 
	Graph graph;
	
	//r: post-processing threshold
	double threshHold;//某个阈值
	
	//T : the user defined maximum iteration
	int iterations;//迭代次数
	
	//Output file to store the communities
	String outputFile;//输出的位置
	static double[] qs=new double[20];
	static int index=0;

	/*
	 * Constructor to create the graph from the given input.
	 */
	public LPA(String inputFileName, String outPutFileName,
			int inputIterations, double inputThreshHold) {
		threshHold = inputThreshHold;
		iterations = inputIterations;
		outputFile = outPutFileName;
		graph = new Graph(inputFileName);
	}
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 20; i++) {
			
		
		String inputFileName ="D:/data/tar/output1.21/nlastadj.txt"; //args[0];
		String outPutFileName ="D:/data/output/l.txt"; //args[1];
		int inputIterations = 10;//Integer.parseInt(args[2]);
		double inputThreshHold = 0.5;//Double.parseDouble(args[3]);
		LPA algorithm = new LPA(inputFileName, outPutFileName,
				inputIterations, inputThreshHold);
		algorithm.propogateMemorylabel();
		index++;
		}
		double alld=0;
		for (double d : qs) {
			System.out.println(d);
			alld+=d;
		}
		System.out.println(alld/20.0);
	}
	private void propogateMemorylabel() {
		
		int[] nodeId = new int[graph.getNumberVertices()];
		for (int i = 0; i < nodeId.length; i++) {
			nodeId[i] = i;
		}
		Qfunction q=new Qfunction();
		//Loop iteration T number of times
		for (int i = 1; i <=iterations; i++) {
			ShuffleArray(nodeId);
			for (int j = 0; j < nodeId.length; j++) {
				Node listener = graph.getNode(nodeId[j]);
				listener.listen();
			}
//			double dif=0;
//			for (int j = 0; j < nodeId.length; j++) {
//				Node node = graph.getNode(j);
//				int[] labels=node.getlLabels();
//				if(labels[0]==labels[1]) dif++;
//				labels[0]=labels[1];
//				
//			}
//			System.out.println(q.Qfunction(graph));
//			System.out.println(dif/(double)nodeId.length);
			qs[index]=q.Qfunction(graph);
		}
		this.show(nodeId.length); 
		
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
