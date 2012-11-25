package khh.sort.comparator;

import java.util.Comparator;

public class CompareString extends CompareBase<String> {
    
    public CompareString(int sorttype) {
        super(sorttype);
    }

    public int compare(String o1, String o2) {
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
