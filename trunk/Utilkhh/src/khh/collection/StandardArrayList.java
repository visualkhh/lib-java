package khh.collection;

import java.util.ArrayList;

import khh.std.Standard;

public class StandardArrayList<K,V> extends ArrayList<Standard<K,V>> {
    public ArrayList<K> getKeyArrayList(){
        ArrayList<K> k = new ArrayList<K>();
        for (int i = 0; i < this.size(); i++) {
         k.add(get(i).getKey());   
        }
        return k;
    }
    
    public ArrayList<V> getValueArrayList(){
        ArrayList<V> v = new ArrayList<V>();
        for (int i = 0; i < this.size(); i++) {
         v.add(get(i).getValue());   
        }
        return v;
    }

}
