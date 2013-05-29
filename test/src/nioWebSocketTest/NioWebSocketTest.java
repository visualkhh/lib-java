package nioWebSocketTest;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.HashMap;

import khh.communication.tcp.nio.server.NioServer;
import khh.communication.tcp.nio.websocket.msg.NioWebSocketMsg;
import khh.communication.tcp.nio.websocket.worker.NioWebSocketWorker;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.debug.LogK;

public class NioWebSocketTest {

	NioWebSocketWorker worker =new NioWebSocketWorker(){
		LogK log = LogK.getInstance();
		@Override
		public NioActionMsg onReceiveAction(NioActionMsg msg,SelectionKey selectionKey) throws Exception {
			NioWebSocketMsg wmsg = (NioWebSocketMsg) msg;
			log.debug(wmsg.getParamString("Host"));
			return wmsg;
		}
		@Override
		public NioActionMsg onSendAction(NioActionMsg msg,SelectionKey selectionKey) throws Exception {
			return msg;
		}
		@Override
		public void receiveTelegram(HashMap<String, Object> telegram,SelectionKey selectionKey) throws Exception {
			
		}
	};
	
	
	
	
	public NioWebSocketTest() throws Exception {
		ArrayList<NioWorker> list = new ArrayList<NioWorker>();
		list.add(worker);
		
		NioServer server = new NioServer(8788,list);
		server.setSelectorManagerSize(1);
		server.setWorkerManagerSize(1);
		server.start();
		
	}
	public static void main(String[] args) throws Exception {
		new NioWebSocketTest();

	}

}
