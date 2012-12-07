package khh.communication.tcp.nio.relay.server.worker;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import khh.communication.tcp.nio.relay.msg.RelayMsg;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.debug.LogK;

public abstract class RelayWorkerBase extends NioWorker {

    public static enum ACTION {
        LOGIN_LOGIN(50001),
        LOGIN_OK(50003),
        LOGIN_FAIL(50004),
        //LOGIN_LOGOUT(50002),
        //ADMIN_CLIENT_JOIN(50004)
        ;
        int code;
        ACTION(int id) {
            this.code = id;
        }
        public int getValue() {
            return this.code;
        }
    };
    
    private LogK log = LogK.getInstance();
    
	/*
	 * STX(1byte), ACTIONCODE(int4byte), LENGTH(datalength int4byte), DATA(bytes..~..), ETX(1byte) 
	 */
	public RelayMsg receiveMsg(SelectionKey selectionKey) throws IOException {
		RelayMsg msg = null;
		if(selectionKey.isReadable()){
			ByteBuffer buffer = ByteBuffer.allocate(1);
			int i = read(buffer, 1000);	//stx Read
			buffer.clear();
			log.debug("SEX",buffer);
			
			if(i==1 && RelayMsg.STX == buffer.get(0)){
				msg = new RelayMsg();
				//msg.setStx(buffer.get(0));
				
				buffer = ByteBuffer.allocate(4);
				buffer.order(ByteOrder.BIG_ENDIAN);
				i = read(buffer, 1000);			//action Code Read
				buffer.clear();
				int actionCode = buffer.getInt();
				buffer.clear();
				log.debug("ACTION("+actionCode+")",buffer);
				msg.setAction(actionCode);
				
				
				buffer = ByteBuffer.allocate(4);	//length Code Read
				buffer.order(ByteOrder.BIG_ENDIAN);
				i = read(buffer, 1000);
				buffer.clear();
				int length = buffer.getInt();
				buffer.clear();
				log.debug("LENGTH("+length+")",buffer);
//				msg.setAction(length);
				
				buffer = ByteBuffer.allocate(length);//data Read
				i = read(buffer, 2000);
				buffer.clear();
				msg.setData(buffer);
				log.debug("DATA",buffer);
				
				buffer = ByteBuffer.allocate(1);		//etx Read
				i = read(buffer, 1000);
				buffer.clear();
				//msg.setEtx(buffer.get(0));
				log.debug("ETX",buffer);
				
				if(i==1 && RelayMsg.ETX ==buffer.get(0)){
					log.debug("Good Message ActionCode :"+ msg.getAction());
					msg.setSuccess(true);
					msg.setSelectionKey(selectionKey);
				}
			}
		}
		return msg;
	}
	
	
	public void sendMsg(RelayMsg msg, SelectionKey selectionKey) throws Exception {
		if(selectionKey.isWritable() && msg.isSuccess()){
			SocketChannel channel = (SocketChannel)selectionKey.channel();
			write(msg.makeByteMsg(),channel);
		}
	}
	
	@Override
	public void execute(SelectionKey selectionKey) throws Exception{
		if(selectionKey.isReadable()){
			RelayMsg msg = receiveMsg(selectionKey);
			
			if(msg == null || (msg!=null && msg.isSuccess() == false)){
				log.info("RemoteMsg  [msg == null || msg.isSuccess() == false] "+msg.toString());
				return ;
			}
			msg = onReceiveAction(msg, selectionKey);
			
			if(msg == null || (msg!=null && msg.isSuccess() == false)){
				log.info("RemoteMsg  [msg == null || msg.isSuccess() == false] "+msg.toString());
				return ;
			}
			onSendAction(msg, selectionKey);
			
		}else if(selectionKey.isWritable()){
		}
	}
	
	
	
	
	public abstract RelayMsg onReceiveAction(RelayMsg msg, SelectionKey selectionKey) throws Exception;
	public abstract RelayMsg onSendAction(RelayMsg msg, SelectionKey selectionKey) throws Exception;
	
	
	

}
