package khh.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Test {
	
	public static StringBuffer getString(InputStream i) throws IOException{
		BufferedReader rd = new BufferedReader(new InputStreamReader(i));
		StringBuffer b = new StringBuffer();
		String line=null;
		while ((line = rd.readLine()) != null) {
			//System.out.println(line);
			b.append(line);
		}
		return b;
	}
	
	public static void main(String[] args) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet("http://marketdata.krx.co.kr/contents/COM/GenerateOTP.jspx?bld=MKD%2F04%2F0406%2F04060100%2Fmkd04060100_01&name=form");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager.
            String code ="";
            try {
                HttpEntity entity1 = response1.getEntity();
                System.out.println(response1.getStatusLine()+"  "+entity1.getContentLength());
                StringBuffer r = getString(entity1.getContent());
                System.out.println(r);
                code = r.toString();
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity1);
            } finally {
                response1.close();
            }

            
           
            HttpPost httpPost = new HttpPost("http://marketdata.krx.co.kr/contents/MKD/99/MKD99000001.jspx");
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("market_gubun", "ALL"));
            nvps.add(new BasicNameValuePair("isu_cdnm", "전체"));
            nvps.add(new BasicNameValuePair("isu_cd", ""));
            nvps.add(new BasicNameValuePair("isu_nm", ""));
            nvps.add(new BasicNameValuePair("isu_srt_cd", ""));
            nvps.add(new BasicNameValuePair("sort_type", "A"));
            nvps.add(new BasicNameValuePair("std_ind_cd", ""));
            nvps.add(new BasicNameValuePair("par_pr", ""));
            nvps.add(new BasicNameValuePair("cpta_scl", ""));
            nvps.add(new BasicNameValuePair("sttl_trm", ""));
            nvps.add(new BasicNameValuePair("lst_stk_vl", "1"));
            nvps.add(new BasicNameValuePair("in_lst_stk_vl", ""));
            nvps.add(new BasicNameValuePair("in_lst_stk_vl2", ""));
            nvps.add(new BasicNameValuePair("cpt", "1"));
            nvps.add(new BasicNameValuePair("in_cpt", ""));
            nvps.add(new BasicNameValuePair("in_cpt2", ""));
            nvps.add(new BasicNameValuePair("pagePath", "/contents/MKD/04/0406/04060100/MKD04060100.jsp"));
            nvps.add(new BasicNameValuePair("code", code));
            
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            try {
                System.out.println(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();
                StringBuffer r = getString(entity2.getContent());
                System.out.println(r);
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }
        } finally {
            httpclient.close();
        }
	}
}
