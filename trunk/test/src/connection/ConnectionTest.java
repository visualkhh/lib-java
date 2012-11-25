package connection;

import java.sql.Connection;

import khh.db.connection.ConnectionCreator_I;
import khh.db.connection.pool.ConnectionMultiPool;
import khh.db.resultset.ResultRow;
import khh.db.resultset.ResultSetContainer;
import khh.db.terminal.DBTerminal;
import khh.db.util.ConnectionUtil;
import khh.db.util.DBUtil;

public class ConnectionTest {
    public static void main(String[] args) throws Exception {

        ConnectionCreator_I c = new ConnectionCreator_I() {
            @Override
            public Connection getMakeConnection() throws Exception {
                return ConnectionUtil.getConnection(ConnectionUtil.MYSQL,"localhost",ConnectionUtil.MYSQL_DAFAULT_PORT,"cooltrack_godo_co_kr","root","skfkdsk");
            }
        };
        ConnectionCreator_I c2 = new ConnectionCreator_I() {
            @Override
            public Connection getMakeConnection() throws Exception {
                return ConnectionUtil.getConnection(ConnectionUtil.MYSQL,"localhost",ConnectionUtil.MYSQL_DAFAULT_PORT,"mysql","root","skfkdsk");
            }
        };
        
        /*
        DBTerminal t = new DBTerminal(c);
        ResultSetContainer r  = t.executeQuery("select * from gd_banner");
        for (int i = 0; i < r.size(); i++) {
            ResultRow row = r.get(i);
            System.out.println(row.getString(1));
        }
        */
        
        
        
        
        ConnectionMultiPool pool =ConnectionMultiPool.getInstance();
        pool.addConnectionCreator("cooltrack_godo_co_kr", c, 100);
        pool.addConnectionCreator("mysql", c2, 100);
        
        
        
      final Connection cp =  pool.getConnection("cooltrack_godo_co_kr");
      final Connection cpp =  pool.getConnection("mysql");
       
        DBTerminal t = new DBTerminal(new ConnectionCreator_I() {
            public Connection getMakeConnection() throws Exception {
                return cp;
            }
        });
        
        ResultSetContainer r  = t.executeQuery("select * from gd_banner");
        for (int i = 0; i < r.size(); i++) {
            ResultRow row = r.get(i);
            System.out.println(row.getString(1));
        }
        
        
        System.out.println("\r\n\r\n\r\n\r\n");
        
        DBTerminal t2 = new DBTerminal(new ConnectionCreator_I() {
            public Connection getMakeConnection() throws Exception {
                return cpp;
            }
        });
        ResultSetContainer r2  = t2.executeQuery("select * from user");
        for (int i = 0; i < r2.size(); i++) {
            ResultRow row = r2.get(i);
            System.out.println(row.getString(1));
        }
        
        
        
        
        
    }
}
