package khh.communication.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import khh.communication.Communication_Interface;
import khh.debug.util.DebugUtil;

public class ClientProcess extends Thread
{
	private EventQueue		queue			= null;
//	private ByteBuffer		buffer			= null;
//	private TcpServerCommunication		comunication=null;
	private Communication_Interface		comunication=null;
	public ClientProcess(EventQueue queue,Communication_Interface business)
	{
		
		this.queue = queue;
		this.comunication=business;
//		buffer = ByteBuffer.allocateDirect(1000000);
		DebugUtil.trace("ClientProcess-%d Create...(%s)", getId(),business.getClass().getName());
	}

	public void run()
	{
		DebugUtil.trace("ClientProcess-%d Running...", getId());

		SelectionKey key = null;
		SocketChannel channel = null;
		while (true)
		{
			try
			{
				sleep(1);
				try{
					key = queue.pop();
				}catch (Exception e) {
//					System.out.println(e.getMessage()+"  queuqe Exceptuon pop "+getId());
					continue;
				}
				if ( key == null )
					continue;

				channel = (SocketChannel) key.channel();
				
				if ( channel == null )
				{
					finish(key);
					continue;
				}

				DebugUtil.trace("Process!!!!  execute[%03d]", getId());
				comunication.getConnection().setSocket(channel);
				comunication.execute();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				DebugUtil.trace("Process!!!!  [%03d] Exception : %s", getId(), e.getMessage());
				close(channel);
			}
			finally
			{
				if(key != null)
					finish(key);
			}
		}
	}


	
	
	private void recvMessage(SocketChannel channel) throws java.nio.BufferOverflowException ,java.lang.IllegalArgumentException,Exception
	{
//		int len = 0;
//		int headLeng = 0;
//		String tmp="";
//
//		buffer.clear(); // 버퍼 초기화
//
//		// 헤더 읽기
//		len = readBlock(channel, buffer, KMessage.HEADSIZE, 1000);
//		if ( len == 0 )
//			return;
//		buffer.flip();
//
//		// 헤더 분석
//		tmp = charset.decode( buffer ).toString();
//		String header[] = tmp.split("\\|");
//		headLeng = Integer.parseInt(header[1]);
//		Utilities.trace("Receive MsgHead Success   MsgKind(%s) size(%d) wantGetLength(%d)",header[0],len,headLeng);
//
//		// 데이터 읽기
//		len = readBlock(channel, buffer, headLeng, 10000);
//		Utilities.trace("Receive MsgBody Success  size(%d)",len);
//		if ( len == 0 )
//			return;
//		buffer.flip();
//
//		// Command 실행
//		KCommand command = commandManager.getCommand(header[2].trim());
//		command.execute(channel, buffer);
	}
	
//	private int readBlock(SocketChannel channel, ByteBuffer buffer, int size, int timeout) throws java.nio.BufferOverflowException,java.lang.IllegalArgumentException,Exception
//	{
//		int len = 0;
//		long t = System.currentTimeMillis();
//
//		buffer.limit(size);
//
//		while (true)
//		{
//			len += channel.read(buffer);
//			
////			KDebug.trace("Len(%d)  Position(%d)  limit(%d)   deleytime(%d) ",len, buffer.position() , buffer.limit(),System.currentTimeMillis() - t);
//			if ( len == -1 )
//				throw new Exception("Socket Closed(read, -1)");
//
//			if ( buffer.position() == buffer.limit() || buffer.position() >= buffer.limit() )
//				return len;
//
//			if ( (System.currentTimeMillis() - t) > timeout ){
//				Utilities.trace("Receive TimeOut");
//				return len;
//			}
//
//			sleep(1);
//		}
//	}

	private void finish(SelectionKey key)
	{
		if ( key.isValid() )
			key.interestOps( SelectionKey.OP_READ);
//			key.interestOps(key.interestOps() | SelectionKey.OP_READ);
	}


	private void close(SocketChannel socket)
	{
		try
		{
			socket.close();
		}
		catch (IOException e1)
		{
		}
	}

}
