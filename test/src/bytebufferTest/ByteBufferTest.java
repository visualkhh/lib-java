package bytebufferTest;

import java.nio.ByteBuffer;

public class ByteBufferTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ByteBuffer buf =ByteBuffer.allocate(10);
        buf.position(5);
        System.out.println(buf);
        ByteBuffer byf =buf.slice();
        System.out.println(byf);


    }

}
