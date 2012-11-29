package applet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import khh.gui.applet.AppletBase;

public class AppletTest extends AppletBase implements MouseMotionListener{

	@Override
	public void onAction(int gb, Object o){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddListener(){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDataSetting(){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onViewSetting(){
		// TODO Auto-generated method stub
		
	}

	public void init(){
		addMouseMotionListener(this);
	}

	public void start(){
	}

	public void stop(){
	}

	public void destroy(){
	}
		 
	String mousePos="-";
	public void paint(Graphics g) {
		  g.setFont(new Font("굴림체",Font.ITALIC, 12));
		  g.setColor(Color.RED);
		  g.drawString("테스트1", 10, 50);
		  g.drawString("테스트2", 10, 62);
		  g.drawRect(10,74, 100, 22);
		  g.drawLine(10,100, 110, 100);
		  g.drawOval(10,110, 100, 22);
		  g.drawString(mousePos, 10, 150);
		 }

	public void mouseDragged(MouseEvent e){
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e){
		 String str=new String();
		  str=e.getX()+"/"+e.getY();
		  mousePos=str;
		  repaint();		
	}
	

}
