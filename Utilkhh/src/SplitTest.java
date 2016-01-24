import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import khh.string.util.StringUtil;

public class SplitTest {
	
	

	public static String[] splitScope(String s, String regex){
		
		ArrayList<String> list = new ArrayList<String>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s); // get a matcher object
		
		String ss = s;
		int start=0;
		while(m.find()) {
//			list.add(m.group());
			list .add( s.substring(start,m.start()) );
			start = m.end();
//			s = s.substring(m.end(),ss.length()-5);
		     System.out.println("start(): "+m.start());
		 	System.out.println("end(): "+m.end());
		}
		String[] a = new String[list.size()];
		list.toArray(a);
		return a;
	}
	//String str ="aasdasd${asdasd} vVav ${bbb} 234 ${vvvv}";
	//String[] l = split(str, "\\$\\{[\\w\\.\\s]+\\}");
	public static String[] getScope(String s, String regex){
		
		ArrayList<String> list = new ArrayList<String>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s); // get a matcher object
		while(m.find()) {
			list.add(m.group());
//		     System.out.println("start(): "+m.start());
//		 	System.out.println("end(): "+m.end());
		}
		String[] a = new String[list.size()];
		list.toArray(a);
		return a;
	}
	public static void main(String[] args) {
		
		String str ="aasdasd${asdasd}-vVav+${bbb}z234v${vvvv}";
//		String[] l = getScope(str, "\\$\\{[\\w\\.\\s]+\\}");
//		for (int i = 0; i < l.length; i++) {
//			System.out.println(l[i]);
//		}
		
		String[] l = splitScope(str, "\\$\\{[\\w\\.\\s]+\\}");
		System.out.println("--------");
		for (int i = 0; i < l.length; i++) {
			System.out.println(l[i]);
		}
		
		
		
////		String[] l = str.split("\\$",2);
////		
////		System.out.println("============");
////		
//		l = StringUtil.split(str, "$");
//		for (int i = 0; i < l.length; i++) {
//			System.out.println(l[i]);
//		}
//		
//		ArrayList<String> a = new ArrayList<String>();
	}
}
