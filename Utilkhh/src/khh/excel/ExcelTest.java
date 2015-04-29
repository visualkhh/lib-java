package khh.excel;

import java.io.File;
import java.io.IOException;

import jxl.read.biff.BiffException;

public class ExcelTest {

	public ExcelTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws BiffException, IOException {
		ExcelReaderParser e = new ExcelReaderParser(new File("c:\\poi.xls"));
		int t = e.getImageCount(0);
		System.out.println(t);
		
		for (int i = 0; i < t; i++) {
			jxl.Image img = e.getImage(0, i);
			int w = img.getImageWidth();
			int h = img.getImageHeight();
			System.out.println(w);
			System.out.println(h);
			
			System.out.println( img.getRow() );
			
			System.out.println();
			System.out.println();
		}
		
	}

}
