package khh.communication.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import khh.communication.Connection_Interface;
import khh.util.Util;

abstract public class TcpClientConnection implements Connection_Interface<SocketChannel>
{
//	private KCharset 			charset		= new KCharset();
	private SocketChannel		socket		= null;
//	private Socket		socket_ori		= null;
	
//	private ByteBuffer			buffer		= null;
	private String				serverAddr	= "";
	private int					serverPort	= 9090;
	private int 				serverConnectionTimeout=0;
	
	public TcpClientConnection(){
		
	}
	public TcpClientConnection(String ServerAddr, int ServerPort,int ServerConnectionTimeout) throws IOException
	{
		
//		buffer = ByteBuffer.allocateDirect(1000000);
		setServerAddr(ServerAddr);
		setServerPort(ServerPort);
		setServerConnectionTimeout(ServerConnectionTimeout);
		connection();
	}
	public TcpClientConnection(String ServerAddr, int ServerPort) throws IOException
	{
		
//		buffer = ByteBuffer.allocateDirect(1000000);
		setServerAddr(ServerAddr);
		setServerPort(ServerPort);
		setServerConnectionTimeout(0);
		connection();
	}

	
	//connection=================
	public SocketChannel connection() throws IOException{
		InetSocketAddress	addr = new InetSocketAddress(getServerAddr(), getServerPort());
		
		SocketChannel socket=null;
		if(getServerConnectionTimeout()>0){
			socket = SocketChannel.open();
			socket.socket().connect(addr,getServerConnectionTimeout());
//			socket_ori = socket.socket();
//			socket_ori.connect(addr,getServerConnectionTimeout());
		}else{
			socket = SocketChannel.open(addr);
//			socket_ori=socket.socket();
		}
		socket.configureBlocking(false);
		
		//DebugUtil.trace("New Connection  %s   %d   %d",getServerAddr(), getServerPort(),getServerConnectionTimeout());
		
		setSocket(socket);
		return socket;
	}

	
	public boolean isConnected() 
	{
		if ( socket == null )
			return false;
		return socket.isConnected() && socket.socket().isConnected()  ;//&& socket.isOpen()&&socket.finishConnect();
	}


	public void close() throws IOException
	{
			socket.close();
			//DebugUtil.trace("Socket Close!! is isConnected() = %b",socket.isConnected());
	}
	
	public SocketChannel getSocket() throws Exception{
		if(isConnected()==false){
			socket = connection();
		}
		return socket;
	}
	public void setSocket(SocketChannel socket){
		this.socket= socket;
	}




	public int getServerConnectionTimeout() {
		return serverConnectionTimeout;
	}
	public void setServerConnectionTimeout(int serverConnectionTimeout) {
		this.serverConnectionTimeout = serverConnectionTimeout;
	}
	public String getServerAddr() {
		return serverAddr;
	}


	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}


	public int getServerPort() {
		return serverPort;
	}


	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}


	public void setFServerPort(int fServerPort) {
		serverPort = fServerPort;
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

	
	
	
	///write  sync !!!
	abstract public void sendMessage(ByteBuffer data) throws IOException, Exception ;
	///read  sync!!
	abstract public ByteBuffer receiveMessage(int timeout) throws Exception;

	
	
	synchronized public int write(ByteBuffer data) throws Exception{
		int write_length=0;
		try{
		System.out.println("1) IsConnected  "+isConnected());
		write_length = getSocket().write(data);
		System.out.println("2) WriteLength "+write_length);
		}catch(Exception e){
			e.printStackTrace();
			try{
			socket.close();
			}catch (Exception ze) {
				ze.printStackTrace();
			}
			socket=null;
			System.out.println("3) Write Fail Socket=null  isConnected? -> "+isConnected());
			throw e;
			
		}
		return write_length;
	}
	
	synchronized public  int read(ByteBuffer buffer, int timeout) throws Exception
	{
		int len = 0;
		long start_mm = System.currentTimeMillis();
		while (true)
		{
			len = getSocket().read(buffer);
			if(len > 0) {
				if ( buffer.position() == buffer.limit() ) {
//					KDebug.trace("readBlock End : %d %d", buffer.position(), buffer.limit() );
					return len;
				}
			}
			
			
			//timeout Chk
			if (Util.isTimeOver(start_mm, timeout)) {
			//	DebugUtil.trace("readBlock Time-Out : readbuffer Info"+ buffer.toString() );
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
	
	@Override
		protected void finalize() throws Throwable {
			close();
			super.finalize();
		}
	
	
//	//etc
//	public ByteBuffer getByteBuffer(){
//		buffer.clear();
//		return buffer;
//	}
	
}
