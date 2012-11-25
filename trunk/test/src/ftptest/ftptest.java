package ftptest;

import java.io.IOException;
import java.net.MalformedURLException;

import com.kdt.util.ftp.FtpClient;
import com.khh.util.url.URLUtil;
import com.oroinc.net.ftp.FTPClient;
import com.oroinc.net.ftp.FTPFile;

public class ftptest {
    public static void main(String[] args) throws MalformedURLException, IOException {
//       FtpClient c = new FtpClient("168.78.203.138", 21, "usrbqm", "bktjym$al6ae");
//      c.login();
//      c.cd("/Appl1/bqm");
//       FTPFile[] files  =  c.list();
//     for (int i = 0; i < files.length; i++) {
//         System.out.println(files[i].getName());
//     }
        
        URLUtil.getInputStream("http://localhost:8090/servletTest/hello.do");
    }
}
