package khh.file.pack;

import java.io.Serializable;

public class KipByte  implements Serializable {
    byte data;
    Long index=null;
    
    public KipByte() {
    }
    public KipByte(byte data) {
        this.data=data;
    }

    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }
    
}
