package khh.communication.tcp.nio.relayserver.monitor;

import java.nio.channels.SelectionKey;

import khh.communication.tcp.nio.server.monitor.NioServerMonitor;
import khh.communication.tcp.nio.server.monitor.NioServerMultiMonitor;
import khh.debug.LogK;

public class RelayServerMonitor extends NioServerMultiMonitor{
	public static RelayServerMonitor instance = null;
	private LogK log = LogK.getInstance();
	final public static String CLIENTSERVER_NAME="ClientServer";
	final public static String ADMINSERVER_NAME="AdminServer";
	
	synchronized static public RelayServerMonitor getInstance(){
		if(instance == null)
			instance = new RelayServerMonitor();
		return instance;
	}

	private RelayServerMonitor(){
		super();
	}

	public void addClinetMinitor(NioServerMonitor monitor) throws Exception{
		addMonitor(CLIENTSERVER_NAME, monitor);
	}
	public void addAdminMinitor(NioServerMonitor monitor) throws Exception{
		addMonitor(ADMINSERVER_NAME, monitor);
	}
	public SelectionKey getClientSelectionKey(String sessionKey) throws Exception{
		return getSelectionKey(CLIENTSERVER_NAME, sessionKey);
	}
	public SelectionKey getAdminSelectionKey(String sessionKey) throws Exception{
		return getSelectionKey(ADMINSERVER_NAME, sessionKey);
	}

	public void addClientSelectionKey(String sessionKey,SelectionKey selectionKey) throws Exception{
		addSelectionKey(CLIENTSERVER_NAME, sessionKey, selectionKey);
	}
	public void addAdminSelectionKey(String sessionKey,SelectionKey selectionKey) throws Exception{
		addSelectionKey(ADMINSERVER_NAME, sessionKey, selectionKey);
	}
	
	public void setClientSelectionKey(String sessionKey,SelectionKey selectionKey) throws Exception{
		setSelectionKey(CLIENTSERVER_NAME, sessionKey, selectionKey);
	}
	public void setAdminSelectionKey(String sessionKey,SelectionKey selectionKey) throws Exception{
		setSelectionKey(ADMINSERVER_NAME, sessionKey, selectionKey);
	}
	public void removeClientSelectionKey(String sessionKey) throws Exception{
		removeSelectionKey(CLIENTSERVER_NAME, sessionKey);
	}
	public void removeAdminSelectionKey(String sessionKey) throws Exception{
		removeSelectionKey(ADMINSERVER_NAME, sessionKey);
	}
}
