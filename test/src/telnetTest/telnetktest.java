package telnetTest;

import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import com.kdn.util.conversion.ConversionUtil;
import com.kdn.util.telnet.TelnetKClient;
import com.kdn.util.telnet.TelnetKReadListener;
import com.kdt.util.schedule.Scheduler;

public class telnetktest {
    TelnetKReadListener listener  =new TelnetKReadListener() {
        @Override
        public void read(String msg) {
            try{
            System.out.print(msg);
            }catch (Exception e) {
            }
        }
    };
    
    
    TelnetKClient k = null;
    public void start() throws Exception{
        String addr = "168.78.203.138";
        int port=23;
        k = new TelnetKClient();
        k.connect(addr,port);
        k.addReadListener(listener);
    }
    public static void main(String[] args) throws Exception {
        new telnetktest().start(); 
    }
}
