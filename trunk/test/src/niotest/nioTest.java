package niotest;
import com.kdt.util.communication.tcpip.server.TcpServer;

public class nioTest
{
	public static void main(String[] args)
	{
		try
		{
			TcpServer server =new TcpServer(9090,Ccomunication.class);
			server.setProcessManagerCount(100);
			server.setSelectorManagerCount(100);
			server.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
