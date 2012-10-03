package khh.communication.server;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import khh.communication.Communication_Interface;
import khh.communication.Connection_Interface;
import khh.std.adapter.Adapter_Base;

//꼭 생성자에서 비지니스를 setConnection 해줘야함
abstract public class TcpServerCommunication implements Communication_Interface
{
//	private TcpServerBusiness	connection = null;
	private Connection_Interface<SocketChannel>	connection = null;
	private SocketChannel		socket		= null;
//	private int readBlock_TimeOut=5000;
	
	
//	public TcpServerCommunication()
//	{
//	}
//	public SocketChannel getSocket()
//	{
//		return socket;
//	}
//	public void setSocket(SocketChannel socket)
//	{
//		getConnection().setSocket(socket);
//		this.socket = socket;
//	}
	@Override
	protected void finalize() throws Throwable
	{
		getConnection().close();
		super.finalize();
	}
	
//	public int getReadBlock_TimeOut()
//	{
//		return readBlock_TimeOut;
//	}
//	public void setReadBlock_TimeOut(int readBlockTimeOut)
//	{
//		readBlock_TimeOut = readBlockTimeOut;
//	}
	
//	public abstract void setConnection();
	
	public void setConnection(Connection_Interface con){
		this.connection = con;
	}
	public Connection_Interface<SocketChannel> getConnection(){
		return connection;
	}

//	public boolean isConnected() throws IOException{
//		return connection.isConnected();
//	}


	
//	synchronized public Adapter_Base execute(String data) throws Exception {
//		return execute(data.getBytes());
//	}
//	synchronized public Adapter_Base execute(byte[] data) throws Exception {
//		ByteBuffer bytebuff  = ByteBuffer.allocate(data.length);
//		bytebuff.put(data);
//		bytebuff.rewind();
//		return execute(bytebuff);
//	}
//	synchronized public Adapter_Base execute(ByteBuffer data) throws Exception {
//		return execute(data,getReadBlock_TimeOut());
//	}
	
	

	abstract public Adapter_Base execute() throws Exception;
	

	public Adapter_Base execute(String data) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Adapter_Base execute(byte[] data) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Adapter_Base execute(ByteBuffer data) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Adapter_Base execute(ByteBuffer data, int timeout) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
	
	
}
