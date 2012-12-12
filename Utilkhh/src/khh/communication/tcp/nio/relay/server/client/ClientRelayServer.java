package khh.communication.tcp.nio.relay.server.client;

import java.util.ArrayList;

import khh.communication.tcp.nio.server.NioServer;
import khh.communication.tcp.nio.worker.NioWorker;

public class ClientRelayServer extends NioServer{

	public ClientRelayServer(int port,Class nioWorkerClass){
		super(port,nioWorkerClass);
	}
	public ClientRelayServer(int port,ArrayList<NioWorker> nioWorkerList){
		super(port,nioWorkerList);
	}
	
	public void start() throws Exception{
		super.start();
		getMultimonitor().addMonitor(getName(),getMonitor());
	}
}
