package designpattern;


abstract class Man{
	public static Man getMan(int age){		//추상클래스에서 팩토리를 정의한다.
		if(age>50){
			return new OldMan();
		}else{
			return new YongMan();
		}
	}
	
	abstract public void play();
}

class YongMan extends Man{
	@Override
	public void play() {
		System.out.println("YongMan Play");
	}
}

class OldMan extends Man{
	@Override
	public void play() {
		System.out.println("Old Play");
	}
}



public class AbstractFactory {
	public static void main(String[] args) {
		Man man = Man.getMan(40);
		man.play();
		man = Man.getMan(100);
		man.play();
	}
}
