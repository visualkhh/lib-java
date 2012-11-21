package khh.communication.tcp.nio.server.selector;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import khh.collection.Queue;
import khh.communication.tcp.nio.server.NioServer;
import khh.debug.LogK;
import khh.debug.util.DebugUtil;

public class NioSelector extends Thread
{
	//private ArrayList<SocketChannel>	clientSocket	= null;
	private Selector				clientSelector	= null;
	private BlockingQueue<SelectionKey>	eventQueue	= null;
	private LogK log = LogK.getInstance();
	public NioSelector(BlockingQueue<SelectionKey> eventQueue) throws IOException{
		this.eventQueue = eventQueue;									//공유할 eventqueue
		//this.clientSocket = new ArrayList<SocketChannel>();				//새로들어온 Accep되어진 ! client SocketChannel  Socket!!
		clientSelector = Selector.open();								//감지할 셀렉터
		log.debug(String.format("NioSelector(id:%d) Create...Thread", getId() ));
	}

	public void run(){
		log.debug(String.format("NioSelector(id:%d) Running...Thread Run", getId() ));
		while(true){
			try{
				if(clientSelector.select(3) > 0){
					Iterator<SelectionKey> it = clientSelector.selectedKeys().iterator();
					while (it.hasNext()){
						SelectionKey key = it.next();
						if(key.isReadable() || key.isWritable()){	//ReadState SelectionKey  공유queue 에 input
							key.interestOps(0);
							getEventQueue().put(key);
						}
						it.remove();		//selectorkeys에 남은거지우기위해
					}
				}
			}catch (Exception e){
			}
		}
	}



	public void addSocketChannel(SocketChannel socketChannel) throws InterruptedException, IOException{
		SocketChannel chnnel = socketChannel;
		chnnel.configureBlocking(false);
		chnnel.register(clientSelector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		//clientSocket.add(socketChannel);
	}

	public BlockingQueue<SelectionKey> getEventQueue(){
		return eventQueue;
	}
	
}