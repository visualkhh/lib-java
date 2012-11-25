package classtest;

import java.util.ArrayList;

public class classtest {
    public static void main(String[] args) {
        int a = 1;
        System.out.println(Integer.class.getSimpleName());
        Integer gg = 5;
        System.out.println(gg instanceof Integer);
        
        
        ArrayList gaa = new ArrayList();
        
        gaa.add(1);
        gaa.add(2);
        gaa.add(3);
        gaa.add(4);
        gaa.add(new Double(4));
        gaa.add(new byte[]{1,2,3,4});
        byte av=5;
        gaa.add(av);
        
        
        for (int i = 0; i < gaa.size(); i++) {
            Object b  = gaa.get(i);
            System.out.println( b.getClass().getCanonicalName() );
            System.out.println( b instanceof byte[] );
            System.out.println( b instanceof Object );
            System.out.println(gaa.get(i));
        }
    }
}
