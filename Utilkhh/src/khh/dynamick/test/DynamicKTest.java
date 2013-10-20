package khh.dynamick.test;

import khh.dynamick.DynamicK;

public class DynamicKTest {
    public static void main(String[] args) throws Exception {
        DynamicK k = new DynamicK();
        k.addConfigFile("W:\\workspace\\source\\eclipse\\java\\Utilkhh\\src\\khh\\dynamick\\dynamic_config.xml");
        k.setting();
    }
}
