package nioRelayServerTest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import khh.communication.tcp.nio.client.NioClient;
import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.communication.tcp.nio.worker.msg.FromToFormater;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.debug.LogK;
import khh.util.ByteUtil;

public class NioClient1 {
    public static void main(String[] args) throws IOException {

    	NioActionWorker client1Worker = new NioActionWorker(NioWorker.MODE_FIREST_W) {
            LogK log = LogK.getInstance();
			@Override
			public NioActionMsg onReceiveAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception{
              FromToFormater fromto = new FromToFormater();
              fromto.format(msg.get());
              log.debug("Recv Msg :("+msg.getAction()+") " + fromto.getString());
              return msg;
			}
			
			@Override
			public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception{
              FromToFormater fromto = new FromToFormater();
              fromto.setFrom("client1");
              fromto.setTo("client2");
              //fromto.set(ByteUtil.toByteBuffer("From:Client1(9090) ~ To: Clinet2(801)   Msg: hi~client2"));
              fromto.set("ClientRelayServer8011");

              msg = new NioActionMsg();
              msg.setAction(ACTION.FROMTO.getValue());
              msg.setAction(ACTION.GET_SERVERS.getValue());
              msg.setAction(ACTION.GET_CLIENTS.getValue());
              msg.set(fromto);
              msg.setSuccess(true);
              sendNioActionMsg(msg);
              Thread.sleep(3000);
				
              return msg;
			}

        };
        NioClient client1 = new NioClient("127.0.0.1", 9090, client1Worker);
        client1.connection();
        client1.start();
    }
}
