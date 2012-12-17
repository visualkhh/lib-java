package tcptest;

import java.nio.ByteBuffer;

import khh.communication.server.TcpServerBusiness;
import khh.communication.server.TcpServerCommunication;
import khh.std.adapter.Adapter_Base;

public class ComGG extends TcpServerCommunication{

	public ComGG() {
		setConnection(new TcpServerBusiness() {
			@Override
			public void sendMessage(ByteBuffer data) throws Exception {
				write(data);
				
			}
			@Override
			public ByteBuffer receiveMessage(int timeout) throws Exception {
				ByteBuffer get = ByteBuffer.allocate(1);
				int t  = read(get, timeout);
				return get;
			}
		});
	}
	
	@Override
	public Adapter_Base execute() throws Exception {
		ByteBuffer get = ByteBuffer.allocate(1);
		ByteBuffer g = getConnection().receiveMessage(10);
		g.rewind();
		getConnection().sendMessage(g);
		return null;
	}

	public Adapter_Base resultParsing(ByteBuffer data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}




}
