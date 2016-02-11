package logerss.server;

import java.nio.channels.SelectionKey;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.db.connection.ConnectionCreator_I;
import khh.db.terminal.DBTerminal;
import khh.db.util.ConnectionUtil;

public class LogerssWorker extends NioActionWorker {
	public LogerssWorker() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public NioActionMsg onReceiveAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception {
		
		if(selectionKey.isReadable()){
			String user_seq = msg.getParamString("user_seq");
			String log_seq = msg.getParamString("log_seq");
			String log_id = msg.getParamString("log_id");
			String log_date = msg.getParamString("log_date");
			String log_data = msg.getParamString("log_data");
			
			DBTerminal db = new DBTerminal(new ConnectionCreator_I() {
				@Override
				public Connection getMakeConnection() throws Exception {
					String ip ="visualkhh.c4qmnc7lnzqd.ap-northeast-2.rds.amazonaws.com";
					String sid ="logersstest";
					return ConnectionUtil.getConnection(ConnectionUtil.MYSQL,ip,ConnectionUtil.MYSQL_DAFAULT_PORT,sid,"root","Rnadmfdnlgoa01");
				}
			});
			String sql ="";
			sql+= "		INSERT INTO lo_log_data (USER_SEQ, LOG_SEQ, LOG_ID, LOG_DATE, LOG_DATA)";
			sql+= "		VALUES ('"+user_seq+"', '"+log_seq+"', '"+log_id+"', '"+log_date+"', '"+log_data+"')";
			db.executeUpdate(sql);
		}
		return null;
	}

	@Override
	public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void receiveTelegram(HashMap<String, Object> telegram, SelectionKey selectionKey) throws Exception {
		// TODO Auto-generated method stub

	}
}
