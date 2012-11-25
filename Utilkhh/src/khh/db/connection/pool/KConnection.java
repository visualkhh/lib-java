package khh.db.connection.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class KConnection
{
	private Connection			connction		= null;
	private boolean				closed			= true;

	public KConnection(Connection connction)
	{
		this.connction = connction;
	}

	public Statement createStatement() throws SQLException
	{
		return connction.createStatement();
	}

	public void close()
	{
		closed = true;
	}

	public boolean isClosed()
	{
		return closed;
	}

	public void open()
	{
		closed = false;
	}
}
