package DBTestoracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import com.kdn.util.db.ConnectionUtil;
import com.kdn.util.db.connection.ConnectionCreator_I;
import com.kdn.util.db.connection.ConnectionPoolCreator_I;
import com.kdn.util.db.connection.ConnectionPool_Connection;
import com.kdn.util.db.connection.pool.ConnectionMultiPool;
import com.kdt.util.schedule.Scheduler;

public class ConnectionMultiPoolTest {
    

    
    ConnectionPoolCreator_I islcreator = new ConnectionPoolCreator_I() {
        @Override
        public ConnectionPool_Connection getMakeConnection() throws Exception {
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
        public ConnectionPool_Connection getMakeConnection() throws Exception {
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
                Connection connection = pool.getConnection(islcreatorName);
                Statement stmt = connection.createStatement();
                ResultSet rset =  stmt.executeQuery("SELECT * from tabs");
                ResultSetMetaData   rsmd = rset.getMetaData();
                while(rset.next()){
                    for (int i = 1; i <= rsmd.getColumnCount(); i++)
                    {
                       System.out.print("islrun "+rsmd.getColumnName(i)+"\t\t\t"+rset.getString(i)+"\t");
                    }
                    System.out.println();
                }
                connection.close();
                rset.close();
                stmt.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    
    
    Runnable knetrun = new Runnable() {
        @Override
        public void run() {
            try{
                Connection connection = pool.getConnection(knetcreatorName);
                PreparedStatement a  = connection.prepareStatement(null);
                Statement stmt = connection.createStatement();
                
                ResultSet rset =  stmt.executeQuery("SELECT * from tabs");
                ResultSetMetaData   rsmd = rset.getMetaData();
                while(rset.next()){
                    for (int i = 1; i <= rsmd.getColumnCount(); i++)
                    {
                        System.out.print("knet "+rsmd.getColumnName(i)+"\t\t\t"+rset.getString(i)+"\t");
                    }
                    System.out.println();
                }
                connection.close();
                rset.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    
    
    ConnectionMultiPool pool = ConnectionMultiPool.getInstance();
    String islcreatorName = "isl";
    String knetcreatorName = "knet";
    Scheduler scheduler = new Scheduler();
    
    
    public void action() throws Exception{
        pool.addConnectionCreator(islcreatorName, islcreator, 5);
        pool.addConnectionCreator(knetcreatorName, knetcreator, 5);
        scheduler.schedule(islcreatorName, islrun, 1, 2000);
        scheduler.schedule(knetcreatorName, knetrun, 1, 2000);
    }
    public static void main(String[] args) throws Exception {
        new ConnectionMultiPoolTest().action();
    }
}
