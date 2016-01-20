package khh.httpclient.twitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ibm.icu.impl.ICUService.Key;
import com.mysql.jdbc.log.Log;

import khh.date.util.DateUtil;
import khh.db.terminal.DBTerminal;
import khh.db.terminal.resultset.DBTResultSetContainer;
import khh.debug.LogK;
import khh.httpclient.common.ConCre;
import khh.string.util.StringUtil;

public class TimeLine extends Thread{
	CloseableHttpClient httpclient = null;
	LogK log = LogK.getInstance();
	DBTerminal db = null;
	LinkedBlockingQueue<String> queue;
	public TimeLine(LinkedBlockingQueue queue) {
		this.queue=queue;
		httpclient = HttpClients.createDefault();
//		String keyword
//		log.debug("start Twitter : "+keyword);
//		this.keyword=keyword;
		db = new DBTerminal(new ConCre());
	}
	
	public String getLastTwitt(String keyword) throws Exception{
		String query="";
		query+=" SELECT NUM, CNT, CONTENT, INSERT_DATE FROM TIMELINE  ";
		query+=" WHERE NUM = (SELECT NUM FROM STOCK WHERE KOR_COR_NM ='"+keyword+"') ORDER BY CNT DESC";
		query+=" LIMIT 1";
		DBTResultSetContainer r = db.executeQuery(query);
		String content=null;
		for (int i = 0; i < r.size(); i++) {
			content = r.get(i).getString("CONTENT");
		}
		return content;
	}
	
	
	public StringBuffer getString(InputStream i) throws IOException{
		BufferedReader rd = new BufferedReader(new InputStreamReader(i));
		StringBuffer b = new StringBuffer();
		String line=null;
		while ((line = rd.readLine()) != null) {
			//log.debug(line);
			b.append(line);
		}
		return b;
	}
	public void run(){
		int wcnt=0;
		while(true){
			String keyword = null;
			wcnt++;
			try{
				keyword = queue.take();
				Thread.sleep(1000);
				String lastTwitt = getLastTwitt(keyword);
				log.debug("Twitter ("+this.getName()+")  workCnt("+wcnt+") Start "+keyword+"  lastTwitt :"+lastTwitt);
		//		String query="일본이야 눈 내리거나 비 오거나 날씨가 안정되지";//%ED%95%9C%EA%B5%AD&
				String query=keyword;//%ED%95%9C%EA%B5%AD&
				query =StringUtil.urlEncode(query, StringUtil.SET_UTF_8); 
		//		log.debug(query);
				String url = "https://twitter.com/search?f=tweets&vertical=default&q="+query+"&src=typd";
				log.debug("otp url:"+url);
		        HttpGet httpGet = new HttpGet(url);
		        CloseableHttpResponse response1 = httpclient.execute(httpGet);
		        try {
		            HttpEntity entity1 = response1.getEntity();
		            log.debug(response1.getStatusLine()+"  "+entity1.getContentLength());
		            StringBuffer r = getString(entity1.getContent());
		            Document doc = Jsoup.parse(r.toString());
		            Elements rows = doc.select("#stream-items-id>li .content");
		            Collections.reverse(rows);
		
		            ArrayList<Twitt> twittlist = new ArrayList<Twitt>(); 
		            for (int i = 0 ; i < rows.size();i++) {
		            	Element row = rows.get(i);
		            	String id = row.select(".stream-item-header b").text();
		            	String content = row.select("p").text();
		            	
		            	if(null!=lastTwitt && lastTwitt.trim().equals(content.trim())){
		            		log.debug("LastTwitt !! "+lastTwitt+" break;");
		            		twittlist= new ArrayList<Twitt>();
		            	}else{
		            		twittlist.add(new Twitt(id,content));
		            	}
		              }
		            
		            db.setAutoCommit(false);
		            for (int i = 0; i < twittlist.size();i++) {
		            	Twitt twitt = twittlist.get(i);
		            	ArrayList<String> param= new ArrayList();
		            	param.add(keyword);
		            	param.add(twitt.getId());
		            	param.add(twitt.getContent());
		            	
		            	DBTResultSetContainer rs = db.executeQuery("SELECT * FROM TIMELINE A WHERE A.NUM=((SELECT NUM FROM STOCK WHERE KOR_COR_NM =?)) AND A.ID=? AND A.CONTENT=?" ,param);
		            	if(rs.size()<=0){
		            		param.add(DateUtil.getDate("yyyyMMddHHmmss"));
			            	//log.debug(content);
			            	db.executeUpdate("INSERT INTO TIMELINE (NUM,ID,CONTENT,INSERT_DATE) VALUES((SELECT NUM FROM STOCK WHERE KOR_COR_NM =?),?,?,?)",param);
			            	//Iterator<Element> iterElem = row.getElementsByTag("td").iterator();
			                //StringBuilder builder = new StringBuilder();
		            	}
		            }
		            db.commit();
		            EntityUtils.consume(entity1);
		        } finally {
		            response1.close();
		        };
			}catch(Exception e){
			}finally {
				try {
					queue.put(keyword);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
