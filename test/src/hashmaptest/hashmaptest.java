package hashmaptest;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class hashmaptest {
public static void main(String[] args) {
    Map<String, String> sessions = new ConcurrentHashMap<String, String>();
    
    sessions.put("a", "a");
    sessions.put("ab", "a");
    sessions.put("a", "b");
    System.out.println(sessions.size());
    
    
    Set<String> a  = sessions.keySet();
    Iterator<String> it = a.iterator();
    for (int i = 0; i < sessions.size(); i++) {
        String key = it.next();
        System.out.println("key : "+key+"         val: "+sessions.get(key));
    }
}
}
