package Modularity;

import java.util.Map;

import WDLP.Graph;
import WDLP.Node;

public class Qfunction {
	Graph graph;
	public double Qfunction(LPA.Graph g){
		double q=0;
		double ouhe=0;
		double w=allWeight(g);
		for (LPA.Node i : g.graphADT.values()) {
			for (LPA.Node j : g.graphADT.values()) {
				if(!i.equals(j)){
					int iscommen=KroneckerII(i, j);
					Double dw=i.neighbhours.get(j);
					Double Wij=0.0;
					if(dw!=null)Wij=dw;
					q+=(Wij-computeWeightII(i.neighbhours)*computeWeightII(j.beneighbhours)/(w))*iscommen;
					if(iscommen==0) ouhe+=Wij;
				}
			}
		}
		return q/w;
//		return ouhe/w;
	}
	public double Qfunction(Graph g){ 
		double q=0;
		double ouhe=0;
		double w=allWeight(g);
		for (Node i : g.graphADT.values()) {
			for (Node j : g.graphADT.values()) {
				if(!i.equals(j)){
					int iscommen=Kronecker(i, j);
					Double[] dw=i.neighbhours.get(j);
					Double Wij=0.0;
					if(dw!=null)Wij=dw[0];
					q+=(Wij-computeWeight(i.neighbhours)*computeWeight(j.beneighbhours)/(w))*iscommen;
					if(iscommen==0) ouhe+=Wij;
				}
			}
		}
		return q/w;
//		return ouhe/w;
	}
	/**
	 * 
	 * 克罗内克函数 
	 * 两节点在一个社团内返回 1，否则为 0
	 */
	public int Kronecker(Node n1,Node n2){
		if(n1.getlLabels()[0]==n2.getlLabels()[0]){
		return 1;
		}else {
			return 0;
		}
	}
	public int KroneckerII(LPA.Node n1,LPA.Node n2){
		if(n1.getlLabels()[0]==n2.getlLabels()[0]){
		return 1;
		}else {
			return 0;
		}
	}
	
	public double allWeight(LPA.Graph g){
		double allWeight=0;
		for (LPA.Node n : g.graphADT.values()) {
			for (Double w : n.getNeighbhours().values()) {
				allWeight+=w;
			}
		}
		return allWeight;
	}
	
	
	/**
	 * 计算所有权重
	 * @param g
	 * @return
	 */
	public double allWeight(Graph g){
		double allWeight=0;
		for (Node n : g.graphADT.values()) {
			for (Double[] w : n.getNeighbhours().values()) {
				allWeight+=w[0];
			}
		}
		return allWeight;
	}
	/**
	 * 计算出入度权重
	 * @param beneighbhours
	 * @return
	 */
	public double computeWeight(Map<Node, Double[]> beneighbhours){
		double allWieght=0;
		for ( Double[] w : beneighbhours.values()) {
			allWieght+=w[0];
		}
		return allWieght;
	}
	public double computeWeightII(Map<LPA.Node, Double> beneighbhours){
		double allWieght=0;
		for ( Double w : beneighbhours.values()) {
			allWieght+=w;
		}
		return allWieght;
	}
	
}
