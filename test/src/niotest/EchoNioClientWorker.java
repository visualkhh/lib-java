package niotest;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import khh.communication.tcp.nio.worker.NioWorker;
public class EchoNioClientWorker extends NioWorker {
    public EchoNioClientWorker() {
//        setFirestMode(MODE_FIREST_W);
        setFirestMode(MODE_DISABLE);
    }
    private int i=0;
    @Override
    public void execute(SelectionKey selectionKey) throws Exception {
    	Object s=null;
    	try{
    		s = getNioCommunication().getTelegramQueue().pop();
    	if(s!=null){
    		write((String) s);
    	}}catch (Exception e) {
		}
    	
    	
    	
        if(selectionKey.isReadable()){
            ByteBuffer b = ByteBuffer.allocate(1);
            read(b, 100);
            b.clear();
            System.out.println("Client Recv: "+(char)b.get());
        }
        if(selectionKey.isWritable()&&s==null){
            Thread.sleep(5000);
            byte send = (byte)('a'+(i++));
            byte[] a = {send};
            System.out.println("Client Send: "+(char)send);
            write(a); //bytebuffer도가능
        }
    }
}
