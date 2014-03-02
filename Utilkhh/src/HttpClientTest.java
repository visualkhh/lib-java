import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import khh.html.client.HttpKClient;


public class HttpClientTest {
	public static void main(String[] args) throws ClientProtocolException, IOException, InterruptedException {
			

		HttpKClient client = new HttpKClient();
		HttpPost httpPost = new HttpPost("http://localhost:8080/test/index.jsp");
	   List<NameValuePair> parameters = new ArrayList<NameValuePair>(); 
	   parameters.add(new BasicNameValuePair("a", "avalue")); 
	   parameters.add(new BasicNameValuePair("b", "bvalue"));

		
	   UrlEncodedFormEntity entityRequest = new 
	   UrlEncodedFormEntity(parameters, "EUC-KR");
	   httpPost.setEntity(entityRequest);
		
//		HttpPost method = new HttpPost("http://localhost:8080/test/index.jsp");
//		method.getParams().setParameter("a", "hhh");
//		method.setHeader(FilesConstants.X_AUTH_TOKEN, authToken);
	   HttpResponse responsePost = client.execute(httpPost);
	   HttpEntity resEntity = responsePost.getEntity();
	   resEntity.getContent();
		
//		String g = client.executeGetToString("http://localhost:8080/test/index.jsp?a=1");
//		System.out.println(g);
	}
}
