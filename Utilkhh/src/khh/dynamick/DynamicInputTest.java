package khh.dynamick;

public class DynamicInputTest {
    String  a = "a_default";
    Integer b = null;
    Boolean c = null;
    public DynamicInputTest() {
    	System.out.println("DynamicInputText new");
	}
    
    public DynamicInputTest(DynamicInputTest d) {
    	System.out.println("DynamicInputText Dyn-->"+d);
	}
    
    public DynamicInputTest(String d,String a) {
    	System.out.println("DynamicInputText str,str-->"+d+"      "+a);
    }
    
    public DynamicInputTest(String a) {
    	System.out.println("DynamicInputText new  A:"+a);
    	this.a=a;
    }
    public DynamicInputTest(String a, Integer b, Boolean c) {
        this.a = a;
        this.b = b;
        this.c = c;
        System.out.println("DynamicInputText"+a+b+c);
    }

    public String getA() {
        System.out.println("getA Call : "+a);
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
        System.out.println("getB Call"+b);
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

    public void print(String a){
    	System.out.println("----"+a);
    }
}
