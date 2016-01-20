package khh.httpclient;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import khh.collection.RoundRobin;
import khh.db.terminal.DBTerminal;
import khh.db.terminal.resultset.DBTResultSetContainer;
import khh.debug.LogK;
import khh.httpclient.common.ConCre;
import khh.httpclient.twitter.TimeLine;

public class Index {
	public static LogK log = LogK.getInstance();
	
	public static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	public static void main(String[] args) throws Exception {
		DBTerminal db = new DBTerminal(new ConCre());
		DBTResultSetContainer r = db.executeQuery("SELECT KOR_COR_NM FROM STOCK");
//		ArrayList<String> stlist = new ArrayList();
		for (int i = 0; i < r.size(); i++) {
			queue.put(r.get(i).getString("KOR_COR_NM"));
		}
		
		for (int i = 0; i < 100; i++) {
			new TimeLine(queue).start();
			Thread.sleep(10000);
		}
		
		
//		while (true) {
//			for (int i = 0; i < stlist.size(); i++) {
//				log.debug("start=========" + stlist.get(i) + "============");
//				try {
//					new TimeLine(stlist.get(i)).search();
//				} catch (Exception e) {
//					log.debug("error: " + e);
//				}
//				log.debug("end=========" + stlist.get(i) + "============");
//				
//				Thread.sleep(1000);
//			}
//
//		}
	}
}
