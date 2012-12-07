package khh.communication.tcp.nio.relay.server.client;

import khh.communication.tcp.nio.server.NioServer;

public class ClientRelayServer extends NioServer{

	public ClientRelayServer(int port,Class nioWorkerClass){
		super(port,nioWorkerClass);
	}
	public void start() throws Exception{
		super.start();
		getMultimonitor().addMonitor(getName(),getMonitor());
	}
}
