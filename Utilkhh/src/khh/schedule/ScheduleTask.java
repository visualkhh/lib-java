package khh.schedule;

import java.util.TimerTask;

public class ScheduleTask extends TimerTask
{
	private Runnable	run;
	private String	name;
	boolean isPause=false;
	public ScheduleTask(String name){
		setName(name);
	}
	
	@Override
	public void run() {
		if(isPause==false)
		getRunnable().run();
	}
	
	public void setRunnable(Runnable run){
		this.run = run;
	}
	public Runnable getRunnable(){
		return this.run;
	}
	private void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}

	public boolean isPause()
	{
		return isPause;
	}

	public void setPause(boolean isPause)
	{
		this.isPause = isPause;
	}
	
	
}
