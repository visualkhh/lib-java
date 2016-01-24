package khh.dynamin;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import khh.debug.LogK;
import khh.string.util.StringUtil;
import khh.xml.Element;
import khh.xml.XMLK;


public class DynaminTest {
	static LogK log = LogK.getInstance();
	public static void main(String[] args) throws Exception {
		Dynamin d = new Dynamin();
//		d.addConfigFile("Z:\\me\\project\\personal\\web\\logger\\workspace\\logerss\\WebContent\\WEB-INF\\config\\compactk_ajax_config.xml");
//		d.setRootElementName("/compact");
		d.addConfigFile("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\dynamin\\dynamin_config.xml");
		d.start();
		LinkedHashMap<String, DynaminClass> dclassList = d.getTargetDClass();
		DynaminClass atDclass = dclassList.get("gazzzzga");
		//atDclass.newClass();
		Object o =atDclass.call();
		log.debug(o);
		 
//		XMLK xml = new XMLK("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\dynamin\\dynamin_config.xml");
//		xml.setTargetXPath("/dynamin/*");
//		xml.start();
//		ArrayList<Element> te = xml.getTargetElements();
//		xml.loopNode(te, (Element e, Integer depth)->{
//			log.debug(StringUtil.loopString("\t",depth)+e);
//		});
		
	}
}
