package khh.finger.remote.finmanager.monitor;

import khh.communication.tcp.nio.server.monitor.NioServerMonitor;
import khh.communication.tcp.nio.server.monitor.NioServerMultiMonitor;
import khh.debug.LogK;

public class FingServerMonitor extends NioServerMultiMonitor{
	public static FingServerMonitor instance = null;
	private LogK log = LogK.getInstance();
	final public static String CLIENTSERVER_NAME="ClientServer";
	final public static String ADMINSERVER_NAME="AdminServer";
	
	synchronized static public FingServerMonitor getInstance(){
		if(instance == null)
			instance = new FingServerMonitor();
		return instance;
	}

	private FingServerMonitor(){
	}

	public void addClinetMinitor(NioServerMonitor monitor) throws Exception{
		addMonitor(CLIENTSERVER_NAME, monitor);
	}
	public void addServerMinitor(NioServerMonitor monitor) throws Exception{
		addMonitor(ADMINSERVER_NAME, monitor);
	}

}

