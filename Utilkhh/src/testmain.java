import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import khh.callstack.util.StackTraceUtil;

class tt {
    public void gogo() throws InterruptedException {

//        StackTraceElement[] s = StackTraceUtil.getStrackTraceElements();
//        for (int i = 0; i < s.length; i++) {ggg
//            StackTraceElement stackTraceElement = s[i];
//            System.out.println(stackTraceElement.getClassName());
//        }
        ggg();
    }
    private final BlockingQueue<String> messages = new LinkedBlockingQueue<String>();
  
    public void ggg() throws InterruptedException {
        messages.put("a");
        messages.put("b");
        messages.put("c");
//        System.out.println( messages.poll() );
//        System.out.println( messages.poll() );
//        System.out.println( messages.poll() );
//        System.out.println( messages.poll() );
        System.out.println( messages.take() );
        System.out.println( messages.take() );
        System.out.println( messages.take() );
        System.out.println( messages.take() );
    }
}

public class testmain {
    public static void main(String[] args) throws InterruptedException {
    	
    	String menu_cd = "3101 ";
    	if(menu_cd!=null && menu_cd.trim().length()>0 && menu_cd.trim().equals("3101") ){ 
    		System.out.println(1);
    	}
    	
    	
//        new tt().gogo();
//�׽�Ʈ�Դϴ�.
    }
}
