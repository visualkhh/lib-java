package arduino.game.tcpserver;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.TooManyListenersException;

import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

import khh.communication.serial.SerialCommunication;
import khh.communication.serial.SerialParameters;
import khh.communication.tcp.nio.worker.NioWorker;

public class ArduinoGameServerWorker  extends NioWorker implements SerialPortEventListener{

    SerialCommunication sc;
  
    public ArduinoGameServerWorker() {
        sc = new SerialCommunication();
        SerialParameters params = new SerialParameters();
        params.setPortName("COM3");
        sc.setSerialParameters(params);
        try{
            sc.addEventListener(this);
            sc.openConnection();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void execute(SelectionKey selectionKey) throws Exception {
        if(selectionKey.isReadable()){
            byte[] senddata = new byte[1];
            try{
               int i = read(senddata);
               if(i==1){
                   sc.getBufferedwriter().write(senddata[0]);
                   sc.getBufferedwriter().flush();
               }
            }catch (Exception e) {
                return;
            }
        }

        
    }

    @Override
    public void receiveTelegram(HashMap<String, Object> telegram, SelectionKey selectionKey) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void serialEvent(SerialPortEvent e) {
        switch (e.getEventType()) {
            case SerialPortEvent.BI:   // "\n--- BREAK RECEIVED ---\n"  // Break interrupt
            case SerialPortEvent.OE:     // Overrun error
            case SerialPortEvent.FE:      // Framing error
            case SerialPortEvent.PE:     // Parity error. 
            case SerialPortEvent.CD:     // Carrier detect
            case SerialPortEvent.CTS:   // Clear to send
            case SerialPortEvent.DSR:   // Data set ready
            case SerialPortEvent.RI:      // Ring indicator. 
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                  break;             // Output buffer is empty
          case SerialPortEvent.DATA_AVAILABLE:
              try {
                byte[] b ={(byte)sc.getBufferedreader().read()};
                write(b);
                //System.out.println(echoMsg);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
          break;
      }
        
    }

}
