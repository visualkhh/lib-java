package khh.db.connection.pool;

import java.sql.Connection;
import java.sql.SQLException;

import khh.db.connection.ConnectionCreator_I;
import khh.debug.LogK;
import khh.std.adapter.Adapter_Std;


public class ConnectionMultiPool
{
	private static ConnectionMultiPool	instance		= null;

	private Adapter_Std<String,Adapter_Std<Integer,ConnectionPool_Connection>>		connectionPool	;
	private Adapter_Std<String,ConnectionCreator_I> connectionCreators ;
	private Adapter_Std<String,Integer> maximumConnectionSizes ;
	private long connectionWaitMillisl = 1000;
	private LogK logk  = LogK.getInstance();
	private ConnectionMultiPool()
	{ 
	    connectionPool = new Adapter_Std<String, Adapter_Std<Integer,ConnectionPool_Connection>>();
	    connectionCreators = new Adapter_Std<String, ConnectionCreator_I>();
	    maximumConnectionSizes = new Adapter_Std<String, Integer>();
	}
	
   public synchronized void addConnectionCreator(String creatorName,final ConnectionCreator_I creator,Integer maximumConnectionSize) throws Exception{
        connectionCreators.add(creatorName, creator);
        maximumConnectionSizes.add(creatorName, maximumConnectionSize);
    }
	/*   
	public synchronized void addConnectionCreator(String creatorName,ConnectionPoolCreator_I creator,int maximumConnectionSize) throws Exception{
	    connectionCreators.add(creatorName, creator);
	    maximumConnectionSizes.add(creatorName, maximumConnectionSize);
	}
	 */
	private synchronized ConnectionPool_Connection makeConnection(String creatorName) throws Exception
	{
		synchronized (connectionCreators)
		{
		        ConnectionPool_Connection kcon = new ConnectionPool_Connection(connectionCreators.get(creatorName).getMakeConnection());
			    logk.debug(creatorName+"  커넥션풀을 생성합니다 DB KConnectionPool MakeConnection  isClosed? = "+kcon.isClosed()+"("+kcon.isClosed());
				return kcon;
		}
	}

	private synchronized void clearConnectionpool() throws Exception
	{
	    synchronized (connectionPool) {
    		for (int i = 0; i < connectionPool.size(); i++)
    		{
    		    Adapter_Std<Integer,ConnectionPool_Connection> std = connectionPool.get(i);
    		    for (int j = 0; j < std.size(); j++) {
    		        std.get(i).close();
                }
    		}
    		connectionPool.clear();
	    }

	}

	public static  ConnectionMultiPool getInstance()
	{

		if ( instance == null )
			instance = new ConnectionMultiPool();

		return instance;
	}

    public synchronized Connection getConnection(String creatorName) throws Exception{
        return ConnectionPool_Connection(creatorName);
    }
    public synchronized Connection getConnection(String creatorName,long connectionWaitMillis) throws Exception{
        return ConnectionPool_Connection(creatorName,connectionWaitMillis);
    }
    

	public synchronized ConnectionPool_Connection ConnectionPool_Connection(String creatorName) throws Exception
	{
		return ConnectionPool_Connection(creatorName,connectionWaitMillisl);
	}

