package khh.schedule;

import java.util.Timer;

import khh.std.adapter.AdapterMap;



public class Scheduler
{
	AdapterMap<String, ScheduleTask>	taskContainer	= null;
	Timer timer = null;
	public Scheduler()
	{
		taskContainer = new AdapterMap<String, ScheduleTask>();
	}
	
//delay는 말그대로 딜레이 delay있다가 수행   주기적인  period는 ms 콜
	public void schedule(String name,Runnable run,long delay,long period) throws Exception{
		ScheduleTask task= new ScheduleTask(name);
		task.setRunnable(run);
		taskContainer.add(name, task);
		getTimer().schedule(task, delay,period);
	}
	public void schedule(String name,ScheduleTask task,long delay,long period) throws Exception{
		taskContainer.add(name, task);
		getTimer().schedule(task, delay,period);
	}
	
    public void schedule(final String name, final Runnable run, long delay, long period,final long count) throws Exception {
        Runnable acceptrun = new Runnable() {
            int startcnt=0;
            public void run() {
                startcnt++;
                //System.out.println(startcnt+"     "+count+"     "+name);
                if(startcnt<=count){
                    run.run();
                }else{
                    try {
                        removeScheduleTask(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        schedule(name,acceptrun,delay,period);
        
    }
    public void schedule(final String name, final ScheduleTask task, long delay, long period,final long count) throws Exception {
        final Runnable oldrunnable = task.getRunnable();;
        Runnable acceptrun = new Runnable() {
            int startcnt=0;
            public void run() {
                startcnt++;
                if(startcnt<=count){
                    oldrunnable.run();
                }else{
                    try {
                        removeScheduleTask(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        task.setRunnable(acceptrun);
        schedule(name,task,delay,period);
    }
    
    
    
	
	public void removeScheduleTask(String name) throws Exception {
		ScheduleTask task = taskContainer.get(name);
		task.cancel();
		taskContainer.remove(name);
		if(taskContainer.size()<=0){
		    cancel();
		}
		
	}

    public void setScheduleTaskPause(String name,boolean wantPause) throws Exception {
		ScheduleTask task = taskContainer.get(name);
		task.setPause(wantPause);
	}
	
	
	public void cancel(){
		taskContainer.clear();
		if(timer!=null)
		timer.cancel();
		timer=null;
	}

	public void pause(boolean wantPause){
		for(int i = 0 ; i< taskContainer.size();i++){
			try{
			taskContainer.get(i).setPause(wantPause);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	@Override
	protected void finalize() throws Throwable
	{
	    //System.out.println("fianlly");
		if(timer!=null)
		timer.cancel();
		super.finalize();
	}
	
	
   public ScheduleTask getScheduleTask(String name) throws Exception{
        return taskContainer.get(name);
    }
	public   AdapterMap<String, ScheduleTask> getScheduleContainer(){
	    return taskContainer;
	}
	public int getTaskSize(){
		return taskContainer.size();
	}
	
	
	public Timer getTimer(){
	    if(timer==null){
	        timer = new Timer();
	    }
		return timer;
	}
}
