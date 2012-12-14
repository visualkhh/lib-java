package dbterminal;

import java.sql.Connection;
import java.util.ArrayList;

import khh.db.connection.ConnectionCreator_I;
import khh.db.connection.pool.ConnectionMultiPool;
import khh.db.terminal.DBTerminal;
import khh.db.terminal.resultset.DBTResultRecord;
import khh.db.terminal.resultset.DBTResultSetContainer;
import khh.db.util.ConnectionUtil;
import khh.debug.LogK;
import khh.file.util.FileUtil;
import khh.std.adapter.AdapterMapBase;

public class DBTerminalTest {
    LogK log = LogK.getInstance();
    ConnectionCreator_I creator = new ConnectionCreator_I() {
        public Connection getMakeConnection() throws Exception {
            return ConnectionUtil.getConnection(ConnectionUtil.MYSQL, "localhost", ConnectionUtil.MYSQL_DAFAULT_PORT, "dbname_sid", "id", "pwd");
        }
    };
    DBTerminal dbterminal=null;
    public DBTerminalTest() throws Exception {
        setting();
        select();
        insert();
        update();
        delete();
    }
    public void setting() {
        dbterminal =new DBTerminal(creator);
        DBTerminal.addConfigfile("dbt/sqlmapa.xml");
    }
    public void select() throws Exception{
        AdapterMapBase<String, Object> param = new AdapterMapBase<String, Object>() {};
        param.add("hit",436);
        param.add("name","관리자");
        DBTResultSetContainer result = dbterminal.executeMapQuery("cool_select",param);
        for (int i = 0; i < result.size(); i++) {
            DBTResultRecord recode = result.get(i);  
            log.debug(recode.getString("contents"));
        } 
    }
    private void insert() throws Exception {
        AdapterMapBase<String, Object> param = new AdapterMapBase<String, Object>() {};
        param.add("seq","김2");
        int result = dbterminal.executeMapUpdate("cool_insert",param);
        log.debug("insert result : "+result);
    }
    private void update() throws Exception {
        AdapterMapBase<String, Object> param = new AdapterMapBase<String, Object>() {};
        param.add("seq","김빵");
        int result = dbterminal.executeMapUpdate("cool_update",param);
        log.debug("update result : "+result);
    }
    private void delete() throws Exception {
        AdapterMapBase<String, Object> param = new AdapterMapBase<String, Object>() {};
        param.add("seq","김");
        int result = dbterminal.executeMapUpdate("cool_delete",param);
        log.debug("update result : "+result);
    }
    public static void main(String[] args) throws Exception {
        new DBTerminalTest ();
    }
}
