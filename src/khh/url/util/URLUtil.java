package khh.url.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import khh.property.util.PropertyUtil;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class URLUtil
{
	public static JSONObject getJson(String urlStr,String charSet) throws MalformedURLException{
		return getJson( new URL(urlStr),charSet);
	}
	public static JSONObject getJson(String urlStr) throws MalformedURLException{
		return getJson( new URL(urlStr),"UTF-8");
	}
	public static JSONObject getJson(URL url,String charSet){
		JSONObject object=null;
		try{
		// 한글 처리를 위해 InputStreamReader를 UTF-8 인코딩으로 감싼다.
		InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), charSet);

		// JSON을 Parsing 한다. 문법오류가 날 경우 Exception 발생, without Exception -> parse 메소드
		object = (JSONObject)JSONValue.parse(isr);
		// 객체
//		JSONObject channel = (JSONObject)(object.get("channel"));
//		// item 배열
//		JSONArray items = (JSONArray)channel.get("item");
//
//		for(int i = 0 ; i < items.size(); i++) {
//			JSONObject obj1 = (JSONObject)items.get(i);			
//			System.out.println(obj1.get("title").toString());
//		}
		isr.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return object;
		
	}
	
	
	
	
	public static String getURLString(String urlStr,String charset) throws MalformedURLException, IOException{
		return getURLString(new URL(urlStr),charset);
	}
	public static String getURLString(String urlStr) throws MalformedURLException, IOException {
		   return getURLString(new URL(urlStr),"UTF-8");
	}
	public static String getURLString(URL url,String charset) throws IOException{
	
		/*
		
		String returnvalue="";
		//url = new URL("http://jmkim.com/utf8test.txt");
//	        System.out.println("url=["+url+"]");
//	        System.out.println("protocol=["+url.getProtocol()+"]");
//	        System.out.println("host=["+url.getHost()+"]");
//	        System.out.println("port=["+url.getPort()+"]");
//	        System.out.println("file=["+url.getFile()+"]");
//	        System.out.println("ref=["+url.getRef()+"]");
//	            System.out.println("content=["+url.getContent()+"]");
		InputStream is = url.openStream();
		InputStreamReader inputreader = new InputStreamReader(is,charset);
//		InputStreamReader inputreader = new InputStreamReader(is);
		BufferedReader in = new BufferedReader(inputreader);
		String inputLine;
		int count=0;
		while ((inputLine = in.readLine()) != null) {
			if(count!=0)
				returnvalue += '\n';
			
//	            	System.out.println(inputLine);
			returnvalue += inputLine;
			count++;
		}
		in.close();
//	            int ch;
//	            
//	            while ( (ch = is.read()) != -1 ) {  
////	                System.out.print( (char)ch );
//	            	returnvalue+=(char)ch ;
//	            }
		return returnvalue;
		
		*/
		
		//finger
		StringBuffer buffer = new StringBuffer();
		InputStream in = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in,charset));
		String line = null;

		while ( (line = br.readLine()) != null ){
			buffer.append(line);
			buffer.append(PropertyUtil.getSeparator());
		}

		return buffer.toString();
	}
	
	
	public static InputStream getInputStream(String url) throws MalformedURLException, IOException  {
		URL urlo = new URL(url);
		InputStream is = urlo.openStream();
		
		return is;
	}
	public static InputStream getInputStream(URL url) throws IOException  {
		InputStream is = url.openStream();
		return is;
	}
	
}
