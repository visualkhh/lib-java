package khh.project.remote.server.worker;

import java.nio.channels.SelectionKey;

import khh.communication.tcp.nio.relay.server.client.ClientRelayWorker;
import khh.communication.tcp.nio.worker.msg.NioMsg;

public class RemoteWorker extends ClientRelayWorker{
	@Override
	public NioMsg onReceiveAction(NioMsg msg, SelectionKey selectionKey) throws Exception{
		return super.onReceiveAction(msg, selectionKey);
	}
	@Override
	public NioMsg onSendAction(NioMsg msg, SelectionKey selectionKey) throws Exception{
		return super.onSendAction(msg, selectionKey);
	}
}
