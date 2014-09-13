package designpattern;
//전력적패턴
interface Connection{
	void say();
}

class ByteConnection implements Connection{
	@Override
	public void say() {
		System.out.println("ByteSay");
	}
}

class HtmlConnection implements Connection{
	@Override
	public void say() {
		System.out.println("HtmlSay");
	}
}





public class StrategyPattern {
	public static void main(String[] args) throws InterruptedException {
		Connection c = null;
		c = getConnection();
		c.say();
		Thread.sleep(1);
		c = getConnection();
		c.say();
		Thread.sleep(1);
		c = getConnection();
		c.say();
		Thread.sleep(1);
		c = getConnection();
		c.say();
	}
	
	
	public static Connection getConnection(){ //팩토리클래스를 따로두어 처리하면 팩토리 패턴이 먹힌다.
		long s = System.currentTimeMillis();
		if(s%2==0){
			return new ByteConnection();
		}else{
			return new HtmlConnection();
		}
	}
}
