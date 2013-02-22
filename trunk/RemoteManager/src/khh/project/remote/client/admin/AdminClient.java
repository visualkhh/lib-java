package khh.project.remote.client.admin;

import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.communication.tcp.nio.worker.msg.NioActionMsg.ACTION;
import khh.gui.component.frame.FrameFrame;
import khh.listener.action.ActionListener;
import khh.project.remote.client.admin.communication.AdminCommunication;
import khh.util.Util;
public class AdminClient extends FrameFrame  {
	public static final String name="AdminClient";
	public static enum VACTION{
		CONNECTION(Util.getNextNumber()),
		REFRESH(Util.getNextNumber()),
		;
		int id;
		VACTION(int id){
			this.id = id;
		}
		public int getValue(){
			return this.id;
		}
	};
	AdminClientDesigner designer = null;
	AdminCommunication com = null;
	public ActionListener listener = new ActionListener<NioActionMsg>(name){
		public void actionPerformed(khh.std.Action<NioActionMsg> event){
			if(ACTION.GET_SERVERS.getValue()==event.getId()){
				if(event.getSource().getString()!=null){
					String[] a = event.getSource().getString().split(",");
					designer.drawServerList(a);
				}
			}
		}
	};
	public AdminClient(String title){
		super(title);
		setName(name);
	}
	@Override
	public void onBeforeProcess(){
		designer = new AdminClientDesigner(context);
		com = new AdminCommunication();
		
	}
	@Override
	public void onViewSetting(){
		designer.drawDesign();
	}
	@Override
	public void onDataSetting(){
	}
	@Override
	public void onAddListener(){
		designer.onAddListener();
	}
	@Override
	public void onAction(int gb, Object o){
		try{
			if(VACTION.CONNECTION.getValue() == gb){
				com.connection(designer.getIp(), designer.getPort());
				com.addActionEventListener(listener);
			}else if(VACTION.REFRESH.getValue() == gb){
				com.getServers(designer.getId());
				if(o!=null)
					com.getServers(designer.getId());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args){
		new AdminClient("AdminClient").flow();
	}
	@Override
	public void dispose(){
		super.dispose();
		com.stop();
	}
}
