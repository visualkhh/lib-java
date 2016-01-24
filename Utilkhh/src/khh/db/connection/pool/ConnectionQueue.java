package khh.db.connection.pool;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import khh.db.connection.ConnectionCreator_I;
import khh.debug.LogK;

public class ConnectionQueue extends Thread{
	private int min = 1;
	private int max = 10;
	private long pollTimeoutms = 10000;
	private ConnectionCreator_I creator;
	private ArrayList<ConnectionPool_Connection> storege;
	private LinkedBlockingQueue<ConnectionPool_Connection> queue;
	private LogK log  = LogK.getInstance();
	public ConnectionQueue(ConnectionCreator_I creator, int max) {
		this.creator = creator; 
		this.max = max;
		init();
	}
	private void init() {
		storege = new ArrayList<ConnectionPool_Connection>();
		queue = new LinkedBlockingQueue<ConnectionPool_Connection>();
		try {
			storege.add(new ConnectionPool_Connection(getCreator().getMakeConnection()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public ConnectionCreator_I getCreator() {
		return creator;
	}
	public void setCreator(ConnectionCreator_I creator) {
		this.creator = creator;
	}
	
	public long getPollTimeoutms() {
		return pollTimeoutms;
	}
	public void setPollTimeoutms(long pollTimeoutms) {
		this.pollTimeoutms = pollTimeoutms;
	}
	
	
	public void run() {
		//계속돌면서 넣어줘야함
		while(true){
			try {Thread.sleep(100);} catch (InterruptedException e1) {e1.printStackTrace();}
			
			for (int i = 0; i < storege.size(); i++){ //사용가능한것이 있을때 queue에 넣는다.
				try{
					ConnectionPool_Connection c = storege.get(i);
					//log.debug("Connection State:"+c+"     storege.size("+storege.size()+") qsize:"+queue.size());
					if(c.isClosed()){ //사용가능한것이 있을때 queue에 넣는다.
						c.open();
						if(!queue.contains(c)){
						queue.put(c);
						log.debug("move BlockingQueue("+c+")   storege.size("+storege.size()+") qsize:"+queue.size());
						}
					};
					 if(storege.size()<max && queue.size()<=0){
						 try {
							storege.add(new ConnectionPool_Connection(getCreator().getMakeConnection()));
							log.debug("Full Busy  storege.size("+storege.size()+") qsize:"+queue.size()+" (setting -> min:"+getMin()+", max:"+getMax()+")  -> add one Connection Create");
						 } catch (Exception e) {e.printStackTrace();}
					 }
				}catch(Exception e){log.debug("queue Error",e);}

			}
			
		}

	}
	
	public ConnectionPool_Connection poll() throws InterruptedException{
		return poll(getPollTimeoutms());
	}
	public ConnectionPool_Connection poll(long pollTimeoutms) throws InterruptedException {
		ConnectionPool_Connection i = queue.poll(pollTimeoutms, TimeUnit.MILLISECONDS);
		if(null==i){
			log.debug("getConnection TimeOut wite mills: "+pollTimeoutms);
		}
		return i;
	}
	
}
