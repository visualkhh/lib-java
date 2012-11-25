package arraylisttest;

import java.util.ArrayList;
import java.util.Date;

public class ArrayListTest {
    public static void main(String[] args) {
//        ArrayList<String> g = new ArrayList<String>();
//        String aaa = "aaaaaaaa";
//        String a="v";
//        String aa=new String("v");
//        g.add(aaa);
//        g.add(aaa);
//        g.add(a);
//        System.out.println(g.size());
//        g.remove(aa);
//        System.out.println(g.size());
//        
        
        ArrayList<Date> datelist = new ArrayList<Date>();
        Date date = new Date(111);
        datelist .add(date);
        System.out.println(datelist.size());
        date = null;
        System.out.println(datelist.size());
    }
}