	public synchronized ConnectionPool_Connection ConnectionPool_Connection(String creatorName,long connectionWaitMillis) throws Exception
	{
		synchronized (connectionPool)
		{
			ConnectionPool_Connection con = getStandbyConnction(creatorName);
			Integer poolmaxsize = maximumConnectionSizes.get(creatorName);
			if(poolmaxsize==null){
				logk.debug(creatorName+ " 의 이름을 찾을수없습니다.");
				return null;
			}
			Adapter_Std<Integer, ConnectionPool_Connection>  atConnections = connectionPool.get(creatorName);
			if ( con == null )
			{
			    
			    if(atConnections==null){
			        atConnections=new Adapter_Std<Integer, ConnectionPool_Connection>();
			        connectionPool.add(creatorName, atConnections);
			    }
				if ( atConnections.size() < poolmaxsize ) // 추가
				{
				    atConnections.add(atConnections.size(),makeConnection(creatorName));
				}

				long starttime = System.currentTimeMillis();
				while (true)
				{
					if ( con != null )
						break;

					Thread.sleep(1);
					long temptime = System.currentTimeMillis() - starttime;
					if ( temptime > connectionWaitMillis )
					{// timeOver
					    logk.error(String.format(creatorName+"  Faill TimeOut ConnectionPoll get Connection Wait Time 커넥션 풀에서 커넥션을 기다리는데 시간이 초가됐습니다  초가시간 %d", temptime));
						//DebugUtil.trace(creatorName+"  Faill TimeOut ConnectionPoll get Connection Wait Time 커넥션 풀에서 커넥션을 기다리는데 시간이 초가됐습니다  초가시간 %d", temptime);
						break;
					}
					else
					{
						con = getStandbyConnction(creatorName);

					}
				}

				if ( con == null ) // 진짜 기달려도 Connection을 못받을때
				{
				    SQLException sele = new SQLException("Fail ConnectionPoll Creative !! 커넥션 풀에 커넥션을 생성하지못했습니다.");
				    logk.error("커넥션 풀에 커넥션을 생성하지못했습니다.",sele);
					throw sele;
				}

			}

			logk.debug(String.format(creatorName+"  커넥션풀에서 커넥션이 나갔습니다   커넥션풀      maxSize=(%d)  PoolSize = (%d)  usePool =(%d) ", poolmaxsize, connectionPool.size(),getUseConnction(creatorName)));
//			DebugUtil.trace(creatorName+"  커넥션풀에서 커넥션이 나갔습니다   커넥션풀      maxSize=(%d)  PoolSize = (%d)  usePool =(%d) ", poolmaxsize, connectionPool.size(),getUseConnction(creatorName));
			con.open(); // 마지막에 오픈된 커넥션을 내보낸다.
			return con;

		}
	}

	public ConnectionPool_Connection getStandbyConnction(String creatorName) throws Exception
	{
	    Adapter_Std<Integer, ConnectionPool_Connection> atConnections  = connectionPool.get(creatorName);
		for (int i = 0;atConnections!=null && i < atConnections.size(); i++)
		{
			if ( atConnections.get(i).isClosed() )
			{// 닫힌거 반환 나갈때 오픈되서 나간다
			    logk.debug(creatorName+"  닫쳐져있는것 즉 사용하지 않는(사용할수있는) 커넥션이 하나 나갔습니다.getStandbyConnction         atConnectionsSize="+atConnections.size());
//				DebugUtil.trace(creatorName+"  닫쳐져있는것 즉 사용하지 않는(사용할수있는) 커넥션이 하나 나갔습니다.getStandbyConnction         atConnectionsSize="+atConnections.size());
				ConnectionPool_Connection con = atConnections.get(i);
		        if(con.getRealConnection().isClosed()){
		            Integer key = atConnections.getKey(i);
		            atConnections.remove(key);
		            atConnections.add(key,makeConnection(creatorName));
		            logk.debug(creatorName+"  진짜 커넥션이 닫혀있어서 나갈때 다시 리커넥션하고 나갑니다.");
		            //DebugUtil.trace(creatorName+"  진짜 커넥션이 닫혀있어서 나갈때 다시 리커넥션하고 나갑니다.");
	            }
		        //기본이 오토커밋으로 가져감
		        if(!con.getAutoCommit())
		        con.rollback();
		        con.setAutoCommit(true);
				return con;
			}
		}
		logk.warn(creatorName+"  닫쳐져있는것 즉 사용하지 않는(사용할수있는) 커넥션이 없습니다.getStandbyConnction             atConnectionsSize="+(atConnections!=null?atConnections.size():"no creative"));
		//DebugUtil.trace(creatorName+"  닫쳐져있는것 즉 사용하지 않는(사용할수있는) 커넥션이 없습니다.getStandbyConnction             atConnectionsSize="+(atConnections!=null?atConnections.size():"no creative") );
		return null;
	}

	
	public int getUseConnction(String creatorName) throws Exception
	{
	    Adapter_Std<Integer, ConnectionPool_Connection>  atConnections = connectionPool.get(creatorName);
		int poolusesize=0;
		for (int i = 0;atConnections!=null && i < atConnections.size(); i++)
		{
			if ( !(atConnections.get(i).isClosed()) )
			{
				poolusesize++;
			}
		}
		//System.out.println("usecon"+poolusesize);
		return poolusesize;
	}


}
