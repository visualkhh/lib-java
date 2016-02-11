package khh.test.dynamin;

import java.util.ArrayList;

public class ExecuteObject  extends ArrayList<String>{
	public ExecuteObject() {
		System.out.println("ExecuteObject");
	}
	
	public boolean add(String s) {
		System.out.println(s);
		super.add(s);
		return true;
	}
	public boolean addObj(SuperObject e) throws java.lang.Exception {
		System.out.println("adddddddddddddddddd  --> "+e.getName());
		//return super.add(e.getName());
		return true;
	}
//		System.out.println("adddddddddddddddddd");
//		super.add(index, element);
	
}
