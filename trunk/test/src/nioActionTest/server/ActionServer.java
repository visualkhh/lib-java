package nioActionTest.server;

import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.debug.LogK;

public class ActionServer extends NioActionWorker {

	LogK log = LogK.getInstance();
	
	public ActionServer() {
		super(MODE_FIREST_RW);
	}
	
	@Override
	public NioActionMsg onReceiveAction(NioActionMsg msg,SelectionKey selectionKey) throws Exception {
		LinkedHashMap<String, byte[]> param = msg.getParam();
		Iterator<String> iter = param.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			log.debug("key: "+key,param.get(key));
			msg.putParam(key,new String(param.get(key))+key+"1"+new String(param.get(key)));
		}
		return msg;
	}

	@Override
	public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception {
		return msg;
	}

	@Override
	public void receiveTelegram(HashMap<String, Object> telegram,SelectionKey selectionKey) throws Exception {

	}

}
