import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import khh.annotation.Convert;
import khh.annotation.Convert;
import khh.annotation.Convertor;
import khh.annotation.ConvertorBeforeImp;

//class Join extends LinkedHashMap<String, String>{
class Join extends LinkedHashMap<String, String> {
	String name;

	public Join() {
	}

	public String getName() {
		return name;
	}

	@Convert(returns=ConvertorBeforeImp.class)
	public String setName(@Convert(returns=ConvertorBeforeImp.class) String name) {
		this.name = name;
		return name;
	}

}

public class ColTest {
	public static void main(String[] args) {
		ArrayList<Join> g = new ArrayList();
		Join j = new Join();
		j.name = "v";
		System.out.println(g.add(j));

		Join b = new Join();
		b.name = "b";
		System.out.println(g.add(b));
		System.out.println(g.size());
	}
}
