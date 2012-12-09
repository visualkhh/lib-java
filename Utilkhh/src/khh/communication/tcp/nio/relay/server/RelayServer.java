package khh.communication.tcp.nio.relay.server;

import java.util.ArrayList;

import khh.communication.Communication_I;
import khh.communication.tcp.nio.relay.server.client.ClientRelayServer;
import khh.communication.tcp.nio.relay.server.client.ClientRelayServerWorker;
import khh.communication.tcp.nio.server.monitor.NioServerMultiMonitor;




public class RelayServer implements Communication_I {
	private int selectorPoolSize=10;
	private int workerPoolSize=10;
	private ArrayList<Integer> serverPort=null;
	
	
	public void start() throws Exception {
		NioServerMultiMonitor multimonitor = new NioServerMultiMonitor();
		ArrayList<Integer> serverPort=getServerPort();
		for(int i = 0; serverPort!=null && i < serverPort.size(); i++){
			ClientRelayServer clientRelayServer = new ClientRelayServer(serverPort.get(i),ClientRelayServerWorker.class);
			clientRelayServer.setMultimonitor(multimonitor);
			clientRelayServer.setName("ClientRelayServer"+serverPort.get(i));
			clientRelayServer.setSelectorPoolSize(getSelectorPoolSize());
			clientRelayServer.setWorkerPoolSize(getWorkerPoolSize());
			clientRelayServer.start();
		}
	}

    @Override
    public void stop() throws Exception {
        // TODO Auto-generated method stub
        
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

    
    
    public ArrayList<Integer> getServerPort() {
        return serverPort;
    }

    public void setServerPort(ArrayList<Integer> serverPort) {
        this.serverPort = serverPort;
    }


}
