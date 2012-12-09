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

public class NioClient2 {
    public static void main(String[] args) throws IOException, InterruptedException {

        NioWorker client1Worker = new NioWorker(NioWorker.MODE_FIREST_W) {
            LogK log = LogK.getInstance();
            public void execute(SelectionKey selectionKey) throws Exception {
                if ( selectionKey.isWritable() ) {
                    FromToRelayFormater fromto = new FromToRelayFormater();
                    fromto.setFrom("client2");
                    fromto.setTo("client1");
                    fromto.set(ByteUtil.toByteBuffer("From:Client2(801) ~ To: Clinet1(9090)   Msg: hi~client1"));
                    ByteBuffer data = fromto.unformat();

                    NioMsg msg = new NioMsg();
                    msg.set(data);
                    msg.setSuccess(true);
                    write(msg.unformat());
                    Thread.sleep(3000);
                }

                if ( selectionKey.isReadable() ) {
                    NioMsg msg = receiveNioMsg();
                    FromToRelayFormater fromto = new FromToRelayFormater();
                    fromto.format(msg.get());
                    log.debug("Recv Msg : " + fromto.getString());
                }

            }
        };
        NioClient client2 = new NioClient("192.168.0.2", 801, client1Worker);
        client2.connection();
        client2.start();

    }
}
