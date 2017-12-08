package test;


public class main {
	public static void main(String[] args) {
		main.getString("");
	}
	public static void getString(String s){
		String ss="_";
		byte[] by=new byte[s.length()];
		char[] cs=s.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			char c=cs[i];
			if(c>='0'&&c<='9') by[i]=0;
			else if(c>='a'&&c<='z') {
				by[i]=1;
			}else if(c>='A'&&c<='Z') {
				by[i]=2;
			} else {
				by[i]=-1;
			}
		}
		byte flag=by[cs.length-1];
		for (int i = cs.length-1; i >=0; i--) {
			if(by[i]==-1){ 
				ss="_"+ss;flag=by[i-1]; continue;}
			if(flag==by[i]){
				ss=cs[i]+ss;
				flag=by[i];
			}else if(by[i]==2&&flag==1) {
				if(i==0) ss=cs[i]+ss;
				else{
				ss="_"+cs[i]+ss;
				flag=by[i-1];}
			}else {
				ss=cs[i]+"_"+ss;
				flag=by[i];
			}
			
			
		}
		System.out.println("_"+ss.toUpperCase());
	}
}
