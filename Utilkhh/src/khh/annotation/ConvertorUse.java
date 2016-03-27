package khh.annotation;

import java.lang.reflect.Field;

import khh.reflection.ReflectionUtil;

public class ConvertorUse {
	@Convert(before=ConvertorBeforeImp.class,after=ConvertorAfterImp.class)
	public String good;
	
	@Convert(before=ConvertorBeforeImp.class,after=ConvertorAfterImp.class)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	@Override
	public String toString() {
		return "ConvertorUse [good=" + good + ", name=" + name + "]";
	}

	//////////////////////////
	public static void main(String[] args)  {
		ConvertorUse c = new ConvertorUse();
//		c.setName("visualkhh");
//		System.out.println(c);
		
//		Field[] fields = ReflectionUtil.getDeclaredFields(c);
//		for (int i = 0; i < fields.length; i++) {
//			System.out.println(fields[i]);
//		}
		
		before(c);
		System.out.println(c);
		
		after(c);
		System.out.println(c);
		
	}

	private static void before(ConvertorUse c)  {
		try{
			Field[] f = ReflectionUtil.getDeclaredAnnotationFields(c.getClass(),Convert.class);
			for(Field elem : f) {
				Convert convert = elem.getAnnotation(Convert.class);
				Convertor convertor = ReflectionUtil.newClass(convert.before(),Convertor.class);
	 
				Object o = convertor.converting(elem.get(c));
	 
				ReflectionUtil.setDeclaredField(c, elem, o);
			}
		}catch(Exception e){
		}
		
	}
	
	private static void after(ConvertorUse c) {
		try{
			Field[] f = ReflectionUtil.getDeclaredAnnotationFields(c.getClass(),Convert.class);
			for(Field elem : f) {
				Convert convert = elem.getAnnotation(Convert.class);
				Convertor convertor = ReflectionUtil.newClass(convert.after(),Convertor.class);
	 
				Object o = convertor.converting(elem.get(c));
	 
				ReflectionUtil.setDeclaredField(c, elem, o);
			}
		}catch(Exception e){
		}
	}
}
