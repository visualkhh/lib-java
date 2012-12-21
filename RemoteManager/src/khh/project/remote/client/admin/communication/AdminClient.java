package khh.project.remote.client.admin.communication;

import java.io.IOException;

import khh.communication.tcp.nio.client.NioClient;

public class AdminClient{
	private NioClient client = null;
	public AdminClient() throws IOException{
		client = new NioClient("127.0.0.1",9595,AdminClientActionWorker.class);
	}

	public void connection() throws Exception{
		client.start();
	}
}
