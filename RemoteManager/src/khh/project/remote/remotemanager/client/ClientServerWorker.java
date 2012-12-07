package khh.project.remote.remotemanager.client;

import java.nio.channels.SelectionKey;

import khh.debug.LogK;
import khh.project.remote.remotemanager.monitor.RemoteServerMonitor;
import khh.project.remote.remotemanager.msg.RemoteMsg;
import khh.project.remote.remotemanager.msg.format.FromToFormater;
import khh.project.remote.remotemanager.worker.RemoteWorkerBase;
import khh.util.*;
public class ClientServerWorker extends RemoteWorkerBase{
	private LogK log = LogK.getInstance();
	RemoteServerMonitor monitor = RemoteServerMonitor.getInstance();

	public ClientServerWorker(){
		setFirestMode(MODE_FIREST_R);
	}

	@Override
	public RemoteMsg onReceiveAction(RemoteMsg msg, SelectionKey selectionKey) throws Exception{
		FromToFormater fromto = new FromToFormater();
		fromto.format(msg.getData());
		if(ACTION.LOGIN_LOGIN.getValue() == msg.getAction()){
			monitor.setClientSelectionKey(fromto.getFrom(), msg.getSelectionKey());
		}else if(fromto.getFrom() != null){
			monitor.setClientSelectionKey(fromto.getFrom(), msg.getSelectionKey());
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
			SelectionKey adminSelectionKey = monitor.getAdminSelectionKey(fromto.getTo());
			
			try{
				sendMsg(msg, adminSelectionKey);
			}catch (Exception e) {
				log.debug("Error Msg ClientSend"+e.getMessage());
				msg.setData(ByteUtil.toByteBuffer(e.toString()+"["+e.getMessage()+"]"));
				sendMsg(msg,selectionKey);
			}
		}
		return msg;
	}

}
