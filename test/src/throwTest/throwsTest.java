package throwTest;

import java.nio.ByteBuffer;

class test
{
	public test()
	{
		// TODO Auto-generated constructor stub
	}

	void a()
	{
		try{
			System.out.println("start");
		throw new Exception("avava");
//		System.out.println("ED");
		}catch (java.nio.BufferOverflowException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
		    System.out.println(e.getMessage());
		}finally{
		    System.out.println("finally");
		}
	}

	void b() throws java.nio.BufferOverflowException ,Exception
	{
		ByteBuffer bv = ByteBuffer.allocateDirect(10);
		bv.put("rlraarl".getBytes());
		bv.limit(11);
	}
}

public class throwsTest
{
	public static void main(String[] args)
	{

		test t = new test();
		t.a();
	}
}
