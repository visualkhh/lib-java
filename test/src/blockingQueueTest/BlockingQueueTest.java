package blockingQueueTest;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class AA{
	String a ;
	String b;
}
public class BlockingQueueTest {
    private final BlockingQueue<String> messages = new LinkedBlockingQueue<String>();
    public BlockingQueueTest() throws InterruptedException {
        ggg();
    }

    public void ggg() throws InterruptedException {
        messages.put("a");
        messages.put("b");
        messages.put("c");
        // System.out.println( messages.poll() );
        // System.out.println( messages.poll() );
        // System.out.println( messages.poll() );
        // System.out.println( messages.poll() );
        System.out.println(messages.take());
        System.out.println(messages.take());
        System.out.println(messages.take());
        System.out.println(messages.take());
    }

    public static void main(String[] args) throws InterruptedException {
        new BlockingQueueTest();
    }
}
