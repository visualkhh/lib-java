package filetest;

import java.io.File;
import java.io.IOException;

import com.kdn.util.file.FileUtilKDN;

public class copydir {
    public static void main(String[] args) throws IOException {
        FileUtilKDN.copyFile(new File("C:\\logk") , new File("C:\\logk2"),false);
    }
}
