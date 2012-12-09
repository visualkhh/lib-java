package nioRelayServerTest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import khh.communication.tcp.nio.client.NioClient;
import khh.communication.tcp.nio.protocol.NioMsg;
import khh.communication.tcp.nio.relay.format.FromToRelayFormater;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.debug.LogK;
import khh.util.ByteUtil;

public class NioClient1 {
    public static void main(String[] args) throws IOException {

        NioWorker client1Worker = new NioWorker(NioWorker.MODE_FIREST_W) {
            LogK log = LogK.getInstance();
            public void execute(SelectionKey selectionKey) throws Exception {
                if ( selectionKey.isWritable() ) {
                    FromToRelayFormater fromto = new FromToRelayFormater();
                    fromto.setFrom("client1");
                    fromto.setTo("client2");
                    fromto.set(ByteUtil.toByteBuffer("From:Client1(9090) ~ To: Clinet2(801)   Msg: hi~client2"));
                    ByteBuffer data = fromto.unformat();

                    NioMsg msg = new NioMsg();
                    msg.set(data);
                    msg.setSuccess(true);
                    write(msg.unformat());
                    Thread.sleep(3000);
                }
                
                if(selectionKey.isReadable()){
                    NioMsg msg = receiveNioMsg();
                    FromToRelayFormater fromto = new FromToRelayFormater();
                    fromto.format(msg.get());
                    log.debug("Recv Msg : "+fromto.getString());
                }
            }

        };
        NioClient client1 = new NioClient("192.168.0.2", 9090, client1Worker);
        client1.connection();
        client1.start();
    }
}
