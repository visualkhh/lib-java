package ziptest;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;

import com.kdn.util.file.FileUtilKDN;
import com.kdn.util.file.pack.ZipUtil;

public class ziptest {

    public ziptest() {
    }

    public  File[] getFileListOnlyFile(String directory) throws IOException {
        return getFileListOnlyFile(new File(directory));
    }
    public  File[] getFileListOnlyFile(File file) throws IOException {
        FileFilter ff = new FileFilter() {
            @Override
            public boolean accept(File arg0) {
                return arg0.isFile();
            }
        };
        return getFileList(file, ff);
    }
    public  File[] getFileList(File directory, FileFilter filefilter) throws IOException {
        return directory.listFiles(filefilter);
    }

    public static  void main(String[] args) throws Exception {
        new ziptest().gogosing();
    }
    
    
    
    public void gogosing() throws Exception {
//        File[] a = getFileListOnlyFile("C:\\write");
//        ArrayList<String> aa = new ArrayList<String>();
//        for (int i = 0; i < a.length; i++) {
//            String aaaa = a[i].getName();
//            aa.add(aaaa);
//            System.out.println(aa.get(i));
//        }
//        ZipUtil.zip("C:\\write", "C:\\a.zip", aa);
        ArrayList<File> a = FileUtilKDN.getFileListFullDepth("C:\\write");
        ArrayList<String> aa = new ArrayList<String>();
        for (int i = 0; i < a.size(); i++) {
            String aaaa = a.get(i).getPath().replace("C:\\write\\", "");
            aa.add(aaaa);
            System.out.println(aa.get(i));
        }
        ZipUtil.zip("C:\\write", "C:\\a.zip", aa);
    }
}
