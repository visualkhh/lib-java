package khh.communication.tcp.nio.server.monitor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import khh.collection.RoundRobin;
import khh.communication.tcp.nio.server.selector.NioServerSelector;
import khh.debug.LogK;

public class NioServerMonitor {
//public class NioServerMonitor extends Thread {
	//private Selector clientSelector	= null;
	RoundRobin<NioServerSelector> selectorPool = null;
	private LogK log = LogK.getInstance();
	public NioServerMonitor(RoundRobin<NioServerSelector> selectorPool) throws IOException {
		init();
		this.selectorPool = selectorPool;
	}
	public void init() throws IOException{
		//clientSelector = Selector.open();
	}
//	@Override
//	public void run() {
//		while (true) {
//			try {
//				sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			log.debug("Monitor  ConnectedClientSize: " + getSelectionKey());
//		}
//	}
	public ArrayList<SelectionKey> getSelectionKeys(){
		ArrayList<SelectionKey> ret = new ArrayList<SelectionKey>();
		for (int i = 0; i < getSelectorPool().size(); i++) {
			NioServerSelector nioServerSelector = getSelectorPool().get(i);
			Selector selector = nioServerSelector.getSelector();
			if(selector==null){
				continue;
			}
			Set<SelectionKey> s = selector.keys();
			Iterator<SelectionKey> it = s.iterator();
			while (it.hasNext()){
				SelectionKey key = it.next();
				SocketChannel socket = (SocketChannel)key.channel();
				if(socket.isConnected()){	//ReadState SelectionKey  공유queue 에 input
					ret.add(key);
				}
//				it.remove();		//selectorkeys에 남은거지우기위해
			}
		} 
		return ret;
	}
	private RoundRobin<NioServerSelector> getSelectorPool(){
		return selectorPool;
	}
	
}
