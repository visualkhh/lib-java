package khh.communication.serial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

/*
 -Djava.library.path=W:\workspace\source\eclipse\java\test\jnilib
 package serial;

 
 
 
 */
public class SerialCommunication {
    private SerialParameters serialParameters=null;
    
    private OutputStream outputStream;
    private InputStream inputStreame;
    private BufferedWriter bufferedwriter;
    private BufferedReader bufferedreader;
    
    private CommPortIdentifier portId;
    private SerialPort serialPort;
    private boolean open=false;
    ArrayList<SerialPortEventListener> listenerList = new ArrayList<SerialPortEventListener>();
    
    public SerialCommunication() {
    }
    public SerialCommunication(SerialParameters parameters) {
        this.serialParameters = parameters;
    }
    
    public void setSerialParameters(SerialParameters parameters){
        this.serialParameters = parameters;
    }
    
    
    
    
    

    public SerialParameters getSerialParameters() {
        return serialParameters;
    }
    public OutputStream getOutputStream() {
        return outputStream;
    }
    public InputStream getInputStreame() {
        return inputStreame;
    }
    
    public BufferedWriter getBufferedwriter() {
        return bufferedwriter;
    }
    public BufferedReader getBufferedreader() {
        return bufferedreader;
    }
    public CommPortIdentifier getPortId() {
        return portId;
    }
    public SerialPort getSerialPort() {
        return serialPort;
    }
    public ArrayList<SerialPortEventListener> getListenerList() {
        return listenerList;
    }
    /**
    Sets the connection parameters to the setting in the parameters object.
    If set fails return the parameters object to origional settings and
    throw exception.
    */
    private void setConnectionParameters() throws SerialConnectionException {
    // Save state of parameters before trying a set.
    int oldBaudRate = serialPort.getBaudRate();
    int oldDatabits = serialPort.getDataBits();
    int oldStopbits = serialPort.getStopBits();
    int oldParity   = serialPort.getParity();
    int oldFlowControl = serialPort.getFlowControlMode();

    // Set connection parameters, if set fails return parameters object
    // to original state.
    try {
        serialPort.setSerialPortParams(serialParameters.getBaudRate(),
                      serialParameters.getDatabits(),
                      serialParameters.getStopbits(),
                      serialParameters.getParity());
    } catch (UnsupportedCommOperationException e) {
        serialParameters.setBaudRate(oldBaudRate);
        serialParameters.setDatabits(oldDatabits);
        serialParameters.setStopbits(oldStopbits);
        serialParameters.setParity(oldParity);
        throw new SerialConnectionException("Unsupported parameter");
    }

    // Set flow control.
    try {
        serialPort.setFlowControlMode(serialParameters.getFlowControlIn() 
                       | serialParameters.getFlowControlOut());
    } catch (UnsupportedCommOperationException e) {
        throw new SerialConnectionException("Unsupported flow control");
    }
    }
    
    
    
    
    public void openConnection() throws SerialConnectionException {
        openConnection(this.serialParameters);
    }
    
    public void openConnection(SerialParameters parameters) throws SerialConnectionException {
        // Obtain a CommPortIdentifier object for the port you want to open.
        try {
            portId = 
             CommPortIdentifier.getPortIdentifier(parameters.getPortName());
        } catch (NoSuchPortException e) {
            throw new SerialConnectionException(e.getMessage());
        }

        // Open the port represented by the CommPortIdentifier object. Give
        // the open call a relatively long timeout of 30 seconds to allow
        // a different application to reliquish the port if the user 
        // wants to.
        try {
            serialPort = (SerialPort)portId.open("SerialCommunication", 30000);
        } catch (PortInUseException e) {
            throw new SerialConnectionException(e.getMessage());
        }

        // Set the parameters of the connection. If they won't set, close the
        // port before throwing an exception.
        try {
            setConnectionParameters();
        } catch (SerialConnectionException e) { 
            serialPort.close();
            throw e;
        }

        // Open the input and output streams for the connection. If they won't
        // open, close the port before throwing an exception.
        try {
            outputStream = serialPort.getOutputStream();
            inputStreame = serialPort.getInputStream();
            bufferedreader = new BufferedReader(new InputStreamReader(inputStreame));
            bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            
        } catch (IOException e) {
            serialPort.close();
            throw new SerialConnectionException("Error opening i/o streams");
        }


        // Add this object as an event listener for the serial port.
        try {
            for (int i = 0; i <listenerList.size() ; i++) {
                serialPort.addEventListener(listenerList.get(i));//여기
            }
        } catch (TooManyListenersException e) {
            serialPort.close();
            throw new SerialConnectionException("too many listeners added");
        }

        // Set notifyOnDataAvailable to true to allow event driven input.
        serialPort.notifyOnDataAvailable(true);

        // Set notifyOnBreakInterrup to allow event driven break handling.
        serialPort.notifyOnBreakInterrupt(true);

        // Set receive timeout to allow breaking out of polling loop during
        // input handling.
        try {
            serialPort.enableReceiveTimeout(30);
        } catch (UnsupportedCommOperationException e) {
        }

        // Add ownership listener to allow ownership event handling.
        //portId.addPortOwnershipListener(this);

        open = true;
        }
    
