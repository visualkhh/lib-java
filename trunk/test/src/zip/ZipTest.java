package zip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.zip.ZipOutputStream;

public class ZipTest {
	public static void main(String[] args) throws Exception {
		File[] fileArray  = new File("C:\\Program Files\\NPKI").listFiles();
		String zfileNm="vvv.zip";
		File zfile = new File(zfileNm);
		
		//zip 파일객체 스트림
		FileOutputStream foutput = new FileOutputStream(zfile);
		
		//집출력스트림
		ZipOutputStream zoutput = new ZipOutputStream(foutput);
		
		zipEnter()
		
		zoutput.close();
		
		foutput.close();
		
		
	}
}
