package com.kdn.p.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.SliderUI;

import com.kdn.gui.frame.FrameFrame;
import com.kdn.util.db.ConnectionUtil;
import com.kdn.util.db.connection.ConnectionCreator_I;
import com.kdn.util.db.connection.ConnectionPoolCreator_I;
import com.kdn.util.db.connection.ConnectionPool_Connection;
import com.kdn.util.db.connection.pool.ConnectionMultiPool;
import com.kdn.util.db.statement.LogPreparedStatement;
import com.kdn.util.property.PropertyUtil;
import com.kdt.util.schedule.Scheduler;

public class ISL_GENPROC1A extends FrameFrame
{
	public static enum VIEWID{
	    START_DATE("start_date"),
		END_DATE("end_date"),
		BTN("obtn"),
		INFO("info"),
		EXECUTELIST("executelist");
		String id;
		VIEWID(String id){
			this.id=id;
		}
		public String getValue(){
			return this.id;
		}
	}

	public ISL_GENPROC1A(String title)
	{
		super(title);
		super.flow();
	}
	JPanel toolbar;
	JTextField start_date  ;
	JTextField end_date  ;
//	TextArea textarea;
	JTextArea executelist;
	Button cutButton;
	@Override
	public void onViewSetting()
	{
		setBounds(10,10,400,400);
		setVisible(true);
		
		
		 toolbar = new JPanel();
		 toolbar.setBackground(new Color(80, 80, 80));
		 start_date = new JTextField(10); start_date.setName(VIEWID.START_DATE.getValue());
		 end_date = new JTextField(10); end_date.setName(VIEWID.END_DATE.getValue());
		 cutButton = new Button("Replace Start");
		 cutButton.setName(VIEWID.BTN.getValue());
		 
		 toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
		 
		 toolbar.add(start_date);
		 toolbar.add(end_date);
		 toolbar.add(cutButton);
		add(toolbar,BorderLayout.NORTH);
		
		
//		textarea = new TextArea();
//		 textarea.setName(VIEWID.INFO.getValue());
//		add(textarea,BorderLayout.CENTER);
		
		
		executelist = new JTextArea();
		 executelist.setName(VIEWID.EXECUTELIST.getValue());
		add(executelist,BorderLayout.CENTER);
		
		
		
//		this.pack();
	}

	@Override
	public void onDataSetting()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddListener()
	{
		Button jb = (Button) getComponent(VIEWID.BTN.getValue());
		jb.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				onAction(1);
				
			}
		});

	}

