package khh.ppt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import jxl.read.biff.BiffException;

import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Shape;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.PictureData;
import org.apache.poi.hslf.usermodel.SlideShow;
//http://poi.apache.org/      API and example      
//http://poi.apache.org/slideshow/how-to-shapes.html
//visualkhh@gmail.com
public class PPTParser{
	
	private SlideShow slideShow = null;

	public PPTParser(SlideShow slideShow) throws BiffException, IOException{
		setSlideShow(slideShow);
	}
	public SlideShow getSlideShow(){
		return slideShow;
	}

	public  void setSlideShow(SlideShow slideShow){
		this.slideShow = slideShow;
	}

	public Slide[] getSlides(){
		return getSlideShow().getSlides();
	}
	public Slide getSlide(int slideIndex){
		Slide[] slides = getSlides();
		Slide slide = slides[slideIndex];
		return slide;
	}
	public int getSlideSize(){
		return getSlides().length;
	}
	public TextRun[] getTextRuns(int slideIndex){
		return getTextRuns(getSlide(slideIndex));
	}
	public TextRun[] getTextRuns(Slide slide){
		return slide.getTextRuns();
	}
	public Shape[] getShape(int slideIndex){
		return getShapes(getSlide(slideIndex));
	}
	public Shape[] getShapes(Slide slide){
		return slide.getShapes();
		//slide.getShapes().getShapeName();
//	     if (sh[j] instanceof Line){
//	         Line line = (Line)sh[j];
//	         //work with Line
//	       } else if (sh[j] instanceof AutoShape){
//	         AutoShape shape = (AutoShape)sh[j];
//	         //work with AutoShape
//	       } else if (sh[j] instanceof TextBox){
//	         TextBox shape = (TextBox)sh[j];
//	         //work with TextBox
//	       } else if (sh[j] instanceof Picture){
//	         Picture shape = (Picture)sh[j];
//	         //work with Picture
//	       }
	}
	
	public PictureData[] getPictureDatas(){
		/*
		 byte[] data = pict.getData();
		FileOutputStream out = new FileOutputStream("pict_"+i + ext);
      	out.write(data);
      	out.close()
      
		 int type = pict.getType();
	     String ext;
	     switch (type){
	      case Picture.JPEG: ext=".jpg"; break;
	      case Picture.PNG: ext=".png"; break;
	      case Picture.WMF: ext=".wmf"; break;
	      case Picture.EMF: ext=".emf"; break;
	      case Picture.PICT: ext=".pict"; break;
	      default: continue;
	    }
	    */
		return getSlideShow().getPictureData();
	}
	
	
	public Dimension getPageSize(){
		return getSlideShow().getPageSize();
	}

	public BufferedImage getSlideBuffredImage(int slideIndex){
		Slide[] slides = getSlides();
		Slide slide = slides[slideIndex];
		return getSlideBuffredImage(slide);
	}
	public BufferedImage getSlideBuffredImage(Slide slide){
		Dimension size = getPageSize();
		return getSlideBuffredImage(slide, 0, 0, size.width, size.height);
	}
	public BufferedImage getSlideBuffredImage(Slide slide, int startX, int startY, int endX, int endY){
		BufferedImage img = new BufferedImage(endX-startX,endY-startY,BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = img.createGraphics();
		//clear the drawing area
	    graphics.setPaint(Color.white);
	    graphics.fill(new Rectangle2D.Float(startX, startY, endX, endY));
	    slide.draw(graphics);
	    return img;
	}

}
