package khh.test.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTest {
	public static void main(String[] args) {
		
		String a ="5_259c60be-36c6-49be-ae6b-7c92cfa5a1c7,5_e58d9c01-7a83-4cf7-9edc-aae53eba2281";
		
		System.out.println(a.split(",")[0]);
		System.out.println(a.split(",")[1]);
		
	}
}
