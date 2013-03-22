import java.io.IOException;

import jxl.read.biff.BiffException;
import khh.file.util.FileUtil;
import khh.ppt.*;

import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;

public class POItest{
	public POItest() throws Exception{
		this.start();
	}

	public void start() throws IOException, BiffException{
//		String filepath = "D:\\finger\\신한\\비즈파트너\\비즈파트너산출물\\BizBANK가이드북(영업점용).ppt";;
//		PPTParser  p = new PPTParser(filepath);
//		Slide[] s = p.getSlides();
//		for(int i = 0; i < s.length; i++){
//			FileUtil.writeFile(i+"aa.jpg",  p.getSlideBuffredImage(s[i]), "jpg");
//		}
		PPTWriteParser p = new PPTWriteParser();
		p.addTitle( p.createSlide(),"1");
		p.addTitle( p.createSlide(),"2");
		p.addTitle( p.createSlide(),"3");
		p.addTitle( p.createSlide(),"4");
		p.write("./g.ppt");
	}

	public static void main(String[] args) throws Exception{
		new POItest();
	}
}
