package khh.communication.tcp.nio.server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import khh.collection.RoundRobin;
import khh.communication.Communication_I;
import khh.communication.tcp.nio.server.accept.NioAcceptManager;
import khh.communication.tcp.nio.server.selector.NioSelector;
import khh.communication.tcp.nio.server.worker.NioWorker;
import khh.communication.tcp.nio.worker.NioWorkerBusiness;
import khh.debug.LogK;
import khh.reflection.ReflectionUtil;

public class NioServer implements Communication_I
{
	
	private int port = 9090;
	private int selectorPoolSize 	= 10;
	private int workerPoolSize 		= 10;
	private NioAcceptManager acceptSelectThread					= null;
	private Class nioWorkerBusiness 							= null;
	private BlockingQueue<SelectionKey> eventQueue 				= null;
	private ArrayList<NioWorker> workerPool    					= null;
	private RoundRobin<NioSelector> selectorPool 				= null;
	private LogK log = LogK.getInstance();
	
	public NioServer(int port,Class nioWorkerBusiness){
		setPort(port);
		setNioWorkerBusiness(nioWorkerBusiness);
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
		this.eventQueue 	= new LinkedBlockingQueue<SelectionKey>();
		this.workerPool 	= new ArrayList<NioWorker>();
		this.selectorPool 	= new RoundRobin<NioSelector>();
	}

	
	public RoundRobin<NioSelector> getSelectorPool() {
		return selectorPool;
	}
	public int getSelectorPoolSize() {
		return selectorPoolSize;
	}
	public void setSelectorPoolSize(int selectorPoolSize) {
		this.selectorPoolSize = selectorPoolSize;
	}
	public int getWorkerPoolSize() {
		return workerPoolSize;
	}
	public void setWorkerPoolSize(int workerPoolSize) {
		this.workerPoolSize = workerPoolSize;
	}
	public BlockingQueue<SelectionKey> getEventQueue() {
		return eventQueue;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getPort() {
		return port;
	}
	public Class getNioWorkerBusiness() {
		return nioWorkerBusiness;
	}
	public void setNioWorkerBusiness(Class nioWorkerBusiness) {
		this.nioWorkerBusiness = nioWorkerBusiness;
	}
	//start
	public void start() throws Exception{
		log.debug(String.format("Server Start Port (%d)  SelectorPoolSize(%d)  WorkerPoolSize(%d)",getPort(),getSelectorPoolSize(),getWorkerPoolSize()));
		selectorPoolSetting();
		workerPoolSetting();
		acceptSelectThread = new NioAcceptManager(getSelectorPool(),getPort());
		acceptSelectThread.start();
	}
	public void stop(){
	}

	private void selectorPoolSetting() throws IOException {
		for(int i = 0 ; i < getSelectorPoolSize() ; i++){
			NioSelector selector = new NioSelector(eventQueue);
			selector.start();
			getSelectorPool().add(selector);
		}
	}
	private void workerPoolSetting() throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		for(int i = 0 ; i < getWorkerPoolSize() ; i++){
			NioWorker worker = new NioWorker(eventQueue,(NioWorkerBusiness) ReflectionUtil.newClass(getNioWorkerBusiness()));
			worker.start();
			workerPool.add(worker);
		}		
	}

	public static void main(String[] args) throws IOException{
//		System.out.println("=============a");
//		new TcpServer(9090).start();
//		System.out.println("=============b");
	}
}
