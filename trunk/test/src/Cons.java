//import java.io.IOException;
//import java.nio.ByteBuffer;
//
//import com.kdt.util.Utilities;
//import com.kdt.util.comunication.TcpClientConnection;
//
//
//class Cons extends TcpClientConnection{
//
//	public Cons(String ServerAddr, int ServerPort) throws IOException
//	{
//		super(ServerAddr, ServerPort);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public ByteBuffer receiveMessage(int timeout) throws Exception
//	{
//		ByteBuffer a = ByteBuffer.allocate(10);
//		read(a,4);
//		Utilities.trace(a, "read");
//		return a;
//	}
//
//	@Override
//	public void sendMessage(ByteBuffer data) throws IOException
//	{
//		write(data);
//		
//	}
//	
//}