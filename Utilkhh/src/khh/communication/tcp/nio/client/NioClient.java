package khh.communication.tcp.nio.client;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import khh.communication.tcp.nio.NioCommunication;
import khh.communication.tcp.nio.connect.NioConnectManager;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.debug.LogK;

public class NioClient extends NioCommunication{
	private int connectionTimeout	= 0;
	private LogK log 					= LogK.getInstance();
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
	public void init() throws IOException{
		try{
			super.setSelectorManagerSize(1);
			super.setWorkerManagerSize(1);
		}catch (Exception e) {
		}
	}
	
	@Override
	public void start() throws Exception{
		super.start();
		NioConnectManager nioconnectmanager = new NioConnectManager(getSelectorManagerList(),getIp(),getPort(),getConnectionTimeout());
		nioconnectmanager.start();
	}
	@Override
	public void stop() throws Exception{
	}
	
	public int getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(int connectionTimeout){
		this.connectionTimeout = connectionTimeout;
	}

	@Override
	public void setSelectorManagerSize(int selectorManagerSize) throws Exception{
		throw new Exception("NioClient  SelectorManagerSize Only 1  sorry  No Input plz");
	}
	@Override
	public void setWorkerManagerSize(int workerManagerSize) throws Exception{
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
}
