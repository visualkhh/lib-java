package khh.communication.tcp.nio.selector;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import khh.collection.Queue;
import khh.communication.tcp.nio.NioCommunication;
import khh.communication.tcp.nio.server.NioServer;
import khh.debug.LogK;
import khh.debug.util.DebugUtil;

public class NioSelectorManager extends Thread
{
	//private ArrayList<SocketChannel>	clientSocket	= null;
	private Selector				selector	= null;
	private BlockingQueue<SelectionKey>	eventQueue	= null;
	private LogK log = LogK.getInstance();
	private NioCommunication nioCommunication;
	public NioSelectorManager(NioCommunication nioCommunication,BlockingQueue<SelectionKey> eventQueue) throws IOException{
		this.eventQueue = eventQueue;									//공유할 eventqueue
		//this.clientSocket = new ArrayList<SocketChannel>();				//새로들어온 Accep되어진 ! client SocketChannel  Socket!!
//		if(getClientSelector()==null){
//			setClientSelector(Selector.open());								//감지할 셀렉터
//		}
		this.nioCommunication = nioCommunication;
		log.debug(String.format("NioSelector(id:%d) Create...Thread", getId() ));
	}

	public void run(){
		log.debug(String.format("NioSelector(id:%d) Running...Thread Run", getId() ));
		while(!Thread.currentThread().isInterrupted()){
			try{
			   
			    if(getSelector()==null){
			        Thread.sleep(2000);
			        continue;
			    }
			    
				if(getSelector().select(3) > 0){
					Iterator<SelectionKey> it = getSelector().selectedKeys().iterator();
					while (it.hasNext()){
						if(getEventQueue().size()>=nioCommunication.getWorkerManagerSize()){
							Thread.sleep(1);
							break;
						}
						
						SelectionKey key = it.next();
						if(key.isReadable() || key.isWritable()){	//ReadState SelectionKey  공유queue 에 input
							key.interestOps(0);
							//log.debug(">>>>>>>>>"+key.isValid()+"  "+getEventQueue().size());
							if(key.isValid()){
								getEventQueue().put(key);
							}
						}
						it.remove();		//selectorkeys에 남은거지우기위해
					}
				}
			}catch (InterruptedException e) {
				log.error("InterruptedException  SelectorManager End ",e);
				return;
			}catch (Exception e){
			}
		}
	}



	public void addSocketChannel(SocketChannel socketChannel) throws InterruptedException, IOException{
		SocketChannel chnnel = socketChannel; 
		Selector selector = getSelector();
		if(selector==null){
		    selector = Selector.open();
		    setSelector(selector);
		}
		chnnel.configureBlocking(false);
		chnnel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		//clientSocket.add(socketChannel);
	}

	public BlockingQueue<SelectionKey> getEventQueue(){
		return eventQueue;
	}

	private void setSelector(Selector selector) {
		this.selector = selector;
	}

	public Selector getSelector() {
		return selector;
	}
	
}
