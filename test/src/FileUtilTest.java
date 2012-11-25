import java.io.File;
import java.io.IOException;

import com.kdn.util.file.FileUtilKDN;
import com.kdn.util.property.PropertyUtil;
import com.khh.util.file.FileUtil;

public class FileUtilTest {
    public static void main(String[] args) throws IOException {
        File f = new File("./a.txt");
        f.delete();
        for (int i = 0; i < 5; i++) {
            
            FileUtil.writeFile("./a.txt", "a2김현하affa"+PropertyUtil.getSeparator()+i,true);
        }
    }
}
