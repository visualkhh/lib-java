package khh.communication.tcp.nio.server;

public class NioServerTest{
	public static void main(String[] args) throws Exception {
		
		NioServer server = new NioServer(81,ServerWorkerTest.class);
		server.setSelectorManagerSize(1);
		server.setWorkerManagerSize(1);
		server.start();
	}
}
