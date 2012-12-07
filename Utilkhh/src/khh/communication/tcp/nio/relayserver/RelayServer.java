package khh.communication.tcp.nio.relayserver;

import java.util.ArrayList;

import khh.communication.tcp.nio.relayserver.client.ClientRelayServer;
import khh.debug.LogK;




public class RelayServer {
    private LogK log = LogK.getInstance();
	private int selectorPoolSize=10;
	private int workerPoolSize=10;
	
	
	public void start(ArrayList<Integer> serverPort) throws Exception {
		for(int i = 0; i < serverPort.size(); i++){
			ClientRelayServer clientRelayServer = new ClientRelayServer();
			clientRelayServer.setPort(serverPort.get(i));
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
		serverPort.add(80);
		
		RelayServer f = new RelayServer();
		f.start(serverPort);
	}
}
