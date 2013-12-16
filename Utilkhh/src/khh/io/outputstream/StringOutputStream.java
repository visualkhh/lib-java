package khh.io.outputstream;

import java.io.IOException;
import java.io.OutputStream;

//ByteArrayOutputStream 클래스를 써라 기본으로되어있는거 아고
public class StringOutputStream extends OutputStream {

    StringBuilder mBuf;

    public StringOutputStream() {
        mBuf = new StringBuilder();
    }

    public void write(int byte_val) throws IOException {
        mBuf.append((char) byte_val);
    }

    public String getString() {
        return mBuf.toString();
    }
}