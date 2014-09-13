package designpattern;
//템플릿메서드 패턴
abstract class Logic{
	void login(String id, String pwd) throws Exception{
		if("VISUAL".equals(id) && "VISUAL".equals(pwd)){
			start(id);
		}else{
			throw new Exception("로그인실패");
		}
	}
	abstract void start(String id);//훅메서드
}

class ByteLogic extends Logic{
	@Override
	void start(String id) {
		System.out.println("ByteLogic Login Welcom "+id);
	}
}

class HtmlLogic extends Logic{
	@Override
	void start(String id) {
		System.out.println("HtmlLogic Login Welcom "+id);
	}
}


public class TemplateMethodPattern {
	public static void main(String[] args) throws Exception {
		new ByteLogic().login("VISUAL", "VISUAL");
		new HtmlLogic().login("VISUAL", "VISUAL");
		new HtmlLogic().login("VISUAL", "VISUALGSADES");
	}
	
	
}
