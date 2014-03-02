import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import khh.html.client.HttpKClient;

public class HttpConnectionTest {
	public static void main(String[] args) throws Exception {
		
		

		
		
		
		
		
		   Properties prop = new Properties();
		   prop.setProperty("a", "aparamValue1");
		   prop.setProperty("b", "김현하");
		   String encodedString = encodeString(prop);
		   if(encodedString!=null && encodedString.length()>0){
			   encodedString = "?"+encodedString;
		   }
		HttpURLConnection con = getHttpConnection("http://localhost:8080/test/index.jsp"+encodedString);
		
		InputStream is = con.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
        char[] buff = new char[512];
        int len = -1;
        while( (len = br.read(buff)) != -1) {
           System.out.print(new String(buff, 0, len));
        }
        br.close();
		
        
 	   con.connect();
 	   String cookies = "";
 	   Map m = con.getHeaderFields();
 	   if(m.containsKey("Set-Cookie")) {
 	    Collection c =(Collection)m.get("Set-Cookie");
 	    for(Iterator i = c.iterator(); i.hasNext(); ) {
 	     cookies += (String)i.next() + ", ";
 	    }
 	    System.out.println(cookies);
 	   }
        
		con = (HttpURLConnection)getHttpConnection("http://localhost:8080/test/index.jsp");
//		con = (HttpURLConnection)getHttpConnection("http://localhost:8080/test/index.jsp"+encodedString);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("cookie", cookies);
		con.setDoOutput(true);
		con.setInstanceFollowRedirects(false);
		
		DataOutputStream out =null;
	   try {
		      out = new DataOutputStream(con.getOutputStream());
		      encodedString = encodeString(prop);
		      out.writeBytes(encodedString);
		      out.flush();
		   } finally {
		      if (out != null) out.close();
		   }
		
		
	   con.connect();
		is = con.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));
        buff = new char[512];
        len = -1;
        while( (len = br.read(buff)) != -1) {
           System.out.print(new String(buff, 0, len));
        }
        br.close();
	}

	
	
	public static HttpURLConnection getHttpConnection(String uri,String method) throws Exception{
		HttpURLConnection con = getHttpConnection(uri);
		con.setRequestMethod(method);
		return con;
	}
	public static HttpURLConnection getHttpConnection(String uri) throws Exception{
		URL url = new URL(uri);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		return con;
	}
	
	 public static String encodeString(Properties params) {
	      StringBuffer sb = new StringBuffer(256);
	      Enumeration names = params.propertyNames();
	      
	      while (names.hasMoreElements()) {
	         String name = (String) names.nextElement();
	         String value = params.getProperty(name);
	         sb.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value) );
	         
	         if (names.hasMoreElements()) sb.append("&");
	      }
	      return sb.toString();
	   }
}
