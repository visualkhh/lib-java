import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class REGEXTEST {
	public static void main(String[] args) {
		Pattern p = Pattern.compile("\\bthis\\b");
		Matcher m = p.matcher("Print this");
		m.find();
		System.out.println(m.group());
		
		
		
		String statement = "Hello, my beautiful/vi/?asdg=world";
//		Pattern pattern = Pattern.compile("Hello, my (\\w+).*");
		Pattern pattern = Pattern.compile("Hello, my (.+).*");
		Matcher mm = pattern.matcher(statement);
		mm.find();
		System.out.println(mm.group(1));
		
		System.out.println("==============");
		statement = "He${123123}llo,${srfhgvsd asd} my${g.dfv1234.23445gg} beautiful/${aaa}vi/?asdg=world";
//		pattern = Pattern.compile("\\$\\{[\\w\\.\\#\\$\\%}]+\\}");
		pattern = Pattern.compile("\\$\\{[\\w\\.\\s]+\\}");
		mm = pattern.matcher(statement);
       while(mm.find()) {
    	   System.out.println("group(): "+mm.group());
	         System.out.println("start(): "+mm.start());
	         System.out.println("end(): "+mm.end());
      }
		
		
		/*
		Explain:
		
		(?i) - ignorecase
		.*? - allow (optinally) any characters before
		\b - word boundary
		%s - variable to be changed by String.format (quoted to avoid regex errors)
		\b - word boundary
		.*? - allow (optinally) any characters after
		 */
	}
}
