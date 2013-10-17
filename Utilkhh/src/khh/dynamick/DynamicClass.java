package khh.dynamick;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import khh.collection.StandardArrayList;
import khh.reflection.ReflectionUtil;
import khh.std.adapter.AdapterMap;

public class DynamicClass {
    private String id          = null;
    private String classPath   = null;
    private String ref         = null;
    private Object classObject = null;

    private ArrayList<DynamicClass> constructorParameter        = null;
    private AdapterMap<String,ArrayList<DynamicClass>> methoadParameter    = null;
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public Object getClassObject() {
        return this.classObject;
    }

    private void setClassObject(Object classObject) {
        this.classObject = classObject;
    }
    

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    
    public ArrayList<DynamicClass> getConstructorParameter() {
        return constructorParameter;
    }

    public void setConstructorParameter(ArrayList<DynamicClass> constructorParameter) {
        this.constructorParameter = constructorParameter;
    }


    public AdapterMap<String, ArrayList<DynamicClass>> getMethoadParameter() {
        return methoadParameter;
    }

    public void setMethoadParameter(AdapterMap<String, ArrayList<DynamicClass>> methoadParameter) {
        this.methoadParameter = methoadParameter;
    }

    public void newClass() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
        this.classObject =  ReflectionUtil.newClass(this.classPath);
    }
    public void newClass(Class[] constructorParamType ,Object[] constructorArgs) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
        this.classObject = ReflectionUtil.newClass(this.classPath,constructorParamType,constructorArgs);
    }
    public void newClass(StandardArrayList<Class, Object> parameter) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
        this.classObject = ReflectionUtil.newClass(this.classPath,parameter);
    }
    
    public Object executeMathod(String mathodname) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectionUtil.executeMathod(this.classObject, mathodname);
    }
    public Object executeMathod(String mathodname ,Class[] parameterType, Object[] parameterArgs) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectionUtil.executeMathod(this.classObject, mathodname,parameterType,parameterArgs);
    }
    public Object executeMathod(String mathodname ,StandardArrayList<Class, Object> parameter) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectionUtil.executeMathod(this.classObject, mathodname,parameter);
    }

}
