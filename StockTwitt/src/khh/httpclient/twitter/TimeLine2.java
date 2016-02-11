package khh.httpclient.twitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import khh.db.terminal.DBTerminal;
import khh.db.terminal.resultset.DBTResultSetContainer;
import khh.debug.LogK;
import khh.httpclient.Index2;
import khh.httpclient.common.ConCre2;

public class TimeLine2 extends Thread{
	CloseableHttpClient httpclient = null;
	LogK log = LogK.getInstance();
	LinkedBlockingQueue<String> queue;
	public TimeLine2(LinkedBlockingQueue queue) {
		this.queue=queue;
		httpclient = HttpClients.createDefault();
//		String keyword
//		log.debug("start Twitter : "+keyword);
//		this.keyword=keyword;
	}
	
	public String getLastTwitt(DBTerminal db, String keyword) throws Exception{
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
				log.debug(getName()+"  keyword take()"+keyword);
				Thread.sleep(1000);
				log.debug("1------------");
				DBTerminal db = new DBTerminal(new ConCre2());
				log.debug("2------------");
				String query="";
				query+=" SELECT NUM, CNT, CONTENT, INSERT_DATE FROM TIMELINE  ";
				query+=" WHERE NUM = (SELECT NUM FROM STOCK WHERE KOR_COR_NM ='"+keyword+"') ORDER BY CNT DESC";
				query+=" LIMIT 1";
				db.executeQuery(query);
				log.debug("4------------");
				
				String lastTwitt=";";
				log.debug(this.getName()+"Twitter  workCnt("+wcnt+") Start "+keyword+"  lastTwitt :"+lastTwitt);
			}catch(Exception e){
				log.debug(e);
			}finally {
				try {
					queue.put(keyword);
					log.debug(this.getName()+"  keyword put()"+keyword);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
