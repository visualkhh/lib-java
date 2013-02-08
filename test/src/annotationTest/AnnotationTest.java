package annotationTest;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationTest{
	   @AnnotationClass(type = "field type")
	    public String field1 = "";
	 
	    @AnnotationClass(type = "method type")
	    public String method1(String x) {
	        return "";
	    }
	 
	    public static void main(String[] args) throws SecurityException,
	            NoSuchMethodException, IllegalArgumentException,
	            IllegalAccessException, InvocationTargetException,
	            NoSuchFieldException {
	        AnnotationTest test = new AnnotationTest();
	        AnnotationClass a;
	 
	        Class<AnnotationClass> c = (Class<AnnotationClass>) test.getClass();
	        //Class<!--?--> c = (Class<!--?-->) test.getClass();
	        a = (AnnotationClass)c.getAnnotation(AnnotationClass.class);
	        System.out.println(String.format("%s's type is '%s'",
	                c.getSimpleName(), a.type()));
	 
	        Field f = c.getField("field1");
	        a = f.getAnnotation(AnnotationClass.class);
	        System.out.println(String.format("%s's type is '%s'", f.getName(),
	                a.type()));
	 
	        Method m = c.getMethod("method1", new Class[] { String.class });
	        a = m.getAnnotation(AnnotationClass.class);
	        System.out.println(String.format("%s's type is '%s'.", m.getName(),
	                a.type()));
	    }
}
