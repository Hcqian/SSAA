package analysis;

public class WeightEdge{
	public String from;
	public String to;
	public  double dependence;
	public  double dweight; 
	public WeightEdge(String to,int weight) {
		this.to=to;
		this.dependence=weight;
	}
	public WeightEdge(String from,String to,double weight) {
		this.from=from;
		this.to=to;
		this.dweight=weight;
	}
	public void setDependence(double dependence) {
		this.dependence = dependence;
	}
	@Override
	public boolean equals(Object obj) {
		WeightEdge o=(WeightEdge) obj;
		//from=o.from;
		return to.equals(o.to);
	}
// 	@Override
//	public int compareTo(WeightEdge o) {
//		// TODO Auto-generated method stub
//		return Integer.compare(weight, o.weight);
//	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.from+","+this.to+","+this.dweight+","+this.dependence;
	}

}
