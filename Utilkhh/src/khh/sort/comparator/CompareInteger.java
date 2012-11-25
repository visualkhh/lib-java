package khh.sort.comparator;

import java.util.Comparator;

public class CompareInteger extends CompareBase<Integer> {
    
    public CompareInteger(int sorttype) {
        super(sorttype);
    }
    
    public int compare(Integer o1, Integer o2) {
        if(getSortType() == TYPE_ASC){
            // 오름차순(ASC)
            return o1.compareTo(o2);
        }else if(getSortType() ==TYPE_DESC){
            // 내림차순 (DESC)
            return o2.compareTo(o1);  
        }
        return o1.compareTo(o2);
    }
}
