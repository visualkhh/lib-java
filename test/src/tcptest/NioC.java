package tcptest;

import java.awt.Point;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import khh.communication.tcp.nio.worker.NioWorker;
import khh.debug.LogK;
import khh.io.mouse.util.MouseUtil;

public class NioC extends NioWorker {

	LogK log = LogK.getInstance();
	public NioC() {
		setFirestMode(MODE_FIREST_W);
	}
	@Override
	public void execute(SelectionKey selectionKey) throws Exception {
		log.debug("B: isRead:"+selectionKey.isReadable()+"       isWriterble:"+selectionKey.isWritable());
		
		if(selectionKey.isReadable()){
			ByteBuffer a = ByteBuffer.allocate(1);
			int s = read(a, 1000);
			char c = ((char)a.get(0));
			log.debug("Read :"+c+" size:"+s);
			Point p = MouseUtil.getPoint();
			p.x += Integer.parseInt((""+c));
			p.y += Integer.parseInt((""+c));
			MouseUtil.move(p);
		}
		
		if(selectionKey.isWritable()){
			write("9");
			Thread.sleep(1000);
		}
	}

}
