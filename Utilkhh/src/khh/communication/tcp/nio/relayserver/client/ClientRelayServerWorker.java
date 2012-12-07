package khh.communication.tcp.nio.relayserver.client;

import java.nio.channels.SelectionKey;

import khh.communication.tcp.nio.relayserver.msg.RelayMsg;
import khh.communication.tcp.nio.relayserver.msg.format.FromToFormater;
import khh.communication.tcp.nio.relayserver.worker.RelayWorkerBase;
import khh.debug.LogK;
import khh.util.ByteUtil;
public class ClientRelayServerWorker extends RelayWorkerBase{
	private LogK log = LogK.getInstance();

	public ClientRelayServerWorker(){
		setFirestMode(MODE_FIREST_R);
	}

	@Override
	public RelayMsg onReceiveAction(RelayMsg msg, SelectionKey selectionKey) throws Exception{
		FromToFormater fromto = new FromToFormater();
		fromto.format(msg.getData());
		if(ACTION.LOGIN_LOGIN.getValue() == msg.getAction()){
			getServer().getMultimonitor().setSelectionKey(getServer().getName(),fromto.getFrom(), msg.getSelectionKey());
		}else if(fromto.getFrom() != null){
			getServer().getMultimonitor().setSelectionKey(getServer().getName(),fromto.getFrom(), msg.getSelectionKey());
		}
		return msg;
	}

	@Override
	public RelayMsg onSendAction(RelayMsg msg, SelectionKey selectionKey) throws Exception{
		if(ACTION.LOGIN_LOGIN.getValue() == msg.getAction()){
			msg.setAction(ACTION.LOGIN_OK.getValue());
			sendMsg(msg, selectionKey);
		}else{
			FromToFormater fromto = new FromToFormater();
			fromto.format(msg.getData());
			SelectionKey adminSelectionKey = getServer().getMultimonitor().getSelectionKey(fromto.getTo());
			
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
