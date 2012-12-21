package argsTest;

import khh.date.util.DateUtil;

public class ArgsTest{
	public void start(){
		trace("d");
	}
	public static void trace(String format, Object... args)
	{
		System.out.format(DateUtil.getDate("yyyy-MM-dd HH:mm:ss,SSS") + " : " + format + "\n", args);
	}
	
public static void main(String[] args){
new ArgsTest().start();	
}
}
