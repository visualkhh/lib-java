import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientTest {
	public static void main(String[] args) throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://www.vogella.com");
		HttpResponse response = client.execute(request);

		// Get the response
		BufferedReader rd = new BufferedReader
		  (new InputStreamReader(response.getEntity().getContent()));
		    
		String line = "";
		while ((line = rd.readLine()) != null) {
		  textView.append(line);
		} 

	}
}
