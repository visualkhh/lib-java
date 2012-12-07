package khh.communication.tcp.nio.relayserver.client;

import khh.communication.tcp.nio.relayserver.monitor.RelayServerMonitor;
import khh.communication.tcp.nio.server.NioServer;

public class ClientRelayServer{
	private int port = 80;
	private int selectorPoolSize=10;
	private int workerPoolSize=10;
	private NioServer server = null;
	public ClientRelayServer(){
		init();
	}

	private void init(){
	}

	public void start() throws Exception{
		server = new NioServer(getPort(), ClientRelayServerWorker.class);
		server.setSelectorPoolSize(getSelectorPoolSize());
		server.setWorkerPoolSize(getWorkerPoolSize());
		server.start();
		RelayServerMonitor.getInstance().addClinetMinitor(server.getMonitor());
	}

	public int getPort(){
		return port;
	}

	public void setPort(int port){
		this.port = port;
	}

	public int getSelectorPoolSize(){
		return selectorPoolSize;
	}

	public void setSelectorPoolSize(int selectorPoolSize){
		this.selectorPoolSize = selectorPoolSize;
	}

	public int getWorkerPoolSize(){
		return workerPoolSize;
	}

	public void setWorkerPoolSize(int workerPoolSize){
		this.workerPoolSize = workerPoolSize;
	}
	



}
