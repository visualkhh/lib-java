package threadtest;

import java.lang.Thread.State;

import javax.swing.plaf.SliderUI;

import com.kdt.util.schedule.Scheduler;

public class threadtest {
    tt t=null;
    public void start() throws Exception{
        System.out.println("a");
        t = new tt();
        t.start();
        System.out.println("b");

        if(t.getState()==Thread.State.NEW){
            System.out.println("new");
        }else if(t.getState()==Thread.State.BLOCKED){
            System.out.println("BLOCKED");
        }else if(t.getState()==Thread.State.RUNNABLE){
            System.out.println("RUNNABLE");
        }else if(t.getState()==Thread.State.TERMINATED){
            System.out.println("TERMINATED");
        }else if(t.getState()==Thread.State.TIMED_WAITING){
            System.out.println("TIMED_WAITING");
        }else if(t.getState()==Thread.State.WAITING){
            System.out.println("WAITING");
        }
        Thread.sleep(2000);
        t.interrupt();
            if(t.getState()==Thread.State.NEW){
                System.out.println("new");
            }else if(t.getState()==Thread.State.BLOCKED){
                System.out.println("BLOCKED");
            }else if(t.getState()==Thread.State.RUNNABLE){
                System.out.println("RUNNABLE");
            }else if(t.getState()==Thread.State.TERMINATED){
                System.out.println("TERMINATED");
            }else if(t.getState()==Thread.State.TIMED_WAITING){
                System.out.println("TIMED_WAITING");
            }else if(t.getState()==Thread.State.WAITING){
                System.out.println("WAITING");
            }
        
//        Scheduler s = new Scheduler();
//        s.schedule("a", r, 0, 100);
    }
    
    public static void main(String[] args) throws Exception {
        threadtest a = new threadtest();
        a.start();
    }
    
    
    Runnable r = new Runnable() {
        public void run() {
            System.out.println(t.getState());
            if(t.getState()==State.TERMINATED){
                t.start();
            }
        }
    };
}
