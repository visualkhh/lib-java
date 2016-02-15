package khh.communication.tcp.nio;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import khh.collection.Queue;
import khh.collection.RoundRobin;
import khh.communication.Communication_I;
import khh.communication.tcp.nio.monitor.NioMonitor;
import khh.communication.tcp.nio.monitor.NioMultiMonitor;
import khh.communication.tcp.nio.selector.NioSelectorManager;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.communication.tcp.nio.worker.NioWorkerManager;
import khh.filter.Filter;
import khh.listener.action.ActionListener;
import khh.reflection.ReflectionUtil;
import khh.std.Action;

public abstract class NioCommunication implements Communication_I{
	private int port 					= 9090;
	private String ip					= "127.0.0.1";
	private String name					= null;
	private Queue<HashMap<String, Object>> telegramQueue	= new Queue<HashMap<String, Object>>();
	private NioMonitor monitor			= new NioMonitor();
	private NioMultiMonitor multimonitor= null;
	private Class nioWorkerClass       							= null;
	private ArrayList<NioWorker> nioWorkerList					= null;
	private BlockingQueue<SelectionKey> eventQueue 				= new LinkedBlockingQueue<SelectionKey>();
	private ArrayList<NioWorkerManager> workerManagerList		= new ArrayList<NioWorkerManager>();
	private RoundRobin<NioSelectorManager> selectorManagerList 	= new RoundRobin<NioSelectorManager>();
	private int selectorManagerSize = 10;
	private int workerManagerSize	= 10;
	private ArrayList<ActionListener> actionEventListenerList = new ArrayList<ActionListener>();
	
	
	public int getPort(){
		return port;
	}
	public void setPort(int port){
		this.port = port;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public void pushTelegram(HashMap<String, Object> telegram){
		getTelegramQueue().push(telegram);
	}
	public Queue<HashMap<String, Object>> getTelegramQueue(){
		return telegramQueue;
	} 
//	public void setTelegramQueue(Queue<Object> telegramQueue){
//		this.telegramQueue = telegramQueue;
//	}
	public NioMultiMonitor getMultimonitor(){
		return multimonitor;
	}
	public void setMultimonitor(NioMultiMonitor multimonitor){
		this.multimonitor = multimonitor;
	}
	public NioMonitor getMonitor(){
		return monitor;
	}
	public String getIp(){
		return ip;
	}
	public void setIp(String ip) throws Exception{
		this.ip = ip;
	}
	
	public Class getNioWorkerClass() {
        return nioWorkerClass;
    }
	public void setNioWorkerClass(Class nioWorkerClass) {
        this.nioWorkerClass = nioWorkerClass;
    }
    public ArrayList<NioWorker> getNioWorkerList() {
		return nioWorkerList;
	}
	public void setNioWorkerList(ArrayList<NioWorker> nioWorker) throws Exception {
		this.nioWorkerList = nioWorker;
	}
	
	public BlockingQueue<SelectionKey> getEventQueue() {
		return eventQueue;
	}
	public ArrayList<NioWorkerManager> getWorkerManagerList(){
		return workerManagerList;
	}
	private void setWorkerManagerList(ArrayList<NioWorkerManager> workerManagerList){
		this.workerManagerList = workerManagerList;
	}
	public int getWorkerManagerSize(){
		return workerManagerSize;
	}
	public void setWorkerManagerSize(int workerManagerSize)throws Exception{
		this.workerManagerSize = workerManagerSize;
	}
	public RoundRobin<NioSelectorManager> getSelectorManagerList(){
		return selectorManagerList;
	}
	private void setSelectorManagerList(RoundRobin<NioSelectorManager> selectorManagerList){
		this.selectorManagerList = selectorManagerList;
	}
	public int getSelectorManagerSize(){
		return selectorManagerSize;
	}
	public void setSelectorManagerSize(int selectorManagerSize) throws Exception{
		this.selectorManagerSize = selectorManagerSize;
	}
	
	@Override
	public void start() throws Exception{
		selectorManagerSetting();
		workerManagerSetting();
		monitorSetting();
	}
	

	private void selectorManagerSetting() throws IOException {
		for(int i = 0 ; i < getSelectorManagerSize() ; i++){
			NioSelectorManager selector = new NioSelectorManager(this,getEventQueue());
			selector.start();
			getSelectorManagerList().add(selector);
		}
	}
	private void workerManagerSetting() throws Exception {
		if(getNioWorkerList()!=null && getNioWorkerList().size()>0){
			for(int i = 0 ; i < getNioWorkerList().size() ; i++){
				NioWorker worker = getNioWorkerList().get(i);
				worker.setNioCommunication(this);
				NioWorkerManager serverWorker = new NioWorkerManager(getEventQueue(),worker);
				serverWorker.setNioCommunication(this);
				serverWorker.start();
				getWorkerManagerList().add(serverWorker);
			}	
			setWorkerManagerSize(getNioWorkerList().size());
		}else{
			for(int i = 0 ; i < getWorkerManagerSize() ; i++){
				NioWorker worker = (NioWorker) ReflectionUtil.newClass(getNioWorkerClass());
				worker.setNioCommunication(this);
				NioWorkerManager serverWorker = new NioWorkerManager(getEventQueue(),worker);
				serverWorker.setNioCommunication(this);
				serverWorker.start();
				getWorkerManagerList().add(serverWorker);
			}	
		}
	}
	
	private void monitorSetting(){
		getMonitor().setSelectorManagerList(getSelectorManagerList());
//		addSelectorM(getSelectorManagerList().get(i).getSelector());
	}
	
	public void addActionEventListener(ActionListener listener){
		actionEventListenerList.add(listener);
	}
	public void sendActionEventToListener(Action event){
		for(int i = 0; i < actionEventListenerList.size(); i++){
			actionEventListenerList.get(i).actionPerformed(event);
		}
	}
	public void sendActionEventToListener(Filter<ActionListener> filter,Action event){
		for(int i = 0; i < actionEventListenerList.size(); i++){
			ActionListener listener = actionEventListenerList.get(i);
			if(filter.test(listener)){
				actionEventListenerList.get(i).actionPerformed(event);
			}
		}
	}
	
	
	@Override
	public String toString(){
		return getIp()+":"+getPort()+" name:"+getName()+" SelectorMngSize"+getSelectorManagerSize()+" WorkerMngSize"+getWorkerManagerSize();
	}
	
}
