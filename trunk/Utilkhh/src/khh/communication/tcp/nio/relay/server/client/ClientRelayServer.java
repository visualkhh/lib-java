package khh.communication.tcp.nio.relay.server.client;

import khh.communication.tcp.nio.server.NioServer;

public class ClientRelayServer extends NioServer{

	public ClientRelayServer(int port,Class nioWorkerClass){
		super(port,nioWorkerClass);
	}
	

	

	public void start() throws Exception{
//		server = new NioServer(getPort(), ClientRelayServerWorker.class);
//		server.setName("ClientRelayServer"+getPort());
//		server.setSelectorPoolSize(getSelectorPoolSize());
//		server.setWorkerPoolSize(getWorkerPoolSize());
//		server.start();
		super.start();
		getMultimonitor().addMonitor(getName(),getMonitor());
	}

	
	



}
