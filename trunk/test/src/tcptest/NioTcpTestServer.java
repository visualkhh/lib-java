package tcptest;

import khh.communication.tcp.nio.server.NioServer;
import khh.debug.LogK;

public class NioTcpTestServer {
	LogK log = LogK.getInstance();
	public void start() throws Exception {
		NioServer n = new NioServer(9090,NioS.class);
		n.setSelectorPoolSize(10);
		n.setWorkerPoolSize(10);
		n.start();
	}
	
	
	public static void main(String[] args) throws Exception  {
		new NioTcpTestServer().start();
	}
}
