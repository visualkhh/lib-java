package khh.communication.server;

import java.nio.channels.SelectionKey;
import java.util.Vector;

import khh.debug.util.DebugUtil;


public class EventQueue 
{
	private final Vector<SelectionKey>	readQueue	= new Vector<SelectionKey>();
	private static EventQueue			instance	= new EventQueue();

	public static EventQueue getInstance()
	{
		if ( instance == null )
		{
			synchronized (EventQueue.class)
			{
				instance = new EventQueue();
			}
		}
		return instance;
	}

	public EventQueue()
	{
		DebugUtil.trace("Keracle KEventQueue Create...");
	}

	public synchronized SelectionKey pop()
	{
		if ( readQueue.isEmpty() )
			return null;

		return readQueue.remove(0);
	}

	public void push(SelectionKey channel)
	{
		readQueue.add(channel);
	}

	public int size()
	{
		return readQueue.size();
	}
}