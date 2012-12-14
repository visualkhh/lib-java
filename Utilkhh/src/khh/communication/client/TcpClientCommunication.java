package khh.communication.client;

import java.nio.ByteBuffer;

import khh.communication.Communication_Interface;
import khh.communication.Connection_Interface;
import khh.std.adapter.AdapterMapBase;

abstract public class TcpClientCommunication implements Communication_Interface
{
	private TcpClientConnection			connection = null;
	private int readBlock_TimeOut=5000;
	
	
	public TcpClientCommunication()
	{
	}
	@Override
	protected void finalize() throws Throwable
	{
		getConnection().close();
		super.finalize();
	}
	
	public int getReadBlock_TimeOut()
	{
		return readBlock_TimeOut;
	}
	public void setReadBlock_TimeOut(int readBlockTimeOut)
	{
		readBlock_TimeOut = readBlockTimeOut;
	}
//	public void close() throws Exception
//	{
//		connection.close();
//		Utilities.trace("KDBC.java   Call!!  Close()");
//	}
	
	public void setConnection(Connection_Interface con){
		this.connection = (TcpClientConnection)con;
	}
	public TcpClientConnection getConnection(){
		return connection;
	}

//	public boolean isConnected() throws IOException{
//		return connection.isConnected();
//	}


	synchronized public AdapterMapBase execute() throws Exception{
		return null;
	}
	synchronized public AdapterMapBase execute(String data) throws Exception {
		return execute(data.getBytes());
	}
	synchronized public AdapterMapBase execute(byte[] data) throws Exception {
		ByteBuffer bytebuff  = ByteBuffer.allocate(data.length);
		bytebuff.put(data);
		bytebuff.rewind();
		return execute(bytebuff);
	}
	synchronized public AdapterMapBase execute(ByteBuffer data) throws Exception {
		return execute(data,getReadBlock_TimeOut());
	}
	abstract public  AdapterMapBase execute(ByteBuffer data,int timeout) throws Exception;

	abstract public AdapterMapBase resultParsing(ByteBuffer buffer) throws Exception;
	
	
}
