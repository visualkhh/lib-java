package duplicatiomap;

import java.lang.Thread.State;

import com.kdn.util.collection.DuplicationArrayList;


public class duptest {
    public static void main(String[] args) {
        DuplicationArrayList<State> a = new DuplicationArrayList<State>();
        
        a.add(State.NEW);
        a.add(State.NEW);
        a.add(State.NEW);
        a.add(State.NEW);
        a.add(State.NEW);
        a.add(State.NEW);
        a.add(State.NEW);
        a.add(State.RUNNABLE);
        
        System.out.println(a.getDuplicateCount(State.WAITING));
        
    }
}
