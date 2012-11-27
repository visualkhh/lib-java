package com.kdn.p.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import khh.gui.component.frame.FrameFrame;
import khh.gui.util.GUIUtil;
import khh.util.Util;
import khh.xml.XMLparser;

import com.kdn.p.techexport.engin.STD;
import com.kdn.p.techexport.engin.Systeminfo;

public class TechExport extends FrameFrame
{
    public static enum VIEWID{
        SERVER_INFO("server_info"),
        SIGONG_INFO("sigong_info"),
        JUNGONG_INFO("jungong_info"),
        LOAD_BTN("load_btn"),
        RUN_BTN("run_btn"),
        SYSTEM_LAYOUT("system_layout"),
        
        
        
        NAME_B("name_b"),
        BTN("obtn"),
        INFO("info"),
        INFO2("info2"),
        PATH("path");
        String id;
        VIEWID(String id){
            this.id=id;
        }
        public String getValue(){
            return this.id;
        }
    }
    
    
    public static enum ACTION{
        SERVER_INFO_LOAD(Util.getNextNumber());
        int id;
        ACTION(int id){
            this.id=id;
        }
        public int getValue(){
            return this.id;
        }
    }

    
//    FrameFrame context = this;
    public TechExport(String title)
    {
        super(title);
        super.flow();
    }
    
    @Override
    public void onViewSetting()
    {
        setBounds(10,10,400,400);
        setVisible(true);
        
        JPanel toolbar = new JPanel();
        toolbar.setBackground(new Color(0, 0, 0));
        toolbar.setLayout(new GridBagLayout());
//        toolbar.setLayout(new GridLayout(3,1));



        
        
        
        
        
        JTextField server_info = new JTextField("C:\\추출\\지사계정.xml"); server_info.setName(VIEWID.SERVER_INFO.getValue());    GUIUtil.setSize(server_info,220,20);
        JTextField sigong_info = new JTextField("C:\\추출\\지사별 신기술추출\\시공_2000"); sigong_info.setName(VIEWID.SIGONG_INFO.getValue());  GUIUtil.setSize(sigong_info,220,20);
        JTextField jungong_info = new JTextField("C:\\추출\\지사별 신기술추출\\준공_1000"); jungong_info.setName(VIEWID.JUNGONG_INFO.getValue());  GUIUtil.setSize(jungong_info,220,20);
//        JTextField server_info = new JTextField(); server_info.setName(VIEWID.SERVER_INFO.getValue());    GUIUtil.setSize(server_info,220,20);
//        JTextField sigong_info = new JTextField(); sigong_info.setName(VIEWID.SIGONG_INFO.getValue());  GUIUtil.setSize(sigong_info,220,20);
//        JTextField jungong_info = new JTextField(); jungong_info.setName(VIEWID.JUNGONG_INFO.getValue());  GUIUtil.setSize(jungong_info,220,20);
        
    
        Button loadbtn = new Button("Load"); loadbtn.setName(VIEWID.LOAD_BTN.getValue()); GUIUtil.setSize(loadbtn,40,20);
        Label serverl  = new Label("server_info");    GUIUtil.setSize(serverl, 40,20); 
        Label sigongl  = new Label("sigong_info"); GUIUtil.setSize(sigongl, 40,20);
        Label jungongl  = new Label("jungong_info");GUIUtil.setSize(jungongl, 40,20);
        
        toolbar.add(serverl,GUIUtil.getGridBagConstraints(0, 0, 1, 1,GridBagConstraints.BOTH));
        toolbar.add(sigongl,GUIUtil.getGridBagConstraints(0, 1, 1, 1,GridBagConstraints.BOTH));
        toolbar.add(jungongl,GUIUtil.getGridBagConstraints(0, 2, 1, 1,GridBagConstraints.BOTH));
        toolbar.add(server_info,GUIUtil.getGridBagConstraints(1, 0, 1, 1,GridBagConstraints.BOTH));
        toolbar.add(sigong_info,GUIUtil.getGridBagConstraints(1, 1, 1, 1,GridBagConstraints.BOTH));
        toolbar.add(jungong_info,GUIUtil.getGridBagConstraints(1, 2, 1, 1,GridBagConstraints.BOTH));
        toolbar.add(loadbtn,GUIUtil.getGridBagConstraints(3, 0, 1, 3,GridBagConstraints.BOTH));
        add(toolbar,BorderLayout.NORTH);
        
        ///////////////
        
        JPanel systemlayout = new JPanel();
        systemlayout.setBackground(new Color(220,0,0));
        systemlayout.setLayout(new GridLayout(20,1));
        systemlayout.setName(VIEWID.SYSTEM_LAYOUT.getValue());
//        System.out.println(systemlayout);
        
//        JPanel pan = new JPanel();
//        pan.setBackground(new Color(10,255,10));
//        pan.setPreferredSize(new Dimension(40,40));
//        pan.setLayout(new FlowLayout());
//        pan.add(new Label("aa"));
//        systemlayout.add(pan);
//        
        add(systemlayout,BorderLayout.CENTER);
        
//      textarea = new TextArea();
//       textarea.setName(VIEWID.INFO.getValue());
//       
//       textarea2 = new TextArea();
//       textarea2.setName(VIEWID.INFO.getValue());
//       
//       JPanel   toolbar2 = new JPanel();
//       toolbar2.setLayout(new FlowLayout(FlowLayout.CENTER));
//       
//       toolbar2.add(textarea);
//       toolbar2.add(textarea2);
//       
//      add(toolbar2,BorderLayout.CENTER);
//        //////////
//        
//        path = new JTextField(20);
//         path.setName(VIEWID.PATH.getValue());
//        add(path,BorderLayout.SOUTH);
//        
        
        
       // this.pack();
    }

