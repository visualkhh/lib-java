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
import khh.communication.tcp.nio.server.accept.NioServerAcceptManager;
import khh.communication.tcp.nio.server.monitor.NioServerMonitor;
import khh.communication.tcp.nio.server.selector.NioServerSelector;
import khh.communication.tcp.nio.server.worker.NioServerWorker;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.debug.LogK;
import khh.reflection.ReflectionUtil;

public class NioServer implements Communication_I
{
	
	private int port = 9090;
	private int selectorPoolSize 	= 10;
	private int workerPoolSize 		= 10;
	private NioServerAcceptManager acceptSelectThread			= null;
	private NioServerMonitor monitor							= null;
	private BlockingQueue<SelectionKey> eventQueue 				= null;
	private ArrayList<NioServerWorker> workerPool    			= null;
	private RoundRobin<NioServerSelector> selectorPool 			= null;
	private LogK log = LogK.getInstance();
	private Class nioWorkerBusiness 							= null;
	private ArrayList<NioWorker> nioWorker						= null;
	private String name											= null;
	public NioServer(int port,Class nioWorkerBusiness){
		setPort(port);
		setNioWorkerBusiness(nioWorkerBusiness);
		init();
	}
	public NioServer(int port,ArrayList<NioWorker> nioWorker){
		setPort(port);
		setNioWorker(nioWorker);
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
		this.workerPool 	= new ArrayList<NioServerWorker>();
		this.selectorPool 	= new RoundRobin<NioServerSelector>();
	}

	
	public RoundRobin<NioServerSelector> getSelectorPool() {
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
		acceptSelectThread = new NioServerAcceptManager(getSelectorPool(),getPort());
		acceptSelectThread.start();
		monitor = new NioServerMonitor(getSelectorPool());
		//monitor.start();
	}
	public void stop(){
	}

	private void selectorPoolSetting() throws IOException {
		for(int i = 0 ; i < getSelectorPoolSize() ; i++){
			NioServerSelector selector = new NioServerSelector(eventQueue);
			selector.start();
			getSelectorPool().add(selector);
		}
	}
	private void workerPoolSetting() throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if(getNioWorker()!=null && getNioWorker().size()>0){
			for(int i = 0 ; i < getNioWorker().size() ; i++){
				NioServerWorker worker = new NioServerWorker(eventQueue,getNioWorker().get(i));
				worker.start();
				workerPool.add(worker);
			}	
			setWorkerPoolSize(getNioWorker().size());
		}else{
			for(int i = 0 ; i < getWorkerPoolSize() ; i++){
				NioServerWorker worker = new NioServerWorker(eventQueue,(NioWorker) ReflectionUtil.newClass(getNioWorkerBusiness()));
				worker.start();
				workerPool.add(worker);
			}	
		}
	}
	public NioServerMonitor getMonitor() {
		return monitor;
	}
	
	private ArrayList<NioWorker> getNioWorker() {
		return nioWorker;
	}
	private void setNioWorker(ArrayList<NioWorker> nioWorker) {
		this.nioWorker = nioWorker;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public static void main(String[] args) throws IOException{
//		System.out.println("=============a");
//		new TcpServer(9090).start();
//		System.out.println("=============b");
	}
}
