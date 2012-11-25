package niotest;

import khh.communication.tcp.nio.server.NioServer;


public class ReadNio {
    public static void main(String[] args) throws Exception {
        NioServer a = new NioServer(8080,ReadWorker.class);
        a.start();
    }
}
