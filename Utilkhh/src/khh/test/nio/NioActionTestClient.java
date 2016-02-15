package khh.test.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.HashMap;

import khh.communication.tcp.nio.client.NioClient;
import khh.communication.tcp.nio.server.NioServer;
import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;

public class NioActionTestClient extends NioActionWorker{

	int i=9;
	public NioActionTestClient() {
		setFirestMode(MODE_FIREST_W);
	}



	@Override
	public NioActionMsg onReceiveAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception {
		log.debug(msg);
		msg = new NioActionMsg();
		msg.putParam("visualkhh", "zzzzzzzzzz");
		msg.setSuccess(true);
		
		Thread.sleep(5000);
		return msg;
	}
	
	
	@Override
	public void receiveTelegram(HashMap<String, Object> telegram, SelectionKey selectionKey) throws Exception {
	}

	
	
	public static void main(String[] args) throws Exception {
		String ip="192.168.0.95";
		int port = 9090;
		NioClient client = new NioClient(ip, port, NioActionTestClient.class);
		client.setSelectorManagerSize(1);   
		client.setWorkerManagerSize(1);     
		client.start();
		
	}
}
