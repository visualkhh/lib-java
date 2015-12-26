package khh.db.terminal;

import java.io.File;
import java.sql.Connection;

import org.w3c.dom.Document;

import khh.db.connection.ConnectionCreator_I;
import khh.db.terminal.resultset.DBTResultRecord;
import khh.db.terminal.resultset.DBTResultSetContainer;
import khh.db.util.ConnectionUtil;
import khh.xml.XMLparser;

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
        
        
        
        XMLparser xml = new XMLparser(new File("src/khh/db/terminal/doc.xml"));
        
        Document doc = dbt.execute(xml.getDocument());
        XMLparser p = new XMLparser(doc);
        System.out.println( p.getString() );
        
//        for (int i = 0; i < rc.size(); i++) {
//            DBTResultRecord r =  rc.get(i);
//            System.out.println(r.get("data"));
//        }
        
        
        
        /*
         DBTResultSetContainer g = db.executeMapQuery("test");
		String a = "";
		for (int i = 0; i < g.size(); i++) {
			DBTResultRecord r = g.get(i);
			log.debug("--> "+r.getString("A"));
			a+="_"+r.getString("A");
		}
         */
    }
}
