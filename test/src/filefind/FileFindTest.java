package filefind;

import java.io.File;

import khh.debug.LogK;

public class FileFindTest {
    public static LogK log = LogK.getInstance();
    public static void main(String[] args) {
        log.debug("a");
            File file = new File("c:\\go\\ja\\good.txt");
            System.out.println(file.getAbsolutePath());
            System.out.println(file.getPath());
            System.out.println(file.getParent());
            System.out.println(file.getName());
    }
}
