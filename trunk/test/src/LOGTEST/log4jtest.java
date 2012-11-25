package LOGTEST;

import org.apache.log4j.Logger;

import com.khh.util.debug.DebugUtil;


public class log4jtest  {
    static Logger logger = Logger.getLogger(log4jtest.class);
    private void start() {
        System.out.println("a");
        System.out.println(System.getProperty("java.class.path"));
        byte[] ba = {1,2,3,4,5,6,7,8,9,4,4,5,5,5,56,6,6,6,6,6,5};
        logger.info("a");
    }

    
    public static void main(String[] args) {
        
        log4jtest l = new log4jtest();
        l.start();
        DebugUtil.trace("aa");
       // String a = DebugUtil.trace("aaaaaaaaaaaa%d",5);
    //    System.out.print(a);
//        byte[] ba = {1,2,3,4,5,6,7,8,9,4,4,5,5,5,56,6,6,6,6,6,5};
//        DebugUtil.trace(ba.length,ba,"aaa");
//        //logtest log = new logtest();
//        //log.start();
//        String unix = System.getProperty("java.class.path").replace(";", ":");
//        System.out.println(unix);
//        String [] classpaths = unix.split(":");
//        
//        for (int i = 0; i < classpaths.length; i++) {
//            System.out.println(classpaths[i]);
//            
//        }
        
    }
}
