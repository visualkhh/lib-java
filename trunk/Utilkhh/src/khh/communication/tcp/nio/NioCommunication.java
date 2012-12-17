package khh.communication.tcp.nio;

import khh.collection.Queue;
import khh.communication.Communication_I;
import khh.communication.tcp.nio.monitor.NioMonitor;
import khh.communication.tcp.nio.monitor.NioMultiMonitor;

abstract class NioCommunication implements Communication_I{
	private int port 					= 9090;
	private String name					= null;
	private Queue<Object> telegramQueue	= null;
	private NioMonitor monitor			= null;
	private NioMultiMonitor multimonitor= null;
	private int getPort(){
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
	public Queue<Object> getTelegramQueue(){
		return telegramQueue;
	}
	public void setTelegramQueue(Queue<Object> telegramQueue){
		this.telegramQueue = telegramQueue;
	}
	public NioMultiMonitor getMultimonitor(){
		return multimonitor;
	}
	public void setMultimonitor(NioMultiMonitor multimonitor){
		this.multimonitor = multimonitor;
	}
	public NioMonitor getMonitor(){
		return monitor;
	}
	
	
	
}
