package khh.finger.remote.finmanager.admin;

import java.nio.channels.SelectionKey;

import khh.communication.tcp.nio.worker.NioWorker;
import khh.finger.remote.finmanager.client.ClientServer;
import khh.finger.remote.finmanager.msg.FingMsg;
import khh.finger.remote.finmanager.msg.format.FingTitleFormater;
import khh.finger.remote.finmanager.worker.FingWorkerBase;
import khh.finger.remote.finmanager.worker.FingWorkerBase.ACTION;
import khh.std.adapter.Adapter_Std;

public class AdminServerWorker extends FingWorkerBase {
	public AdminServerWorker() {
		setFirestMode(MODE_FIREST_R);
	}

	
	@Override
	public void execute(SelectionKey selectionKey) throws Exception {
		FingMsg msg = null;
		if(selectionKey.isReadable()){
			msg = receiveMsg(selectionKey);
			msg = onReceiveAction(msg,selectionKey);
			onSendAction(msg, selectionKey);
		}else if(selectionKey.isWritable()){
		}
		
	}
	
	
	@Override
	public FingMsg onReceiveAction(FingMsg msg, SelectionKey selectionKey) throws Exception{
		if(msg!=null && msg.isSuccess()==false){
			return null;
		}
			if(ACTION.LOGIN_LOGIN.getValue() == msg.getAction()){
				AdminServer.userList.set(msg.getDataToStr(), msg.getSelectionKey());
			}else if(ACTION.LOGIN_LOGOUT.getValue() == msg.getAction()){
				AdminServer.userList.remove(msg.getDataToStr());
			}
			
//			else if(ACTION.ADMIN_CLIENT_JOIN.getValue() == msg.getAction()){
//			}
		return msg;
	}

	@Override
	public FingMsg onSendAction(FingMsg msg, SelectionKey selectionKey) throws Exception{
		if(msg!=null && msg.isSuccess()==false && !selectionKey.isWritable()){
			return null;
		}
		FingMsg sendMsg = new FingMsg();
		sendMsg.setSuccess(true);
		
		if(ACTION.LOGIN_LOGIN.getValue() == msg.getAction()){
			sendMsg.setAction(ACTION.LOGIN_OK.getValue());
			sendMsg(sendMsg,selectionKey);
		}else if(ACTION.LOGIN_LOGOUT.getValue() == msg.getAction()){
		}
		
		else if(ACTION.ADMIN_CLIENT_JOIN.getValue() == msg.getAction()){
			FingTitleFormater titleformat = new FingTitleFormater();
			titleformat.format(msg.getData());
			
			SelectionKey clientSelectionKey = ClientServer.userList.get(titleformat.getTitle());
			sendMsg.setAction(ACTION.ADMIN_CLIENT_JOIN.getValue());
			sendMsg.setData(titleformat.getData());
			sendMsg(sendMsg, clientSelectionKey);
		}
			
			
		return msg;
	}







}
