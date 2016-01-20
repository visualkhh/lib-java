package khh.httpclient.krx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import khh.db.terminal.DBTerminal;
import khh.debug.LogK;
import khh.httpclient.common.ConCre;

public class KRX {
	
	CloseableHttpClient httpclient = null;
	LogK log = LogK.getInstance();
	public KRX() {
		httpclient = HttpClients.createDefault();
	}
	
	
	public String getOPT(String bld,String name) throws ClientProtocolException, IOException{
//		String bld = "MKD%2F04%2F0406%2F04060100%2Fmkd04060100_01";
//		String name= "form";
		String url = "http://marketdata.krx.co.kr/contents/COM/GenerateOTP.jspx?bld="+bld+"&name="+name;
		log.debug("otp url:"+url);
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        String code ="";
        try {
            HttpEntity entity1 = response1.getEntity();
            log.debug(response1.getStatusLine()+"  "+entity1.getContentLength());
            StringBuffer r = getString(entity1.getContent());
            log.debug(r);
            code = r.toString();
            EntityUtils.consume(entity1);
        } finally {
            response1.close();
        };
        return code;
	}
	public void getCompany() throws UnsupportedOperationException, Exception{
		String bld="MKD%2F04%2F0406%2F04060100%2Fmkd04060100_02";
		String name="tablesubmit";
		String pagePath ="/contents/MKD/04/0406/04060100/MKD04060100.jsp";
		String postUrl = "http://marketdata.krx.co.kr/contents/MKD/99/MKD99000001.jspx";
		
		HttpPost httpPost = new HttpPost(postUrl);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("market_gubun", "ALL"));
		nvps.add(new BasicNameValuePair("code", getOPT(bld, name)));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response2 = httpclient.execute(httpPost);
		
		int tot_cnt=0;
		try {
          log.debug(response2.getStatusLine());
          HttpEntity entity2 = response2.getEntity();
          //StringBuffer r = getString(entity2.getContent());
          JSONParser parser = new JSONParser();
          JSONObject result = (JSONObject)parser.parse(new InputStreamReader(entity2.getContent()));
          JSONArray blockArr = (JSONArray)result.get("block1");
          JSONObject atData = (JSONObject) blockArr.get(0);
          tot_cnt =Integer.parseInt( (String)atData.get("tot_cnt") );
//          Iterator iterator = blockArr.iterator();
//	  		while (iterator.hasNext()) {
//	  			log.debug(iterator.next());
//	  		}
          EntityUtils.consume(entity2);
		} finally {
			response2.close();
		}
		
		int cnt = (int) (Math.ceil(tot_cnt/10.d));
		bld ="MKD/04/0406/04060100/mkd04060100_01";
		name ="form";
		ArrayList<Stock> slist = new ArrayList<Stock>();
		for (int i = 1; i <= cnt; i++) {
			httpPost = new HttpPost(postUrl);
			log.debug("-----"+i);
            nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("market_gubun", "ALL"));
            nvps.add(new BasicNameValuePair("curPage", String.valueOf(i)));
            nvps.add(new BasicNameValuePair("code", getOPT(bld, name)));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            response2 = httpclient.execute(httpPost);
            try {
                log.debug(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();
                JSONParser parser = new JSONParser();
                JSONObject result = (JSONObject)parser.parse(new InputStreamReader(entity2.getContent()));
                JSONArray blockArr = (JSONArray)result.get("block1");
                
                for (int j = 0; j < blockArr.size(); j++) {
                	Stock stock = new Stock();
                	stock.setNo				((String)((JSONObject)blockArr.get(j)).get("no			".trim()));
                	stock.setIsu_cd			((String)((JSONObject)blockArr.get(j)).get("isu_cd 		".trim()));
                	stock.setKor_cor_nm 	((String)((JSONObject)blockArr.get(j)).get("kor_cor_nm 	".trim()));
                	stock.setStd_ind_cd 	((String)((JSONObject)blockArr.get(j)).get("std_ind_cd 	".trim()));
                	stock.setStd_ind_nm 	((String)((JSONObject)blockArr.get(j)).get("std_ind_nm 	".trim()));
                	stock.setLst_stk_vl 	((String)((JSONObject)blockArr.get(j)).get("lst_stk_vl 	".trim()));
                	stock.setCpt 			((String)((JSONObject)blockArr.get(j)).get("cpt 		".trim()));
                	stock.setPar_pr 		((String)((JSONObject)blockArr.get(j)).get("par_pr 		".trim()));
                	stock.setIso_cd 		((String)((JSONObject)blockArr.get(j)).get("iso_cd 		".trim()));
                	stock.setTel_no 		((String)((JSONObject)blockArr.get(j)).get("tel_no 		".trim()));
                	stock.setAddr 			((String)((JSONObject)blockArr.get(j)).get("addr 		".trim()));
                	slist.add(stock);
				}
                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }
		}
		
		
		
		DBTerminal db = new DBTerminal(new ConCre());
		String query ="";
		query+=" INSERT INTO STOCK ( ";
		query+=" 		num,		isu_cd,		kor_cor_nm,		std_ind_cd,		std_ind_nm,";
		query+=" 		lst_stk_vl,	cpt,		par_pr,			iso_cd,			addr,			totcnt )";
		query+=" 		VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		db.setAutoCommit(false);
		for (int i = 0; i < slist.size(); i++) {
			Stock st = slist.get(i);
			ArrayList<String> param = new ArrayList<String>();
			param.add(st.getNo			());
			param.add(st.getIsu_cd		());
			param.add(st.getKor_cor_nm 	());
			param.add(st.getStd_ind_cd 	());
			param.add(st.getStd_ind_nm 	());
			param.add(st.getLst_stk_vl 	());
			param.add(st.getCpt 		());
			param.add(st.getPar_pr 		());
			param.add(st.getIso_cd 		());
			param.add(st.getTel_no 		());
			param.add(st.getAddr 		());
			db.executeUpdate(query, param);
		}
		db.commit();
	}
	
	@Override
	protected void finalize() throws Throwable {
		httpclient.close();
		super.finalize();
	}
	
	
	public static StringBuffer getString(InputStream i) throws IOException{
		BufferedReader rd = new BufferedReader(new InputStreamReader(i));
		StringBuffer b = new StringBuffer();
		String line=null;
		while ((line = rd.readLine()) != null) {
			b.append(line);
		}
		return b;
	}
	
	public static void main(String[] args) throws Exception {
		KRX krx=new KRX();
		krx.getCompany();
	}
}
