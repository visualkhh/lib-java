package khh.communication.tcp.nio.bypass.client.worker;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.util.HashMap;

import khh.communication.tcp.nio.bypass.server.worker.NioByPassServerWorker;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.debug.LogK;

public class NioByPassClientWorker extends NioWorker {
	LogK log = LogK.getInstance();
	NioByPassServerWorker server;
	public NioByPassClientWorker() {
		super(MODE_FIREST_R);
		setReadTimeout(1000);
	}
	@Override
	public void execute(SelectionKey selectionKey) throws Exception {
		ByteBuffer bff = ByteBuffer.allocate(10000);
		try{
			while(selectionKey.isReadable()){
				byte[] b = new byte[1];
				int i = read(b);
				if(i>=1){
					bff.put(b);
				} 
			}
		}catch (Exception e) {
		}
		
		bff.flip();
		if(server!=null){
			HashMap map = new HashMap<String,ByteBuffer>();
			map.put("rcvData",bff );
			server.receiveTelegram(map,selectionKey);
		}
	}

	@Override
	public void receiveTelegram(HashMap<String, Object> telegram,
			SelectionKey selectionKey) throws Exception {
		if(telegram!=null && telegram.get("sendData")!=null){
			ByteBuffer bff = (ByteBuffer) telegram.get("sendData");
			write(bff);
		}

	}

	public NioByPassServerWorker getServer() {
		return server;
	}

	public void setServer(NioByPassServerWorker server) {
		this.server = server;
	}

}
