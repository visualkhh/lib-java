package webservertest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    public void serverStanby() {
        try {
            ServerSocket listener = new ServerSocket(9090);
            
            Socket connectionSocket;
            
            while((connectionSocket =listener.accept())!=null){
                read(connectionSocket);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void read(Socket socket) throws IOException{
        BufferedReader in=null;
        InputStreamReader is = new InputStreamReader(socket.getInputStream());
        in = new BufferedReader(is);
        
        String data =  in.readLine();
         while((data =  in.readLine())!=null){
             System.out.println(data);
         }
        System.out.println("end");
    }
    
    public static void main(String[] args) {
        new WebServer().serverStanby();
    }
}
