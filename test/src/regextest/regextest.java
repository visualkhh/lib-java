package regextest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kdn.util.UtilitiyKDN;
import com.kdn.util.ValidationUtil;

public class regextest
{
	public static void main(String[] args)
	{

//		String rex="^one cat.*.yard";
//        Pattern p = Pattern.compile(rex);
//        Matcher m = p.matcher("one cat," +" two cats in the yard");
//
//        
//        boolean b = m.find();
//        System.out.println(b);
//        if (b) {
//            System.out.println("match");
//        } else {
//            System.out.println("not match");
//        }

		//^com\\.[A-Za-z0-9\\.\\@_\\-~#]+\\.hhk
		
//        boolean a = UtilitiyKDN.isFind("logk.*.\\.xml", "logk_hhk.xml");
//        System.out.println(a);
        String w="LOGTEST.logktest";
       // w = h_rex(w);
        String rex2="^LOGTEST\\,";
//        rex2 = rex2.replaceAll("\\.", "\\\\\\.");
        
        System.out.println(w+"    "+rex2);
        
        boolean a = UtilitiyKDN.isFind(rex2,w);
        System.out.println(a);
		
	}
	
	   public  static String h_rex(String h){
	        String in = (String)h;
	        in = in.replaceAll("\\\\", "\\\\\\\\");
	        in = in.replaceAll("\\.", "\\\\\\.");
	        in = in.replaceAll("\\?", "\\\\\\?");
	        in = in.replaceAll("\\(", "\\\\\\(");
	        in = in.replaceAll("\\)", "\\\\\\)");
	        in = in.replaceAll("\\{", "\\\\\\{");
	        in = in.replaceAll("\\[", "\\\\\\[");
	        in = in.replaceAll("\\$", "\\\\\\$");
	        in = in.replaceAll("\\^", "\\\\\\^");
	        in = in.replaceAll("\\*", "\\\\\\*");
	        in = in.replaceAll("\\+", "\\\\\\+");
	        in = in.replaceAll("\\|", "\\\\\\|");
	        return in;
	    }
}
