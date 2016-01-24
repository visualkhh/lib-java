import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexGroupTest {

	public static void main(String args[]) {

		// String to be scanned to find the pattern.
		String line = "This order was placed for QT3000! OK?";
		String pattern = "(.*)(\\d+)(.*)";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(line);
		if (m.find()) {
			System.out.println("Found value: " + m.group(0));
			System.out.println("Found value: " + m.group(1));
			System.out.println("Found value: " + m.group(2));
		} else {
			System.out.println("NO MATCH");
		}
		
		
		
		
		System.out.println("========================");
		
		
		String REGEX = "\\bcat\\b";
		String INPUT ="cat cat cat cattie cat";
		
		
		Pattern p = Pattern.compile(REGEX);
	       m = p.matcher(INPUT); // get a matcher object
	       int count = 0;
	       while(m.find()) {
	         count++;
	         System.out.println("Match number "+count);
	         System.out.println("start(): "+m.start());
	         System.out.println("end(): "+m.end());
	      }
	       
	       
	       
	       System.out.println("========================");
	      REGEX = "foo";
	      INPUT = "fooooooooooooooooo";
	       
	      p = Pattern.compile(REGEX);
	       m = p.matcher(INPUT);

	       System.out.println("Current REGEX is: "+REGEX);
	       System.out.println("Current INPUT is: "+INPUT);

	       System.out.println("lookingAt(): "+m.lookingAt());
	       System.out.println("matches(): "+m.matches());
	       
	       
	       System.out.println("========================");
	       REGEX = "dog";
	       INPUT = "The dog says meow. " +
	                                       "All dogs say meow.";
	       String REPLACE = "cat";
	       
	       p = Pattern.compile(REGEX);
	       // get a matcher object
	       m = p.matcher(INPUT); 
	       INPUT = m.replaceAll(REPLACE);
	       System.out.println(INPUT);
	       
	       
	       
	       
	       
	       System.out.println("========================");
	       REGEX = "a*b";
	       INPUT = "aabfooaabfooabfoob";
	       REPLACE = "-";
	          p = Pattern.compile(REGEX);
	          // get a matcher object
	          m = p.matcher(INPUT);
	          StringBuffer sb = new StringBuffer();
	          while(m.find()){
	             m.appendReplacement(sb,REPLACE);
	          }
	          m.appendTail(sb);
	          System.out.println(sb.toString());
	       
	}
	
	
}
