package khh.dynamin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import khh.debug.LogK;
import khh.reflection.ReflectionUtil;
import khh.string.util.StringUtil;
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

//	public void refesh() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
//		////////
//		ArrayList<Element> method = element.getChildElementByTagName("method");
//		method.stream().filter(atMethod->atMethod.isAttr("name")).forEach(atMethod->{//매서드들..
//			try {
//				executeMethodInElement(atMethod.getAttr("name"), atMethod.getChildElementByTagName("class"));
//			} catch (Exception e) {e.printStackTrace();}
//		});
//	}
	//rmethod호출을해준값 리턴
	public Object call() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		if("new".equals(element.getAttr("life"))){
			clear();
		}
		if(null == element.getObject()){
			return newClass();
		}else{
			return call(element,null);
		}
	}
//	private Object call(Element callerE) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
//		return call(element,callerE);
//	}
	private Object call(Element element,Element callerE) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		Object object = null;
		if(null == element.getObject()){
//			element.setObject(null);
			object = newClass(element,callerE);
		}
		
		
		//rmethod실행시켜서 호출해야함.
//		Element rmethod = (callerE==null?element:callerE).getChildElementByTagNameAtLast("rmethod");
//		if(null!=rmethod&&rmethod.isAttr("name")){
//			object = executeMethodInElement(rmethod.getAttr("name"), rmethod.getChildElementByTagName("class"));
//		}else{
//			object = element.getObject();
//		}
		
		//call될때마다 실행된다!!!  리턴값은 마지막 rmethod의 리턴값을 리턴한다.  20160203변경
		object = element.getObject();
//		ArrayList<Element> rmethod = (callerE==null?element:callerE).getChildElementByTagName("rmethod");
//		for (int i = 0; i < rmethod.size(); i++) {
//			Element atRmethod = rmethod.get(i);
//			if(null!=atRmethod&&atRmethod.isAttr("name")){
//				object = executeMethodInElement((callerE==null?element:callerE),atRmethod.getAttr("name"), atRmethod.getChildElementByTagName("class"));
//			}
//		};
		
		ArrayList<Element> rmethod = element.getChildElementByTagName("rmethod");
		for (int i = 0; null==callerE && i < rmethod.size(); i++) {
			Element atRmethod = rmethod.get(i);
			if(null!=atRmethod&&atRmethod.isAttr("name")){
				object = executeMethodInElement(element,atRmethod.getAttr("name"), atRmethod.getChildElementByTagName("class"));
			}
		};
		
		
		
		return object;
	}
	
	public Object executeMethodInElement(String methodName,ArrayList<Element> param) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		return executeMethodInElement(element,methodName,param);
	}
	public Object executeMethodInElement(Element element, String methodName,ArrayList<Element> param) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		ArrayList<Object> conParam = new ArrayList<Object>();
		for (int i = 0; i < param.size(); i++) {
			Object ob = null;
			Element atE = param.get(i);
			if(null==atE.getObject() || "new".equals(atE.getAttr("life"))){
				atE.setObject(null);
				ob = newClass(param.get(i));
			}else{
				ob = atE.getObject();
			}
			conParam.add(ob);
		}
		Object[] objectParam = new Object[conParam.size()];
		conParam.toArray(objectParam);
		
		return executeMethod(element,methodName,objectParam);
//		return ReflectionUtil.executeMethod(element.getObject(),methodName,conParam);
	}
	public Object executeMethod(String methodName,Object[]param) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		return executeMethod(element,methodName,param);
	}
	public Object executeMethod(Element element, String methodName,Object[]param) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		
		Method execution = ReflectionUtil.getMethod(element.getObject().getClass(), methodName, param);
