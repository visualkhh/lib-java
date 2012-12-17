package roundrobin;

import khh.collection.RoundRobin;

class T extends Thread{
	int  is  =0 ;
	public void run() {
		for (int i = 0; i < 10; i++) {
			synchronized (RoundRobinSync.r) {
				RoundRobinSync.r.add("a"+(is++)+"  id:"+getId());
			}
		}
		RoundRobinSync.result(getId());
	};
};

public class RoundRobinSync {
	public static RoundRobin<String> r = new RoundRobin<String>();
	

	
	
	public static void result(long id){
		System.out.println("id:"+id+"   r.size:"+r.size());
		for (int i = 0; i < r.size(); i++) {
		//	System.out.println("id:"+id+ "  "+ r.get(i));
		}
	}
	public static void main(String[] args) {
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
		new T().start();
	};
}
