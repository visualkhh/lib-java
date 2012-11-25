package serial;

import java.awt.event.KeyEvent;
import java.io.*;

import javax.comm.*;

import com.khm.util.io.keyboard.KeyBoardUtil;
public class Rs232c {
    public static void main( String arg[] ) {
        try {
            CommPortIdentifier ports = CommPortIdentifier.getPortIdentifier( "COM2" );
            SerialPort port = ( SerialPort )ports.open( "RS232C", 1000 );
            port.setSerialPortParams( 9600,
                SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE );
            port.setFlowControlMode( SerialPort.FLOWCONTROL_NONE );
          
            
            
            BufferedReader  br = new BufferedReader(new InputStreamReader(port.getInputStream()));
            BufferedWriter  bw = new BufferedWriter(new OutputStreamWriter(port.getOutputStream()));
            
            byte[] readBuffer = new byte[20];
            try {
                while (true) {
                    String echoMsg = br.readLine();
                    System.out.print("Echo: " + echoMsg);
                    KeyBoardUtil.pess(echoMsg);
                    KeyBoardUtil.pess(KeyEvent.VK_ENTER);
                    Thread.sleep(100);
                }
            } catch (IOException e) {
            }            
            
            
       /*     try {
                int c=0;
                while ((c=br.read())!=-1) {
            //        System.out.print((char)c);
                    System.out.format("%02X",(byte)c);
                    //KeyBoardUtil.pess(c);
                    Thread.sleep(100);
                }
            } catch (IOException e) {
            }       
         */   
            
            
         /*   OutputStream out = port.getOutputStream();
            String msg = "Hello";
            out.write( msg.getBytes() );
            out.flush();
            out.close();
            */
            port.close();
        }
        catch( Exception e ) {
            System.out.println( "Error:" + e.getMessage() );
        }
    }
}
