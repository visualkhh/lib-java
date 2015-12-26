package khh.dynamin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Predicate;

import org.w3c.dom.Node;

import khh.debug.LogK;
import khh.reflection.ReflectionUtil;
import khh.std.adapter.AdapterMap;
import khh.string.util.StringUtil;
import khh.xml.Element;
import khh.xml.XMLK;

public class Dynamin {
	private LinkedHashMap<String,File> configlist 			= null;
	private String rootElementName							= "/dynamin";
	private LinkedHashMap<String, DynaminClass> targetDClass= null;
	private LogK log = LogK.getInstance();
	
	public Dynamin() {
		configlist = new LinkedHashMap<String, File>();
		targetDClass = new LinkedHashMap<String, DynaminClass>();
	}
    public void addConfigFile(String realpath) throws Exception{
        if(realpath!=null){
            addConfigFile(new File(realpath));
        }
    }
    public void addConfigFile(File file) throws Exception{
        if(file!=null && file.exists() && file.isFile()){
            configlist.put(file.getAbsolutePath(),file);
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
			return Node.ELEMENT_NODE == t.getNodeType()
					&&("class".equals(t.getNodeName())
					|| "constructor".equals(t.getNodeName())
					|| "method".equals(t.getNodeName())
					|| "rmethod".equals(t.getNodeName())
					);
	  });
	  xml.setFilterExtends((Element parent, Element child)->{
		  return false;
	  });
	  xml.setTargetXPath(contextpath);
	  ArrayList<Element> targetXMLKElements =  new ArrayList<>();
	  
	  configlist.entrySet().stream().forEach(et->{
          try{
              xml.addConfigFile(et.getValue());
          }catch (Exception e) {
                  log.error("Class Global Element Error ",e);
          }
	  });
      xml.start();
      
      log.debug("*****XMLK에서가져온거 Dynamin에셋팅******");
      ArrayList<Element> xmlkList = xml.getTargetElements();
      xmlkList.stream().filter(et->null!=et.getAttr("id")).forEach(et->{
    	  targetDClass.put(et.getAttr("id"), new DynaminClass(et,targetDClass));
      });
      
      
      //log.debug("*****기본타입 생성합니다******");
      //xmlkList.stream().filter(et->null!=et.getAttr("id")&&et.getChildElementSize()<=0).forEach(et->{
    	  //et.setObject(newNormalTypeClass(et));
      //});
      
      
      
      
      //////////////////////////////////////////////////////////////////
//      log.debug("화면찍기1");
//      targetDClass.entrySet().stream().forEach(et->{
//    	  //Object object = newClass(et.getValue());
//    	  //et.
//    	  log.debug(et);
//      });
      
      log.debug("****화면찍기****");
      xml.loopNode(xmlkList,(Element e, Integer depth)->{
    	  log.debug(StringUtil.loopString("\t",depth)+e);
      });
      
      log.debug("****SETTING END****");
      
  }
  
  

	
	
	
	
	public LinkedHashMap<String, DynaminClass> getTargetDClass() {
		return targetDClass;
	}
	public static void main(String[] args) throws Exception {
		Dynamin k = new Dynamin();
		k.addConfigFile("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\dynamin\\dynamin_config.xml");
//		k.addConfigFile("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\dynamin\\dynamin_config2.xml");
		//k.setRootElementName("/dynamin");
		//k.setRootElementName("/");
//		k.addConfigFile("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\dynamick\\dynamic_config.xml");
//		k.setTargetXPath("/dynamick/class");
		k.start();
//		LinkedHashMap<String, DynaminClass> dclassList = k.getTargetDClass();
//		DynaminClass dc= dclassList.get("f");
//		 dc.newClass();
		
	}
}
