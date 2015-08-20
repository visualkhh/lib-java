

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import khh.gui.component.frame.FrameFrame;
import khh.gui.component.view.ImageView;
import khh.image.ImageUtil;
import khh.io.display.util.DisPlayUtil;
import khh.io.mouse.util.MouseUtil;
import khh.schedule.Scheduler;
import khh.util.Util;

import com.sun.glass.ui.Pixels.Format;



public class VideoAmp  extends FrameFrame{
    
    

    public static enum VIEWID {
    	IMAGE_BEFORE("IMAGE_BEFORE"), 
        IMAGE_AFTER("IMAGE_AFTER"), 
        IMAGE_DIFFE("IMAGE_DIFFE"), 
        IMAGE_MIX("IMAGE_MIX"), 
        IMAGE_AMP("IMAGE_AMP") 
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
        CAPTURE(Util.getNextNumber()) ;
        int id;
        ACTION(int id){
            this.id=id;
        }
        public int getValue(){
            return this.id;
        }
    };
    
    
    private Format[] formats =null;
    public VideoAmp(String title) {
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
        setSize ( 1100, 1000); // default size...
//        setLayout ( new BorderLayout() );
        setLayout ( new FlowLayout() );
        
        
        
        ImageView bimgV 	= new ImageView(); 
        ImageView aimgV 	= new ImageView(); 
        ImageView dimgV 	= new ImageView(); 
        ImageView mimgV 	= new ImageView(); 
        ImageView ampimgV 	= new ImageView(); 
        bimgV.setName(VIEWID.IMAGE_BEFORE.getValue());
        bimgV.setPreferredSize(new Dimension(500, 300));
        aimgV.setName(VIEWID.IMAGE_AFTER.getValue());
        aimgV.setPreferredSize(new Dimension(500, 300));
        dimgV.setName(VIEWID.IMAGE_DIFFE.getValue());
        dimgV.setPreferredSize(new Dimension(500, 300));
        mimgV.setName(VIEWID.IMAGE_MIX.getValue());
        mimgV.setPreferredSize(new Dimension(500, 300));
        ampimgV.setName(VIEWID.IMAGE_AMP.getValue());
        ampimgV.setPreferredSize(new Dimension(500, 300));
       
//        add (bimgV, BorderLayout.SOUTH);        
//        add (aimgV, BorderLayout.NORTH);        
//        add (dimgV, BorderLayout.EAST);        
        add (bimgV); 
        add (aimgV); 
        add (dimgV);
        add (mimgV);
        add (ampimgV);
       
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
            scheduler.schedule("loop", run, 1000,1);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    Scheduler scheduler = new Scheduler();
    
    
	Runnable run = new Runnable() {
	    public void run() {
	        try{
	
	        
	        
	        	int w  =  500;
	        	int h  =  300;
	        
		        ImageView bimgV = (ImageView) getComponent(VIEWID.IMAGE_BEFORE.getValue());
		        BufferedImage bImg = DisPlayUtil.getScreenCapture(300, 300, 500, 300);
		        Graphics2D bg = bImg.createGraphics();
		        
		        ImageView aimgV = (ImageView) getComponent(VIEWID.IMAGE_AFTER.getValue());
		        Thread.sleep(20);
		        BufferedImage aImg = DisPlayUtil.getScreenCapture(300, 300, 500, 300);
		        Graphics2D ag = aImg.createGraphics();
		        
		        ImageView dimgV = (ImageView) getComponent(VIEWID.IMAGE_DIFFE.getValue());
		        BufferedImage dImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		        Graphics2D dg = dImg.createGraphics();
		        
		        ImageView mimgV = (ImageView) getComponent(VIEWID.IMAGE_MIX.getValue());
		        BufferedImage mImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		        Graphics2D mg = mImg.createGraphics();
		        
		        ImageView ampimgV = (ImageView) getComponent(VIEWID.IMAGE_AMP.getValue());
		        BufferedImage ampImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		        Graphics2D ampg = ampImg.createGraphics();
		        
	        


		        
		        /////////process...
		        boolean change = false;
		        for (int x = 0; x < w; x++) {
					for (int y = 0; y < h; y++) {
						
						Color bColor = new Color(bImg.getRGB(x, y));
						Color aColor = new Color(aImg.getRGB(x, y));
						int rmax = Math.max(bColor.getRed(),	aColor.getRed());
						int rmin = Math.min(bColor.getRed(),	aColor.getRed());
						int gmax = Math.max(bColor.getGreen(),	aColor.getGreen());
						int gmin = Math.min(bColor.getGreen(),	aColor.getGreen());
						int bmax = Math.max(bColor.getBlue(),	aColor.getBlue());
						int bmin = Math.min(bColor.getBlue(),	aColor.getBlue());
						int rdiffe = rmax-rmin;
						int gdiffe = gmax-gmin;
						int bdiffe = bmax-bmin;
						Color dColor = new Color(rdiffe, gdiffe, bdiffe);
						
						
						int mred 	= bColor.getRed()+rdiffe;
						int mgreen 	= bColor.getGreen()+gdiffe;
						int mblue 	= bColor.getBlue()+bdiffe;
						mred 		= mred>255?255:mred;
						mgreen 		= mgreen>255?255:mgreen;
						mblue 		= mblue>255?255:mblue;
						
						Color mColor = new Color(mred, mgreen, mblue);
						
						int drgb = dColor.getRGB();
						int mrgb = mColor.getRGB();
						if(drgb != 0){
							dImg.setRGB(x, y, drgb);
							mImg.setRGB(x, y, mrgb);
							change=true;
						}
						
					}
				}
		        
		        
		        ////////
		        int scop 	= 30;
		        int limit 	= 100;
		        int samScop	= 2;
		        for (int x = 0+scop; x < w-scop; x++) {
			        for (int y = 0+scop; y < h-scop; y++) {
//			        	
			        	Color bColor = new Color(bImg.getRGB(x, y));
			        	Color aColor = new Color(aImg.getRGB(x, y));
			        	Color dColor = new Color(dImg.getRGB(x, y));
			        	Color mColor = new Color(mImg.getRGB(x, y));
			        	ampImg.setRGB(x, y, bImg.getRGB(x, y));
//			        	ampImg.setRGB(x, y, dImg.getRGB(x, y));
//			        	if(dImg.getRGB(x, y)==-16777216){
			        	if(dColor.getRed() > limit && dColor.getGreen() > limit && dColor.getBlue() > limit){
//			        		Color ampColor = new Color(aColor.getRed(), aColor.getGreen(), aColor.getBlue(),255);
			        		Color ampColor = new Color(255, 0, 0,255);
			        		ampImg.setRGB(x, y, ampColor.getRGB());
			        		
			        		for (int subX = x-scop; subX < x+scop; subX++) {
								for (int subY = y-scop; subY < y+scop; subY++) {
									if(     Math.abs(bColor.getRed()-aColor.getRed())>samScop && 
											Math.abs(bColor.getGreen()-aColor.getGreen())>samScop && 
											Math.abs(bColor.getBlue()-aColor.getBlue())>samScop){
										//ampg.setColor(new Color(bColor.getRed(),bColor.getGreen(),bColor.getBlue(),100));
//										GradientPaint gp = new GradientPaint(new Point2D.Float(x, y), bColor, new Point2D.Float(subX, subY), aColor);
//										ampg.setPaint(gp);
										ampg.setColor(bColor);
//										ampg.drawRect(subX, subY, x-subX, y-subY);
										ampg.drawLine(x, y, subX, subY);
									}
								}
							}
//			        		ampImg.setRGB(x, y, 0xff0000);
			        	}
//			        	
////			        	int mred 	= ampColor.getRed();
////						int mgreen 	= ampColor.getGreen();
////						int mblue 	= ampColor.getBlue();
////			        	ampImg.setRGB(x, y, ampColor.getRGB());
			        }
		        }
		        ////////amp
		        
		        
		        ////////////
		        
		        
		        ///////////infomation draw
		        bg.setColor(new Color(0xFF0000));
		        bg.setStroke(new BasicStroke(2));
		        bg.drawString("before", 3, 15);
		        bg.drawRect(0, 0, w, h);
		        
		        ag.setColor(new Color(0x0000FF));
		        ag.setStroke(new BasicStroke(2));
		        ag.drawString("after", 3, 15);
		        ag.drawRect(0, 0, w, h);
		        
		        dg.setColor(new Color(0x00FF00));
		        dg.setStroke(new BasicStroke(2));
		        dg.drawString("diffe", 3, 15);
		        dg.drawRect(0, 0, w, h);
		        
		        mg.setColor(new Color(0x00FFFF));
		        mg.setStroke(new BasicStroke(2));
		        mg.drawString("mix", 3, 15);
		        mg.drawRect(0, 0, w, h);
		        
		        ampg.setColor(new Color(0xFFFF00));
		        ampg.setStroke(new BasicStroke(2));
		        ampg.drawString("amp", 3, 15);
		        ampg.drawRect(0, 0, w, h);
		        ///////////
		        
		        
		        
		        
		        
		        bimgV.setImage(bImg);
		        aimgV.setImage(aImg);
		        bimgV.repaint();
		        aimgV.repaint();
		        if(change){
			        dimgV.setImage(dImg);
			        dimgV.repaint();
			        mimgV.setImage(mImg);
			        mimgV.repaint();
			        ampimgV.setImage(ampImg);
			        ampimgV.repaint();
		        }
	        
	        

	        }catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	};
    
    
//    public boolean isWantColor(Color color) {
//        if ( color.getRed() > 200 && color.getGreen() < 50 && color.getBlue() > 150 ) {
//            return true;
//        }
//        return false;
//    }
    
    @Override
    public void dispose() {
        if(scheduler!=null){
            scheduler.cancel();
        }
        super.dispose();
    }

    protected void finalize ( ) throws Throwable
    {
        if(scheduler!=null){
            scheduler.cancel();
        }
        super.finalize();
    }
    
    
    
    
    
    
    
    public static void main(String[] args) throws InterruptedException {
        VideoAmp vamp = new VideoAmp("aaaaa");
    }
}
