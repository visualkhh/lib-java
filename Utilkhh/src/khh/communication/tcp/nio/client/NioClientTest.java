package khh.communication.tcp.nio.client;

import java.io.IOException;

public class NioClientTest {
	public NioClientTest() {
		// TODO Auto-generated constructor stub
	}

	public void start() {

	}

	public static void main(String[] args) throws Exception {
		NioClient c = new NioClient("192.168.0.95",81,ClientWorkerTest.class);
		c.setSelectorManagerSize(1);
		c.setWorkerManagerSize(1);
		c.start();
	}
}
