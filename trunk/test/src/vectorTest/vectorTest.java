package vectorTest;

import java.util.Vector;

public class vectorTest {
public static void main(String[] args) {
    Vector<String> a = new Vector<String>();
    
    a.add("a1");
    a.add("a2");
    a.add("a3");
    a.add("a4");
   // a.clear();
    a.add("a5");
    a.add("a6");
    a.add("a7");
    
//    System.out.println(1<05);
    
    for (int i = 0; i < a.size(); i++) {
       // System.out.println(a.get(i));
    }
    
//    int i = a.size()-1;
//    System.out.println(i>0);
//    System.out.println( i <= 0);
    
    for (int i = a.size()-1; i >= 0; i--) {
        System.out.println(a.get(i));
    }
}
}
