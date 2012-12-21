package khh.project.remote.client.admin.communication;

import java.nio.channels.SelectionKey;

import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;

public class AdminClientActionWorker extends NioActionWorker{
	public AdminClientActionWorker(){
		setFirestMode(MODE_FIREST_W);
	}
	@Override
	public NioActionMsg onReceiveAction(NioActionMsg msg, SelectionKey selectionKey)
			throws Exception{
		return msg;
	}

	@Override
	public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey) throws Exception{
		
		
		return msg;
	}

}
