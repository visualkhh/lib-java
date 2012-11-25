package khh.file.pack;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class KipCellContainer extends ArrayList<Byte> implements Serializable{
    public void setData(byte[] data){
        this.clear();
        for (int i = 0; i < data.length; i++) {
            this.add((data[i]));
//            this.add(new KipByte(data[i]));
        }
    }
    
    public void appendData(byte[] data) throws IOException{
        for (int i = 0; i < data.length; i++) {
            this.add((data[i]));
//            this.add(new KipByte(data[i]));
        }
    }
    public byte[] getCutByte(int size){
        byte[] b= new byte[size] ;
        for (int i = 0; i <size; i++) {
            b[i]=get(0);
//            b[i]=get(0).getData();
            remove(0);
        }
        return b;
    }
}
