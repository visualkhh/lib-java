import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.util.ArrayList;

import khh.debug.LogK;
import khh.gui.component.frame.FrameFrame;
import khh.gui.component.view.ImageView;
import khh.gui.util.GUIUtil;
import khh.image.ImageUtil;
import khh.io.display.util.DisPlayUtil;
import khh.io.mouse.util.MouseUtil;
import khh.schedule.Scheduler;
import khh.std.realworld.TPoint;
import khh.std.realworld.TPointToPoint;
import khh.util.Util;


class Pang {
    public static final int MONKEY=1;//원숭이
//    public static final Color MONKEY_S  = new Color(166,122,81);
//    public static final Color MONKEY_E  = new Color(186,120,91);
    public static final Color MONKEY_S  = new Color(156,112,71);
    public static final Color MONKEY_E  = new Color(196,130,101);
    
    public static final int PIG=2;//돼지
//    public static final Color PIG_S  = new Color(207,126,118);
//    public static final Color PIG_E  = new Color(217,129,120);
    public static final Color PIG_S  = new Color(175,100,100);
    public static final Color PIG_E  = new Color(227,139,130);
    
    public static final int RAT=3;//이명박
//    public static final Color RAT_S  = new Color(114,158,41);
//    public static final Color RAT_E  = new Color(122,162,42);
    public static final Color RAT_S  = new Color(104,148,31);
    public static final Color RAT_E  = new Color(132,172,52);
    
    
    public static final int RABBIT=4;//토끼
//    public static final Color RABBIT_S  = new Color(179,179,170);
//    public static final Color RABBIT_E  = new Color(193,183,182);
    public static final Color RABBIT_S  = new Color(164,164,155);
    public static final Color RABBIT_E  = new Color(203,193,192);
    
    
    
    public static final int DOG=5;//개
    public static final Color DOG_S  = new Color(19,148,145);
    public static final Color DOG_E  = new Color(40,180,175);
    
    public static final int CHICK=6;//병아리
    public static final Color CHICK_S  = new Color(191,140,33);
    public static final Color CHICK_E  = new Color(231,177,67);
    
    public static final int CAT=7;//고양이
    public static final Color CAT_S  = new Color(82,122,122);
    public static final Color CAT_E  = new Color(129,147,146);
    
    public static final int BOOMB=10;//폭탄
    public static final Color BOOMB_S  = new Color(91,39,13);
    public static final Color BOOMB_E  = new Color(111,59,33);
    
    int type=0;
    Color color=null;
    int x=0;
    int y=0;
    
    public Pang(int type,int x,int y,Color color) {
        this.type= type;this.color=color;
        this.x=x;this.y=y;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    
    
}



public class CopyOfAnyPangHack  extends FrameFrame{
    
    


    LogK log = LogK.getInstance();






    public static enum VIEWID {
        IMAGE_COMPONENT("IMAGE_COMPONENT"), 
        START_BTN("START_BTN") ,
        MOUSE_BTN("MOUSE_BTN") 
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
    
    
   // private Player player =null;
    private Format[] formats =null;
    public CopyOfAnyPangHack(String title) {
        super(title);
        flow();
    }
    
    
    
    @Override
    public void onViewSetting() {
        GUIUtil.setSize (context, 500, 500 ); // default size...
       // setSize(400,400);
        
        setLayout ( new BorderLayout() );
        try {
            Image i;
            i = ImageUtil.getImage(new File("c:\\red_a.png"));
            setIconImage(i);
        } catch (IOException e) {
        }
        ImageView iv = new ImageView(); 
        iv.setName(VIEWID.IMAGE_COMPONENT.getValue());
        add (iv, BorderLayout.CENTER);    
        
        
        
        Panel topPanel = new Panel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        Button btn = new Button("Start");
        btn.setName(VIEWID.START_BTN.getValue());
        topPanel.add (btn);    

        Button btnm = new Button("mouse");
        btnm.setName(VIEWID.MOUSE_BTN.getValue());
        topPanel.add (btnm);    
        
        
        add(topPanel,BorderLayout.SOUTH);
        
        
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
            scheduler.schedule("loop", run_scan, 1000,50);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
          
        
        Button btn = (Button) getComponent(VIEWID.START_BTN.getValue());
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                scan_start=!scan_start;
            }
        });
        
