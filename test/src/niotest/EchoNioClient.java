package niotest;
import java.io.IOException;

import khh.communication.tcp.nio.client.NioClient;
public class EchoNioClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        NioClient c = new NioClient("127.0.0.1",801,new EchoNioClientWorker());
//        c.setConnectionTimeout(40);
        c.connection();
        c.start();
        
        Thread.sleep(5000);
    }
}