    /**
    Send a one second break signal.
    */
    public void sendBreak() {
    serialPort.sendBreak(1000);
    }

    /**
    Reports the open status of the port.
    @return true if port is open, false if port is closed.
    */
    public boolean isOpen() {
    return open;
    }
    
    
    
    public void addEventListener(SerialPortEventListener listener) throws TooManyListenersException{
        listenerList.add(listener);
        if(serialPort!=null){
            serialPort.addEventListener(listener);
        }
    }
    public void removeEventListener(){
        listenerList.clear();
        if(serialPort!=null){
            serialPort.removeEventListener();
        }
    }
    
    
    
    
    /*
    public void ownershipChange(int type) {
        if ( type == CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED ) {
            PortRequestedDialog prd = new PortRequestedDialog(parent);
        }
    }*/
    
    
//    public void serialEvent(SerialPortEvent e) {
//        // Create a StringBuffer and int to receive input data.
//        StringBuffer inputBuffer = new StringBuffer();
//        int newData = 0;
//
//        // Determine type of event.
//        switch (e.getEventType()) {
//
//            // Read data until -1 is returned. If \r is received substitute
//            // \n for correct newline handling.
//            case SerialPortEvent.DATA_AVAILABLE:
//                while (newData != -1) {
//                    try {
//                        newData = is.read();
//                    if (newData == -1) {
//                    break;
//                    }
//                    if ('\r' == (char)newData) {
//                    inputBuffer.append('\n');
//                    } else {
//                        inputBuffer.append((char)newData);
//                    }
//                    } catch (IOException ex) {
//                        System.err.println(ex);
//                        return;
//                    }
//                }
//
//            // Append received data to messageAreaIn.
//            messageAreaIn.append(new String(inputBuffer));
//            break;
//
//            // If break event append BREAK RECEIVED message.
//            case SerialPortEvent.BI:
//            messageAreaIn.append("\n--- BREAK RECEIVED ---\n");
//        }
//
//        }   

/*------------------------------*/
    //sc.addEventListener(this);
//    public void serialEvent(SerialPortEvent e) {
//
//        // Determine type of event.
//           switch (e.getEventType()) {
//
//                 case SerialPortEvent.BI:   // "\n--- BREAK RECEIVED ---\n"  // Break interrupt
//                 case SerialPortEvent.OE:     // Overrun error
//                 case SerialPortEvent.FE:      // Framing error
//                 case SerialPortEvent.PE:     // Parity error. 
//                 case SerialPortEvent.CD:     // Carrier detect
//                 case SerialPortEvent.CTS:   // Clear to send
//                 case SerialPortEvent.DSR:   // Data set ready
//                 case SerialPortEvent.RI:      // Ring indicator. 
//                 case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
//                       break;             // Output buffer is empty
//               // Data available at the serial port. 
//               // Read data until -1 is returned. If \r is received substitute
//               // \n for correct newline handling.
//               case SerialPortEvent.DATA_AVAILABLE:
//                   while (true) {
//                       try {
//                       String echoMsg = sc.getBufferedreader().readLine();
//                       System.out.print("Echo: " + echoMsg);
//                       KeyBoardUtil.pess(echoMsg);
//                         KeyBoardUtil.pess(KeyEvent.VK_ENTER);
//                         break;
//                     } catch (Exception e1) {
//                         e1.printStackTrace();
//                     }
//                   }
//                   
//               break;
//               
//
//           }
//           
//           
//           
//         }

    public void close(){
        try{
        if(serialPort!=null){
            serialPort.close();
        }
        }catch (Exception e) {
        }
        
        try{
        if(inputStreame!=null){
            inputStreame.close();
        }
        }catch (Exception e) {
        }
        
        try{
        if(outputStream!=null){
            outputStream.close();
        }
        }catch (Exception e) {
        }
        
        try{
        if(bufferedreader!=null){
            bufferedreader.close();
        }
        }catch (Exception e) {
        }
        try{
        if(bufferedwriter!=null){
            bufferedwriter.close();
        }
        }catch (Exception e) {
        }
    }
    
    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
    
    
    
    
    public static Enumeration<CommPortIdentifier> getPortList(){
        Enumeration<CommPortIdentifier> ports = CommPortIdentifier.getPortIdentifiers(); 
//        while (ports.hasMoreElements()) { 
//            CommPortIdentifier port = 
//              (CommPortIdentifier)ports.nextElement(); 
//            String type; 
//            switch (port.getPortType()) { 
//              case CommPortIdentifier.PORT_PARALLEL: 
//                type = "Parallel"; 
//                break; 
//              case CommPortIdentifier.PORT_SERIAL: 
//                type = "Serial"; 
//                break; 
//              default: /// Shouldn't happen 
//                type = "Unknown"; 
//                break; 
//            } 
//            System.out.println(port.getName() + ": " + type); 
//          } 
        
        return ports;
    }
}
