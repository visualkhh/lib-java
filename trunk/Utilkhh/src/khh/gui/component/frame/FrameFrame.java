package khh.gui.component.frame;

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

abstract public class FrameFrame  extends Frame  
{
	
	private HashMap<String,Object> viewContainer = new HashMap<String,Object>();
	public FrameFrame  context =null;
	boolean isfinish=false;

	public FrameFrame(){
	    super();
	    defaultFlow();
	}
	public FrameFrame(GraphicsConfiguration gc){
	    super(gc);
	    defaultFlow();
	}
    public FrameFrame(String title){
        super(title);
        defaultFlow();
    }
    
    public FrameFrame(String title, GraphicsConfiguration gc){
        super(title,gc);
        defaultFlow();
    }
	
	private void defaultFlow(){
	    
	    
	    
	    
	    
	    context=this;
		addWindowListener(new WindowAdapter(){
		          public void windowClosing(WindowEvent e) {
		        	  dispose();
//		            System.exit(0);
		              return;
		          }      
		     });
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
	
	
	//나갈때 없어지는..
	@Override
	public void dispose() {
	    super.dispose();
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
	
	
	@Override
	public void setMenuBar(MenuBar menuBar)
	{
//		DebugUtil.trace("MenuBar Name => %s",menuBar.getName());
//		DebugUtil.trace("menubar menuCount %d",menuBar.getMenuCount());
		addComponent(menuBar.getName(), menuBar);
		
		for (int i = 0; i < menuBar.getMenuCount(); i++)
		{
			Menu menu = menuBar.getMenu(i);
//			DebugUtil.trace("\t Menu Name=> %s",menu.getName());
//			DebugUtil.trace("\t menu menuItemCount %d",menu.getItemCount());
			
			addComponent(menu.getName(), menu);
			for (int j = 0; j < menu.getItemCount(); j++)
			{
				MenuItem menuItem = menu.getItem(i);
//				DebugUtil.trace("\t\t MenuItem Name=> %s",menuItem.getName());
				addComponent(menuItem.getName(), menuItem);
			}
		}
		
		super.setMenuBar(menuBar);
		
	}
	
	
   public void finish() {
        isfinish=true;
    }
    public void setfinish(boolean wantfinish){
        isfinish=wantfinish;
    }
}
