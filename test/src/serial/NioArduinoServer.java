package serial;

import java.nio.channels.SelectionKey;

import khh.communication.tcp.nio.worker.NioWorker;

public class NioArduinoServer extends NioWorker{
    public NioArduinoServer() {
        setFirestMode(MODE_ONLY_TELEGRAM);
    }
    @Override
    public void execute(SelectionKey selectionKey) throws Exception {
        byte[] senddata = new byte[1];
        
        try{
            senddata[0] = (Byte) getNioCommunication().getTelegramQueue().pop();
        }catch (Exception e) {
            return;
        }
        write(senddata);
    }

}
