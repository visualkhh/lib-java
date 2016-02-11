package khh.httpclient.common;

import java.sql.Connection;

import khh.db.connection.ConnectionCreator_I;
import khh.db.connection.pool.ConnectionMultiPool2;
import khh.db.util.ConnectionUtil;
import khh.db.util.DBUtil;
import khh.httpclient.Index;

public class ConCre implements ConnectionCreator_I {
	public Connection getMakeConnection() throws Exception {
//		return ConnectionUtil.getConnection(ConnectionUtil.MYSQL,"192.168.0.14",ConnectionUtil.MYSQL_DAFAULT_PORT,"STOCK","root","javadev");
		return Index.cp.getConnection("stock");
				//ConnectionUtil.getConnection(ConnectionUtil.MYSQL,"192.168.0.14",ConnectionUtil.MYSQL_DAFAULT_PORT,"STOCK","root","javadev");
	}

}
