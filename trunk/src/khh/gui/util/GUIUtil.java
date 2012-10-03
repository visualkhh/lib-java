package khh.gui.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.MenuItem;
import java.io.File;
import java.io.IOException;

import khh.image.ImageUtil;

public class GUIUtil
{

	
//	public static MenuItem AddMenuItem(String title){
//		 MenuItem mi= new MenuItem(title); //메뉴생성
//		 return mi;
//	}
//	
    
    public static  GridBagConstraints getGridBagConstraints(int x, int y, int width,int height){
        GridBagConstraints gbl = new GridBagConstraints();
        gbl.gridx=x;
        gbl.gridy=y;
        gbl.gridwidth=width;
        gbl.gridheight=height;
        return gbl;
    }
    public static  GridBagConstraints getGridBagConstraints(int x, int y, int width,int height,int fillmode){
        GridBagConstraints gbl = getGridBagConstraints(x, y, width, height);
        gbl.fill=fillmode;
        //GridBagConstraints.BOTH  FULL
        return gbl;
    }
//    public static  GridBagConstraints getGridBagConstraints(int x, int y, int width,int height,int anchor){
//        GridBagConstraints.CENTER (D)
//        GridBagConstraints.NORTH 
//          .....    
//    }
    
    public static Component  setSize(Component c , int width,int height){
        Dimension d = new Dimension(width, height);
        c.setSize(d); //컴포넌트 크기결정하는거.
        c.setPreferredSize(d);//기본크기를 결정하는것
        return c;
    }
    public static Component  setVisible(Component c , boolean wantVisible){
        c.setVisible(wantVisible);
        return c;
    }
    public static Component  setBackGround(Component c ,Color color){
        c.setBackground(color);
        return c;
    }
    public static Component  setBackGround(Component c , int r,int g,int b){
        c.setBackground(new Color(r,g,b));
        return c;
    }
    
    public static void validate(Container c){
        c.validate();
    }
    
    
    public static void setIcon(Frame frame, Image image){
           frame.setIconImage(image);
    }
}
