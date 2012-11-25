package khh.db.connection;

import java.sql.Connection;

public interface ConnectionCreator_I {
//    public ConnectionPool_Connection getMakeConnection() throws Exception;
    public Connection getMakeConnection() throws Exception;
}
