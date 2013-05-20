package khh.communication.tcp.nio.worker;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

import khh.callstack.util.StackTraceUtil;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.date.util.DateUtil;
import khh.debug.LogK;

public abstract class NioActionWorker extends NioWorker {
	private LogK log = LogK.getInstance();
	private int readTimeout_ms =5000; 
	private boolean autoFeedbackException=false;
	public NioActionWorker(int firestMode) {
	   super(firestMode);
    }
	public NioActionWorker(){
		
	}
	
	
//	public NioMsgActionWorker(){
//		setFirestMode(MODE_FIREST_R);
//	}
	
	public boolean isAutoFeedbackException(){
		return autoFeedbackException;
	}
	public void setAutoFeedbackException(boolean autoFeedbackException){
		this.autoFeedbackException = autoFeedbackException;
	}
	public int getReadTimeout(){
		return readTimeout_ms;
	}
	public void setReadTimeout(int readTimeout_ms){
		this.readTimeout_ms = readTimeout_ms;
	}



	
//////////protocol!!
	   /*
  * STX(1byte), ACTIONCODE(int4byte), LENGTH(datalength int4byte), DATA(bytes..~..), ETX(1byte) 
  */
	public NioActionMsg receiveNioActionMsg() throws IOException {
	    return receiveNioActionMsg(getSocketChannel());
	}
	public NioActionMsg receiveNioActionMsg(SelectionKey selectionKey) throws IOException {
	    return receiveNioActionMsg((SocketChannel)selectionKey.channel());
	}
 public NioActionMsg receiveNioActionMsg(SocketChannel socket) throws IOException {
     NioActionMsg msg = null;
     long startms=System.currentTimeMillis();
     int timeoutms = getReadTimeout();
//     if(selectionKey.isReadable()){
	ByteBuffer buffer = ByteBuffer.allocateDirect(4);
	buffer.order(ByteOrder.BIG_ENDIAN);
	
	
	//stx check
	int cnt=0;
	int readLength = 0;
	while(cnt!=4){
		buffer.position(cnt);
		buffer.limit(cnt+1);
		timeoutms 		= timeoutms - (int)(System.currentTimeMillis()-startms);
		readLength 		= read(buffer, timeoutms,socket); //stx Read
		if(readLength == 1 && NioActionMsg.STX[cnt] == buffer.get(cnt)){
			cnt++;
		}else{
			cnt=0;
		}
	}
         
         buffer.clear();
         log.debug("SEX",buffer);
//         if(readLength==1 && NioActionMsg.STX == buffer.get(0)){
             msg = new NioActionMsg();
//             buffer = ByteBuffer.allocateDirect(4);
//             buffer.order(ByteOrder.BIG_ENDIAN);
             timeoutms = timeoutms - (int)(System.currentTimeMillis()-startms);
             readLength = read(buffer, timeoutms,socket);         //action Code Read
             buffer.clear();
             int actionCode = buffer.getInt();
             buffer.clear();
             log.debug("ACTION("+actionCode+")",buffer);
             msg.setAction(actionCode);
             
             
             
             
             
             buffer.clear();
             //buffer = ByteBuffer.allocateDirect(4);    //length Code Read
             //buffer.order(ByteOrder.BIG_ENDIAN);
             timeoutms = timeoutms - (int)(System.currentTimeMillis()-startms);
             readLength = read(buffer, timeoutms,socket);
             buffer.clear();
             int length = buffer.getInt();
             buffer.clear();
            log.debug("LENGTH("+length+")",buffer);
             
             
             
            //데이터있을시.ㅎㅎ
             if(length>0){
            	 ByteBuffer bufferBody = ByteBuffer.allocateDirect(length);//data Read
            	 timeoutms = timeoutms - (int)(System.currentTimeMillis()-startms);
	             readLength = read(bufferBody, timeoutms,socket);
	             bufferBody.clear();
	             msg.format(bufferBody);
//	             msg.set(buffer);
	             log.debug("DATA",bufferBody);
             }
             
             
             buffer.clear();
         	cnt=0;
        	readLength = 0;
        	while(cnt!=4){
        		buffer.position(cnt);
        		buffer.limit(cnt+1);
        		timeoutms 		= timeoutms - (int)(System.currentTimeMillis()-startms);
        		readLength 		= read(buffer, timeoutms,socket); //stx Read
        		if(readLength == 1 && NioActionMsg.ETX[cnt] == buffer.get(cnt)){
        			cnt++;
        		}else{
        			msg.setSuccess(false);
        			log.debug("ETX Fail Unknown Action Msg ETX:"+NioActionMsg.ETX);
        			throw new IOException("ETX Fail Unknown Action Msg");
        		}
        	}
            //buffer = ByteBuffer.allocateDirect(1);        //etx Read
//             timeoutms = timeoutms - (int)(System.currentTimeMillis()-startms);
//             readLength = read(buffer, timeoutms,socket);
             buffer.clear();
             log.debug("ETX",buffer);
             
             if(cnt==4){
                 msg.setSuccess(true);
             }else{
            	 msg.setSuccess(false);
             }
//         }
//     }
     return msg;
 }
 
 
 public void sendNioActionMsg(NioActionMsg msg) throws Exception {
     sendNioActionMsg(msg,getSocketChannel());
 }
 public void sendNioActionMsg(NioActionMsg msg, SelectionKey selectionKey) throws Exception {
     sendNioActionMsg(msg,(SocketChannel)selectionKey.channel());
 }
 public void sendNioActionMsg(NioActionMsg msg, SocketChannel socket) throws Exception {
     if(msg.isSuccess()){
         write(msg.unformat(),socket);
     }
 }
 
	
	@Override
	final public void execute(SelectionKey selectionKey) throws Exception{
		NioActionMsg msg = null;
		try{
			if(selectionKey.isReadable()){
				msg = receiveNioActionMsg(selectionKey);
				if(msg == null || (msg!=null && msg.isSuccess() == false)){
					log.info("NioMsg  [msg == null || msg.isSuccess() == false] (Before receiveNioMsg) "+msg);
					return ;
				}else{
					msg = onReceiveAction(msg,selectionKey);
				}
			}
			if(selectionKey.isWritable()){
				msg = onSendAction(msg, selectionKey);
				if(msg!=null)
				sendNioActionMsg(msg);
			}
		}catch (Exception e) {
			if(isAutoFeedbackException() && selectionKey!=null && selectionKey.isWritable()){
				if(msg==null){
					msg = new NioActionMsg();
				}
				msg.setAction(NioActionMsg.ACTION.EXCEPTION.getValue());
				
				log.info("NioActionWorker Exception: ",e);
				msg.clear();
				msg.set("exception:"+e+",message:"+e.getMessage()+",trace:"+StackTraceUtil.getStackTrace(e)+",date:"+DateUtil.getDate("yyyy/MM/dd HH:mm:ss/SSS"));
				msg.setSuccess(true);
				sendNioActionMsg(msg);
			}else{
			    throw e;
			}
		}
	}
	



	public abstract NioActionMsg onReceiveAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception;
	public abstract NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception;
	
	
	
	

}
