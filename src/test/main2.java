package test;

import java.util.ArrayList;
import java.util.List;

public class main2 {

	public static void main(String[] args) {
		main2 m = new main2();
		m.puoyi("12", 0, new StringBuffer());
		for (StringBuffer string : m.list) {
			System.out.println(string);
		}
	}

	List<StringBuffer> list = new ArrayList<>();

	public void puoyi(String s, int index, StringBuffer sb) {
		if (index >= s.length()) {
			list.add(sb);
			return;
		}
		char[] cs = s.toCharArray();
		if (cs[index] == '0')
			return;

		char c = (char) (cs[index] + 'a' - '1');
		StringBuffer nsb = new StringBuffer(sb);
		nsb.append(c);
		puoyi(s, index + 1, nsb);
		if (index + 1 < s.length()) {
			StringBuffer num = new StringBuffer();
			num.append(cs[index]);
			num.append(cs[index + 1]);
			int number = Integer.parseInt(num.toString());
			if (number <= 26) {
				c = (char) (number + 'a' - 1);
				sb.append(c);
				puoyi(s, index + 2, sb);
			}

		}
	}
}
