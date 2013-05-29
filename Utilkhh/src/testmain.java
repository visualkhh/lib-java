import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.script.ScriptException;

import khh.cloude.image.CloudImage;
import khh.conversion.util.ConversionUtil;
import khh.file.util.FileUtil;
import khh.property.util.PropertiesUtil;
import khh.script.ScriptManager;


public class testmain {
	public testmain() {
		// TODO Auto-generated constructor stub
	}
	
	public void s() throws InterruptedException, IOException{
	}
    public static void main(String[] args) throws InterruptedException, IOException, ScriptException {
    	ByteBuffer b = ByteBuffer.allocate(10);
    	b.put((byte)66);
    	b.put((byte)66);
    	b.put((byte)66);
    	b.put((byte)66);
    	b.put((byte)66);
    	b.put((byte)66);
    	b.position(0);
    	b.limit(1);
    	System.out.println(ConversionUtil.toString(b));
    }
}
