package khh.test.dynamin;

public class SuperObject {
	
	public SuperObject(String name){
		System.out.println("SuperObject Create");
		this.name = name;
	}
	
	public SuperObject() {
		System.out.println("super");
	}

	String name = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("setName" + name);
		this.name = name;
	}

	@Override
	public String toString() {
		return "SuperObject [name=" + name + "]";
	}

}
