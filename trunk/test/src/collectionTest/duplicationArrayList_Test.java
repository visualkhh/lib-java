package collectionTest;

import java.util.ArrayList;

import com.kdn.util.collection.DuplicationArrayList;

public class duplicationArrayList_Test {
    public static void main(String[] args) {
        DuplicationArrayList aa = new DuplicationArrayList();
        aa.add("현");
        aa.add("김");
        aa.add("하");
//        aa.add("짱");
        aa.add("하");
        aa.add("김");
//        aa.add("현");
//        aa.add("짱");
        ArrayList<String> duplication_data = aa.getDuplication();
        for (int i = 0; i < duplication_data.size(); i++) {
            System.out.println(duplication_data.get(i));
        }
        
        
        
        System.out.println("de-------------");
        
        ArrayList<String> deduplication_data = aa.getDeDuplication();
        for (int i = 0; i < deduplication_data.size(); i++) {
            System.out.println(deduplication_data.get(i));
        }
        
        System.out.println("distinct-------------");
        
        ArrayList<String> distinct_data = aa.getDistinct();
        for (int i = 0; i < distinct_data.size(); i++) {
            System.out.println(distinct_data.get(i));
        }
    }
}
