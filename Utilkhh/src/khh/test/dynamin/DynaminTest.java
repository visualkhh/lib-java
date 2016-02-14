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
		String path = FileUtil.getPath(DynaminTest.class)+"dynaminConfig.xml";
		log.debug(path);
		d.addConfigFile(path);
		d.start();
		LinkedHashMap<String, DynaminClass> dclassList = d.getTargetDClass();
		
		String a = null;
		
		DynaminClass atDclass = dclassList.get("gazzzzga");
		Object  o  = atDclass.call();
		log.debug(o);
//
		log.debug("----------------");
		o  = atDclass.call();
		log.debug(o);
		
		
	}
}
