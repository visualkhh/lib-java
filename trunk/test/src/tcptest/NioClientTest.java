package tcptest;

import khh.communication.tcp.nio.client.NioClient;
import khh.debug.LogK;

public class NioClientTest {
	LogK log = LogK.getInstance();
	public void start() throws Exception {
		NioClient n = new NioClient("10.25.230.138",9090);
		n.setNioworkerbusiness(new NioC());
		n.start();
	}
	
	
	public static void main(String[] args) throws Exception  {
		new NioClientTest().start();;
	}
}
