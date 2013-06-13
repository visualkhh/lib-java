import java.net.Inet4Address;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wpg.proxy.HttpMessageHandler;
import com.wpg.proxy.HttpMessageRequest;
import com.wpg.proxy.HttpMessageResponse;
import com.wpg.proxy.Proxy;
import com.wpg.proxy.ProxyRegistry;
public class ProxyServer {

	 public static void main(String[] args) throws Exception{
		 System.out.println(InetAddress.getLocalHost().getHostAddress());
		 
		 String ip=InetAddress.getLocalHost().getHostAddress();
		 int port=9999;
		 if(args.length>=2){
			 ip=args[0];
			 port=Integer.parseInt(args[1]);
		 }
		 
		 
	        Proxy p = new Proxy(Inet4Address.getByName(ip), port, 0);
	        final DateFormat f = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	        ProxyRegistry.addHandler(new HttpMessageHandler(){
	            public void failed(Exception exception) {} // 안 쓰는 메소드들.
	            public void failedRequest(HttpMessageRequest request,
	                    Exception exception) {}
	            public void failedResponse(HttpMessageResponse response,
	                    HttpMessageRequest request, Exception exception) {}
	            public void receivedResponse(HttpMessageResponse response,
	                    HttpMessageRequest request) {
	            	System.out.println(response);
	            	System.out.println(request);
	            	return;
	            }

	            public void receivedRequest(HttpMessageRequest request) {
	            	if(request.getUri().toString().equals("http://printsecurity.shinhan.com/servlet/Servlet.GetUpdateInfoServlet")){
	            		return;
	            	}
	                // 입맛에 맞게 꾸며주세요.
	                System.out.println("user: " + request.getFromHost());
	                System.out.println("url: " + request.getUri());
	                System.out.println("datetime: " + f.format(new Date()));
	            }});
	        p.start();
	    }
}
