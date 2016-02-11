package khh.communication.tcp.nio.server;

import java.nio.channels.SelectionKey;
import java.util.HashMap;

import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;

public class ServerWorkerTest extends NioActionWorker{

	public ServerWorkerTest() {
		
	}
	@Override
	public NioActionMsg onReceiveAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception {
		if(selectionKey.isReadable()){
			String a = msg.getParamString("visual");
			System.out.println(a);
		}
		return null;
	}

	@Override
	public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void receiveTelegram(HashMap<String, Object> telegram, SelectionKey selectionKey) throws Exception {
		// TODO Auto-generated method stub
		
	}
};