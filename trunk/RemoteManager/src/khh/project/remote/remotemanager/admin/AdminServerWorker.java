package khh.project.remote.remotemanager.admin;

import java.nio.channels.SelectionKey;

import khh.project.remote.remotemanager.monitor.RemoteServerMonitor;
import khh.project.remote.remotemanager.msg.RemoteMsg;
import khh.project.remote.remotemanager.msg.format.FromToFormater;
import khh.project.remote.remotemanager.worker.RemoteWorkerBase;

public class AdminServerWorker extends RemoteWorkerBase{
	RemoteServerMonitor monitor = RemoteServerMonitor.getInstance();

	public AdminServerWorker(){
		setFirestMode(MODE_FIREST_R);
	}

	@Override
	public RemoteMsg onReceiveAction(RemoteMsg msg, SelectionKey selectionKey) throws Exception{
		FromToFormater fromto = new FromToFormater();
		if(ACTION.LOGIN_LOGIN.getValue() == msg.getAction()){
			fromto.format(msg.getData());
			monitor.setAdminSelectionKey(fromto.getFrom(), msg.getSelectionKey());
		}

		return msg;
	}

	@Override
	public RemoteMsg onSendAction(RemoteMsg msg, SelectionKey selectionKey) throws Exception{
		if(ACTION.LOGIN_LOGIN.getValue() == msg.getAction()){
			msg.setAction(ACTION.LOGIN_OK.getValue());
			sendMsg(msg, selectionKey);
		}else{
			FromToFormater fromto = new FromToFormater();
			fromto.format(msg.getData());
			SelectionKey clientSelectionKey = monitor.getClientSelectionKey(fromto.getTo());
			sendMsg(msg, clientSelectionKey);
		}

		return msg;
	}

}
