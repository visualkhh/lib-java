package khh.finger.remote.finmanager;

import java.nio.ByteBuffer;

import khh.conversion.util.ConversionUtil;
import khh.debug.LogK;
import khh.finger.remote.finmanager.admin.AdminServer;
import khh.finger.remote.finmanager.client.ClientServer;
import khh.util.ByteUtil;




public class FingServerManager {
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
		FingServerManager f = new FingServerManager();
		f.start();
		
		
		
	}
}
