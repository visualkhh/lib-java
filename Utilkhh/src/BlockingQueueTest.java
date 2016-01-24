import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueTest {
	public static void main(String[] args) throws Exception {
		LinkedBlockingQueue<String>  q = new LinkedBlockingQueue();
		q.put("a");
		
		for (int i = 0; i < 5; i++) {
			System.out.println( q.poll(5000,TimeUnit.MILLISECONDS) );
		}
	}
}
