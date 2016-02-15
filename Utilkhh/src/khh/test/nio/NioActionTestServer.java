package khh.test.nio;

import java.nio.channels.SelectionKey;
import java.util.HashMap;

import khh.communication.tcp.nio.server.NioServer;
import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;

public class NioActionTestServer extends NioActionWorker {
	public NioActionTestServer() {
		setFirestMode(MODE_FIREST_R);
	}

	@Override
	public NioActionMsg onReceiveAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception {
		
		log.debug(msg.getParamString("visualkhh"));
		
		return null;
	}


	@Override
	public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void receiveTelegram(HashMap<String, Object> telegram, SelectionKey selectionKey) throws Exception {
	}
	
	public static void main(String[] args) throws Exception {
		int port = 9090;
		NioServer server = new NioServer(port,NioActionTestServer.class);
		server.setSelectorManagerSize(1);
		server.setWorkerManagerSize(1);
		server.start();
	}
}
