package niotest;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import khh.communication.tcp.nio.worker.NioWorker;
import khh.debug.LogK;
public class EchoNioServerWorker extends NioWorker {
    LogK log = LogK.getInstance();
    public EchoNioServerWorker() {
        setFirestMode(MODE_FIREST_R);
    }
    @Override
    public void execute(SelectionKey selectionKey) throws Exception {
        ByteBuffer b = null;
        if ( selectionKey.isReadable() ) {
            b =  ByteBuffer.allocate(1);
            read(b, 100);
            b.clear();
            System.out.println("Server Recv: "+(char)b.get());
        }
        if(selectionKey.isWritable() && b!=null){
            b.clear();
            System.out.println("Server Send: "+(char)b.get());
            b.clear();
            write(b);
        }
    }
}
