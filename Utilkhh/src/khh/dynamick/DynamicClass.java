package khh.dynamick;

import static org.w3c.dom.Node.TEXT_NODE;
import static org.w3c.dom.Node.ELEMENT_NODE;
import static org.w3c.dom.Node.CDATA_SECTION_NODE;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.poi.hslf.record.ExAviMovie;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import khh.collection.StandardArrayList;
import khh.debug.LogK;
import khh.reflection.ReflectionUtil;
import khh.std.Standard;
import khh.std.adapter.AdapterMap;

public class DynamicClass {
    private Node node          = null;
//    private String id          = null;
//    private String classPath   = null;
//    private String ref         = null;
    private Object classObject = null;

    private LogK log = LogK.getInstance();
    
    private ArrayList<DynamicClass> constructorParameter        = null;
    private AdapterMap<String,ArrayList<DynamicClass>> methoadParameter    = null;
    
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

    private void setClassObject(Object classObject) {
        this.classObject = classObject;
    }
    
    
    
    public String getAttribute(String attrname){
        try{
        Attr at = (Attr) this.node.getAttributes().getNamedItem(attrname);
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


    public AdapterMap<String, ArrayList<DynamicClass>> getMethoadParameter() {
        return methoadParameter;
    }

    public void setMethoadParameter(AdapterMap<String, ArrayList<DynamicClass>> methoadParameter) {
        this.methoadParameter = methoadParameter;
    }

    public void newClassObject() throws Exception{
        log.debug("NewClassObject   id:"+getAttribute("id")+"  classpath:"+getAttribute("classpath"));
        ArrayList<DynamicClass> constructorParameter = getConstructorParameter();
        StandardArrayList<Class, Object> conParamList = new StandardArrayList<Class, Object>();
        
        //생성자 없고  value Attribute있을때에만.
        if( constructorParameter.size()==0 && ( getAttribute("value")!=null || getAttribute("value").length()>0 )){
            String classPath = getAttribute("classpath");
            String id = getAttribute("id");
            String value = getAttribute("value");
            log.debug(" Constructor NewClassObject   id:"+id+"  classpath:"+classPath+"  value:"+value);
            if(classPath==null || classPath.length()<=0){
                throw new Exception("NewClassObject  classpath IsNull or length<=0  ERROR: "+getAttribute("classpath"));
            }
            conParamList.add(new Standard<Class,Object>(value.getClass(), value));
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
            short type =node.getNodeType();
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
        setClassObject( newClass(conParamList) );
        
//        getConstructorParameter()
    }
    
    public void executeMethoad() throws Exception{
        
        AdapterMap<String, ArrayList<DynamicClass>> methoadParameter = getMethoadParameter();
//        StandardArrayList<Class, Object> methoadParamList = new StandardArrayList<Class, Object>();
        for (int i = 0; i < methoadParameter.size(); i++) { //methoad start
            String methoadName  = methoadParameter.getKey(i);
            ArrayList<DynamicClass> methoadDynamicParamList = methoadParameter.get(methoadName);
            
            StandardArrayList<Class, Object> methoadParamList = new StandardArrayList<Class, Object>();
            
            for (int j = 0; j < methoadDynamicParamList.size(); j++) {
                Object objAt = null;
                DynamicClass dynamicclassAt = methoadDynamicParamList.get(j);
                if(dynamicclassAt.getClassObject()==null){
                    dynamicclassAt.newClassObject();
                }
                objAt = dynamicclassAt.getClassObject();
                Class classAt = objAt.getClass();
                methoadParamList.add(new Standard<Class, Object>(classAt, objAt));
                
            }
            executeMathod(methoadName, methoadParamList);
            
//            Class classAt = ReflectionUtil.getClass(conParam.getAttribute("classpath"));
//            Object objAt = ReflectionUtil.newClass(classAt) ;
//            methoadParamList.add(new Standard<Class,Object>(classAt, objAt));
        }
//        setClassObject( newClass(methoadParamList) );
        
//        getConstructorParameter()
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
    
    public Object executeMathod(String mathodName) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectionUtil.executeMathod(this.classObject, mathodName);
    }
    public Object executeMathod(String mathodName ,Class[] parameterType, Object[] parameterArgs) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectionUtil.executeMathod(this.classObject, mathodName,parameterType,parameterArgs);
    }
    public Object executeMathod(String mathodName ,StandardArrayList<Class, Object> parameter) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectionUtil.executeMathod(this.classObject, mathodName,parameter);
    }

}
