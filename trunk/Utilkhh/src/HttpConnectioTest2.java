import java.util.HashMap;

import khh.html.client.HttpKClient;


public class HttpConnectioTest2 {
public static void main(String[] args) throws Exception {
	HttpKClient client = new HttpKClient();
	
	String g = client.requestGet("https://twitter.com/");
	System.out.println(g);
	
	
	
	System.out.println("-----------------------------");
	
	
	HashMap map  =  new HashMap();
	map.put("session[username_or_email]", "visualkhh@gmail.com");
	map.put("session[password]", "Rnadmfdnlgot01");
	g = client.requestPost("https://twitter.com/sessions/",map);
	
	System.out.println(g);
	
	
	
	
	
	
	

}
}
