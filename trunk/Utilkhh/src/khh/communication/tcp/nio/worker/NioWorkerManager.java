package khh.communication.tcp.nio.worker;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import khh.communication.tcp.nio.NioCommunication;
import khh.debug.LogK;
import khh.filter.Filter;
import khh.listener.action.ActionListener;
import khh.std.Action;

public class NioWorkerManager extends Thread
{
	private BlockingQueue<SelectionKey>	eventQueue	= null;
	private NioWorker business = null;
	private NioCommunication niocommunication = null;
	private LogK log = LogK.getInstance();
	public NioWorkerManager(BlockingQueue<SelectionKey>eventQueue,NioWorker business){
		this.eventQueue = eventQueue;
		this.business = business;
		log.debug(String.format("NioWorker(id:%d) Create...Thread(%s)", getId(),business.getClass().getName()));
	}

	public void run(){
		log.debug(String.format("NioWorker(id:%d) Running...Thread Run", getId()));
		SelectionKey key = null;
		SocketChannel channel = null;
		Socket socket = null;
		while(!Thread.currentThread().isInterrupted()){
			try{
				Thread.sleep(1);
				key = getEventQueue().take();	//BlockingQueue
				channel = (SocketChannel)key.channel();
				if(channel == null){
					finish(key);
					continue;
				}
				socket = channel.socket();
				business.setSocketChannel(channel);
				//긴급수신 메시지가있음 그쪽으로 telegram-_-
				if(niocommunication.getTelegramQueue()!=null && niocommunication.getTelegramQueue().size()>0){
					try{
					HashMap<String,Object> telegram = niocommunication.getTelegramQueue().pop();
						if(telegram!=null){
						business.receiveTelegram(telegram,key);
						}
					}catch (Exception e) {
					}
				}
				
				
				
				if((business.getFirestMode()==NioWorker.MODE_DISABLE)){ 
					continue;//완전안함
				}
				//else if(business.getFirestMode()==NioWorker.MODE_ONLY_TELEGRAM && niocommunication.getTelegramQueue()==null && niocommunication.getTelegramQueue().size()<=0){
				//	continue;//메시지전용인데 없으면 잿겨야지.
				//}
				else if((business.getFirestMode()==NioWorker.MODE_FIREST_R) && (key.isReadable()==false)){
					continue;//읽기부터인데 읽기가 활성화안되면 넘겨
				}else if((business.getFirestMode()==NioWorker.MODE_FIREST_W) && (key.isWritable()==false)){
					continue;//쓰기인데 쓰기가 활성화안되어있으면 넘겨
				}else if((business.getFirestMode()==NioWorker.MODE_FIREST_RW) && (key.isReadable()==false || key.isWritable()==false)){
					continue;//일기쓰기 인데 둘다 안되어있으면 넘겨
				}//if(key.isReadable()&&key.isWritable())
				business.execute(key);
				//log.info(String.format("Worker! END [%03d] isConnected:%b isConnectionPending:%b isOpen:%b  isClosed:%b isBound:%b isWriteble:%b"+channel.socket().getInetAddress(), getId(), channel.socket().isConnected(),channel.isConnectionPending(), channel.isOpen(),channel.socket().isClosed(),channel.socket().isBound(),key.isWritable()));
			}catch (InterruptedException e) {
				log.error("InterruptedException  WorkerBusiness End ",e);
				return;
			}catch (SocketTimeoutException e) {
				log.error("SocketTimeoutException execute WorkerBusiness End ",e);
				//log.error(String.format("SocketTimeoutException execute WorkerBusiness End [%03d] isConnected:%b isConnectionPending:%b isOpen:%b isClosed:%b isBound:%b isWriteble:%b "+channel.socket().getInetAddress(), getId(), channel.socket().isConnected(),channel.isConnectionPending(), channel.isOpen(),channel.socket().isClosed(),channel.socket().isBound(),key.isWritable()),e);
				business.close(channel);
//				close(channel);
			}catch (IOException e) {
				log.error("IOException execute WorkerBusiness End ",e);
				//log.error(String.format("IOException  execute WorkerBusiness End [%03d] "+channel.socket().getInetAddress(), getId(), getId()),e);
				business.close(channel);
//				close(channel);
			}catch (Exception e){
				log.error("Exception execute WorkerBusiness End ",e);
				//log.error(String.format("Exception  Close Socket!!  execute WorkerBusiness End [%03d] "+channel.socket().getInetAddress(), getId(), getId()),e);
				business.close(channel);
//				close(channel);
			}finally{
//				if( channel.isConnected() && channel.socket().isConnected()  && channel.isOpen() && !channel.socket().isClosed() && !channel.socket().isClosed()){
					//커넥트된상태
//				}else{
//				}
				if(key != null)
					finish(key);
				
				if(Thread.currentThread().isInterrupted()){
					return;
				}
			}
		}
	}
	

	private void finish(SelectionKey key){
		if (key.isValid()) {
			key.interestOps( SelectionKey.OP_READ | SelectionKey.OP_WRITE );
		}
	}

//	private void close(SocketChannel socket){
//		try{
//			if(socket!=null)
//			socket.close();
//		}catch (IOException e1){
//		}
//	}

	public BlockingQueue<SelectionKey> getEventQueue() {
		return eventQueue;
	}

	public NioCommunication getNioCommunication(){
		return niocommunication;
	}

	public void setNioCommunication(NioCommunication niocommunication){
		this.niocommunication = niocommunication;
	}
	

}
