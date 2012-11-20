package khh.communication.tcp.nio.worker;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import khh.communication.Communication_Interface;
import khh.communication.Connection_Interface;
import khh.debug.LogK;
import khh.debug.util.DebugUtil;
import khh.std.adapter.Adapter_Base;
import khh.util.Util;

abstract public class NioWorkerBusiness{
	
	public static final int MODE_FIREST_NONE 	= 0;
	public static final int MODE_FIREST_RW		= SelectionKey.OP_READ | SelectionKey.OP_WRITE;
	public static final int MODE_FIREST_W		= SelectionKey.OP_WRITE;
	public static final int MODE_FIREST_R		= SelectionKey.OP_READ;
	private int firestMode = MODE_FIREST_R;
	private SocketChannel		socketChannel		= null;
	private LogK log = LogK.getInstance();
	
	public void write(String data) throws IOException{
		write(data.getBytes());
	}
	public void write(byte[] data) throws IOException{
		ByteBuffer bytebuff  = ByteBuffer.allocate(data.length);
		bytebuff.put(data);
		bytebuff.clear();
		write(bytebuff);
	}	
	
	//여기서 알아서 처리혀라.
	abstract public void execute(SelectionKey selectionKey) throws Exception;
	
	
	synchronized public int write(ByteBuffer data) throws IOException{
		int write_length=0;
		try{
			log.debug("1) IsConnected  "+isConnected());
			write_length = getSocketChannel().write(data);
			log.debug("2) WriteLength "+write_length);
		}catch(IOException e){
			log.debug("3) Exception : Write Fail Socket=null  isConnected? -> "+isConnected());
			throw e;
		}
		return write_length;
	}
	
	synchronized public  int read(byte[] buffer, int timeout_daly_mm) throws IOException{
		ByteBuffer bytebuff  = ByteBuffer.allocate(buffer.length);
//		bytebuff.put(data);
		bytebuff.clear();
		int r = read(buffer, timeout_daly_mm);
		bytebuff.clear();
		bytebuff.get(buffer);
		bytebuff.clear();
		return r;
	}
	synchronized public  int read(ByteBuffer buffer, int timeout_daly_mm) throws IOException{
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
			if (Util.isTimeOver(start_mm, timeout_daly_mm)) {
				log.debug("readBlock Time-Out : readbuffer Info"+ buffer.toString() );
				throw new IOException("readBlock Time-Out  readbuffer Info"+buffer.toString());
			}
			try{
				Thread.sleep(1);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}	
	
	
	
	
	
	public boolean isConnected()
	{
		if ( getSocketChannel() == null )
			return false;
		return getSocketChannel().isConnected()   ;//&& socket.isOpen()&&socket.finishConnect();
	}
	
	
	public void setSocketChannel(SocketChannel socketChannel){
		this.socketChannel = socketChannel;
	}
	public SocketChannel getSocketChannel(){
		return this.socketChannel;
	}

	
	

	public int getFirestMode() {
		return firestMode;
	}
	public void setFirestMode(int firestMode) {
		this.firestMode = firestMode;
	}
	@Override
	protected void finalize() throws Throwable{
		getSocketChannel().close();
		super.finalize();
	}
	
	
	
}
