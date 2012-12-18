package niotest;
import java.io.IOException;

import khh.communication.tcp.nio.client.NioClient;
public class EchoNioClient {
    public static void main(String[] args) throws Exception {
        final NioClient c = new NioClient("127.0.0.1",9595,EchoNioClientWorker.class);
//        c.setConnectionTimeout(40);
        c.start();
        
        
        new Thread(){
        	public void run() {
        		int i=0;
        		while(true){
        			try{
						sleep(1000);
					}catch(InterruptedException e){
					}
        			c.pushTelegram(i+"");
        			i++;
        			if(i>5){
        				return;
        			}
        		}
        	};
        }.start();
    }
}
