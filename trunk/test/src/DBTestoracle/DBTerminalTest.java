package DBTestoracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import com.kdn.util.db.connection.ConnectionCreator_I;
import com.kdn.util.db.connection.ConnectionPool_Connection;
import com.kdn.util.db.resultset.ResultRow;
import com.kdn.util.db.resultset.ResultSetContainer;
import com.kdn.util.db.terminal.DBTerminal;

public class DBTerminalTest
{
    
    ConnectionCreator_I connectioncreator = new ConnectionCreator_I() {
        @Override
        public Connection getMakeConnection() throws Exception {
//            String id="dcwebusr";
//            String passwd="daugwapdc!08";
//            String ip="100.1.26.86";
//            String port="1521";
//            String sid="DCWEB";
//            String driverPath ="oracle.jdbc.driver.OracleDriver";
            String id="SCOTT";
            String passwd="tiger";
            String ip="localhost";
            String port="1521";
            String sid="XE";
            String driverPath ="oracle.jdbc.driver.OracleDriver";
            Class.forName(driverPath);
            String url =  "jdbc:oracle:thin:@"+ip+":"+port+":"+sid; 

            Connection connction = DriverManager.getConnection(url, id, passwd);
            return connction;
        }
    }; 
    
    DBTerminal terminal =null;
    
    public DBTerminalTest() {
        terminal = new DBTerminal(connectioncreator);
    }
    
    public void gogo() throws Exception{
       String sql="SELECT TABLE_NAME from tabs";
       //sql="select * from AATEST";
       //sql="select TABLE_NAME,max_trans from tabs";
       
       for (int i = 0; i < 10; i++) {
           sql+=" union all ";
           sql+="select TABLE_NAME from tabs";
    }
       
      // sql="SELECT * from tabs where  TABLESPACE_NAME like ?  ";
        sql="INSERT INTO goodjob (n,s)  values(?,?) ";
       // sql="delete  goodjob  ";
       
       ArrayList param = new ArrayList();
       param.add(100);
       param.add("vv현하3442");
//       ResultSetContainer rs =  terminal.executeQuery(sql,param);
       
       terminal.setAutoCommit(false);
       int rs =  terminal.executeUpdate(sql,param);
       terminal.commit();
       param.set(1, "ㅋㅋㅋㅋㅋvㅋvㅋㅋ");
       int rs2 =  terminal.executeUpdate(sql,param);
       terminal.commit();
       
       //terminal.rollback();
//       int rs =  terminal.executeUpdate(sql);
//       System.out.println(rs);
//       System.out.println(rs2);
      // terminal.commit();       
       
//       ResultSetContainer rs =  terminal.executeQuery(sql);
////       System.out.println(rs.size());
//       for (int i = 0; i < rs.size(); i++) {
//          ResultRow row = rs.get(i);
//          for (int j = 0; j < row.size(); j++) {
//              String key = row.getKey(j);
//            System.out.print(key+"\t\t\t"+ row.getString(j)+"\t");;
//          }
//            System.out.print("\t"+row.getKey(0)+":("+ row.getString(0)+")");;
//            System.out.print("\t"+row.getKey(1)+":("+ row.getInt(1)+")");;
//          System.out.println();
//       }
    }
    
    
    
	public static void main(String[] args) throws Exception
	{
	    DBTerminalTest a = new DBTerminalTest();
	    
	    long starttime = System.currentTimeMillis();
        System.out.println("Start "+starttime);
	    a.gogo();
	    long endtime = System.currentTimeMillis();
        System.out.println("END "+endtime);
        System.out.println("==  "+(endtime - starttime));
	}
}