        btn = (Button) getComponent(VIEWID.MOUSE_BTN.getValue());
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                mouse_start=!mouse_start;
             /*   if(mouse_start){
                    try {
                        scheduler.schedule("mouse", mouse_action, 500,50);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }else{
                    try {
                        scheduler.removeScheduleTask("mouse");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    
                }*/
            }
        });
        
        mouse_t = new Thread(mouse_action);
        mouse_t.start();
    }
    Thread mouse_t =  null;
    Scheduler scheduler = new Scheduler();
    
    
    public boolean scan_start = false;
    public boolean mouse_start = false;
    Runnable mouse_action = new Runnable() {
        @Override
        public void run() {
            
         while(!Thread.currentThread().isInterrupted()){
             if(mouse_start==false&&panglist.length>0){
                 try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                 continue;
             }
      
            
                    for (int y = 0; y < panglist.length; y++) {
                        for (int x = 0; x < panglist[y].length; x++) {
                            Pang pang = panglist[x][y];
                            pang = (pang==null?new Pang(-1,0,0,null):pang);
                            int type = pang.getType();
                           // System.out.print(type+",");
                            
                            //네번옮긴다
                            //8가지 체크한다
                            //있음 수행한다.
                            
                            
          
                            ArrayList<TPointToPoint> points = new ArrayList<TPointToPoint>();
                            
                            try{
                              //오른쪽한번 이동해서 나란쭉
                                if(pang.getType() == panglist[x+2][y].getType() && pang.getType() == panglist[x+3][y].getType()){
                                    points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x+1][y].getX(),panglist[x+1][y].getY())));   
                                    pang.setType(-1);
                                }
                            }catch (Exception e) {
                            }
                            
                            try{
                                //오른쪽한번 이동해서   위쪽으로나란
                                if(pang.getType() == panglist[x+1][y-1].getType() && pang.getType() == panglist[x+1][y-2].getType()){
                                    points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x+1][y].getX(),panglist[x+1][y].getY())));   
                                    pang.setType(-1);
                                }
                            }catch (Exception e) {
                            }
                            
                            try{
                                //오른쪽한번 이동해서   아래쪽으로나란
                                if(pang.getType() == panglist[x+1][y+1].getType() && pang.getType() == panglist[x+1][y+2].getType()){
                                    points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x+1][y].getX(),panglist[x+1][y].getY())));   
                                    pang.setType(-1);
                                }
                            }catch (Exception e) {
                            }
                            try{
                              //오른쪽한번 이동해서  양위으로나란
                                if(pang.getType() == panglist[x+1][y+1].getType() && pang.getType() == panglist[x+1][y-1].getType()){
                                    points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x+1][y].getX(),panglist[x+1][y].getY())));   
                                    pang.setType(-1);
                                }
                            }catch (Exception e) {
                            }
                          
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            try{
                              //왼쪽한번 이동해서  나란쭉
                                if(pang.getType() == panglist[x-2][y].getType() && pang.getType() == panglist[x-3][y].getType()){
                                    points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x-1][y].getX(),panglist[x-1][y].getY())));   
                                    pang.setType(-1);
                                }
                            }catch (Exception e) {
                            }
                            
                            try{
                              //왼쪽한번 이동해서   위쪽으로나란
                                if(pang.getType() == panglist[x-1][y-1].getType() && pang.getType() == panglist[x-1][y-2].getType()){
                                    points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x-1][y].getX(),panglist[x-1][y].getY())));   
                                    pang.setType(-1);
                                }
                            }catch (Exception e) {
                            }
                            
                            try{
                              //왼쪽한번 이동해서   아래으로나란
                                if(pang.getType() == panglist[x-1][y+1].getType() && pang.getType() == panglist[x-1][y+2].getType()){
                                    points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x-1][y].getX(),panglist[x-1][y].getY())));   
                                    pang.setType(-1);
                                }
                            }catch (Exception e) {
                            }
                            
                            try{
                              //왼쪽한번 이동해서  양쪽으로나란
                                if(pang.getType() == panglist[x-1][y+1].getType() && pang.getType() == panglist[x-1][y-1].getType()){
                                    points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x-1][y].getX(),panglist[x-1][y].getY())));   
                                    pang.setType(-1);
                                }
                            }catch (Exception e) {
                            }
                            
                            
                            
                            
                            
                            
                            
                           try{ 
                            //위쪽한번 이동해서 위쪽나란쭉
                            if(pang.getType() == panglist[x][y-2].getType() && pang.getType() == panglist[x][y-3].getType()){
                                points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x][y-1].getX(),panglist[x][y-1].getY())));   
                                pang.setType(-1);
                            }
                           }catch (Exception e) {
                           }
                           
                           try{
                         //위쪽한번 이동해서   왼쪽으로나란
                            if(pang.getType() == panglist[x-1][y-1].getType() && pang.getType() == panglist[x-2][y-1].getType()){
                                points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x][y-1].getX(),panglist[x][y-1].getY())));   
                                pang.setType(-1);
                            }
                           }catch (Exception e) {
                           }
                           
                           try{
                          //위쪽한번 이동해서   오른쪽으로나란
                            if(pang.getType() == panglist[x+1][y-1].getType() && pang.getType() == panglist[x+2][y-1].getType()){
                                points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x][y-1].getX(),panglist[x][y-1].getY())));   
                                pang.setType(-1);
                            }
                           }catch (Exception e) {
                           }
                           
                           try{
                          //위쪽한번 이동해서  좌우 양쪽으로나란
                            if(pang.getType() == panglist[x-1][y-1].getType() && pang.getType() == panglist[x+1][y-1].getType()){
                                points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x][y-1].getX(),panglist[x][y-1].getY())));   
                                pang.setType(-1);
                            }
                           }catch (Exception e) {
                           }
                            
                            
                            
                           try{
                            //아래한번 이동해서  나란쭉
                            if(pang.getType() == panglist[x][y+2].getType() && pang.getType() == panglist[x][y+3].getType()){
                                points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x][y+1].getX(),panglist[x][y+1].getY())));   
                                pang.setType(-1);
                            }
                           }catch (Exception e) {
                           }
                           try{
                         //아래한번 이동해서   왼쪽으로나란
                            if(pang.getType() == panglist[x-1][y+1].getType() && pang.getType() == panglist[x-2][y+1].getType()){
                                points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x][y+1].getX(),panglist[x][y+1].getY())));   
                                pang.setType(-1);
                            }
                           }catch (Exception e) {
                           }
                           try{
                          //아래한번 이동해서   오른쪽으로나란
                            if(pang.getType() == panglist[x+1][y+1].getType() && pang.getType() == panglist[x+2][y+1].getType()){
                                points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x][y+1].getX(),panglist[x][y+1].getY())));   
                                pang.setType(-1);
                            }
                           }catch (Exception e) {
                           }
        
                           try{
                          //아래한번 이동해서  좌우 양쪽으로나란
                            if(pang.getType() == panglist[x-1][y+1].getType() && pang.getType() == panglist[x+1][y+1].getType()){
                                points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(panglist[x][y+1].getX(),panglist[x][y+1].getY())));   
                                pang.setType(-1);
                            }
                           }catch (Exception e) {
                           }
                             
                           
                           try{
                           //붐!
                             if(pang.getType() == Pang.BOOMB){
                                 points.add( new TPointToPoint( new TPoint(pang.getX(),pang.getY()),new TPoint(pang.getX()+1,pang.getY()+1)));   
                                 pang.setType(-1);
                             }
                            }catch (Exception e) {
                            }
                            
                            
                           
                            
                            //log.debug("points Size:"+points.size());
                            if(points.size()>0){
                                int selectIndex = Util.getRandomInt(points.size());
                                TPointToPoint tpt = points.get(selectIndex);
                               // log.debug("selectIndex :"+selectIndex+"    StartDragX:"+tpt.getStart().getX()+"    StartDragY:"+tpt.getStart().getY()+"    EndDragX:"+tpt.getEnd().getX()+"    EndDragX:"+tpt.getEnd().getX());
                                
                                try {
                                    Thread.sleep(10);
                                    MouseUtil.move(tpt.getStart());
                                    Thread.sleep(10);
                                    MouseUtil.press(InputEvent.BUTTON1_MASK);
                                    Thread.sleep(10);
                                    MouseUtil.move(tpt.getEnd());
                                    Thread.sleep(10);
                                    MouseUtil.release(InputEvent.BUTTON1_MASK);
                                    Thread.sleep(100);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                points.clear();
                                
                            }
                        }
                    }
                }
                
        }
        
    };
    
   public  Pang[][] panglist=new Pang[7][7];
   
