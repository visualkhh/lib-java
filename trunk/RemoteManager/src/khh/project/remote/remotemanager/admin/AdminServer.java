package khh.project.remote.remotemanager.admin;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;

import khh.communication.tcp.nio.server.NioServer;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.project.remote.remotemanager.msg.RemoteMsg;
import khh.std.adapter.Adapter_Std;

public class AdminServer{
	
	private int port = 9090;
	private NioServer server = null;
	public AdminServer(){
		init();
	}

	private void init(){
	}

	public void start() throws Exception{
		server = new NioServer(getPort(), AdminServerWorker.class);
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
