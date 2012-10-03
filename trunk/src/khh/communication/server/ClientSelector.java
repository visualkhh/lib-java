package khh.communication.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import khh.collection.Queue;
import khh.debug.util.DebugUtil;

public class ClientSelector extends Thread
{
	private int						clientCount		= 0; //나갈때 처리된건아직,...
//	private Vector<SocketChannel>	newClients		= new Vector<SocketChannel>();
//	private ArrayList<SocketChannel>	newClients		= new ArrayList<SocketChannel>();
	private Queue<SocketChannel>	newClients		= new Queue<SocketChannel>();
	private Selector				clientSelector	= null;
	private EventQueue				queue			= null;

	public ClientSelector(EventQueue queue) throws IOException
	{
		this.queue = queue;
		clientSelector = Selector.open();
		DebugUtil.trace("ClientSelector-%03d Create...", getId() );
	}

	public void run()
	{
		DebugUtil.trace("ClientSelector-%03d Running...", getId() );
		while (true)
		{
			try
			{
				processNewClient();
				if( clientSelector.select(3) > 0 )
				{
					Iterator<SelectionKey> it = clientSelector.selectedKeys().iterator();
					
					while (it.hasNext())
					{
						SelectionKey key = (SelectionKey) it.next();
						
						if(key.isReadable())
						{
							key.interestOps(0);
							queue.push(key);
						}
						it.remove();
					}
				}
			}
			catch (Exception e)
			{
			}
		}
	}

	private synchronized void processNewClient() throws IOException
	{
//		Iterator<SocketChannel> iter = newClients.iterator();

//		while (iter.hasNext())
//		{
//			SocketChannel chnnel = (SocketChannel) iter.next();
//			chnnel.configureBlocking(false);
//			chnnel.register(clientSelector, SelectionKey.OP_READ);
//		}
//		newClients.clear();
//		for (int i = 0; i < newClients.size(); i++)
//		{
//			SocketChannel chnnel = (SocketChannel) newClients.get(i);
//			chnnel.configureBlocking(false);
//			chnnel.register(clientSelector, SelectionKey.OP_READ);
//		}
//		newClients.clear();
		
//		for (int i = 0; i < newClients.size(); i++)
//		{
//			SocketChannel chnnel = (SocketChannel) newClients.get(i);
//			chnnel.configureBlocking(false);
//			chnnel.register(clientSelector, SelectionKey.OP_READ);
//			newClients.remove(i);
//			break;
//		}
		try{
			SocketChannel chnnel = (SocketChannel) newClients.pop();
			chnnel.configureBlocking(false);
			chnnel.register(clientSelector, SelectionKey.OP_READ);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void addNewClient(SocketChannel newClient)
	{
		clientCount++;
		newClients.push(newClient);
	}

	public int size()
	{
		return clientCount;
	}
}
