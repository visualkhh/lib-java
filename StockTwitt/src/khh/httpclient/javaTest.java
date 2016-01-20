package khh.httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class javaTest {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ArrayList <String> a= new ArrayList();
		a.add("a");
		a.add("b");
		
		Collections.reverse(a);;
		for (int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i));
		}
		
		
		Class.forName("com.mysql.jdbc.Driver");
	}
}
