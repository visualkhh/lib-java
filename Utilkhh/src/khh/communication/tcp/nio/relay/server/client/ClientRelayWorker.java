package khh.communication.tcp.nio.relay.server.client;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.HashMap;

import khh.communication.tcp.nio.server.monitor.NioServerMonitor;
import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.msg.FromToFormater;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.date.util.DateUtil;
import khh.debug.LogK;
import khh.std.adapter.AdapterMap;
import khh.string.token.StringTokenizer;
public class ClientRelayWorker extends NioActionWorker{
	private LogK log = LogK.getInstance();
    public ClientRelayWorker(int firestMode){
		super(firestMode);
	}
    public ClientRelayWorker(){
    	
    }
	public NioActionMsg onReceiveAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception{
		FromToFormater fromto = new FromToFormater();
		fromto.format(msg.get());
		if(ACTION.POLL.getValue() == msg.getAction()){
			getServer().getMultimonitor().putSelectionKey(getServer().getName(),fromto.getFrom(), selectionKey);
		}else if(fromto.getFrom() != null){
			getServer().getMultimonitor().putSelectionKey(getServer().getName(),fromto.getFrom(), selectionKey);
		}
		return msg;
	}

	public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception{
		if(ACTION.POLL.getValue() == msg.getAction()){
			msg.clear();
			sendNioActionMsg(msg, selectionKey);
		}else if(ACTION.ECHO.getValue() == msg.getAction()){
			sendNioActionMsg(msg, selectionKey);
		}else if(ACTION.GET_SERVERTIME.getValue() == msg.getAction()){
			msg.set(DateUtil.getDate("yyyy/MM/dd HH:mm:ss/SSS"));
			sendNioActionMsg(msg, selectionKey);
		}
		
		else if(ACTION.GET_SERVERS.getValue() == msg.getAction()){
			HashMap<String, NioServerMonitor> monitors = getServer().getMultimonitor().getMonitors();
			StringTokenizer token = new StringTokenizer(",");
			msg.set(token.makeKeyString(monitors));
			sendNioActionMsg(msg, selectionKey);
		}else if(ACTION.GET_CLIENTS.getValue() == msg.getAction()){
			FromToFormater fromto = new FromToFormater();
			fromto.format(msg.get());
			HashMap<String, SelectableChannel> clients = getServer().getMultimonitor().getClientSocketChannels(fromto.getString());
			StringTokenizer token = new StringTokenizer(",");
			msg.set(token.makeString(clients));
			sendNioActionMsg(msg, selectionKey);
		}
		
		
		
		else if(ACTION.FROMTO.getValue() <= msg.getAction()){
			FromToFormater fromto = new FromToFormater();
			fromto.format(msg.get());
			SelectionKey toSelectionKey = getServer().getMultimonitor().getSelectionKey(fromto.getTo());
			if(toSelectionKey!=null){
				try{
				    sendNioActionMsg(msg, toSelectionKey);
				}catch (Exception e) {
					log.debug("Error:"+e.getMessage());
					msg.setAction(ACTION.EXCEPTION.getValue());
					msg.set(e+","+e.getMessage()+","+fromto);
					sendNioActionMsg(msg,selectionKey);
				}
			}else{
				msg.setAction(ACTION.NOSUCH_TOTARGET_EXCEPTION.getValue());
				msg.clear();
				sendNioActionMsg(msg,selectionKey);
			}
		}
		return msg;
	}
	
}
