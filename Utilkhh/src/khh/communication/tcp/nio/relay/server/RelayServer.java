package khh.communication.tcp.nio.relay.server;

import java.util.ArrayList;

import khh.communication.tcp.nio.relay.server.client.ClientRelayServer;
import khh.communication.tcp.nio.relay.server.client.ClientRelayServerWorker;
import khh.communication.tcp.nio.server.monitor.NioServerMultiMonitor;
import khh.debug.LogK;




public class RelayServer {
    private LogK log = LogK.getInstance();
	private int selectorPoolSize=10;
	private int workerPoolSize=10;
	
	
	public void start(ArrayList<Integer> serverPort) throws Exception {
		NioServerMultiMonitor multimonitor = new NioServerMultiMonitor();
		for(int i = 0; i < serverPort.size(); i++){
			ClientRelayServer clientRelayServer = new ClientRelayServer(serverPort.get(i),ClientRelayServerWorker.class);
			clientRelayServer.setMultimonitor(multimonitor);
			clientRelayServer.setName("ClientRelayServer"+serverPort.get(i));
			clientRelayServer.setSelectorPoolSize(getSelectorPoolSize());
			clientRelayServer.setWorkerPoolSize(getWorkerPoolSize());
			clientRelayServer.start();
		}
	}
	
	
	private int getSelectorPoolSize(){
		return selectorPoolSize;
	}
	private void setSelectorPoolSize(int selectorPoolSize){
		this.selectorPoolSize = selectorPoolSize;
	}
	private int getWorkerPoolSize(){
		return workerPoolSize;
	}
	private void setWorkerPoolSize(int workerPoolSize){
		this.workerPoolSize = workerPoolSize;
	}
	public static void main(String[] args) throws Exception  {
		ArrayList<Integer> serverPort = new ArrayList<Integer> ();
		serverPort.add(9090);
		serverPort.add(801);
		
		RelayServer f = new RelayServer();
		f.setSelectorPoolSize(10);
		f.setWorkerPoolSize(10);
		f.start(serverPort);
	}
}
