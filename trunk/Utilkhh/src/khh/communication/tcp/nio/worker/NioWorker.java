package khh.communication.tcp.nio.worker;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import khh.communication.Communication_Interface;
import khh.communication.Connection_Interface;
import khh.communication.tcp.nio.protocol.NioMsg;
import khh.communication.tcp.nio.server.NioServer;
import khh.debug.LogK;
import khh.debug.util.DebugUtil;
import khh.std.adapter.Adapter_Base;
import khh.util.ByteUtil;
import khh.util.Util;

abstract public class NioWorker{
	
	public static final int MODE_FIREST_NONE 	= 0;
	public static final int MODE_FIREST_RW		= SelectionKey.OP_READ | SelectionKey.OP_WRITE;
	public static final int MODE_FIREST_W		= SelectionKey.OP_WRITE;
	public static final int MODE_FIREST_R		= SelectionKey.OP_READ;
	private int firestMode 						= MODE_FIREST_R;
	private SocketChannel socketChannel			= null;
	private LogK log 							= LogK.getInstance();
	private NioServer server =  null;
	
	
	public NioWorker(int firestMode) {
	    setFirestMode(firestMode);
    }
	public NioWorker() {
    }
	
	final synchronized public void write(String data) throws IOException{
		write(data.getBytes());
	}
	final synchronized public void write(byte[] data) throws IOException{
		ByteBuffer bytebuff = ByteBuffer.allocate(data.length);
		bytebuff.put(data);
		bytebuff.clear();
		write(bytebuff);
	}	
	
	//여기서 알아서 처리혀라.
	abstract public void execute(SelectionKey selectionKey) throws Exception;
	
	
	final synchronized public int write(ByteBuffer data) throws IOException{
		return write(data,getSocketChannel());
	}
	final synchronized public int write(ByteBuffer data,SocketChannel socketChannel) throws IOException{
		int write_length=0;
		try{
//		    System.out.println( log.toHexlog(data));
//		    DebugUtil.trace(ByteUtil.toByteArray(data), "---------");
//		    System.out.println( log.toHexlog(data));
			//log.debug("Write Before  "+isConnected(),data);
			write_length = socketChannel.write(data);
			//log.debug("Write after OK!  "+isConnected());
//			log.debug("2) WriteLength "+write_length);
		}catch(IOException e){
			log.debug("Exception : Write Fail Socket=null  isConnected? -> "+isConnected());
			throw e;
		}
		return write_length;
	}
	
	
	
