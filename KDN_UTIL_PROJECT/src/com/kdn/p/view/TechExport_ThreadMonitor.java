package com.kdn.p.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.Thread.State;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.kdn.gui.frame.FrameFrame;
import com.kdn.gui.util.UtilGUI;
import com.kdn.p.techexport.engin.Execute;
import com.kdn.p.techexport.engin.STD;
import com.kdn.p.techexport.engin.Systeminfo;
import com.kdn.util.collection.DuplicationArrayList;
import com.kdn.util.property.PropertyUtil;
import com.kdt.util.Utilities;
import com.kdt.util.schedule.Scheduler;
import com.kdt.util.xml.XMLparser;

public class TechExport_ThreadMonitor extends FrameFrame
{
    public static enum VIEWID{
        SERVER_INFO("server_info"),
        STATUS_INFO("status_info"),
        REFRESH_INFO("refresh_info");
        String id;
        VIEWID(String id){
            this.id=id;
        }
        public String getValue(){
            return this.id;
        }
    }
    
    
    public static enum ACTION{
        REFRESH(Utilities.getNextNumber());
        int id;
        ACTION(int id){
            this.id=id;
        }
        public int getValue(){
            return this.id;
        }
    }

    
    FrameFrame context = this;
    Systeminfo systeminfo;
    STD std;
    private Scheduler monitor_scheduler =null;
    public TechExport_ThreadMonitor(String title,Systeminfo systeminfo , STD std)
    {
        super(title);
        this.systeminfo = systeminfo;
        this.std= std;
        this.monitor_scheduler = new Scheduler();
        super.flow();
    }
    
    @Override
    public void onViewSetting()
    {
        setBounds(10,10,400,400);
        setVisible(true);
        
        JPanel toolbar = new JPanel();
        toolbar.setBackground(new Color(0, 0, 0));
        toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));


        JTextField  server_info = new JTextField(30);  server_info.setName(VIEWID.SERVER_INFO.getValue());
        toolbar.add(server_info);
        add(toolbar,BorderLayout.NORTH);
        
        
        
        JTextArea  status_info= new JTextArea(); status_info.setName(VIEWID.STATUS_INFO.getValue());
        add(status_info,BorderLayout.CENTER);
        
        JTextField refreshcnt= new JTextField(20);
        refreshcnt.setName(VIEWID.REFRESH_INFO.getValue());
        add(refreshcnt,BorderLayout.SOUTH);
//        
        
        
//        this.pack();
    }

    @Override
    public void onDataSetting()
    {
        JTextField serverinfo_field = (JTextField) getComponent(VIEWID.SERVER_INFO.getValue());
        String title=systeminfo.getNAME()+"("+systeminfo.getDBSID()+")    "+systeminfo.getSTARTYMD()+"  "+systeminfo.getENDYMD();
        serverinfo_field.setText(title);
        
       onAction(ACTION.REFRESH.getValue());

    }

    @Override
    public void onAddListener()
    {

    }

    @Override
    public void onAction(int gb, Object o)
    {
//        
        if(gb==ACTION.REFRESH.getValue()){
//            onAction_Refresh();
            try {
                monitor_scheduler.schedule("minitor", monitor, 0,1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
      
        
    }

    
int refresh_cnt=0;
    Runnable monitor = new Runnable() {
        
        @Override
        public void run() {
            
            JTextField cntfeld =  (JTextField) context.getComponent(VIEWID.REFRESH_INFO.getValue());
            JTextArea status_area =  (JTextArea) context.getComponent(VIEWID.STATUS_INFO.getValue());
            status_area.setText("");
            
         ArrayList<Execute>  sigong_tlist = std.getSigongThread();
         ArrayList<Execute>  jungong_tlist = std.getJungongThread();
            
           
           for (int i = 0; i < sigong_tlist.size(); i++) {
               Execute t = sigong_tlist.get(i);
               State tstate = t.getState();
               ArrayList<String> errorlist = t.getErrorList();
               status_area.append("이름:"+t.getName()+"\t쓰레드상태:"+t.getState()+ "\t현시작일:"+t.getBdate_s()+"\t현끝날일:"+t.getAdate_s()+"\t현상태:"+t.getState_s()+"\t에러수:"+errorlist.size()+"\t실행수:"+t.getExecutecnt()+PropertyUtil.getSeparator());
               for (int j = 0; j < errorlist.size(); j++) {
                   status_area.append("\terror->   "+errorlist.get(j)+PropertyUtil.getSeparator());
               }
               
           }
           for (int i = 0; i < jungong_tlist.size(); i++) {
               Execute t = jungong_tlist.get(i);
               State tstate = t.getState();
               ArrayList<String> errorlist = t.getErrorList();
               status_area.append("이름:"+t.getName()+"\t쓰레드상태:"+t.getState()+ "\t현시작일:"+t.getBdate_s()+"\t현끝날일:"+t.getAdate_s()+"\t현상태:"+t.getState_s()+"\t에러수:"+errorlist.size()+"\t실행수:"+t.getExecutecnt()+PropertyUtil.getSeparator());
               for (int j = 0; j < errorlist.size(); j++) {
                   status_area.append("\terror->   "+errorlist.get(j)+PropertyUtil.getSeparator());
               }
               
           }
           
           cntfeld.setText(refresh_cnt+"");
           refresh_cnt++;
           
           
        }
    };
    
    
    
    
    public void dispose() {
        monitor_scheduler.cancel();
        super.dispose();
    };
}
