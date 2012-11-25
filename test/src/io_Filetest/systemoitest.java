package io_Filetest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class systemoitest {
    public static void main(String[] args) throws IOException {
        streamTest(System.in,System.out);

    }
    public static void streamTest(InputStream is,OutputStream os)throws IOException{
        while (true) {
            int i = is.read();
            
            if(i==-1){
                break;
            }
            char c=(char)i;
            os.write(c);
        }
    }

}
