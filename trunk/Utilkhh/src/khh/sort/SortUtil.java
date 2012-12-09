package khh.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import khh.sort.comparator.CompareBase;

public class SortUtil {
    public static void sort(List list,Comparator comparator){
        Collections.sort(list,comparator);
    }
    public static void sort(List list,CompareBase comparator){
        Collections.sort(list,comparator);
    }
}
