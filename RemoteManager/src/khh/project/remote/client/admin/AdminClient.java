package khh.project.remote.client.admin;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import khh.communication.tcp.nio.client.NioClient;
import khh.gui.component.frame.FrameFrame;
import khh.gui.util.GUIUtil;
import khh.project.remote.client.admin.communication.AdminClientActionWorker;
import khh.project.remote.client.admin.monitor.AdminMonitor;
import khh.schedule.Scheduler;
import khh.util.Util;

public class AdminClient extends FrameFrame{
	public static enum VIEWID{
		IMAGE_COMPONENT("IMAGE_COMPONENT"), IP_TEXT("IP_TEXT"), PORT_TEXT("PORT_TEXT"), LOGIN_BTN(
				"LOGIN_BTN"), STATE_LABEL("STATE_LABEL"), CENTER_PANEL("CENTER_PANEL"), ETC(
				"ETCBTN");
		String id;

		VIEWID(String id){
			this.id = id;
		}

		public String getValue(){
			return this.id;
		}
	};

	public static enum ACTION{
		CONNECTION(Util.getNextNumber()), ;
		int id;

		ACTION(int id){
			this.id = id;
		}

		public int getValue(){
			return this.id;
		}
	};

	public AdminClient(String title){
		super(title);
	}

	@Override
	public void onBeforeProcess(){
		Scheduler s = new Scheduler();
		try{
			s.schedule("a", new Runnable(){
				@Override
				public void run(){
					Label l = (Label)context
							.getComponent(AdminClient.VIEWID.STATE_LABEL.getValue());
					l.setText(client + "");
				}
			}, 10, 1000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onViewSetting(){
		GUIUtil.setSize(getContext(), 500, 500); // default size...

		setLayout(new BorderLayout());
		// try {
		// Image i;
		// i = ImageUtil.getImage(new File("c:\\red_a.png"));
		// setIconImage(i);
		// } catch (IOException e) {
		// }
		Panel topPanel = new Panel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		topPanel.add(new Label("ServerInfo:"));

		TextField ip = new TextField(15);
		ip.setText("127.0.0.1");
		ip.setName(VIEWID.IP_TEXT.getValue());
		topPanel.add(ip);

		TextField port = new TextField(4);
		port.setText("9595");
		port.setName(VIEWID.PORT_TEXT.getValue());
		topPanel.add(port);

		Button btnm = new Button("ServerLogin");
		btnm.setName(VIEWID.LOGIN_BTN.getValue());
		topPanel.add(btnm);

		Label state = new Label("[STATE]");
		state.setName(VIEWID.STATE_LABEL.getValue());
		topPanel.add(state);

		// center
		Panel centerPanel = new Panel(new GridLayout(10, 10));
		centerPanel.setName(VIEWID.CENTER_PANEL.getValue());
		centerPanel.removeAll();
		this.add(centerPanel, BorderLayout.CENTER);

		topPanel.setBackground(new Color(255, 0, 0));
		this.add(topPanel, BorderLayout.NORTH);

		this.add(new Button("RefreshGetClient"), BorderLayout.SOUTH);
		setVisible(true);
	}

	@Override
	public void onDataSetting(){
	}

	NioClient client;

	@Override
	public void onAddListener(){
		((Button)getComponent(VIEWID.LOGIN_BTN.getValue())).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				onAction(ACTION.CONNECTION.getValue());
			}
		});
	}

	@Override
	public void onAction(int gb, Object o){

		try{

			if(ACTION.CONNECTION.getValue() == gb){
				TextField ip = (TextField)getComponent(VIEWID.IP_TEXT.getValue());
				TextField port = (TextField)getComponent(VIEWID.PORT_TEXT.getValue());
				if(client != null){
					client.connnection();
				}else{
					client = new NioClient(ip.getText(), Integer.parseInt(port.getText()),
							AdminClientActionWorker.class);
					client.start();
				}

			}

		}catch(Exception e){
		}

	}

	protected NioClient getClient(){
		return client;
	}

	public static void main(String[] args){
		new AdminClient("aa").flow();
	}
	
	@Override
	public void dispose(){
		super.dispose();
		try{
			getClient().stop();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
