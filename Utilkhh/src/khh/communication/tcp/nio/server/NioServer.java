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
	private Class nioWorkerClass       							= null;
	private ArrayList<NioWorker> nioWorkerList					= null;
	private String name											= null;
	public NioServer(int port,Class nioWorkerClass){
		setPort(port);
		setNioWorkerClass(nioWorkerClass);
		init();
	}
	public NioServer(int port,ArrayList<NioWorker> nioWorkerList){
		setPort(port);
		setNioWorkerList(nioWorkerList);
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


    public Class getNioWorkerClass() {
        return nioWorkerClass;
    }
    public void setNioWorkerClass(Class nioWorkerClass) {
        this.nioWorkerClass = nioWorkerClass;
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
		if(getNioWorkerList()!=null && getNioWorkerList().size()>0){
			for(int i = 0 ; i < getNioWorkerList().size() ; i++){
				NioServerWorker worker = new NioServerWorker(eventQueue,getNioWorkerList().get(i));
				worker.start();
				workerPool.add(worker);
			}	
			setWorkerPoolSize(getNioWorkerList().size());
		}else{
			for(int i = 0 ; i < getWorkerPoolSize() ; i++){
				NioServerWorker worker = new NioServerWorker(eventQueue,(NioWorker) ReflectionUtil.newClass(getNioWorkerClass()));
				worker.start();
				workerPool.add(worker);
			}	
		}
	}
	public NioServerMonitor getMonitor() {
		return monitor;
	}
	
	private ArrayList<NioWorker> getNioWorkerList() {
		return nioWorkerList;
	}
	private void setNioWorkerList(ArrayList<NioWorker> nioWorker) {
		this.nioWorkerList = nioWorker;
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
