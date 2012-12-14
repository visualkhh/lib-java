package khh.communication.tcp.nio.relay.server.client;

import java.util.ArrayList;

import khh.communication.tcp.nio.server.NioServer;
import khh.communication.tcp.nio.worker.NioWorker;

public class ClientRelayServer extends NioServer{

	public ClientRelayServer(int port,Class clientRelayWorkerClass){
		super(port,clientRelayWorkerClass);
	}
	public ClientRelayServer(int port,ArrayList<NioWorker> clientRelayWorkerList){
		super(port,clientRelayWorkerList);
	}
	
	
	public void start() throws Exception{
		super.start();
		getMultimonitor().putMonitor(getName(),getMonitor());
	}
	
  

}
