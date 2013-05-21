package khh.property.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import khh.callstack.util.StackTraceUtil;
import khh.reflection.ReflectionUtil;

public class PropertiesUtil extends Properties
{
	InputStream is=null;
//	Properties props =null;
	public PropertiesUtil()
	{
	}
	public PropertiesUtil(String filepath) throws IOException
	{
		
		StackTraceElement[] elements =StackTraceUtil.getStrackTraceElements();
		Class c =ReflectionUtil.getClass(((StackTraceElement)elements[1]).getClassName());
		this.is  = c.getResourceAsStream(filepath);
//		props = new Properties();
		load(this.is);
		
		
	}
	

	
	
}
