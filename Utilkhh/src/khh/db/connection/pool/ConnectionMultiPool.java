package khh.db.connection.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import khh.db.connection.ConnectionCreator_I;
import khh.debug.LogK;
import khh.std.adapter.AdapterMap;


public class ConnectionMultiPool{
	private static ConnectionMultiPool	instance		= null;
	HashMap<String, ConnectionQueue> queue = null;
	
	private LogK logk  = LogK.getInstance();
	public static  ConnectionMultiPool getInstance(){
		if ( instance == null )
			instance = new ConnectionMultiPool();
		return instance;
	}
	private ConnectionMultiPool(){ 
		queue = new HashMap<String, ConnectionQueue>();
	}
	
   public synchronized void addConnectionCreator(String pollName,final ConnectionCreator_I creator,Integer maximumConnectionSize) throws Exception{
	   ConnectionQueue c = new ConnectionQueue(creator,maximumConnectionSize);
	   c.setName("Thread-ConnectionQueue-"+pollName);
	   c.start();
	   queue.put(pollName, c);
    }

    public Connection getConnection(String pollName) throws InterruptedException{
        return ConnectionPool_Connection(pollName, null);
    }
    public Connection getConnection(String pollName,long connectionWaitMillis) throws InterruptedException{
        return ConnectionPool_Connection(pollName,connectionWaitMillis);
    }
    


	public ConnectionPool_Connection ConnectionPool_Connection(String pollName,Long connectionWaitMillis) throws InterruptedException{
		synchronized (queue)
		{
			ConnectionQueue cqueue = queue.get(pollName);
			if(null==cqueue){return null;};
			
			ConnectionPool_Connection connection = null;
			if(null==connectionWaitMillis){
				connection = cqueue.poll();
			}else{
				connection = cqueue.poll(connectionWaitMillis);
			}
			return connection;
		}
	}


}
