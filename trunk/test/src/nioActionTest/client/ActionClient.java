package nioActionTest.client;

import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.debug.LogK;

public class ActionClient extends NioActionWorker {
	LogK log = LogK.getInstance();
	
	public ActionClient() {
		super(MODE_FIREST_W);
	}
	@Override
	public NioActionMsg onReceiveAction(NioActionMsg msg,SelectionKey selectionKey) throws Exception {
		
		LinkedHashMap<String, byte[]> param = msg.getParam();
		Iterator<String> iter = param.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			log.debug("key: "+key,param.get(key));
			msg.putParam(key,new String(param.get(key))+key);
		}
		return msg;
		
	}

	@Override
	public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey)throws Exception {
		Thread.sleep(5000);
		
		if(msg==null){
			msg = new NioActionMsg(1);	
			setFirestMode(MODE_FIREST_R);
		}
		msg.putParam("a", "b");
		msg.putParam("b", "c");
		msg.setSuccess(true);
		
		return msg;
	}

	@Override
	public void receiveTelegram(HashMap<String, Object> telegram,
			SelectionKey selectionKey) throws Exception {
		// TODO Auto-generated method stub

	}

}
