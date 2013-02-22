package khh.project.remote.server;

import java.util.ArrayList;

import khh.communication.tcp.nio.relay.server.RelayServer;
import khh.debug.LogK;
public class RemoteServer{
	private LogK log = LogK.getInstance();

	public void start() throws Exception{
		ArrayList<Integer> port = new ArrayList<Integer>();
		port.add(9595);
		port.add(80);
		
		RelayServer relayServer = new RelayServer();
		relayServer.setSelectorPoolSize(1);
		relayServer.setWorkerPoolSize(1);
		relayServer.setPort(port);
		relayServer.start();
	}

	public static void main(String[] args) throws Exception{
		RemoteServer f = new RemoteServer();
		f.start();
		
		
	}
}
