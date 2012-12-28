package khh.project.remote.client.admin.monitor;

import java.awt.Label;

import khh.communication.tcp.nio.client.NioClient;
import khh.project.remote.client.admin.AdminClient;

public class AdminMonitor extends Thread{
	NioClient client=null;
	AdminClient context = null;
	public AdminMonitor(AdminClient context,NioClient client){
		this.context = context;
		this.client = client;
	}
	@Override
	public void run(){
			try{
				sleep(1000);
				Label l = (Label)this.context.getComponent(AdminClient.VIEWID.STATE_LABEL.getValue());
				l.setText(client+"");
				System.out.println(client);
				l.repaint();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
	}
}
