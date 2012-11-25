package niotest;
import java.io.IOException;
import khh.communication.tcp.nio.client.NioClient;
public class EchoNioClient {
    public static void main(String[] args) throws IOException {
        NioClient c = new NioClient("127.0.0.1",8080,new EchoNioClientWorker());
        c.connection();
        c.start();
    }
}
