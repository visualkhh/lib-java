package khh.io.outputstream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class OutputMemoryStream  extends OutputStream{
    protected ArrayList<Integer> buf;
    public OutputMemoryStream() {
        buf = new ArrayList<Integer>();
    }
    
    @Override
    public synchronized void write(int b) throws IOException {
        buf.add(b);
    }
    
    /*
    private BufferedInputStream buffereadinputstream = null;
    public MemoryOutputStream(OutputStream out) {
        super(out);
        buffereadinputstream = new BufferedInputStream(new InputStream() {
            private int pos=0;
            @Override
            public int read() throws IOException {
                return getBuffer()[pos++];
            }
        });
    }

    public byte []  getBuffer() throws IOException{
        if (buf == null){
            throw new IOException("Stream closed");
        };
        return buf;
    }

    public BufferedInputStream getBuffereadinputstream() {
        return buffereadinputstream;
    }*/
    

    public ArrayList<Integer>  getBuffer() throws IOException{
        if (buf == null){
            throw new IOException("Stream closed");
        };
        return buf;
    }



    public String getString(){
        StringBuffer returnStr=new StringBuffer();
        for (int i = 0; i < buf.size(); i++) {
            returnStr.append((char)(int)buf.get(i));
        }
        return returnStr.toString();
    }
    
    
   
}
