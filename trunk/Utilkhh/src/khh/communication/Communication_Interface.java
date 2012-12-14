package khh.communication;

import java.nio.ByteBuffer;

import khh.std.adapter.AdapterMapBase;

public interface Communication_Interface
{
	public void setConnection(Connection_Interface con);
	public Connection_Interface getConnection();
	
	public AdapterMapBase execute() throws Exception;
	public AdapterMapBase execute(String data) throws Exception;
	public AdapterMapBase execute(byte[] data) throws Exception ;
	public AdapterMapBase execute(ByteBuffer data) throws Exception ;
	public AdapterMapBase execute(ByteBuffer data,int timeout) throws Exception;
	
	public AdapterMapBase resultParsing(ByteBuffer data) throws Exception;
	
	
}
