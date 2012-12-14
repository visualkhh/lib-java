package khh.project.remote.client.admin;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

import khh.gui.component.frame.FrameFrame;
import khh.gui.util.GUIUtil;


public class AdminClient extends FrameFrame{
    public static enum VIEWID {
        IMAGE_COMPONENT("IMAGE_COMPONENT"), 
        IP_TEXT("IP_TEXT") ,
        PORT_TEXT("PORT_TEXT") ,
        LOGIN_BTN("LOGIN_BTN"), 
        STATE_LABEL("STATE_LABEL"), 
        CENTER_PANEL("CENTER_PANEL"), 
        ETC("ETCBTN") 
        ;
        String id;

        VIEWID(String id) {
            this.id = id;
        }
        public String getValue() {
            return this.id;
        }
    };
    
	public AdminClient(String title){
		super(title);
	}
	
	@Override
	public void onViewSetting(){
		GUIUtil.setSize (getContext(), 500, 500 ); // default size...
		
		setLayout ( new BorderLayout() );
//	  try {
//            Image i;
//            i = ImageUtil.getImage(new File("c:\\red_a.png"));
//            setIconImage(i);
//        } catch (IOException e) {
//        }
		Panel topPanel = new Panel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        topPanel.add (new Label("ServerInfo:")); 
        
        TextField ip= new TextField(15);
        ip.setName(VIEWID.IP_TEXT.getValue());
        topPanel.add (ip); 
        
        TextField port= new TextField(4);
        ip.setName(VIEWID.PORT_TEXT.getValue());
        topPanel.add (port); 
        
        
        Button btnm = new Button("ServerLogin");
        btnm.setName(VIEWID.LOGIN_BTN.getValue());
        topPanel.add (btnm); 

        Label state = new Label("[STATE]");
        state.setName(VIEWID.LOGIN_BTN.getValue());
        topPanel.add (state); 
        
        
        
        //center
		Panel centerPanel = new Panel(new GridLayout(10,10));
		centerPanel.setName(VIEWID.CENTER_PANEL.getValue());
		centerPanel.removeAll();
		centerPanel.add(new Button("aaaaaa"));
		this.add(centerPanel,BorderLayout.CENTER);
        
        
        topPanel.setBackground(new Color(255,0,0));
        this.add(topPanel,BorderLayout.NORTH);
		setVisible(true);
	}
	@Override
	public void onDataSetting(){
	}
	@Override
	public void onAddListener(){
	}
	
	
	@Override
	public void onAction(int gb, Object o){
	}
	
	public static void main(String[] args){
		new AdminClient("aa").flow();
	}
}
