package khh.db.terminal;

import java.sql.Connection;

import khh.db.connection.ConnectionCreator_I;
import khh.db.terminal.resultset.DBTResultRecord;
import khh.db.terminal.resultset.DBTResultSetContainer;
import khh.db.util.ConnectionUtil;

public class DBTerminalTest {
    public static void main(String[] args) throws Exception {
        DBTerminal dbt = new DBTerminal(new ConnectionCreator_I() {
            public Connection getMakeConnection() throws Exception {
                return ConnectionUtil.getConnection(
                        ConnectionUtil.MYSQL,
                        "localhost",ConnectionUtil.MYSQL_DAFAULT_PORT,
                        "khh","root","root"
                        );
            }
        });
        
        
        dbt.addConfigfile("src/khh/db/terminal/sqlmapa.xml");
        
        DBTResultSetContainer rc = dbt.executeMapQuery("gg");
        for (int i = 0; i < rc.size(); i++) {
            DBTResultRecord r =  rc.get(i);
            System.out.println(r.get("data"));
        }
        
    }
}
