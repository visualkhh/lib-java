package filetest;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import com.kdn.util.UtilitiyKDN;
import com.kdn.util.file.FileUtilKDN;
import com.khh.util.file.FileUtil;

public class filelist_test {
    public static void main(String[] args) {
           try {
//            File[] file = FileUtil.getFileListToFile("C:\\write");
//            for (int i = 0; i < file.length; i++) {
//                System.out.println(file[i].getName());
//            }
               FilenameFilter a = new FilenameFilter() {
                   @Override
                   public boolean accept(File dir, String name) {
                       return true;
                   }
              };
              FileFilter af = new FileFilter() {
                  @Override
                  public boolean accept(File dir) {
                         
                     // return UtilitiyKDN.isFind("^새 텍스트 문서.txt", dir.getName());
                      return true;
                  }
             };
            
            
//            File[] file =FileUtilKDN.getFileList("C:\\write", a);
//             for (int i = 0; i < file.length; i++) {
//                // System.out.println("ff  "+file[i].getName());
//             }
            
            
//             File[] file  = new File("C:\\write\\tt.obj").listFiles();
//             for (int i = 0; i < file.length; i++) {
//                 System.out.println("ff  "+file[i].getName());
//             }
               
               
               
            ArrayList<File>  files  =FileUtilKDN.getFileListOnlyFileFullDepth("c:\\write");
             for (int i = 0; i < files.size(); i++) {
                 File file  =  files.get(i);
                 System.out.println("ff  "+file.getParent()+"    "+file.getAbsolutePath()+"     "+file.getName()+"     isFile: "+file.isFile()+"       size: "+file.length());
             }
            
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
