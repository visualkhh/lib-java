import java.util.HashMap;

import khh.string.token.StringTokenizer;


public class GA {
	public static void main(String[] args) {
		StringTokenizer h = new StringTokenizer("&");
		HashMap map = new HashMap();
		map.put("a", "a");
		map.put("c", "c");
		String s = h.makeString(map);
		System.out.println(s);
		
	}
}
