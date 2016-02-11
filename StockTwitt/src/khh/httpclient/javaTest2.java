package khh.httpclient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import khh.db.connection.ConnectionCreator_I;
import khh.db.connection.pool.ConnectionMultiPool;
import khh.db.terminal.DBTerminal;
import khh.db.util.ConnectionUtil;
import khh.httpclient.common.ConCre;

class CC implements ConnectionCreator_I{
	public Connection getMakeConnection() throws Exception {
		return javaTest2.cp.getConnection("stock");
	}
}
public class javaTest2 {
	public static ConnectionMultiPool cp = ConnectionMultiPool.getInstance();
	public static void main(String[] args) throws Exception {
		cp.addConnectionCreator("stock",new ConnectionCreator_I() {
			public Connection getMakeConnection() throws Exception {
				return ConnectionUtil.getConnection(ConnectionUtil.MYSQL,"192.168.0.14",ConnectionUtil.MYSQL_DAFAULT_PORT,"STOCK","root","javadev");
			}
		},20);
		
		
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						while(true){
						DBTerminal t = new DBTerminal(new CC());
						t.executeQuery("SELECT * FROM STOCK");
							
//							Connection  c = cp.getConnection("stock");
//							c.setAutoCommit(false);
//							Statement s = c.createStatement();
//							ResultSet r = s.executeQuery("select * from TIMELINE");
//							r.close();
//							c.commit();
//							c.close();
							
						Thread.sleep(100000);
							
						}
							
					} catch (Exception e) {
						e.printStackTrace();
					}
						
				}
			}).start();
		}
		
//		Thread.sleep(10000);
//		new Thread(new Runnable() {
//			public void run() {
//				try {
//					DBTerminal t = new DBTerminal(new CC());
//					System.out.println("--------------------");
//					t.executeQuery("select NUM from STOCK");
//					System.out.println("--------------------");
//						Thread.sleep(1000000);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//			}
//		}).start();
//		Thread.sleep(5000000);
	}
}
