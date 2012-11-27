package khh.project.remote.remotemanager.client;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;

import khh.communication.tcp.nio.server.NioServer;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.project.remote.remotemanager.msg.RemoteMsg;
import khh.std.adapter.Adapter_Std;

public class ClientServer{
	private int port = 80;
	private NioServer server = null;
	public ClientServer(){
		init();
	}

	private void init(){
	}

	public void start() throws Exception{
		server = new NioServer(getPort(), ClientServerWorker.class);
		server.setSelectorPoolSize(10);
		server.setWorkerPoolSize(10);
		server.start();
	}

	private int getPort(){
		return port;
	}

	private void setPort(int port){
		this.port = port;
	}



}
