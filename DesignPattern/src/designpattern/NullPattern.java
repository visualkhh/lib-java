package designpattern;

abstract class Printer{
	abstract public void print(String msg);
}

class InkPrinter extends Printer{
	@Override
	public void print(String msg) {
		System.out.println("Ink PRINT : "+msg);
	}
}

class DotPrinter extends Printer{
	@Override
	public void print(String msg) {
		System.out.println("Dot PRINT : "+msg);
		
	}
}

class NullPrint extends Printer{
	@Override
	public void print(String msg) {
		System.out.println("Null PRINT : "+msg);
	}
	
}

public class NullPattern {
	public static void main(String[] args) {
		getPrint(100).print("100");
		getPrint(200).print("200");
		getPrint(500).print("500");
	}

	static public Printer getPrint(int money) {
		if (money == 100) {
			return new DotPrinter();
		} else if (money == 200) {
			return new InkPrinter();
		}
		return new NullPrint(); // 아무것도 걸리지 않으면 널 인객체를 반환한다 이렇게하여 널포인트를 막는다.
	}
}
