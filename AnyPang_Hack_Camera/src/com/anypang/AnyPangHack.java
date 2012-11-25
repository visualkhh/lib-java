package com.anypang;

import jxl.format.Format;
import khh.gui.component.frame.FrameFrame;
import khh.schedule.Scheduler;
import khh.util.Util;

public class AnyPangHack extends FrameFrame {

    
    
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
    public AnyPangHack(String title) {
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
       

       
       
       
       //JPanel chatContainer = new JPanel();
       JToolBar toolbar = new JToolBar();
       toolbar.setFloatable(false);
       JButton capturebtn = new JButton("capture");
       capturebtn.setName(VIEWID.CAPTURE_BTN.getValue());
       JButton changeformatbtn= new JButton("changeformat");
       changeformatbtn.setName(VIEWID.CHANGEFORMAT_BTN.getValue());
       toolbar.add(changeformatbtn);
       toolbar.add(capturebtn);
       add (toolbar, BorderLayout.NORTH);
       
       
       
       
       //imageView Setting
       // Image img= Toolkit.getDefaultToolkit().getImage("c:\\bb.jpg");
       // ImageView iv = new ImageView(img); 
         ImageView iv = new ImageView(); 
        iv.setName(VIEWID.IMAGE_COMPONENT.getValue());
        iv.setPreferredSize(new Dimension(320, 240  ));
        add(iv, BorderLayout.EAST); 
       System.out.println("preview size w:"+iv.getWidth()+"     h:"+iv.getHeight());
        
       
       
       //device  setting  !!  or  component setting  
       ArrayList<CaptureDeviceInfo> list = CameraUtil.getCameraList();
       System.out.println("CaptureDeviceInfo Size: "+list.size());
       for (int i = 0; i < list.size(); i++) {
           CaptureDeviceInfo deviceinfo  = list.get(i); 
           formats = list.get(i).getFormats();
           statusBar.setText(deviceinfo.getName());
           System.out.println("deviceinfo.getName() : "+deviceinfo.getName());
           System.out.println("formats.length : "+formats.length);
        
           /*
            for (int j = 0; j <formats.length; j++) {
            //  Format format = formats[i];
                VideoFormat format = (VideoFormat)formats[j];
                System.out.println("format.getSize() : "+format.getSize()+"   "+format+format.getClass().getName()+"    "+ format.getClass().getSimpleName());
            }
            */
           
          try{
              player = CameraUtil.getPlayer(deviceinfo);
              player.start();
              Component playerComponent = player.getVisualComponent();
              playerComponent.setName(VIEWID.PLAYER_COMPONENT.getValue());
              add ( playerComponent, BorderLayout.CENTER );
              System.out.println("playerComponent size w:"+playerComponent.getWidth()+"     h:"+playerComponent.getHeight());
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
          ImageView imgview = (ImageView) getComponent(VIEWID.IMAGE_COMPONENT.getValue());
          Image image = CameraUtil.getImage(player);
          BufferedImage bi = ImageUtil.toBufferedImage (image);
        
         // System.out.println("getImage   w:"+bi.getWidth()+"        h:"+bi.getHeight());
          
          
          //위쪽에 이미지그리기위해
          Graphics2D g2 = bi.createGraphics();
          g2.drawImage ( image, null, null );
          
          Color startColor  =   null;
          Color endColor    =   null;
          ArrayList<Rectangle> rectanglelist    =   null;
        
          
          if(position==0){
              //blue
              startColor= new Color(0, 123, 217);
              endColor = new Color(59, 187, 255);
              g2.setColor(new Color(0x0000ff));
          }else if(position==1){
              //red
              endColor= new Color(255, 108, 111);
              startColor = new Color(240,80,15);
              g2.setColor(new Color(0xff0000));
          }else if(position==2){
              //yellow
              endColor= new Color(255, 159, 109);
              startColor = new Color(255,103,38);
              g2.setColor(new Color(0xffff00));
          }else if(position==3){
            //green
              endColor= new Color(18, 88, 20);
              startColor = new Color(135,167,54);
              g2.setColor(new Color(0x00ff00));
          }
          
          g2.setStroke(new BasicStroke(2));
          rectanglelist= ImageUtil.getScop(bi, 25, 0, 0, bi.getWidth(), bi.getHeight(), startColor, endColor);
          System.out.println("ImageUtil.getScop Size:"+rectanglelist.size()+"      position:"+position);
          for (int j = 0; j < rectanglelist.size(); j++) {
              
              Rectangle rectangle = rectanglelist.get(j);
              if(rectangle.height>=50 || rectangle.width>=50)
              g2.drawRect((int)rectangle.getX(), (int)rectangle.getY(),(int) rectangle.getWidth(),(int) rectangle.getHeight());
          }
          

          imgview.setImage(image);
          imgview.repaint();
            }catch (Exception e) {
                    e.printStackTrace();
            }
        }
    };
      
     
   try {
        scheduler.schedule("loop", run, 1000,1);
        scheduler.schedule("cntloop", new Runnable() {
            public void run() {
                position++;
                System.out.println(position);
                if(position>3){
                    position=0;
                }
            }
        },  1000,2000);
    } catch (Exception e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    }
      
      
      
      
      
    }

    int position=0;
    Scheduler scheduler = new Scheduler();
    Scheduler scheduler_cnt = new Scheduler();

    
    
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
    
    
    public static void main(String[] args) {
        AnyPangHack myWebCam = new AnyPangHack ( "Web Cam Capture" );
    }
    
}
