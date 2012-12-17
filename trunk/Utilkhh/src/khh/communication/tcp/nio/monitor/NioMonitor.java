package khh.communication.tcp.nio.monitor;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import khh.collection.RoundRobin;
import khh.communication.tcp.nio.server.selector.NioServerSelector;
import khh.debug.LogK;

public class NioMonitor {
//public class NioServerMonitor extends Thread {
	//private Selector clientSelector	= null;
	ArrayList<Selector> selectorList = new ArrayList<Selector>();
	private LogK log = LogK.getInstance();
	public NioMonitor(ArrayList<Selector> selectorList) {
		init();
		this.selectorList = selectorList;
	}
	public NioMonitor() {
		init();
	}
	
	public void setSelectorPool(ArrayList<Selector> selectorList){
		this.selectorList = selectorList;
	}


	public void init() {
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
		for (int i = 0; i < getSelectorList().size(); i++) {
			Selector selector = getSelectorList().get(i);
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
	public ArrayList<Selector> getSelectorList(){
		return selectorList;
	}
	public void addSelector(Selector selector){
		getSelectorList().add(selector);
	}
}
