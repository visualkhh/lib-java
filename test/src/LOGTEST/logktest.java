package LOGTEST;

import com.kdn.util.debug.LogK;

public class logktest {
    public  void test(){
        test2();
    }
    
    public void test2(){
        LogK logk = LogK.getInstance();
        
        while (true) {
            System.out.println("start"+System.currentTimeMillis());
            logk.fatal("aa");
            System.out.println("end"+System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        
    }
    
    
    
    public static void main(String[] args) throws InterruptedException {
        new logktest().test();
    }
}
