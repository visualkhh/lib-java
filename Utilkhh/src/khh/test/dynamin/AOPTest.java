package khh.test.dynamin;

public class AOPTest {
	public AOPTest() {
		System.out.println("AOPTEST Create");
	}
	
	public void before(){
		System.out.println("Before" );
	}
	public void after(){
		System.out.println("After");
	}
	public void before(Object ...objects){
		System.out.println("Before "+objects[0]);
	}
	public void after(SuperObject zz){
		System.out.println("After "+zz);
	}
}
