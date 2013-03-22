package khh.ppt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jxl.read.biff.BiffException;

import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.SlideShow;

public class PPTReadParser extends PPTParser{
	public PPTReadParser() throws BiffException, IOException{
		this(new SlideShow());
	}
	public PPTReadParser(String filepath) throws BiffException, IOException{
		this(new SlideShow(new HSLFSlideShow(filepath)));
	}
	public PPTReadParser(File file) throws BiffException, FileNotFoundException, IOException{
		this(new SlideShow(new FileInputStream(file)));
	}
	public PPTReadParser(SlideShow slideShow) throws BiffException, IOException{
		super(slideShow);
	}

	public void open(String filepath) throws IOException{
		setSlideShow(new SlideShow(new HSLFSlideShow(filepath)));
	}
	public void open(File file) throws FileNotFoundException, IOException{
		setSlideShow(new SlideShow(new FileInputStream(file)));
	}
}
