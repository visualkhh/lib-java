package khh.communication.tcp.nio.relay.server;

import java.util.ArrayList;

import khh.communication.Communication_I;
import khh.communication.tcp.nio.relay.server.client.ClientRelayServer;
import khh.communication.tcp.nio.relay.server.client.ClientRelayServerWorker;
import khh.communication.tcp.nio.server.monitor.NioServerMultiMonitor;
import khh.debug.LogK;




public class RelayServer implements Communication_I {
    private LogK log = LogK.getInstance();
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




    
    
    
    
    private ArrayList<Integer> getServerPort() {
        return serverPort;
    }

    private void setServerPort(ArrayList<Integer> serverPort) {
        this.serverPort = serverPort;
    }


}
