import java.util.HashMap;


public class test2 {

    public static void main(String[] args) {

    	String[] a = {"aa","bb","cc","dd"};
    	System.out.println(a.toString());
            HashMap<String,String> ab = new HashMap<String,String>();
            ab.put("a", "b");
            ab.put("b", "b");
            ab.put("c", "b");
            System.out.println(ab.toString());
    }
}