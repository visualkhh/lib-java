import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import com.kdt.util.Utilities;

;
public class bytebufferConversionstring
{
	public static void main(String[] args)
	{
		// KHH: 스트링 -> 바이트배열로 -2010. 2. 3.오전 10:51:09 VISUALKHH
		String temp = "ABCDEFG";
		byte[] btemp = temp.getBytes();

		for (int i = 0; i < btemp.length; i++)
		{
			Utilities.trace("%02X\t%02X\t%02X\t%02X\t%02X\n", btemp[i], btemp[i], btemp[i], btemp[i], btemp[i], btemp[i]);
		}

		// KHH: 바이트배열 ->스트링 -2010. 2. 3.오전 10:51:29 VISUALKHH
		String ttt = new String(btemp);
		Utilities.trace(ttt);

		System.out.println("");

		// KHH: 바이트배열 ->바이트버퍼 -2010. 2. 3.오전 10:51:44 VISUALKHH
		ByteBuffer bt = ByteBuffer.allocateDirect(100);
		bt.put(btemp);
		for (int i = 0; i < btemp.length; i++)
		{
			byte x = (byte) i;
			Utilities.trace("%02X\t%02X\t%02X\t%02X\t%02X\n", bt.get(x), bt.get(x), bt.get(x), bt.get(x), bt.get(x));
		}

		System.out.println("");
		// KHH: 바이트버퍼 ->바이트배열 -2010. 2. 3.오전 11:13:28 VISUALKHH
		bt.flip();
		byte[] by = new byte[bt.remaining()];
		bt.get(by);

		for (int i = 0; i < by.length; i++)
		{
			byte x = (byte) i;
			Utilities.trace("%02X\t%02X\t%02X\t%02X\t%02X\n", by[i], by[i], btemp[i], by[i], by[i], by[i]);
		}

		try
		{
			ByteBuffer bv = ByteBuffer.allocateDirect(8);
			bv.put("aaaaaa".getBytes());
			bv.flip();
			CharBuffer cv = bv.asCharBuffer();
			System.out.println(cv.toString());
		}
		catch (java.nio.BufferOverflowException e)
		{
			System.out.println("오류");
		}
		catch (Exception e)
		{
			System.out.println("오류2");
			// TODO: handle exception
		}
		


	}
}
