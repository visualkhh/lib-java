package khh.communication.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import khh.debug.util.DebugUtil;


public class AcceptSelector extends Thread
{

	private Selector				acceptSelector		= null;
	private ServerSocket			serverSocket		= null;
	private ServerSocketChannel		serverSocketChannel	= null;
	private ClientSelectorManager	clientSelectManager	= null;

	public AcceptSelector(ClientSelectorManager clientSelectManager,int port) throws IOException
	{
		this.clientSelectManager = clientSelectManager;

		acceptSelector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocket = serverSocketChannel.socket();
		InetSocketAddress isa = new InetSocketAddress(port);
		serverSocket.bind(isa);
		serverSocketChannel.register(acceptSelector, SelectionKey.OP_ACCEPT);
		DebugUtil.trace("ClientSelector-%d Create...", getId() );
	}

	public void run()
	{
		DebugUtil.trace("ClientSelector-%d Running...", getId() );
		try
		{
			while (true)
			{
				acceptSelector.select();

				Iterator<SelectionKey> it = acceptSelector.selectedKeys().iterator();

				while (it.hasNext())
				{
					SelectionKey key = (SelectionKey) it.next();
					it.remove();

					ServerSocketChannel server = (ServerSocketChannel) key.channel();
					SocketChannel client = server.accept();
					
					DebugUtil.trace("AcceptThread Connected From %s  user port %d ", client.socket().getRemoteSocketAddress().toString(),client.socket().getPort());
					clientSelectManager.addNewClient(client);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
