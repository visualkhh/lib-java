package khh.sort.comparator;

import khh.std.Standard;


public class CompareIntegerStandard extends CompareBase<Standard<Integer, Object>> {

    public CompareIntegerStandard(int sorttype) {
        super(sorttype);
    }
    
    @Override
    public int compare(Standard<Integer, Object> o1, Standard<Integer, Object> o2) {
        if(getSortType() == TYPE_ASC){
            // 오름차순(ASC)
            return o1.getKey().compareTo(o2.getKey());
        }else if(getSortType() ==TYPE_DESC){
            // 내림차순 (DESC)
            return o2.getKey().compareTo(o1.getKey());  
        }
        return o1.getKey().compareTo(o2.getKey());
    }
 
}
