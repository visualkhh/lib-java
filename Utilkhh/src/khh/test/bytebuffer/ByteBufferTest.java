package khh.test.bytebuffer;

import java.nio.ByteBuffer;

import khh.debug.LogK;
public class ByteBufferTest {
	public static LogK log 							= LogK.getInstance();
	public static void main(String[] args) {
		ByteBuffer sr = ByteBuffer.allocate(100);
		sr.put("visualkhh".getBytes());
		sr.flip();
		log.debug("-->"+sr.remaining(),sr);
		
		ByteBuffer newB = ByteBuffer.allocate(100);
		log.debug("-->",newB);
		newB.put(sr);
		log.debug("-->",newB);
		
	}
}