//		log.debug("execution--> "+execution);
		//aop처리 before
		targetDClass.entrySet().stream().filter(at->
				"aop".equals(at.getValue().getAttr("type")) && "before".equals(at.getValue().getAttr("around")) 
				&& (getAttr("id").equals(at.getValue().getAttr("execution"))  || StringUtil.isMatches(execution.toString(),at.getValue().getAttr("execution"))) 
				).forEach(at->{//이름하고 execution하고 같으면  또는 regex 매칭이된다면
					log.debug("AOP execution before-->"+at.getValue().getAttr("execution"));
					DynaminClass aopC = at.getValue();
					String aopMethod = aopC.getAttr("method");
					try {
						if(null==aopC.getObject()){
							aopC.newClass();
						}
						ReflectionUtil.executeMethod(aopC.getObject(), aopMethod, param);		//파라미터 똑같은매서드 호출 먼저해본다
					}catch (NoSuchMethodException e) {
						try {
							ReflectionUtil.executeMethod(aopC.getObject(), aopMethod, new Object[]{param}); //없으면 Object Array받는곳
						} catch (NoSuchMethodException e1) {
							try {
								ReflectionUtil.executeMethod(aopC.getObject(), aopMethod); //그래도없으면 파라미터없는거 호출
							} catch (Exception e2) {e2.printStackTrace();}
						}catch (Exception e11) {e11.printStackTrace();}
					}catch (Exception e) {e.printStackTrace();}
				});
		
		
		
		//body
		Object returnObj = ReflectionUtil.executeMethod(element.getObject(),methodName,param);
		
		
		
		
		//after
		targetDClass.entrySet().stream().filter(at->
				"aop".equals(at.getValue().getAttr("type")) && "after".equals(at.getValue().getAttr("around")) 
				&& (getAttr("id").equals(at.getValue().getAttr("execution"))  || StringUtil.isMatches(execution.toString(),at.getValue().getAttr("execution"))) 
				).forEach(at->{//이름하고 execution하고 같으면  또는 regex 매칭이된다면
					log.debug("AOP execution after-->"+at.getValue().getAttr("execution"));
					DynaminClass aopC = at.getValue();
					String aopMethod = aopC.getAttr("method");
					try {
						if(null==aopC.getObject()){
							aopC.newClass();
						}
						ReflectionUtil.executeMethod(aopC.getObject(), aopMethod, param);		//파라미터 똑같은매서드 호출 먼저해본다
					}catch (NoSuchMethodException e) {
						try {
							ReflectionUtil.executeMethod(aopC.getObject(), aopMethod, new Object[]{param}); //없으면 Object Array받는곳
						} catch (NoSuchMethodException e1) {
							try {
								ReflectionUtil.executeMethod(aopC.getObject(), aopMethod); //그래도없으면 파라미터없는거 호출
							} catch (Exception e2) {e2.printStackTrace();}
						}catch (Exception e11) {e11.printStackTrace();}
					}catch (Exception e) {e.printStackTrace();}
				});		
		
		return returnObj;
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
							conParam.add(extendsClass.call(extendsClass.element,conParamClass));
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
			et.setObject(ReflectionUtil.newClass(mergeExtendsAttr(et).get("classpath"), conParam));
			
			
			
			//method실행..들하고
			ArrayList<Element> method = et.getChildElementByTagName("method");
			method.stream().filter(atMethod->atMethod.isAttr("name")).forEach(atMethod->{//매서드들..
				try {
					executeMethodInElement(atMethod.getAttr("name"), atMethod.getChildElementByTagName("class"));
				} catch (Exception e) {e.printStackTrace();}
			});
			
			

			
			
			//콜
			object = call(et,callerE);//return call();
			
			
		
		}else if(null==et.getObject() && et.isAttr("extends")){
			DynaminClass dc = targetDClass.get(et.getAttr("extends"));
			if(null!=dc.getObject()){ //부모생성되어있으면 실행시키고 
				object = dc.call(dc.element,et);//return dc.call();
			}else{//부모 생성안되어있으면 생성시켜라
				object = dc.newClass(dc.element,et);
			}
			

			et.setObject(dc.getObject());
			object = call(et,callerE);//부모꺼 생성됐거나 실행됐으면 내것도 실행!!
			
			
//			Object lastO = call(et);//부모꺼 생성됐거나 실행됐으면 내것도 실행!!
//			if(null != lastO)
//				object = lastO;
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
			
			
			object = call(et,callerE);
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
	//부모 attr 머지한다 마지막 내꺼를 최신으로,.덮어.
	public HashMap<String, String> mergeExtendsAttr(Element element){
		String extend = element.getAttr("extends");
		LinkedHashMap<String, String> arrt = new LinkedHashMap<>();
		targetDClass.entrySet().stream().filter(td->td.getKey().equals(extend)).forEach(at->{
			arrt.putAll(at.getValue().getAttr());
		});
		
		arrt.putAll(element.getAttr());
		
		return arrt;
	}

	
	public Element getElement() {
		return element;
	}
	public String toString() {
		return ""+element;
	}
}
