import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import khh.string.util.StringUtil;


public class Test {

	

	public void s(String a){
		a+="v";
	}
	//String forward_url = "\\$\\{(\\w+)*(\\w+)*\\}";
	public static void main(String[] args) throws Exception {
		
//		String requestURI="/view/log/form/bbb/ddd/ccc/grg.jsp";
//		String requestURI="/view/log/form/bbb/ddd/ccc/grg.jsp";
		String requestURI="/user/profile.jpg";
//		String requestURI="/user/p${aa}rofi${vv}le.jp.g";
//		String requestURI="${bcd}1${bcd}2${bc}${d}3${bcd}";
//		String forward_url = "/view.*form/${visualkhh}/bbb.*\\.${html}";
		String forward_url = "${/WEB-INF/users/}/user/(.*).*";
//		String forward_url = ".*/user/(.+).*";
//		String forward_url = "/user/(.+)*.*";
//		String forward_url = "\\$\\{[.+]*\\}";
//		String forward_url = "\\$\\{[.]*\\}";

//		Pattern pattern = Pattern.compile(forward_url);
//		Matcher mm = pattern.matcher(requestURI);
//		while(mm.find()) {
//			System.out.println(mm.groupCount());
//			System.out.println(mm.group());
//			System.out.println(mm.group(1));
//		}
		
		
		System.out.println(StringUtil.transRegex(requestURI,forward_url));
		
//		HashMap<String,String> param = new HashMap<String,String>();
//		param.put("visu234alkhh", "zzzzzz");
//		System.out.println(StringUtil.transRegex(requestURI,forward_url,param));
		
		
		
		
//		String regex ="${/WEB-INF/}";
//		regex ="/view.*form/";
//		String regex =".*form/";
//		String[] m = StringUtil.findScope(requestURI,regex);
//		String[] m = StringUtil.findScope(requestURI,"/","/");
		
//		for (int i = 0; i < m.length; i++) {
//			System.out.println(m[i]);
//		}
//		System.out.println(m.group());
		
		
//		regex ="/bbb.*\\.";
//		m = StringUtil.find(requestURI,regex);
//		System.out.println(m.group());
		
		
//		String regex="\\$\\{([\\w\\.\\s]*)\\}";
//		String[] lsl = StringUtil.getScope(requestURI);
//		for (int i = 0; i < lsl.length; i++) {
//			System.out.println(lsl[i]);
//		}
		
		
		
		
		
	}

       
}
