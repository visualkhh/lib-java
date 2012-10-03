package khh.db.connection.pool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;


public class ConnectionPool_Connection implements Connection
{
	private Connection			connection		= null;
	private boolean				closed			= true;

	public ConnectionPool_Connection(Connection connction)
	{
		this.connection = connction;
	}

	
	
	
	
	
	
	public Connection getRealConnection(){
	    return connection;
	}
	///////////////
   @Override
	public Statement createStatement() throws SQLException
	{
		return connection.createStatement();
	}
   @Override
	public void close()
	{
		closed = true;
	}
   @Override
	public boolean isClosed()
	{
		return closed;
	}

	public void open()
	{
		closed = false;
	}
///////////////////////////////////////////////
   @Override
    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        return getRealConnection().isWrapperFor(arg0);
    }

    @Override
    public <T> T unwrap(Class<T> arg0) throws SQLException {
        return getRealConnection().unwrap(arg0);
    }

    @Override
    public void clearWarnings() throws SQLException {
        getRealConnection().clearWarnings();
    }

    @Override
    public void commit() throws SQLException {
        getRealConnection().commit();
    }

    @Override
    public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
        return getRealConnection().createArrayOf(arg0, arg1);
    }

    @Override
    public Blob createBlob() throws SQLException {
        return getRealConnection().createBlob();
    }

    @Override
    public Clob createClob() throws SQLException {
        return getRealConnection().createClob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        return getRealConnection().createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return getRealConnection().createSQLXML();
    }

    @Override
    public Statement createStatement(int arg0, int arg1) throws SQLException {
        return connection.createStatement(arg0, arg1);
    }

    @Override
    public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
        return connection.createStatement(arg0, arg1, arg2);
    }

    @Override
    public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
        return getRealConnection().createStruct(arg0, arg1);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return getRealConnection().getAutoCommit();
    }

    @Override
    public String getCatalog() throws SQLException {
        return getRealConnection().getCatalog();
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return getRealConnection().getClientInfo();
    }

    @Override
    public String getClientInfo(String arg0) throws SQLException {
        return getRealConnection().getClientInfo(arg0);
    }

    @Override
    public int getHoldability() throws SQLException {
        return getRealConnection().getHoldability();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return getRealConnection().getMetaData();
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return getRealConnection().getTransactionIsolation();
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return getRealConnection().getTypeMap();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return getWarnings();
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return getRealConnection().isReadOnly();
    }

    @Override
    public boolean isValid(int arg0) throws SQLException {
        return getRealConnection().isValid(arg0);
    }

    @Override
    public String nativeSQL(String arg0) throws SQLException {
        return getRealConnection().nativeSQL(arg0);
    }

    @Override
    public CallableStatement prepareCall(String arg0) throws SQLException {
        return getRealConnection().prepareCall(arg0);
    }

    @Override
    public CallableStatement prepareCall(String arg0, int arg1, int arg2) throws SQLException {
        return getRealConnection().prepareCall(arg0);
    }

    @Override
    public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
        return getRealConnection().prepareCall(arg0);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0) throws SQLException {
        return connection.prepareStatement(arg0);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
        return connection.prepareStatement(arg0, arg1);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
        return connection.prepareStatement(arg0, arg1);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
        return connection.prepareStatement(arg0, arg1);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) throws SQLException {
        return connection.prepareStatement(arg0, arg1, arg2);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
        return connection.prepareStatement(arg0, arg1, arg2);
    }

    @Override
    public void releaseSavepoint(Savepoint arg0) throws SQLException {
        getRealConnection().releaseSavepoint(arg0);
    }

    @Override
    public void rollback() throws SQLException {
        connection.rollback();
    }

    @Override
    public void rollback(Savepoint arg0) throws SQLException {
        connection.rollback(arg0);
    }

    @Override
    public void setAutoCommit(boolean arg0) throws SQLException {
        getRealConnection().setAutoCommit(arg0);
    }

    @Override
    public void setCatalog(String arg0) throws SQLException {
        getRealConnection().setCatalog(arg0);
    }

    @Override
    public void setClientInfo(Properties arg0) throws SQLClientInfoException {
        getRealConnection().setClientInfo(arg0);
    }

    @Override
    public void setClientInfo(String arg0, String arg1) throws SQLClientInfoException {
        getRealConnection().setClientInfo(arg0,arg1);
    }

    @Override
    public void setHoldability(int arg0) throws SQLException {
        getRealConnection().setHoldability(arg0);
    }

    @Override
    public void setReadOnly(boolean arg0) throws SQLException {
        getRealConnection().setReadOnly(arg0);
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return getRealConnection().setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String arg0) throws SQLException {
        return getRealConnection().setSavepoint(arg0);
    }

    @Override
    public void setTransactionIsolation(int arg0) throws SQLException {
        getRealConnection().setTransactionIsolation(arg0);
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
        getRealConnection().setTypeMap(arg0);
    }
	
	
	
	
	
	
	
	
	
}
