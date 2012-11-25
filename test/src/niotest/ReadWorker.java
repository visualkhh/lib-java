package niotest;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import khh.communication.tcp.nio.worker.NioWorker;
import khh.debug.LogK;

public class ReadWorker extends NioWorker {
    LogK log = LogK.getInstance();
public ReadWorker() {
    // TODO Auto-generated constructor stub
}
    @Override
    public void execute(SelectionKey selectionKey) throws Exception {
        ByteBuffer b = ByteBuffer.allocate(1);
        if ( selectionKey.isReadable() ) {
            read(b, 100);
            System.out.print(b.getChar());
        }

    }

}
