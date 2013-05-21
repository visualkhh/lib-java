package khh.communication.tcp.nio.bypass;

import java.util.ArrayList;

import khh.communication.Communication_I;
import khh.communication.tcp.nio.bypass.client.worker.NioByPassClientWorker;
import khh.communication.tcp.nio.bypass.server.worker.NioByPassServerWorker;
import khh.communication.tcp.nio.client.NioClient;
import khh.communication.tcp.nio.server.NioServer;
import khh.communication.tcp.nio.worker.NioWorker;

public class NioByPass implements Communication_I {

	int serverPort = 9999;
	String targetAddr = "127.0.0.1";
	int targetPort = 9090;

	NioServer server;
	NioClient client;
	
	public NioByPass() {
	}
	public NioByPass(int serverPort,String targetAddr,int targetPort) {
		this.serverPort = serverPort;
		this.targetAddr = targetAddr;
		this.targetPort = targetPort;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getTargetAddr() {
		return targetAddr;
	}

	public void setTargetAddr(String targetAddr) {
		this.targetAddr = targetAddr;
	}

	public int getTargetPort() {
		return targetPort;
	}

	public void setTargetPort(int targetPort) {
		this.targetPort = targetPort;
	}

	@Override
	public void start() throws Exception {
		NioByPassServerWorker serverWorker = new NioByPassServerWorker();
		NioByPassClientWorker clientWorker = new NioByPassClientWorker();
		serverWorker.setClient(clientWorker);
		clientWorker.setServer(serverWorker);
		
		 
		ArrayList<NioWorker> serverlist = new ArrayList<NioWorker>();
		serverlist .add (serverWorker);
		
		
		server  =  new NioServer(getServerPort(), serverlist);
		server.setSelectorManagerSize(1);
		server.setWorkerManagerSize(1);
		server.start();
		
		
		client =  new NioClient(getTargetAddr(),getTargetPort(),clientWorker);
		client.start();
	}

	@Override
	public void stop() throws Exception {
		client.stop();
		server.stop();
	}

}
