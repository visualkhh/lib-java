package com.kdn.p.techexport.engin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.Thread.State;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.plaf.SliderUI;

import com.kdn.gui.frame.FrameFrame;
import com.kdn.p.view.TechExport;
import com.kdn.util.collection.DuplicationArrayList;
import com.kdn.util.file.FileUtilKDN;
import com.kdt.util.schedule.Scheduler;
import com.khh.util.debug.DebugUtil;

public class STD {
    private Systeminfo systeminfo;
//    private String sigongpath="C:\\추출\\지사별 신기술추출\\시공_2000";
//    private String  jungongpath="C:\\추출\\지사별 신기술추출\\준공_1000";
    
    private ArrayList<Execute> sigong_tlist = new ArrayList<Execute>();
    private ArrayList<Execute> jungong_tlist = new ArrayList<Execute>();
    private Scheduler monitor_scheduler =null;
    private FrameFrame context;
    
    public STD(FrameFrame context,Systeminfo systeminfo) throws IOException {
        this.context = context;
        this.systeminfo = systeminfo;
        this.monitor_scheduler = new Scheduler();
        init();
    }
    
    
    private void init() throws IOException {
        FilenameFilter filenamefilter = new FilenameFilter() {
            @Override
            public boolean accept(File arg0, String filename) {
                return filename.endsWith(".sql"); 
//                return true;
            }
        };
        
        File[] sigongfiles =null;;
        File[] jungongfiles =null;;
        try{
            sigongfiles =  FileUtilKDN.getFileList(new File(systeminfo.getSIGONG_PATH()),filenamefilter);
        }catch (Exception e) {
        }
        try{
            jungongfiles = FileUtilKDN.getFileList(new File(systeminfo.getJUNGONF_PATH()),filenamefilter);
        }catch (Exception e) {
        }
        
        
        if(sigongfiles!=null){
            for (int i = 0; i < sigongfiles.length; i++) {
                File file = sigongfiles[i];
                Execute execute = new Execute(systeminfo,file);
                execute.setName(systeminfo.getNAME()+"("+systeminfo.getDBSID()+")"+file.getName());
                sigong_tlist.add(execute);
            }
        }
        
        if(jungongfiles!=null){
            for (int i = 0; i < jungongfiles.length; i++) {
                File file =  jungongfiles[i];
                Execute execute = new Execute(systeminfo,file);
                execute.setName(systeminfo.getNAME()+"("+systeminfo.getDBSID()+")"+file.getName());
                jungong_tlist.add(execute);
            }
        }
        
//      File file = sigongfiles[0];
//      DebugUtil.trace(file.getName()+" Start=========");
//      Execute execute = new Execute(systeminfo,file);
//      execute.setName(systeminfo.getNAME()+"("+systeminfo.getDBSID()+")"+file.getName());
//      sigong_tlist.add(execute);
        
        
    }


    public void start() throws IOException{
        
        for (int i = 0; i < sigong_tlist.size(); i++) {
            sigong_tlist.get(i).start();
        }
        for (int i = 0; i < jungong_tlist.size(); i++) {
            jungong_tlist.get(i).start();
        }
        
        
        try {
            monitor_scheduler.schedule("monitoring", threadmonitoring, 0, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
//    String THREAD_STATE_NEW = "NEW";//실행전
//    String THREAD_STATE_RUNNABLE = "RUNNABLE";//실행가능
//    String THREAD_STATE_WAITING = "WAITING";//메서드호출중
//    String THREAD_STATE_TIMED_WAITING = "TIMED_WAITING";//sleep 메서드 호출중
//    String THREAD_STATE_BLOCKED  = "BLOCKED";//다른 스레드의 동기화 블록이나 동기화 메소드가 끝나기를 기다리는 상태 
//    String THREAD_STATE_TERMINATED  = "TERMINATED";//실행후
    
    
    Runnable threadmonitoring  =  new Runnable() {
        @Override
        public void run() {
            boolean is_run = false;
            
            int si_error = 0;
            int ju_error = 0;
            
            
            
            for (int i = 0; i < sigong_tlist.size(); i++) {
                Execute t = sigong_tlist.get(i);
                State tstate = t.getState();
                si_error += t.getErrorList().size(); 
                if(State.NEW.equals(tstate) || State.RUNNABLE.equals(tstate) || State.WAITING.equals(tstate)||State.TIMED_WAITING.equals(tstate)|| State.BLOCKED.equals(tstate))
                    is_run = true;
            }
            
            
            for (int i = 0; i < jungong_tlist.size(); i++) {
                Execute t = jungong_tlist.get(i);
                State tstate = t.getState();
                ju_error += t.getErrorList().size(); 
                if(State.NEW.equals(tstate) || State.RUNNABLE.equals(tstate) || State.WAITING.equals(tstate)||State.TIMED_WAITING.equals(tstate)|| State.BLOCKED.equals(tstate))
                    is_run = true;
            }
            
            JButton startbtn = (JButton) context.getComponent(systeminfo.getDBSID()+TechExport.START_BTN);
            JLabel statelabel = (JLabel) context.getComponent(systeminfo.getDBSID()+TechExport.STATUS_LABEL);
            statelabel.setText("에러 : 시("+si_error+")  준("+ju_error+")");
            if(is_run){
                startbtn.setText("시작 시("+sigong_tlist.size()+")  준("+jungong_tlist.size()+")");
            }else{
                startbtn.setText("완전끝  시("+sigong_tlist.size()+")  준("+jungong_tlist.size()+")");
                monitor_scheduler.cancel();
            }
        }
    };
    
    
    
    
    public ArrayList<Execute> getSigongThread(){
        return sigong_tlist;        
    }
    public ArrayList<Execute> getJungongThread(){
        return jungong_tlist;        
    }
    
    
    
    
}
    
