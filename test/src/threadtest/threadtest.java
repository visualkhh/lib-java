package ThreadTest;


class TT extends Thread{
	int i=0;
	@Override
	public void run() {

		while (true) {
			try {
				System.out.println("while s");
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		i++;

		System.out.println("while end");
		if(i>5){
			break;
		}
		}
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	
}

public class ThreadTest {
	public static void main(String[] args) throws InterruptedException {
		TT t = new TT();
		t.run();
		Thread.sleep(500);
		System.out.println( t.getI());
	}

}
