package khh.communication.tcp.nio.util;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class NioUtil {

	public static Iterator<SelectionKey> getSelectionKeys(Selector selector){
		return selector.selectedKeys().iterator();
	}
}
