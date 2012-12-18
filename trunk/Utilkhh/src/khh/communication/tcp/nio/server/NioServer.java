package khh.communication.tcp.nio.server;

import java.util.ArrayList;

import khh.communication.tcp.nio.NioCommunication;
import khh.communication.tcp.nio.aceept.NioAcceptManager;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.debug.LogK;
public class NioServer extends NioCommunication
{
	
	private NioAcceptManager acceptSelectThread			= null;
	private LogK log = LogK.getInstance();
	
	public NioServer(int port,Class nioWorkerClass){
		setPort(port);
		setNioWorkerClass(nioWorkerClass);
		init();
	}
	public NioServer(int port,ArrayList<NioWorker> nioWorkerList){
		setPort(port);
		try{
		setNioWorkerList(nioWorkerList);
		}catch (Exception e) {
		}
		init();
	}
	
	public NioServer(int port){
		setPort(port);
		init();
	}
	public NioServer(){
		init();
	}
	private void init(){
	}

	
	public void start() throws Exception{
		super.start();
		log.debug(String.format("Server Start Port (%d)  SelectorPoolSize(%d)  getWorkerManagerSize(%d)",getPort(),getSelectorManagerSize(),getWorkerManagerSize()));
		acceptSelectThread = new NioAcceptManager(getSelectorManagerList(),getPort());
		acceptSelectThread.start();
	}
	public void stop(){
	}

	@Override
	public void setIp(String ip) throws Exception{
		throw new Exception("NioServer  sorry No Input plz");
	}
}
