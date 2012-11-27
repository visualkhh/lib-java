package com.kdn.p.techexport.engin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import khh.date.util.DateUtil;
import khh.db.statement.LogPreparedStatement;
import khh.db.util.ConnectionUtil;
import khh.debug.util.DebugUtil;
import khh.file.util.FileUtil;
import khh.property.util.PropertyUtil;
import khh.string.util.StringUtil;

public class Execute extends Thread {

    private File sqlfile;
    private Systeminfo systeminfo;
    private String logtotpath;
    private String logpath;
    private String datapath;
    private ArrayList<String> errorlist;
    private int executecnt=0;
    private boolean restart=false;
    
    public Execute(Systeminfo systeminfo, File sqlfile) {
        this.systeminfo = systeminfo;
        this.sqlfile=sqlfile;
        this.errorlist = new ArrayList<String>();
    }
    
    @Override
    public void run() {
        logtotpath = sqlfile.getParent()+"\\"+systeminfo.getNAME()+"_totlog.txt";
        logpath = sqlfile.getParent()+"\\"+getName()+"_log.txt";
        datapath = sqlfile.getParent()+"\\"+getName()+"_data.txt";
        
        String ip = systeminfo.getDBIP();
        String port = systeminfo.getDBPORT();
        String sid = systeminfo.getDBSID();
        String id = systeminfo.getDBID();
        String passwd = systeminfo.getDBPASSWORD();
        
        
        
        
        DebugUtil.trace(systeminfo.getNAME()+" "+systeminfo.getDBIP()
                +" "+systeminfo.getDBPORT()+" "+systeminfo.getDBSID()+" "+systeminfo.getDBID()
                +" "+systeminfo.getDBPASSWORD()+" "+systeminfo.getSTARTYMD()+" "+systeminfo.getENDYMD()+" "+systeminfo.getSTEP());
      
        boolean iserror=false;
        Connection con=null;
        //START
        try {
            Date startdate = DateUtil.getDate("yyyyMMdd", systeminfo.getSTARTYMD());
            Date enddate = DateUtil.getDate("yyyyMMdd", systeminfo.getENDYMD());
            int step = systeminfo.getSTEP();
            
            con =ConnectionUtil.getConnection(ConnectionUtil.ORACLE, ip, port, sid, id, passwd);
            String sql = FileUtil.readeFileToString(sqlfile);
            
            

            Date bdate=startdate;
            Date adate = DateUtil.modifyDate(bdate, Calendar.DATE, step);
            
            
            while(bdate.getTime()< enddate.getTime()){
                if(adate.getTime()>=enddate.getTime() ){
                  adate =  enddate;
                }
                
                
                String bdate_s = DateUtil.dateFormat(bdate, "yyyyMMdd");
                String adate_s = DateUtil.dateFormat(adate, "yyyyMMdd");
                bdate = DateUtil.modifyDate(adate, Calendar.DATE, 1);
                adate = DateUtil.modifyDate(bdate, Calendar.DATE, step);
                this.bdate_s = bdate_s;
                this.adate_s = adate_s;
                this.state_s="새로운 날짜들어가기전";
                executeQuery(con,sql,bdate_s,adate_s);
                this.state_s="새로운 날짜 처리하고 끝남";
                this.executecnt++;;
                
                
                sleep(1000);
              // break;
            }
        
        } catch (Exception e) {
            System.out.print("=-========="+getName());
            e.printStackTrace();
                String msg = getName()+"커넥션들어가기전에..[실패]   "+getBdate_s()+"  "+getAdate_s()+"  "+e.getMessage();
                writeLog(logpath,msg);
                writeLog(logtotpath,msg);
                errorlist.add(msg);
                DebugUtil.trace(msg);
                iserror=true;
                
        }finally{
                try {
                    if(con!=null)
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        
        setRestart(iserror);
        if(isRestart()==false){
            String msg =  getName()+"[완전끝]   "+getBdate_s()+"  "+getAdate_s();
            this.state_s="[완전끝]  "+getBdate_s()+"     "+getAdate_s();
            writeLog(logpath,msg);
            DebugUtil.trace(msg);
        }else{
            run();
        }
        
       
    }

    
    String bdate_s=null;
    String adate_s=null;
    String state_s=null;
    
    private void executeQuery(Connection con, String sql, String bdate_s, String adate_s ) {
        PreparedStatement ppsmt = null;
        ResultSet rs=null;
        String excuteSQL=null;
        String logdata =  getName()+"  "+bdate_s+" "+adate_s;
       
        try {
            
            sql = StringUtil.replaceAll(sql, ";","");

//sql="select a.*,'13. 더블브레이드를 이용한 케이블포설공법' NEWTECH_CD from kit0130 a where a.oper_ntc_ymd BETWEEN ? AND ?";
                ppsmt =  new LogPreparedStatement(con,sql);
                ppsmt.setString(1, bdate_s);
                ppsmt.setString(2, adate_s);
                excuteSQL = ((LogPreparedStatement)ppsmt).getQueryString();
                
                String msg =  logdata+"[시작]   "+getBdate_s()+"  "+getAdate_s();
                writeLog(logpath,msg);
                writeLog(logtotpath, msg);
                DebugUtil.trace(msg);
                
                this.state_s="쿼리돌리기 바로전";
                rs = ppsmt.executeQuery();
                this.state_s="쿼리돌린 바로후[아직 데이터뽑기전]";
                ResultSetMetaData rsm = rs.getMetaData();
                int columncnt = rsm.getColumnCount();
                int rowcnt=0;
                while (rs.next()) {
                    String returnString="";
                    for (int i = 1; i <= columncnt; i++) {
                        returnString +=  rs.getString(i);
                        if(i==columncnt){
                        }else{
                            returnString+= "\t";
                        }
                    }
                    writeData(datapath, returnString);
                    rowcnt++;
                }
                
                this.state_s="쿼리돌린 바로후[완료]";
                msg =logdata+"[완료]   "+getBdate_s()+"  "+getAdate_s()+"  "+rowcnt;
                writeLog(logpath,  msg);
                writeLog(logtotpath,  msg);
                DebugUtil.trace(msg);
                
            
        } catch (SQLException e) {
            e.printStackTrace();
            this.state_s="쿼리날리고 데이터 에러"+e.getMessage();
            String msg =logdata+"[실패]   "+getBdate_s()+"  "+getAdate_s()+"  "+PropertyUtil.getSeparator()+excuteSQL+e.getMessage();
            writeLog(logpath,msg);
            writeLog(logtotpath, msg);
            errorlist.add(msg);
            DebugUtil.trace(msg);
        }finally{
            try {
                if(ppsmt!=null)
                ppsmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if(rs!=null)
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
    
    public ArrayList<String> getErrorList(){
        return errorlist;
    }
    

    synchronized public static void writeLog(String path,String info){
        try {
            String msg = PropertyUtil.getSeparator()+ DateUtil.getDate("yyyy/MM/dd HH:mm:ss/SSS")+"   "+info+PropertyUtil.getSeparator();
            FileUtil.writeFile(path,msg,true);
            
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    
    synchronized public static void writeData(String path,String data){
        try {
            FileUtil.writeFile(path,DateUtil.getDate("yyyy/MM/dd HH:mm:ss/SSS")+"\t"+ data+PropertyUtil.getSeparator(),true);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public boolean isRestart() {
        return restart;
    }

    private void setRestart(boolean restart) {
        this.restart = restart;
    }

    public String getBdate_s() {
        return bdate_s;
    }

    public String getAdate_s() {
        return adate_s;
    }

    public String getState_s() {
        return state_s;
    }

    public int getExecutecnt() {
        return executecnt;
    }

    
    
    
}
