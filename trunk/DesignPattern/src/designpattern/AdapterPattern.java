package designpattern;

//오리지널
interface Print_I {
	public void print(String msg);
}

class DefaultPrint implements Print_I {
	@Override
	public void print(String msg) {
		System.out.println("DefaultPrint_"+msg);
	}
}


// 전혀 새로운 인터페이스
interface QuickPrint_I {
	public void print(byte[] msg);
}
class QuickPrint implements QuickPrint_I {
	@Override
	public void print(byte[] msg) {
		System.out.println("QuickPrint_"+new String(msg));
	}
}


//어뎁터 처리
class AdapterPrint implements Print_I{
	QuickPrint qp =null;
	public AdapterPrint() {
		qp=new QuickPrint();
	}
	@Override
	public void print(String msg) {
		System.out.println("어뎁터처리 들어감");
		qp.print( msg.getBytes());
	}
}


public class AdapterPattern {
	public static void main(String[] args) {
		Print_I print = getPrintObj();
		print.print("안녕하세요");
	}
	
	public static Print_I getPrintObj(){
		return new AdapterPrint();
//		return new DefaultPrint();
	}
}
