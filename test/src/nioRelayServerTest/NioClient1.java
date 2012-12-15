package nioRelayServerTest;

import java.io.IOException;
import java.nio.channels.SelectionKey;

import khh.communication.tcp.nio.client.NioClient;
import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.debug.LogK;

public class NioClient1 {
    public static void main(String[] args) throws IOException {

    	NioActionWorker client1Worker = new NioActionWorker(NioWorker.MODE_FIREST_W) {
            LogK log = LogK.getInstance();
			@Override
			public NioActionMsg onReceiveAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception{
              log.debug("Recv Msg :("+msg.getAction()+") " + msg.getString());
              return msg;
			}
			
            @Override
            public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception {
                if(msg==null){
                    msg=new NioActionMsg();
                }
                msg.clear();
                 msg.setAction(NioActionMsg.ACTION.ECHO.getValue());
//                 msg.setAction(NioActionMsg.ACTION.GET_SERVERTIME.getValue());
                msg.setAction(NioActionMsg.ACTION.FROMTO.getValue());
              //  msg.setAction(NioActionMsg.ACTION.GET_SERVERS.getValue());
             //   msg.setAction(NioActionMsg.ACTION.GET_CLIENTS.getValue());
                msg.putParam(NioActionMsg.PARAM.FROM.getValue(), "client1");
                msg.putParam(NioActionMsg.PARAM.TO.getValue(), "client2");
                // fromto.set(ByteUtil.toByteBuffer("From:Client1(9090) ~ To: Clinet2(801)   Msg: hi~client2"));
//                msg.set("ClientRelayServer9090");
                msg.set("hey Client2");
                msg.setSuccess(true);

                sendNioActionMsg(msg);
                Thread.sleep(5000);

                return msg;
            }

        };
        NioClient client1 = new NioClient("127.0.0.1", 9090, client1Worker);
        client1.connection();
        client1.start();
    }
}
