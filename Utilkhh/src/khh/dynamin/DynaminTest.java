package khh.dynamin;

import java.util.LinkedHashMap;

import khh.debug.LogK;


public class DynaminTest {
	static LogK log = LogK.getInstance();
	public static void main(String[] args) throws Exception {
		Dynamin d = new Dynamin();
//		d.addConfigFile("Z:\\me\\project\\personal\\web\\logger\\workspace\\logerss\\WebContent\\WEB-INF\\config\\compactk_ajax_config.xml");
//		d.setRootElementName("/compact");
		d.addConfigFile("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\dynamin\\dynamin_config.xml");
		d.start();
		LinkedHashMap<String, DynaminClass> dclassList = d.getTargetDClass();
		DynaminClass atDclass = dclassList.get("gaga");
		atDclass.newClass();
		
	}
}
