package tcptest;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import khh.communication.tcp.nio.worker.NioWorker;
import khh.debug.LogK;

public class NioS extends NioWorker {

	LogK log = LogK.getInstance();
	public NioS() {
		setFirestMode(MODE_FIREST_R);
	}
	@Override
	public void execute(SelectionKey selectionKey) throws Exception {
		log.debug("B: isRead:"+selectionKey.isReadable()+"       isWriterble:"+selectionKey.isWritable());
		ByteBuffer a=null;
		if(selectionKey.isReadable()){
			a = ByteBuffer.allocate(1);
			int s = read(a, 1000);
			char c = ((char)a.get(0));
			log.debug("Read :"+c+" size:"+s);
		}
		
		if(selectionKey.isWritable() &&  a!=null){
			write(""+a.get(0));
			Thread.sleep(2000);
		}
	}

}
