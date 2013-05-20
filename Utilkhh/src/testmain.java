import java.nio.ByteBuffer;


public class testmain {
    public static void main(String[] args) throws InterruptedException {
    	ByteBuffer b = ByteBuffer.allocate(4);
    	b.limit(4);
    	b.put((byte)1);
    	System.out.println(b);
    	b.position(4);
    	System.out.println(b);
    	
//    	for (int i = 0; i < b.limit(); i++) {
//			System.out.println(b.get(i));
//		}
//    	System.out.println(b);
//    	for (int i = 0; i < b.limit(); i++) {
//    		System.out.println(b.get(i));
//    	}
    	
    }
}
