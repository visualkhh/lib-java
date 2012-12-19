package khh.communication.serial;

import java.io.IOException;
import java.util.TooManyListenersException;

import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

import khh.property.util.PropertiesUtil;

public class SerialCommunicationTest implements SerialPortEventListener {
    
    
    public static void main(String[] args) throws SerialConnectionException, TooManyListenersException {
        new SerialCommunicationTest().start();
    }

    
    SerialCommunication sc;
    public void start() throws TooManyListenersException, SerialConnectionException{
		PropertiesUtil p = new PropertiesUtil();
		p.setProperty("java.library.path", "W:\\workspace\\source\\eclipse\\java\\test\\jnilib");
//		
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
                int echoMsg = sc.getBufferedreader().read();
                System.out.println(echoMsg);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
              
//              while (true) {
//                  try {
//                  String echoMsg = sc.getBufferedreader().readLine();
//                  System.out.print("Echo: " + echoMsg);
//                  //KeyBoardUtil.pess(echoMsg);
//                  //KeyBoardUtil.pess(KeyEvent.VK_ENTER);
//                    break;
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//              }
              
          break;
          
          
          
//          byte[] readBuffer = new byte[20];
//          try{
//           while(true){
//           echoMsg = br.readLine();
//           System.out.println("Echo: " + echoMsg);
//           bw.write(echoMsg,0,echoMsg.length());
//           bw.newLine();
//           bw.flush();
//           }                                
//          }catch (IOException e) {}
//            break;
//          }

      }
      
      
      
    }
}
