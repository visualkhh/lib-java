package khh.test.logk;

import khh.debug.LogK;

public class LogKTest {
	LogK log = LogK.getInstance();
	public LogKTest() {
	}

	public void start() throws InterruptedException{
		int i=0;
		while(true){
			
			log.debug("------"+(i++));
			
			Thread.sleep(1000);
		}
	}
	public static void main(String[] args) throws InterruptedException {
		LogKTest l = new LogKTest();
		l.start();
	}
}
