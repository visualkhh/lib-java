package khh.test.quartz;

import java.util.Calendar;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
/*
참고로 아래의 표는 CronTrigger 에서 작업주기를 설정하는 식의 예와 설명이다.
순서
    1. Seconds (0-59) - * /
    2. Minutes (0-59)  - * /
    3. Hours (0-23)  - * /
    4. Day-of-month (1-31) - * ? / L W C
    5. Month (1-12 or JAN-DEC) - * /
    6. Day-of-week (1-7 or SUN-SAT) - * ? / L C #
    7. Year (optional, empty, 1970-2099) - * /
식
의미
"0 0 12 * * ?"
매일 12시(정오)에실행
"0 15 10 ? * *"
매일 오전 10시15분에 실행
"0 15 10 * * ?"
매일 오전 10시15분에 실행
"0 15 10 * * ? *"
매일 오전 10시15분에 실행
"0 15 10 * * ? 2005"
2005년의 매일 오전 10시15분에 실행
"0 * 14 * * ?"
매일 오후 2시부터 오후 2시59분까지 매분마다 실행
"0 0/5 14 * * ?"
매일 오후 2시부터 오후 2시55분까지 매5분마다 실행
"0 0/5 14,18 * * ?"
매일 오후 2시부터 오후 2시55분까지 매5분마다 실행 그리고
매일 오후 6시부터 오후 6시55분까지 매5분마다 실행
"0 0-5 14 * * ?"
매일 오후 2시부터 오후 2시05분까지 매분마다 실행
"0 10,44 14 ? 3 WED"
3월의 매주 수요일 오후 2시10분과 오후 2시44분에 실행
"0 15 10 ? * MON-FRI"
매주 월, 화, 수, 목, 금요일 오전 10시15분에 실행
"0 15 10 15 * ?"
매월 15일 오전 10시15분에 실행
"0 15 10 L * ?"
매월 마지막날 오전 10시15분에 실행
"0 15 10 ? * 6L"
매월 마지막 금요일 오전 10시15분에 실행
"0 15 10 ? * 6L 2002-2005"
2002년부터 2005년까지의 매월 마지막 금요일 오전 10시15분에 실행
"0 15 10 ? * 6#3"
매월 세번째 금요일 오전 10시15분에 실행
[출처] [Java] Quartz (쿼츠)를 사용하여 자바 스케줄링(scheduling) 하기|작성자 마루아라

 */
/*
* : 모든 값을 의미.
? : 특정 값을 정하지 않음.
- : 범위를 의미. 예) 0-10이면 0부터 10까지.
, : 값을 추가. 0-10,20-30은 0부터 10까지, 그리고 20부터 30까지.
/ : 증분을 의미. 예) 초에 0/15를 사용하면 15초마다(0, 15, 30, 45) .
L : 마지막을 의미. 날짜에 사용하면 월의 마지막 날을 의미.  
    31, 30 또는 28(윤달에는 29).
W : 주중(weekday)를 의미. 날짜와 같이 쓰면 그 날짜가 주중인 날을 의미.  
 */

class QJobListener implements JobListener{
	@Override
	public String getName() {
		return "LLL"; //must return a name;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		System.out.println("jobToBeExecuted>>"+context.getResult());
		
		String jobName = context.getJobDetail().getKey().toString();
		System.out.println("jobToBeExecuted");
		System.out.println("Job : " + jobName + " is going to start..."+context.getResult());
		
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		// TODO Auto-generated method stub
		System.out.println("jobExecutionVetoed>>"+context.getResult());
		
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		System.out.println("jobWasExecuted>>"+context.getResult()+"  -->"+jobException.getMessage());
		// TODO Auto-generated method stub
		
	}
	
}

public class QuartzTest implements Job {
	int i =0 ;
//	public QuartzTest(String a ) {
//		System.out.println("create QuaT");
//	}
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Calendar date = Calendar.getInstance();
		String stamp = date.get(Calendar.HOUR_OF_DAY)+":"
	            +date.get(Calendar.MINUTE)+":"
	            +date.get(Calendar.SECOND)+":"
	            +date.get(Calendar.MILLISECOND);
	    System.out.println(stamp +" " + "Generating report >> "+i);
	    i++;
	    arg0.setResult(i);
//	    try{
	    int z = 1/i;
//	    }catch(Exception e){
//	    	e.printStackTrace();
//	    }
	    
	    
	}
	
	
	public static void main(String[] args) throws Exception {
		// Grab the Scheduler instance from the Factory
	      Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
	      // and start it off
	      scheduler.start();

	      // define the job and tie it to our HelloJob class
	      JobKey jkey = new JobKey("job1", "group1");
	      JobDetail job = newJob(QuartzTest.class)
          .withIdentity(jkey) 
          .build();
	    		  //("QuartzJob",Scheduler.DEFAULT_GROUP,QuartzTest.class);
	      // Trigger the job to run now, ss mm hh dd MM Week
	      TriggerKey tkey = new TriggerKey("trigger1", "group1");
	      Trigger trigger = newTrigger()
	              .withIdentity(tkey)
	              .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
	              .build();
	      // Tell quartz to schedule the job using out trigger
	      scheduler.scheduleJob(job, trigger);
	      
	      
	      scheduler.getListenerManager().addJobListener(new QJobListener(),KeyMatcher.keyEquals(jkey));
	      
	      while(true){
//	    	  scheduler.getJobGroupNames().stream().forEach(et->{
//	    		  System.out.println("getJobGroupNames>>"+et);
//	    	  });
//	    	  scheduler.getTriggerGroupNames().stream().forEach(et->{
//	    		  System.out.println("getTriggerGroupNames>>"+et);
//	    	  });
	    	  
//	    	  scheduler.getJobKeys(GroupMatcher.jobGroupEquals("group1")).stream().forEach(et->{
//	    		  try {
//					System.out.println("getJobKeys >>"+et+"    "+scheduler.getJobDetail(et).getJobClass().getName());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//	    		  
//	    	  });;
	    	  
	    	  
//		      scheduler.getCurrentlyExecutingJobs().stream().forEach(et->{
////		    	  System.out.println("getJobKeys >>"+et+"    "+scheduler.getJobDetail(et).getJobClass().getName());
//		    	  System.out.println(">>>>>>>>>>>"+et.getResult());
////		    	  et.getTrigger().getKey()
//		      });
	    	  
	    	  
	    	  
	    	  
	    	  System.out.println(scheduler.getSchedulerName());
	    	  
	    	  Thread.sleep(1000);
	      }

	      
	      
	      
	      //scheduler.shutdown();
		
	}
}
