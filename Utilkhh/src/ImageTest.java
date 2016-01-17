
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.text.html.HTML.Tag;


import khh.file.util.FileUtil;
import khh.image.ImageUtil;
import khh.random.Randomer;
import khh.util.Util;

public class ImageTest {
	public static void main(String[] args) throws IOException {
//	        BufferedImage thum = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
		int width = 30;
		int height = 30;
		int type = BufferedImage.TYPE_INT_ARGB;
		String label = "c";
        BufferedImage thum = ImageUtil.getRandomImage(width, height, type);
//        BufferedImage thum = new BufferedImage(width, height, type); 
	        
        Graphics2D g = thum.createGraphics(); // 가상이미지에 씀 
        g.setColor(ImageUtil.getRandomColor());
        g.setFont(new Font( "SansSerif", Font.BOLD, 48 ));
        g.drawString("v", 2, 28);
        g.setColor(ImageUtil.getRandomColor());
        g.setFont(new Font( "SansSerif", Font.BOLD, 40 ));
        g.drawString("v", 5, 25);
        FileUtil.writeFile(new File("c:\\ggg.png"), thum, "png");
//	        g.drawImage(buff, x,y, width, height, null); // 이미지 만듬 
//	        g.dispose();
//	        return thum;
//	        BufferedImage dest = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
//	        Graphics2D g = dest.createGraphics();
//	        g.setComposite(AlphaComposite.Src);
//	        g.drawImage(buff, 0, 0, width, height, x, y, x + width, y + height,null);
//	        g.dispose();
//	        return dest;
	}
}
