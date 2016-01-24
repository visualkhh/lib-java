package khh.dynamin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import khh.debug.LogK;
import khh.reflection.ReflectionUtil;
import khh.xml.Element;

public class DynaminClass {
	private LogK log = LogK.getInstance();
	private LinkedHashMap<String, DynaminClass> targetDClass = null;
	private Element element = null;
	public DynaminClass() {
	}
	public DynaminClass(Element element) {
		this.element = element;
	}
	public DynaminClass(Element element,LinkedHashMap<String, DynaminClass> targetDClass) {
		this.element = element;
		this.targetDClass = targetDClass;
	}

	//rmethod호출을해준값 리턴
	public Object call() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		if(null == element.getObject()){
			return newClass();
		}else{
			return call(null);
		}
	}
	private Object call(Element callerE) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		Object object = null;
		if(null == element.getObject()){
			object = newClass(element,callerE);
		}
		//rmethod실행시켜서 호출해야함.
		Element rmethod = (callerE==null?element:callerE).getChildElementByTagNameAtLast("rmethod");
		if(null!=rmethod&&rmethod.isAttr("name")){
			object = executeMethodInElement(rmethod.getAttr("name"), rmethod.getChildElementByTagName("class"));
		}else{
			object = element.getObject();
		}
		return object;
	}
	public Object executeMethodInElement(String methodName,ArrayList<Element> param) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		ArrayList<Object> conParam = new ArrayList<>();
		for (int i = 0; i < param.size(); i++) {
			Object ob = null;
			Element atE = param.get(i);
			if(null!=atE.getObject()){
				ob = atE.getObject();
			}else{
				ob = newClass(param.get(i));
			}
			conParam.add(ob);
		}
		return ReflectionUtil.executeMethod(element.getObject(),methodName,conParam);
	}
	public Object executeMethod(String methodName,Object[]param) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		return ReflectionUtil.executeMethod(element.getObject(),methodName,param);
	}
//	public Object executeMethod(String methodName) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
//		
//	}
	public Object getObject() {
		return element.getObject();
	}
	
	private Object newNormalTypeClass(Element et){
		  Object object=null;
		  String classpath = et.getAttr("classpath");
		  String value = et.getAttr("value");
		  try {
			  if(null!=classpath && null!=value){
				  object = ReflectionUtil.newClass(classpath,new Object[]{value});
			  }else if(null!=classpath){
				  object = ReflectionUtil.newClass(classpath);
			  }
		  } catch (Exception e1) {e1.printStackTrace();}
		 return object;
	  }
	  
	  

	//객체를 생성한다.
	public Object newClass() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		return newClass(element);
	}
	private Object newClass(Element et) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		return newClass(et,null);
	}
	private Object newClass(Element et,Element callerE) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		//아이디있고, 클래스패스 있고 , 자식없고 상속안받으면 그냥 생성
		if(null==et.getObject()&& et.isAttr("classpath") && et.getChildElementSize()<=0 && !et.isAttr("extends")){
			et.setObject(newNormalTypeClass(et));
			return et.getObject();
		}
		
		
		String classpath	= et.getAttr("classpath");
		String id 			= et.getAttr("id");
		String extend 		= et.getAttr("extends");
		
		
			
		Object object = null;
		//생성자 있으면  constructor  가있으면 생성한다.
		Element constructor = et.getChildElementByTagNameAtLast("constructor");
		if(null==et.getObject()&&null!=constructor ){//여기서 생성 및 메서드 실행시켜야한다.
			//생성에대한것.
			ArrayList<Object> conParam = new ArrayList<>();
			((ArrayList<Element>)constructor.getChildElementByTagName("class")).stream().forEach(conParamClass->{//생성자
				DynaminClass extendsClass = targetDClass.get(conParamClass.getAttr("extends"));
				try {
					if(null!=extendsClass){	//상속찾으면
							conParam.add(extendsClass.call(conParamClass));
					}else{//상속못찾으면 그냥 생성
						if(null!=conParamClass.getObject()){
							conParam.add(conParamClass.getObject());
						}else{
							conParam.add(newClass(conParamClass));
						}
					}
				} catch (Exception e) {e.printStackTrace();}
				//extendsClass.
			});
			
			
			//클래스패스 없으면  extends있으면 부모꺼 클래스패스씀
			et.setObject(ReflectionUtil.newClass(mergeExtendsAttr().get("classpath"), conParam));
			
			
			
			//method실행..들하고
			ArrayList<Element> method = et.getChildElementByTagName("method");
			method.stream().filter(atMethod->atMethod.isAttr("name")).forEach(atMethod->{//매서드들..
				try {
					executeMethodInElement(atMethod.getAttr("name"), atMethod.getChildElementByTagName("class"));
				} catch (Exception e) {e.printStackTrace();}
			});
			
			

			
			
			//콜
			object = call(callerE);//return call();
			
			
		
		}else if(null==et.getObject() && et.isAttr("extends")){
			DynaminClass dc = targetDClass.get(et.getAttr("extends"));
			if(null!=dc.getObject()){ //부모생성되어있으면 실행시키고 
				object = dc.call(et);//return dc.call();
			}else{//부모 생성안되어있으면 생성시켜라
				object = dc.newClass(dc.element,et);
			}
			et.setObject(dc.getObject());
			//object = call();
			
		//생성자 없으면
		}else if(null!=et.getObject()){// 여기서는 이미 생성되어 있으니 rmethod로 호출된 값을 리턴한다.
			object = call();//return call();
		}else{ //매서드는 있지만 생성자가없는경우 디폴트 생성자 실행
			et.setObject(newNormalTypeClass(et));
			object = et.getObject();
			//method실행..들하고
			ArrayList<Element> method = et.getChildElementByTagName("method");
			method.stream().filter(atMethod->atMethod.isAttr("name")).forEach(atMethod->{//매서드들..
				try {
					executeMethodInElement(atMethod.getAttr("name"), atMethod.getChildElementByTagName("class"));
				} catch (Exception e) {e.printStackTrace();}
			});
			
			
			object = call(et);
		}
			
			
		return object;
	}
	
	
	public void clear(){
		element.setObject(null);
	}
	public Map<String, String> getAttr() {
		return element.getAttr();
	}
	public boolean isAttr(String attrName) {
		return element.isAttr(attrName);
	}
	public String getAttr(String attrName){
		return element.getAttr(attrName);
	}
	//부모 attr 머지한다 마지막 내꺼를 최신으로,.덮어.
	public HashMap<String, String> mergeExtendsAttr(){
		String extend = element.getAttr("extends");
		LinkedHashMap<String, String> arrt = new LinkedHashMap<>();
		targetDClass.entrySet().stream().filter(td->td.getKey().equals(extend)).forEach(at->{
			arrt.putAll(at.getValue().getAttr());
		});
		
		arrt.putAll(getAttr());
		
		return arrt;
	}

	
	public Element getElement() {
		return element;
	}
	public String toString() {
		return ""+element;
	}
}
