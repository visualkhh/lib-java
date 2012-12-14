package nioRelayServerTest;

import java.util.ArrayList;

import khh.communication.tcp.nio.relay.server.RelayServer;

public class RelayServerTest {
    public static void main(String[] args) throws Exception  {
        ArrayList<Integer> serverPort = new ArrayList<Integer> ();
        serverPort.add(9090);
        serverPort.add(801);
        
        RelayServer f = new RelayServer();
        f.setPort(serverPort);
        f.start();
    }
}
