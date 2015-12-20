package khh.dynamick.test;

import khh.dynamick.DynamicK;

public class DynamicKTest {
    public static void main(String[] args) throws Exception {
        DynamicK k = new DynamicK();
        k.addConfigFile("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\dynamick\\dynamic_config.xml");
        k.setting();
        k.newClassObject();
        k.executeMethod();
    }
}
