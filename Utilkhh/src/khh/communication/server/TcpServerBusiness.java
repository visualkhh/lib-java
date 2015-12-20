package khh.communication.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import khh.communication.Connection_Interface;
import khh.debug.util.DebugUtil;
import khh.util.Util;

abstract public class TcpServerBusiness implements Connection_Interface<SocketChannel>
{
	private SocketChannel		socket		= null;
	public TcpServerBusiness(){
	}
	public boolean isConnected()
	{
		if ( socket == null )
			return false;
		return socket.isConnected()   ;//&& socket.isOpen()&&socket.finishConnect();
	}
	
	public void sendMessage(String data) throws Exception
	{
		sendMessage(data.getBytes());
	}
	public void sendMessage(byte[] data) throws Exception
	{
		ByteBuffer bytebuff  = ByteBuffer.allocate(data.length);
		bytebuff.put(data);
		bytebuff.rewind();
		sendMessage(bytebuff);
	}

	
	///read  sync!!
	abstract public void sendMessage(ByteBuffer data) throws Exception;
	///read  sync!!
	abstract public ByteBuffer receiveMessage(int timeout) throws Exception;

	

	
	synchronized public int write(ByteBuffer data) throws Exception{
		int write_length=0;
		try{
		System.out.println("1) IsConnected  "+isConnected());
		write_length = getSocket().write(data);
		System.out.println("2) WriteLength "+write_length);
		}catch(Exception e){
//			e.printStackTrace();
//			try{
//			socket.close();
//			}catch (Exception ze) {
//				ze.printStackTrace();
//			}
//			socket=null;
			System.out.println("3) Write Fail Socket=null  isConnected? -> "+isConnected());
			throw e;
		}
		return write_length;
	}
	
	synchronized public  int read(ByteBuffer buffer, int timeout) throws IOException
	{
		int len = 0;
		long start_mm = System.currentTimeMillis();
		while (true)
		{
			len = getSocket().read(buffer);
			if(len > 0) {
				if ( buffer.position() == buffer.limit() ) {
//					Utilities.trace(buffer, "readBlock End");
//					Utilities.trace("readBlock End : %d %d", buffer.position(), buffer.limit() );
					return len;
				}
			}
			
			
			//timeout Chk
			if (Util.isTimeOver(start_mm, timeout)) {
				DebugUtil.trace("readBlock Time-Out : readbuffer Info"+ buffer.toString() );
				throw new IOException("readBlock Time-Out  readbuffer Info"+buffer.toString());
			}
			
			
			
			try
			{
				Thread.sleep(1);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			
		}
	}	
	public void setSocket(SocketChannel socket){
		this.socket=socket;
	}
	public SocketChannel getSocket()
	{
		return socket;
	}
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
	
	
	
	
	
	
	public void close() throws IOException
	{
		getSocket().close();
		
	}
	public SocketChannel connection() throws IOException
	{
		return null;
	}

	
	
//	//etc
//	public ByteBuffer getByteBuffer(){
//		buffer.clear();
//		return buffer;
//	}
	
}
