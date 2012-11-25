package arraylistTest;

import java.util.ArrayList;

public class ArrayListText {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        ArrayList <String> a = new ArrayList<String>();
        a.add("a");
        a.add("b");
        a.add("c");
        a.add("d");
        a.add("e");
        System.out.println(a.get(0));
        a.remove(0);
        System.out.println(a.get(0));
        System.out.println(a.size());

        ArrayList <String> ab = new ArrayList<String>(10);
        ab.add(3, "a");
        System.out.println(ab.size());
        

    }

}
