package khh.communication.tcp.nio.client;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import khh.collection.RoundRobin;
import khh.communication.tcp.nio.NioCommunication;
import khh.communication.tcp.nio.connect.NioConnectManager;
import khh.communication.tcp.nio.selector.NioSelectorManager;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.communication.tcp.nio.worker.NioWorkerManager;

public class NioClient extends NioCommunication{
	private int connectionTimeout	= 0;
	public NioClient() throws IOException{
		init();
	}
	public NioClient(String serverAddr, int serverPort,int serverConnectionTimeout,Class nioWorkerClass) throws IOException{
		try{
			setIp(serverAddr);
		}catch(Exception e){
		}
		setPort(serverPort);
		setConnectionTimeout(serverConnectionTimeout);
		setNioWorkerClass(nioWorkerClass);
		init();
	}
	public NioClient(String ServerAddr, int ServerPort,Class nioWorkerClass) throws IOException{
		try{
			setIp(ServerAddr);
		}catch(Exception e){
		}
		setPort(ServerPort);
		setConnectionTimeout(0);
		setNioWorkerClass(nioWorkerClass);
		init();
	}
	public NioClient(String serverAddr, int serverPort,int serverConnectionTimeout,NioWorker nioWorker) throws IOException{
		try{
			setIp(serverAddr);
		}catch(Exception e){
		}
		setPort(serverPort);
		setConnectionTimeout(serverConnectionTimeout);
		ArrayList<NioWorker> a= new ArrayList<NioWorker>();
		a.add(nioWorker);
		try{
			setNioWorkerList(a);
		}catch(Exception e){
			e.printStackTrace();
		}
		init();
	}
	public NioClient(String ServerAddr, int ServerPort,NioWorker nioWorker) throws IOException{
		try{
			setIp(ServerAddr);
		}catch(Exception e){
		}
		setPort(ServerPort);
		setConnectionTimeout(0);
		ArrayList<NioWorker> a= new ArrayList<NioWorker>();
		a.add(nioWorker);
		try{
			setNioWorkerList(a);
		}catch(Exception e){
			e.printStackTrace();
		}
		init();
	}
	
	
	
	
	public void init() throws IOException{
		try{
			super.setSelectorManagerSize(1);
			super.setWorkerManagerSize(1);
		}catch (Exception e) {
		}
	}
	
	NioConnectManager nioconnectmanager=null;
	@Override
	public void start() throws Exception{
		super.start();
		nioconnectmanager = new NioConnectManager(getSelectorManagerList(),getIp(),getPort(),getConnectionTimeout());
		nioconnectmanager.start();
		
	}
	
	
	@Override
	public void stop() throws Exception{
		if(getNioconnectmanager()!=null)
		getNioconnectmanager().interrupt();
		
			RoundRobin<NioSelectorManager> sm = getSelectorManagerList();
		if(sm != null){
			for(int i = 0; i < sm.size(); i++){
				if(sm.get(i)!=null)
				sm.get(i).interrupt();
			}
		}
		ArrayList<NioWorkerManager> wm = getWorkerManagerList();
		if(wm!=null){
			for(int i = 0; i < wm.size(); i++){
				if(wm.get(i)!=null)
				wm.get(i).interrupt();
			}
		}
	}
	
	public void connnection() throws IOException{
		nioconnectmanager.connnection();
	}
	public boolean isConnected(){
		SocketChannel s = nioconnectmanager==null?null:nioconnectmanager.getSocketChannel();
		return  s==null?false:s.isConnected();
	}
	
	public int getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(int connectionTimeout){
		this.connectionTimeout = connectionTimeout;
	}

	protected NioConnectManager getNioconnectmanager(){
		return nioconnectmanager;
	}
	@Override
	public void setSelectorManagerSize(int selectorManagerSize) throws Exception{
		if(selectorManagerSize>1)
		throw new Exception("NioClient  SelectorManagerSize Only 1  sorry  No Input plz");
	}
	@Override
	public void setWorkerManagerSize(int workerManagerSize) throws Exception{
		if(workerManagerSize>1)
		throw new Exception("NioClient WorkerManagerSize Only 1 Size sorry No Input plz");
	}
	@Override
	public void setNioWorkerList(ArrayList<NioWorker> nioWorker) throws Exception{
		if(nioWorker.size()==1){
			super.setNioWorkerList(nioWorker);
		}else{
			throw new Exception("NioClient NioWorkerList Only 1 Size sorry you Size"+nioWorker.size());
		}
	}
	@Override
	public String toString(){
		return	super.toString()+" isConnected:"+isConnected();
	}
}
