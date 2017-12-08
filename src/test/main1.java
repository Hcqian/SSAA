package test;

import java.util.Scanner;

public class main1 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int[] value = new int[n];
		int[] times = new int[n];
		for (int i = 0; i < 5; i++) {
			value[i] = scan.nextInt();
		}
		for (int i = 0; i < 5; i++) {
			times[i] = scan.nextInt();
		}
		int time = scan.nextInt();
		int[] dp = new int[time + 1];
		int max=0;
		for (int i = 0; i < n; i++) {
			int t=times[i];
			for (int j = time; j-t >=0; j--) {
				if(j-t==0||dp[j-t]>0) dp[j]=Math.max(dp[j], dp[j-t]+value[i]);
				if(dp[j]>max) max=dp[j];
			}
		}
		System.out.println(max);
	}
}
