package resourcetest;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.khh.util.file.FileUtil;

public class ResourceTest {
    public ResourceTest(){
    }
    
    public void start() throws IOException{
        int aaa = 5/0;
        URL a = getClass().getResource("/log4j.xml");
        if(a==null){
            System.out.println("null");
        }else{
            System.out.println(a.getFile());
            System.out.println(a.toString());
            
            String va = FileUtil.readeFileToString(a.getFile());
            System.out.println(va);
        }
    }
    
    
    
    
    
    public static void main(String[] args) throws IOException {
        new ResourceTest().start();
    }
}
