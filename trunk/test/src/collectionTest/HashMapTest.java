package collectionTest;

import java.util.HashMap;
import java.util.Iterator;

public class HashMapTest {
    public static void main(String[] args) {
        HashMap map = new HashMap();
        
        map.put("a", 1);
        map.put(new String("a"), 2);
        map.put(new String("a"), 2);
        map.put(new String("a"), 2);
        map.put(new String("a"), 2);
        Iterator iter = map.keySet().iterator();
        
        while(iter.hasNext()){
            String key =(String) iter.next();
            System.out.println(key+"       "+ map.get(key));
        }
        
    }
}
