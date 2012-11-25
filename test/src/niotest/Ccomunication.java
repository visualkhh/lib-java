package niotest;
import java.nio.ByteBuffer;

import com.kdt.std.adapter.Adapter_Base;
import com.kdt.util.communication.tcpip.server.TcpServerCommunication;

public class Ccomunication extends TcpServerCommunication
{
	public Ccomunication()
	{
		setConnection(new CBusiness());
	}

	@Override
	public Adapter_Base execute() throws Exception
	{
		ByteBuffer buff = getConnection().receiveMessage(1000);
		Thread.sleep(100);
		getConnection().sendMessage(buff);
		return null;
	}

    @Override
    public Adapter_Base resultParsing(ByteBuffer data) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
	





}
