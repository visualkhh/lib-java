package khh.dynamick;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import khh.collection.StandardArrayList;
import khh.debug.LogK;
import khh.reflection.ReflectionUtil;
import khh.std.Standard;
import khh.std.adapter.AdapterMap;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;

public class DynamicClass {
    private Node node          = null;
//    private String id          = null;
//    private String classPath   = null;
//    private String ref         = null;
    private Object classObject = null;

    private LogK log = LogK.getInstance();
    
    private ArrayList<DynamicClass> constructorParameter        = null;
//    private AdapterMap<String, Standard<String, ArrayList<DynamicClass>>> methoadParameter    = null;
    private AdapterMap<Node, ArrayList<DynamicClass>> methodParameter    = null;
    
    public int newClassObjectCnt = 0;
    public DynamicClass() {
    }

    public DynamicClass(Node node) {
        setNode(node);
    }
    
    
    
    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    
//    public void getConstructorParameter(){
//        
//    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getClassPath() {
//        return classPath;
//    }
//
//    public void setClassPath(String classPath) {
//        this.classPath = classPath;
//    }
//
//
//    public String getRef() {
//        return ref;
//    }
//
//    public void setRef(String ref) {
//        this.ref = ref;
//    }
    
    public Object getClassObject() {
        return this.classObject;
    }

    public void setClassObject(Object classObject) {
        this.classObject = classObject;
    }
    
    
    
    public String getAttribute(String attrname){
    	return getAttribute(this.node, attrname);
    }
    public String getAttribute(Node node, String attrname){
    	Attr at = (Attr) node.getAttributes().getNamedItem(attrname);
    	return getAttribute(at, attrname);
    }
    public String getAttribute(Attr at,String attrname){
        try{
            return at.getValue();
        }catch (Exception e) {
            return null;
        }
    }
    
    
    
//    public  StandardArrayList<Class,Object> getParameter(DynamicClass dynamicclass){
//        return null;
//    }
    public ArrayList<DynamicClass> getConstructorParameter() {
        return constructorParameter;
    }

    public void setConstructorParameter(ArrayList<DynamicClass> constructorParameter) {
        this.constructorParameter = constructorParameter;
    }




    public void newClassObject() throws Exception{
        log.debug("NewClassObject   id:"+getAttribute("id")+"  classpath:"+getAttribute("classpath"));
        ArrayList<DynamicClass> constructorParameter = getConstructorParameter();
        StandardArrayList<Class, Object> conParamList = new StandardArrayList<Class, Object>();
        
        //생성자 없다  
//        if( constructorParameter.size()==0 && ( getAttribute("value")!=null || getAttribute("value").length()>0 )){
        if(constructorParameter.size()==0){
            String classPath = getAttribute("classpath");
            String id = getAttribute("id");
            String value = getAttribute("value");
            log.debug(" Constructor NewClassObject   id:"+id+"  classpath:"+classPath+"  value:"+value);
            if(classPath==null || classPath.length()<=0){
                throw new Exception("NewClassObject  classpath IsNull or length<=0  ERROR: "+getAttribute("classpath"));
            }
            if(value != null){ //value Attribute있을때에만.
            	conParamList.add(new Standard<Class,Object>(value.getClass(), value));
            }
            setClassObject( newClass(conParamList) );
            return;
        }
        
        
        for (int i = 0; i < constructorParameter.size(); i++) {
            DynamicClass conParam = constructorParameter.get(i);
            Object objAt = null;
            String classPath = null;
            String id = null;
            //안쪽  다음text타입 노드일때 안쪽내용을..초기값으로
            Node node = conParam.getNode();
            short type = node.getNodeType();
            classPath = conParam.getAttribute("classpath");
            id = conParam.getAttribute("id");
            log.debug(" Constructor NewClassObject   id:"+id+"  classpath:"+classPath);
            if(classPath==null || classPath.length()<=0){
                throw new Exception("NewClassObject  classpath IsNull or length<=0  ERROR: "+conParam.getAttribute("classpath"));
            }
//            if(conParam.getClassObject() == null){
//                objAt = ReflectionUtil.newClass(classPath) ;
//            }else{
//                objAt = conParam.getClassObject();
//            }
            if(conParam.getClassObject() == null){
                 conParam.newClassObject() ;
            }
            objAt = conParam.getClassObject();
            Class classAt = objAt.getClass();
            conParamList.add(new Standard<Class,Object>(classAt, objAt));
        }
        setClassObject(newClass(conParamList));
        newClassObjectCnt++;
//        getConstructorParameter()
    }

    
    
    
    
