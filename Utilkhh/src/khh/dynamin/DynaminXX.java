package khh.dynamin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

import org.w3c.dom.Node;

import khh.debug.LogK;
import khh.reflection.ReflectionUtil;
import khh.std.adapter.AdapterMap;
import khh.string.util.StringUtil;
import khh.xml.Element;
import khh.xml.XMLK;

public class DynaminXX {
	private AdapterMap<String,File> configlist 			= null;
	private String rootElementName						= "/";
	private ArrayList<Element> targetElements 			= null;
//	private HashMap<String,Object> targetObject			= null;
	private LogK log = LogK.getInstance();
	
	public DynaminXX() {
		configlist = new AdapterMap<String, File>();
		targetElements = new ArrayList<Element>();
//		targetObject = new HashMap<>();
	}
    public void addConfigFile(String realpath) throws Exception{
        if(realpath!=null){
            addConfigFile(new File(realpath));
        }
    }
    public void addConfigFile(File file) throws Exception{
        if(file!=null && file.exists() && file.isFile()){
            configlist.add(file.getAbsolutePath(),file);
        }
    }
    
    public void start() {
        if(configlist!=null && configlist.size()>0){
            run();
        }
    }
    
    public String getRootElementName() {
  		return rootElementName;
  	}

  	public void setRootElementName(String rootElementName) {
  		this.rootElementName = rootElementName;
  	}
  	
  	private String classxpath="/class";
  private void run() {
      
      //Global settingConfig
	  String contextpath = getRootElementName()+classxpath;
      // 우선 전체적인 config파일에서  뭐든 class파일을 불러서 넣는다.
	  XMLK xml = new XMLK();
	  xml.setFilterAddElement((Node t)->{
//			return Node.ELEMENT_NODE == t.getNodeType()
//					&&("class".equals(t.getNodeName())
//					|| "constructor".equals(t.getNodeName())
//					|| "method".equals(t.getNodeName()))
//					;
		  return true;
	  });
	  xml.setFilterExtends((Element a, Element b)->{return false;});
	  xml.setTargetXPath(contextpath);
	  
	  
      for (int configCnt = 0; configCnt < configlist.size(); configCnt++) {
          try{
              log.debug("ConfigFile["+configCnt+"]   size("+configlist.size()+")");
              xml.addConfigFile(configlist.get(configCnt));
          }catch (Exception e) {
                  log.error("Class Global Element Error ",e);
          }
      }
      
      xml.start();
      targetElements.addAll(xml.getTargetElements());
//      xml.loopNode(targetElements,(Element e, Integer depth)->{
//    	  log.debug(StringUtil.loopString("\t",depth)+e);
//      });
      
      //기본 클레스 셋팅
//      targetElements.stream().filter(aE->null!=aE.getAttr("id")&&null!=aE.getAttr("classpath")).
//      forEach(aE->{
//    	  log.debug(aE);
//    	  String id = aE.getAttr("id");
//    	  String classpath = aE.getAttr("classpath");
//    	  String value = aE.getAttr("value");
//    	  Object object = null;
//    	  try {
//    		  if(null!=value){
//    			  object = ReflectionUtil.newClass(classpath,new Object[]{value});
//    		  }else{
//    			  object = ReflectionUtil.newClass(classpath);
//    		  }
//    		  aE.setObject(object);
//		} catch (Exception e1) {e1.printStackTrace();}
//    	  //log.debug(aE);
//    	  //newClassObject(aE);
//      });

      log.debug("********기본 클레스 셋팅 (기본생성자 생성)***상속 처리*****");
//      targetElements.stream().filter(aE->null!=aE.getAttr("id")&&null!=aE.getAttr("classpath")).
//      xml.loopNode(targetElements,(Element aE)->{
//    	  log.debug(aE);
//    	  String id = aE.getAttr("id");
//    	  String classpath = aE.getAttr("classpath");
//    	  String value = aE.getAttr("value");
//    	  String extend = aE.getAttr("extends");
//    	  Object object = null;
//    	  Element parent = getElementById(extend);
//    	  //만약 상속도 원하면,   
//    	  if(null!=extend&&null!=parent){
//    		  String parentClasspath = parent.getAttr("classpath");
//    		  String parentValue = parent.getAttr("value");
//	    	  if(null!=classpath&&classpath.equals(parentClasspath)){
//	    		  aE.setObject(parent.getObject());
//	    	  }else{
//	    		  aE.setObject(newClass(classpath,value));
//	    	  }
//    	  }else{//상송원하지 않으면
//    		  if(null!=classpath)
//    		  aE.setObject(newClass(classpath,value));
//    	  }
//    	  //log.debug(aE);
//    	  //newClassObject(aE);
//      });
      
      
      
      log.debug("********마지막 화면찍기********");
      xml.loopNode(targetElements,(Element e, Integer depth)->{
    	  log.debug(StringUtil.loopString("\t",depth)+e);
      });
      
  }
  
  
	private Object newClass(String classpath, String value) {
		Object object=null;
  	  try {
		  if(null!=value){
			  object = ReflectionUtil.newClass(classpath,new Object[]{value});
		  }else{
			  object = ReflectionUtil.newClass(classpath);
		  }
  	  } catch (Exception e1) {e1.printStackTrace();}
  	 return object;
	}
	
	
	private void newClassObject(Element aE) {
		
	}
	
	public Element getElementById(String id) {
		return getElementById("id", id);
	}

	public Element getElementById(String attrName, String value) {
		for (int i = 0; value != null && i < targetElements.size(); i++) {
			Element atE = targetElements.get(i);
			if (value.equals(atE.getAttr(attrName))) {
				return atE;
			}
		}
		return null;
	}
	public static void main(String[] args) throws Exception {
		Dynamin k = new Dynamin();
		k.addConfigFile("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\dynamin\\dynamin_config.xml");
//		k.addConfigFile("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\dynamin\\dynamin_config2.xml");
		//k.setRootElementName("/dynamin");
		k.setRootElementName("/");
//		k.addConfigFile("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\dynamick\\dynamic_config.xml");
//		k.setTargetXPath("/dynamick/class");
		k.start();
	}
}
