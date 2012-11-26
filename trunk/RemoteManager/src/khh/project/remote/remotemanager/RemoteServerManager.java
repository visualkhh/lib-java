package khh.project.remote.remotemanager;

import java.nio.ByteBuffer;

import khh.conversion.util.ConversionUtil;
import khh.debug.LogK;
import khh.project.remote.remotemanager.admin.AdminServer;
import khh.project.remote.remotemanager.client.ClientServer;
import khh.util.ByteUtil;




public class RemoteServerManager {
    private LogK log = LogK.getInstance();
	public void start() throws Exception {
		ClientServer clientS = new ClientServer();
		AdminServer adminS = new AdminServer();
		clientS.start();
		adminS.start();
	}
	
	
	
	public static void main(String[] args) throws Exception  {
//		System.out.format("%02X ",(byte)'[');
//		System.out.format("%02X ",(byte)']');
//		System.out.format("%02X ",(byte)'B');
//		ByteBuffer b = ByteBuffer.allocate(5);
//		b.put((byte)1);
//		b.put((byte)2);
//		b.put((byte)3);
//		b.put((byte)4);
//		b.put((byte)5);
//		b.clear();
//		b.position(3);
//		ByteBuffer a = ByteUtil.copyByteBuffer(b);
//		a.position(1);
//		System.out.println(b);
//		byte[] a =  b.array();
//		System.out.println(b);
		RemoteServerManager f = new RemoteServerManager();
		f.start();
		
		
		
	}
}
