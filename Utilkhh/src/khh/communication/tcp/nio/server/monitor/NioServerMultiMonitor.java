package khh.communication.tcp.nio.server.monitor;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;

import khh.std.adapter.Adapter_Std;

public class NioServerMultiMonitor{
	// serverid, <user_sessionKey_Strig,selectionKey>
	public Adapter_Std<String, Adapter_Std<String, SelectionKey>> serverlist = null;
	public Adapter_Std<String, NioServerMonitor> monitors = null;

	public NioServerMultiMonitor(){
		init();
	}

	synchronized private void init(){
		serverlist = new Adapter_Std<String, Adapter_Std<String, SelectionKey>>();
		monitors = new Adapter_Std<String, NioServerMonitor>();
	}

	synchronized public void addMonitor(String servername, NioServerMonitor monitor) throws Exception{
		synchronized(monitors){
			monitors.add(servername, monitor);
		}
		synchronized(monitor){
			serverlist.add(servername, new Adapter_Std<String, SelectionKey>());
		}
	}

	synchronized public void removeMonitor(String servername) throws Exception{
		synchronized(monitors){
			monitors.remove(servername);
		}
		synchronized(serverlist){
			serverlist.remove(servername);
		}
	}
	synchronized public void addSelectionKey(String servername,String sessionKey,SelectionKey selectionKey) throws Exception{
		synchronized(serverlist){
			Adapter_Std<String, SelectionKey> list = serverlist.get(servername);
			synchronized(list){
				list.add(sessionKey, selectionKey);
			}
		}
	}
	synchronized public void setSelectionKey(String servername,String sessionKey,SelectionKey selectionKey) throws Exception{
		synchronized(serverlist){
			Adapter_Std<String, SelectionKey> list = serverlist.get(servername);
			synchronized(list){
				list.set(sessionKey, selectionKey);
			}
		}
	}
	synchronized public void removeSelectionKey(String servername,String sessionKey) throws Exception{
		synchronized(serverlist){
			Adapter_Std<String, SelectionKey> list = serverlist.get(servername);
			synchronized(list){
				list.remove(sessionKey);
			}
		}
	}
	
	

	synchronized public SelectionKey getSelectionKey(String serverid, String sessionKey) throws Exception{
		ArrayList<SelectionKey> monitorKeys = null;
		Adapter_Std<String, SelectionKey> serverKeys = null;
		SelectionKey skey = null;
		synchronized(monitors){
			synchronized(serverlist){
				monitorKeys = monitors.get(serverid).getSelectionKeys();
				serverKeys = serverlist.get(serverid);
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
		}
		
		return null;
	}
}
