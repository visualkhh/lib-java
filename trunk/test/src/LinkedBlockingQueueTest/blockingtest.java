package LinkedBlockingQueueTest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class blockingtest {
    public static void main(String[] args) {
        BlockingQueue<String> messages = new LinkedBlockingQueue<String>();
        
        messages.add("a");
        messages.add("a");
        messages.add("a");
        messages.add("a");
        messages.add("a");
        messages.add("a");
        System.out.println(messages.size());
    }
}
