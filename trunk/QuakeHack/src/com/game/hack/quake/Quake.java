package com.game.hack.quake;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.media.Format;
import javax.media.Player;

import com.kdn.gui.frame.FrameFrame;
import com.kdn.util.image.ImageUtil;
import com.kdt.util.Utilities;
import com.kdt.util.schedule.Scheduler;
import com.khm.gui.component.ImageView;
import com.khm.util.io.display.DisPlayUtil;
import com.khm.util.io.mouse.MouseUtil;

public class Quake  extends FrameFrame{
    
    

    public static enum VIEWID {
        IMAGE_COMPONENT("IMAGE_COMPONENT") 
        ;
        String id;

        VIEWID(String id) {
            this.id = id;
        }
        public String getValue() {
            return this.id;
        }
    };
    
    public static enum ACTION{
        CAPTURE(Utilities.getNextNumber()) ;
        int id;
        ACTION(int id){
            this.id=id;
        }
        public int getValue(){
            return this.id;
        }
    };
    
    
    private Player player =null;
    private Format[] formats =null;
    public Quake(String title) {
        super(title);
        flow();
    }
    
    
    
    @Override
    public void onViewSetting() {
     /*   try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }
        catch ( Exception cnfe )
        {
            System.out.println ("UI 에러");
        }*/
        setSize ( 800, 600 ); // default size...
        setLayout ( new BorderLayout() );
        
        
        
//        Image img= Toolkit.getDefaultToolkit().getImage("c:\\bb.jpg");
//        ImageView iv = new ImageView(img); 
        ImageView iv = new ImageView(); 
        iv.setName(VIEWID.IMAGE_COMPONENT.getValue());
        iv.setPreferredSize(new Dimension(320, 240  ));
        add (iv, BorderLayout.CENTER);        
       
       
       
       setVisible(true);
    }
    
    @Override
    public void onDataSetting() {
    }   

    @Override
    public void onAction(int gb, Object o) {

    }

    @Override
    public void onAddListener() {
      //스케
        try {
            scheduler.schedule("loop", run, 1000,50);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
          
        
    }

    Scheduler scheduler = new Scheduler();
Runnable run = new Runnable() {
    public void run() {
        try{

        
        
        
        
        ImageView imgview = (ImageView) getComponent(VIEWID.IMAGE_COMPONENT.getValue());
        BufferedImage bi = DisPlayUtil.getScreenCapture(0, 0, 800, 600);

        
        
        int s_w  =  200;
        int s_h  =  200;
//        int s_w  =  335;
//        int s_h  =  235;
      //  int s_w  =  200;
      //  int s_h  =  200;
        int e_w = bi.getWidth()-s_w;
        int e_h =  bi.getHeight()-s_h;
        
        //Graphics2D g2  = (Graphics2D) imgview.getGraphics();
        Graphics2D g2 = bi.createGraphics();
        g2.setColor(new Color(0x00ff00));
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(s_w, s_h, e_w-s_w, e_h-s_h);
        g2.setColor(new Color(0xff0000));
        
        
        //serarch Color  Draw Color
        /*
        ArrayList<Point> pointlist=new ArrayList<Point>();
        for (int s_h_v =  s_h  ; s_h_v <e_h; s_h_v++) {
            for (int s_w_v  = s_w; s_w_v < e_w; s_w_v++) {
                 Color color = new Color(bi.getRGB(s_w_v, s_h_v)); 
                 if(isWantColor(color)){
                         pointlist.add(new Point(s_w_v,s_h_v));
                 }
             }
         }
*/

        Graphics gg = imgview.getGraphics();
        Color startColor = new Color(200, 0, 150);
        Color endColor = new Color(255, 50, 255);
        ArrayList<Rectangle> rectanglelist= ImageUtil.getScop(bi, 40, s_w, s_h, e_w, e_h, startColor, endColor,1);
        
        double mouse_step =1.2;
        for (int j = 0; j < rectanglelist.size(); j++) {
            Rectangle rectangle = rectanglelist.get(j);
            //System.out.println((int)rectangle.getX()+"  "+(int)rectangle.getY()+"  "+(int) rectangle.getWidth()+"  "+(int) rectangle.getHeight());
            g2.drawRect((int)rectangle.getX(), (int)rectangle.getY(),(int) rectangle.getWidth(),(int) rectangle.getHeight());
         
            Point point = MouseUtil.getPoint();
            //Point point = new Point(400,300);
            
            int goX=(int) point.getX();
            int goY=(int) point.getY();
            
            boolean xgo=false;
            boolean ygo=false;
            if(goX>rectangle.getCenterX()){
                goX =   -(int)( Math.abs(goX-(int)rectangle.getCenterX())/mouse_step );
                xgo=false;
            }else{
                goX =   +(int)( Math.abs(goX-(int)rectangle.getCenterX())/mouse_step );
                xgo=true;
            }
            
            if(goY>rectangle.getCenterY()){
                goY = -(int)(Math.abs(goY-(int)rectangle.getCenterY())/mouse_step);
                ygo=false;
            }else{
                goY = +(int)(Math.abs(goY-(int)rectangle.getCenterY())/mouse_step);
                ygo=true;
            }
            
            
            
            Point pointGo = new Point((int)point.getX() + goX, (int)point.getY() + goY );
            g2.drawArc((int)pointGo.getX()-20, (int)pointGo.getY()-20, 40, 40, 0, 360);
            MouseUtil.move(pointGo);
            //break;
        }
        
        
        //end
        //g2.drawImage ( bi, null, null );
        
        imgview.setImage(bi);
        imgview.repaint();
        
        
        
        
        
        
        
        
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
};
    
    
    public boolean isWantColor(Color color) {
        if ( color.getRed() > 200 && color.getGreen() < 50 && color.getBlue() > 150 ) {
            return true;
        }
        return false;
    }
    
    @Override
    public void dispose() {
        if(scheduler!=null){
            scheduler.cancel();
        }
        if ( player != null )
        {
            player.close();
            player.deallocate();
            player = null;
        }
        super.dispose();
    }

    protected void finalize ( ) throws Throwable
    {
        if(scheduler!=null){
            scheduler.cancel();
        }
        if ( player != null )
        {
            player.close();
            player.deallocate();
            player = null;
        }
        super.finalize();
    }
    
    
    
    
    
    
    
    public static void main(String[] args) throws InterruptedException {
        Quake q = new Quake("aaaaa");
    }
}
