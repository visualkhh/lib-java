package kip_test;

import java.io.File;
import java.io.IOException;

import com.kdn.util.file.FileUtilKDN;
import com.khh.util.file.pack.Kip;

public class kip_test {
    private String path="C:\\write\\";
    public kip_test() {
        // TODO Auto-generated constructor stub
    }

    public void write(){
    }
    
    
    public void start() throws IOException {
        Kip kip = new Kip();
        kip.pack(new File("C:\\write"));
        FileUtilKDN.writeFile("C:\\write\\good.job", kip);
//        kip.unpack(new File("C:\\write\\unpack"));
        
        
    }

    public void end() throws IOException, ClassNotFoundException {
     //   Kip kip = (Kip) FileUtilKDN.readFileToObject("C:\\write\\good.job");
     //   kip.unpack(new File("C:\\write\\unpack"));
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        kip_test k = new kip_test();
        k.start();
        k.end();
    }

  
}
