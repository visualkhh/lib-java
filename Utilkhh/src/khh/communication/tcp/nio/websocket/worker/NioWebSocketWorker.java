package khh.communication.tcp.nio.websocket.worker;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import khh.communication.tcp.nio.websocket.msg.NioWebSocketMsg;
import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.debug.LogK;

public abstract class NioWebSocketWorker extends NioActionWorker {
	LogK log = LogK.getInstance();
	@Override
	public NioWebSocketMsg receiveNioActionMsg() throws IOException {
	    return receiveNioActionMsg(getSocketChannel());
	}
	@Override
	public NioWebSocketMsg receiveNioActionMsg(SelectionKey selectionKey) throws IOException {
	    return receiveNioActionMsg((SocketChannel)selectionKey.channel());
	}
	@Override
	public NioWebSocketMsg receiveNioActionMsg(SocketChannel socket) throws IOException {
		
		     long startms=System.currentTimeMillis();
		     int timeoutms = getReadTimeout();
////		     if(selectionKey.isReadable()){
			ByteBuffer buffer = ByteBuffer.allocateDirect(5);
			buffer.order(ByteOrder.BIG_ENDIAN);
			
			//stx check
			int cnt=0;
			int readLength = 0;
			while(cnt!=NioWebSocketMsg.STX.length){
				buffer.position(cnt);
				buffer.limit(cnt+1);
				timeoutms 		= timeoutms - (int)(System.currentTimeMillis()-startms);
				readLength 		= read(buffer, timeoutms,socket); //stx Read
				if(readLength==1){
					//log.debug("Read"+String.format("%02X",buffer.get(cnt)) );
				}
				if(readLength == 1 && NioWebSocketMsg.STX[cnt] == buffer.get(cnt)){
					cnt++;
				}else{
					cnt=0;
				}
			}
		         
         	 buffer.clear();
         	 log.debug("SEX",buffer);
		    

            
            //DATA SETTING
 			readLength = 0;
 			cnt=0;
         	buffer = ByteBuffer.allocateDirect(100000000);//10mb
 			while(true){
 				buffer.position(cnt);
 				buffer.limit(cnt+1);
 				
 				timeoutms 		= timeoutms - (int)(System.currentTimeMillis()-startms);
 				readLength 		= read(buffer, timeoutms,socket); //stx Read
 				
 				if(readLength == 1 ){
 					cnt++;
 				}
 				if( 
 						buffer.limit() >=4  && 
 						buffer.get( buffer.limit()-4 )== NioWebSocketMsg.ETX[0] && 
 						buffer.get( buffer.limit()-3 )== NioWebSocketMsg.ETX[1] && 
 						buffer.get( buffer.limit()-2 )== NioWebSocketMsg.ETX[2] && 
 						buffer.get( buffer.limit()-1 )== NioWebSocketMsg.ETX[3]  
					){
 					buffer.limit(buffer.limit()-4);
 		 			buffer.flip();
 					break;
 				}
 			}
 			
 			log.debug("Data param",buffer);
 			NioWebSocketMsg msg = new NioWebSocketMsg();
 			msg.format(buffer);
 			msg.setSuccess(true);
		return msg;
	}
	
	@Override
	public NioWebSocketMsg makeFeedBackExceptionActionMsg(NioActionMsg msg,Exception e) {
		return (NioWebSocketMsg) msg;
	}
	
}
