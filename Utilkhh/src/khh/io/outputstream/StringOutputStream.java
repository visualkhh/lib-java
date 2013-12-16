package khh.io.outputstream;

import java.io.IOException;
import java.io.OutputStream;

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