package hashmaptest;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class hashmaptest {
public static void main(String[] args) {
  /*  Map<String, String> sessions = new ConcurrentHashMap<String, String>();
    
    sessions.put(new String("a"), "a");
    sessions.put("ab", "a");
    sessions.put(new String("a"), "b");
    System.out.println(sessions.size());
    
    
    Set<String> a  = sessions.keySet();
    Iterator<String> it = a.iterator();
    for (int i = 0; i < sessions.size(); i++) {
        String key = it.next();
        System.out.println("key : "+key+"         val: "+sessions.get(key));
    }
    
    it = a.iterator();
    for (int i = 0; i < sessions.size(); i++) {
        String key = it.next();
        System.out.println("key : "+key+"         val: "+sessions.get(key));
    }
    
    */
//    while(it.hasNext()){
//    	String key = it.next();
//    	sessions.remove(key);
//    	System.out.println("key : "+key+"         val: "+sessions.get(key));
//    	
//    }
	
	LinkedHashMap h = new LinkedHashMap();
	h.put("a", "aa");
	h.put("ba", "baa");
	h.put("ca", "caa");
	h.put("da", "daa");
	h.put("ea", "eaa");
	h.put("fa", "faa");
	h.put("ga", "gaa");
	
	
	Set s =  h.keySet();
	Iterator iter = s.iterator();
	for(int i = 0; i < s.size(); i++){
		String key = (String)iter.next();
		System.out.println(key+"      "+h.get(key));
	}
	
}
}
