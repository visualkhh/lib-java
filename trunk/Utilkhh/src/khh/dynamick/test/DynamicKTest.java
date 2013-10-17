package khh.dynamick.test;

import java.io.File;

import khh.xml.XMLparser;

public class DynamicKTest {
    public static void main(String[] args) throws Exception {
//        DynamicK k = new DynamicK();
//        k.addConfigFile("W:\\workspace\\source\\eclipse\\java\\Utilkhh\\src\\khh\\dynamick\\dynamic_config.xml");
//        k.setting();
//        AdapterMap<String, DynamicClass> classlist = k.getClasslist();
        
        XMLparser g = new XMLparser(new File("W:\\workspace\\source\\eclipse\\java\\Utilkhh\\src\\khh\\dynamick\\dynamic_config.xml"));
        
        g.listNodes(g.getNode("/dynamick"), "visualkhh");
        }
}
