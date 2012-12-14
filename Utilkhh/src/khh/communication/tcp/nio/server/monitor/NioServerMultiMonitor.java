package khh.communication.tcp.nio.server.monitor;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class NioServerMultiMonitor{
	// serverid, <user_sessionKey_Strig,selectionKey>
	public HashMap<String, HashMap<String, SelectionKey>> clientSelectionKey = null;
	public HashMap<String, NioServerMonitor> monitors = null;
//	public Adapter_Std<String, Adapter_Std<String, SelectionKey>> serverlist = null;
//	public Adapter_Std<String, NioServerMonitor> monitors = null;

	public NioServerMultiMonitor(){
		init();
	}

	synchronized private void init(){
		clientSelectionKey = new HashMap<String, HashMap<String, SelectionKey>>();
		monitors = new HashMap<String, NioServerMonitor>();
//		serverlist = new Adapter_Std<String, Adapter_Std<String, SelectionKey>>();
//		monitors = new Adapter_Std<String, NioServerMonitor>();
	}

	synchronized public void putMonitor(String servername, NioServerMonitor monitor) throws Exception{
		synchronized(monitors){
			monitors.put(servername, monitor);
		}
		synchronized(clientSelectionKey){
			clientSelectionKey.put(servername, new HashMap<String, SelectionKey>());
		}
	}
	
	

	synchronized public void removeMonitor(String servername) throws Exception{
		synchronized(clientSelectionKey){
			clientSelectionKey.remove(servername);
		}
		synchronized(monitors){
			monitors.remove(servername);
		}
	}
	synchronized public void putSelectionKey(String servername,String sessionKey,SelectionKey selectionKey) throws Exception{
		synchronized(clientSelectionKey){
			HashMap<String, SelectionKey> list = clientSelectionKey.get(servername);
			synchronized(list){
				list.put(sessionKey, selectionKey);
			}
		}
	}
//	synchronized public void setSelectionKey(String servername,String sessionKey,SelectionKey selectionKey) throws Exception{
//		synchronized(serverlist){
//			Adapter_Std<String, SelectionKey> list = serverlist.get(servername);
//			synchronized(list){
//				list.set(sessionKey, selectionKey);
//			}
//		}
//	}
	
	
	synchronized public void removeSelectionKey(String servername,String sessionKey) throws Exception{
		synchronized(clientSelectionKey){
			HashMap<String, SelectionKey> list = clientSelectionKey.get(servername);
			synchronized(list){
				list.remove(sessionKey);
			}
		}
	}
	
	

	synchronized public SelectionKey getSelectionKey(String serverid, String sessionKey) throws Exception{
		ArrayList<SelectionKey> monitorKeys = null;
		HashMap<String, SelectionKey> serverKeys = null;
		SelectionKey skey = null;
		synchronized(monitors){
			synchronized(clientSelectionKey){
				monitorKeys = monitors.get(serverid).getSelectionKeys();
				serverKeys = clientSelectionKey.get(serverid);
				skey = serverKeys.get(sessionKey);
			}
		}
		synchronized(monitorKeys){
			for(int i = 0; i < monitorKeys.size(); i++){
				SelectionKey monitorSelectionKey = monitorKeys.get(i);
				if(monitorSelectionKey.equals(skey)||monitorSelectionKey==skey){
					return monitorSelectionKey;
				}
			}
			
			removeSelectionKey(serverid,sessionKey);
			return null;
		}
	}


	
	
	
	synchronized public SelectionKey getSelectionKey(String sessionKey) throws Exception{
		synchronized(monitors){
			Iterator<String> iter = monitors.keySet().iterator();
			while(iter.hasNext()){
				SelectionKey key  =  getSelectionKey(iter.next(),sessionKey);
				if(key!=null){
					return key;
				}
			}
			return null;
		}
	}
	synchronized public ArrayList<SelectionKey> getSelectionKeys(String sessionKey) throws Exception{
		synchronized(monitors){
			ArrayList<SelectionKey> selectionKeys = new ArrayList<SelectionKey>();
			Iterator<String> iter = monitors.keySet().iterator();
			while(iter.hasNext()){
				SelectionKey key  =  getSelectionKey(iter.next(),sessionKey);
				if(key!=null){
					selectionKeys.add( key );
				}
			}
			return selectionKeys;
		}
	}
	
	synchronized public HashMap<String,SelectionKey> getClientSelectionKeys(String serverid) throws Exception{
		HashMap<String,SelectionKey> keys = new HashMap<String,SelectionKey>();
		HashMap<String,SelectionKey> acct = clientSelectionKey.get(serverid);
		Iterator<String> iter = acct.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			SelectionKey selectionKey = getSelectionKey(serverid, key);
			if(key!=null){
				keys.put(key,selectionKey);
			}
		}
		return keys;
	}
	synchronized public HashMap<String,SelectableChannel > getClientSocketChannels(String serverid) throws Exception{
		HashMap<String,SelectableChannel > keys = new HashMap<String,SelectableChannel >();
		HashMap<String,SelectionKey> acct = clientSelectionKey.get(serverid);
		Iterator<String> iter = acct.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			SelectionKey selectionKey = getSelectionKey(serverid, key);
			if(key!=null){
				keys.put(key,selectionKey.channel());
			}
		}
		return keys;
	}

	public HashMap<String, NioServerMonitor> getMonitors(){
		return monitors;
	}
	
	
	
	
	
}