    //methoad---------
    public Node getMethodNode(String id) throws Exception {
    	Node metHodNode = null;
    	for (int i = 0; i < getMethodParameter().size(); i++) {
    		Node node =  getMethodParameter().getKey(i);
    		ArrayList<DynamicClass> methodParameters =  getMethodParameter().get(i);
			if(id.equals(getAttribute(node, "id"))){
				metHodNode = node;
				break;
			}
		}
    	return metHodNode;
    }
    
    public ArrayList<DynamicClass> getMethodParameter(String id) throws Exception {
    	ArrayList<DynamicClass> list = null;
    	for (int i = 0; i < getMethodParameter().size(); i++) {
    		Node node =  getMethodParameter().getKey(i);
    		ArrayList<DynamicClass> methodParameters =  getMethodParameter().get(i);
			if(id.equals(getAttribute(node, "id"))){
				list = methodParameters;
				break;
			}
		}
    	return list;
    }

    public AdapterMap<Node, ArrayList<DynamicClass>> getMethodParameter() {
        return methodParameter;
    }

    public void setMethodParameter(AdapterMap<Node, ArrayList<DynamicClass>> methodParameter) {
        this.methodParameter = methodParameter;
    }
    
    
    public void executeMethod() throws Exception{
    	AdapterMap<Node, ArrayList<DynamicClass>> methodParameter = getMethodParameter();
//        StandardArrayList<Class, Object> methoadParamList = new StandardArrayList<Class, Object>();
        for (int i = 0; i < methodParameter.size(); i++) { //methoad start
        	Node key = methodParameter.getKey(i);
        	ArrayList<DynamicClass> parameters = methodParameter.get(i);
        	executeMethod(key);
        	//executeMethod(getAttribute(key, "name") ,parameters);
        }
//        setClassObject( newClass(methoadParamList) );
//        getConstructorParameter()
    }
    
    public Object executeMethod(String id) throws Exception {
    	Node methodNode = getMethodNode(id);
    	return executeMethod(methodNode);
    }
    public Object executeMethod(Node methodNode) throws Exception {
    	String id = getAttribute(methodNode, "id");
    	String name = getAttribute(methodNode, "name");
    	return executeMethod(name, getMethodParameter(id));
    }
    
    public Object executeMethod(String methodName, ArrayList<DynamicClass> methodParameters) throws Exception{
    	//String methoadId  = methoadParameter.getKey(i);
        StandardArrayList<Class, Object> methodParamList = new StandardArrayList<Class, Object>();
        
        for (int j = 0; j < methodParameters.size(); j++) {
            Object objAt = null;
            DynamicClass dynamicclassAt = methodParameters.get(j);
            if(dynamicclassAt.getClassObject()==null){
                dynamicclassAt.newClassObject();
            }
            objAt = dynamicclassAt.getClassObject();
            //Class classAt = objAt.getClass();
            Class classAt = ReflectionUtil.getClass(dynamicclassAt.getAttribute("classpath"));
            methodParamList.add(new Standard<Class, Object>(classAt, objAt));
        }
        return executeMethod(methodName, methodParamList);
    }
    

    //public Object executeMethod(String mathodName , Object[] parameterArgs) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    //	return ReflectionUtil.executeMethod(this.classObject, mathodName, parameterArgs);
    //}
    public Object executeMethod(String mathodName ,Class[] parameterType, Object[] parameterArgs) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectionUtil.executeMethod(this.classObject, mathodName,parameterType,parameterArgs);
    }
    private Object executeMethod(String mathodName ,StandardArrayList<Class, Object> parameter) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectionUtil.executeMethod(this.classObject, mathodName,parameter);
    }
    
    
    private Object newClass() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
        return ReflectionUtil.newClass(getAttribute("classpath"));
    }
    private Object newClass(Class[] constructorParamType ,Object[] constructorArgs) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
        return ReflectionUtil.newClass(getAttribute("classpath"),constructorParamType,constructorArgs);
    }
    private Object newClass(StandardArrayList<Class, Object> parameter) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
        return ReflectionUtil.newClass(getAttribute("classpath"),parameter);
    }
    
    
    



}
