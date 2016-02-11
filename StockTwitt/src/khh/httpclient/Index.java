package khh.httpclient;

import java.lang.Thread.State;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import khh.db.connection.ConnectionCreator_I;
import khh.db.connection.pool.ConnectionMultiPool;
import khh.db.terminal.DBTerminal;
import khh.db.terminal.resultset.DBTResultSetContainer;
import khh.db.util.ConnectionUtil;
import khh.debug.LogK;
import khh.httpclient.common.ConCre;
import khh.httpclient.twitter.TimeLine;

public class Index {
	public static LogK log = LogK.getInstance();
	
	public static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	public static ArrayList<Thread> worklist = new ArrayList<Thread>();
	public static ConnectionMultiPool cp = ConnectionMultiPool.getInstance();
	public static void main(String[] args) throws Exception {
		int workCnt = 100;
		if(args.length>0){
			workCnt = Integer.parseInt(args[0]);
		}
		
		log.debug("workCnt:"+workCnt);
		
		cp.addConnectionCreator("stock", new ConnectionCreator_I() {
			public Connection getMakeConnection() throws Exception {
				return ConnectionUtil.getConnection(ConnectionUtil.MYSQL,"192.168.0.14",ConnectionUtil.MYSQL_DAFAULT_PORT,"STOCK","root","javadev");
			}
		}, 50);
		
		
		new Thread(new Runnable() {
			public void run() {
				while(true){
					DBTerminal db = new DBTerminal(new ConCre());
					DBTResultSetContainer r;
					try {
						log.info("-----add List!!-----------");
						r = db.executeQuery("SELECT KOR_COR_NM FROM STOCK");
						if(queue.size()>5000){
							queue.clear();
						}
						for (int i = 0; i < r.size(); i++) {
							queue.put(r.get(i).getString("KOR_COR_NM"));
						}
						Thread.sleep(60*60*1000);
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						try {
							db.getConnection().close();
						} catch (SQLException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
		
		
//		Thread.sleep(1000);
		Thread.sleep(10000);
	
		

//		workCnt =50;
		for (int i = 0; i < workCnt; i++) {
			TimeLine t = new TimeLine(queue);
			worklist.add(t);
//			Thread.sleep(100);
			Thread.sleep(1000);
		}
		
		
		new Thread(new Runnable() {
			public void run() {
				
				
				while(true){
					try {
						for (int i = 0; i < worklist.size(); i++) {
							Thread t = worklist.get(i);
							log.info("ThreadMonitor! ("+i+")("+worklist.size()+") stste "+t.getState()+ " queue:"+queue.size());
							if(t.getState()==State.TERMINATED){
								worklist.remove(t);
								TimeLine nt = new TimeLine(queue);
								nt.setName(nt.getName()+" new");
								worklist.add(nt);
								nt.start();
							}
						}
						Thread.sleep(30*1000);
//						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();;
		
		
		Thread.sleep(10000);
//		Thread.sleep(100);
		
		
		
		
		
		for (int i = 0; i < worklist.size(); i++) {
			worklist.get(i).start();
			Thread.sleep(10000);
//			Thread.sleep(100);
		}
		
//		Thread.sleep(10000);
		
		

		
	}
}
