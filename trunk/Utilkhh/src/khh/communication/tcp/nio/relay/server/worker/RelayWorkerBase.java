package khh.communication.tcp.nio.relay.server.worker;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import khh.communication.tcp.nio.protocol.NioMsg;
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
    

	
	@Override
	public void execute(SelectionKey selectionKey) throws Exception{
		if(selectionKey.isReadable()){
		    NioMsg msg = receiveNioMsg(selectionKey);
			
			if(msg == null || (msg!=null && msg.isSuccess() == false)){
				log.info("NioMsg  [msg == null || msg.isSuccess() == false] "+msg);
				return ;
			}
			msg = onReceiveAction(msg, selectionKey);
			
			if(msg == null || (msg!=null && msg.isSuccess() == false)){
				log.info("NioMsg  [msg == null || msg.isSuccess() == false] "+msg);
				return ;
			}
			onSendAction(msg, selectionKey);
			
		}else if(selectionKey.isWritable()){
		}
	}
	
	
	
	
	public abstract NioMsg onReceiveAction(NioMsg msg, SelectionKey selectionKey) throws Exception;
	public abstract NioMsg onSendAction(NioMsg msg, SelectionKey selectionKey) throws Exception;
	
	
	

}
