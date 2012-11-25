package schedulertest;

import com.kdt.util.schedule.ScheduleTask;
import com.kdt.util.schedule.Scheduler;


public class schedulertest {
    
    public void start() throws Exception{
        Scheduler sc = new Scheduler();
        sc.cancel();
        sc.schedule("a", new Runnable() {
            @Override
            public void run() {
                System.out.println("a");
            }
        }, 1, 1000,3);
    }
    public void start2() throws Exception{
        Scheduler sc = new Scheduler();
        ScheduleTask task= new ScheduleTask("aaaaaaaa");
        task.setRunnable(new Runnable() {
            @Override
            public void run() {
                System.out.println("a");
            }
        });
        sc.schedule("a",task, 1, 1000,3);
    }
    public static void main(String[] args) throws Exception {
        new schedulertest().start();
    }
}
