package khh.communication.server;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import khh.collection.RoundRobin;

public class ClientSelectorManager
{
	private RoundRobin<ClientSelector>	pool	= new RoundRobin<ClientSelector>();
	private int						index	= 0;

	public ClientSelectorManager(EventQueue queue, int size) throws IOException
	{
		for (int i = 0; i < size; i++)
			pool.add(new ClientSelector(queue));
		
		init();
	}

	public void init()
	{
//		Iterator<KClientSelector> iter = pool.iterator();
//		while (iter.hasNext())
//		{
//			Thread handler = (Thread) iter.next();
//			handler.start();
//		}
		for(int i = 0 ; i <pool.size();i++){
			ClientSelector handler = (ClientSelector) pool.get(i);//ClientSelector ->Thread
			handler.start();
		}
	}

	public void addNewClient(SocketChannel channel)
	{
		ClientSelector clientSelector = pool.getNext();
		clientSelector.addNewClient(channel);
	}
}
