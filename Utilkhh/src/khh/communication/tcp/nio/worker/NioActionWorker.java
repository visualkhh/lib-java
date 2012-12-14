package khh.communication.tcp.nio.worker;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import khh.callstack.util.StackTraceUtil;
import khh.communication.tcp.nio.worker.msg.FromToFormater;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.date.util.DateUtil;
import khh.debug.LogK;
import khh.util.ByteUtil;

public abstract class NioActionWorker extends NioWorker {
	private LogK log = LogK.getInstance();
	private int readTimeout_ms =5000; 
	private boolean autoFeedbackException=true;
	public NioActionWorker(int firestMode) {
	   super(firestMode);
    }
	public NioActionWorker(){
		
	}
	
	
//	public NioMsgActionWorker(){
//		setFirestMode(MODE_FIREST_R);
//	}
	
	private boolean isAutoFeedbackException(){
		return autoFeedbackException;
	}
	private void setAutoFeedbackException(boolean autoFeedbackException){
		this.autoFeedbackException = autoFeedbackException;
	}
	private int getReadTimeout(){
		return readTimeout_ms;
	}
	private void setReadTimeout(int readTimeout_ms){
		this.readTimeout_ms = readTimeout_ms;
	}


	//int        부호 있는 정수    32 bits      -2147483648 ~ 2147483647
	//기본액션은 			1000001 이상
	//서버액션은 			2000001 이상
	//EXCEPTION액션은 	3000001 이상
	//사용자정의액션은 		50000001 이상
	//FromTo액션은 		100000001 이상
	public static enum ACTION {
        POLL(1000001),
        ECHO(1000002),
        
        GET_SERVERTIME(2000001),//서버시간 yyyy/MM/dd HH:mm:ss/SSS
        GET_SERVERS(2000003),//서버정보요청 열려있는 서버이름s!   
        GET_CLIENTS(2000004),//서버에 붙어있는 클라이언트이름s!   
        
        NOSUCH_EXCEPTION(3000001),
        NOSUCH_FROMTARGET_EXCEPTION(3000002),
        NOSUCH_TOTARGET_EXCEPTION(3000003),
        EXCEPTION(3000005),
        
        FROMTO(100000001), //여기위에서부터는 다 FROMTO임..
        //LOGIN_LOGOUT(50002),
        //ADMIN_CLIENT_JOIN(50004)
        ;
        int value;
        ACTION(int id) {
            this.value = id;
        }
        public int getValue() {
            return this.value;
        }
    };
	
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
         ByteBuffer buffer = ByteBuffer.allocateDirect(1);
         int i = read(buffer, timeoutms,socket); //stx Read
         buffer.clear();
         
         timeoutms = timeoutms - (int)(System.currentTimeMillis()-startms);
         
         log.debug("SEX",buffer);
         if(i==1 && NioActionMsg.STX == buffer.get(0)){
             msg = new NioActionMsg();
            // msg.setStx(buffer.get(0));
             
             buffer = ByteBuffer.allocateDirect(4);
             buffer.order(ByteOrder.BIG_ENDIAN);
             i = read(buffer, timeoutms,socket);         //action Code Read
             buffer.clear();
             int actionCode = buffer.getInt();
             buffer.clear();
             log.debug("ACTION("+actionCode+")",buffer);
             msg.setAction(actionCode);
             
             
             
             timeoutms = timeoutms - (int)(System.currentTimeMillis()-startms);
             
             
             
             buffer = ByteBuffer.allocateDirect(4);    //length Code Read
             buffer.order(ByteOrder.BIG_ENDIAN);
             i = read(buffer, timeoutms,socket);
             buffer.clear();
             int length = buffer.getInt();
             buffer.clear();
            log.debug("LENGTH("+length+")",buffer);
//           msg.setAction(length);
             
             
             timeoutms = timeoutms - (int)(System.currentTimeMillis()-startms);
             
             if(length>0){
	             buffer = ByteBuffer.allocateDirect(length);//data Read
	             i = read(buffer, timeoutms,socket);
	             buffer.clear();
	             msg.set(buffer);
	             log.debug("DATA",buffer);
             }
             
             
             timeoutms = timeoutms - (int)(System.currentTimeMillis()-startms);
             
             buffer = ByteBuffer.allocateDirect(1);        //etx Read
             i = read(buffer, timeoutms,socket);
             buffer.clear();
//             msg.setEtx(buffer.get(0));
             log.debug("ETX",buffer);
             
             if(i==1 && NioActionMsg.ETX ==buffer.get(0)){
             //    log.debug("Good Message ActionCode :"+ msg.getAction());
                 msg.setSuccess(true);
                 //msg.setSelectionKey(selectionKey);
             }
         }
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
					onReceiveAction(msg,selectionKey);
				}
			}
			if(selectionKey.isWritable()){
				onSendAction(msg, selectionKey);
			}
		}catch (Exception e) {
			if(isAutoFeedbackException() && selectionKey!=null && selectionKey.isWritable()){
				if(msg==null){
					msg = new NioActionMsg(ACTION.EXCEPTION.getValue());
				}
				msg.set("Exception e:"+e+","+e.getMessage()+","+StackTraceUtil.getStackTrace(e)+","+DateUtil.getDate("yyyy/MM/dd HH:mm:ss/SSS"));
				sendNioActionMsg(msg);
			}
		}
	}
	
	



	public abstract NioActionMsg onReceiveAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception;
	public abstract NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception;
	
	
	
	

}
