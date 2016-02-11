package khh.test.dynamin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import khh.debug.LogK;
import khh.dynamin.Dynamin;
import khh.dynamin.DynaminClass;
import khh.file.util.FileUtil;
import khh.string.util.StringUtil;
import khh.xml.Element;
import khh.xml.XMLK;


public class DynaminTest {
	static LogK log = LogK.getInstance();
	public static void main(String[] args) throws Exception {
		Dynamin d = new Dynamin();
//		d.addConfigFile("Z:\\me\\project\\personal\\web\\logger\\workspace\\logerss\\WebContent\\WEB-INF\\config\\compactk_ajax_config.xml");
//		d.setRootElementName("/compact");
		String path = FileUtil.getPath(DynaminTest.class)+"dynaminConfig.xml";
//		String path = FileUtil.getPath(DynaminTest.class)+"dynamin_configHashMap.xml";
//		String path = FileUtil.getPath(DynaminTest.class)+"dynamin_config.xml";
		log.debug(path);
		d.addConfigFile(path);
		d.start();
		LinkedHashMap<String, DynaminClass> dclassList = d.getTargetDClass();
		
		String a = null;
		
		DynaminClass atDclass = dclassList.get("gazzzzga");
//		DynaminClass atDclass = dclassList.get("ga");
		Object  o  = atDclass.call();
		log.debug(o);
//
		log.debug("----------------");
		o  = atDclass.call();
		log.debug(o);
		
		
//		atDclass = dclassList.get("gggg");
//		o  = atDclass.call();
//		log.debug(o);
		
		
//		log.debug("---------********-------");
//		atDclass.clear();
//		o  = atDclass.call();
//		log.debug(o);
//		o  = atDclass.call();
//		log.debug(o);
//		
//		DynaminClass atDclass = dclassList.get("ga");
////		atDclass.refesh();
//		
//		
//		Object o =atDclass.call();
//		log.debug(o);
//		
////		atDclass = dclassList.get("gazzzzga");
////		o =atDclass.call();
//		
//		log.debug(atDclass.getObject());
//		
////		atDclass = dclassList.get("gazzzzga");
////		o =atDclass.call();
////		log.debug(o);
//		
////		DynaminClass aop1= dclassList.get("aop1");
		 
		
		
		
//		XMLK xml = new XMLK("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\dynamin\\dynamin_config.xml");
//		xml.setTargetXPath("/dynamin/*");
//		xml.start();
//		ArrayList<Element> te = xml.getTargetElements();
//		xml.loopNode(te, (Element e, Integer depth)->{
//			log.debug(StringUtil.loopString("\t",depth)+e);
//		});
		
	}
}
