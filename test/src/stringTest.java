
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import javax.rmi.CORBA.Util;

import com.kdt.util.Utilities;

public class stringTest
{
	public static void main(String[] args)
	{
	
	ByteBuffer b =ByteBuffer.allocate(100);
	b.order(ByteOrder.LITTLE_ENDIAN);
	b.putInt(55454);
	b.flip();
	
	Utilities.trace(b,"bb");
	int a = b.getInt();
	System.out.println(a+"   "+Integer.toHexString(a));
	
	ByteBuffer bb =ByteBuffer.allocate(100);
	bb.putInt(a);
	bb.flip();
	Utilities.trace(bb,"bb");
	
		
//		ByteBuffer b  = buffer.duplicate();
		
//		for (int i = buffer.position(); i < buffer.limit(); i++)
//		{
//			System.out.format("%02X \t ",buffer.get());
//			
//		}
//		System.out.println();
//		for (int i = b.position(); i < b.limit(); i++)
//		{
//			System.out.format("%02X \t ",b.get(i));
//			
//		}
		
		
//		System.out.println();
	
//	{
//		System.out.format("%02X \t ",a[i]);
//	}
	
	
	
//		
//		int tt = 200;
//		buffer.rewind();
//		
//		for (int i = buffer.position(); i <buffer.limit(); i++)
//		{
//			System.out.format("%02X  ",buffer.get());
//		}
//		System.out.println();
		
//
//		byte[] b = a.getBytes();
//		
//		for (int i = 0; i < b.length; i++)
//		{
//			System.out.println(b[i]);
//		}
//		
//		System.out.println();
//		ByteBuffer bf = ByteBuffer.wrap(b);
//		for (int i = bf.position(); i < bf.limit(); i++)
//		{
//			System.out.println(bf.get());
//		}
//		
//		
//	Utilities.trace(bf.array().length, bf.array(),"aaaaaaaaaaaaa");
		

	}
}