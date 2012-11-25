package DBTestoracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DB
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
	    
//	    String id="dcwebusr";
//	    String passwd="daugwapdc!08";
//	    String ip="100.1.26.86";
//	    String port="1521";
//	    String sid="DCWEB";
        String id="SCOTT";
        String passwd="tiger";
        String ip="localhost";
        String port="1521";
        String sid="XE";
	    String driverPath ="oracle.jdbc.driver.OracleDriver";
		Class.forName(driverPath);
		String url =  "jdbc:oracle:thin:@"+ip+":"+port+":"+sid; 

		
		long starttime = System.currentTimeMillis();
		  System.out.println("Start "+starttime);
		
	       String sql="SELECT * from tabs";
	       //sql="select * from AATEST";
	       //sql="select TABLE_NAME,max_trans from tabs";
	       
	       for (int i = 0; i < 400; i++) {
	           sql+=" union all ";
	           sql+="select * from tabs";
	    }
		  
		Connection connction = DriverManager.getConnection(url, id, passwd);
		Statement stmt = connction.createStatement();
		ResultSet rset =  stmt.executeQuery(sql);
		ResultSetMetaData	rsmd = rset.getMetaData();
        while(rset.next()){
            for (int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                System.out.print(rsmd.getColumnName(i)+"\t\t\t"+rset.getString(i)+"\t");
            }
            System.out.println();
        }
        connction.close();
        rset.close();
        stmt.close();
        
        long endtime = System.currentTimeMillis();
        System.out.println("END "+endtime);
        System.out.println("==  "+(endtime - starttime));
	}
	
}
