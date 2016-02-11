package khh.test.regex;

import java.util.HashMap;

import khh.string.util.StringUtil;

public class RegexTest {
public static void main(String[] args) {
//	String regex = "^public .* .*(.*)" ;
//	String regex = "^public .* khh.web.jsp.framework.compact.controller.URIDivisionService.*(.*)" ;
//	String str = "public java.lang.String khh.web.jsp.framework.compact.controller.URIDivisionService.doRequest(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse) throws java.lang.Exception";
//	System.out.println(StringUtil.isMatches(str, regex));
//	<join url=""  forward="${'/WEB-INF'}/ouser/[0-9]+/${'profile.png'}"  type="new">
	String url = "/ouser/profile.png?u=1";
	
	System.out.println(StringUtil.isMatches(url, "^/ouser/profile.png\\?u=[0-9]+"));
	//System.out.println("^/ouser/profile.png\\?.*");
	
	String regex="${'/WEB-INF/'}${param.u}${'/profile.png'}";
	
	System.out.println(regex);
	
	
	HashMap<String,String> map = new HashMap<>();
	map.put("param.u","zzzz");
	String a = StringUtil.transRegex(url, regex,map);
	System.out.println(a);
}
}
