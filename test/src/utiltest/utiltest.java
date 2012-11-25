package utiltest;

import java.util.ArrayList;
import java.util.HashMap;

import com.kdn.util.ValidationUtil;

public class utiltest
{
	public static void main(String[] args)
	{
		
	String inputStr ="b_ad!@#$%^&*(~')_+-=[`]{~}\\|'\";:/?.>,<";
	
	HashMap h = new HashMap();
	h.put("@","골뱅이");
	h.put("?","물음표");
	h.put("_","밑줄");
	h.put("'","`");
	h.put("~","물결");
	
	ArrayList g = new ArrayList();
	g.add("@");
	g.add("*");
	g.add("!");
	
	
	inputStr = ValidationUtil.replaceAll(inputStr,h);
	
	
	
	System.out.println(inputStr);
	String[] resultIndex = ValidationUtil.isMatches(inputStr,new String[]{"!","@","#","$","%","^","&","*","(",")","_","-","+","=","{","}","[","]","<",">",",",".","/","?","~",";",":","\"","'","|","\\"});
	System.out.println(resultIndex+"  "+resultIndex.length);
	for (int i = 0; i < resultIndex.length; i++)
	{
		System.out.print("\t"+resultIndex[i]);
	}
	
	
	

	
		
//		String str = "asdbbfggfk|\nbgm@#$t*65030jbmbmb";
//		String regex = ".*\\.*";
//		boolean a = ValidationUtil.isMatches(str,regex);
//		System.out.println(a);
		
		
//		String ff = "asdasd\r <asd";
//		ff = ff.replaceAll("\r","");
//		System.out.println(ff.matches(".*<.*"));
		
		
		
		
//		char a='\t';
//		String f   = String.format("%c    %x",a,(int)a);
//		System.out.println(f);
		
		
	}
}
