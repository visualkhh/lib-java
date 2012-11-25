package niotest;
import java.nio.ByteBuffer;

import com.kdt.util.communication.tcpip.server.TcpServerBusiness;

public class CBusiness extends TcpServerBusiness
{

	@Override
	public ByteBuffer receiveMessage(int timeout) throws Exception
	{
		ByteBuffer buff = ByteBuffer.allocate(10);
		read(buff, timeout);
		buff.rewind();
		return buff;
	}
	@Override
	public void sendMessage(ByteBuffer data) throws Exception
	{
		write(data);
	}
	
	

}
