package khh.ppt;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import jxl.read.biff.BiffException;

import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Fill;
import org.apache.poi.hslf.model.Picture;
import org.apache.poi.hslf.model.Shape;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.SlideMaster;
import org.apache.poi.hslf.model.TextBox;
import org.apache.poi.hslf.usermodel.SlideShow;

public class PPTWriteParser extends PPTParser{

	public PPTWriteParser() throws BiffException, IOException{
		this(new SlideShow());
	}
	public PPTWriteParser(SlideShow slideShow) throws BiffException, IOException{
		super(slideShow);
	}
	
	public void setPageSize(Dimension size){
		getSlideShow().setPageSize(size);
	}
	public Slide createSlide(){
		return getSlideShow().createSlide();
	}
	
	public void addTitle(int slideIndex, String title){
		addTitle(getSlide(slideIndex),title);
	}
	public void addTitle(Slide slide, String title){
		TextBox titlebox = slide.addTitle();
		titlebox.setText(title);
	}
	
	public void addShape(int slideIndex,Shape shape){
		addShape(getSlide(slideIndex),shape);
	}
	public void addShape(Slide slide,Shape shape){
		slide.addShape(shape);
	}
	
	
	public void setBackground(String path,int pictureType) throws IOException{
		setBackground(new File(path), pictureType);
	}
	
	//(slide,file,Picture.PNG)
	public void setBackground(File file, int pictureType) throws IOException{
		SlideShow ppt = getSlideShow();
        SlideMaster master = ppt.getSlidesMasters()[0];

        Fill fill = master.getBackground().getFill();
        int idx = ppt.addPicture(file, pictureType);
        fill.setFillType(Fill.FILL_PICTURE);
        fill.setPictureData(idx);
	}
	//(slide,file,Picture.PNG)
	public void setBackground(Slide slide,File file, int pictureType) throws IOException{
        slide.setFollowMasterBackground(false);
        Fill fill = slide.getBackground().getFill();
        int idx = getSlideShow().addPicture(file, pictureType);
        fill.setFillType(Fill.FILL_PATTERN);
        fill.setPictureData(idx);
	}
	
	public void addTable(Slide slide,String[][] table){
//		Table table = new Table(5, 2);
//	      for (int i = 0; i < data.length; i++) {
//	          for (int j = 0; j < data[i].length; j++) {
//	              TableCell cell = table.getCell(i, j);
//	              cell.setText(data[i][j]);
//
//	              RichTextRun rt = cell.getTextRun().getRichTextRuns()[0];
//	              rt.setFontName("Arial");
//	              rt.setFontSize(10);
//
//	              cell.setVerticalAlignment(TextBox.AnchorMiddle);
//	              cell.setHorizontalAlignment(TextBox.AlignCenter);
//	          }
//	      }
//
//	      //set table borders
//	      Line border = table.createBorder();
//	      border.setLineColor(Color.black);
//	      border.setLineWidth(1.0);
//	      table.setAllBorders(border);
//
//	      //set width of the 1st column
//	      table.setColumnWidth(0, 300);
//	      //set width of the 2nd column
//	      table.setColumnWidth(1, 150);
//
//	      slide.addShape(table);
//	      table.moveTo(100, 100);
	}
	
	
	
	public void write(String path) throws IOException{
		write(new File(path));
	}
	public void write(File file) throws IOException{
		FileOutputStream out = new FileOutputStream(file);
		getSlideShow().write(out);
	}
	
}
