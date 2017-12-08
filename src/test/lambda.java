package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class lambda {

	public static void main(String[] args) {
		List<Double> l=new ArrayList<>();
		l.sort((Double d1,Double d2)->d2.compareTo(d1));
		l.add(10.0);
		l.add(2.0);
		Map<Double,Integer> m=new TreeMap<>();
		m.put(2.0, 2);
		m.put(10.0, 1);
		System.out.println(m);
	}

}
