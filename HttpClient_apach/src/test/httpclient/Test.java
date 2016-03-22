package test.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Test {
	CloseableHttpClient httpclient = null;
	public Test() {
		httpclient = HttpClients.createDefault();
	}

	public void start() throws UnsupportedOperationException, IOException {
		String postUrl = "http://192.168.0.95:8080";
		HttpPost httpPost = new HttpPost(postUrl);
//		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//		nvps.add(new BasicNameValuePair("market_gubun", "ALL"));
//		nvps.add(new BasicNameValuePair("code", getOPT(bld, name)));
//		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response2 = httpclient.execute(httpPost);
		int tot_cnt = 0;
		try {
			System.out.println(response2.getStatusLine());
			HttpEntity entity2 = response2.getEntity();
			StringBuffer r = getString(entity2.getContent());
			System.out.println(r);
			
			
			postUrl = "http://192.168.0.95:8080/view/gis/opengis";
			httpPost = new HttpPost(postUrl);
			response2 = httpclient.execute(httpPost);
			System.out.println(response2.getStatusLine());
			entity2 = response2.getEntity();
			r = getString(entity2.getContent());
			System.out.println(r);

			// StringBuffer r = getString(entity2.getContent());
//			JSONParser parser = new JSONParser();
//			JSONObject result = (JSONObject) parser.parse(new InputStreamReader(entity2.getContent()));
//			JSONArray blockArr = (JSONArray) result.get("block1");
//			JSONObject atData = (JSONObject) blockArr.get(0);
//			tot_cnt = Integer.parseInt((String) atData.get("tot_cnt"));
			EntityUtils.consume(entity2);
		} finally {
			response2.close();
		}
	}

	public StringBuffer getString(InputStream i) throws IOException{
		BufferedReader rd = new BufferedReader(new InputStreamReader(i));
		StringBuffer b = new StringBuffer();
		String line=null;
		while ((line = rd.readLine()) != null) {
			b.append(line);
		}
		return b;
	}
	public static void main(String[] args) throws UnsupportedOperationException, IOException {
		new Test().start();
	}
}