Runnable run_scan = new Runnable() {
    public void run() {
        try{
        
        ImageView imgview = (ImageView) getComponent(VIEWID.IMAGE_COMPONENT.getValue());
        BufferedImage bi = DisPlayUtil.getScreenCapture(0, 0, imgview.getWidth(), imgview.getHeight());

        
        
        int w_w  =  350;
        int w_h  =  350;
        
        int s_wc = bi.getWidth()/2;
        int s_hc = bi.getHeight()/2;
        
        //String a = String.format("b_w: %d  b_h: %d  w_w: %d  w_h: %d   s_wc:%d  s_hc:%d",  bi.getWidth(), bi.getHeight(),w_w,w_h,s_wc,s_hc);
        //log.debug(a);
        
        
        int s_w = (w_w/2);
        int s_h = (w_h/2);
        int u_w = s_wc-s_w;
        int u_h = s_hc-s_h;
        
        //Graphics2D g2  = (Graphics2D) imgview.getGraphics();
        Graphics2D g2 = bi.createGraphics();
        g2.setColor(new Color(0x00ff00));
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(u_w, u_h, w_w, w_h);
        
        int box_size=50;
       panglist = new Pang[7][7];
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                int p_x         = u_w+box_size*x;
                int p_y         = u_h+box_size*y;
                int p_width     = box_size;
                int p_height    = box_size;
                
                
                if(scan_start){
                   
                    
                    Color startColorIgnore = new Color(0,0,0);
                    Color endColorIgnore = new Color(0,30,37);
                    Color color  = ImageUtil.getAvgColorIgnore(bi, p_x, p_y, p_width, p_height,startColorIgnore,endColorIgnore);
                    
                    //color = ImageUtil.upDensity(color,new Color(30,30,30) );
                        
                        
                        g2.setStroke(new BasicStroke(1));
                        g2.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),200));
                        //g2.setColor(color);
                        g2.fillRect(p_x,p_y ,p_width ,p_height );
                        //g2.drawRect(p_x,p_y ,p_width ,p_height );
                        
                        
                        //해당되는건 제외해라.
                       /* if(    startColor.getRed()<=color.getRed() && startColor.getGreen()<=color.getGreen() && startColor.getBlue()<= color.getBlue()  &&  
                                endColor.getRed()>=color.getRed() && endColor.getGreen()>=color.getGreen() && endColor.getBlue()>= color.getBlue()  ){
                            ignore_cnt++;
                            continue;
                        }*/
                        //해당되는건 제외해라.
                        g2.setColor(new Color(0xff0000));
                        if(Pang.MONKEY_S.getRed()<=color.getRed() && Pang.MONKEY_S.getGreen()<=color.getGreen() && Pang.MONKEY_S.getBlue()<= color.getBlue() &&  
                                Pang.MONKEY_E.getRed()>=color.getRed() && Pang.MONKEY_E.getGreen()>=color.getGreen() && Pang.MONKEY_E.getBlue()>= color.getBlue() 
                        ){
                            g2.drawString("원"+x+","+y, p_x+(box_size/2)-4, p_y+box_size/2);
                            panglist[x][y] = new Pang(Pang.MONKEY,p_x+(box_size/2),p_y+(box_size/2),color);
                        }
                        
                        g2.setColor(new Color(0xff0000));
                        if(Pang.PIG_S.getRed()<=color.getRed() && Pang.PIG_S.getGreen()<=color.getGreen() && Pang.PIG_S.getBlue()<= color.getBlue() &&  
                                Pang.PIG_E.getRed()>=color.getRed() && Pang.PIG_E.getGreen()>=color.getGreen() && Pang.PIG_E.getBlue()>= color.getBlue() 
                        ){
                            g2.drawString("돼"+x+","+y, p_x+(box_size/2)-4, p_y+box_size/2);
                            panglist[x][y] = new Pang(Pang.PIG,p_x+(box_size/2),p_y+(box_size/2),color);
                        }
                        
                        
                        g2.setColor(new Color(0xff0000));
                        if(Pang.RAT_S.getRed()<=color.getRed() && Pang.RAT_S.getGreen()<=color.getGreen() && Pang.RAT_S.getBlue()<= color.getBlue() &&  
                                Pang.RAT_E.getRed()>=color.getRed() && Pang.RAT_E.getGreen()>=color.getGreen() && Pang.RAT_E.getBlue()>= color.getBlue() 
                        ){
                            g2.drawString("쥐"+x+","+y, p_x+(box_size/2)-4, p_y+box_size/2);
                            panglist[x][y] = new Pang(Pang.RAT,p_x+(box_size/2),p_y+(box_size/2),color);
                        }
                        
                        g2.setColor(new Color(0xff0000));
                        if(Pang.RABBIT_S.getRed()<=color.getRed() && Pang.RABBIT_S.getGreen()<=color.getGreen() && Pang.RABBIT_S.getBlue()<= color.getBlue() &&  
                                Pang.RABBIT_E.getRed()>=color.getRed() && Pang.RABBIT_E.getGreen()>=color.getGreen() && Pang.RABBIT_E.getBlue()>= color.getBlue() 
                        ){
                            g2.drawString("토"+x+","+y, p_x+(box_size/2)-4, p_y+box_size/2);
                            panglist[x][y] = new Pang(Pang.RABBIT,p_x+(box_size/2),p_y+(box_size/2),color);
                        }
                        
                        
                        g2.setColor(new Color(0xff0000));
                        if(Pang.DOG_S.getRed()<=color.getRed() && Pang.DOG_S.getGreen()<=color.getGreen() && Pang.DOG_S.getBlue()<= color.getBlue() &&  
                                Pang.DOG_E.getRed()>=color.getRed() && Pang.DOG_E.getGreen()>=color.getGreen() && Pang.DOG_E.getBlue()>= color.getBlue() 
                        ){
                            g2.drawString("개"+x+","+y, p_x+(box_size/2)-4, p_y+box_size/2);
                            panglist[x][y] = new Pang(Pang.DOG,p_x+(box_size/2),p_y+(box_size/2),color);
                        }
                        
                        g2.setColor(new Color(0xff0000));
                        if(Pang.CHICK_S.getRed()<=color.getRed() && Pang.CHICK_S.getGreen()<=color.getGreen() && Pang.CHICK_S.getBlue()<= color.getBlue() &&  
                                Pang.CHICK_E.getRed()>=color.getRed() && Pang.CHICK_E.getGreen()>=color.getGreen() && Pang.CHICK_E.getBlue()>= color.getBlue() 
                        ){
                            g2.drawString("병"+x+","+y, p_x+(box_size/2)-4, p_y+box_size/2);
                            panglist[x][y] = new Pang(Pang.CHICK,p_x+(box_size/2),p_y+(box_size/2),color);
                        }
                        g2.setColor(new Color(0xff0000));
                        if(Pang.CAT_S.getRed()<=color.getRed() && Pang.CAT_S.getGreen()<=color.getGreen() && Pang.CAT_S.getBlue()<= color.getBlue() &&  
                                Pang.CAT_E.getRed()>=color.getRed() && Pang.CAT_E.getGreen()>=color.getGreen() && Pang.CAT_E.getBlue()>= color.getBlue() 
                        ){
                            g2.drawString("고"+x+","+y, p_x+(box_size/2)-4, p_y+box_size/2);
                            panglist[x][y] = new Pang(Pang.CAT,p_x+(box_size/2),p_y+(box_size/2),color);
                        }
                        g2.setColor(new Color(0xff0000));
                        if(Pang.BOOMB_S.getRed()<=color.getRed() && Pang.BOOMB_S.getGreen()<=color.getGreen() && Pang.BOOMB_S.getBlue()<= color.getBlue() &&  
                                Pang.BOOMB_E.getRed()>=color.getRed() && Pang.BOOMB_E.getGreen()>=color.getGreen() && Pang.BOOMB_E.getBlue()>= color.getBlue() 
                        ){
                            g2.drawString("폭"+x+","+y, p_x+(box_size/2)-4, p_y+box_size/2);
                            panglist[x][y] = new Pang(Pang.BOOMB,p_x+(box_size/2),p_y+(box_size/2),color);
                        }
                        
                        
                        
                        
                  
                }
                        
            }
        }
        

        
        
        
        
        /*
        //돼지 핑크
         Color startColor = new Color(255,169,175);
         Color endColor = new Color(255, 86, 82);
         g2.setStroke(new BasicStroke(1));
         g2.setColor(new Color(0xff0000));
        ArrayList<Rectangle> rectanglelist= ImageUtil.getScop(bi,10, u_w, u_h, w_w, w_h, startColor, endColor);
        //log.debug("get size"+rectanglelist.size()+"     u_w : "+u_w+ "    u_h: "+u_h+"    w_w:"+w_w+"    w_h:"+w_h);
        for (int j = 0; rectanglelist!=null && j < rectanglelist.size(); j++) {
            Rectangle rectangle = rectanglelist.get(j);
            if(rectangle.getWidth()<=24 && rectangle.getHeight()<=24    )
                continue;
            g2.drawRect((int)rectangle.getX(), (int)rectangle.getY(),(int) rectangle.getWidth(),(int) rectangle.getHeight());
        }
        
        //병아리 노랑
        startColor = new Color(255,130,22);
        endColor = new Color(255, 217, 90);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(0xff8216));
     rectanglelist= ImageUtil.getScop(bi,10, u_w, u_h, w_w, w_h, startColor, endColor);
       //log.debug("get size"+rectanglelist.size()+"     u_w : "+u_w+ "    u_h: "+u_h+"    w_w:"+w_w+"    w_h:"+w_h);
       for (int j = 0; rectanglelist!=null && j < rectanglelist.size(); j++) {
           Rectangle rectangle = rectanglelist.get(j);
           if(rectangle.getWidth()<=24 && rectangle.getHeight()<=24    )
               continue;
           g2.drawRect((int)rectangle.getX(), (int)rectangle.getY(),(int) rectangle.getWidth(),(int) rectangle.getHeight());
       }
       
       //강아지 파랑
       startColor = new Color(24,221,216);
       endColor = new Color(0, 131, 141);
       g2.setStroke(new BasicStroke(1));
       g2.setColor(new Color(0x428dbe));
    rectanglelist= ImageUtil.getScop(bi,10, u_w, u_h, w_w, w_h, startColor, endColor);
      //log.debug("get size"+rectanglelist.size()+"     u_w : "+u_w+ "    u_h: "+u_h+"    w_w:"+w_w+"    w_h:"+w_h);
      for (int j = 0; rectanglelist!=null && j < rectanglelist.size(); j++) {
          Rectangle rectangle = rectanglelist.get(j);
          if(rectangle.getWidth()<=24 && rectangle.getHeight()<=24    )
              continue;
          g2.drawRect((int)rectangle.getX(), (int)rectangle.getY(),(int) rectangle.getWidth(),(int) rectangle.getHeight());
      }
      
      //쥐새끼 초록
      startColor = new Color(78, 133, 8);
      endColor = new Color(210,50,89);
      g2.setStroke(new BasicStroke(1));
      g2.setColor(new Color(0x4d8408));
   rectanglelist= ImageUtil.getScop(bi,10, u_w, u_h, w_w, w_h, startColor, endColor);
     //log.debug("get size"+rectanglelist.size()+"     u_w : "+u_w+ "    u_h: "+u_h+"    w_w:"+w_w+"    w_h:"+w_h);
     for (int j = 0; rectanglelist!=null && j < rectanglelist.size(); j++) {
         Rectangle rectangle = rectanglelist.get(j);
         if(rectangle.getWidth()<=24 && rectangle.getHeight()<=24    )
             continue;
         g2.drawRect((int)rectangle.getX(), (int)rectangle.getY(),(int) rectangle.getWidth(),(int) rectangle.getHeight());
     }
       */
        
        
        
        
        
        
        
        
        
        
        imgview.setImage(bi);
        imgview.repaint();
        
        
        
        
        
        
        
        
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
};
    
    
 
    
    @Override
    public void dispose() {
        if(scheduler!=null){
            scheduler.cancel();
        }
        System.out.println("dispose");
        mouse_t.interrupt();
        super.dispose();
        
    }

    protected void finalize ( ) throws Throwable
    {
        if(scheduler!=null){
            scheduler.cancel();
        }
        System.out.println("finalize");
        mouse_t.interrupt();
        super.finalize();
    }
    
    
    
    
    
    
    
    public static void main(String[] args) throws InterruptedException {
        CopyOfAnyPangHack q = new CopyOfAnyPangHack("AnyPangHack");
    }
}
