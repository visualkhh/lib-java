package khh.communication.tcp.nio.aceept;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import khh.collection.RoundRobin;
import khh.communication.tcp.nio.selector.NioSelectorManager;
import khh.debug.LogK;


public class NioAcceptManager extends Thread
{

	private Selector					acceptSelector		= null;
	private ServerSocket				socket				= null;
	private ServerSocketChannel			socketChannel		= null;
	private RoundRobin<NioSelectorManager> 	selectorManagerList		= null;
	private int port 	= 9090;
	private LogK log 	= LogK.getInstance();
	public NioAcceptManager(RoundRobin<NioSelectorManager> selectorManagerList,int port) throws IOException{
		this.selectorManagerList = selectorManagerList;
		this.port = port;
		init();
	}

	private void init() throws IOException{
		acceptSelector 				= Selector.open();
		socketChannel 				= ServerSocketChannel.open();
		socketChannel.configureBlocking(false);
		socket 						= socketChannel.socket();
		InetSocketAddress isa 		= new InetSocketAddress(getPort());
		socket.bind(isa);
		socketChannel.register(acceptSelector, SelectionKey.OP_ACCEPT);
		log.debug(String.format("NioAcceptManager(id:%d) Create...Thread", getId() ));
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	public void run(){
		log.debug(String.format("NioAcceptManager(id:%d) Running...Thread Run", getId()));
		try{
			while(!Thread.currentThread().isInterrupted()){
				acceptSelector.select(0);
				Iterator<SelectionKey> it = acceptSelector.selectedKeys().iterator();
				while(it.hasNext()){
					SelectionKey key = (SelectionKey) it.next();
					ServerSocketChannel serverSocket = (ServerSocketChannel) key.channel();
					SocketChannel clientSocket = serverSocket.accept();
					log.debug(String.format("NioAcceptManager Connected From %s  user port %d ", clientSocket.socket().getRemoteSocketAddress().toString(),clientSocket.socket().getPort()));
					synchronized (selectorManagerList) {//이거해줘야함 roundrobin 동기화안되는객체임
					    NioSelectorManager s = selectorManagerList.getNext();
					    s.addSocketChannel(clientSocket);
//                        clientSocket.configureBlocking(false);
//					    clientSocket.register(Selector.open(), SelectionKey.OP_READ | SelectionKey.OP_WRITE);
					}
					it.remove();
				}
			}
		}catch (InterruptedException e) {
			log.error("InterruptedException  NioAccepManager End ",e);
			return;
		}catch (Exception e){
			log.error("Accept Error",e);
		}
	}
	
}

