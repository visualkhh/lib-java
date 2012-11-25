package khh.db.connection.pool;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import khh.debug.util.DebugUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class KConnectionPool
{
	private static KConnectionPool	instance		= null;

	private String driver;
	private String url;
	private String id;
	private String pass;
	
	
//	private String classname;
//	private String dbID;
//	private String dbPass;
//	private String ip;
//	private String port;
//	private String databaseName;
//	private String url;
	
	private Vector<KConnection>		connectionPool	= new Vector<KConnection>();
	private int						poolmaxsize		= 10;
	private String					FileName		= "../xml/dbinfo.xml";
	private KConnectionPool()
	{
		DebugUtil.trace("DB KConnectionPool Creative");
		DocumentBuilderFactory documentFactory;
		DocumentBuilder docBuilder;
		Document document;
		
	
		try
		{
			documentFactory = DocumentBuilderFactory.newInstance();
			docBuilder = documentFactory.newDocumentBuilder();
			document = docBuilder.parse(new File(FileName));
			if ( document == null )
				return;
			Node node = document.getDocumentElement().getFirstChild();
			DebugUtil.trace("DBPool info get!");
			while (node != null)
			{
				for (int i = 0; i < node.getChildNodes().getLength(); i++)
				{
					Node cnode = node.getChildNodes().item(i);
					if ( cnode.getNodeName().equals("pass") )
					{
						pass = cnode.getTextContent().replaceAll("\t", "").replaceAll("\n", " ").trim();
						DebugUtil.trace("pass : %s", pass);
					}
					else if ( cnode.getNodeName().equals("id") )
					{
						id = cnode.getTextContent().replaceAll("\t", "").replaceAll("\n", " ").trim();
						DebugUtil.trace("id : %s", id);
					}
					else if ( cnode.getNodeName().equals("url") )
					{
						url = cnode.getTextContent().replaceAll("\t", "").replaceAll("\n", " ").trim();
						DebugUtil.trace("url : %s", url);
					}
					else if ( cnode.getNodeName().equals("driver") )
					{
						driver = cnode.getTextContent().replaceAll("\t", "").replaceAll("\n", " ").trim();
						DebugUtil.trace("driver : %s", driver);
					}
					
				}

				node = node.getNextSibling();
			}
			
			
			DebugUtil.trace("contact id : %s", id);
			DebugUtil.trace("contact pass : %s", pass);
			DebugUtil.trace("contact url : %s", url);
			DebugUtil.trace("contact driver : %s", driver);
			DebugUtil.trace("contact connectionPool : %d", connectionPool.size());
	

			//오라클만 적용된상태 입니다.
			
			Class.forName(driver);
			// url = "jdbc:oracle:thin:@211.43.195.109:1521:ora10g"; // 리얼
			// dbPass = "gems4sys";
//			url = "jdbc:oracle:thin:@"+ip+":"+port+":"+databaseName+""; // 테스트서버  

			clearConnectionpool();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private synchronized KConnection makeConnection() throws SQLException
	{
		synchronized (connectionPool)
		{
			try
			{
				Connection connction = DriverManager.getConnection(url, id, pass);
				KConnection kcon = new KConnection(connction);
				DebugUtil.trace("커넥션풀을 생성합니다 DB KConnectionPool MakeConnection  isClosed? = "+kcon.isClosed()+"("+connction.isClosed());
				return kcon;
			}
			catch (SQLException e)
			{
				throw e;
			}
		}
	}

	private void clearConnectionpool() throws SQLException
	{

		for (int i = 0; i < connectionPool.size(); i++)
		{
			connectionPool.get(i).close();
		}
		connectionPool.clear();

	}

	public static KConnectionPool getInstance()
	{

		if ( instance == null )
			instance = new KConnectionPool();

		return instance;
	}

	public KConnection getConnection() throws SQLException, InterruptedException
	{
		return getConnection(1000);
	}

	public synchronized KConnection getConnection(long time) throws SQLException, InterruptedException
	{
		synchronized (connectionPool)
		{
			KConnection con = null;

			con = getStandbyConnction();
			if ( con == null )
			{

				if ( connectionPool.size() <= poolmaxsize ) // 추가
				{
					connectionPool.add(makeConnection());
				}

				long starttime = System.currentTimeMillis();
				while (true)
				{
					if ( con != null )
						break;

					Thread.sleep(1);
					long temptime = System.currentTimeMillis() - starttime;
					if ( temptime > time )
					{// timeOver
						DebugUtil.trace("Faill TimeOut ConnectionPoll get Connection Wait Time 커넥션 풀에서 커넥션을 기다리는데 시간이 초가됐습니다  초가시간 %d", temptime);
						break;
					}
					else
					{
						con = getStandbyConnction();

					}
				}

				if ( con == null ) // 진짜 기달려도 Connection을 못받을때
				{
					throw new SQLException("Fail ConnectionPoll Creative !! 커넥션 풀에 커넥션을 생성하지못했습니다.");
				}

			}

			DebugUtil.trace("커넥션풀에서 커넥션이 나갔습니다   커넥션풀      maxSize=(%d)  PoolSize = (%d)  usePool =(%d) ", poolmaxsize, connectionPool.size(),getUseConnction());

			con.open(); // 마지막에 오픈된 커넥션을 내보낸다.
			return con;

		}
	}

	public KConnection getStandbyConnction()
	{

		for (int i = 0; i < connectionPool.size(); i++)
		{
			if ( connectionPool.get(i).isClosed() )
			{// 닫힌거 반환 나갈때 오픈되서 나간다
				DebugUtil.trace("닫쳐져있는것 즉 사용하지 않는(사용할수있는) 커넥션이 하나 나갔습니다.getStandbyConnction         connectionPollSize="+connectionPool.size());
				return connectionPool.get(i);
			}
		}

		DebugUtil.trace("닫쳐져있는것 즉 사용하지 않는(사용할수있는) 커넥션이 없습니다.getStandbyConnction             connectionPollSize="+connectionPool.size());
		return null;
	}

	
	public int getUseConnction()
	{
		int poolusesize=0;
		for (int i = 0; i < connectionPool.size(); i++)
		{
			if ( !(connectionPool.get(i).isClosed()) )
			{
				poolusesize++;
			}
		}
		return poolusesize;
	}


}
