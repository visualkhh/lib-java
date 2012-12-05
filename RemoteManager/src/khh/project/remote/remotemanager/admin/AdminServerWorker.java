package khh.project.remote.remotemanager.admin;

import java.nio.channels.SelectionKey;

import khh.communication.tcp.nio.worker.NioWorker;
import khh.project.remote.remotemanager.client.ClientServer;
import khh.project.remote.remotemanager.monitor.RemoteServerMonitor;
import khh.project.remote.remotemanager.msg.RemoteMsg;
import khh.project.remote.remotemanager.msg.format.RemoteTitleFormater;
import khh.project.remote.remotemanager.worker.RemoteWorkerBase;
import khh.project.remote.remotemanager.worker.RemoteWorkerBase.ACTION;
import khh.std.adapter.Adapter_Std;

public class AdminServerWorker extends RemoteWorkerBase{
	RemoteServerMonitor monitor = RemoteServerMonitor.getInstance();

	public AdminServerWorker(){
		setFirestMode(MODE_FIREST_R);
	}

	@Override
	public void execute(SelectionKey selectionKey) throws Exception{
		RemoteMsg msg = null;
		if(selectionKey.isReadable()){
			msg = receiveMsg(selectionKey);
			msg = onReceiveAction(msg, selectionKey);
			onSendAction(msg, selectionKey);
		}else if(selectionKey.isWritable()){
		}

	}

	@Override
	public RemoteMsg onReceiveAction(RemoteMsg msg, SelectionKey selectionKey) throws Exception{
		if(msg != null && msg.isSuccess() == false){
			return null;
		}
		if(ACTION.LOGIN_LOGIN.getValue() == msg.getAction()){
			RemoteTitleFormater titleformat = new RemoteTitleFormater();
			titleformat.format(msg.getData());
			monitor.setAdminSelectionKey(titleformat.getTitle(), msg.getSelectionKey());
		}else if(ACTION.LOGIN_LOGOUT.getValue() == msg.getAction()){
			RemoteTitleFormater titleformat = new RemoteTitleFormater();
			titleformat.format(msg.getData());
			monitor.removeAdminSelectionKey(titleformat.getTitle());
		}
		return msg;
	}

	@Override
	public RemoteMsg onSendAction(RemoteMsg msg, SelectionKey selectionKey) throws Exception{
		if(msg != null && msg.isSuccess() == false && !selectionKey.isWritable()){
			return null;
		}
		RemoteMsg sendMsg = new RemoteMsg();
		sendMsg.setSuccess(true);

		if(ACTION.LOGIN_LOGIN.getValue() == msg.getAction()){
			sendMsg.setAction(ACTION.LOGIN_OK.getValue());
			sendMsg(sendMsg, selectionKey);
		}else if(ACTION.LOGIN_LOGOUT.getValue() == msg.getAction()){
		}else if(ACTION.ADMIN_CLIENT_JOIN.getValue() == msg.getAction()){
			RemoteTitleFormater titleformat = new RemoteTitleFormater();
			titleformat.format(msg.getData());
			SelectionKey clientSelectionKey = monitor.getClientSelectionKey(titleformat.getTitle());
			sendMsg.setAction(ACTION.ADMIN_CLIENT_JOIN.getValue());
			sendMsg.setData(titleformat.getData());
			sendMsg(sendMsg, clientSelectionKey);
		}else{
			// sendMsg.setAction(msg.getAction());
			// sendMsg.setData(msg.getData());
			// sendMsg(sendMsg,selectionKey);

		}

		return msg;
	}

}
