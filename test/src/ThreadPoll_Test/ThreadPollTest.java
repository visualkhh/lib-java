package ThreadPoll_Test;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
Executor 인터페이스:
제공된 작업(Runnable 구현체)을 실행하는 객체가 구현해야 할 인터페이스. 이 인터페이스는 작업을 제공하는 코드와 작업을 실행하는 메커니즘의 사이의 커플링을 제거해준다.
ExecutorService 인터페이스:
Executor의 라이프사이클을 관리할 수 있는 기능을 정의하고 있다. Runnable 뿐만 아니라 Callable을 작업으로 사용할 수 있는 메소드가 추가로 제공된다.
ScheduledExecutorService:
지정한 스케쥴에 따라 작업을 수행할 수 있는 기능이 추가되었다.
 */




public class ThreadPollTest {
    
    Executor executor = new Executor(){
        @Override
        public void execute(Runnable command) {
            
        }
    };
    
    
    Runnable run = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("aaaaaa");
        }
    };
    
    ExecutorService executor_s = Executors.newSingleThreadExecutor();
    public void singleStart(){
        System.out.println("isShutdown : "+executor_s.isShutdown()+"     isTerm : "+executor_s.isTerminated());
        executor_s.execute(run);
    }
    public void singleEnd(){
        executor_s.shutdown();
        executor_s.shutdownNow();
    }
    
    
    ExecutorService executor_pool = Executors.newFixedThreadPool(10);
    public void poolStart(){
        executor_pool.execute(run);
    }
    
    ScheduledExecutorService executor_schedul = Executors.newScheduledThreadPool(10);
    public void schedulStart(){
//        executor.scheduleAtFixedRate(instance, 시작시간, 반복시간, 시간단위);
  //      ( instance 는 Runnable 을 구현한 객체이며 시간 단위는 TimeUnit Enum 에 선언되어있다. 요걸 가져다 쓰도록 한다. )
        executor_schedul.scheduleAtFixedRate(run, 0, 100, TimeUnit.MILLISECONDS);
    }
    
    public static void main(String[] args) {
        ThreadPollTest t= new ThreadPollTest();   
//        t.singleStart();   
//        t.singleEnd();
        
        
        
//        t.poolStart();   
//        t.poolStart();   
        t.schedulStart();
//        System.out.println("a");
    }
}
