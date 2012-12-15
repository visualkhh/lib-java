package khh.communication.tcp.nio.relay.server.client;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.HashMap;

import khh.communication.tcp.nio.server.monitor.NioServerMonitor;
import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.date.util.DateUtil;
import khh.debug.LogK;
import khh.string.token.StringTokenizer;
public class ClientRelayWorker extends NioActionWorker{
	private LogK log = LogK.getInstance();
    public ClientRelayWorker(int firestMode){
		super(firestMode);
		init();
	}
    public ClientRelayWorker(){
    	init();
    }
    public void init(){
        setAutoFeedbackException(true);
    }
	public NioActionMsg onReceiveAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception{
		String from  =  msg.getParamString(NioActionMsg.PARAM.FROM.getValue());
		if(from!=null){
			getServer().getMultimonitor().putSelectionKey(getServer().getName(),from, selectionKey);
		}
//		if(ACTION.POLL.getValue() == msg.getAction()){
//			getServer().getMultimonitor().putSelectionKey(getServer().getName(),fromto.getFrom(), selectionKey);
//		}else if(fromto.getFrom() != null){
//			getServer().getMultimonitor().putSelectionKey(getServer().getName(),fromto.getFrom(), selectionKey);
//		}
		return msg;
	}

	public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception{
		if(NioActionMsg.ACTION.POLL.getValue() == msg.getAction()){
			msg.clear();
			sendNioActionMsg(msg, selectionKey);
		}else if(NioActionMsg.ACTION.ECHO.getValue() == msg.getAction()){
			sendNioActionMsg(msg, selectionKey);
		}else if(NioActionMsg.ACTION.GET_SERVERTIME.getValue() == msg.getAction()){
		    String format = msg.getString(); 
		    if(format!=null){
		        msg.set(DateUtil.getDate(format));
		    }else{
		        msg.set(DateUtil.getDate("yyyy/MM/dd HH:mm:ss/SSS"));
		    }
			sendNioActionMsg(msg, selectionKey);
		}
		
		else if(NioActionMsg.ACTION.GET_SERVERS.getValue() == msg.getAction()){
			HashMap<String, NioServerMonitor> monitors = getServer().getMultimonitor().getMonitors();
			StringTokenizer token = new StringTokenizer(",");
			msg.set(token.makeKeyString(monitors));
			sendNioActionMsg(msg, selectionKey);
		}else if(NioActionMsg.ACTION.GET_CLIENTS.getValue() == msg.getAction()){
			HashMap<String, SelectableChannel> clients = getServer().getMultimonitor().getClientSocketChannels(msg.getString());
			StringTokenizer token = new StringTokenizer(",");
			msg.set(token.makeString(clients));
			sendNioActionMsg(msg, selectionKey);
		}
		
		
		
		else if(NioActionMsg.ACTION.FROMTO.getValue() <= msg.getAction()){
			SelectionKey toSelectionKey = getServer().getMultimonitor().getSelectionKey(msg.getParamString(NioActionMsg.PARAM.TO.getValue()));
			if(toSelectionKey!=null){
				try{
				    sendNioActionMsg(msg, toSelectionKey);
				}catch (Exception e) {
					//log.debug("Error:"+e.getMessage());
					msg.setAction(NioActionMsg.ACTION.EXCEPTION.getValue());
					msg.set(e+","+e.getMessage()+","+msg);
					sendNioActionMsg(msg,selectionKey);
				}
			}else{
			    msg.clear();
				msg.setAction(NioActionMsg.ACTION.NOSUCH_TOTARGET_EXCEPTION.getValue());
				msg.setSuccess(true);
				sendNioActionMsg(msg,selectionKey);
			}
		}
		return msg;
	}
	
}
