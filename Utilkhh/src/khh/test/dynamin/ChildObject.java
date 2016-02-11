package khh.test.dynamin;

public class ChildObject  extends SuperObject{
public ChildObject() {
	System.out.println("child");
}
	public String getName() {
		return name+" child~~~";
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "ChildObject [name=" + name + "]";
	}
	
}
