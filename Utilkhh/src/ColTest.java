import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

//class Join extends LinkedHashMap<String, String>{
class Join extends LinkedHashMap<String, String>{
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
public class ColTest {
	public static void main(String[] args) {
		ArrayList<Join> g = new ArrayList();
		Join j =new Join();
		j.name="v";
		System.out.println( g.add(j) );
		
		Join b =new Join();
		b.name="b";		
		System.out.println( g.add(b) );
		System.out.println(g.size());
	}
}
