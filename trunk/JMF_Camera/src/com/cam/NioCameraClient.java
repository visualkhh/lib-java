package com.cam;

import java.nio.channels.SelectionKey;
import java.util.HashMap;

import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;

public class NioCameraClient extends NioActionWorker {
    public NioCameraClient() {
        super(MODE_FIREST_W);
    }

    @Override
    public void receiveTelegram(HashMap<String, Object> telegram, SelectionKey selectionKey) throws Exception {
        byte[] img = (byte[]) telegram.get("img");
        if(null!=img){
            NioActionMsg msg = new NioActionMsg();
            msg.set(img);
            msg.setSuccess(true);
            sendNioActionMsg(msg);
        }

    }

    @Override
    public NioActionMsg onReceiveAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
