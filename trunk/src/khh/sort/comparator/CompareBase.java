package khh.sort.comparator;

import java.util.Comparator;

public abstract class CompareBase<T> implements Comparator<T> {
    final public static int TYPE_DESC=1; 
    final public static int TYPE_ASC=1; 
    private int sortType=TYPE_ASC;

    
    public CompareBase(){
    }
    public CompareBase(int sortType){
        setSortType(sortType);
    }
    
    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }
    
    
}
