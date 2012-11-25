package niotest;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import khh.communication.tcp.nio.worker.NioWorker;
public class EchoNioClientWorker extends NioWorker {
    public EchoNioClientWorker() {
        setFirestMode(MODE_FIREST_W);
    }
    private int i=0;
    @Override
    public void execute(SelectionKey selectionKey) throws Exception {
        if(selectionKey.isReadable()){
            ByteBuffer b = ByteBuffer.allocate(1);
            read(b, 100);
            b.clear();
            System.out.println("Client Recv: "+(char)b.get());
        }
        if(selectionKey.isWritable()){
            Thread.sleep(2000);
            byte send = (byte)('a'+(i++));
            byte[] a = {send};
            System.out.println("Client Send: "+(char)send);
            write(a); //bytebuffer도가능
        }
    }

}
