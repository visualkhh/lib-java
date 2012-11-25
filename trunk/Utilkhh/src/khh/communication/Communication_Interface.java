package khh.communication;

import java.nio.ByteBuffer;

import khh.std.adapter.Adapter_Base;

public interface Communication_Interface
{
	public void setConnection(Connection_Interface con);
	public Connection_Interface getConnection();
	
	public Adapter_Base execute() throws Exception;
	public Adapter_Base execute(String data) throws Exception;
	public Adapter_Base execute(byte[] data) throws Exception ;
	public Adapter_Base execute(ByteBuffer data) throws Exception ;
	public Adapter_Base execute(ByteBuffer data,int timeout) throws Exception;
	
	public Adapter_Base resultParsing(ByteBuffer data) throws Exception;
	
	
}
