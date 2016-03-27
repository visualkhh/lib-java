package khh.reflection;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import khh.collection.StandardArrayList;
import khh.conversion.util.ConversionUtil;
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
	public static Field[] getFields(Object at){
		return getFields(at.getClass());
	}
	public static Field[] getFields(Class at){
		Field[] fields = at.getFields();
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
	
	
	public static Field[] getDeclaredFields(Object at){
		return getDeclaredFields(at.getClass());
	}
	public static Field[] getDeclaredFields(Class at){
		Field[] fields = at.getDeclaredFields();
		return fields;
	}
	public static Field[] getDeclaredAnnotationFields(Class classclass, Class annoClass){
		ArrayList<Field> fieldArray = new ArrayList<>();
		Field[] fields = getDeclaredFields(classclass);
		
		for (int i = 0; i < fields.length; i++) {
			Field elem = fields[i];
		  if (elem == null || !elem.isAnnotationPresent(annoClass)) {
			   continue;
		  }
		  fieldArray.add(elem);
		}
		
		return  fieldArray.toArray(new Field[fieldArray.size()]);
	}
	
	public static Field getDeclaredField(Class at,String fieldname){
		Field fields = null;
		try {
			fields = at.getDeclaredField(fieldname);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return fields;
	}
	public static Object getDeclaredField(Object object,String fieldname) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		return getDeclaredField(object,fieldname,Object.class);
	}
	public static <T> T getDeclaredField(Object object,String fieldname, Class<T> requiredType) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Object value=null;
		Class klass =object.getClass();
		Field field = getDeclaredField(klass,fieldname);
		value = field.get(object);
		return (T)value;
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
		return newClass(classpath,Object.class);
	}
	public static <T> T newClass(String classpath,Class<T> requiredType) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
		return newClass(getClass(classpath),requiredType);
	}
	public static Object newClass(Class classclass) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
		return newClass(classclass,Object.class);
	}
	public static <T> T newClass(Class classclass,Class<T> requiredType) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
		return newClass(classclass, new Object[]{},requiredType);
	}
	public static Object newClass(String classpath, ArrayList parameters) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		//Object[] paramObject = new Object[parameters.size()];
		return newClass(classpath,parameters,Object.class);
	}
	public static <T> T newClass(String classpath, ArrayList parameters,Class<T> requiredType) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		return newClass(getClass(classpath),null==parameters?new Object[]{}:parameters.toArray(),requiredType);
	}
	public static Object newClass(String classpath, Object[] parameters) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		return newClass(classpath, parameters, Object.class);
	}
	public static <T> T newClass(String classpath, Object[] parameters,Class<T> requiredType) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		return newClass(getClass(classpath),parameters,requiredType);
	}
	
	public static Object newClass(Class classclass, Object[] parameters) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		return newClass(classclass,parameters, Object.class);
    }
	public static <T> T newClass(Class classclass, Object[] parameters,Class<T> requiredType) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
        Class[] classs = new Class[null==parameters?0:parameters.length];
        for (int i = 0; i < classs.length; i++) {
            classs[i]  =   parameters[i].getClass();
        }
       return newClass(classclass, classs, parameters,requiredType);
	}
	
	public static <T> T  newClass(String classpath,Class[] constructorParamType ,Object[] constructorArgs,Class<T> requiredType) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
		return newClass(getClass(classpath), constructorParamType,constructorArgs, requiredType);
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
		return newClass(classclass, constructorParamType, constructorArgs, Object.class);
	}
	
	public static <T> T  newClass(Class classclass,Class[] constructorParamType ,Object[] constructorArgs, Class<T> requiredType) throws SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//		  Class[] paramTypes = {
		//        String.class, 
		//        String.class, 
		//        Integer.TYPE };
		
		//Object[] args = { 
		//        "Fred", 
		//        "Fintstone", 
		//        new Integer(90000) };
		Class klass =classclass;
		T theNewObject = null;
		//try {
		if(constructorParamType!=null && constructorParamType.length>0){
			 Constructor cons = null;
			 
			 try{
				 cons = klass.getConstructor(constructorParamType);
			 }catch(NoSuchMethodException e){
		        	//System.out.println("noSuchMethod");
				 Constructor[] constructors = klass.getConstructors();
		        	for (int i = 0; i < constructors.length; i++) {
		        		Constructor atConstructor = constructors[i];
		//        		Parameter[] atParameters = atConstructor.getParameters();
		        		Class[] atParameters = atConstructor.getParameterTypes();
		        		boolean isPass = true;
						if(atParameters.length==constructorArgs.length){//이름과 파라미터개수가 똑같아야된다.
							for (int pCnt = 0; pCnt < atParameters.length; pCnt++) {
								Class atParameter = atParameters[pCnt];
								if(atParameter.isInstance(constructorArgs[pCnt])){
									isPass = true;
								}else{
									isPass = false;
								}
								
							}
							if(isPass){
								cons = atConstructor;
							}else{
								throw new NoSuchMethodException();
							}
						}
					}
			 }
			 
			 theNewObject = (T) cons.newInstance(constructorArgs);
		}else{
			theNewObject = (T) klass.newInstance();
		}
		
		//} catch (Exception e) {
		//e.printStackTrace();
		//}
		return theNewObject;
	}
	
	public static Object executeMethod(Object object,String methodname) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return executeMethod(object,methodname,null,null);
	}
    public static Object executeMethod(Object object,String methodName, StandardArrayList<Class,Object>parameter) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        Class[] classs = new Class[parameter.size()];
        Object[] objects = new Object[parameter.size()];
        
        for (int i = 0; i < parameter.size(); i++) {
            Standard<Class, Object> standardAt = parameter.get(i);
            classs[i]  =   standardAt.getKey();
            objects[i] =   standardAt.getValue();
        }
       return executeMethod(object, methodName, classs, objects);
    }
    public static Object executeMethod(Object object, String methodName,  ArrayList parameters) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
    	return executeMethod(object,methodName,parameters.toArray());
    }
    public static Object executeMethod(Object object, String methodName, Object[] parameters) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
       return executeMethod(object, methodName, ConversionUtil.toClassArray(parameters), parameters);
    }
    public static Object executeMethod(Object object, String methodName, Class[] paramTypes, Object[] parameters) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
            Class klass =object.getClass();
            Method setSalaryMethod =  getMethod(klass,methodName,paramTypes,parameters);//null;//getMethod(object, methodName, paramTypes);
            //Method setSalaryMethod = klass.getMethod(methodName, paramTypes);
            Object returnObject = setSalaryMethod.invoke(object, parameters);
            return returnObject;
    }
    
    public static Object executeMethod(Object object, Method setSalaryMethod, Object[] parameters) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
    	Object returnObject = setSalaryMethod.invoke(object, parameters);
    	return returnObject;
    }
    
    public static Method getMethod(Class klass, String methodName, Object[] parameters) throws NoSuchMethodException {
    	return getMethod(klass,methodName, ConversionUtil.toClassArray(parameters),parameters);
    }
    public static Method getMethod(Class klass, String methodName, Class[] paramTypes, Object[] parameters) throws NoSuchMethodException {
    	Method setSalaryMethod = null;
    	try{
        	setSalaryMethod = klass.getMethod(methodName, paramTypes);
        }catch(NoSuchMethodException e){
        	Method[] methods = klass.getMethods();
        	for (int i = 0; i < methods.length; i++) {
        		Method atMethod = methods[i];
//        		Parameter[] atParameters = atMethod.getParameters();
        		Class[] atParameters = atMethod.getParameterTypes();
        		boolean isPass = true;
				if(methodName.equals(atMethod.getName()) && atParameters.length==parameters.length){//이름과 파라미터개수가 똑같아야된다.
					for (int pCnt = 0; pCnt < atParameters.length; pCnt++) {
						Class atParameter = atParameters[pCnt];  
//						if(paramTypes[pCnt].isInstance(parameters[pCnt])){
						if(atParameter.isInstance(parameters[pCnt])){
							isPass = true;
						}else{
							isPass = false;
						}
					}
					if(isPass){
						setSalaryMethod = atMethod;
					}else{
						throw new NoSuchMethodException();
					}
				}
			}
        	if(null==setSalaryMethod){
				throw new NoSuchMethodException();
			}
        }
		return setSalaryMethod;
	}


	public static void executeDeclaredMethod(Object object,String methodname) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        executeDeclaredMethod(object,methodname,null,null);
    }
    public static void executeDeclaredMethod(Object object, String methodName, Class[] paramTypes, Object[] parameters) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        Class klass =object.getClass();
        Method setSalaryMethod = null;
        try{
        	setSalaryMethod = klass.getDeclaredMethod(methodName, paramTypes);
        }catch(NoSuchMethodException e){
        	Method[] methods = klass.getMethods();
        	for (int i = 0; i < methods.length; i++) {
        		Method atMethod = methods[i];
//        		Parameter[] atParameters = atMethod.getParameters();
        		Class[] atParameters = atMethod.getParameterTypes();
        		boolean isPass = true;
				if(methodName.equals(atMethod.getName()) && atParameters.length==parameters.length){//이름과 파라미터개수가 똑같아야된다.
					for (int pCnt = 0; pCnt < atParameters.length; pCnt++) {
						Class atParameter = atParameters[pCnt];
						if(atParameter.isInstance(parameters[pCnt])){
							isPass = true;
						}else{
							isPass = false;
						}
						
					}
					if(isPass){
						setSalaryMethod = atMethod;
					}else{
						throw new NoSuchMethodException();
					}
				}
			}
        }
        setSalaryMethod.invoke(object, parameters);
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
		return getField(object, fieldname, Object.class);
	}
	public static <T> T getField(Object object, String fieldname, Class<T> requiredType) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		Object value=null;
			Class klass =object.getClass();
			Field field = klass.getField(fieldname);
			value = field.get(object);
		return (T)value;
	}
	
	public static void setField(Object object, String fieldname, Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		Field field = object.getClass().getField(fieldname);
		setField(object, field, value);
	}
	public static void setField(Object object,  Field field,Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
			Class klass =object.getClass();
			field.setAccessible(true);
			field.set( object, value);
	}
	
	public static void setDeclaredField(Object object,  String fieldname,Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		Field field = object.getClass().getDeclaredField(fieldname);
		setDeclaredField(object, field, value);
	}
	public static void setDeclaredField(Object object, Field field, Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
			Class klass =object.getClass();
			field.setAccessible(true);
			field.set( object, value);
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
