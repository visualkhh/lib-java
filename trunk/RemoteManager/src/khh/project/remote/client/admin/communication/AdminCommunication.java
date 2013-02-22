package khh.project.remote.client.admin.communication;

import java.util.HashMap;

import khh.communication.tcp.nio.client.NioClient;
import khh.listener.action.ActionListener;
import khh.project.remote.client.admin.communication.AdminActionWorker.TELEGRAM;
import khh.communication.tcp.nio.worker.msg.NioActionMsg.ACTION;
public class AdminCommunication{
	private NioClient client = null;
	private AdminActionWorker adminWorker = null;
	public AdminCommunication(){
		adminWorker=new AdminActionWorker();
	}

	public void connection(String ip, int port) throws Exception{
		if(client != null){
			client.stop();
			client = null;
		}
		client = new NioClient(ip, port, adminWorker);
		client.start();
	}
	
	
	public void getServers(String from){
		HashMap<String,Object> telegram = new HashMap<String, Object>();
		telegram.put(TELEGRAM.ACTION.getValue(), ACTION.GET_SERVERS.getValue());
		pushTelegram(from,null,telegram);
	}
	
	public void getClients(String from,String servername){
		HashMap<String,Object> telegram = new HashMap<String, Object>();
		telegram.put(TELEGRAM.ACTION.getValue(), ACTION.GET_CLIENTS.getValue());
		pushTelegram(from,null,telegram);
	}
	
	
	
	
	public void pushTelegram(String from,String to,HashMap<String,Object> telegram){
		if(from!=null)
		telegram.put(TELEGRAM.FROM.getValue(), from);
		if(to!=null)
		telegram.put(TELEGRAM.TO.getValue(), to);
		client.pushTelegram(telegram);
		
	}
	public void addActionEventListener(ActionListener listener){
		client.addActionEventListener(listener);
	}
	
	
	public void stop() {
		try{
			client.stop();
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	protected NioClient getClient(){
		return client;
	}
	
	
}
