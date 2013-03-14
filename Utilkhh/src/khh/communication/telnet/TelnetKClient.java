package khh.communication.telnet;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketException;

import com.oroinc.net.telnet.TelnetClient;

public class TelnetKClient  extends TelnetClient{
    InputStream writeinputStream =System.in;
    int readbuffersize=1024;
    int writebuffersize=1024;
    Thread receiver = null;
    Thread writer = null;
    TelnetKReadListener readlistener=null;
    
    
    Runnable receiver_run=new Runnable() {
        public void run() {
            
            try{
                    while(true){
                        Thread.sleep(0);
                        if(isConnected()){
                            int c=0;
                            byte[] buf = new byte[getReadbuffersize()];
//                            BufferedReader in = new BufferedReader(new InputStreamReader(getInputStream()));
//                            String line=null;
//                            while ((line = in.readLine()) != null) {
//                            	TelnetKReadListener listener = getReadlistener();
//                              if(listener!=null){
//                                  listener.read(line);
//                              }
//                            }
                                while((c = getInputStream().read(buf)) != -1){
                                    TelnetKReadListener listener = getReadlistener();
                                    if(listener!=null){
                                        listener.read(new String(buf,0,c));
                                    }
                                   Thread.sleep(1);
                                }
                        }
                    }
            }catch (Exception e) {
                return;
            }
            
        }
    };
    
    
    Runnable writer_run=new Runnable() {
        public void run() {
            try{
                while(true){
                    Thread.sleep(10);
                    if(isConnected()){
                        int c=0;
                        byte[] buf = new byte[getWritebuffersize()];
                            while((c = getWriteinputStream().read(buf)) != -1){
                                getOutputStream().write(buf,0,c);
                                getOutputStream().flush();
                               Thread.sleep(1);
                            }
                    }
                }
            }catch (Exception e) {
                return;
            }
        }
    };
    public TelnetKClient() {
        super();
//        init();
    }
    
    @Override
    public void connect(InetAddress arg0) throws SocketException, IOException {
        super.connect(arg0);
        init();
    }
    @Override
    public void connect(InetAddress arg0, int arg1) throws SocketException, IOException {
        super.connect(arg0, arg1);
        init();
    }
    @Override
    public void connect(InetAddress arg0, int arg1, InetAddress arg2, int arg3) throws SocketException, IOException {
        super.connect(arg0, arg1, arg2, arg3);
        init();
    }
    @Override
    public void connect(String arg0) throws SocketException, IOException {
        super.connect(arg0);
        init();
    }
    @Override
    public void connect(String arg0, int arg1) throws SocketException, IOException {
        super.connect(arg0, arg1);
        init();
    }
    @Override
    public void connect(String arg0, int arg1, InetAddress arg2, int arg3) throws SocketException, IOException {
        super.connect(arg0, arg1, arg2, arg3);
        init();
    }
    
    
    
    private void init() {
     //   System.out.println("a");
        clear();
            receiver = new Thread(receiver_run);
            receiver.start();
            
            writer = new Thread(writer_run);
            writer.start();
    }
    
    
    
    
    
    public void addReadListener(TelnetKReadListener readlistener){
        this.readlistener = readlistener;
    }
    private TelnetKReadListener getReadlistener() {
        return readlistener;
    }


    public int getReadbuffersize() {
        return readbuffersize;
    }


    public void setReadbuffersize(int readbuffersize) {
        this.readbuffersize = readbuffersize;
    }


    public int getWritebuffersize() {
        return writebuffersize;
    }


    public void setWritebuffersize(int writebuffersize) {
        this.writebuffersize = writebuffersize;
    }


    public InputStream getWriteinputStream() {
        return writeinputStream;
    }


    public void setWriteinputStream(InputStream writeinputStream) {
        this.writeinputStream = writeinputStream;
    }
    
    @Override
    public void disconnect() throws IOException {
        super.disconnect();
        clear();
    }
    @Override
    protected void finalize() throws Throwable {
        clear();
        super.finalize();
    }
    public void clear(){
        if(receiver!=null){
//            receiver.setDaemon(false);
            receiver.interrupt();
            receiver=null;
        }
        if(writer!=null){
//            writer.setDaemon(false);
           writer.interrupt();
           writer=null;
        }
    }
}
