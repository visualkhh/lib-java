package khh.communication.tcp.nio.server.worker;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;

import khh.communication.Communication_Interface;
import khh.communication.tcp.nio.server.NioServer;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.debug.LogK;
import khh.debug.util.DebugUtil;

public class NioServerWorker extends Thread
{
	private BlockingQueue<SelectionKey>	eventQueue	= null;
	private NioWorker			business	= null;
	private LogK log = LogK.getInstance();
	public NioServerWorker(BlockingQueue<SelectionKey>eventQueue,NioWorker business){
		this.eventQueue = eventQueue;
		this.business = business;
		log.debug(String.format("NioWorker(id:%d) Create...Thread(%s)", getId(),business.getClass().getName()));
	}

	public void run(){
		log.debug(String.format("NioWorker(id:%d) Running...Thread Run", getId()));
		SelectionKey key 		= null;
		SocketChannel channel 	= null;
		Socket  socket = null;
		while(true){
			try{
				Thread.sleep(1);
				key = getEventQueue().take();	//BlockingQueue
				if((business.getFirestMode()==NioWorker.MODE_FIREST_R) && (key.isReadable()==false)){
					continue;//읽기부터인데 읽기가 활성화안되면 넘겨
				}else if((business.getFirestMode()==NioWorker.MODE_FIREST_W) && (key.isWritable()==false)){
					continue;//쓰기인데 쓰기가 활성화안되어있으면 넘겨
				}else if((business.getFirestMode()==NioWorker.MODE_FIREST_RW) &&(key.isReadable()==false || key.isWritable()==false)){
					continue;//일기쓰기 인데 둘다 안되어있으면 넘겨
				}
				//if(key.isReadable()&&key.isWritable())
				
				channel = (SocketChannel) key.channel();
				if (channel == null){
					finish(key);
					continue;
				}
				
				log.info(String.format("Worker! Start [%03d]", getId()));
				socket = channel.socket();
				business.setSocketChannel(channel);
				business.execute(key);
				log.info(String.format("Worker! END [%03d] isConnected:%b isConnectionPending:%b isOpen:%b  isClosed:%b isBound:%b isWriteble:%b"+channel.socket().getInetAddress(), getId(), channel.socket().isConnected(),channel.isConnectionPending(), channel.isOpen(),channel.socket().isClosed(),channel.socket().isBound(),key.isWritable()));
			}catch (SocketTimeoutException e) {
				log.error(String.format("SocketTimeoutException execute WorkerBusiness End [%03d] isConnected:%b isConnectionPending:%b isOpen:%b isClosed:%b isBound:%b isWriteble:%b "+channel.socket().getInetAddress(), getId(), channel.socket().isConnected(),channel.isConnectionPending(), channel.isOpen(),channel.socket().isClosed(),channel.socket().isBound(),key.isWritable()),e);
				close(channel);
			}catch (IOException e) {
				log.error(String.format("IOException  execute WorkerBusiness End [%03d] "+channel.socket().getInetAddress(), getId(), getId()),e);
				close(channel);
			}catch (Exception e){
				log.error(String.format("Exception  Close Socket!!  execute WorkerBusiness End [%03d] "+channel.socket().getInetAddress(), getId(), getId()),e);
				close(channel);
			}finally{
//				if( channel.isConnected() && channel.socket().isConnected()  && channel.isOpen() && !channel.socket().isClosed() && !channel.socket().isClosed()){
					//커넥트된상태
//				}else{
//				}
				if(key != null)
					finish(key);
			}
		}
	}
	

	private void finish(SelectionKey key){
		if (key.isValid()) {
			key.interestOps( SelectionKey.OP_READ | SelectionKey.OP_WRITE );
		}
	}

	private void close(SocketChannel socket){
		try{
			if(socket!=null)
			socket.close();
		}catch (IOException e1){
		}
	}

	public BlockingQueue<SelectionKey> getEventQueue() {
		return eventQueue;
	}
	

}
