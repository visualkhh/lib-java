package khh.communication.tcp.nio.connect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import khh.collection.RoundRobin;
import khh.communication.tcp.nio.selector.NioSelectorManager;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.debug.LogK;

public class NioConnectManager extends Thread{
	private LogK log 					= LogK.getInstance();
	private RoundRobin<NioSelectorManager> selectorManagerList;
	private int port;
	private String ip;
	private int connectionTimeout;
	private SocketChannel socketChannel	= null;
	private Selector connectSelector		= null;
	public NioConnectManager (RoundRobin<NioSelectorManager> selectorManagerList,String ip,int port) throws IOException{
		this.selectorManagerList = selectorManagerList;
		this.ip = ip;
		this.port = port;
		init();
	}
	public NioConnectManager (RoundRobin<NioSelectorManager> selectorManagerList,String ip,int port,int connectionTimeout) throws IOException{
		this.selectorManagerList = selectorManagerList;
		this.ip = ip;
		this.port = port;
		this.connectionTimeout = connectionTimeout;
		init();
	}
	
	private void init() throws IOException{
		connectSelector 	= Selector.open();	
		InetSocketAddress	addr = new InetSocketAddress(getIp(), getPort());
		SocketChannel socketChannel=null;
		if(getConnectionTimeout()>0){
			socketChannel = SocketChannel.open();
			socketChannel.socket().connect(addr,getConnectionTimeout());
		}else{
			socketChannel = SocketChannel.open(addr);
		}
		socketChannel.configureBlocking(false);
		log.debug(String.format("NioConnectManager  %s   Port:%d   ConnectiomTimeOut:%d",getIp(), getPort(),getConnectionTimeout()));
		setSocketChannel(socketChannel);
		socketChannel.register(connectSelector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		//return socketChannel;
	}
	
	public void connnection() throws IOException{
		if(getSocketChannel().isConnected()){
		}else{
			init();
		}
	}
	@Override
	public void run(){
		while(getSocketChannel().isConnected()){
			try{
				if(connectSelector.select(3) > 0){
					Iterator<SelectionKey> it = connectSelector.selectedKeys().iterator();
					while(it.hasNext()){
						SelectionKey key = (SelectionKey)it.next();
						SocketChannel connectSocket = (SocketChannel)key.channel();
						synchronized(selectorManagerList){// 이거해줘야함 roundrobin 동기화안되는객체임
							NioSelectorManager s = selectorManagerList.getNext();
							s.addSocketChannel(connectSocket);
						}
						it.remove(); // selectorkeys에 남은거지우기위해
					}
				}else{
					Thread.sleep(2000);
				}
			}catch(Exception e){
				log.error("Connect Error", e);
			}
		}
	}
	
	public RoundRobin<NioSelectorManager> getSelectorManagerList(){
		return selectorManagerList;
	}
	public void setSelectorManagerList(RoundRobin<NioSelectorManager> selectorManagerList){
		this.selectorManagerList = selectorManagerList;
	}
	public int getPort(){
		return port;
	}
	public void setPort(int port){
		this.port = port;
	}
	public String getIp(){
		return ip;
	}
	public void setIp(String ip){
		this.ip = ip;
	}
	public int getConnectionTimeout(){
		return connectionTimeout;
	}
	public void setConnectionTimeout(int connectionTimeout){
		this.connectionTimeout = connectionTimeout;
	}
	
	public SocketChannel getSocketChannel(){
		return socketChannel;
	}
	private void setSocketChannel(SocketChannel socketChannel){
		this.socketChannel = socketChannel;
	}
//	protected Selector getConnectSelector(){
//		return connectSelector;
//	}
//	protected void setConnectSelector(Selector connectSelector){
//		this.connectSelector = connectSelector;
//	}
	  public boolean isConnected(){
			if ( getSocketChannel() == null )
				return false;
			return getSocketChannel().isConnected() && getSocketChannel().socket().isConnected()  ;//&& socket.isOpen()&&socket.finishConnect();
		}
		public void close(){
			try{
				if(getSocketChannel()!=null)
					getSocketChannel().close();
			}catch (IOException e1){
			}
		}
}
