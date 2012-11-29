package khh.gui.applet;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

abstract public class AppletBase  extends Applet  
{
	
	private HashMap<String,Object> viewContainer = new HashMap<String,Object>();
	public AppletBase  context =null;
	boolean isfinish=false;

	public AppletBase(){
	    super();
	    defaultFlow();
	}
	
	private void defaultFlow(){
	    context=this;
	}
	
	
	public void flow(){
	    if(isfinish==false)
		onBeforeProcess();
	    if(isfinish==false)
		onViewSetting();
	    if(isfinish==false)
		onDataSetting();
	    if(isfinish==false)
		onAddListener();
	}
	
	
	public void onBeforeProcess(){
		
	}
	
	abstract public void onViewSetting();
	
	abstract public void onDataSetting();
	
	abstract public void onAddListener();
	

	
	
	
	public void onAction(int gb){
		onAction(gb, null);
	}
	abstract public void onAction(int gb,Object o);
	
	
	
	
	@Override
	public void init(){
		super.init();
		flow();
	}
	
	
	
	
	private void addComponents(String name,Component c){
		try{
			addComponent(name,c);//컴포넌트추가
		Container container = (Container) c;
		Component[] ca= container.getComponents();
		 for (int i = 0; i < ca.length; i++)
		{
			 addComponents(ca[i].getName(),ca[i]);
		}
		
		}catch (Exception e) {
				//e.printStackTrace();
		}
		
	}
	
	
	public void addComponent(String name,Object c){
		viewContainer.put(name,c);
	}
	
	public Object getComponent(String name){
	    

	    Object c = viewContainer.get(name);
	    
	    if(c==null){
	        //System.out.println("널이랑꼐꼐");
	        c = getComponentAll(this,name);
	    }
	    
		return c;
	}
	public Component getComponentAll(Component c , String name){
	    Component returnc=null;
	    Component[] componnents = getChildComponent(c);
	    //System.out.println(componnents.length);
        boolean is_matching=false;
	    for (int i = 0; i < componnents.length; i++) {
	        Component atcom = componnents[i];
	        //System.out.println("이름이랑께"+atcom.getName());
            if(name.equals(atcom.getName())|| name.equals(atcom.getName())){
                is_matching=true;
                returnc = atcom;
                //System.out.println("맞췄당께.");
            }
        }
	    //System.out.println(is_matching);
	    if(is_matching==false){
	        for (int i = 0; i < componnents.length; i++) {
	            //System.out.println("없어서찾는당께."+i);
                returnc = getComponentAll(componnents[i],name);
                if(returnc!=null){
                    addComponent(returnc.getName(), returnc);
                    break;
                }
            }
	    }
	    
	    if(returnc !=null){
	        //System.out.println(returnc.getName());
	    }
        return returnc;
	    
	}
	
	
	private Component[] getChildComponent(Component c){
	    Component[] r = {};
        try{
            Container container = (Container) c;
            r= container.getComponents();
        }catch (Exception e) {
        }
        return r;
	}
	
	
	
//	@Override
//	public Component add(Component arg0)
//	{
//		addComponents(arg0.getName(),arg0);
//		return super.add(arg0);
//	}
//	@Override
//	public Component add(Component arg0, int arg1)
//	{
//		addComponents(arg0.getName(),arg0);
//		return super.add(arg0, arg1);
//	}
//	@Override
//	public void add(Component arg0, Object arg1)
//	{
//		addComponents(arg0.getName(),arg0);
//		super.add(arg0, arg1);
//	}
//	@Override
//	public void add(Component arg0, Object arg1, int arg2)
//	{
//		addComponents(arg0.getName(),arg0);
//		super.add(arg0, arg1, arg2);
//	}
	
	
//	@Override
//	public synchronized void add(PopupMenu arg0)
//	{
////		addComponent(arg0.getName(),arg0);
//		super.add(arg0);
//	}
//	@Override
//	public Component add(String arg0, Component arg1)
//	{
//		addComponents(arg1.getName(),arg1);
//		return super.add(arg0, arg1);
//	}
	
	
	
	
   public void finish() {
        isfinish=true;
    }
    public void setfinish(boolean wantfinish){
        isfinish=wantfinish;
    }

    /*
		Applet 만들기
 
		1. Applet 클래스 중요 오버라이딩 메서드
		public void init( )   //웹페이지를 처음 로딩해서 초기화할때 호출
		public void start( )   //웹페이지를 처음 방문이나 재방문시 호출
		public void stop( )   //다른 웹페이지로 이동할때 호출
		public void destroy( )   //웹페이지를 닫을때 호출
		 
		2. 애플릿 동작시 호출되는 메서드 순서
		init() ==> start() ==> paint( )
		start() <==> stop()
		stop() ==> destroy()

     */
    //닫을때.
    public void destroy() {
    	super.destroy();
	}
}
