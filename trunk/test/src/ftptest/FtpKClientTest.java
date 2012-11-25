package ftptest;

import java.io.IOException;
import java.net.SocketException;

import com.kdn.util.ftp.FTPKClient;
import com.oroinc.net.ftp.FTPClient;
import com.oroinc.net.ftp.FTPFile;

public class FtpKClientTest {
    FTPKClient ftpClient;
//    private  String sServer = "100.1.26.72"; 
    private  String sServer = "100.1.26.84"; 
    private  int iPort = 21;
    private  String sId = "dist"; 
    private  String sPassword = "ghdcd*al!vh2";
    FtpKClientTest() {
        ftpClient=new FTPKClient();
    }
    public void start() throws SocketException, IOException{
        ftpClient.connect(sServer, iPort);
        
        
        boolean loginsw = ftpClient.login(sId, sPassword);
        System.out.println(ftpClient.getReplyString());
        
        System.out.println(ftpClient.isConnected()+"          "+loginsw);
        
        //ftpClient.sendSiteCommand("chmod 000 testqrcode");
        ftpClient.syst();
        System.out.println(ftpClient.getReplyString());
       // ftpClient.pwd();
        //System.out.println(ftpClient.getReplyString());
        
        ftpClient.sendCommand("PASV");
        System.out.println(ftpClient.getReplyString());
        
        
        
        ftpClient.sendCommand("TYPE I");
        System.out.println(ftpClient.getReplyString());
        


        
        
        
      //  ftpClient.cd("hhk");
        if(loginsw){
           FTPFile[] files  =  ftpClient.listFiles();
            for (int i = 0; files!=null && i < files.length ; i++) {
                System.out.println(files[i].getName()+"      "+files[i].getType());
            }
        }
           
//          ftpClient.sendSiteCommand("mkdir test");
        //ftpClient.cd("/app/dist/KH_MESU_FILES/desn_map/460720124049");
      //  System.out.println(ftpClient.getReplyString());
        
        ftpClient.cd("umask 777");
          System.out.println(ftpClient.getReplyString());
          
        
          ftpClient.mkdir("test");
          System.out.println(ftpClient.getReplyString());
        //ftpClient.chmod("777", "test");
    //    ftpClient.sendCommand("SITE CHMOD 777 test");
        System.out.println(ftpClient.getReplyString());
          
//        ftpClient.upload("C:\\testqrcode.png", "testqrcode.png");
      //  ftpClient.sendSiteCommand("chmod 000 testqrcode");
    }
    public static void main(String[] args) throws SocketException, IOException {
        new FtpKClientTest().start();
    }
}