    @Override
    public void onDataSetting()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAddListener()
    {
        Button jb = (Button) getComponent(VIEWID.LOAD_BTN.getValue());
        jb.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                onAction(ACTION.SERVER_INFO_LOAD.getValue());
                
            }
        });
//         jb = (Button) getComponent(VIEWID.CSVBTN.getValue());
//        jb.addActionListener( new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0)
//            {
//                onAction(2);
//                
//            }
//        });

    }

    @Override
    public void onAction(int gb, Object o)
    {
//        
        if(gb==ACTION.SERVER_INFO_LOAD.getValue()){
            onAction_Serverinfo();
        }
      
        
    }

    
    
    private void onAction_Serverinfo() {
        JTextField serverinfo = (JTextField) getComponent(VIEWID.SERVER_INFO.getValue());
        JTextField sigonginfo = (JTextField) getComponent(VIEWID.SIGONG_INFO.getValue());
        JTextField jungonginfo = (JTextField) getComponent(VIEWID.JUNGONG_INFO.getValue());
        JPanel system_layout = (JPanel) getComponent(VIEWID.SYSTEM_LAYOUT.getValue());
//        System.out.println(serverinfo.getText());
        system_layout.removeAll();
        try{
            XMLparser parser = new XMLparser(serverinfo.getText());
            String xmlpath="//STD/SYSTEM";
            int size =  parser.getInt("count("+xmlpath+")");
            
            system_layout.setLayout(new GridLayout(size,1));
            
            for (int i = 1; i <= size; i++) {
                Systeminfo s = new Systeminfo();
                String subxmlpath = (xmlpath+"["+i+"]");
                String NAME=parser.getString(subxmlpath+"/NAME");
                String DBIP=parser.getString(subxmlpath+"/DBIP");
                String DBPORT=parser.getString(subxmlpath+"/DBPORT");
                String DBSID=parser.getString(subxmlpath+"/DBSID");
                String DBID=parser.getString(subxmlpath+"/DBID");
                String DBPASSWORD=parser.getString(subxmlpath+"/DBPASSWORD");
                String STARTYMD=parser.getString(subxmlpath+"/STARTYMD");
                String ENDYMD=parser.getString(subxmlpath+"/ENDYMD");
                Integer STEP=parser.getInt(subxmlpath+"/STEP");
                
                s.setNAME(NAME);
                s.setDBIP(DBIP);
                s.setDBPORT(DBPORT);
                s.setDBSID(DBSID);
                s.setDBID(DBID);
                s.setDBPASSWORD(DBPASSWORD);
                s.setSTARTYMD(STARTYMD);
                s.setENDYMD(ENDYMD);
                s.setSTEP(STEP);
                
                s.setSIGONG_PATH(sigonginfo.getText());
                s.setJUNGONF_PATH(jungonginfo.getText());
                
                
                
                
                system_layout.add(getSystemItemPanel(s));
                
                
                this.validate();
                //System.out.println(NAME+" "+DBIP+" "+DBPORT+" "+DBSID+" "+DBID+" "+DBPASSWORD+" "+STARTYMD+" "+ENDYMD+" "+STEP );
                
//                STD std =  new STD(s);
//                std.start();
                
                //break;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        validate();
//        pack();
    }

     public static final String STATUS_LABEL="STATUS_LABEL"; 
     public static final String START_BTN="START_BTN"; 
     public static final String THREAD_BTN="THREAD_BTN"; 
     
     private JPanel getSystemItemPanel(final Systeminfo s) throws IOException{
         JPanel pan = new JPanel();
         pan.setBackground( new Color(0,255,0));
         pan.setLayout(new FlowLayout(FlowLayout.LEFT));
         pan.add(new JLabel("이름 : "+s.getNAME()+"("+s.getDBSID()+")"));
         
         JLabel status = new JLabel("-");
         status.setForeground(Color.RED);
//         status.setBackground();
         status.setName(s.getDBSID()+STATUS_LABEL);
         pan.add(status);
         
         JButton b = new JButton("처음 시작");
         b.setName(s.getDBSID()+START_BTN);
         pan.add(b);
         
        JButton t = new JButton("세부Thread상태");
        t.setName(s.getDBSID()+THREAD_BTN);
        pan.add(t);
         
         
       final  STD std = new STD(context,s);
        
         b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    std.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }
        });
         
         t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new TechExport_ThreadMonitor(s.getNAME(),s,std);
            }
        });
         
         
         
         return pan;
     }

    
}
