package nioActionTest;

import khh.communication.tcp.nio.client.NioClient;
import nioActionTest.client.ActionClient;

public class IndexClient {
	NioClient client;
	public IndexClient() throws Exception {
		client = new NioClient("127.0.0.1",9999, ActionClient.class);
		client.setSelectorManagerSize(1);
		client.setWorkerManagerSize(1);
		client.start();
	}
	
	public static void main(String[] args) throws Exception {
		IndexClient c = new IndexClient();
	}
}
