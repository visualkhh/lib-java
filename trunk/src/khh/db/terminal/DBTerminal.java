package khh.db.terminal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import khh.db.connection.ConnectionCreator_I;
import khh.db.resultset.ResultSetContainer;
import khh.db.statement.LogPreparedStatement;
import khh.db.util.DBUtil;
import khh.debug.LogK;

public class DBTerminal {
    
    ConnectionCreator_I connectioncreator=null;
    Connection connection=null;
    private LogK logk  = LogK.getInstance();
    Boolean autoCommit=null;
    
    public DBTerminal(ConnectionCreator_I connectioncreator) {
        this.connectioncreator=connectioncreator;
    }
    

    
    public void setAutoCommit(Boolean watnAutoCommit) {
        autoCommit=watnAutoCommit;
    }
    
    public ResultSetContainer executeQuery(String sql) throws Exception{
        Connection connection =null;
        Statement stmt=null;
        ResultSet rset=null;
        ResultSetContainer rsc=null;
        try {
            connection = getConnection();
            
            stmt = connection.createStatement();
            logk.debug(sql);
            rset =  stmt.executeQuery(sql);
            rsc = DBUtil.makeResultSetContainer(rset);
            //commit();
        } catch (Exception e) {
            logk.error("error  ",e);
            if(connection!=null){
                rollback();
            }
            throw e;
        }finally{
            if(rset!=null){
                rset.close();
            }
            if(stmt!=null){
                stmt.close();
            }
            if(connection!=null){
                if(connection.getAutoCommit()==true){
                    connection.close();
                    closeConnection();
                }
            }
        }
        return rsc;
    }
    private void closeConnection() throws SQLException, Exception {
       getConnection().close();
       logk.debug("CloseConnection");
        
    }



    public int executeUpdate(String sql) throws Exception{
        Connection connection =null;
        Statement stmt=null;
        int updatecnt=0;
        try {
            connection = getConnection();
            stmt = connection.createStatement();
            logk.debug(sql);
            updatecnt =  stmt.executeUpdate(sql);
            //commit();
        } catch (Exception e) {
            logk.error("error  ",e);
            if(connection!=null){
                rollback();
            }
            throw e;
        }finally{
            if(stmt!=null){
                stmt.close();
            }
            if(connection!=null){
                if(connection.getAutoCommit()==true){
                    connection.close();
                    closeConnection();
                }
            }
        }
        return updatecnt;
    }
    
    
    
    
    public ResultSetContainer executeQuery(String sql,ArrayList param) throws Exception{
        Connection connection =null;
        PreparedStatement pstmt=null;
        ResultSet rset=null;
        ResultSetContainer rsc=null;
        try {
            connection = getConnection();
            pstmt = new LogPreparedStatement(connection,sql);
            pstmt = DBUtil.setPreparedStatementValue(pstmt,param);
            logk.debug( ((LogPreparedStatement)pstmt).getQueryString());
            rset =  pstmt.executeQuery();
            rsc = DBUtil.makeResultSetContainer(rset);
            //commit();
        } catch (Exception e) {
            logk.error("error  ",e);
            if(connection!=null){
                rollback();
            }
            throw e;
        }finally{
            if(rset!=null){
                rset.close();
            }
            if(pstmt!=null){
                pstmt.close();
            }
            if(connection!=null){
                if(connection.getAutoCommit()==true){
                    connection.close();
                    closeConnection();
                }
            }
        }
        return rsc;
    }
    public int executeUpdate(String sql,ArrayList param) throws Exception{
        Connection connection =null;
        PreparedStatement pstmt=null;
        int updatecnt=0;
        try {
            connection = getConnection();
           // System.out.println("getConnection2   "+connection.getAutoCommit());
            pstmt = new LogPreparedStatement(connection,sql);
            pstmt = DBUtil.setPreparedStatementValue(pstmt,param);
            logk.debug( ((LogPreparedStatement)pstmt).getQueryString());
            updatecnt =  pstmt.executeUpdate();
            //commit();
        } catch (Exception e) {
            logk.error("error  ",e);
            if(connection!=null){
                rollback();
            }
            throw e;
        }finally{
            if(pstmt!=null){
                pstmt.close();
            }
            if(connection!=null){
                if(connection.getAutoCommit()==true){
                    connection.close();
                    closeConnection();
                }
            }
        }
        return updatecnt;
    }
    
    
    public void commit() throws Exception{
        
        if(connection!=null && connection.isClosed()==false){
            connection.commit();
            connection.close();
            closeConnection();
        }
    }
    
    public void rollback() throws Exception{
        if(connection!=null && connection.isClosed()==false){
            connection.rollback();
            connection.close();
            closeConnection();
        }
    }
    
    
    

    
    
    

    public Connection getConnection() throws Exception{
        
        //오토커밋시 다른 커넥션으로 사용하면안되니깐.. 열려있고 오토커밋이 펄스인놈
        if(connection!=null && connection.isClosed()==false && connection.getAutoCommit()==false){
            return connection;
        }
        
        //없어지기전에 새로만들기전 무조건 옛날건 클로우즈
        if(connection!=null && connection.isClosed()==false){
            connection.close();
            connection=null;
        }
        
        //그외는 뭐든지 새롭게 커넥션
        connection = connectioncreator.getMakeConnection();
        logk.debug("GetConnection ConnectionCreator.getMakeConnection      Call  makeConnection AutoCommit("+connection.getAutoCommit()+")   settingAutoCommit("+autoCommit+")");
        
        //사용자가 셋팅한게있음 그걸로 셋팅
        if(autoCommit!=null){
            connection.setAutoCommit(autoCommit);
        }
        
        return  connection;
    }
    
    
    @Override
    protected void finalize() throws Throwable {
        //없어지기전에 무조건 클로우즈
        if(connection!=null){
            try{
            connection.close();
            }catch (Exception e) {
            }
        }
        
        super.finalize();
    }
}
