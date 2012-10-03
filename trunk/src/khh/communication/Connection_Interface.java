package khh.communication;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface Connection_Interface<T>
{
	public void sendMessage(String data) throws Exception;
	
	public void sendMessage(byte[] data) throws Exception;
	
	
	///read  sync!!
	public void sendMessage(ByteBuffer data) throws Exception;
	///read  sync!!
	public ByteBuffer receiveMessage(int timeout) throws Exception;

	

	
	 public int write(ByteBuffer data) throws Exception;
	 public  int read(ByteBuffer buffer, int timeout) throws Exception;
	 
	 
	public void setSocket(T socket);
	public T getSocket()throws Exception;
	
	public T connection() throws IOException;
	public boolean isConnected();
	
	public void close() throws IOException;
	
	
}