//	@Override
//	public void onAction(int gb, Object o)
//	{
//	    JTextArea executelist = (JTextArea) getComponent(VIEWID.EXECUTELIST.getValue());
//	    JTextField start_date = (JTextField) getComponent(VIEWID.START_DATE.getValue());
//	    JTextField end_date = (JTextField) getComponent(VIEWID.END_DATE.getValue());
//
//	    String base = executelist.getText();
//	    
//	    
//	    executelist.append("DECLARE"+PropertyUtil.getSeparator()); 
//	    executelist.append("S_DATE_C VARCHAR2(200);"+PropertyUtil.getSeparator()); 
//	    executelist.append(" BEGIN "+PropertyUtil.getSeparator()); 
//	    
//	    try {
//            Date start_date_d=DateUtil.getDate("yyyyMMdd", start_date.getText());
//            Date end_date_d= DateUtil.getDate("yyyyMMdd", end_date.getText());
//        
//            while(start_date_d.getTime()<= end_date_d.getTime()){
//                
//                executelist.append("S_DATE_C := '"+DateUtil.dateFormat(start_date_d, "yyyyMMdd")+"';"+PropertyUtil.getSeparator());
//                executelist.append("USRISL.GENPROC1A ( S_DATE_C );"+PropertyUtil.getSeparator()); 
//                executelist.append("COMMIT; "+PropertyUtil.getSeparator()); 
//                start_date_d = DateUtil.modifyDate(start_date_d, Calendar.DATE, 1);
//            }
//        
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//	    
//	    executelist.append("END;"+PropertyUtil.getSeparator());  
//	}
	
	
	
	
	
	
	ConnectionPoolCreator_I islcreator = new ConnectionPoolCreator_I() {
        @Override
        public ConnectionPool_Connection getMakeConnection() throws ClassNotFoundException, SQLException {
            String ip = "168.78.203.138";
          String port = "1521";
          String sid = "Ora9";
          String id = "usrisl";
          String passwd = "YOGB*HJVCG50";
          Connection con =ConnectionUtil.getConnection(ConnectionUtil.ORACLE, ip, port, sid, id, passwd);
            return new ConnectionPool_Connection(con);
        }
    };
	
    ConnectionPoolCreator_I knetcreator = new ConnectionPoolCreator_I() {
        @Override
        public ConnectionPool_Connection getMakeConnection() throws ClassNotFoundException, SQLException {
            String ip = "100.1.26.72";
          String port = "1521";
          String sid = "ORKV";
          String id = "usrtech";
          String passwd = "GKL76HG*HOHH";
          Connection con =ConnectionUtil.getConnection(ConnectionUtil.ORACLE, ip, port, sid, id, passwd);
            return new ConnectionPool_Connection(con);
            
        }
    };
    
	
	Runnable islrun  =  new Runnable() {
        @Override
        public void run() {
            
            try{
                Connection c = pool.getConnection(islcreatorName);
                Thread.sleep(1000);
                c.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    
    
    Runnable knetrun = new Runnable() {
        @Override
        public void run() {
            try{
                Connection c = pool.getConnection(knetcreatorName);
                Thread.sleep(1000);
                c.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
	
	
    ConnectionMultiPool pool = ConnectionMultiPool.getInstance();
    String islcreatorName = "isl";
    String knetcreatorName = "knet";
    Scheduler scheduler = new Scheduler();
	@Override
	public void onAction(int gb, Object o){
	    try{
	        pool.addConnectionCreator(islcreatorName, islcreator, 5);
	        pool.addConnectionCreator(knetcreatorName, knetcreator, 5);
	        scheduler.schedule(islcreatorName, islrun, 1, 50);
	        scheduler.schedule(knetcreatorName, knetrun, 1, 50);
	        
	        
	    }catch (Exception e) {
	        e.printStackTrace();
        }
//	    pool.addConnectionCreator(creatorName, creator, maximumConnectionSize)
	    
	}
	
	
	@Override
	public void dispose() {
	    scheduler.cancel();
	    super.dispose();
	}
	
//	
//	@Override
//	public void onAction(int gb, Object o)
//	{
//        String ip = "168.78.203.138";
//        String port = "1521";
//        String sid = "Ora9";
//        String id = "usrisl";
//        String passwd = "YOGB*HJVCG50";
//        JTextArea executelist = (JTextArea) getComponent(VIEWID.EXECUTELIST.getValue());
////        JTextField b = getCon
//        Connection con=null;
//        PreparedStatement ppsmt = null;
//        ResultSet rs=null;
//        String sql="select * from tab where rownum <4";
//	    try {
//	        executelist.append("con");
//	        System.out.println("con");
//            con =ConnectionUtil.getConnection(ConnectionUtil.ORACLE, ip, port, sid, id, passwd);
//            System.out.println("con end");
//            executelist.append("con end");
//            ppsmt =  new LogPreparedStatement(con,sql);
////            ppsmt.setString(1, bdate_s);
////            ppsmt.setString(2, adate_s);
////            excuteSQL = ((LogPreparedStatement)ppsmt).getQueryString();
//            rs = ppsmt.executeQuery();
//            
//            ResultSetMetaData rsm = rs.getMetaData();
//            int columncnt = rsm.getColumnCount();
//            int rowcnt=0;
//            
//            while (rs.next()) {
//                String returnString="";
//                for (int i = 1; i <= columncnt; i++) {
//                    returnString +=  rs.getString(i);
//                    if(i==columncnt){
//                    }else{
//                        returnString+= ",";
//                    }
//                }
//                rowcnt++;
//                executelist.append(returnString+PropertyUtil.getSeparator());
//                System.out.println(returnString);
//            }
//            
//            
//            ppsmt.close();
//            ppsmt.close();
//            ppsmt.close();
//            
//            
//            
//            
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//	
//	}

}
