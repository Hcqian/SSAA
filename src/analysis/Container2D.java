package analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.collections.comparators.ComparableComparator;
//存储边的值计算rank
// 直接计算排名  还是 积分制 分数怎么定
public class Container2D {
	//private List<Integer> ele=null;
	private List<List<WeightEdge>> list;//使用的是rank->element的指向,存储element得到名次的次数
	public Container2D() {
		//ele=new ArrayList<>();
		list=new ArrayList<>();
	}
//	public void add(int element,int rank){//element指代节点的代号，rank此次该节点的排名
//		//if(ele.contains(element)){//不含该节点
//			//ele.add(element);
//		if(list.size()<rank){
//			list.add(new ArrayList());
//		}
//		List<WeightEdge> s=list.get(rank);
//		boolean flag=false;
//		for (WeightEdge we : s) {
//			if(we.to==element){
//				we.weight++;
//				flag=true;
//			}
//		}
//		if(!flag){
//			s.add(new WeightEdge(element, 1));
//		}
////		if(ele.contains(element)){
////			matrix[ele.indexOf(element)][rank-1]++;
////		}else{
////			ele.add(element);
////			expand(rank);
////		}
//	}
	
//	public Set<Integer>[] computeRank(){
//		Set<Integer>[] rank=new Set[list.size()];
//		Stack<WeightEdge> stack=new Stack<>();
//		int r=0;
//		Comparator<WeightEdge> c=new Comparator<WeightEdge>() {
//			
//			@Override
//			public int compare(WeightEdge o1, WeightEdge o2) {
//				if(o1.weight>o2.weight)
//				return 1;
//				else if(o1.weight==o2.weight)
//				return 0;
//				else
//				return -1;
//			}
//		};
//		for (List<WeightEdge> set : list) {//遍历每个名次中 element出现的次数
//			rank[r]=new HashSet<>();
//			//把上一个rank遗留的边加到加到下一个
//			while(!stack.isEmpty()){
//				WeightEdge s=stack.pop();
//				for (WeightEdge w: set) {
//					if(w.to==s.to)w.weight+=s.weight;
//				}
//			}
//			//找出rank中ele的第一名
//			Collections.sort(set, c);
//			int num=set.get(set.size()-1).weight;
//			//找到第一加入rank，其余的加入栈，放到下一个rank中
//			for (int i = set.size()-1; i >=0; i--) {
//				if(set.get(i).weight==num) rank[r].add(set.get(i).to);
//				else stack.push(set.get(i));
//			}
//			//？？？？？？？？已经选走的ele遗留在下一个rank中的问题
//			r++;
//		}
//		return rank;
//	}
	
	
//	private void expand(int rank){
//		int[][] exMatrix=new int[ele.size()][ele.size()];
//		for (int i = 0; i < matrix.length; i++) {
//			for (int j = 0; j < matrix.length; j++) {
//				exMatrix[i][j]=matrix[i][j];
//			}
//		}
//		exMatrix[ele.size()-1][rank-1]++;
//		matrix=exMatrix;
//	}

}
