package tcptest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import khh.communication.server.TcpServer;

public class TcpTest {
	public static void main(String[] args) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		TcpServer server = new TcpServer(8777, ComGG.class);
		server.setProcessManagerCount(1);
		server.setSelectorManagerCount(1);
		server.start();
	}
}
