package telnetTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.swing.plaf.SliderUI;

import com.kdn.util.property.PropertiesUtil;
import com.kdn.util.property.PropertyUtil;
import com.kdt.util.converting.Converting;
import com.kdt.util.schedule.Scheduler;
import com.oroinc.net.telnet.TelnetClient;
import com.oroinc.net.telnet.TelnetCommand;
import com.oroinc.net.telnet.TelnetOption;

public class telnetTest {
    TelnetClient client = new TelnetClient();
    InputStream is =null;
    OutputStream out =null;
    
    
    Scheduler sc = new Scheduler();
    
    public void connection(String addr,int port) throws Exception{
        client.connect(addr,port);
        client.setDefaultPort(23);
//        client.setDefaultTimeout(5000);
//        client.setSoTimeout(5000);

        is = client.getInputStream();
        out =client.getOutputStream();
        
        Runnable in_run =new Runnable(){
            @Override
            public void run() {
                int c=0;
                byte[] buf = new byte[1024*5];
                    try {
                        while((c = is.read(buf)) != -1){
                            String a = new String(buf,0,c);
                            System.out.print(a);
                           Thread.sleep(1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        };
//        Scheduler s = new Scheduler();
//        s.schedule("a", in_run, 10, 1000);
       new Thread(in_run).start();
       
       
       
       Runnable out_run = new Runnable() {
        @Override
        public void run() {
            byte[] buf = new byte[1024*5];
            while (true) {
                int c=0;
                try {
                    while((c = System.in.read(buf)) != -1){
                        out.write(buf,0,c);
                        out.flush();
                        Thread.sleep(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    
    new Thread(out_run).start();
    
        
    }
    public static void main(String[] args) throws Exception {
        String addr = "168.78.203.138";
        int port=23;
        new telnetTest().connection(addr, port);
        
    }
}
