package khh.test.nio;

import java.nio.channels.SelectionKey;
import java.util.HashMap;

import khh.communication.tcp.nio.server.NioServer;
import khh.communication.tcp.nio.worker.NioWorker;

public class NioTestServer extends NioWorker {
	public NioTestServer() {
		setFirestMode(MODE_FIREST_R);
	}

	@Override
	public void execute(SelectionKey selectionKey) throws Exception {
		String read=null;
		if(selectionKey.isReadable()){
			read = readString();
		}
		Thread.sleep(2000);
		if(selectionKey.isWritable() && null!=read){
			write(read);
		}
	}

	@Override
	public void receiveTelegram(HashMap<String, Object> telegram, SelectionKey selectionKey) throws Exception {
	}
	
	public static void main(String[] args) throws Exception {
		int port = 9090;
		NioServer server = new NioServer(port,NioTestServer.class);
		server.setSelectorManagerSize(1);
		server.setWorkerManagerSize(1);
		server.start();
	}
}
