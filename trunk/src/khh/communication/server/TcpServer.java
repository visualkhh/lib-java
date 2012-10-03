package khh.communication.server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import khh.debug.util.DebugUtil;

public class TcpServer
{
	private int port = 9090;
	private int selectorManagerCount = 10;
	private int ProcessManagerCount = 10;
	private EventQueue				eventQueue				= null;
	private AcceptSelector			acceptSelectThread		= null;
	private ClientSelectorManager	clientSelectManager		= null;
	private ClientProcessManager	clientProcessManager	= null;
	private Class tcpServerCommunication =null;
	public TcpServer(int port,Class tcpServerCommunication) throws IOException
	{
		this.port=port;
		this.tcpServerCommunication=tcpServerCommunication;
	} 
 
	public int getSelectorManagerCount()
	{
		return selectorManagerCount;
	}

	public void setSelectorManagerCount(int selectorManagerCount)
	{
		this.selectorManagerCount = selectorManagerCount;
	}
	public int getProcessManagerCount()
	{
		return ProcessManagerCount;
	}

	public void setProcessManagerCount(int processManagerCount)
	{
		ProcessManagerCount = processManagerCount;
	}

	public int getPort()
	{
		return port;
	}
	
	

	public Class getTcpServerCommunication()
	{
		return tcpServerCommunication;
	}

	public void setTcpServerCommunication(Class tcpServerCommunication)
	{
		this.tcpServerCommunication = tcpServerCommunication;
	}

	public void start() throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException 
	{
		DebugUtil.trace("Server Start Port (%d)  selectorManagerCount(%d)  ProcessManagerCount(%d)",getPort(),getSelectorManagerCount(),getProcessManagerCount());
//		Utilities.trace("TcpServerCommunication Class \n%s\n\n",Utilities.getClassInfo(getTcpServerCommunication()));
		eventQueue = EventQueue.getInstance();
		clientProcessManager = new ClientProcessManager(eventQueue,getTcpServerCommunication(), getProcessManagerCount());	
		clientSelectManager = new ClientSelectorManager(eventQueue, getSelectorManagerCount());
		acceptSelectThread = new AcceptSelector(clientSelectManager,port);
		acceptSelectThread.start();
	}

	public static void main(String[] args) throws IOException
	{
//		System.out.println("=============a");
//		new TcpServer(9090).start();
//		System.out.println("=============b");
	}
}
