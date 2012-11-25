import java.util.Timer;
import java.util.TimerTask;

import com.kdt.util.Utilities;
import com.kdt.util.schedule.Scheduler;

import finalize.main;




public class SchedulTest
{
	
	
	public static void main(String[] args) throws Exception
	{
		
		Scheduler sdr = new Scheduler();
		
		sdr.schedule("1", new Runnable() {
			@Override
			public void run()
			{
			System.out.println(1);
				
			}
		}, 0, 1000);
		
		
		
		
		
		sdr.schedule("2", new Runnable() {
			@Override
			public void run()
			{
			System.out.println(2);
				
			}
		}, 0, 1000);
		
		
		long m=System.currentTimeMillis();
		boolean cancel=true;
		boolean add=true;
		while(true){
			if(Utilities.isTimeOver(m, 5000) && cancel ){
				sdr.cancel();
				cancel=false;
			}
			if(Utilities.isTimeOver(m, 10000) && add ){
				sdr.schedule("3", new Runnable() {
					@Override
					public void run()
					{
					System.out.println(2);
						
					}
				}, 0, 1000);
			}
				System.out.println("size "+sdr.getTaskSize() );
			Thread.sleep(1000);
		}
		
	}
}
