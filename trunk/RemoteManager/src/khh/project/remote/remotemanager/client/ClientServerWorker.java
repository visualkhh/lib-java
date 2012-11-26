package khh.project.remote.remotemanager.client;

import java.nio.channels.SelectionKey;

import khh.debug.LogK;
import khh.project.remote.remotemanager.msg.RemoteMsg;
import khh.project.remote.remotemanager.worker.RemoteWorkerBase;

public class ClientServerWorker extends RemoteWorkerBase{
	private LogK log = LogK.getInstance();
	public ClientServerWorker() {
		setFirestMode(MODE_FIREST_R);
	}


	@Override
	public void execute(SelectionKey selectionKey) throws Exception {
		RemoteMsg msg = null;
		if(selectionKey.isReadable()){
			msg = receiveMsg(selectionKey);
			msg = onReceiveAction(msg,selectionKey);
			onSendAction(msg, selectionKey);
		}else if(selectionKey.isWritable()){
		}
		
	}
	
	
	
	@Override
	public	RemoteMsg onReceiveAction(RemoteMsg msg, SelectionKey selectionKey) throws Exception {
		if(msg!=null && msg.isSuccess()==false){
			return null;
		}
			if(ACTION.LOGIN_LOGIN.getValue() == msg.getAction()){
				ClientServer.userList.set(msg.getDataToStr(), msg.getSelectionKey());
			}else if(ACTION.LOGIN_LOGOUT.getValue() == msg.getAction()){
				ClientServer.userList.remove(msg.getDataToStr());
			}
		return msg;
	}


	@Override
	public RemoteMsg onSendAction(RemoteMsg msg, SelectionKey selectionKey) throws Exception {
		if(msg!=null && msg.isSuccess()==false && !selectionKey.isWritable()){
			return null;
		}
		RemoteMsg sendMsg = new RemoteMsg();
		sendMsg.setSuccess(true);
		
		if(ACTION.LOGIN_LOGIN.getValue() == msg.getAction()){
			sendMsg.setAction(ACTION.LOGIN_OK.getValue());
			sendMsg(sendMsg,selectionKey);
		}else if(ACTION.LOGIN_LOGOUT.getValue() == msg.getAction()){
		}
		return msg;
	}


}
