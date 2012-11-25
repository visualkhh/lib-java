package khh.io.inputstream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class InputMemoryStream  extends InputStream{

    ArrayList<Integer> buf = null;
    public InputMemoryStream(ArrayList<Integer> buf){
        
        this.buf = buf;
       
    }
    int pos=0;
    @Override
    public int read() throws IOException {
        //input.get(index)
        try{
        int g  = buf.get(pos++);
        return g; 
        }catch (IndexOutOfBoundsException e) {
              return -1;
        }
    }
    
    @Override
    public void close() throws IOException {
        super.close();
        buf = null;
    }
    
    public String getString(){
        StringBuffer returnStr=new StringBuffer();
        for (int i = 0; i < buf.size(); i++) {
            returnStr.append((char)(int)buf.get(i));
        }
        return returnStr.toString();
    }
    
    
    

}
