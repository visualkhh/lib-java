package khh.communication.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketException;

import com.oroinc.net.ftp.FTPClient;
import com.oroinc.net.ftp.FTPReply;


public class FTPKClient extends FTPClient {
    
    final public static String PWD="PWD";
    final public static String SEND="SEND";
    final public static String PUT="PUT";
    final public static String RECV="RECV";
    final public static String GET="GET";
    final public static String DEL="DEL";
    final public static String DELETE="DELETE";
    final public static String LIST="LIST";
    final public static String MKDIR="MKDIR";
    final public static String CHMOD="CHMOD";
    final public static String FEAT="FEAT";
    
    @Override
    public void connect(InetAddress arg0) throws SocketException, IOException {
        super.connect(arg0);
        init();
        
    }
    @Override
    public void connect(InetAddress arg0, int arg1) throws SocketException, IOException {
        super.connect(arg0, arg1);
        init();
    }
    @Override
    public void connect(InetAddress arg0, int arg1, InetAddress arg2, int arg3) throws SocketException, IOException {
        super.connect(arg0, arg1, arg2, arg3);
        init();
    }
    @Override
    public void connect(String arg0) throws SocketException, IOException {
        super.connect(arg0);
        init();
    }
    @Override
    public void connect(String arg0, int arg1) throws SocketException, IOException {
        super.connect(arg0, arg1);
        init();
    }
    @Override
    public void connect(String arg0, int arg1, InetAddress arg2, int arg3) throws SocketException, IOException {
        super.connect(arg0, arg1, arg2, arg3);
        init();
    }
    private void init() throws IOException{
        if(isConnected()){
            int reply = getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                disconnect();
                throw new SocketException("서버로부터 연결을 거부당했습니다.");
            }
        }
    }
    
    public File download(String source, String downFilePath) throws IOException {
        OutputStream output = null;
        File local = null;
        try {
            // 받는 파일 생성
            local = new File(downFilePath);
            output = new FileOutputStream(local);
        } catch (FileNotFoundException fnfe) {
            throw new IOException("다운로드할 디렉토리가 없습니다.");
        }

        File file = new File(source);
        try {
            if (retrieveFile(source, output)) {
                //
            }
        } catch (IOException ioe) {
            throw new IOException("파일을 다운로드 하지 못했습니다.");
        }
        return local;
    }
    
 // 파일을 전송 한다
    public void upload(String upFilePath, String targetFileName) throws IOException {

        boolean flag = false;
        InputStream input = null;
        File local = null;

        try {
            local = new File(upFilePath);
            input = new FileInputStream(local);
        } catch (FileNotFoundException e) {
            throw new IOException("로컬파일이 없습니다.");
            //return flag;
        }

        try {
            // targetName 으로 파일이 올라간다
            if (storeFile(targetFileName, input)) {
                flag = true;
            }
        } catch (IOException e) {
           throw new IOException("파일을 전송하지 못했습니다.");
            //return flag;
        }
       // return flag;
    }
    // 서버 디렉토리 이동
    public void cd(String path) throws IOException {
        
            changeWorkingDirectory(path);
    }
    
    public String getPath() throws IOException{
        return printWorkingDirectory();
    }
    
    public boolean chmod(String permission,String path) throws IOException{
        // final public static String CHMOD="CHMOD";
        return sendSiteCommand(this.CHMOD+" "+permission+" "+path);
    }
    public int mkdir(String directory ) throws IOException{
        return mkd(directory);
    }

    //닫기.
    @Override
    public void disconnect() throws IOException {
        super.disconnect();
    }
    
    @Override
    protected void finalize() throws Throwable {
        logout();
        disconnect();
        super.finalize();
    }
}
