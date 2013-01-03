package khh.communication.tcp.nio.relay.server.client;

import java.util.ArrayList;

import khh.communication.tcp.nio.server.NioServer;
import khh.communication.tcp.nio.worker.NioWorker;

public class RelayClientServer extends NioServer{

	public RelayClientServer(int port,Class clientRelayWorkerClass){
		super(port,clientRelayWorkerClass);
	}
	public RelayClientServer(int port,ArrayList<NioWorker> clientRelayWorkerList){
		super(port,clientRelayWorkerList);
	}
	
	
	public void start() throws Exception{
		super.start();
		getMultimonitor().putMonitor(getName(),getMonitor());
	}
	
  

}
