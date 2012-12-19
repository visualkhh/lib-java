package serial;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.util.TooManyListenersException;

import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

import khh.communication.serial.SerialCommunication;
import khh.communication.serial.SerialConnectionException;
import khh.communication.serial.SerialParameters;
import khh.communication.tcp.nio.server.NioServer;
import khh.communication.tcp.nio.worker.NioWorker;

public class SerialToTcpCommunication implements SerialPortEventListener {
    
    public static void main(String[] args) throws Exception {
        new SerialToTcpCommunication().start();
    }

    
    NioServer server ;
    SerialCommunication sc;
    public void start() throws Exception{
        server = new NioServer(9090,NioArduinoServer.class);
        server.setWorkerManagerSize(1);
        server.setSelectorManagerSize(1);
        server.start();
        
        sc = new SerialCommunication();
        SerialParameters params = new SerialParameters();
        params.setPortName("COM5");
        sc.setSerialParameters(params);
        sc.addEventListener(this);
        sc.openConnection();
    }
    public void serialEvent(SerialPortEvent e) {

   // Determine type of event.
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
          // Data available at the serial port. 
          // Read data until -1 is returned. If \r is received substitute
          // \n for correct newline handling.
          case SerialPortEvent.DATA_AVAILABLE:
              try {
                byte echoMsg = (byte)sc.getBufferedreader().read();
                System.out.println(echoMsg);
                server.pushTelegram(echoMsg);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
          break;
      }
      
      
      
    }
}
