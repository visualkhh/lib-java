package khh.httpclient;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.plaf.SliderUI;

import khh.db.connection.ConnectionCreator_I;
import khh.db.connection.pool.ConnectionMultiPool2;
import khh.db.terminal.DBTerminal;
import khh.db.util.ConnectionUtil;
import khh.httpclient.common.ConCre;


public class javaTest {
	public static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	public static ArrayList<Thread> list  = new ArrayList<Thread>();

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 9999; i++) {
			queue.put("" + i);
		}
		

		for (int i = 0; i < 10; i++) {
			Thread t = (new Thread(new Runnable() {
				public void run() {
					String a;
					while(true){
					try {
						a = queue.take();
						System.out.println( a+ "    "+queue.size());
						Thread.sleep(1000);
						queue.put(a);
						;
					} catch (Exception e) {
						e.printStackTrace();
					}
					}
				}
			}));
			list.add(t);
			t.start();
		}

		
		
		
		
		new Thread(new Runnable() {
			public void run() {
				while(true){
				for (int i = 0; i < list.size(); i++) {
					Thread t = list.get(i);
					System.out.println("ThreadMonitor! ("+i+")("+list.size()+") stste "+t.getState()+ " queue:"+queue.size());
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
			}
		}).start();
	}
}
