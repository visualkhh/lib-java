package khh.communication.tcp.nio.relay.server.client;

import java.nio.channels.SelectionKey;

import khh.communication.tcp.nio.protocol.NioMsg;
import khh.communication.tcp.nio.relay.format.FromToRelayFormater;
import khh.communication.tcp.nio.relay.server.worker.RelayWorkerBase;
import khh.debug.LogK;
import khh.util.ByteUtil;
public class ClientRelayServerWorker extends RelayWorkerBase{
	private LogK log = LogK.getInstance();

	public ClientRelayServerWorker(){
		setFirestMode(MODE_FIREST_R);
	}

	@Override
	public NioMsg onReceiveAction(NioMsg msg, SelectionKey selectionKey) throws Exception{
		FromToRelayFormater fromto = new FromToRelayFormater();
		fromto.format(msg.get());
		if(ACTION.LOGIN_LOGIN.getValue() == msg.getAction()){
			getServer().getMultimonitor().setSelectionKey(getServer().getName(),fromto.getFrom(), selectionKey);
		}else if(fromto.getFrom() != null){
			getServer().getMultimonitor().setSelectionKey(getServer().getName(),fromto.getFrom(), selectionKey);
		}
		return msg;
	}

	@Override
	public NioMsg onSendAction(NioMsg msg, SelectionKey selectionKey) throws Exception{
		if(ACTION.LOGIN_LOGIN.getValue() == msg.getAction()){
			msg.setAction(ACTION.LOGIN_OK.getValue());
			sendNioMsg(msg, selectionKey);
		}else{
			FromToRelayFormater fromto = new FromToRelayFormater();
			fromto.format(msg.get());
			SelectionKey toSelectionKey = getServer().getMultimonitor().getSelectionKey(fromto.getTo());
			
			try{
			    sendNioMsg(msg, toSelectionKey);
			}catch (Exception e) {
				log.debug("Error:"+e.getMessage());
				msg.set(ByteUtil.toByteBuffer(e.toString()+"["+e.getMessage()+"]"+fromto));
				sendNioMsg(msg,selectionKey);
			}
		}
		return msg;
	}

}
