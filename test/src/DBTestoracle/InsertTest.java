package DBTestoracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.kdn.util.db.ConnectionUtil;
import com.kdn.util.db.DBUtil;

public class InsertTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connction = ConnectionUtil.getConnection(ConnectionUtil.ORACLE, "localhost", "1521", "XE", "DCWEBUSR", "DCWEBUSR");
        Statement stmt = connction.createStatement();
        ResultSet rset =  stmt.executeQuery("SELECT * from TABS");
        ResultSetMetaData   rsmd = rset.getMetaData();
        
        
        
        
        
//        System.out.println(DBUtil.getTableTag(rset));
        while(rset.next()){
            for (int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                System.out.print(rsmd.getColumnName(i)+"\t\t\t"+rset.getString(i)+"\t");
            }
            System.out.println();
        }
        
        System.out.println("commit");
        stmt.executeUpdate("commit");
        stmt.executeUpdate("ALTER TABLE KJT0410_HIU2 MODIFY ( DPST_PNL_YMD  NULL) ");
        System.out.println("commit end");
        
        connction.close();
        rset.close();
        stmt.close();
        
    }
}
