package khh.project.remote.client.admin.communication;

import java.nio.channels.SelectionKey;
import java.util.HashMap;

import khh.collection.Queue;
import khh.communication.tcp.nio.NioCommunication;
import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.std.Action;

public class AdminActionWorker extends NioActionWorker{
	public static enum TELEGRAM{
		FROM("FROM"),
		TO("TO"),
		ACTION("ACTION"),
		//GET_SERVERS("GET_SERVERS"),
		//GET_CLIENTS("GET_CLIENTS"),
		;
		String id;

		TELEGRAM(String id){
			this.id = id;
		}
		public String getValue(){
			return this.id;
		}
	};
	
	public AdminActionWorker(){
		setFirestMode(MODE_FIREST_W);
	}
	private NioCommunication com = null;
	private Queue<HashMap<String, Object>> tel = null;
	void AdminCommunication(){
		this.com = getNioCommunication();
		tel = com.getTelegramQueue();
	}
	@Override
	public NioActionMsg onReceiveAction(NioActionMsg msg, SelectionKey selectionKey)throws Exception{
		Action<NioActionMsg> a = new Action<NioActionMsg>(msg, msg.getAction(), getNioCommunication().getName(),System.currentTimeMillis());
		sendActionEventToListener(a);
		return msg;
	}

	@Override
	public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception{
		return msg;
	}
	@Override
	public void receiveTelegram(HashMap<String, Object> telegram, SelectionKey selectionKey)throws Exception{
		NioActionMsg msg = new NioActionMsg();
		if(telegram.get(TELEGRAM.FROM.getValue())!=null)
			msg.putParam(TELEGRAM.FROM.getValue(),(String)telegram.get(TELEGRAM.FROM.getValue()));
		if(telegram.get(TELEGRAM.TO.getValue())!=null)
			msg.putParam(TELEGRAM.TO.getValue(),(String)telegram.get(TELEGRAM.TO.getValue()));
	
		msg.setAction((Integer)telegram.get(TELEGRAM.ACTION.getValue()));
		
		
//		if(telegram.get(TELEGRAM.GET_SERVERS.getValue())!=null){
//			msg.setAction(ACTION.GET_SERVERS.getValue());
//		}if(telegram.get(TELEGRAM.GET_CLIENTS.getValue())!=null){
//			msg.setAction(ACTION.GET_CLIENTS.getValue());
//		}
		
		
		if(selectionKey.isWritable()){
			msg.setSuccess(true);
			sendNioActionMsg(msg);
		}
		
	}

	
	
	
}