	final synchronized public  int read(byte[] buffer, int timeout_daly_mm) throws IOException{
		ByteBuffer bytebuff  = ByteBuffer.allocate(buffer.length);
		bytebuff.clear();
		int r = read(buffer, timeout_daly_mm);
		bytebuff.clear();
		bytebuff.get(buffer);
		bytebuff.clear();
		return r;
	}
	final synchronized public  int read(ByteBuffer buffer, int timeout_daly_ms) throws IOException{
		return read(buffer,timeout_daly_ms,getSocketChannel());
	}
	final synchronized public  int read(ByteBuffer buffer, int timeout_daly_ms,SocketChannel socketChannel) throws IOException{
		int len = 0;
		long start_mm = System.currentTimeMillis();
		while (true){
			len = getSocketChannel().read(buffer);
			if(len > 0){
				if ( buffer.position() == buffer.limit() ) {
					return len;
				}
			}
			//timeout Chk
			if (Util.isTimeOver(start_mm, timeout_daly_ms)) {
				log.debug("readBlock Time-Out : readbuffer Info"+ buffer.toString()+" daly_ms:"+timeout_daly_ms);
				throw new SocketTimeoutException("readBlock Time-Out  readbuffer Info"+buffer.toString()+" daly_ms:"+timeout_daly_ms+"  isConnected:"+getSocketChannel().isConnected());
			}
			
			try{
				Thread.sleep(1);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}	
	
	
	
	
	//////////protocol!!
	   /*
     * STX(1byte), ACTIONCODE(int4byte), LENGTH(datalength int4byte), DATA(bytes..~..), ETX(1byte) 
     */
	public NioMsg receiveNioMsg() throws IOException {
	    return receiveNioMsg(getSocketChannel());
	}
	public NioMsg receiveNioMsg(SelectionKey selectionKey) throws IOException {
	    return receiveNioMsg((SocketChannel)selectionKey.channel());
	}
    public NioMsg receiveNioMsg(SocketChannel socket) throws IOException {
        NioMsg msg = null;
//        if(selectionKey.isReadable()){
            ByteBuffer buffer = ByteBuffer.allocate(1);
            int i = read(buffer, 1000,socket); //stx Read
            buffer.clear();
            //log.debug("SEX",buffer);
            
            if(i==1 && NioMsg.STX == buffer.get(0)){
                msg = new NioMsg();
                //msg.setStx(buffer.get(0));
                
                buffer = ByteBuffer.allocate(4);
                buffer.order(ByteOrder.BIG_ENDIAN);
                i = read(buffer, 1000,socket);         //action Code Read
                buffer.clear();
                int actionCode = buffer.getInt();
                buffer.clear();
                //log.debug("ACTION("+actionCode+")",buffer);
                msg.setAction(actionCode);
                
                
                buffer = ByteBuffer.allocate(4);    //length Code Read
                buffer.order(ByteOrder.BIG_ENDIAN);
                i = read(buffer, 1000,socket);
                buffer.clear();
                int length = buffer.getInt();
                buffer.clear();
               // log.debug("LENGTH("+length+")",buffer);
//              msg.setAction(length);
                
                buffer = ByteBuffer.allocate(length);//data Read
                i = read(buffer, 2000,socket);
                buffer.clear();
                msg.set(buffer);
                //log.debug("DATA",buffer);
                
                buffer = ByteBuffer.allocate(1);        //etx Read
                i = read(buffer, 1000,socket);
                buffer.clear();
                //msg.setEtx(buffer.get(0));
               // log.debug("ETX",buffer);
                
                if(i==1 && NioMsg.ETX ==buffer.get(0)){
                //    log.debug("Good Message ActionCode :"+ msg.getAction());
                    msg.setSuccess(true);
                    //msg.setSelectionKey(selectionKey);
                }
            }
//        }
        return msg;
    }
    
    
    public void sendNioMsg(NioMsg msg) throws Exception {
        sendNioMsg(msg,getSocketChannel());
    }
    public void sendNioMsg(NioMsg msg, SelectionKey selectionKey) throws Exception {
        sendNioMsg(msg,(SocketChannel)selectionKey.channel());
    }
    public void sendNioMsg(NioMsg msg, SocketChannel socket) throws Exception {
        if(msg.isSuccess()){
            write(msg.unformat(),socket);
        }
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	final public boolean isConnected(){
		if ( getSocketChannel() == null )
			return false;
		return getSocketChannel().isConnected()   ;//&& socket.isOpen()&&socket.finishConnect();
	}
	final public void setSocketChannel(SocketChannel socketChannel){
		this.socketChannel = socketChannel;
	}
	final public SocketChannel getSocketChannel(){
		return this.socketChannel;
	}
	final public int getFirestMode(){
		return firestMode;
	}
	final public void setFirestMode(int firestMode){
		this.firestMode = firestMode;
	}
	@Override
	protected void finalize() throws Throwable{
		getSocketChannel().close();
		super.finalize();
	}
	
	final synchronized public void close(){
		close(getSocketChannel());
	}
	final synchronized public void close(SocketChannel socket){
		try{
			if(socket!=null)
			socket.close();
		}catch (IOException e1){
		}
	}
	public NioServer getServer(){
		return server;
	}
	public void setServer(NioServer server){
		this.server = server;
	}
	
	
}
