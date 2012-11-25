package niotest;
import java.util.ArrayList;

import khh.communication.tcp.nio.server.NioServer;
import khh.communication.tcp.nio.worker.NioWorker;
public class EchoNioServer {
    public static void main(String[] args) throws Exception {
//        ArrayList<NioWorker> aa = new ArrayList<NioWorker>();
//        
//        for (int i = 0; i < 10; i++) {
//            aa . add( new EchoNioServerWorker());
//        }
//        NioServer a = new NioServer(8080,aa);
        
        NioServer a = new NioServer(9595,EchoNioServerWorker.class);
        a.setSelectorPoolSize(1);  //clinet selecting을 관장하는 쓰레드개수
        a.setWorkerPoolSize(1);   //일하는워커 쓰레드개수.
        a.start();
    }
}
