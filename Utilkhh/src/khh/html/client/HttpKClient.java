package khh.html.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import khh.property.util.PropertyUtil;



public class HttpKClient {
	String cookie = null;
	HttpURLConnection connection = null;
	
	public String requestGet(String uri) throws IOException{
		return requestGet(uri,null);
	}
	public String requestGet(String uri,HashMap param) throws IOException{
		
		
		HttpURLConnection con = getHttpURLConnection(uri, param, "GET");
		return read(con);
	}
	public String requestPost(String uri) throws IOException{
		return requestPost(uri,null);
	}
	public String requestPost(String uri,HashMap param) throws IOException{
		HttpURLConnection con = getHttpURLConnection(uri, param, "POST");
		
		
		 return read(con);
	}
	
	
	private HttpURLConnection getHttpURLConnection(String uri,HashMap param, String method) throws IOException {

		HttpURLConnection con = null;
		
		if("POST".equals(method)){
			URL url = new URL(uri);
			con = (HttpURLConnection)url.openConnection();

			con.setRequestMethod("POST");
//			con.setDoInput(true);
			con.setDoOutput(true);
//			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setInstanceFollowRedirects(false);
			con.setUseCaches(false);
			
			

		}else{
			String param_str = paramEncode(param);
			if(param_str!=null && param_str.length()>0){
				param_str = "?"+param_str;
			}
			URL url = new URL(uri+param_str);
			con = (HttpURLConnection)url.openConnection();
			con.setUseCaches(false);
		}
		
		
		if(cookie==null){
			   Map m = con.getHeaderFields();
			   if(m.containsKey("Set-Cookie")) {
				Collection c =(Collection)m.get("Set-Cookie");
				cookie="";
				for(Iterator i = c.iterator(); i.hasNext(); ) {
					cookie += (String)i.next() + ", ";
			    }
			   }
			   System.out.println(cookie);
		}else{
			System.out.println(cookie);
			con.setRequestProperty("cookie", cookie);
		}
		
		
		
		
		if("POST".equals(method)){
			DataOutputStream out = null;
			try {
				out = new DataOutputStream(con.getOutputStream());
				out.writeBytes(paramEncode(param));
				out.flush();
			} finally {
				if (out != null) out.close();
			}
		}
		
		
		
		return con;
	}
	
	private String paramEncode(HashMap param){
		
		String s = "";
		if(param!=null){
			Iterator iter = param.keySet().iterator();
			while (iter.hasNext()) {
				Object key = iter.next();
				param.put(key,URLEncoder.encode((String) param.get(key)));
			}
			
//			StringTokenizer h = new StringTokenizer("&");
//				h.makeString(param);
		}
		return s;
	}
	
	private String read(HttpURLConnection con) throws IOException{
		
		
		
		con.connect();
		InputStream is = con.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		StringBuffer buffer = new StringBuffer();
		while ( (line = br.readLine()) != null ){
			buffer.append(line);
			buffer.append(PropertyUtil.getSeparator());
		}
		br.close();
		con.disconnect();
		
		return buffer.toString();
	}
	
}
