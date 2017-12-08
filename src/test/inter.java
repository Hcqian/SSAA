package test;

public interface inter {
    int a=0;
	public default int df(){
		return 1;
	}
	public static String sta(){
		return "static";
	}
	public void nomal();
}
