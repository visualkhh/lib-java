import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.script.ScriptException;

import khh.cloude.image.CloudImage;
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
//    	String path = testmain.class.getResource("testmain.java").getPath(); 
//    	System.out.println(path);
//    	System.out.println( FileUtil.getFilePath(testmain.class, "testmain.class") );
//    	ScriptManager manager = new ScriptManager();
//    	System.out.println( manager.getJavaScriptEngin().eval("document.write(1)"));
//    	ScriptEngine engine = manager.getEngineByName("js"); 
//    	Object result = engine.eval("4*5"); 
    	CloudImage f = new CloudImage();
//    	f.setData(FileUtil.readFileToByte("c:\\23950902.jpg"));
//    	f.writeImagePNG(new File("c:\\ggg.png"));
    	
    	byte[] b = f.readImagePNG(new File("c:\\ggg.png"));
    	FileUtil.writeFile("c:\\aa.jpg", b);
    }
}
