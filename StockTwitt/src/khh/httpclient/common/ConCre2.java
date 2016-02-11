package khh.httpclient.common;

import java.sql.Connection;

import khh.db.connection.ConnectionCreator_I;
import khh.db.util.ConnectionUtil;
import khh.httpclient.Index2;

public class ConCre2 implements ConnectionCreator_I {
	public Connection getMakeConnection() throws Exception {
//		return ConnectionUtil.getConnection(ConnectionUtil.MYSQL,"192.168.0.14",ConnectionUtil.MYSQL_DAFAULT_PORT,"STOCK","root","javadev");
		return Index2.cp.getConnection("stock");
				//ConnectionUtil.getConnection(ConnectionUtil.MYSQL,"192.168.0.14",ConnectionUtil.MYSQL_DAFAULT_PORT,"STOCK","root","javadev");
	}

}
