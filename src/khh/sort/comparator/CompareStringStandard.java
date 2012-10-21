package khh.sort.comparator;

import khh.std.Standard;


public class CompareStringStandard extends CompareBase<Standard<String, Object>> {

    public CompareStringStandard(int sorttype) {
        super(sorttype);
    }
    
    @Override
    public int compare(Standard<String, Object> o1, Standard<String, Object> o2) {
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
