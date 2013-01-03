package khh.communication.tcp.nio.monitor;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;


public class NioMultiMonitor{
	// serverid, <user_sessionKey_Strig,selectionKey>
	public Hashtable<String, Hashtable<String, SelectionKey>> clientSelectionKey = null;
	public Hashtable<String, NioMonitor> monitors = null;
//	public Adapter_Std<String, Adapter_Std<String, SelectionKey>> serverlist = null;
//	public Adapter_Std<String, NioServerMonitor> monitors = null;

	public NioMultiMonitor(){
		init();
	}

	synchronized private void init(){
		clientSelectionKey = new Hashtable<String, Hashtable<String, SelectionKey>>();
		monitors = new Hashtable<String, NioMonitor>();
//		serverlist = new Adapter_Std<String, Adapter_Std<String, SelectionKey>>();
//		monitors = new Adapter_Std<String, NioServerMonitor>();
	}

	synchronized public void putMonitor(String servername, NioMonitor monitor) throws Exception{
		synchronized(monitors){
			monitors.put(servername, monitor);
		}
		synchronized(clientSelectionKey){
			clientSelectionKey.put(servername, new Hashtable<String, SelectionKey>());
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
			Hashtable<String, SelectionKey> list = clientSelectionKey.get(servername);
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
			Hashtable<String, SelectionKey> list = clientSelectionKey.get(servername);
			synchronized(list){
				list.remove(sessionKey);
			}
		}
	}
	
	

	synchronized public SelectionKey getSelectionKey(String serverid, String sessionKey) throws Exception{
		ArrayList<SelectionKey> monitorKeys = null;
		Hashtable<String, SelectionKey> serverKeys = null;
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
	
	synchronized public Hashtable<String,SelectionKey> getClientSelectionKeys(String serverid) throws Exception{
		Hashtable<String,SelectionKey> keys = new Hashtable<String,SelectionKey>();
		Hashtable<String,SelectionKey> acct = clientSelectionKey.get(serverid);
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
	synchronized public Hashtable<String,SelectableChannel > getClientSocketChannels(String serverid) throws Exception{
		Hashtable<String,SelectableChannel > keys = new Hashtable<String,SelectableChannel >();
		Hashtable<String,SelectionKey> acct = clientSelectionKey.get(serverid);
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

	public Hashtable<String, NioMonitor> getMonitors(){
		return monitors;
	}
	
	
	
	
	
}
