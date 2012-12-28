import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import khh.db.util.ConnectionUtil;

public class TTT {
	public static void main(String[] args) throws MalformedURLException, IOException, ClassNotFoundException, SQLException {
		Connection c = ConnectionUtil.getConnection(ConnectionUtil.MYSQL, "127.0.0.1", ConnectionUtil.MYSQL_DAFAULT_PORT, "nexon", "root", "javadev");
	PreparedStatement p =  	c.prepareStatement("select ? from bp_user");
	p.setString(1, "count(*)");
	ResultSet r = p.executeQuery();
	while(r.next()){
		System.out.println(r.getInt(1));
	}
		
	}
}
