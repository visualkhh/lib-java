package logerss.server;

import khh.communication.tcp.nio.server.NioServer;

public class LogerssServer {
	public LogerssServer() {
	}

	public void start() throws Exception {
		NioServer server = new NioServer(81,LogerssWorker.class);
		server.start();
	}

	public static void main(String[] args) throws Exception {
		LogerssServer l = new LogerssServer();
		l.start();
	}

}
