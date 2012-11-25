package conversionTest;

import com.kdt.util.Utilities;
import com.kdt.util.converting.Converting;
import com.khm.util.sort.SortManager;



public class sortTest {
    public static void main(String[] args) {
        
        /*String [] a = {"a","b","c","d","e"};
        SortManager<String> sort = new SortManager<String>();
        Object[][]bb  = sort.toGroupArray(a, 2, "-", SortManager.ALIGN_LEFT);
*/
        
        Byte [] a = {1,2,3,4,5};
        SortManager<Byte> sort = new SortManager<Byte>();
        Byte[][]bb  = sort.toGroupArray(a, 2, (byte) 9, SortManager.ALIGN_LEFT);
        
        
        //Object[][] bb =  Utilities.splitGroup(a, 2);
        
        for (int i = 0; i < bb.length; i++) {
            for (int j = 0; j < bb[i].length; j++) {
                System.out.print(bb[i][j]);
            }
            System.out.println();
        }
    }
}
