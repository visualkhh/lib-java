package khh.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import khh.reflection.ReflectionUtil;

public class ReflectionTest {
	public static void main(String[] args) throws Exception, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		String[] sa = new String[]{"a","b"};
		ReflectionTargetClass r = (ReflectionTargetClass) ReflectionUtil.newClass(ReflectionTargetClass.class);
		
		ArrayList param  = new ArrayList();
		param.add(sa);
		
//		Object[] ppp = new Object[]{"a","b"};
		Object[] ppp = new Object[]{"title",sa};
		ReflectionUtil.executeMethod(r, "printlong",ppp);
		System.out.println("=----");
		ReflectionUtil.executeMethod(r, "print",sa);
		
//		r.print("a","b","c");
	}
}
