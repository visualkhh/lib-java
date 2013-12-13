package khh.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import khh.collection.StandardArrayList;
import khh.std.Standard;

public class ReflectionUtil
{
//////////
	public static Class getClass(String classpath){
		Class klass = null;
		try {
			klass = Class.forName(classpath);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return klass;
	}
	
	
	public static Method[] getMethods(Class at){
	      Method[] methods = at.getMethods();
//	      for(Method m : methods )
//	      {
//	         System.out.println( "Found a public method: " + m );
//	      }
	      return methods;
	}
	public static Method[] getDeclaredMethods(Class at){
	    Method[] methods = at.getDeclaredMethods();
//	      for(Method m : methods )
//	      {
//	         System.out.println( "Found a public method: " + m );
//	      }
	    return methods;
	}
	public static Field[] getFields(Class at){
		Field[] fields = at.getFields();
//		for(Field m : fields )
//		{
//			System.out.println( "Found a public Field: " + m );
//		}
		return fields;
	}
	
	public static Method getMethod(Class at,String methodName){
		return getMethod(at, methodName, null);
		}
	public static Method getMethod(Class at,String methodName,Class[] paramsTypes){
		Method setSalaryMethod = null;
		try {
				setSalaryMethod = at.getMethod(methodName, paramsTypes);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return setSalaryMethod;
	}
	public static Method getDeclaredMethod(Class at,String methodName){
	    return getDeclaredMethod(at, methodName, null);
	}
	public static Method getDeclaredMethod(Class at,String methodName,Class[] paramsTypes){
	    Method setSalaryMethod = null;
	    try {
	        setSalaryMethod = at.getDeclaredMethod(methodName, paramsTypes);
	    } catch (SecurityException e) {
	        e.printStackTrace();
	    } catch (NoSuchMethodException e) {
	        e.printStackTrace();
	    }
	    return setSalaryMethod;
	}

	public static Field getField(Class at,String fieldName){
		Field fields = null;
		try {
			fields = at.getField(fieldName);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return fields;
	}
	
	
	public static Object newClass(String classpath) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
		return newClass(getClass(classpath), null,null);
	}
	public static Object newClass(Class classclass) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
		return newClass(classclass, null,null);
	}
	public static Object newClass(String classpath,Class[] constructorParamType ,Object[] constructorArgs) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
		return newClass(getClass(classpath), constructorParamType,constructorArgs);
	}
	
	public static Object newClass(String classpath,StandardArrayList<Class,Object> parameter) throws SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
	    return newClass(getClass(classpath),parameter);
	}
	public static Object newClass(Class classclass,StandardArrayList<Class,Object> parameter) throws SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
	    
	    Class[] classs = new Class[parameter.size()];
	    Object[] objects = new Object[parameter.size()];
	    
	    for (int i = 0; i < parameter.size(); i++) {
	        Standard<Class, Object> standardAt = parameter.get(i);
	        classs[i]  =   standardAt.getKey();
	        objects[i] =   standardAt.getValue();
        }
	    
	    return newClass(classclass,classs,objects);
	}
	public static Object newClass(Class classclass,Class[] constructorParamType ,Object[] constructorArgs) throws SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
//		  Class[] paramTypes = {
//		            String.class, 
//		            String.class, 
//		            Integer.TYPE };
		
//		 Object[] args = { 
//		            "Fred", 
//		            "Fintstone", 
//		            new Integer(90000) };
		Class klass =classclass;
		Object theNewObject = null;
//		try {
			if(constructorParamType!=null && constructorParamType.length>0){
				 Constructor cons = klass.getConstructor(constructorParamType);
				 theNewObject = cons.newInstance(constructorArgs);
			}else{
				theNewObject = klass.newInstance();
			}
			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	      return theNewObject;
	}
	
	
    public static Object executeMathod(Object object,String mathodname) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        return executeMathod(object,mathodname,null,null);
    }
    
    public static Object executeMathod(Object object,String methodName, StandardArrayList<Class,Object>parameter) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        Class[] classs = new Class[parameter.size()];
        Object[] objects = new Object[parameter.size()];
        
        for (int i = 0; i < parameter.size(); i++) {
            Standard<Class, Object> standardAt = parameter.get(i);
            classs[i]  =   standardAt.getKey();
            objects[i] =   standardAt.getValue();
        }
       return executeMathod(object, methodName, classs, objects);
    }
    public static Object executeMathod(Object object, String methodName, Class[] paramTypes, Object[] parameters) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
//      try{
//              Object object = newClass(classclass);
                Class klass =object.getClass();
                Method setSalaryMethod =  klass.getMethod(methodName, paramTypes);
              return  setSalaryMethod.invoke(object, parameters);
//      }catch(Exception e){
//          e.printStackTrace();
//      }
    }
    
    public static void executeDeclaredMethod(Object object,String mathodname) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        executeDeclaredMethod(object,mathodname,null,null);
    }
    public static void executeDeclaredMethod(Object object, String methodName, Class[] paramTypes, Object[] parameters) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
//      try{
//              Object object = newClass(classclass);
                Class klass =object.getClass();
                Method setSalaryMethod =  klass.getDeclaredMethod(methodName, paramTypes);
                setSalaryMethod.invoke(object, parameters);
//      }catch(Exception e){
//          e.printStackTrace();
//      }
    }
	
	public static Object getField(Object object, Field field) throws IllegalArgumentException, IllegalAccessException{
		Object value=null;
//		try{
//			Object object = newClass(classfullpath,constructorParamType,constructorArgs);
			Class klass =object.getClass();
//			Field field =klass.getDeclaredField(fieldname);
//			field.setAccessible(true);
			value = field.get(object);
//			field.set( object, value);
//			setSalaryMethod.invoke(object, parameters);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		return value;
	}
	public static Object getField(Object object, String fieldname) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		Object value=null;
//		try{
//			Object object = newClass(classfullpath,constructorParamType,constructorArgs);
			Class klass =object.getClass();
//			Field field =klass.getDeclaredField(fieldname);
//			field.setAccessible(true);
			Field field = klass.getField(fieldname);
			value = field.get(object);
//			field.set( object, value);
//			setSalaryMethod.invoke(object, parameters);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		return value;
	}
	
	public static void setField(Object object,  String fieldname,Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
//		try{
//			Object object = newClass(classfullpath,constructorParamType,constructorArgs);
			Class klass =object.getClass();
			Field field = klass.getField(fieldname);
			 Object oldValue = field.get(object);
			 field.set( object, value);
//			setSalaryMethod.invoke(object, parameters);
//		}catch(Exception e){
//			
//		}
	}
	
//	public static Object getValue(Object clazz, String lookingForValue) throws Exception {
//			    Field field = clazz.getClass().getField(lookingForValue);
//			    Class clazzType = field.getType();
//			    if (clazzType.toString().equals("double"))
//			      return field.getDouble(clazz);
//			    else if (clazzType.toString().equals("int"))
//			      return field.getInt(clazz);
//			    // else other type ...
//			    // and finally
//			    return field.get(clazz);
//	}
}
