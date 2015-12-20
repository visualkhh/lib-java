package khh.dynamick;

public class DynamicInputTest {
    String  a = null;
    Integer b = null;
    Boolean c = null;
    public DynamicInputTest() {
    	System.out.println("DynamicInputText new");
	}
    public DynamicInputTest(String a, Integer b, Boolean c) {
        this.a = a;
        this.b = b;
        this.c = c;
        System.out.println("DynamicInputText"+a+b+c);
    }

    public String getA() {
        System.out.println("getA"+a);
        return a;
    }

    public void setA(String a) {
        System.out.println("setA"+a);
        this.a = a;
    }
    public void setA(String a, String b) {
        System.out.println("setAAA**********"+a+"  "+b);
        this.a = a;
    }

    public Integer getB() {
        System.out.println("getB"+b);
        return b;
    }

    public void setB(Integer b) {
        System.out.println("setB"+b);
        this.b = b;
    }

    public Boolean getC() {
        System.out.println("getC"+c);
        return c;
    }

    public void setC(Boolean c) {
        System.out.println("setC"+c);
        this.c = c;
    }

}
