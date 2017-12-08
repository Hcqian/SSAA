package analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeNode {
	private String name = null;
	public int call = 1;
	// private Map<TreeNode,Integer> children=null;
	private List<TreeNode> children = null;
	public  Set<String> allChld=null;

	public TreeNode(String name) {
		this.name = name;
		// children=new HashMap<>();
		children=new ArrayList<>();
		allChld=new HashSet<>();
	}
	public List<TreeNode> getChildren() {
		return children; 
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return name.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		TreeNode o=(TreeNode)obj;
		return name.equals(o.name);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCall() {
		return call;
	}
	public void setCall(int call) {
		this.call = call;
	}
	public TreeNode addChild(String s) {
		for (TreeNode treeNode : children) {
			if (treeNode.name.equals(s)) {
				treeNode.call += 1;
				return treeNode;
			}
		}
		if(s.equals(this.name)){
			this.call++;
			return this;
		}
		TreeNode t=new TreeNode(s);
		children.add(t);
		return t;
	}

}
