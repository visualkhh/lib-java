package conversionTest;

import java.util.ArrayList;

import com.kdn.util.convesion.ConversionUtil;

public class ConversionTest {
    
    
    private void get(Integer aa) {
        System.out.println("int CLASS");
    }
    
    
    private void get(int a) {
      System.out.println("int");
    }
    
    
    public static void main(String[] args) {

        String boolean_s = "FALSE";
        boolean boolean_b = Boolean.parseBoolean(boolean_s);
        
        System.out.println(boolean_b);
        
        Double a = (double) 4444444444.444444;
        System.out.println(a);
        
        
        ConversionTest c = new ConversionTest();
        c.get((Integer)1);
        
        
        System.out.println(Integer.MAX_VALUE);
        
        
        
        
        
        ArrayList<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("aaab");
        
        Object[] ggg  = ConversionUtil.arrayListToArray(list);
        
        for (int i = 0; i < ggg.length; i++) {
            String string =(String) ggg[i];
            System.out.println(string);
        }
    }
}
