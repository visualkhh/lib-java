package khh.communication.tcp.nio.util;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioUtil {

	public static Iterator<SelectionKey> getSelectionKeys(Selector selector){
		return selector.selectedKeys().iterator();
	}
	public static SocketChannel getSocketChannel(SelectionKey selectionKey){
	       SocketChannel channel = (SocketChannel)selectionKey.channel();
	       return channel;
	   }
	public static ServerSocketChannel getServerSocketChannel(SelectionKey selectionKey){
	    ServerSocketChannel channel = (ServerSocketChannel)selectionKey.channel();
	       return channel;
	   }
	   
}
