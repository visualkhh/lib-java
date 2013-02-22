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

import khh.gui.component.frame.FrameFrame;
import khh.listener.action.ActionListener;
import khh.project.remote.client.admin.AdminClient.VACTION;
public class AdminClientDesigner extends ActionListener{
	public static enum VIEWID{
		IMAGE_COMPONENT("IMAGE_COMPONENT"), 
		ID_TEXT("ID_TEXT"), 
		IP_TEXT("IP_TEXT"), 
		PORT_TEXT("PORT_TEXT"), 
		LOGIN_BTN("LOGIN_BTN"), 
		STATE_LABEL("STATE_LABEL"), 
		LEFT_PANEL("LEFT_PANEL"), 
		CENTER_PANEL("CENTER_PANEL"), 
		REFRESH_BTN("REFRESH_BTN"), 
		ETC("ETCBTN");
		String id;

		VIEWID(String id){
			this.id = id;
		}

		public String getValue(){
			return this.id;
		}
	};
	
	String ip_param;
	int port_param;
	String id_param;
	FrameFrame context=null;
	public AdminClientDesigner(FrameFrame context){
		this.context = context;
	}
	
	public void drawDesign(){
		ip_param="127.0.0.1";
		port_param=9595;
		id_param="hhk";
		
		
		//GUIUtil.setSize(getContext(), 700, 500); // default size...
		context.setSize(700,500);

		context.setLayout(new BorderLayout());
		// try {
		// Image i;
		// i = ImageUtil.getImage(new File("c:\\red_a.png"));
		// setIconImage(i);
		// } catch (IOException e) {
		// }
		Panel topPanel = new Panel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		TextField id = new TextField(15);
		topPanel.add(new Label("ID"));
		id.setText(id_param);
		id.setName(VIEWID.ID_TEXT.getValue());
		topPanel.add(id);
		
		topPanel.add(new Label("S"));

		TextField ip = new TextField(15);
		ip.setText(ip_param);
		ip.setName(VIEWID.IP_TEXT.getValue());
		topPanel.add(ip);

		TextField port = new TextField(4);
		port.setText(port_param+"");
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
		context.add(centerPanel, BorderLayout.CENTER);

		topPanel.setBackground(new Color(255, 0, 0));
		context.add(topPanel, BorderLayout.NORTH);

		
		
		//serverlist
		Panel leftPanel = new Panel();
		leftPanel.setName(VIEWID.LEFT_PANEL.getValue());
		leftPanel.setLayout(new GridLayout(10,1));
		leftPanel.setBackground(new Color(0, 255, 0));
		leftPanel.add(new Label("SERVERLIST"));
		context.add(leftPanel, BorderLayout.WEST);
		
		Button refreshbtn = new Button("Refresh");
		refreshbtn.setName(VIEWID.REFRESH_BTN.getValue());
		context.add(refreshbtn, BorderLayout.SOUTH);
		context.setVisible(true);
	}
	
	
	public void drawServerList(String[] serverList){
		Panel leftPanel = (Panel)context.getComponent(VIEWID.LEFT_PANEL.getValue());
		leftPanel.removeAll();
		leftPanel.add(new Label("SERVERLIST"+System.currentTimeMillis()));
		for(int i = 0; i < serverList.length; i++){
			String string = serverList[i];
			leftPanel.add(new Button(string));
		}
		leftPanel.validate();
	}
	
	
	public void onAddListener(){
		((Button)context.getComponent(VIEWID.LOGIN_BTN.getValue())).addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				context.onAction(VACTION.CONNECTION.getValue());
			}
		});
		((Button)context.getComponent(VIEWID.REFRESH_BTN.getValue())).addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				context.onAction(VACTION.REFRESH.getValue());
			}
		});
		
	}
	
	
	
	
	
	
	
	
	public String getId(){
		return ((TextField)context.getComponent(VIEWID.ID_TEXT.getValue())).getText();
	}
	public String getIp(){
		return ((TextField)context.getComponent(VIEWID.IP_TEXT.getValue())).getText();
	}
	public int getPort(){
		return Integer.parseInt(((TextField)context.getComponent(VIEWID.PORT_TEXT.getValue())).getText());
	}

	@Override
	public void actionPerformed(khh.std.Action event){
		// TODO Auto-generated method stub
		
	}
}
