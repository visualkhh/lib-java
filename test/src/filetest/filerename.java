package filetest;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import com.kdn.util.UtilitiyKDN;
import com.kdn.util.file.FileUtilKDN;
import com.khh.util.file.FileUtil;

public class filerename {

    public static void main(String[] args) throws IOException {
        String path="W:\\workspace\\source\\eclipse\\java\\java3D\\src\\org\\selman\\java3d\\book";
        System.out.println(path);
        File[] t =  FileUtilKDN.getFileList(path, new FileFilter() {
            @Override
            public boolean accept(File arg0) {
                return arg0.isDirectory();
            }
        });
        
        for (int i = 0; i < t.length; i++) {
            File file = t[i];
            String a = file.getName();
            File filea = new File(file.getParentFile(),a.toLowerCase());
            FileUtil.FileRename(file, filea);
//            System.out.println(file.getName()+"    "+file.getName().toLowerCase()+"     "+filea.getName()+"    "+filea.getName().toLowerCase());
        }
    }
}