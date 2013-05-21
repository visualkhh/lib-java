package nioActionTest;

import khh.communication.tcp.nio.server.NioServer;
import nioActionTest.server.ActionServer;

public class IndexServer {
	NioServer server;
	public IndexServer() throws Exception {
		server = new NioServer(9999, ActionServer.class);
		server.setSelectorManagerSize(1);
		server.setWorkerManagerSize(1);
		server.start();
	}
	
	public static void main(String[] args) throws Exception {
		IndexServer s = new IndexServer();
	}
}