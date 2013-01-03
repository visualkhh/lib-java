package khh.communication.tcp.nio.relay.server;

import java.util.ArrayList;

import khh.communication.Communication_I;
import khh.communication.tcp.nio.monitor.NioMultiMonitor;
import khh.communication.tcp.nio.relay.server.client.RelayClientServer;
import khh.communication.tcp.nio.relay.server.client.worker.RelayClientWorker;




public class RelayServer implements Communication_I {
	private int selectorPoolSize=10;
	private int workerPoolSize=10;
	private ArrayList<Integer> serverPort=null;
	
	
	public void start() throws Exception {
		NioMultiMonitor multimonitor = new NioMultiMonitor();
		ArrayList<Integer> serverPort=getPort();
		for(int i = 0; serverPort!=null && i < serverPort.size(); i++){
			RelayClientServer clientRelayServer = new RelayClientServer(serverPort.get(i),RelayClientWorker.class);
			clientRelayServer.setMultimonitor(multimonitor);
			clientRelayServer.setName("ClientRelayServer"+serverPort.get(i));
			clientRelayServer.setSelectorManagerSize(getSelectorPoolSize());
			clientRelayServer.setWorkerManagerSize(getWorkerPoolSize());
			clientRelayServer.start();
		}
	}


    public void stop() throws Exception {
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

    
    
    public ArrayList<Integer> getPort() {
        return serverPort;
    }

    public void setPort(ArrayList<Integer> serverPort) {
        this.serverPort = serverPort;
    }


}
