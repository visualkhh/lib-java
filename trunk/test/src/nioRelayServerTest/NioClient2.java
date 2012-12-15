package nioRelayServerTest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import khh.communication.tcp.nio.client.NioClient;
import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.communication.tcp.nio.worker.msg.NioParamMsg;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.debug.LogK;
import khh.util.ByteUtil;

public class NioClient2 {
    public static void main(String[] args) throws IOException, InterruptedException {

    	NioActionWorker client1Worker = new NioActionWorker(NioWorker.MODE_FIREST_W) {
            LogK log = LogK.getInstance();

			@Override
			public NioActionMsg onReceiveAction(NioActionMsg msg, SelectionKey selectionKey)throws Exception{
                NioParamMsg fromto = new NioParamMsg();
                fromto.format(msg.get());
                log.debug("Recv Msg :("+msg.getAction()+") " + fromto.getString());
                return msg;
			}

			@Override
			public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey)throws Exception{
              NioParamMsg fromto = new NioParamMsg();
              fromto.setFrom("client2");
              fromto.setTo("client1");
              fromto.set(ByteUtil.toByteBuffer("From:Client2(801) ~ To: Clinet1(9090)   Msg: hi~client1"));
              ByteBuffer data = fromto.unformat();

              msg = new NioActionMsg();
              msg.set(data);
              msg.setAction(ACTION.FROMTO.getValue());
              msg.setSuccess(true);
              sendNioActionMsg(msg);
              Thread.sleep(3000);
              return msg;
			}
        };
        NioClient client2 = new NioClient("127.0.0.1", 801, client1Worker);
        client2.connection();
        client2.start();

    }
}
