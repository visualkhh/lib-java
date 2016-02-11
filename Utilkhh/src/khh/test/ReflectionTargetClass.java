package khh.test;

public class ReflectionTargetClass {
	public ReflectionTargetClass() {
		System.out.println("start");
	}
	
	
	public void print(String a, String b){
		System.out.println("atP "+a+"   "+b);
		
	}
	
	public void printlong(String t,String ... a){
		System.out.println("p-->"+t);
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}
	public void print(String ... a){
		System.out.println("p");
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}
	public void print2(String[] a){
		System.out.println("p2");
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}
}
