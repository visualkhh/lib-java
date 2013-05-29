package arduino.game;

import khh.communication.tcp.nio.server.NioServer;
import arduino.game.tcpserver.ArduinoGameServerWorker;

public class ArduinoGame {
    NioServer server =null;
    
    
    public ArduinoGame() throws Exception {
        server = new NioServer(6565,ArduinoGameServerWorker.class);
        server.setSelectorManagerSize(1);
        server.setWorkerManagerSize(1);
        server.start();
    }
    
    
    
    
    public static void main(String[] args) throws Exception {
        new ArduinoGame();
    }
}
