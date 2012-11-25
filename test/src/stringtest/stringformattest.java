package stringtest;

import java.io.File;
import java.io.FilenameFilter;

public class stringformattest {
    public static void main(String[] args) {
        Boolean sw =null;
        boolean sw2 = true;
        String a = String.format("%5s   "+sw+"   "+sw2, "김현하");
        System.out.println(a);
        
        String  filename="logk_hhk.xml";
        System.out.println(filename.startsWith("logk") && filename.endsWith(".xml"));
        
        System.out.println(filename.toUpperCase());
        
        
        /*
         boolean sw = filename.endsWith(".xml"); 
         System.out.println(sw);
         sw = filename.startsWith("logk"); 
         System.out.println(sw);
         */
//        FilenameFilter filenamefilter = new FilenameFilter() {
//            public boolean accept(File arg0, String filename) {
//                return filename.endsWith(".sql"); 
////                return true;
//            }
//        };
        
        
    }
}
