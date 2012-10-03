package khh.callstack.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import khh.reflection.ReflectionUtil;


public class StackTraceUtil
{

	
	public static StackTraceElement[] getStrackTraceElements(){
		StackTraceElement[] elements = null;
		try {throw new Exception("getStrackTraceElements");}
		catch (Exception e){elements = e.getStackTrace();}
		
		
		StackTraceElement[] elements_r = new StackTraceElement[elements.length-1];
		
		for (int i = 0; i < elements_r.length; i++)
		{
			elements_r[i] = elements[i+1];
		}
		
		return elements_r;
	}
	
	
    public static StackTraceElement getBeforeStackTraceElement(Class atclass) {
        StackTraceElement[] elements = null;
        try {
            throw new Exception("getClassName");
        } catch (Exception e) {
            elements = e.getStackTrace();
        }
        boolean sw = false;
        for (int i = 0; i < elements.length; i++) {
            StackTraceElement stackTraceElement = elements[i];
            if ( atclass.getName().equals(stackTraceElement.getClassName()) ) {
                sw = true;
                continue;
            } else {
                if ( sw ) {
                    return stackTraceElement;
                } else {
                    continue;
                }
            }
        }
        return null;
    }
    
    
    public static String getBeforeFileName(Class atclass) {
        StackTraceElement stack = getBeforeStackTraceElement(atclass);
        return stack.getFileName();
    }

    public static String getBeforeMethodName(Class atclass) {
        StackTraceElement stack = getBeforeStackTraceElement(atclass);
        return stack.getMethodName();
    }

    public static String getBeforeClassFullName(Class atclass) {
        StackTraceElement stack = getBeforeStackTraceElement(atclass);
        return stack.getClassName();
    }

    public static int getBeforeClassLineNumber(Class atclass) {
        StackTraceElement stack = getBeforeStackTraceElement(atclass);
        return stack.getLineNumber();
    }
    
    
    
    
    
    
    
    
    //this
  //메서드명 뽑아오기
    public static String getMethodName()
    {
        StackTraceElement[] elements = null;
        
        try {throw new Exception("getMethodName");}
        catch (Exception e){elements = e.getStackTrace();}
        
        String methodName = ((StackTraceElement)elements[1]).getMethodName();
        
        return methodName;
    }
    public static String getClassFullName()
    {
        StackTraceElement[] elements = null;
        
        try {throw new Exception("getClassName");}
        catch (Exception e){elements = e.getStackTrace();}
        
        String methodName = ((StackTraceElement)elements[1]).getClassName();
        
        return methodName;
    }
    public static String getClassName()
    {
        StackTraceElement[] elements = null;
        
        try {throw new Exception("getClassName");}
        catch (Exception e){elements = e.getStackTrace();}
        
        String methodName = ((StackTraceElement)elements[1]).getClassName();
        String [] returnvalue = methodName.split("\\.");
        return returnvalue[returnvalue.length-1];
    }
    
    public static String getClassName(Class at){
        return at.getName();
    }
    public static String getClassInfo(String at){
        return getClassInfo(ReflectionUtil.getClass(at));
    }
    public static String getClassInfo(Class at){
        String a="";
         Class klass =at;
         a+=( "Class name:" + klass.getName());
         a+=",\n";
         a+=("Class super class:" + klass.getSuperclass());
          
          int mods = klass.getModifiers();
          a+=",\n";
          a+=("Class is public:" + Modifier.isPublic(mods));
          a+=",\n";
          a+=("Class is final:" +  Modifier.isFinal(mods));
          a+=",\n";
          a+=("Class is abstract:" + Modifier.isAbstract(mods)); 
          
           Field[] fields=ReflectionUtil.getFields(at);
           Method[] methods= ReflectionUtil.getMethods(at);
           for(Field m : fields )
            {
               a+=",\n";
               a+=("Found a public Field:" + m );
            }
           for(Method m : methods )
          {
               a+=",\n";
               a+=("Found a public method:" + m );
          }
           return a;
    }
    
    
    
    
    public static String getFileName()
    {
        StackTraceElement[] elements = null;
        
        try {throw new Exception("getClassName");}
        catch (Exception e){elements = e.getStackTrace();}
        
        String fileName = ((StackTraceElement)elements[1]).getFileName();
        
        return fileName;
    }
    public static int getLineNumber()
    {
        StackTraceElement[] elements = null;
        
        try {throw new Exception("getNumberLine");}
        catch (Exception e){elements = e.getStackTrace();}
        
        int methodName = ((StackTraceElement)elements[1]).getLineNumber();
        
        return methodName;
    }
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
