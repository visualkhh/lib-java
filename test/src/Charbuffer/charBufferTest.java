package Charbuffer;

import java.nio.CharBuffer;

public class charBufferTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String tt ="KDT|000044|Error     |1|13|000|BufferOverflow";
		
		CharBuffer t = CharBuffer.wrap(tt);
		
		System.out.println(t.toString());
		System.out.println(t.toString());

	}

}
