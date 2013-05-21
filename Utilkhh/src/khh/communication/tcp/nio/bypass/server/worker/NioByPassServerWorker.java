package khh.communication.tcp.nio.bypass.server.worker;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.util.HashMap;

import khh.communication.tcp.nio.bypass.client.worker.NioByPassClientWorker;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.debug.LogK;

public class NioByPassServerWorker extends NioWorker {
	LogK log = LogK.getInstance();
	NioByPassClientWorker client;
	public NioByPassServerWorker() {
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
			if(client!=null){
				HashMap map = new HashMap<String,ByteBuffer>();
				map.put("sendData",bff );
				client.receiveTelegram(map,selectionKey);
			}

	}

	@Override
	public void receiveTelegram(HashMap<String, Object> telegram,
			SelectionKey selectionKey) throws Exception {
		if(telegram!=null && telegram.get("rcvData")!=null){
			ByteBuffer bff = (ByteBuffer) telegram.get("rcvData");
			write(bff);
		}

	}
	
	
	public NioByPassClientWorker getClient() {
		return client;
	}
	public void setClient(NioByPassClientWorker client) {
		this.client = client;
	}

}
