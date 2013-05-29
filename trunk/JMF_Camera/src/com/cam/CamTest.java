package com.cam;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.HashMap;

import javax.media.CaptureDeviceInfo;
import javax.media.Format;
import javax.media.Player;
import javax.media.control.FormatControl;
import javax.media.format.VideoFormat;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

import khh.communication.tcp.nio.client.NioClient;
import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.file.util.FileUtil;
import khh.gui.component.frame.FrameFrame;
import khh.gui.component.view.ImageView;
import khh.image.ImageUtil;
import khh.io.jmf.camera.util.CameraUtil;
import khh.schedule.Scheduler;
import khh.util.Util;
public class CamTest extends FrameFrame {

    
    
    public static enum VIEWID {
        PLAYER_COMPONENT("PLAYER_COMPONENT"), 
        STATUS_PANEL("STATUS_PANEL"), 
        CHANGEFORMAT_BTN("CHANGEFORMAT_BTN") ,
        IMAGE_COMPONENT("IMAGE_COMPONENT") ,
        CAPTURE_BTN("CAPTURE_BTN") 
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
    
    
    private Player player =null;
    private Format[] formats =null;
    private NioCameraClient clientworker=null;
    public CamTest(String title) throws Exception {
        super(title);
        clientworker = new NioCameraClient();
        new NioClient("127.0.0.1", 9876, clientworker).start();
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
        
        
        
    /*   JPanel visualContainer = new JPanel();
       visualContainer.setLayout ( new BorderLayout() );
       add ( visualContainer, BorderLayout.CENTER );
      */ 
       
       
       JLabel statusBar = new JLabel (""){
           public Dimension getPreferredSize (  )
           {
               return ( new Dimension ( 10, super.getPreferredSize().height ) );
           }
       };
       statusBar.setName(VIEWID.STATUS_PANEL.getValue());
       statusBar.setBorder ( new EtchedBorder() );
       add ( statusBar, BorderLayout.SOUTH );
     //  add (new JLabel("후훗"), BorderLayout.EAST); 
       
        Image img= Toolkit.getDefaultToolkit().getImage("c:\\bb.jpg");
       ImageView iv = new ImageView(img); 
       iv.setName(VIEWID.IMAGE_COMPONENT.getValue());
       iv.setPreferredSize(new Dimension(320, 240  ));
       add (iv, BorderLayout.EAST); 
       
       
       
       //JPanel chatContainer = new JPanel();
       JToolBar toolbar = new JToolBar();
       toolbar.setFloatable(false);
       JButton capturebtn = new JButton("capture");
       capturebtn.setName(VIEWID.CAPTURE_BTN.getValue());
       JButton changeformatbtn= new JButton("changeformat");
       changeformatbtn.setName(VIEWID.CHANGEFORMAT_BTN.getValue());
       toolbar.add(changeformatbtn);
       toolbar.add(capturebtn);
       add ( toolbar, BorderLayout.NORTH );
       
       
       ArrayList<CaptureDeviceInfo> list = CameraUtil.getCameraList();
       for (int i = 0; i < list.size(); i++) {
           CaptureDeviceInfo deviceinfo  = list.get(i); 
           formats = list.get(i).getFormats();
           statusBar.setText(deviceinfo.getName());
           System.out.println(deviceinfo.getName());
        
           
            for (int j = 0; j <formats.length; j++) {
//                Format format = formats[i];
                VideoFormat format = (VideoFormat)formats[j];
                System.out.println(format.getSize()+"   "+format+format.getClass().getName()+"    "+ format.getClass().getSimpleName());
            }
            
           
          try{
              player = CameraUtil.getPlayer(deviceinfo);
              player.start();
              Component playerComponent = player.getVisualComponent();
              playerComponent.setName(VIEWID.PLAYER_COMPONENT.getValue());
              add ( playerComponent, BorderLayout.CENTER );
              
              
          //    FormatControl formatControl = (FormatControl)player.getControl ( "javax.media.control.FormatControl" );
          //   VideoFormat format = (VideoFormat) formatControl.getFormat();
          //    Dimension size = format.getSize();
          //    playerComponent.setPreferredSize(size);
          //    System.out.println(size);
              invalidate();
              pack();
              
              
              //System.out.println(format.getClass().getName()+"    "+ format.getClass().getSimpleName());
           }catch (Exception e) {
               System.err.println ("Error : Could not get visual component");
             e.printStackTrace();
           }
           
       }
       
       
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
        JButton capture_btn =   (JButton) getComponent(VIEWID.CAPTURE_BTN.getValue());
      capture_btn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Image image = CameraUtil.getImage(player);
//            BufferedImage bi = new BufferedImage ( image.getWidth(null),image.getHeight(null),
            BufferedImage bi = ImageUtil.toBufferedImage ( image);
            Graphics2D g2 = bi.createGraphics();
            g2.drawImage ( image, null, null );
            
       /*  
            System.out.println(bi.getWidth()+"      "+bi.getHeight());
            BufferedImage outbi = new BufferedImage(320,240, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = outbi.createGraphics();
            for (int w = 0; w < bi.getWidth(); w++) {
                for (int h = 0; h < bi.getHeight(); h++) {
                    int rgb = bi.getRGB(w, h);
                    int mrgb = 0x333333;
                    System.out.println(rgb);
                    rgb-=mrgb;
                    g.setColor(new Color(rgb));
                    g.drawLine(w, h, w, h);
                }
            }
            g.setColor(Color.BLACK);
            g.drawLine(0, 0, 320, 240);
         */   
            try{
                FileUtil.writeFile("c:\\b.jpg", bi, "jpg");
        //        FileUtilKHM.writeFile("c:\\bb.jpg", outbi, "jpg");
            }catch (Exception e1) {
                e1.printStackTrace();
            }
        }
      });
      
      JButton changeformat_btn =   (JButton) getComponent(VIEWID.CHANGEFORMAT_BTN.getValue());
      changeformat_btn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              Object selected = JOptionPane.showInputDialog (context,
                                      "Select Video format",
                                      "Capture format selection",
                                      JOptionPane.INFORMATION_MESSAGE,
                                      null,        //  Icon icon,
                                      formats, // videoFormats,
                                      formats[0] );
                if ( selected != null ){
                //setFormat ( ((MyVideoFormat)selected).format );
                    VideoFormat format =(VideoFormat) selected;
                    System.out.println(format);
                    player.stop();
                    Component playerComponent = (Component) getComponent(VIEWID.PLAYER_COMPONENT.getValue());
//                    remove (playerComponent);
//                    playerComponent =  player.getVisualComponent();
                    FormatControl formatControl =  CameraUtil.getFormatControl(player);
                    formatControl.setFormat(format);

//                    playerComponent.setName(VIEWID.PLAYER_COMPONENT.getValue());
//                    add ( playerComponent, BorderLayout.CENTER );
                    
                    player.start();
                    
                    playerComponent.setPreferredSize(format.getSize());
                    invalidate();     
                    pack();
                }
              
          }
      });
      
      
      
      
      Runnable run= new Runnable() {
        
        public void run() {
            try{
                //Thread.sleep(1000);
                
          ImageView imgview = (ImageView) getComponent(VIEWID.IMAGE_COMPONENT.getValue());
          Image image = CameraUtil.getImage(player);
          BufferedImage bi = ImageUtil.toBufferedImage ( image);
         // FileUtil.writeFile("c:\\aa.jpg", ImageUtil.toByteArray(bi, "jpg"));
          HashMap<String,Object> map = new HashMap<String, Object>();
          map.put("img", ImageUtil.toByteArray(bi, "jpg"));
          if(clientworker!=null && clientworker.isConnected()){
              clientworker.receiveTelegram(map, null);
          }
          /*
          ArrayList<Point> pointlist=new ArrayList<Point>();
           for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                Color color = new Color(bi.getRGB(x, y)); 
                if(color.getRed()>100){
                    if(color.getGreen()<50 && color.getBlue()<50){
                        pointlist.add(new Point(x,y));
                    }
                }
            }
          }
          */
          
          
          
          Graphics2D g2 = bi.createGraphics();
          g2.drawImage ( image, null, null );

          
//          Color startColor = new Color(140, 100, 100);
//          Color endColor = new Color(160, 120, 120);
          Color startColor = new Color(176, 170, 92);
          Color endColor = new Color(124, 160, 95);
          
          
          ArrayList<Rectangle> rectanglelist= ImageUtil.getScop(bi, 100, 0, 0, bi.getWidth(), bi.getHeight(), startColor, endColor);
          for (int j = 0; j < rectanglelist.size(); j++) {
              Rectangle rectangle = rectanglelist.get(j);
              g2.drawRect((int)rectangle.getX(), (int)rectangle.getY(),(int) rectangle.getWidth(),(int) rectangle.getHeight());
          }
          
          //System.out.println(rectanglelist.size());
          
          /*
          for (int i = 0; i < pointlist.size(); i++) {
              if(i+1>=pointlist.size())
                  break;
              Point point1 = pointlist.get(i);
              Point point2 = pointlist.get(i+1);
              
             g2.drawLine((int)point1.getX(),(int)point1.getY(), (int)point2.getX(), (int)point2.getY());
           MouseUtil.move(point1);  
          }
          */
          imgview.setImage(image);
          imgview.repaint();
//          imgview.paint(imgview.getGraphics());
          //imgview.validate();
            }catch (Exception e) {
                    e.printStackTrace();
            }
        }
    };
      
     
  try {
        scheduler.schedule("loop", run, 1000,10);
    } catch (Exception e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    }
      
      
      
      
      
    }

    Scheduler scheduler = new Scheduler();

    
    
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
        if ( player != null )
        {
            player.close();
            player.deallocate();
            player = null;
        }
        super.finalize();
    }
    
    
    public static void main(String[] args) throws Exception {
//        ArrayList<String> a=  new ArrayList<String>();
//        a.add("1");
//        a.add("2");
//        a.add("3");
//        a.add("4");
//        for (int i = 0; i < a.size(); i++) {
//            String av = a.get(i);
////            System.out.println(av );
//            a.remove(av);
//            i--;
//        }
//        for (int i = 0; i < a.size(); i++) {
//            String av = a.get(i);
//            System.out.println(av );
//        }
        
        CamTest myWebCam = new CamTest ( "Web Cam Capture" );
    }
    
}
