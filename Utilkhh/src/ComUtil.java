import java.io.File;
import java.io.IOException;

import khh.file.pack.util.CompressionUtil;

public class ComUtil {
	public static void main(String[] args) throws IOException {
		CompressionUtil zip = new CompressionUtil();
//		zip.zip(new File("D:\\khh\\office\\project\\workspace"));
		zip.zip(new File("D:\\khh\\office\\project\\ggg"));
		
//		String name="vvv/";
//		ZipArchiveEntry entry = new ZipArchiveEntry(name);
//		entry.setSize(5);
//		
//		String charsetName = Charset.defaultCharset().name();
//		File destFile = new File("c:\\gg.zip"); 
//		
//		
//		ZipArchiveOutputStream zipOutput = new ZipArchiveOutputStream(destFile);
//		zipOutput.setEncoding(charsetName);
//		
//		
//		
//		
//		zipOutput.putArchiveEntry(entry);
////		zipOutput.write(0);
//		zipOutput.closeArchiveEntry();
//		
//		zipOutput.close();
	}
}
