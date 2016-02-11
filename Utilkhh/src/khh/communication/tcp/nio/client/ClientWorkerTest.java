package khh.communication.tcp.nio.client;

import java.nio.channels.SelectionKey;
import java.util.HashMap;

import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;

public class ClientWorkerTest extends NioActionWorker{

	public ClientWorkerTest() {
		setFirestMode(MODE_FIREST_W);
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
		Thread.sleep(5000);
		if(selectionKey.isWritable()){
			NioActionMsg smsg = new NioActionMsg(); 
			smsg.putParam("visual", "zzzzzzz");
			smsg.setSuccess(true);
			return smsg;
		}
		return null;
	}

	@Override
	public void receiveTelegram(HashMap<String, Object> telegram, SelectionKey selectionKey) throws Exception {
		// TODO Auto-generated method stub
		
	}
};